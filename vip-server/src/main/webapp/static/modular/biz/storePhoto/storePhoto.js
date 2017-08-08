/**
 * 店铺图片管理初始化
 */
var StorePhoto = {
    id: "StorePhotoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StorePhoto.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
StorePhoto.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StorePhoto.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加店铺图片
 */
StorePhoto.openAddStorePhoto = function () {
    var index = layer.open({
        type: 2,
        title: '添加店铺图片',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/storePhoto/storePhoto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看店铺图片详情
 */
StorePhoto.openStorePhotoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '店铺图片详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/storePhoto/storePhoto_update/' + StorePhoto.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除店铺图片
 */
StorePhoto.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/storePhoto/delete", function (data) {
            Feng.success("删除成功!");
            StorePhoto.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("storePhotoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询店铺图片列表
 */
StorePhoto.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    StorePhoto.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = StorePhoto.initColumn();
    var table = new BSTable(StorePhoto.id, "/storePhoto/list", defaultColunms);
    table.setPaginationType("client");
    StorePhoto.table = table.init();
});
