/**
 * 积分明细管理初始化
 */
var PointTrade = {
    id: "PointTradeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PointTrade.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '收入方式', field: 'inOut', visible: true, align: 'center', valign: 'middle'},
        {title: '交易方式', field: 'tradeType', visible: true, align: 'center', valign: 'middle'},
        {title: '积分规则', field: 'pointName', visible: true, align: 'center', valign: 'middle'},
        {title: '交易积分', field: 'point', visible: true, align: 'center', valign: 'middle'},
        {title: '交易时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PointTrade.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PointTrade.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加积分明细
 */
PointTrade.openAddPointTrade = function () {
    var index = layer.open({
        type: 2,
        title: '添加积分明细',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/pointTrade/pointTrade_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看积分明细详情
 */
PointTrade.openPointTradeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '积分明细详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/pointTrade/pointTrade_update/' + PointTrade.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除积分明细
 */
PointTrade.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/pointTrade/delete", function (data) {
	            Feng.success("删除成功!");
	            PointTrade.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("pointTradeId",PointTrade.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询积分明细列表
 */
PointTrade.search = function () {
    var queryData = {};
    queryData['nickname'] = $("#nickname").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['inOut'] = $("#inOut").val();
    queryData['pointId'] = $("#pointId").val();
    queryData['tradeType'] = $("#tradeType").val();
    PointTrade.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PointTrade.initColumn();
    var table = new BSTable(PointTrade.id, "/pointTrade/list", defaultColunms);
    table.setPaginationType("server");
    PointTrade.table = table.init();
});
