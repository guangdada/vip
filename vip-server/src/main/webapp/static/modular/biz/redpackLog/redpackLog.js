/**
 * 红包记录管理初始化
 */
var RedpackLog = {
    id: "RedpackLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
RedpackLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '微信ID', field: 'openid', visible: true, align: 'center', valign: 'middle'},
        {title: '红包名称', field: 'redpackName', visible: true, align: 'center', valign: 'middle'},
        {title: '发送金额', field: 'send_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '交易号', field: 'billno', visible: true, align: 'center', valign: 'middle'},
        {title: '红包状态', field: 'sendStatus', visible: true, align: 'center', valign: 'middle'},
        {title: '发送结果', field: 'reason', visible: true, align: 'center', valign: 'middle'},
        {title: '发送时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '退款时间', field: 'refund_time', visible: true, align: 'center', valign: 'middle'},
        {title: '领取时间', field: 'rcv_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
RedpackLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        RedpackLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加红包记录
 */
RedpackLog.openAddRedpackLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加红包记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/redpackLog/redpackLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看红包记录详情
 */
RedpackLog.openRedpackLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '红包记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/redpackLog/redpackLog_update/' + RedpackLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除红包记录
 */
RedpackLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/redpackLog/delete", function (data) {
            Feng.success("删除成功!");
            RedpackLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("redpackLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询红包记录列表
 */
RedpackLog.search = function () {
    var queryData = {};
    queryData['billno'] = $("#billno").val();
    queryData['openid'] = $("#openid").val();
    queryData['redpackId'] = $("#redpackId").val();
    queryData['sendStatus'] = $("#sendStatus").val();
    queryData['sendS'] = $("#sendS").val();
    queryData['sendE'] = $("#sendE").val();
    RedpackLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = RedpackLog.initColumn();
    var table = new BSTable(RedpackLog.id, "/redpackLog/list", defaultColunms);
    table.setPaginationType("server");
    RedpackLog.table = table.init();
});
