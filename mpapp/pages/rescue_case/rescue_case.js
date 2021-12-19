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
    rescueId: '',
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
      rescueId: options.rescueId,
      caseList: JSON.parse(decodeURIComponent(options.list)),
    });
  },
  caseDetail(event) {
    console.log(event.currentTarget.dataset.index);
    var caseId = event.currentTarget.dataset.index;
    console.log(caseId);
    var that = this;
    // let res = {};
    // if (event.currentTarget.dataset.index == 1) {
    //   res = {
    //     data:{
    //       caseId: '1',
    //       status: '已结束',
    //       time: '2021-10-24 08:00:00',
    //       place: {
    //         latitude: 31.025687,
    //         longitude: 121.436891,
    //       },
    //       personName: '李四',
    //       gender: '男',
    //       age: '73',
    //       dressing: '白色外套，黑色裤子',
    //       accent: '上海话',
    //       image: 'https://pic.baike.soso.com/ugc/baikepic2/0/ori-20210515150343-1538722476_jpeg_500_563_24332.jpg/800'
    //     },
    //   };
    // } else if (event.currentTarget.dataset.index == 2) {
    //   res = {
    //     data:{
    //       caseId: '2',
    //       status: '进行中',
    //       time: '2021-10-27 21:00:00',
    //       place: {
    //         latitude: 30.025687,
    //         longitude: 120.436891,
    //       },
    //       personName: '王五',
    //       gender: '男',
    //       age: '80',
    //       dressing: '黑色中山装',
    //       accent: '四川方言',
    //       image: 'https://pic1.zhimg.com/v2-ff88ecb3c8d65559189b989d52dfedff_720w.jpg?source=172ae18b'
    //     },
    //   };
    // };
    
    // GET 请求案件详情
    wx.request({
      url: app.globalData.rmpserver + 'case/' + caseId,
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        var resData1 = res.data.data;
        console.log(app.globalData.webserver + 'missing_person/case/' + caseId)
        // GET 请求失踪者详情
        wx.request({
          url: app.globalData.rmpserver + 'missing_person/' + resData1.missing_person_id,
          header: {
            'content-type': 'application/json'
          },
          success (res) {
            console.log(res.data.data);
            var resData2 = res.data.data;
            // GET 请求地点详情
            wx.request({
              url: app.globalData.rmpserver + 'place/' + resData1.lost_place_id,
              header: {
                'content-type': 'application/json'
              },
              success (res) {
                console.log(res.data.data);
                var resPlaceData = res.data.data;
                // GET missing_person照片
                wx.request({
                  url: app.globalData.rmpserver + 'photo/?photo.lost_person_id=' + resData1.missing_person_id,
                  header: {
                    'content-type': 'application/json'
                  },
                  success (res) {
                    console.log(res.data);
                    var url = '';
                    if (res.data.data.length != 0) {
                      url = res.data.data[0].url;
                    }
                    var caseDetail = {
                      caseId: caseId,
                      status: resData1.status,
                      time: resData1.lost_time,
                      place: {
                        latitude: resPlaceData.latitude,
                        longitude: resPlaceData.longitude
                      },
                      description: resData1.description,
                      personName: resData2.name,
                      age: resData2.age,
                      gender: resData2.gender,
                      dressing: resData2.dressing,
                      accent: resData2.accent,
                      image: url
                    };
                    wx.navigateTo({
                      url: '/pages/rescue_case_detail/rescue_case_detail?rescueId=' + that.data.rescueId + '&list=' + encodeURIComponent(JSON.stringify(caseDetail)),
                    })
                  }
                })
              }
            })
          }
        })
      }
    });
  },
})
