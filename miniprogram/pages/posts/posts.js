// pages/posts/posts.js
var util = require('../../utils/util.js');


Page({

  /**
   * 页面的初始数据
   */
  data: {
    navbar: ['全部', '销售中', '已下架'],
    itemType: ['all', 'onsale', 'offshelf'],
    currentTab: 0,
    listData: []
  },

  navbarTap: function (e) {
    let id = e.currentTarget.dataset.idx;
    this.setData({
      currentTab: id
    });
    this.refreshItems(this.data.itemType[id]);
  },
  /**
   * 用户点击item转到详情页
   */
  toDetail: function (e) {
    var itemid = e.currentTarget.dataset.itemid;
    wx.navigateTo({
      url: `/pages/itemDetail/itemDetail?itemid=${itemid}`
    })
  },

  onLoad: function (options) {
    this.refreshItems(this.data.itemType[0]);
  },

  refreshItems: function(status) {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    wx.getStorage({
      key: 'cookie',
      success: function (res) {
        var cookie = res.data;
        let header = util.getCommonHeaders(cookie);
        wx.request({
          url: `http://127.0.0.1:7001/tradeservice/api/items/mypost?status=${status}&page=1`,
          method: "GET",
          header: header,
          success: function (res) {
            if(res.statusCode == 200){
              var list = res.data;
                that.setData({
                  listData: list
                });
            }else{
              that.setData({
                listData: []
              })
            }
          },
          complete: () => {
            wx.hideLoading();
          }
        })
      },
      fail: (e) => {
        console.log(e);
        wx.hideLoading()
      }
    })
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

  soldOut: function (res) {
    let that = this;
    let item_id = res.target.dataset.itemid;
    wx.showModal({
      title: '下架',
      content: '确定要下架该商品吗？',
      success: (e) => {
        if (e.cancel) return;
        wx.showLoading({
          title: '下架中，请稍后...',
        })
        wx.getStorage({
          key: 'cookie',
          success: function(res) {
            let cookie = res.data;
            let header = util.getCommonHeaders(cookie);
            let url = 'http://127.0.0.1:7001/tradeservice/api/items?itemId='+item_id;
            wx.request({
              url: url,
              method: "DELETE",
              header: header,
              success: (res) => {
                if(res.statusCode == 204){
                  wx.showToast({
                    title: '下架成功！',
                  });
                  that.refreshItems(that.data.itemType[that.data.currentTab]);
                }else{
                  wx.showToast({
                    title: '下架失败！',
                  });
                  console.log(res);
                }
              },
              fail: res => {
                wx.showToast({
                  icon: "none",
                  title: '请求失败',
                });
                console.log(res);
              },
              complete: res=>{
                wx.hideLoading();
              }
            })
          },
          fail: (res) => {
            wx.hideLoading();
            wx.showToast({
              icon: "none",
              title: '加载缓存失败',
            })
          }
        })
      }
    })
  },
})