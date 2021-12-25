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
    loserList: [],
    family_id: '',
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function(options) {
    var that = this;
    console.log(options);
    wx.request({
      url: app.globalData.webserver + 'missing_person/family_member/' + options.family_id,
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data);
        that.setData({
          loserList: res.data.data,
          family_id: options.family_id
        });
      }
    });
  },
  addLoser() {
    var that = this;
    wx.navigateTo({
      url: '/pages/add_loser/add_loser?family_id=' + that.data.family_id,
    })
  },
  submit(event) {
    console.log(event.currentTarget.dataset.index)
    var missingPersonId = event.currentTarget.dataset.index;
    console.log(this.data.familyId)
    wx.navigateTo({
      url: '/pages/family_case_submit/family_case_submit?familyId=' + this.data.family_id + '&missingPersonId=' + missingPersonId,
    })
  }
})
