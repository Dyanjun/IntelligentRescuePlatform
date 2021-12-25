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
  submitLogin(event) {
    console.log(event);
    const { 
      username,
      password,
      radio
    } = event.detail.value;
    console.log(username, password, radio);
    let res = {
      data: {
        personName: "张三",
        telephone: "18092417848"
      }
    }
    
    // todo: POST 请求后端验证 并返回数据
    if (radio == 'family') {
      wx.redirectTo({
        url: '/pages/family/family?username=' + username + '&personName=' + res.data.personName + '&telephone=' + res.data.telephone,
      });
    } else if (radio == 'rescue') {
      wx.redirectTo({
        url: '/pages/rescue/rescue?username=' + username + '&personName=' + res.data.personName + '&telephone=' + res.data.telephone,
      });
    }
    
  },
  redirectRegister() {
    // todo: POST 存入数据库
    wx.navigateTo({
      url: '/pages/register/register'
    })
  }
})
