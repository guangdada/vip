/**
 * 门店管理初始化
 */
var Store = {
    id: "StoreTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Store.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '门店名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'service_phone', visible: true, align: 'center', valign: 'middle'},
        {title: '营业时间', field: 'open_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Store.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Store.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加门店
 */
Store.openAddStore = function () {
    /*var index = layer.open({
        type: 2,
        title: '添加门店',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/store/store_add'
    });
    this.layerIndex = index;*/
	location.href=Feng.ctxPath + '/store/store_add';
};

/**
 * 打开查看门店详情
 */
Store.openStoreDetail = function () {
    if (this.check()) {
        /*var index = layer.open({
            type: 2,
            title: '门店详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/store/store_update/' + Store.seItem.id
        });
        this.layerIndex = index;*/
    	location.href = Feng.ctxPath + '/store/store_update/' + Store.seItem.id;
    }
};

/**
 * 删除门店
 */
Store.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/store/delete", function (data) {
	            Feng.success("删除成功!");
	            Store.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("storeId",Store.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询门店列表
 */
Store.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Store.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Store.initColumn();
    var table = new BSTable(Store.id, "/store/list", defaultColunms);
    table.setPaginationType("server");
    Store.table = table.init();
});
