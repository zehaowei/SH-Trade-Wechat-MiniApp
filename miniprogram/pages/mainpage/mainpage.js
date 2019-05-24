//index.js
//获取应用实例
var util = require('../../utils/util.js');
var WxSearch = require('../wxSearch/wxSearch.js');
const app = getApp()

Page({
  data: {

    curPage: [0],
    curPageSta: [true],

    visited: [],

    isSearch: false,

    limit: 200,

    currentList: []
  },
  //事件处理函数

  /**
   * 用户点击书籍item转到详情页
   */
  toDetail: function (e) {
    var itemid = e.currentTarget.dataset.itemid;
    wx.navigateTo({
      url: '/pages/itemDetail/itemDetail?itemid='+itemid
    })
  },

  /**
   * 点击发布按钮转到发布页面
   */
  postBook: function (e) {
    wx.navigateTo({
      url: '../publishitems/publishitems'
    })
  },

  /**
   * 点击搜索键转到搜索页面
   */
  wxSearchFn: function (e) {
    var that = this
    WxSearch.wxSearchAddHisKey(that);
    wx.navigateTo({
      url: '../search/searchRes?searchWrd='+this.data.wxSearchData.value
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

  // changeList: function(event){
  //   var that = this;
  //   var barType = event.target.dataset.type;
  //   this.loadBooks(1, barType, list => {
  //     var curPage_ = [0, 0, 0, 0];
  //     curPage_[barType - 1] = curPage_[barType-1] + 1;
  //     that.setData({
  //       currentList: list,
  //       curPage: curPage_,
  //       curPageSta: [true, true, true, true],
  //       tabStatus: {
  //         hdindex: barType,
  //       },
  //     });
  //   })
  // },

  scrollLoad: function(e){
    console.log("addmore");
    var that = this;
    this.loadItems(that.data.curPage[0]+1, 1, list=>{
      var curlist = that.data.currentList;
      for(var i = 0; i < list.length; i++)
        curlist.push(list[i]);
      that.setData({
        currentList: curlist,
      });
    });
  },

  onLoad: function () {
    var that = this;
    //初始化的时候渲染wxSearchdata
    WxSearch.init(that, 43, ['零食', '小桌子', '操作系统', '水果', '晾衣架', '显示器', '课本']);
    WxSearch.initMindKeys(['深入理解Java虚拟机', 'Unix网络编程', '世界通史', '线性代数']);

    this.loadItems(that.data.curPage[0]+1, 1, list=>{
      var curPage_ = that.data.curPage;
      curPage_[0] = curPage_[0] + 1;
      that.setData({
        currentList: list,
        curPage: curPage_,
        windowHeight: wx.getSystemInfoSync().windowHeight
      });
    })
  },

  loadItems: function(page, category, callback){
    var that = this;
    wx.showLoading({
      title: '加载中...',
    });
    if(that.data.curPageSta[category-1])
    {
      wx.getStorage({
        key: 'cookie',
        success: function(res) {
          var cookie = res.data;
          console.log(cookie);
          var header = util.getCommonHeaders(cookie);

          wx.request({
            url: 'http://127.0.0.1:7001/tradeservice/api/items/brief?page=' + page,
            method: "GET",
            header: header,
            success: function (res) {
              if (res.statusCode == 200) {
                var list = [];
                for (var i = 0; i < res.data.length; i++) {
                  var item = {};
                  item.id = res.data[i].id;
                  item.imgUrl = res.data[i].imgUrl;
                  item.name = res.data[i].name;
                  item.price = res.data[i].price;
                  item.notes = res.data[i].notes;
                  list.push(item);
                }
                if (callback && typeof (callback) == 'function') {
                  callback(list);
                }
              }
              else {
                var cps = that.data.curPageSta;
                cps[category - 1] = false;
              }
            },
            fail: function (e) {
              console.log(e)
            },
            complete: function () {
              wx.hideLoading();
            }
          })
        },
        fail: function () {
        
        }
      })
    }
    wx.hideLoading();
  },

  getUserInfo: function(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

  onShow: function(){
    var that = this;
    wx.getClipboardData({
      success: function (res) {
        console.log(res);
        var data = res.data;
        var pattern = /BOOKIN\/book\/\d+\//
        var mt = data.match(pattern);
        console.log(mt);

        if (mt === null) return;
        var url = mt[0];
        var id = url.match(/\d+/);
        if(id === null) return;
        id = id[0];
        var visited = that.data.visited;
        if (visited.indexOf(id) !== -1){
          return;
        }

        visited.push(id);
        that.setData({
          visited: visited
        });

        wx.getStorage({
          key: 'cookie',
          success: function (res) {
            var cookie = res.data;
            var header = util.getCommonHeaders(cookie);
            wx.request({
              url: 'https://bookin.moha.vip/book/' + id + "/",
              header: header,
              success: function (res) {
                if(res.statusCode != 200) return;
                wx.showModal({
                  title: '跳转',
                  content: '检查到剪贴板有 ' + res.data.data["book_name"] + " 请问是否跳转？",
                  success: function (res) {
                    if(res.confirm)
                      wx.navigateTo({
                        url: '/pages/bookDetail/bookDetail?id=' + id,
                      });
                  }
                })
              },
            })
          },
          fail: () => {
            wx.hideLoading();
          }
        });
      }
    })
  },

   /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log("addmore");
    var that = this;
    this.loadItems(that.data.curPage[0]+1, 1, list => {
      var curlist = that.data.currentList;
      var curPage_ = that.data.curPage;
      curPage_[catelog - 1] = curPage_[catelog - 1]+1;
      for (var i = 0; i < list.length; i++)
        curlist.push(list[i]);
      that.setData({
        currentList: curlist,
        curPage: curPage_
      });
    });
  },

  /**
  * 页面相关事件处理函数--监听用户下拉动作
  */
  onPullDownRefresh: function () {
    console.log("refresh");
    var that = this;
    this.loadBooks(1, 1, list => {
      var curPage_ = that.data.curPage;
      var curSta_ = that.data.curPageSta;
      curPage_[catelog - 1] = 1;
      curSta_[catelog - 1] = true;
      that.setData({
        currentList: list,
        curPage: curPage_,
        curPageSta: curSta_
      });
    });
    wx.stopPullDownRefresh();
  }
})
