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
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName') // 如需尝试获取用户信息可改为false
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
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
    // POST 存入数据库
    var realUrl = ''
    if (radio == 'family') {
      realUrl = app.globalData.webserver + 'user/register/family_member';
    } else {
      realUrl = app.globalData.webserver + 'user/register/rescue_member'
    }
    wx.request({  
      url: realUrl,  
      data:{
        username: username,
        password: password, 
        name: personName,
        telephone: telephone,
      },  
      method:'POST',  
      header: {  
        'content-type': 'application/json'  
      },  
      success: function (res) {  
        console.log(res.data)
        wx.navigateTo({
          url: '/pages/login/login',
        })
      }  
    })  
  }
})
