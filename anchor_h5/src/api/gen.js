import request from '@/utils/request'

export default {
  gencode: function (data) {
        return request({
            url: 'gen/codeGen',
            method: 'post',
            data: data
    })
  }
}