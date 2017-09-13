/**
 * 使用记录管理初始化
 */
var CouponTrade = {
    id: "CouponTradeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponTrade.initColumn = function () {
	return [
        /*{field: 'selectItem', radio: true},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '头像', field: 'headImg', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	var img = '<img src="'+row.headImg+'" width="50px" height="50px"/>';
        	return img;
        }},
        {title: '优惠券名称', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
        {title: '券码', field: 'verifyCode', visible: true, align: 'center', valign: 'middle'},
        {title: '券类型', field: 'couponType', visible: true, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '会员名称', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '使用金额', field: 'usedValue', visible: true, align: 'center', valign: 'middle'},
        {title: '订单号', field: 'usedOrderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '使用时间', field: 'usedTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CouponTrade.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CouponTrade.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加使用记录
 */
CouponTrade.openAddCouponTrade = function () {
    var index = layer.open({
        type: 2,
        title: '添加使用记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/couponTrade/couponTrade_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看使用记录详情
 */
CouponTrade.openCouponTradeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '使用记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/couponTrade/couponTrade_update/' + CouponTrade.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除使用记录
 */
CouponTrade.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/couponTrade/delete", function (data) {
            Feng.success("删除成功!");
            CouponTrade.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponTradeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询使用记录列表
 */
CouponTrade.search = function () {
    var queryData = {};
    queryData['couponName'] = $("#couponName").val();
    queryData['type'] = $("#type").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['nickname'] = $("#nickname").val();
    queryData['verifyCode'] = $("#verifyCode").val();
    CouponTrade.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CouponTrade.initColumn();
    var table = new BSTable(CouponTrade.id, "/couponTrade/list", defaultColunms);
    table.setPaginationType("server");
    CouponTrade.table = table.init();
});
