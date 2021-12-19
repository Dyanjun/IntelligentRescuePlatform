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
    markers: [],
    caseAddress: {},
    rescueId: '',
    caseId: '',
    photoUrl: '',
    photoId: ''
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function(options) {
    this.setData({
      rescueId: options.rescueId,
      caseId: options.caseId
    })
  },

  mapView() {
    var that = this;
    wx.chooseLocation({
      success: function (res) {
        console.log(res);
        that.data.caseAddress = {
          address: res.address,
          name: res.name,
          latitude: res.latitude,
          longitude: res.longitude
        };
        console.log(that.data.caseAddress);
      },
    })
  },

  submitClue(event) {
    var that = this;
    console.log(event);
    const {
      description, 
      time
    } = event.detail.value;
    console.log(description, time, this.data.caseAddress);
    // POST 存入数据库
    wx.request({
      url: app.globalData.rmpserver + 'place/',
      method: 'POST',
      data: {
        longitude: that.data.caseAddress.longitude,
        latitude: that.data.caseAddress.latitude,
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
        // POST添加线索
        console.log(time, description, that.data.rescueId, that.data.caseId, placeDto.id)
        wx.request({
          url: app.globalData.rmpserver + 'clue',
          method: 'POST',
          data: {
            time: time,
            description: description,
            rescue_member_id: parseInt(that.data.rescueId),
            case_id: parseInt(that.data.caseId),
            place_id: parseInt(placeDto.id)
          },
          header: {
            'content-type': 'application/json'
          },
          success (res) {
            console.log(res.data);
            wx.request({
              // POST photo
              url: app.globalData.rmpserver + 'photo',
              method: 'POST',
              header: {
                'content-type': 'application/json'
              },
              data: {
                url: that.data.photoUrl,
                clue_id: res.data.data.id
              },
              success(res) {
                console.log(res.data);
                // GET 回到rescue_case_clue页面
                wx.request({
                  url: app.globalData.webserver + 'clue/case/' + that.data.caseId,
                  header: {
                    'content-type': 'application/json'
                  },
                  success (res) {
                    console.log(res.data.data);
                    var clueList = res.data.data;
                    wx.navigateTo({
                      url: '/pages/rescue_proceeding_clue/rescue_proceeding_clue?rescueId=' + that.data.rescueId + '&caseId=' + that.data.caseId + '&list=' + encodeURIComponent(JSON.stringify(clueList)),
                    });
                    // var photoList = [];
                    // for (var i = 0; i < clueList.length; i ++) {
                    //   var clueId = clueList[i].id;
                    //   console.log(app.globalData.rmpserver + 'photo/?photo.clue_id=' + clueId.toString());
                    //   // GET 每个线索的图片
                    //   wx.request({
                    //     url: app.globalData.rmpserver + 'photo/?photo.clue_id=' + clueId.toString(),
                    //     header: {
                    //       'content-type': 'application/json'
                    //     },
                    //     success(res) {
                    //       console.log(res.data);
                    //       photoList.push(res.data.data[0].url);
                    //       if (i == clueList.length - 1) {
                    //         console.log(clueList);
                    //         console.log(photoList);
                    //         wx.navigateTo({
                    //           url: '/pages/rescue_proceeding_clue/rescue_proceeding_clue?rescueId=' + that.data.rescueId + '&caseId=' + that.data.caseId + '&list=' + encodeURIComponent(JSON.stringify(clueList)) + '&photoList=' + encodeURIComponent(JSON.stringify(photoList)),
                    //         })
                    //       }
                    //     }
                    //   })
                    // }
                  }
                });
              }
            })
          }
        })
      }
    });
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
})
