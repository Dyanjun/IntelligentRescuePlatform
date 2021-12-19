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
    caseAddress: {}
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function() {
  },
  addLoser() {
    wx.redirectTo({
      url: '/pages/add_loser/add_loser',
    })
  },
  mapView() {
    var that = this;
    wx.chooseLocation({
      success: function (res) {
        console.log(res);
        that.data.caseAddress = {
          address: res.address,
          name: res.name,
          latitude: res.latitude,
          longitude: res.longitude
        };
        console.log(that.data.caseAddress);
      },
    })
  },
  submitCase(event) {
    console.log(event);
    const {
      name, 
      time
    } = event.detail.value;
    console.log(name, time, this.data.caseAddress);
    // todo: POST 存入数据库
    wx.redirectTo({
      url: '/pages/family_case/family_case',
    })
  }
})
