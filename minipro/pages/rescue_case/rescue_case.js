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
    caseList: []
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function(options) {
    console.log(options)
    this.setData({
      caseList: JSON.parse(decodeURIComponent(options.list)),
    });
  },
  caseDetail(event) {
    console.log(event.currentTarget.dataset.index);
    let res = {};
    if (event.currentTarget.dataset.index == 1) {
      res = {
        data:{
          caseId: '1',
          status: '已结束',
          time: '2021-10-24 08:00:00',
          place: {
            latitude: 31.025687,
            longitude: 121.436891,
          },
          personName: '李四',
          gender: '男',
          age: '73',
          dressing: '白色外套，黑色裤子',
          accent: '上海话',
          image: 'https://pic.baike.soso.com/ugc/baikepic2/0/ori-20210515150343-1538722476_jpeg_500_563_24332.jpg/800'
        },
      };
    } else if (event.currentTarget.dataset.index == 2) {
      res = {
        data:{
          caseId: '2',
          status: '进行中',
          time: '2021-10-27 21:00:00',
          place: {
            latitude: 30.025687,
            longitude: 120.436891,
          },
          personName: '王五',
          gender: '男',
          age: '80',
          dressing: '黑色中山装',
          accent: '四川方言',
          image: 'https://pic1.zhimg.com/v2-ff88ecb3c8d65559189b989d52dfedff_720w.jpg?source=172ae18b'
        },
      };
    };
    
    // todo: GET 请求案件详情
    wx.redirectTo({
      url: '/pages/rescue_case_detail/rescue_case_detail?list=' + encodeURIComponent(JSON.stringify(res.data)),
    })
  },
})
