// pages/itemDetail/itemDetail.js
var app = getApp();
var util = require('../../utils/util.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    item: {
    },
    username: "",
    modalHidden: true,
    numberVal: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.showLoading({
      mask: true,
      title: '加载中...',
    })
    var itemid = options["itemid"];
    var that = this;
    wx.getStorage({
      key: 'cookie',
      success: function(res) {
        var cookie = res.data;
        var header = util.getCommonHeaders(cookie);
        wx.request({
          url: 'http://127.0.0.1:7001/tradeservice/api/items?itemId=' + itemid,
          header: header,
          method: 'GET',
          success: function (res) {
            if(res.statusCode !== 200){
              console.log(res)
              return;
            }
            res.data.imgUrl = res.data.imgUrl.split(" ");
            console.log(res.data)
            var itemdata = {};
            itemdata.id = itemid;
            itemdata.name = res.data.name;
            itemdata.imgs = res.data.imgUrl;
            itemdata.address = res.data.address;
            itemdata.notes = res.data.notes;
            itemdata.price = res.data.price;
            itemdata.seller = {};
            itemdata.seller.nickname = res.data.seller.nickname;
            itemdata.seller.avatar = res.data.seller.avatar;
            itemdata.seller.identifier = res.data.seller.identifier;
            that.setData({
              item: itemdata
            })
          },
          complete: ()=>{
            wx.hideLoading()
          }
        })
      },
      fail: ()=>{
        wx.hideLoading();
      }
    });
    wx.getStorage({
      key: 'username',
      success: function(res) {
        that.setData({
          username: res.data
        })
      },
    })
  },

  onNumberInput: function(e){
    this.setData({ numberVal: e.detail.value });
  },

  onModalCancel: function(e){
    this.setData({
      modalHidden: true,
      numberVal: ""
    })
  },

  toBuy: function(){
    this.setData({
      modalHidden: false
    })
  },

  onModalConfirm: function(){
    var that = this;

    wx.showLoading({
      title: '正在提交订单...',
    })
   
    wx.getStorage({
      key: 'cookie',
      success: function(res) {
        var cookie = res.data;
        var header = util.getCommonHeaders(cookie);
        wx.request({
          url: 'http://127.0.0.1:7001/tradeservice/api/orders?itemId=' + that.data.item.id,
          header: header,
          method: "POST",
          success: function(e){
            if(e.statusCode == 201){
              wx.showModal({
                title: '提交成功！',
                content: '请联系卖家协商交易',
                showCancel: false
              });
            }else{
              wx.showModal({
                title: '提交失败',
                content: e.data["message"],
                showCancel: false
              });
            }
          },
          fail: function(e){
            wx.showModal({
              title: '提交失败',
              showCancel: false
            });
          }
        })
      },
      complete: function(){
        wx.hideLoading();
        that.onModalCancel();
      }
    })
  },

  toPreview: function(e){
    var imgs = this.data.book.book_pic;
    let index = e.target.dataset.index;
    console.log(e);
    wx.previewImage({
      current: imgs[index], // 当前显示图片的http链接
      urls: imgs // 需要预览的图片http链接列表
    })
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  moreIntro: function(e){
    var more = e.target.dataset.more;
    if(more == "true"){
      this.setData({
        introduction_full: true
      })
    }else{
      this.setData({
        introduction_full: false
      })
    }
  }
})