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
    rescueId: '',
    username: '',
    personName: '',
    telephone: '',
    longitude: '',
    latitude: '',
    markers: [],
    weatherInfo: ''
  },
  getMyLocation: function(){
    var that=this;
    wx.getLocation({
      type: 'wgs84',
      success (res) {
        console.log(res);
        console.log("1",that.data.latitude);
        that.setData({
          latitude:res.latitude,
          longitude:res.longitude,
          markers: [{
            latitude: res.latitude,
            longitude: res.longitude
          }],
        });
        wx.request({
          url: app.globalData.webserver + 'user/rescue_member/place/' + that.data.rescueId,
          method: 'PUT',
          data: {
            latitude: res.latitude,
            longitude: res.longitude,
          }
        })
        console.log("2",that.data.latitude);
      }
     })
  },

  onLoad: function(options) {
    var that = this;
    console.log(options);
    wx.request({
      url: app.globalData.amapapi,
      data: {
        'key': 'a6cf299a7582006f4a6d758627d55af9',
        'city': '310000'
      },
      header:{
        'content-type': 'application/json'
      },
      success:function(res){
        console.log(res.data);
        var weatherArray = res.data.lives[0];
        that.setData({
          rescueId: options.id,
          username: options.username,
          personName: options.personName,
          telephone: options.telephone,
          weatherInfo: weatherArray
        });
      }
    })
    var a = setInterval(this.getMyLocation, 1000); //循环时间 这里是1秒
  },

  modify() {
    wx.navigateTo({
      url: '/pages/modify_info/modify_info?username=' + this.data.username + '&user=rescue',
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

  proceeding() {
    var that = this;
    // GET 请求案件详情
    wx.request({
      url: app.globalData.webserver + 'case/rescue_member/' + this.data.rescueId + '/proceeding',
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        if (res.data.data == null) {
          wx.navigateTo({
            url: '/pages/rescue_proceeding/rescue_proceeding?rescueId=' + that.data.rescueId + '&list=' + encodeURIComponent(JSON.stringify([])),
          })
        }
        var resData = res.data.data;
        wx.request({
          url: app.globalData.rmpserver + 'photo/?photo.lost_person_id=' + resData.missingPerson.id,
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
              caseId: resData.id,
              status: resData.status,
              time: resData.lost_time,
              place: {
                latitude: resData.place.latitude,
                longitude: resData.place.longitude
              },
              description: resData.description,
              personName: resData.missingPerson.name,
              age: resData.missingPerson.age,
              gender: resData.missingPerson.gender,
              dressing: resData.missingPerson.dressing,
              accent: resData.missingPerson.accent,
              image: url
            };
            wx.navigateTo({
              url: '/pages/rescue_proceeding/rescue_proceeding?rescueId=' + that.data.rescueId + '&list=' + encodeURIComponent(JSON.stringify(caseDetail)),
            })
          }
        })
      }
    })
  },

  case() {
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

    var that = this;
    wx.request({
      url: app.globalData.webserver + 'case/rescue_member/' + that.data.rescueId + '/all',
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        if (res.data.data.length == 0) {
          wx.navigateTo({
            url: '/pages/rescue_case/rescue_case?rescueId=' + that.data.rescueId + '&list=' + encodeURIComponent(JSON.stringify([])),
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
          url: '/pages/rescue_case/rescue_case?rescueId=' + that.data.rescueId + '&list=' + encodeURIComponent(JSON.stringify(list)),
        })
      }
    })
  }
})
