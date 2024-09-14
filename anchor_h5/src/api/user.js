import request from '@/utils/request'

export default {
  save: function () {
    return request({
      url: 'sys/getUser',
      method: 'get'
    })
  },
  async getCode(){
    return request({
      url: 'sys/getCode',
      method: 'get'
    })
    
  },
  login(userName,password,verCode){
    return request({
      url: 'sys/login',
      method: 'post',
      data:{
        userName:userName,
        password:password,
        verCode:verCode
      }
    })
  },

  getUser(userName) {
     return request({
      url: 'sys/getUser',
       method: 'get',
       params: {userName: userName}
    })
  },
  existUser(userName) {
      return request({
      url: 'sys/existUser',
       method: 'get',
       params: {userName: userName}
    })
  },
  register(userName,password,nickName,verCode){
      return request({
      url: 'sys/register',
      method: 'post',
      data:{
        userName:userName,
        password: password,
        nickName: nickName,
        verCode:verCode
      }
    })
  }
}


