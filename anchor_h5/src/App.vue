<template>
  <div>
    <div style="display: flex;">
      <div v-if="$route.path !== '/login'" :style="{ width: menuWidth, backgroundColor: menuBackgroundColor }"
        style="width: 200px;height: 100vh;z-index: 100">
        <div style="padding: 10px;display: flex;">
          <i class="el-icon-s-fold" @click="toggleCollapse" style="margin-left: 15px;font-size: 20px;"
            v-if="!isCollapse"></i>
          <i class="el-icon-s-unfold" @click="toggleCollapse" style="margin-left: 15px;font-size: 20px;" v-else></i>
        </div>
       <el-menu
    :default-active="activeIndex"
    class="el-menu-vertical-demo"
    @open="handleOpen"
    @close="handleClose"
    :collapse="isCollapse"
    :background-color="menuBackgroundColor"
    :text-color="menuTextColor"
    :active-text-color="menuActiveTextColor"
    router
  >
    <!-- 使用递归组件来渲染菜单 -->
    <SubMenuRecursive :menuItems="menuData" />
  </el-menu>
      </div>
      <div :style="{ marginLeft: `${menuWidth}px`, flexGrow: 1 }">
        <router-view></router-view>
      </div>
    </div>

  </div>
</template>
<script>

import menuService from "@/api/menu.js";
import  SubMenuRecursive from '@/components/SubMenuRecursive.vue';
export default {
  name: 'HomePage',
  inject: ['$route'],
   components: {
    SubMenuRecursive,
  }, 
  data() {
    return {
      message: 'Hello, Vue!',
      activeIndex:'',
      menuData:[],
      isCollapse: true,
      menuWidth: '63px',
      topLevelMenus: [],
      currentNavIndex: '1',
      bgcIndex: '0',
      menuColorMap: {
        0: {
          backgroundColor: '#7585BD',
          textColor: '#A3B8E3',
          activeTextColor: '#49608D',
        },
        2: {
          backgroundColor: '#598a9a',
          textColor: '#dffeff ',
          activeTextColor: '#4E6C87',
        },
        1: {
          backgroundColor: '#6289BB',
          textColor: '#5880b1',
          activeTextColor: '#305c8b',
        }
        // ... 添加其他索引对应的颜色
      },
    };
  },
  computed: {
    BgcIndex() {
      return this.$store.state.bgcIndex;
    },

    menuBackgroundColor() {
      return this.menuColorMap[this.bgcIndex]?.backgroundColor || '#7585BD';
    },
    menuTextColor() {
      return this.menuColorMap[this.bgcIndex]?.textColor || '#A3B8E3';
    },
    menuActiveTextColor() {
      return this.menuColorMap[this.bgcIndex]?.activeTextColor || '#49608D';
    },
  },
  watch: {
    BgcIndex(newVal) {
      this.bgcIndex = newVal;
      // 在这里执行当bgcIndex值变化时需要执行的操作
    }
  },
  methods: {
    // 切换菜单的展开/收起状态
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
      this.menuWidth = this.isCollapse ? '63px' : '200px';
      console.log(this.menuWidth);
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    onRandomIndexUpdate(index) {
      console.log(index, 'index');
      this.bgcIndex = index;
    },
    getTopMenu() {
      menuService.get().then(
        res => {
          this.menuData = res
        }
      )
    },
    navigateTo(item) {
      menuService.get(item.id).then(
        res => {
          // this.$store.commit('setMenu', res);
          console.log(res, 'res');
        }
      )
      console.log(item, 'item');
      this.$router.push(item.link);
    },
  },
  mounted() {
    this.getTopMenu();
    console.log(this.$store.state.bgcIndex, 'bgI');

    console.log(this.$store.state.user, 'user');
  },
};
</script>
<style scoped>
.menu-container {
  background-color: #adc7b7;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 100;
  transition: width 0.3s ease;
  /* 添加宽度变化的过渡效果 */
}

.menu-icon {
  margin-left: 18px;
  font-size: 20px;
  cursor: pointer;
}

.content-container {
  padding-left: var(--content-padding, 200px);
  min-width: 0;
  /* 使Flex布局下的内容能够收缩 */
  transition: padding-left 0.3s ease;
  /* 添加内边距变化的过渡效果 */
}
</style>
