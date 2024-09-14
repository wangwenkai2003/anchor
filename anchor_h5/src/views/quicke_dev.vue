<template>
    <div>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
            <el-form-item label="表名称" prop="tableName">
                <el-input v-model="queryParams.tableName" placeholder="请输入表名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            </el-form-item>
        </el-form>
        <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="tableName" label="表名" width="180">
            </el-table-column>
            <el-table-column prop="tableComment" label="备注" width="180">
            </el-table-column>
            <el-table-column prop="address" label="操作">
                <template slot-scope="scope">
                    <el-button type="text" icon="el-icon-view" size="small"
                        @click="handleViewClick(scope.row)">查看</el-button>
                    <el-button type="text" icon="el-icon-video-play
" size="small" @click="handleDown(scope.row)">生成代码</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="生成代码" :visible.sync="dialogVisible" width="60%" center>
            <el-form :model="form" :rules="rules" ref="formRef">
                <el-form-item label="生成代码包名" prop="packageName" required>
                    <el-tooltip class="item" effect="dark" content="生成在哪个java包下，例如com.anchor.system"
                        placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.packageName"></el-input>
                </el-form-item>
                <el-form-item label="作者名" prop="functionAuthor" required>
                    <el-input v-model="form.functionAuthor"></el-input>
                </el-form-item>
                <el-form-item label="模块名" prop="moduleName" required>
                    <el-tooltip class="item" effect="dark" content="可理解为子系统名，例如system" placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.moduleName"></el-input>
                </el-form-item>
                <el-form-item label="功能名" prop="functionName" >
                    <el-tooltip class="item" effect="dark" content="描述功能，备注" placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.functionName"></el-input>
                </el-form-item>
                <el-form-item label="业务名" prop="businessName" required>
                    <el-tooltip class="item" effect="dark" content="可理解为功能英文名，例如user（之后会用于权限判断）" placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.businessName"></el-input>
                </el-form-item>
                <el-form-item label="类名" prop="className" required>
                    <el-tooltip class="item" effect="dark" content="java的类名，会用于实体类等地方" placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.className"></el-input>
                </el-form-item>
                <el-form-item label="代码存放地址" prop="genPath">
                    <el-tooltip class="item" effect="dark" content="生成的代码存放在哪个本地文件夹下,列如E:\opt" placement="top-start">
                        <img width="20px" height="20px" src="@/assets/question.svg">
                    </el-tooltip>
                    <el-input v-model="form.genPath"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单" prop="parentId">
                    <el-cascader v-model="selectedParentId" placeholder="默认为顶级菜单" :props="props" size="small"
                        filterable></el-cascader>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="字段详情" :visible.sync="showDetailDialog" width="60%">
            <el-table :data="detailData">
                <!-- 根据您的数据结构定义列 -->
                <el-table-column prop="columnName" label="字段名"></el-table-column>
                <el-table-column label="是否主键">
                    <template slot-scope="scope">
                        <span v-if="scope.row.columnKey === 'PRI'">主键</span>
                        <span v-else>否</span>
                    </template>
                </el-table-column>
                <el-table-column prop="columnType" label="字段类型"></el-table-column>
                <el-table-column prop="columnComment" label="字段描述"></el-table-column>
                <el-table-column label="是否能为空">
                    <template slot-scope="scope">
                        <span v-if="scope.row.isNullable === 'NO'">否</span>
                        <span v-else>是</span>
                    </template>
                </el-table-column>
                <!-- 添加更多列定义... -->
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="showDetailDialog = false">关闭</el-button>
            </span>
        </el-dialog>
    </div>

</template>
<script>
import tableService from '@/api/table.js'
import genService from '@/api/gen.js'
import tableColumnService from '@/api/tableColumn.js';
import menuService from '@/api/menu.js'
export default {
    data() {
        return {
            tableData: [],// 这里是您原有的表格数据
            showDetailDialog: false, // 控制详情对话框的显示隐藏
            detailData: [],
            queryParams: {},
            dialogVisible: false,
            menuOptions: [],
            selectedParentId: [],
            form: {
                genPath: '',
                functionAuthor: '',
                functionName: '',
                moduleName: '',
                businessName: '',
                className: '',
                packageName:''
            },
            menuTreeData: [], // 从后端获取的菜单树数据
            menuList: [], // 从后端获取的菜单列表数据
            props: {
                value: 'id',
                label: 'menuName',
                lazy: true,
                lazyLoad: this.lazyLoad
            },
            rules: {
                genPath: [
                    { required: true, message: '生成代码路径不能为空', trigger: 'blur' }
                ],
                functionAuthor: [
                    { required: true, message: '作者名不能为空', trigger: 'blur' }
                ],
                moduleName: [
                    { required: true, message: '模块名不能为空', trigger: 'blur' }
                ],
                packageName: [
                    { required: true, message: '包名不能为空', trigger: 'blur' }
                ],
                businessName: [
                    { required: true, message: '业务名不能为空', trigger: 'blur' }
                ]
            },
            formRef: null
        }
    },
    mounted() { 
        tableService.get().then(
            res => {
                console.log(res)
                this.tableData = res
            }
        )
    },
    methods: {
        
        get() {
            tableService.get().then(
                res => {
                    console.log(res)
                   
                }
            )
        },
        handleViewClick(row) {
            console.log(row)// 假设row是当前行的数据
            tableColumnService.get(row.tableName).then(
                res => {
                    console.log(res)
                    this.detailData = res
                    this.showDetailDialog = true;
                }
            )
        },
        async fetchMenuTreeData() {

            // 从后端获取菜单树数据，填充到 this.menuTreeData
        },
        fetchMenuList() {
            // 从后端获取菜单列表数据，填充到 this.menuList
            menuService.get().then(res => {
                console.log(res, 'res');
                this.menuList = res
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
        handleQuery() { 

        },
        
        handleDown(row) {
            console.log(row); 
            this.form = row;
            this.dialogVisible = true;
            console.log('生成代码');
        }
        ,
        handleSubmit() { 
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    alert('表单验证成功!');
                    // 这里可以添加提交表单的逻辑
                    this.form.parentId = this.selectedParentId[this.selectedParentId.length - 1];

                    console.log(this.selectedParentId);
                    genService.gencode(this.form).then(
                        res => {
                            console.log(res);
                        })
                    console.log(this.form);
                } else {
                    console.error('表单验证失败!');
                    return false;
                }
            });
        }
    }
}
</script>