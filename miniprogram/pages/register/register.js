//index.js
//获取应用实例
var util = require('../../utils/util.js');  
var timer = 1;
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    password:"",
    account: "",
    vcode: "",
    vcodeBtnDisabled: false,
    getVcodeText: "验证",
    sendmsg: "sendmsg"
  },
  //事件处理函数
  toLogin: function() {
    wx.redirectTo({
      url: '../login/login'
    })
  },
  onLoad: function () {
    var that = this;
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
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
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  accountInput: function(e) {
    this.setData({
      account: e.detail.value,
    })
  },
  phonenumInput: function (e) {
    this.setData({
      phonenum: e.detail.value,
    })
  },
  vcodeInput: function (e) {
    this.setData({
      vcode: e.detail.value,
    })
  },

  formSubmit: function (e) {
    var account = e.detail.value.account
    var password = e.detail.value.password;
    var that = this;
    console.log(this.data);
    // 判断账号是否为空和判断该账号名是否被注册  
    if ("" == util.trim(password)) {
      util.isError("密码不能为空", that);
      return;
    } else {
      util.clearError(that);
    }

    if ("" == util.trim(account)) {
      util.isError("账号不能为空", that);
      return;
    } else {
      util.clearError(that);
    }

    console.log(util.hexMD5(password))
      wx.showLoading({
        title: '注册中，请稍后',
      });
      wx.request({
        url: "http://127.0.0.1:7000/userservice/api/users",
      data: {
        "identityType": "username",
        "identifier": account,
        "credential": util.hexMD5(password),
      },
      method: 'post',
      header: { 'Content-Type': 'application/json' },
      success: res => {
        if (res.statusCode == 201) {
          wx.setStorage({
            key: 'username',
            data: account,
          });

          wx.request({
            url: 'http://127.0.0.1:7000/userservice/api/users',
            data: {
              "id": res.data.userId,
              "nickname": that.data.userInfo.nickName,
              "avatar": that.data.userInfo.avatarUrl
            },
            method: 'PUT',
            header: { 'Content-Type': 'application/json' },
            success: res => {
              wx.showToast({
                title: '注册成功！',
                complete: () => {
                  wx.reLaunch({
                    url: '../login/login',
                  });
                }
              });
            },
            fail: (e) => {
              console.log(e);
              return;
            }
          });
        } else {
          console.log(res);
          util.isError(res.data.message, that);
        } 
      },
      fail: (e) => {
        console.log(e);
        util.isError(e.data.message, that);
        return;
      },
      complete: function(){
        wx.hideLoading();
      }
    })
  },

  sendMessage: function (e) {
    var phonenum = this.data.phonenum;
    // 判断手机号是否为空
    var that = this; 
    if ("" == util.trim(phonenum) || util.trim(phonenum).length != 11) {
      util.isError("手机号输入错误", that);
      return;
    } else {
      util.clearError(that);
    }
    if (timer == 1) {
      timer = 0
      var that = this
      var time = 59
      that.setData({
        sendmsg: "sendmsgafter",
      })
      that.setData({
        vcodeBtnDisabled: true,
      })
      var inter = setInterval(function () {
        that.setData({
          getVcodeText: time + "s",
        })
        time--
        if (time < 0) {
          timer = 1
          clearInterval(inter)
          that.setData({
            sendmsg: "sendmsg",
            getVcodeText: "验证",
            vcodeBtnDisabled: false,
          })
          
        }
      }, 1000)
    }

    wx.request({
      url: util.getRoot() + '/client/phone_valid/?username=' + this.data.account + "&phone_num=" + this.data.phonenum,
      method: 'get',
      header: { 'Content-Type': 'application/json' },
      success: res => { },
      fail: (e) => {
        console.log(e);
      }
    })
  },
})
