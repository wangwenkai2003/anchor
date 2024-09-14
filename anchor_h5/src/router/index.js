import Vue from 'vue'
import VueRouter from 'vue-router'
// import { useUserStore } from '@/stores'
import Login from '@/views/login.vue'
import Home from '@/components/HomeComponent.vue'
import MenuComponent from '@/components/MenuComponent.vue'
import QuickDev from '@/views/quicke_dev.vue'
Vue.use(VueRouter)
const router = new VueRouter({
  mode: 'history',
  // /article 路径 → Article组件
  routes: [
    { 
      path: '/',
      component: Login,
      redirect: '/login',
      // 通过 children 配置项，可以配置嵌套子路由
      // 1. 在children配置项中，配规则
      // 2. 准备二级路由出口
      children: [
        {
          path: '/login',
          component: Login
        }
      ]
    },
    {
      path: '/home',
      component: Home
    },
    {
      path: '/sys_menu',
      component: MenuComponent
    }, {
      path: '/quick_dev',
      component: QuickDev
    }
  ]
})
// router.beforeEach((to) => {
  // 如果没有token, 且访问的是非登录页，拦截到登录，其他情况正常放行
  // const useStore = useUserStore()
  // if (!useStore.token && to.path !== '/login') return '/login'
// })

export default router
