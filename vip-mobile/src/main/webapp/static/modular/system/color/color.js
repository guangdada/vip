/**
 * 颜色管理初始化
 */
var Color = {
    id: "ColorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Color.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '值', field: 'value', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'create_time', align: 'center', valign: 'middle', sortable: false},
        {title: '修改时间', field: 'update_time', align: 'center', valign: 'middle', sortable: false},
    ];
};

/**
 * 检查是否选中
 */
Color.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Color.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加颜色
 */
Color.openAddColor = function () {
    var index = layer.open({
        type: 2,
        title: '添加颜色',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/color/color_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看颜色详情
 */
Color.openColorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '颜色详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/color/color_update/' + Color.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除颜色
 */
Color.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/color/delete", function (data) {
            Feng.success("删除成功!");
            Color.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("colorId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询颜色列表
 */
Color.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Color.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Color.initColumn();
    var table = new BSTable(Color.id, "/color/list", defaultColunms);
    table.setPaginationType("client");
    Color.table = table.init();
});
