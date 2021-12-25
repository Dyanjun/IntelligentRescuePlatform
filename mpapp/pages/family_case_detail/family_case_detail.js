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
    id: ''
  },

  onLoad: function(options) {
    console.log(options)
    this.setData({
      id: options.familyId,
      caseDetail: JSON.parse(decodeURIComponent(options.list)),
      markers: [{
        latitude: JSON.parse(decodeURIComponent(options.list)).place.latitude,
        longitude: JSON.parse(decodeURIComponent(options.list)).place.longitude,
      }]
    });
  },

  showClue() {
    // let res = {
    //   data: [{
    //     clueId: '1',
    //     time: '2021-11-1 12:00:00',
    //     description: '发现相似人员踪迹',
    //     place: {
    //       latitude: 31.035687,
    //       longitude: 121.446891,
    //     }
    //   }]
    // }

    // GET 线索列表
    var that = this;
    wx.request({
      url: app.globalData.webserver + 'clue/case/' + this.data.caseDetail.caseId,
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data.data);
        wx.navigateTo({
          url: '/pages/family_case_clue/family_case_clue?caseId=' + that.data.caseDetail.caseId + '&list=' + encodeURIComponent(JSON.stringify(res.data.data)),
        })
      }
    })
  },

  callPolice() {
    wx.makePhoneCall({
      phoneNumber: '54749110',
    })
  },

  closeCase() {
    var that = this;
    wx.request({
      url: app.globalData.webserver + 'case/' + that.data.caseDetail.caseId + '/finish',
      method: 'POST',
      data: {

      },
      header: {
        'content-type': 'application/text'
      },
      success(res) {
        console.log(res);
        // GET 请求案件列表
        console.log(that.data.id);
        wx.request({
          url: app.globalData.webserver + 'case/family_member/' + that.data.id,
          header: {
            'content-type': 'application/json'
          },
          success (res) {
            console.log(res.data.data);
            if (res.data.data.length == 0) {
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
      }
    })
  },

  rescueMember() {
    wx.navigateTo({
      url: '/pages/rescue_member/rescue_member?caseId=' + this.data.caseDetail.caseId,
    })
  }
})
