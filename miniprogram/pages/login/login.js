//index.js
//获取应用实例
var util = require('../../utils/util.js');
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  
  toRegister: function () {
    wx.redirectTo({
      url: '../register/register'
    })
  },

  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo;
          console.log(this.data);
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          console.log(res);
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  formSubmit: function (e) {
    var that = this;
    // form 表单取值，格式 e.detail.value.name(name为input中自定义name值) ；使用条件：需通过<form bindsubmit="formSubmit">与<button formType="submit">一起使用  
    var account = e.detail.value.account;
    var password = e.detail.value.password;
    var phonenum = e.detail.value.phonenum;
    var that = this;
    console.log(this.data);
    // 判断账号是否为空和判断该账号名是否被注册  
    if ("" == util.trim(account)) {
      util.isError("账号不能为空", that);
      return;
    } else {
      util.clearError(that);
    }
    // 判断密码是否为空  
    if ("" == util.trim(password)) {
      util.isError("密码不能为空", that);
      return;
    } else {
      util.clearError(that);
    }
    wx.showLoading({
      title: '登录中，请稍后...',
    })
    wx.request({
      url: "http://127.0.0.1:7000/userservice/api/token",
      data: {
        "identityType": "username",
        "identifier": account,
        "credential": util.hexMD5(password)
      },
      method: 'POST',
      header: { 'Content-Type': 'application/json' },
      success: res => {
        console.log(res);
        if (res.statusCode == 201) {
          wx.setStorage({
            key: 'userId',
            data: res.data.userId,
          });
          wx.setStorage({
            key: 'username',
            data: account,
          });
          wx.setStorage({
            key: 'cookie',
            data: res.data.token,
          })
          wx.reLaunch({
            url: '../mainpage/mainpage',
          });
        } else{
          wx.showToast({
            icon: "none",
            title: res.data.message,
          })
        }
      },
      fail: () => {
        var that = this;
        console.log(this.data);
      },
      complete: () => {
        wx.hideLoading();
      }
    })
  }
})
