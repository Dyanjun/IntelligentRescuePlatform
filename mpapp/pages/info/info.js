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
    policeStationList: [],
    hospitalList: [],
    containmentStationList: []
  },
  
  onLoad: function() {
    this.setData({
      policeStationList: [
        {
          id: '1',
          name: '吴泾派出所',
          address: '上海市闵行区永德路800号',
          telephone: '+86 21 6450 5622',
          time: '全天'
        },
        {
          id: '2',
          name: '虹桥派出所',
          address: '上海市闵行区吴中路600号',
          telephone: '+86 21 6406 1006',
          time: '全天'
        },
        {
          id: '3',
          name: '闵行分局',
          address: '上海市闵行区银都路3700号',
          telephone: '+86 21 3407 4800',
          time: '工作日上午9:00-11:00 下午14:00－16:00'
        },
        {
          id: '4',
          name: '碧江路派出所',
          address: '上海市闵行区兰坪路352弄',
          telephone: '+86 21 6430 6176',
          time: '全天'
        },
      ],
      hospitalList: [
        {
          id: '1',
          name: '上海沪闵医院',
          address: '上海市闵行区建设路40号',
          telephone: '021-64355555',
          time: '全天(急诊)'
        },
        {
          id: '2',
          name: '上海交通大学校医院',
          address: '上海市闵行区东川路800号',
          telephone: '021-54742400',
          time: '工作日上午8:30~11:30 下午13:30-16:45'
        },
        {
          id: '3',
          name: '上海市第五人民医院',
          address: '上海市闵行区瑞丽路128号',
          telephone: '021-64308151',
          time: '全天(急诊)'
        },
      ],
    })
  },
})
