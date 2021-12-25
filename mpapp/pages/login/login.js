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
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName'), // 如需尝试获取用户信息可改为false,
    id: '',
    username: '',
    password: '',
    radio: '',
    personName: '',
    telephone: '',
    code: '',
  },
 
  onLoad() {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },
  
  // debug
  test() {
    var that = this;
    wx.requestSubscribeMessage({
      tmplIds: ['R1S1v3UnJasjrAQCzlLmexLRAAX8sWtvO_fJmYDx-qQ'],
      success: function(res) {
        console.log(res);
        if (that.data.radio == 'family') {
          wx.navigateTo({
            url: '/pages/family/family?id=' + that.data.id + '&username=' + that.data.username + '&personName=' + that.data.personName  + '&telephone=' + that.data.telephone,
          });
        } else {
          console.log('/pages/rescue/rescue?id=' + that.data.id + '&username=' + that.data.username + '&personName=' + that.data.personName  + '&telephone=' + that.data.telephone);
          wx.navigateTo({
            url: '/pages/rescue/rescue?id=' + that.data.id + '&username=' + that.data.username + '&personName=' + that.data.personName  + '&telephone=' + that.data.telephone,
          });
        }
      },
      fail: function(err) {
        console.log(err)
      },
    });
  },

  submitLogin(event) {
    var that = this;
    console.log(event);
    this.data.username = event.detail.value.username;
    this.data.password = event.detail.value.password;
    this.data.radio = event.detail.value.radio;
    // POST 请求后端验证 并返回数据
    var realUrl = ''
    if (this.data.radio == 'family') {
      realUrl = app.globalData.webserver + 'user/login/family_member';
    } else {
      realUrl = app.globalData.webserver + 'user/login/rescue_member';
    }

    wx.login({
      success: function(res) {
        console.log(res);
        that.code = res.code;
        console.log(that.code);
        wx.request({  
          url: realUrl + '?code=' + that.data.code + '&username=' + that.data.username + '&role=' + that.data.radio,  
          method:'GET',  
          header: {  
            'content-type': 'application/json'  
          },  
          success: function (res) {  
            console.log(res.data);
            var id = res.data.data;
            if (that.data.radio == 'family') {
              wx.request({
                url: app.globalData.rmpserver + 'family_member?family_member.id=' + id,  
                header: {  
                  'content-type': 'application/json'  
                },  
                success: function (res) {
                  console.log(res.data);
                  that.data.id = res.data.data[0].id;
                  that.data.personName = res.data.data[0].name;
                  that.data.telephone = res.data.data[0].telephone;
                }
              });
            } else {
              wx.request({  
                url: app.globalData.rmpserver + 'rescue_member' + '?rescue_member.id=' + id,  
                header: {  
                  'content-type': 'application/json'  
                },  
                success: function (res) {  
                  console.log(res.data);
                  that.data.id = res.data.data[0].id;
                  that.data.personName = res.data.data[0].name;
                  that.data.telephone = res.data.data[0].telephone;
                }  
              });
            }
          },
          fail: function (err) {
            console.log(err)
          }
        });
      },
    });
  },
  redirectRegister() {
    wx.navigateTo({
      url: '/pages/register/register'
    })
  }
})
