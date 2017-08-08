/**
 * 积分管理管理初始化
 */
var Point = {
    id: "PointTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Point.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '规则类型', field: 'rule_type', visible: true, align: 'center', valign: 'middle'},
        {title: '积分条件', field: 'points_limit', visible: true, align: 'center', valign: 'middle'},
        {title: '奖励分值', field: 'points', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Point.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Point.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加积分管理
 */
Point.openAddPoint = function () {
    var index = layer.open({
        type: 2,
        title: '添加积分规则',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/point/point_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看积分管理详情
 */
Point.openPointDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '积分规则详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/point/point_update/' + Point.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除积分管理
 */
Point.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/point/delete", function (data) {
            Feng.success("删除成功!");
            Point.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("pointId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询积分管理列表
 */
Point.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Point.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Point.initColumn();
    var table = new BSTable(Point.id, "/point/list", defaultColunms);
    table.setPaginationType("server");
    Point.table = table.init();
});
