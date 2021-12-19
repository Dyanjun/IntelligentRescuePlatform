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
    markers: []
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function(options) {
    console.log(options)
    this.setData({
      caseDetail: JSON.parse(decodeURIComponent(options.list)),
      markers: [{
        latitude: JSON.parse(decodeURIComponent(options.list)).place.latitude,
        longitude: JSON.parse(decodeURIComponent(options.list)).place.longitude,
      }]
    });
  },
  addLoser() {
    wx.redirectTo({
      url: '/pages/add_loser/add_loser',
    })
  },
  showClue() {
    let res = {
      data: [{
        clueId: '1',
        time: '2021-11-1 12:00:00',
        description: '发现相似人员踪迹',
        place: {
          latitude: 31.035687,
          longitude: 121.446891,
        }
      }]
    }
    wx.redirectTo({
      url: '/pages/family_case_clue/family_case_clue?list=' + encodeURIComponent(JSON.stringify(res.data)),
    })
  }
})
