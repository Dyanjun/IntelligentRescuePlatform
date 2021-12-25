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
      url: '/pages/modify_info/modify_info?username=' + this.data.username + '&user=family',
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
      url: '/pages/family_case/family_case?list=' + encodeURIComponent(JSON.stringify(res.data)),
    })
  },
  loser() {
    let res = {
      data: [
        {
          caseId: '1',
          name: '李四',
          gender: '男',
          age: '73',
          dressing: '白色外套，黑色裤子',
          accent: '上海话',
          image: 'https://pic.baike.soso.com/ugc/baikepic2/0/ori-20210515150343-1538722476_jpeg_500_563_24332.jpg/800'
        },
        {
          person_id: '2',
          name: '王五',
          gender: '男',
          age: '80',
          dressing: '黑色中山装',
          accent: '四川方言',
          image: 'https://pic1.zhimg.com/v2-ff88ecb3c8d65559189b989d52dfedff_720w.jpg?source=172ae18b'
        },
      ]
    };
    // todo: GET 请求失踪者列表
    wx.redirectTo({
      url: '/pages/loser/loser?list=' + encodeURIComponent(JSON.stringify(res.data)),
    })
  }
})
