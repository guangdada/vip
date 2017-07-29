/**
 * 商户管理初始化
 */
var Merchant = {
    id: "MerchantTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Merchant.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '商户名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: 'QQ', field: 'qq', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Merchant.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Merchant.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加商户
 */
Merchant.openAddMerchant = function () {
    var index = layer.open({
        type: 2,
        title: '添加商户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/merchant/merchant_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看商户详情
 */
Merchant.openMerchantDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '商户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/merchant/merchant_update/' + Merchant.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除商户
 */
Merchant.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/merchant/delete", function (data) {
            Feng.success("删除成功!");
            Merchant.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("merchantId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Merchant.formParams = function() {
    var queryData = {};
    queryData['merName'] = $("#merName").val();
    return queryData;
}

/**
 * 查询商户列表
 */
Merchant.search = function () {
    Merchant.table.refresh({query: Merchant.formParams()});
};

$(function () {
    var defaultColunms = Merchant.initColumn();
    var table = new BSTable(Merchant.id, "/merchant/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Merchant.formParams());
    Merchant.table = table.init();
});
