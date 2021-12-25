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
    loserList: []
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
      loserList: JSON.parse(decodeURIComponent(options.list)),
    });
  },
  addLoser() {
    wx.redirectTo({
      url: '/pages/add_loser/add_loser',
    })
  },
  submitRegister(event) {
    console.log(event);
    const {
      username, 
      password,
      personName,
      telephone,
      radio
    } = event.detail.value;
    console.log(username, password, personName, telephone, radio);
    // todo: POST 存入数据库
    wx.redirectTo({
      url: '/pages/login/login',
    })
  }
})
