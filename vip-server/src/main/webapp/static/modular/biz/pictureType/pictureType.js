/**
 * 图片类型管理初始化
 */
var PictureType = {
    id: "PictureTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PictureType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PictureType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PictureType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加图片类型
 */
PictureType.openAddPictureType = function () {
    var index = layer.open({
        type: 2,
        title: '添加图片类型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/pictureType/pictureType_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看图片类型详情
 */
PictureType.openPictureTypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '图片类型详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/pictureType/pictureType_update/' + PictureType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除图片类型
 */
PictureType.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/pictureType/delete", function (data) {
            Feng.success("删除成功!");
            PictureType.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("pictureTypeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询图片类型列表
 */
PictureType.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PictureType.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PictureType.initColumn();
    var table = new BSTable(PictureType.id, "/pictureType/list", defaultColunms);
    table.setPaginationType("server");
    PictureType.table = table.init();
});
