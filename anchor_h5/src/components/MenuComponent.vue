<template>
    <div>
        <!-- 添加菜单项表单 -->
        <el-dialog title="添加菜单项" :visible.sync="addDialogVisible" @close="resetAddForm">
            <el-form ref="addForm" :model="addForm" label-width="100px" :rules="addRules">
                <el-form-item label="菜单名称" prop="menuName">
                    <el-input v-model="addForm.menuName"></el-input>
                </el-form-item>
                <!-- 新增补是否是菜单栏 -->
                <el-form-item v-if="!isEditing" label="是否是菜单栏" prop="isLink">
                    <el-switch v-model="addForm.isLink"></el-switch>
                </el-form-item>
                <el-form-item label="图标" prop="icon">
                    <el-radio-group v-model="addForm.icon">
                        <el-row :gutter="10">
                            <el-col  :span="1.9" v-for="(item, index) in iconOptions" :key="index">
                                <el-radio-button :label="item.value">
                                    <i :class="item.value"></i>
                                </el-radio-button>
                            </el-col>
                        </el-row>
                    </el-radio-group>
                </el-form-item>
                <!-- 链接条件渲染链接，如果isMenuItem为真，则不显示 -->
                <el-form-item label="链接" prop="path" v-if="!addForm.isLink">
                    <el-input v-model="addForm.path"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" prop="parentId">
                    <el-cascader v-model="selectedParentId" placeholder="默认为顶级菜单" :props="props" size="small"
                        filterable></el-cascader>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitAddForm">添加</el-button>
            </div>
        </el-dialog>

        <!-- 菜单列表展示 -->
        <el-card>
            <el-button type="primary" @click="addDialogVisible = true">添加菜单项</el-button>
            <el-table :data="menuList" style="width: 100%" row-key="id" @row-click="handleRowClick">
                <template v-slot:empty>
                    <!-- 处理空数据时的显示 -->
                </template>
                <el-table-column prop="menuName" label="菜单名称"></el-table-column>
                <el-table-column prop="icon" label="图标">
                    <template slot-scope="scope">
                        <i :class="scope.row.icon"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="path" label="链接"></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button size="mini" type="primary" @click="editMenu(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="deleteMenu(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>

            </el-table>
        </el-card>
    </div>
</template>

<script>
import  menuService from '@/api/menu.js'

export default {
    data() {
        return {
            selectedParentId: [],
            addDialogVisible: false,
            addForm: {
                menuName: '',
                icon: '',
                path: '',
                parentId: '',
                isLink: false
            },
            isEditing: false,
            addRules: {
                menuName: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
                path: [{ required: true, message: '请输入链接', trigger: 'blur' }],

            },
            iconOptions: [
                { label: '首页', value: 'el-icon-s-home' },
                { label: '菜单', value: 'el-icon-menu' },
                { label: '设置', value: 'el-icon-setting' },
                { label: '用户', value: 'el-icon-user' },
                { label: '分析', value: 'el-icon-data-analysis' },
                { label: '文档', value: 'el-icon-document' },
                { label: '消息中心', value: 'el-icon-message' },
                { label: '通知', value: 'el-icon-bell' },
                { label: '日志', value: 'el-icon-notebook-1' },
                { label: '设备', value: 'el-icon-monitor' },
                { label: '管理员', value: 'el-icon-s-custom' },
                { label: '角色', value: 'el-icon-user-solid' },
                { label: '权限', value: 'el-icon-key' },
                { label: '帮助', value: 'el-icon-question' },
                { label: '退出', value: 'el-icon-switch-button' },
                { label: '退出', value: 'el-icon-magic-stick' },

                // 填充您的图标选项，如 { label: '首页', value: 'el-icon-s-home' }, ...
            ],
            menuTreeData: [], // 从后端获取的菜单树数据
            menuList: [], // 从后端获取的菜单列表数据
            props: {
                value: 'id',
                label: 'menuName',
                lazy: true,
                lazyLoad: this.lazyLoad
            },
            
        };
    },
    methods: {
        async fetchMenuTreeData() {

            // 从后端获取菜单树数据，填充到 this.menuTreeData
        },
        fetchMenuList() {
            // 从后端获取菜单列表数据，填充到 this.menuList
            menuService.get().then(res => { 
                console.log(res, 'res');
                this.menuList=res
            })
        },
        // 联级选择器函数
        lazyLoad(node, resolve) {
            if (node.level === 0) {
                menuService.getSupMenu().then(res => {
                    res.push({ id: '0', menuName: '顶级菜单', children: [] })
                    resolve(res)
                })
            }
            if (node.level === 1) {
                if (node.value === '0') {
                        resolve([])
                        return
                }
                menuService.getById(node.value).then(res => {
                    console.log(res);
                    if (res === null) {
                        resolve([])
                        return
                    }
                    resolve(res)
                })
            }
            if (node.level === 2) {
                menuService.getById(node.value).then(res => {
                    if (res === null) {
                        resolve([])
                        return
                    }
                    resolve(res)
                })
            }
            if (node.level === 3) {
                resolve([])
            }
        },
        resetAddForm() {
            this.addForm={
                menuName: '',
                icon: '',
                path: '',
                parentId: '',
                isLink: false
            };
            this.addForm.parentId = [];
        },
        handleMenuTreeCheckChange(data, checkedKeys) {
            this.addForm.parentId = checkedKeys;
        },
        submitAddForm() {
            this.$refs.addForm.validate(async valid => {
                if (valid) {
                        this.addForm.parentId = this.selectedParentId[this.selectedParentId.length - 1];
                        if (this.addForm.isLink == true) {
                            this.addForm.isLink = 1
                        } else { this.addForm.isLink = 0 }
                        if (!this.addForm.parentId) {
                            this.addForm.parentId=0
                    }
                    if (this.isEditing) { // 如果是编辑菜单项，发送更新请求
                        const response = await menuService.update(this.addForm); // 发送更新菜单请求
                        if (response) {
                            this.$message.success(response);
                            this.addDialogVisible = false;
                            this.isEditing = false
                            await this.fetchMenuList(); // 更新菜单列表
                            this.resetAddForm(); // 重置添加表
                        } else {
                            this.$message.error('更新菜单项失败');
                        }
                    } else {
                        const response = await menuService.save(this.addForm); // 发送添加菜单请求
                        if (response) {
                            this.$message.success(response);
                            await this.fetchMenuList(); // 更新菜单列表
                            this.resetAddForm(); // 重置添加表单
                            this.addDialogVisible = false;
                        } else {
                            this.$message.error('添加菜单项失败');
                        }
                     }
                        
                     
                }
            });
        },
        editMenu(menuItem) {
            this.addDialogVisible = true;
            this.isEditing = true; 
            this.addForm = { ...menuItem };

            // 打开编辑菜单项的对话框，传入要编辑的菜单项数据
        },
        handleRowClick(row) {
            // 控制展开行的逻辑，这里可以根据需要调整
            row.expand = !row.expand;
        },
        async deleteMenu(menuItemId) {
            const confirm = await this.$confirm('确定要删除该菜单项吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            });
            if (confirm) {
                    const response = await menuService.delete(menuItemId); // 发送删除菜单请求
                    if (response) {
                        this.$message.success('删除菜单项成功');
                        await this.fetchMenuList(); // 更新菜单列表
                    } else {
                        this.$message.error(response);
                    }
            }
        },
    },
    mounted() { 
        console.log(100);
        this.fetchMenuList();
    },
    created() {
    },
};
</script>

