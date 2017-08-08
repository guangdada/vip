/**
 * 优惠券管理管理初始化
 */
var Coupon = {
    id: "CouponTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Coupon.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '优惠券名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '价值', field: 'value', visible: true, align: 'center', valign: 'middle'},
        {title: '领取限制', field: 'quota', visible: true, align: 'center', valign: 'middle'},
        {title: '库存', field: 'stock', visible: true, align: 'center', valign: 'middle'},
        {title: '生效时间', field: 'start_at', visible: true, align: 'center', valign: 'middle'},
        {title: '失效时间', field: 'end_at', visible: true, align: 'center', valign: 'middle'},
        {title: '领取次数', field: 'get_count', visible: true, align: 'center', valign: 'middle'},
        {title: '领取人数', field: 'get_count_user', visible: true, align: 'center', valign: 'middle'},
        {title: '已使用', field: 'use_count', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Coupon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Coupon.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加优惠券管理
 */
Coupon.openAddCoupon = function () {
    /*var index = layer.open({
        type: 2,
        title: '添加优惠券管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/coupon/coupon_add'
    });
    this.layerIndex = index;
    layer.full(index);*/
	location.href= Feng.ctxPath + '/coupon/coupon_add';
};

/**
 * 打开查看优惠券管理详情
 */
Coupon.openCouponDetail = function () {
    if (this.check()) {
        /*var index = layer.open({
            type: 2,
            title: '优惠券详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/coupon_update/' + Coupon.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);*/
    	location.href= Feng.ctxPath + '/coupon/coupon_update/' + Coupon.seItem.id;
    }
};

/**
 * 删除优惠券管理
 */
Coupon.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/coupon/delete", function (data) {
            Feng.success("删除成功!");
            Coupon.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询优惠券管理列表
 */
Coupon.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Coupon.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Coupon.initColumn();
    var table = new BSTable(Coupon.id, "/coupon/list", defaultColunms);
    table.setPaginationType("server");
    Coupon.table = table.init();
});
