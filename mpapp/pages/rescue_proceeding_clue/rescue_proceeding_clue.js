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
    clueList: [],
    hasMarker: false,
    rescueId: '',
  },

  onLoad: function(options) {
    console.log(options)
    var markers = []
    var clueList = JSON.parse(decodeURIComponent(options.list))
    for (var i = 0; i < clueList.length; i++) {
      markers.push([{
        longitude: clueList[i].place.longitude,
        latitude: clueList[i].place.latitude
      }])
    }
    this.setData({
      rescueId: options.rescueId,
      caseId: options.caseId,
      clueList: clueList,
      markers: markers
    });
  },

  test() {
    console.log(this.data.clueList);
  },

  submit() {
    console.log(this.data.caseId)
    wx.navigateTo({
      url: '/pages/rescue_clue_submit/rescue_clue_submit?rescueId=' + this.data.rescueId + '&caseId=' + this.data.caseId,
    })
  }
})
