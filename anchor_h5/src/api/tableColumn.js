import request from '@/utils/request'

export default {
  get: function (tableName) {
        return request({
        url: 'gen/selectTableColumnListByName?tableName=' + tableName,
        method: 'get'
    })
  }
}