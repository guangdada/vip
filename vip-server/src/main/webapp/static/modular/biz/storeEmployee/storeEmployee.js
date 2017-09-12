/**
 * 员工管理管理初始化
 */
var StoreEmployee = {
    id: "StoreEmployeeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
StoreEmployee.initColumn = function () {
    return [
    	{field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '店铺名称', field: 'storeName',visible: true, align: 'center', valign: 'middle', sortable: true},
        {title: '员工姓名', field: 'name', visible: true,align: 'center', valign: 'middle', sortable: true},
      /*  {title: '性别', field: 'sexName', visible: true,align: 'center', valign: 'middle', sortable: true},*/
        {title: '账号', field: 'mobile', visible: true,align: 'center', valign: 'middle', sortable: true},
        {title: '赋予权限', field: 'roleType',visible: true, align: 'center', valign: 'middle', sortable: true},
    ];
};

/**
 * 检查是否选中
 */
StoreEmployee.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        StoreEmployee.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加员工管理
 */
StoreEmployee.openAddStoreEmployee = function () {
    var index = layer.open({
        type: 2,
        title: '添加员工管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/storeEmployee/storeEmployee_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看员工管理详情
 */
StoreEmployee.openStoreEmployeeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '员工管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/storeEmployee/storeEmployee_update/' + StoreEmployee.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除员工管理
 */
StoreEmployee.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/storeEmployee/delete", function (data) {
	            Feng.success("删除成功!");
	            StoreEmployee.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("storeEmployeeId",StoreEmployee.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询员工管理列表
 */
StoreEmployee.search = function () {
    var queryData = {};
    queryData['employeeName'] = $("#employeeName").val();
    queryData['mobile']=$("#mobile").val();
    queryData['storeId']=$("#storeName").find("option:selected").val();
    queryData['roleId']=$("#roleType").find("option:selected").val();
    StoreEmployee.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = StoreEmployee.initColumn();
    /*var table = new BSTable("StoreEmployeeTable", "/storeEmployee/list", defaultColunms);*/
    var table = new BSTable(StoreEmployee.id, "/storeEmployee/list", defaultColunms);
    table.setPaginationType("server");
    StoreEmployee.table = table.init();
});
