/**
 * 订单管理初始化
 */
var Order = {
    id: "OrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Order.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'order_no', visible: true, align: 'center', valign: 'middle'},
        {title: '会员名称', field: 'member_name', visible: true, align: 'center', valign: 'middle'},
        {title: '支付单号', field: 'pay_order_no', visible: true, align: 'center', valign: 'middle'},
        {title: '订单状态', field: 'pay_status', visible: true, align: 'center', valign: 'middle'},
        {title: '支付金额', field: 'payment', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.payment ? row.payment/100 : "";
        }},
        {title: '优惠金额', field: 'discount', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.discount ? row.discount/100 : "";
        }},
        {title: '总金额', field: 'balance_due', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.balance_due ? row.balance_due/100 : "";
        }},
        {title: '商品数量', field: 'product_num', visible: true, align: 'center', valign: 'middle'},
        {title: '订单时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Order.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Order.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单
 */
Order.openAddOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/order/order_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单详情
 */
Order.openOrderDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/order_update/' + Order.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单
 */
Order.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/order/delete", function (data) {
	            Feng.success("删除成功!");
	            Order.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("orderId",Order.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询订单列表
 */
Order.search = function () {
    var queryData = {};
    queryData['memName'] = $("#memName").val();
    queryData['storeId'] = $("#storeId").val();
    queryData['orderNo'] = $("#orderNo").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['orderSource'] = $("#orderSource").val();
    Order.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("server");
    Order.table = table.init();
});
