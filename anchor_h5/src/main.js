import Vue from 'vue'
import App from './App.vue'
import store from './store' // 引入你创建的Vuex Store
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css' // 引入 Element 默认主题

import { Message } from 'element-ui'
import Fragment from 'vue-fragment'

Vue.prototype.$message = Message
Vue.use(ElementUI).use(Fragment.Plugin)
new Vue({
  router:router,
  store, // 注册Vuex Store
  render: h => h(App),
}).$mount('#app')