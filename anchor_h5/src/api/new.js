import request from '@/utils/request'

export default {
  save: function () {
    return request({
      url: '/new/save',
      method: 'get',
    })
  }
}