/**
 * 初始化红包记录详情对话框
 */
var RedpackLogInfoDlg = {
    redpackLogInfoData : {}
};

/**
 * 清除数据
 */
RedpackLogInfoDlg.clearData = function() {
    this.redpackLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RedpackLogInfoDlg.set = function(key, val) {
    this.redpackLogInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RedpackLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RedpackLogInfoDlg.close = function() {
    parent.layer.close(window.parent.RedpackLog.layerIndex);
}

/**
 * 收集数据
 */
RedpackLogInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
RedpackLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/redpackLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.RedpackLog.table.refresh();
        RedpackLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.redpackLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
RedpackLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/redpackLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.RedpackLog.table.refresh();
        RedpackLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.redpackLogInfoData);
    ajax.start();
}

$(function() {

});
