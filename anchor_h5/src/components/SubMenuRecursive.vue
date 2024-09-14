<template>
  <fragment>

    <template v-for="(item) in menuItems">
      <!-- 如果有子菜单，继续递归 -->
      <el-submenu v-if="item.children && item.children.length > 0" :key="item.id" :index="String(item.id)">
        <template slot="title">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.menuName }}</span>
        </template>
        <SubMenuRecursive :menuItems="item.children" />
      </el-submenu>
      <!-- 没有子菜单，直接渲染菜单项 -->
      <el-menu-item v-else :key="item.id" :index="item.link" @click="navigateTo(item)">
        <i :class="item.icon"></i>
        <span slot="title">{{ item.menuName }}</span>
      </el-menu-item>
    </template>

  </fragment>

</template>

<script>
export default {
  name: 'SubMenuRecursive',
  props: {
    menuItems: {
      type: Array,
      required: true,
    },
  },
  methods: {
    navigateTo(item) {
      if (item.isLink==0) {
         this.$router.replace(item.path);
      }
      // 导航处理逻辑，如果有需要的话
      console.log(item);
      console.log('Navigating to:', item.path);
    },
  },
};
</script>
<style>
.el-submenu__title {
  display: flex;
  align-items: center;
}
</style>