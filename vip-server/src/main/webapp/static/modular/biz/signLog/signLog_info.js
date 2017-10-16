/**
 * 初始化签到记录详情对话框
 */
var SignLogInfoDlg = {
    signLogInfoData : {}
};

/**
 * 清除数据
 */
SignLogInfoDlg.clearData = function() {
    this.signLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SignLogInfoDlg.set = function(key, val) {
    this.signLogInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SignLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SignLogInfoDlg.close = function() {
    parent.layer.close(window.parent.SignLog.layerIndex);
}

/**
 * 收集数据
 */
SignLogInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
SignLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/signLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.SignLog.table.refresh();
        SignLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SignLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/signLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.SignLog.table.refresh();
        SignLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signLogInfoData);
    ajax.start();
}

$(function() {

});
