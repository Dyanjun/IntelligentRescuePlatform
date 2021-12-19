// login.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName'), // 如需尝试获取用户信息可改为false
    username: '',
    user: ''
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function(options) {
    console.log(options);
    this.setData({
      username: options.username,
      user: options.user
    })
  },
  submitSave(event) {
    console.log(event);
    const {
      password,
      personName,
      telephone,
    } = event.detail.value;
    console.log(password, personName, telephone);
    // todo: POST 存入数据库
    if (this.data.user == 'family') {
      wx.navigateTo({
        url: '/pages/family/family?username=' + this.data.username + '&personName=' + personName + '&telephone=' + telephone
      });
    } else if (this.data.user == 'rescue') {
      wx.navigateTo({
        url: '/pages/rescue/rescue?username=' + this.data.username + '&personName=' + personName + '&telephone=' + telephone
      });
    }
    
  }
})
