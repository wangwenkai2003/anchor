import request from '@/utils/request'

export default {
  save: function (verse, author, poemTitle) {
    console.log(verse,'tcers');
    return request({
      url: 'poem/savePoem',
      method: 'post',
         data:{
           verse: verse,
           author: author,
           poemTitle: poemTitle
      }
    })
  },
  get: function () {
        return request({
      url: 'poem/getRandomPoem',
      method: 'get'
    })
  }
}