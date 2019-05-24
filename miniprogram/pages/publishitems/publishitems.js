//index.js
//获取应用实例
var util = require('../../utils/util.js');
const qiniuUploader = require("../../utils/qiniuUploader");
const app = getApp()

Page({
  data: {
    imageUrls: []
  },

  bindTypeChange: function (e) {

    this.setData({
      typeIndex: e.detail.value
    })
  },

  chooseImage: function(e){
    var that = this;
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        var tempFilePath = res.tempFilePaths[0];
        wx.showLoading({
          title: '正在上传中',
          mask: true
        })
        wx.request({
          url: 'http://127.0.0.1:7002/cloudservice/api/qiniutoken',
          method: 'GET',
          success: res => {
            if (res.statusCode == 200) {
              var uploadToken = res.data;
              if (uploadToken != '') {
                qiniuUploader.upload(tempFilePath, (res) => {
                  console.log(res.imageURL);
                  let list = that.data.imageUrls;
                  list.push("http://"+res.imageURL)
                  that.setData({
                    'imageUrls': list,
                  });
                  wx.hideLoading()
                }, (error) => {
                  console.log('error: ' + error);
                }, {
                    region: 'ECN',
                    domain: 'pp4bmuzed.bkt.clouddn.com',
                    uptoken: uploadToken,
                    shouldUseQiniuFileName: true
                  }, (res) => {
                    console.log(res)
                  });
              } else {
                wx.hideLoading();
                util.isError("上传失败", that);
              }
            } else {
              wx.hideLoading();
              console.log(res);
              util.isError("获取token失败", that);
            }
          }
        })
      }
    })
  },

  bindDepartmentChange: function (e) {

    this.setData({
      departmentIndex: e.detail.value
    })
  },
  formSubmit: function (e) {
    // form 表单取值，格式 e.detail.value.name(name为input中自定义name值) ；使用条件：需通过<form bindsubmit="formSubmit">与<button formType="submit">一起使用  
    var bookName = e.detail.value.bookName;
    var price = e.detail.value.price;
    var address = e.detail.value.address;
    var note = e.detail.value.note;
    var that = this;
 
    if ("" == util.trim(bookName)) {
      wx.showToast({
        icon: "none",
        title: '商品名不能为空',
      })
      return;
    } else {
      util.clearError(that);
    }

    if ("" == util.trim(price)) {
      wx.showToast({
        icon: "none",
        title: '价格不能为空',
      })
      return;
    } else {
      util.clearError(that);
    }

    if ("" == util.trim(address)) {
      wx.showToast({
        icon: "none",
        title: '地址不能为空',
      })
      return;
    } else {
      util.clearError(that);
    }
    wx.showLoading({
      title: '正在提交',
    })
    wx.getStorage({
      key: 'cookie',
      success: function (res) {
        var cookie = res.data;
        console.log(cookie);
        var header = util.getCommonHeaders(cookie);
        wx.request({
          url: 'http://127.0.0.1:7001/tradeservice/api/items',
          data: {
            "name": bookName,
            "price": parseFloat(price) + 0.0,
            "address": address,
            "notes": note,
            "imgUrl": that.data.imageUrls.join(" "),
          },
          method: 'POST',
          header: header,
          success: res => {
            if(res.statusCode != 201){
              wx.showToast({
                icon: "none",
                title: '出错啦',
              })
              return;
            }
            var item_id = res.data.id;
            wx.redirectTo({
              url: '/pages/itemDetail/itemDetail?itemid=' + item_id,
            })
          },
          fail: () => {
            wx.showToast({
              icon: "none",
              title: '操作失败请重试',
            })
            return;
          },
          complete: function(){
            wx.hideLoading();
          }
        })
      },
      fail: function(){
        wx.showToast({
          icon: "none",
          title: '操作失败请重试',
        })
        wx.hideLoading();
      }
    })
  },

  toDelete: function(e){
    let that = this;
    let id = e.target.dataset.index;
    let urls = this.data.imageUrls;
    wx.showModal({
      title: '删除',
      content: '确定要删除这个图片吗？',
      success: (res)=>{
        if(res.cancel) return;
        let newUrl = [];
        for(let i in urls){
          if(i == id) continue;
          newUrl.push(urls[i]);
        }
        that.setData({
          imageUrls: newUrl
        })
      }
    })
  }
})