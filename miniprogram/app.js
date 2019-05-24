//app.js
const io = require('/utils/weapp.socket.io.js')

function saveUser(user) {
  wx.setStorage({
    key: "accountInfo",
    data: user
  });
}

function loadUser(that) {
  wx.getStorage({
    key: "accountInfo",
    success: function (res) {
      that.globalData.accountInfo = res.data;
      that.getAccount(null)
    }
  })
}

function getCookie(c_name, cookie) {
  if (cookie.length > 0) {
    var c_start = cookie.indexOf(c_name + "=")
    if (c_start != -1) {
      c_start = c_start + c_name.length + 1
      var c_end = cookie.indexOf(";", c_start)
      if (c_end == -1) c_end = cookie.length
      return unescape(cookie.substring(c_start, c_end))
    }
  }
  return ""
}


App({
  onLaunch: function () {
    var that = this;
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    });

    wx.getStorage({
      key: 'cookie',
      success: function(res) {
        wx.getStorage({
          key: 'username',
          success: function (res) {
            // that.startWebSocket(res.data);
          },
          fail: function (res) {
            wx.showModal({
              title: '登录过期',
              content: '请先登录或者注册',
              success: () => {
                wx.redirectTo({
                  url: '/pages/login/login',
                })
              },
              showCancel: false
            })
          }
        })
      },
      fail: (res)=>{
        wx.showModal({
          title: '登录过期',
          content: '请先登录或者注册',
          success: () => {
            wx.redirectTo({
              url: '/pages/login/login',
            })
          },
          showCancel: false
        })
      }
    })

    
  },




  /**
   * ----------------------------------------------------------------------
   * 注册订阅者
   */
  // registerMessage: function (to, func) {
  //   this.globalData.noticeList[to] = func;
  // },

  /**
   * 注销订阅者
   */
  // unregisterMessage: function (to) {
  //   this.globalData.noticeList[to] = null;
  // },

  /**
   * 启动连接
   */
  // startWebSocket: function (id) {
  //   this.selfID = id;
  //   var socketOpen = false;
  //   var that = this;
  //   var url = "https://bookin.moha.vip/";

  //   const socket = io.connect(url);
  //   this.socket = socket;

  //   socket.on("connect", function(){
  //     console.log(233333);
  //     socket.emit("add user", {username: id});
  //   });

  //   // 先获取历史记录，以免反复写入造成性能下降
  //   wx.getStorage({
  //     key: 'history',
  //     success: function (res) {
  //       if (res.data) {
  //         that.globalData.history = res.data;
  //       } else {
  //         that.globalData.history = {};
  //       }
  //     }
  //   })

  //   // 收到消息
  //   socket.on("message", function (msg) {
  //     console.log(msg);
  //     var destination = msg["from"];
  //     var isnew = false;
  //     //写入历史记录
  //     if (that.globalData.history[destination] === null || that.globalData.history[destination] === undefined) {
  //       that.globalData.history[destination] = {
  //         "user": {
  //           "id": destination,
  //           "nickname": null,
  //           "avatar": null,
  //         },
  //         "new": true,
  //         "message": []
  //       };
  //       isnew = true;
  //     }

  //     that.globalData.history[destination]["message"].push({
  //       "time": new Date(msg["time"]),
  //       "text": msg["text"],
  //       "type": msg["type"],
  //       "obj": true
  //     });
  //     that.globalData.history[destination]["new"] = true;


  //     function notify() {
  //       // 先通知订阅者
  //       var receiver = that.globalData.noticeList[destination];
  //       if (receiver != null) {
  //         receiver(msg);
  //       }

  //       // 再通知全局订阅者
  //       if (that.globalData.globalMessageReceiver != null) {
  //         that.globalData.globalMessageReceiver(msg);
  //       }
  //     }
  //     if (isnew) {
  //       that.refreshUserInfo(notify);
  //     } else {
  //       notify();
  //     }

  //   });

  //   // 写入本地储存worker， 这里不用多线程是因为微信的多线程要求过高，且不能共享全局变量
  //   setInterval(function () {
  //     wx.setStorage({
  //       key: "history",
  //       data: that.globalData.history
  //     })
  //   }, 500);

  //   // 更新用户信息worker，1min一次
  //   setInterval(function () {
  //     that.refreshUserInfo();
  //   }, 60000);

  //   // 新消息提醒器
  //   setInterval(function(){
  //     that.newMsgDetector();
  //   }, 300)
  // },

  // sendTo: function (destination, msg, tp) {
  //   if (this.globalData.history[destination] === null || this.globalData.history[destination] === undefined) {
  //     this.globalData.history[destination] = {
  //       "user": {
  //         "id": destination,
  //         "nickname": null,
  //         "avatar": null,
  //       },
  //       "new": false,
  //       "message": []
  //     };
  //   }

  //   this.socket.emit("message", {
  //     target: destination,
  //     message: msg,
  //     "type": tp
  //   });

  //   this.globalData.history[destination]["new"] = false;
  //   //写入历史记录
  //   this.globalData.history[destination]["message"].push({
  //     "time": new Date(),
  //     "text": msg,
  //     "type": tp,
  //     "obj": false
  //   });
  // },

  /**
   * 刷新用户信息
   */
  // refreshUserInfo: function (callback) {
  //   var that = this;
  //   var ids = Object.keys(this.globalData.history);

  //   wx.getStorage({
  //     key: 'cookie',
  //     success: function (res) {
  //       var cookie = res.data;
  //       var header = {
  //         "Content-Type": "application/json",
  //         'Cookie': "sessionid=" + getCookie("sessionid", cookie) + ";csrftoken=" + getCookie("csrftoken", cookie),
  //         "X-CSRFToken": getCookie("csrftoken", cookie)
  //       };
  //       wx.request({
  //         url: 'https://bookin.moha.vip/client/chat_list_profile/',
  //         header: header,
  //         data: {
  //           "usernames": ids
  //         },
  //         method: "POST",
  //         success: function (res) {
  //           if (res.statusCode == 200) {
  //             for (var i in res.data["data"]) {
  //               var id = res.data["data"][i]["user"];
  //               that.globalData.history[id]["user"] = res.data["data"][i];
  //               if (callback && typeof (callback) == 'function') {
  //                 callback();
  //               }
  //             }
  //           }
  //           else {
  //             console.log(res)
  //           }
  //         }
  //       })
  //     },
  //   })
  // },

  // createDefaultUser: function (id) {
  //   var user = this.globalData.history[id];
  //   if (user === null || user === undefined) {
  //     var d = {
  //       "user": {
  //         "id": id,
  //         "nickname": null,
  //         "avatar": null,
  //       },
  //       "new": false,
  //       "message": []
  //     };
  //     this.globalData.history[id] = d;
  //   }
  //   // refreshUserInfo(cb);
  // },

  // newMsgDetector: function(){
  //   for (var username in this.globalData.history){
  //     if (this.globalData.history[username]["new"]){
  //       wx.setTabBarBadge({
  //         index: 1,
  //         text: 'new',
  //       });
  //       return;
  //     }
  //   }
  //   wx.removeTabBarBadge({
  //     index: 1,
  //   })
  // },

  /**
   * 注册全局消息订阅者
   * func(msg)
   */
  // registerGlobalMessageReceiver: function (func) {
  //   this.globalData.globalMessageReceiver = func;
  // },

  /**
   * 注销全局消息订阅者
   */
  // unregisterGlobalMessageReceiver: function () {
  //   this.globalData.globalMessageReceiver = null;
  // },

  globalData: {
    wxUserInfo: null,
    accountInfo: null,
    userInfo: null,
    selfID: null,
    noticeList: {},
    history: {},
    globalMessageReceiver: null,
  }
})