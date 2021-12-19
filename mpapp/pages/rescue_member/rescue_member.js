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
    caseId: '',
    rescueList: [],
    markers: [],
    rescueId: '',
  },

  onLoad: function(options) {
    console.log(options)
    var caseId = options.caseId;
    var that = this;
    wx.request({
      url: app.globalData.webserver + 'case/' + caseId + '/rescue_member',
      header: {
        'content-type': 'application/text'
      },
      success(res) {
        console.log(res.data.data);
        var markers = [];
        for (var i = 0; i < res.data.data.length; i++) {
          markers.push([{
            longitude: res.data.data[i].place.longitude,
            latitude: res.data.data[i].place.latitude
          }])
        }
        that.setData({
          caseId: caseId,
          rescueList: res.data.data,
          markers: markers
        });
      }
    })
  },
})
