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
    caseDetail: [],
    markers: [],
    caseAddress: {},
    familyId: '',
    missingPersonId: ''
  },
  // 事件处理函数

  onLoad: function(options) {
    this.setData({
      familyId: options.familyId,
      missingPersonId: options.missingPersonId
    });
  },

  mapView() {
    var that = this;
    wx.chooseLocation({
      success: function (res) {
        console.log(res);
        that.data.caseAddress = {
          latitude: res.latitude,
          longitude: res.longitude
        };
        console.log(that.data.caseAddress);
      },
    })
  },

  submitCase(event) {
    var that = this;
    console.log(event);
    const {
      time,
      emergencyLevel,
      rescueNum,
      description,
    } = event.detail.value;
    console.log(event.detail.value);
    // POST 存入数据库
    // 先POST地点
    wx.request({
      url: app.globalData.rmpserver + 'place/',
      method: 'POST',
      data: {
        longitude: that.data.caseAddress.longitude,
        latitude: that.data.caseAddress.latitude
      },
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        var resData = res.data.data;
        const placeDto = {
          id: resData.id,
          longitude: resData.longitude,
          latitude: resData.latitude
        };
        // 再GET missing_person
        var missingPersonId = that.data.missingPersonId;
        console.log(missingPersonId);
        const missingPersonDto = {
          id: missingPersonId
        };
        // 最后POST添加案件
        wx.request({
          url: app.globalData.webserver + 'case',
          method: 'POST',
          data: {
            lost_time: time,
            emergency_level: emergencyLevel,
            rescue_num: rescueNum,
            description: description,
            missingPerson: missingPersonDto,
            place: placeDto
          },
          header: {
            'content-type': 'application/json'
          },
          success (res) {
            console.log(res.data);
            // 回到family_case页面
            console.log(that.data.familyId);
            console.log(app.globalData.webserver + 'case/family_member/' + that.data.familyId)
            wx.request({
              url: app.globalData.webserver + 'case/family_member/' + that.data.familyId,
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
                  url: '/pages/family_case/family_case?family_id=' + that.data.familyId + '&list=' + encodeURIComponent(JSON.stringify(list)),
                })
              }
            });
          }
        })
      }
    })
    
    
    // wx.navigateTo({
    //   url: '/pages/family_case/family_case',
    // })
  }
})
