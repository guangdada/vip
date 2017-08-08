/**
 * 公众号管理管理初始化
 */
var WxConfig = {
    id: "WxConfigTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WxConfig.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: 'appid', field: 'appid', visible: true, align: 'center', valign: 'middle'},
        {title: '商户号', field: 'mch_id', visible: true, align: 'center', valign: 'middle'},
        {title: 'EncodingAESKey', field: 'EncodingAESKey', visible: true, align: 'center', valign: 'middle'},
        {title: 'apiKey', field: 'api_key', visible: true, align: 'center', valign: 'middle'},
        {title: 'secret', field: 'secret', visible: true, align: 'center', valign: 'middle'},
        {title: 'wechatTicket', field: 'wechat_ticket', visible: true, align: 'center', valign: 'middle'},
        {title: 'accessToken', field: 'access_token', visible: true, align: 'center', valign: 'middle'},
        {title: 'createTime', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: 'updateTime', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WxConfig.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WxConfig.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加公众号管理
 */
WxConfig.openAddWxConfig = function () {
    var index = layer.open({
        type: 2,
        title: '添加公众号管理',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/wxConfig/wxConfig_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看公众号管理详情
 */
WxConfig.openWxConfigDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '公众号管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/wxConfig/wxConfig_update/' + WxConfig.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除公众号管理
 */
WxConfig.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/wxConfig/delete", function (data) {
            Feng.success("删除成功!");
            WxConfig.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("wxConfigId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询公众号管理列表
 */
WxConfig.search = function () {
    var queryData = {};
    queryData['appid'] = $("#appid").val();
    WxConfig.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WxConfig.initColumn();
    var table = new BSTable(WxConfig.id, "/wxConfig/list", defaultColunms);
    table.setPaginationType("server");
    WxConfig.table = table.init();
});
