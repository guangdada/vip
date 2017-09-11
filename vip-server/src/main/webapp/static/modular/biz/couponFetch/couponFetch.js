/**
 * 领取记录管理初始化
 */
var CouponFetch = {
    id: "CouponFetchTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponFetch.initColumn = function () {
    return [
        /*{field: 'selectItem', radio: true},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '头像', field: 'headImg', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	var img = '<img src="'+row.headImg+'" width="50px" height="50px"/>';
        	return img;
        }},
        {title: '优惠券名称', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
        {title: '券类型', field: 'couponType', visible: true, align: 'center', valign: 'middle'},
        {title: '会员名称', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '领取时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'isUsed', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CouponFetch.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CouponFetch.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加领取记录
 */
CouponFetch.openAddCouponFetch = function () {
    var index = layer.open({
        type: 2,
        title: '添加领取记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/couponFetch/couponFetch_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看领取记录详情
 */
CouponFetch.openCouponFetchDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '领取记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/couponFetch/couponFetch_update/' + CouponFetch.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除领取记录
 */
CouponFetch.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/couponFetch/delete", function (data) {
            Feng.success("删除成功!");
            CouponFetch.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("couponFetchId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询领取记录列表
 */
CouponFetch.search = function () {
    var queryData = {};
    queryData['couponName'] = $("#couponName").val();
    queryData['type'] = $("#type").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['isUsed'] = $("#isUsed").val();
    queryData['nickname'] = $("#nickname").val();
    CouponFetch.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CouponFetch.initColumn();
    var table = new BSTable(CouponFetch.id, "/couponFetch/list", defaultColunms);
    table.setPaginationType("server");
    CouponFetch.table = table.init();
});
