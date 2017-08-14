/**
 * 初始化领取记录详情对话框
 */
var CouponFetchInfoDlg = {
    couponFetchInfoData : {}
};

/**
 * 清除数据
 */
CouponFetchInfoDlg.clearData = function() {
    this.couponFetchInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponFetchInfoDlg.set = function(key, val) {
    this.couponFetchInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponFetchInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponFetchInfoDlg.close = function() {
    parent.layer.close(window.parent.CouponFetch.layerIndex);
}

/**
 * 收集数据
 */
CouponFetchInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
CouponFetchInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponFetch/add", function(data){
        Feng.success("添加成功!");
        window.parent.CouponFetch.table.refresh();
        CouponFetchInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponFetchInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponFetchInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponFetch/update", function(data){
        Feng.success("修改成功!");
        window.parent.CouponFetch.table.refresh();
        CouponFetchInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponFetchInfoData);
    ajax.start();
}

$(function() {

});
