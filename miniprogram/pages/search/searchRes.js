// pages/search/searchRes.js
var WxSearch = require('../wxSearch/wxSearch.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    listData:[],
    isSearch: false
  },

  /**
   * 用户点击item转到详情页
   */
  toDetail: function (e) {
    var itemid = e.currentTarget.dataset.booknum;
    wx.navigateTo({
      url: '/pages/itemDetail/itemDetail?itemid=' + itemid
    })
  },

  /**
   * 点击搜索键转到搜索页面
   */
  wxSearchFn: function (e) {
    var that = this
    WxSearch.wxSearchAddHisKey(that);
    wx.navigateTo({
      url: '../search/searchRes?searchWrd=' + this.data.wxSearchData.value
    })
  },

  wxSearchInput: function (e) {
    var that = this
    WxSearch.wxSearchInput(e, that);
  },

  wxSerchFocus: function (e) {
    var that = this
    WxSearch.wxSearchFocus(e, that);
  },

  wxSearchBlur: function (e) {
    var that = this
    WxSearch.wxSearchBlur(e, that);
  },

  wxSearchKeyTap: function (e) {
    var that = this
    WxSearch.wxSearchAddHisKey(that);
    WxSearch.wxSearchKeyTap(e, that);
  },

  wxSearchDeleteKey: function (e) {
    var that = this
    WxSearch.wxSearchDeleteKey(e, that);
  },

  wxSearchDeleteAll: function (e) {
    var that = this;
    WxSearch.wxSearchDeleteAll(that);
  },

  wxSearchTap: function (e) {
    var that = this
    WxSearch.wxSearchHiddenPancel(that);
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    console.log(options.searchWrd);
    wx.request({
      url: 'http://127.0.0.1:7001/tradeservice/api/items/seeking?page=1&searchStr='+options.searchWrd,
      method: "GET",
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        var list = [];
        for(var i = 0; i < res.data.length; i++)
        {
          var item = {};
          item.item_id = res.data[i].id;
          item.imgUrl = res.data[i].imgUrl;
          item.name = res.data[i].name;
          item.price = res.data[i].price;
          item.notes = res.data[i].notes;
          list.push(item);
        }
        that.setData({
            listData: list
          }
        );
      }
    });
    //初始化的时候渲染wxSearchdata
    WxSearch.init(that, 43, ['算法导论', '机器学习', '世界通史', '线性代数', '离散数学与应用', '大学物理', '围城']);
    WxSearch.initMindKeys(['深入理解Java虚拟机', 'Unix网络编程', '世界通史', '线性代数']);
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