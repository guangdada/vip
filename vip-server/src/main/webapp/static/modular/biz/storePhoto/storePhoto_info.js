/**
 * 初始化店铺图片详情对话框
 */
var StorePhotoInfoDlg = {
    storePhotoInfoData : {}
};

/**
 * 清除数据
 */
StorePhotoInfoDlg.clearData = function() {
    this.storePhotoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StorePhotoInfoDlg.set = function(key, val) {
    this.storePhotoInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StorePhotoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StorePhotoInfoDlg.close = function() {
    parent.layer.close(window.parent.StorePhoto.layerIndex);
}

/**
 * 收集数据
 */
StorePhotoInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
StorePhotoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storePhoto/add", function(data){
        Feng.success("添加成功!");
        window.parent.StorePhoto.table.refresh();
        StorePhotoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storePhotoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StorePhotoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storePhoto/update", function(data){
        Feng.success("修改成功!");
        window.parent.StorePhoto.table.refresh();
        StorePhotoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storePhotoInfoData);
    ajax.start();
}

$(function() {

});
