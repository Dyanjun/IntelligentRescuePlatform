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
    id: '',
    username: '',
    personName: '',
    telephone: '',
    weatherInfo: ''
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
    that.setData({
      id: options.id,
      username: options.username,
      personName: options.personName,
      telephone: options.telephone,
    });
  },

  modify() {
    wx.navigateTo({
      url: '/pages/modify_info/modify_info?username=' + this.data.username + '&user=family',
    })
  },

  weather() {
    wx.navigateTo({
      url: '/pages/weather/weather',
    })
  },

  help() {
    wx.navigateTo({
      url: '/pages/info/info',
    })
  },

  case() {
    var that = this;
    // let res = {
    //   data: [
    //     {
    //       caseId: '1',
    //       personName: '李四',
    //       time: '2021-10-24 08:00:00',
    //       status: '已结束'
    //     },
    //     {
    //       caseId: '2',
    //       personName: '王五',
    //       time: '2021-10-27 21:00:00',
    //       status: '进行中'
    //     },
    //   ]
    // };
    // GET 请求案件列表
    wx.request({
      url: app.globalData.webserver + 'case/family_member/' + that.data.id,
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        if (res.data.data.length == 0) {
          console.log('test');
          wx.navigateTo({
            url: '/pages/family_case/family_case?family_id=' + that.data.id + '&list=' + encodeURIComponent(JSON.stringify([])),
          })
        }
        var resData = res.data.data;
        var list = [];
        for (var i = 0; i < resData.length; i++) {
          var item = {
            caseId: resData[i].id,
            personName: resData[i].missingPerson.name,
            time: resData[i].lost_time,
            status: resData[i].status
          };
          list.push(item);
        }
        list.sort((a, b) => a.caseId - b.caseId);
        console.log(list);
        wx.navigateTo({
          url: '/pages/family_case/family_case?family_id=' + that.data.id + '&list=' + encodeURIComponent(JSON.stringify(list)),
        })
      }
    })
  },
  
  loser() {
    // let res = {
    //   data: [
    //     {
    //       caseId: '1',
    //       name: '李四',
    //       gender: '男',
    //       age: '73',
    //       dressing: '白色外套，黑色裤子',
    //       accent: '上海话',
    //       image: 'https://pic.baike.soso.com/ugc/baikepic2/0/ori-20210515150343-1538722476_jpeg_500_563_24332.jpg/800'
    //     },
    //     {
    //       person_id: '2',
    //       name: '王五',
    //       gender: '男',
    //       age: '80',
    //       dressing: '黑色中山装',
    //       accent: '四川方言',
    //       image: 'https://pic1.zhimg.com/v2-ff88ecb3c8d65559189b989d52dfedff_720w.jpg?source=172ae18b'
    //     },
    //   ]
    // };
    // GET 请求失踪者列表
    var that = this;
    wx.navigateTo({
      url: '/pages/loser/loser?family_id=' + that.data.id,
    });
  }
})
