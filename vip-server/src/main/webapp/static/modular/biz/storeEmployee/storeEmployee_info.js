/**
 * 初始化员工管理详情对话框
 */
var StoreEmployeeInfoDlg = {
    storeEmployeeInfoData : {}
};

/**
 * 清除数据
 */
StoreEmployeeInfoDlg.clearData = function() {
    this.storeEmployeeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreEmployeeInfoDlg.set = function(key, val) {
    this.storeEmployeeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreEmployeeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StoreEmployeeInfoDlg.close = function() {
    parent.layer.close(window.parent.StoreEmployee.layerIndex);
}

/**
 * 收集数据
 */
StoreEmployeeInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
StoreEmployeeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storeEmployee/add", function(data){
        Feng.success("添加成功!");
        window.parent.StoreEmployee.table.refresh();
        StoreEmployeeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeEmployeeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StoreEmployeeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storeEmployee/update", function(data){
        Feng.success("修改成功!");
        window.parent.StoreEmployee.table.refresh();
        StoreEmployeeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeEmployeeInfoData);
    ajax.start();
}

$(function() {

});
