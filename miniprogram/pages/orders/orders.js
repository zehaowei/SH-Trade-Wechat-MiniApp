// pages/orders/orders.js
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    navbar: ['全部', '交易中', '已完成', '已取消'],
    orderType: ['all', 'ontrade', 'finish', 'cancel'],
    currentTab: 0,
    orders: []
  },


  navbarTap: function (e) {
    let id = e.currentTarget.dataset.idx;
    this.setData({
      currentTab: id
    })
    this.refreshOrders(this.data.orderType[id]);
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options){
    let that = this;
    wx.getStorage({
      key: 'username',
      success: function(res) {
        that.setData({
          username: res.data
        })
      },
    });

    this.refreshOrders(this.data.orderType[0]);
  },

  refreshOrders: function (status) {
    var that = this;
    wx.showLoading({
      title: '加载中...',
    })
    wx.getStorage({
      key: 'cookie',
      success: function(res) {
        var cookie = res.data;
        var header = util.getCommonHeaders(cookie);
        wx.request({
          url: `http://127.0.0.1:7001/tradeservice/api/orders?status=${status}`,
          method: 'GET',
          header: header,
          success: function(res){
            console.log(res.data)
            if(res.statusCode === 200){
              that.setData({
                orders: res.data
              });
            }else{
              that.setData({
                orders: []
              });
            }
            
          },
          complete: ()=>{
            wx.hideLoading();
          }
        })
      },
      fail: (e)=>{
        console.log(e)
        wx.hideLoading()
      }
    })
    wx.getStorage({
      key: 'username',
      success: function (res) {
        that.setData({
          username: res.data
        })
      },
    })
  },
  
  toItemDetail: function(event){
    let id = event.currentTarget.dataset["itemid"];
    wx.navigateTo({
      url: '/pages/itemDetail/itemDetail?itemid=' + id,
    })
  },

  manipulateOrder: function(id, alert, status, successText){
    var that = this;
    var url = 'http://127.0.0.1:7001/tradeservice/api/orders?orderId='+id+'&status='+status;
    this.confirmAlert(alert, function () {
      wx.showLoading({
        title: '提交中...',
      })
      wx.getStorage({
        key: 'cookie',
        success: function (res) {
          var cookie = res.data;
          var header = util.getCommonHeaders(cookie);
          wx.request({
            url: url,
            header: header,
            method: 'PUT',
            success: res => {
              if (res.statusCode === 201) {
                wx.showToast({
                  title: successText,
                });
                that.refreshOrders(that.data.orderType[that.data.currentTab]);
              } else {
                wx.showToast({
                  icon: "none",
                  title: res.data["message"],
                })
              }
            },
            complete: () => {
              wx.hideLoading();
            }
          })
        },
        fail: () => {
          wx.hideLoading();
        }
      })
    })
  },

  toCancelOrder: function (e) {
    let id = e.currentTarget.dataset["order"];
    this.manipulateOrder(id, "取消订单", "cancel", "取消成功！");
  },

  toFinishOrder: function (e) {
    let id = e.currentTarget.dataset["order"];
    this.manipulateOrder(id, "完成订单", "finish", "完成订单！");
  },

  confirmAlert: function (action, func) {
    wx.showModal({
      title: '提示',
      content: '确定要' + action + "吗？请仔细检查，避免财产损失",
      success: (res) => {
        if (res.cancel) return;
        func();
      }
    })
  }
})