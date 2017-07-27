/**
 * 初始化颜色详情对话框
 */
var ColorInfoDlg = {
    colorInfoData : {}
};

/**
 * 清除数据
 */
ColorInfoDlg.clearData = function() {
    this.colorInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ColorInfoDlg.set = function(key, val) {
    this.colorInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ColorInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ColorInfoDlg.close = function() {
    parent.layer.close(window.parent.Color.layerIndex);
}

/**
 * 收集数据
 */
ColorInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
ColorInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/color/add", function(data){
        Feng.success("添加成功!");
        window.parent.Color.table.refresh();
        ColorInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.colorInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ColorInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/color/update", function(data){
        Feng.success("修改成功!");
        window.parent.Color.table.refresh();
        ColorInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.colorInfoData);
    ajax.start();
}

$(function() {

});
