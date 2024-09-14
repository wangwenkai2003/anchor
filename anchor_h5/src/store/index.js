import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 这里定义你的状态（state）
    token: '',
    user: {},
    count: 0,
    bgcIndex: 0,
    // 其他状态...
  },
  mutations: {
    // 这里定义修改状态的方法（mutations）
    SET_TOKEN(state, token) {
      state.token = token
    },
    SET_USER(state, user) {
      state.user = user
    },
    SET_BGC_INDEX(state, index) {
      state.bgcIndex = index
    }
    // 其他mutation...
  },
  actions: {
    // 这里定义异步操作（actions）
    // 其他actions...
  },
  getters: {
    // 这里定义计算属性（getters）
    isLoggedIn: state => !!state.token,
    // 其他getter...
  },
  plugins: [
    createPersistedState({
      // 配置持久化插件
      key: 'my-vuex-store', // 存储的键名
      paths: ['token', 'user'], // 需要持久化的状态路径
      storage: window.localStorage, // 默认使用localStorage，也可以换成sessionStorage或其他兼容的存储方式
    }),
  ],
})