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
    loserList: [],
    family_id: '',
    photoUrl: '',
    photoId: ''
  },
  
  onLoad: function(options) {
    this.setData({
      family_id: options.family_id
    })
  },

  chooseImage() {
    var that = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        console.log(res);
        that.uploadImage(res.tempFilePaths[0])
      }
    });
  },

  // 上传到云开发的存储中
  uploadImage(fileURL) {
    wx.cloud.init();
    wx.cloud.uploadFile({
      cloudPath:new Date().getTime()+'.png', // 上传至云端的路径
      filePath: fileURL, // 小程序临时文件路径
      success: res => {
        console.log(res);
        //获取图片的http路径
        this.addImagePath(res.fileID)  // res.fileID 是上传图片的 fileID
      },
      fail: console.error
    })
  },

  // 获取图片上传后的https的url路径地址  参数是上传图片的 fileId
  addImagePath(fileId) {
    var that = this;
    console.log(fileId);
    wx.cloud.getTempFileURL({
      fileList: [fileId],
      success: res => {
        console.log(res);
        console.log("获取url地址：",res.fileList[0].tempFileURL);
        that.data.photoUrl = res.fileList[0].tempFileURL;
      },
      fail: console.error
    })
  },

  submitLoserInfo(event) {
    console.log(this.data.family_id);
    var that = this;
    console.log(event.detail.value);
    const {
      personName,
      gender,
      age,
      dressing,
      accent
    } = event.detail.value;
    // todo: POST 存入数据库
    wx.request({
      url: app.globalData.webserver + 'missing_person/',
      method: 'POST',
      data: {
        name: personName,
        gender: gender,
        age: age,
        dressing: dressing,
        accent: accent,
        family_member_id: that.data.family_id,
        photo: {
          url: that.data.photoUrl
        }
      },
      header: {
        'content-type': 'application/json'
      },
      success (res) {
        console.log(res.data);
        wx.request({
          url: app.globalData.webserver + 'missing_person/family_member/' + that.data.family_id,
          header: {
            'content-type': 'application/json'
          },
          success (res) {
            console.log(res.data);
            wx.navigateTo({
              url: '/pages/loser/loser?family_id=' + that.data.family_id + '&list=' + encodeURIComponent(JSON.stringify(res.data.data)),
            });
          }
        });
      }
    })
  }
})
