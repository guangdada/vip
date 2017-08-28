/**
 * 订单明细管理初始化
 */
var OrderItem = {
    id: "OrderItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderItem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
OrderItem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OrderItem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单明细
 */
OrderItem.openAddOrderItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单明细',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderItem/orderItem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单明细详情
 */
OrderItem.openOrderItemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单明细详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderItem/orderItem_update/' + OrderItem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单明细
 */
OrderItem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/orderItem/delete", function (data) {
            Feng.success("删除成功!");
            OrderItem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderItemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询订单明细列表
 */
OrderItem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    OrderItem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = OrderItem.initColumn();
    var table = new BSTable(OrderItem.id, "/orderItem/list", defaultColunms);
    table.setPaginationType("server");
    OrderItem.table = table.init();
});
