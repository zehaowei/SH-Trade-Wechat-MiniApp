// pages/account/account.js
var util = require('../../utils/util.js');
let app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    pub_number: 0,
    order_number: 0
  },

  toOrder: function (e) {
    wx.navigateTo({
      url: '/pages/orders/orders'
    })
  },

  toHome: function (e) {
    wx.switchTab({
      url: '/pages/mainpage/mainpage'
    })
  },

  toUs: function (e) {
    wx.showModal({
      title: '关于我们',
      content: '隗泽浩 2015302580332 毕业设计'
    })
  },

  toApp: function (e) {
    wx.showModal({
      title: '关于应用',
      content: '二手商品信息平台\r\nCopyright©2019 All Right Reserved'
    })
  },

  toExit: function (e) {
    wx.showModal({
      title: '退出',
      content: '是否确定登出？',
      success: (e) => {
        if (e.confirm) {
          app.globalData = {};
          wx.clearStorage();
          wx.reLaunch({
            url: '/pages/login/login',
          });
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.getUserInfo({
      withCredentials: false,
      success: function (res) {
        wx.getStorage({
          key: 'username',
          success: function (sto_res) {
            res.userInfo["stuID"] = sto_res.data
            that.setData({
              accountInfo: res.userInfo
            })
          },
        })
      },
      fail: function (e) {
      }
    })
    var pubnum = 0;
    var ordernum = 0;
    wx.getStorage({
      key: 'cookie',
      success: function (sto_res) {
        var cookie = sto_res.data;
        var header = util.getCommonHeaders(cookie);
        wx.request({
          url: 'https://bookin.moha.vip/client/',
          method: "GET",
          header: header,
          success: function (res) {
            if(res.statusCode === 200)
            {
              console.log(res.data)
              pubnum = res.data.book_num;
              ordernum = res.data.transaction_num;
              that.setData({
                windowHeight: wx.getSystemInfoSync().windowHeight,
                pub_number: pubnum,
                order_number: ordernum
              })
              console.log(this.data)
            }
          },
          fail: function (e) {
            console.log(e);
          },
          complete: function () {
            wx.hideLoading();
          }
        });
      },
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})