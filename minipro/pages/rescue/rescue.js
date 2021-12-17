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
    personName: '',
    telephone: '',
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
      personName: options.personName,
      telephone: options.telephone
    });
  },
  modify() {
    wx.redirectTo({
      url: '/pages/modify_info/modify_info?username=' + this.data.username + '&user=rescue',
    })
  },
  case() {
    let res = {
      data: [
        {
          caseId: '1',
          personName: '李四',
          time: '2021-10-24 08:00:00',
          status: '已结束'
        },
        {
          caseId: '2',
          personName: '王五',
          time: '2021-10-27 21:00:00',
          status: '进行中'
        },
      ]
    };
    // todo: GET 请求失踪者列表
    wx.redirectTo({
      url: '/pages/rescue_case/rescue_case?list=' + encodeURIComponent(JSON.stringify(res.data)),
    })
  }
})
