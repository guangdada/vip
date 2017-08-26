/**
 * 初始化图片类型详情对话框
 */
var PictureTypeInfoDlg = {
    pictureTypeInfoData : {}
};

/**
 * 清除数据
 */
PictureTypeInfoDlg.clearData = function() {
    this.pictureTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PictureTypeInfoDlg.set = function(key, val) {
    this.pictureTypeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PictureTypeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PictureTypeInfoDlg.close = function() {
    parent.layer.close(window.parent.PictureType.layerIndex);
}

/**
 * 收集数据
 */
PictureTypeInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
PictureTypeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/pictureType/add", function(data){
        Feng.success("添加成功!");
        window.parent.PictureType.table.refresh();
        PictureTypeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pictureTypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PictureTypeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/pictureType/update", function(data){
        Feng.success("修改成功!");
        window.parent.PictureType.table.refresh();
        PictureTypeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pictureTypeInfoData);
    ajax.start();
}

$(function() {

});
