var md5 = require('md5.js')  

function hexMD5(password){
    return md5.hexMD5(password);
}

function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

var rootDocment = 'https://bookin.moha.vip';
// var rootDocment = 'http://127.0.0.1:8000';
function getRoot(){
  return rootDocment;
}
function req(url, data, cb) {
  wx.request({
    url: rootDocment + url,
    data: data,
    method: 'post',
    header: { 'Content-Type': 'application/x-www-form-urlencoded' },
    success: function (res) {
      return typeof cb == "function" && cb(res.data)
    },
    fail: function () {
      return typeof cb == "function" && cb(false)
    }
  })
}

function getReq(url, data, cb) {
  wx.request({
    url: rootDocment + url,
    data: data,
    method: 'get',
    header: { 'Content-Type': 'application/x-www-form-urlencoded' },
    success: function (res) {
      return typeof cb == "function" && cb(res.data)
    },
    fail: function () {
      return typeof cb == "function" && cb(false)
    }
  })
}

// 去前后空格  
function trim(str) {
  return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 提示错误信息  
function isError(msg, that) {
  wx.showToast({
    title: msg,
    image: '../../image/toast/warning.png',
    duration: 1000
  })
}

// 清空错误信息  
function clearError(that) {
  that.setData({
    showTopTips: false,
    errorMsg: ""
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

function getCommonHeaders(cookie){
  var header = {
    "Content-Type": "application/json",
    'Cookie': cookie
  };
  return header;
}

module.exports = {
  formatTime: formatTime,
  req: req,
  trim: trim,
  isError: isError,
  clearError: clearError,
  getReq: getReq,
  getRoot: getRoot,
  getCookie: getCookie,
  getCommonHeaders: getCommonHeaders,
  hexMD5: hexMD5
}  

// const formatTime = date => {
//   const year = date.getFullYear()
//   const month = date.getMonth() + 1
//   const day = date.getDate()
//   const hour = date.getHours()
//   const minute = date.getMinutes()
//   const second = date.getSeconds()

//   return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
// }

// const formatNumber = n => {
//   n = n.toString()
//   return n[1] ? n : '0' + n
// }

// module.exports = {
//   formatTime: formatTime
// }
