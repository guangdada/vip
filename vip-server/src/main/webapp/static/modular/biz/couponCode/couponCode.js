/**
 * 券码管理管理初始化
 */
var CouponCode = {
    id: "CouponCodeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    ids:null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponCode.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '批次号', field: 'batchNo', visible: true, align: 'center', valign: 'middle'},
        {title: '卡号', field: 'verifyNo', visible: true, align: 'center', valign: 'middle'},
        {title: '券码', field: 'verifyCode', visible: true, align: 'center', valign: 'middle'},
        {title: '现金券名称', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
        {title: '使用状态', field: 'useStatus', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CouponCode.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	var ids = $.map(selected, function (row) {
            return row.id;
        });
        CouponCode.ids = ids;
        return true;
    }
};

/**
 * 点击添加券码管理
 */
CouponCode.openAddCouponCode = function () {
    var index = layer.open({
        type: 2,
        title: '生成券码',
        area: ['500px', '300px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/couponCode/couponCode_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看券码管理详情
 */
CouponCode.openCouponCodeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '券码管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/couponCode/couponCode_update/' + CouponCode.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除券码管理
 */
CouponCode.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/couponCode/delete", function (data) {
	            Feng.success("删除成功!");
	            CouponCode.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("ids",CouponCode.ids.join(','));
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};


/**
 * 已制卡
 */
CouponCode.makeCard = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要修改为“已制卡”状态吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/couponCode/update", function (data) {
	            Feng.success("修改成功!");
	            CouponCode.table.refresh();
	        }, function (data) {
	            Feng.error("修改失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("ids",CouponCode.ids.join(','));
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 导出
 */
CouponCode.exportCode = function () {
    var batchNo = $("#batchNo").val();
    var verifyNo = $("#verifyNo").val();
    var verifyCode = $("#verifyCode").val();
    var useStatus = $("#useStatus").val();
    var couponId = $("#couponId").val();
    
    if(!batchNo && !verifyNo && !verifyCode && !useStatus && !couponId){
    	Feng.error("请输入导出条件!");
    	return;
    }
  //询问框
	layer.confirm('确认要导出吗？', {
	  btn: ['确认','取消'] //按钮
	}, function(index){
		location.href = Feng.ctxPath + "/couponCode/export?batchNo=" +batchNo+ "&verifyNo=" +verifyNo + "&verifyCode=" +verifyCode+ "&useStatus=" +useStatus+ "&couponId=" +couponId;
	    layer.close(index);
	}, function(){
	});
};


/**
 * 查询券码管理列表
 */
CouponCode.search = function () {
    var queryData = {};
    queryData['batchNo'] = $("#batchNo").val();
    queryData['verifyNo'] = $("#verifyNo").val();
    queryData['verifyCode'] = $("#verifyCode").val();
    queryData['useStatus'] = $("#useStatus").val();
    queryData['couponId'] = $("#couponId").val();
    CouponCode.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CouponCode.initColumn();
    var table = new BSTable(CouponCode.id, "/couponCode/list", defaultColunms);
    table.setPaginationType("server");
    CouponCode.table = table.init();
});
