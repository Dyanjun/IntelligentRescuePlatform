// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  globalData: {
    userInfo: null,
    webserver: "http://10.166.135.191:8080/",
    rmpserver: "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/",
    amapapi: "https://restapi.amap.com/v3/weather/weatherInfo",
  }
})
