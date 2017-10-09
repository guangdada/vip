/**
 * 初始化分享日志详情对话框
 */
var ShareLogInfoDlg = {
    shareLogInfoData : {}
};

/**
 * 清除数据
 */
ShareLogInfoDlg.clearData = function() {
    this.shareLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShareLogInfoDlg.set = function(key, val) {
    this.shareLogInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShareLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShareLogInfoDlg.close = function() {
    parent.layer.close(window.parent.ShareLog.layerIndex);
}

/**
 * 收集数据
 */
ShareLogInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
ShareLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shareLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.ShareLog.table.refresh();
        ShareLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shareLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ShareLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shareLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.ShareLog.table.refresh();
        ShareLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shareLogInfoData);
    ajax.start();
}

$(function() {

});
