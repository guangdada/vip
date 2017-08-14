/**
 * 初始化使用记录详情对话框
 */
var CouponTradeInfoDlg = {
    couponTradeInfoData : {}
};

/**
 * 清除数据
 */
CouponTradeInfoDlg.clearData = function() {
    this.couponTradeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponTradeInfoDlg.set = function(key, val) {
    this.couponTradeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponTradeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponTradeInfoDlg.close = function() {
    parent.layer.close(window.parent.CouponTrade.layerIndex);
}

/**
 * 收集数据
 */
CouponTradeInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
CouponTradeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponTrade/add", function(data){
        Feng.success("添加成功!");
        window.parent.CouponTrade.table.refresh();
        CouponTradeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponTradeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponTradeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponTrade/update", function(data){
        Feng.success("修改成功!");
        window.parent.CouponTrade.table.refresh();
        CouponTradeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponTradeInfoData);
    ajax.start();
}

$(function() {

});
