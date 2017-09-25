﻿/*******************************************
 * 
 * 创建人：Quber（qubernet@163.com）
 * 创建时间：2014年6月10日
 * 创建说明：Base=>页面加载（loading）效果
 * 
 * 修改人：
 * 修改时间：
 * 修改说明：
 * 
*********************************************/

//获取浏览器页面可见高度和宽度
var _PageHeight = document.documentElement.clientHeight,
    _PageWidth = document.documentElement.clientWidth;
//计算loading框距离顶部和左部的距离（loading框的宽度为215px，高度为61px）
var _LoadingTop = _PageHeight > 61 ? (_PageHeight - 61) / 2 : 0,
    _LoadingLeft = _PageWidth > 215 ? (_PageWidth - 215) / 2 : 0;
//在页面未加载完毕之前显示的loading Html自定义内容
var _LoadingHtml = '<div id="loadingDiv" style="position:absolute;left:0;width:100%;height:' + _PageHeight + 'px;top:0;background:white;opacity:1;filter:alpha(opacity=80);z-index:10000;"><div class="weui_loading" align="center" style="position:relative;margin-top:50%;"><i class="weui-loading weui-icon_toast"></i><p class="weui-toast_content">数据加载中</p></div></div>';
//呈现loading效果
document.write(_LoadingHtml);
//window.onload = function () {
//    var loadingMask = document.getElementById('loadingDiv');
//    loadingMask.parentNode.removeChild(loadingMask);
//};

//监听加载状态改变
document.onreadystatechange = completeLoading;

//加载状态为complete时移除loading效果
function completeLoading() {
    if (document.readyState == "complete") {
        var loadingMask = document.getElementById('loadingDiv');
        loadingMask.parentNode.removeChild(loadingMask);
    }
}
