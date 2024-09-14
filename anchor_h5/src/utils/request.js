import URL from '@/common/config'
import axios from 'axios'
import store from '@/store'
import router from '@/router'
// import { Loading } from 'element-ui';
const APP_SERVER_URL = URL.APP_SERVER_URL
// 创建 axios 实例，将来对创建出来的实例，进行自定义配置
// 好处：不会污染原始的 axios 实例
const baseURL = APP_SERVER_URL
import { Loading,Message } from 'element-ui';

let loadingInstance = null
const instance = axios.create({
  //1.基础地址，超时时间
  baseURL,
  timeout: 100000
})




// 自定义配置 - 请求/响应 拦截器
// 添加请求拦截器
instance.interceptors.request.use(function (config) {
loadingInstance = Loading.service({
    lock: true, // 锁定屏幕
    text: 'loading...'
  })
  // loadingInstance.fullscreenLoading()
  const token = store.state.token;
  // 只要有token，就在请求时携带，便于请求需要授权的接口
  if (token) {
    config.headers.token = token;
  }
  return config
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error)
})

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
  
  loadingInstance.close()
  // 2xx 范围内的状态码都会触发该函数。
  // 对响应数据做点什么 (默认axios会多包装一层data，需要响应拦截器中处理一下)
  const res = response.data
    console.log(response.data,'response');
  if (response.data.code === 401) {
    router.push('/login'); 
    console.log(store.state.token,'store.state.token');
      store.state.token=''
       Message.error('认证失败或失效，请重新登录');
  }
  if (res.code === 403) {
    Message.error('403,抱歉，您没有权限访问');
  }
    // console.log(response.data.code,'response');
//  loadingInstance1.close()
  if (response.status === 200) {
    // 给错误提示, Toast 默认是单例模式，后面的 Toast调用了，会将前一个 Toast 效果覆盖
    // 同时只能存在一个 Toast
    // 抛出一个错误的promise
      return res
  }
  if (response.status===201) {
      return res
  }


}, function (error) {
  // 超出 2xx 范围的状态码都会触发该函数。
  // 对响应错误做点什么
  return Promise.reject(error)
})

// 导出配置好的实例
export default instance
