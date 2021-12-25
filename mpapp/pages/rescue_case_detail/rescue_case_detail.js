// login.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    motto: '开始使用',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName'), // 如需尝试获取用户信息可改为false
    caseDetail: [],
    markers: [],
    rescueId: '',
  },

  onLoad: function(options) {
    console.log(options)
    this.setData({
      rescueId: options.rescueId,
      caseDetail: JSON.parse(decodeURIComponent(options.list)),
      markers: [{
        latitude: JSON.parse(decodeURIComponent(options.list)).place.latitude,
        longitude: JSON.parse(decodeURIComponent(options.list)).place.longitude,
      }]
    });
  },
  showClue() {
    // let res = {
    //   data: [{
    //     clueId: '1',
    //     time: '2021-11-1 12:00:00',
    //     description: '发现相似人员踪迹',
    //     place: {
    //       latitude: 31.035687,
    //       longitude: 121.446891,
    //     }
    //   }]
    // }

    // GET 线索列表
    var that = this;
    wx.request({
      url: app.globalData.webserver + 'clue/case/' + this.data.caseDetail.caseId,
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        var clueList = res.data.data;
        wx.navigateTo({
          url: '/pages/rescue_case_clue/rescue_case_clue?rescueId=' + that.data.rescueId + '&caseId=' + that.data.caseDetail.caseId + '&list=' + encodeURIComponent(JSON.stringify(clueList)),
        });
      }
    });
  },

  rescueMember() {
    wx.navigateTo({
      url: '/pages/rescue_member/rescue_member?caseId=' + this.data.caseDetail.caseId,
    })
  }
})
