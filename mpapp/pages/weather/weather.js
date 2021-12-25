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
    weatherInfo: ''
  },
  
  onLoad: function() {
    var that = this;
    // 天气状况
    wx.request({
      url: app.globalData.amapapi,
      data: {
        'key': 'a6cf299a7582006f4a6d758627d55af9',
        'city': '310000'
      },
      header:{
        'content-type': 'application/json'
      },
      success:function(res){
        console.log(res.data);
        var weatherArray = res.data.lives[0];
        that.setData({
          weatherInfo: weatherArray
        })
      }
    })
  },
})
