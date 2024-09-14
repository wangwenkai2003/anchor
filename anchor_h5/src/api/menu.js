import request from '@/utils/request'

export default {
  get: function () {
    return request({
      url: '/menu-items/',
      method: 'get',
    })
  },
  getById: function (id) {
    return request({
      url: '/menu-items/' + id,
      method: 'get',
    })
  },
  save: function (data) {
   return request({
      url: '/menu-items/',
      method: 'post',
      data: data,
   })
  },
  getSupMenu: function () {
    return request({
      url: '/menu-items/supMenu',
      method: 'get'
   })
  },
  update: function (data) {
    return request({
      url: '/menu-items/',
      method: 'put',
      data:data
    })
  },
  delete: function (id) {
    return request({
      url: '/menu-items/' + id,
      method: 'delete',
    })
  }
}