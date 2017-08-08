/**
 * 初始化门店详情对话框
 */
var StoreInfoDlg = {
    storeInfoData : {}
};

/**
 * 清除数据
 */
StoreInfoDlg.clearData = function() {
    this.storeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreInfoDlg.set = function(key, val) {
    this.storeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StoreInfoDlg.close = function() {
    parent.layer.close(window.parent.Store.layerIndex);
}

/**
 * 收集数据
 */
StoreInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
StoreInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/store/add", function(data){
        Feng.success("添加成功!");
        window.parent.Store.table.refresh();
        StoreInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StoreInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/store/update", function(data){
        Feng.success("修改成功!");
        window.parent.Store.table.refresh();
        StoreInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeInfoData);
    ajax.start();
}

$(function() {

});
