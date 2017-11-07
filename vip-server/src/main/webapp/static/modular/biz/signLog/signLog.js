/**
 * 签到记录管理初始化
 */
var SignLog = {
    id: "SignLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SignLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '微信id', field: 'unionid', visible: true, align: 'center', valign: 'middle'},
        {title: '会员名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '签到时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SignLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SignLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加签到记录
 */
SignLog.openAddSignLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加签到记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/signLog/signLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看签到记录详情
 */
SignLog.openSignLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '签到记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/signLog/signLog_update/' + SignLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除签到记录
 */
SignLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/signLog/delete", function (data) {
            Feng.success("删除成功!");
            SignLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("signLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询签到记录列表
 */
SignLog.search = function () {
    var queryData = {};
    queryData['uname'] = $("#uname").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['nickname'] = $("#nickname").val();
    queryData['signS'] = $("#signS").val();
    queryData['signE'] = $("#signE").val();
    SignLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SignLog.initColumn();
    var table = new BSTable(SignLog.id, "/signLog/list", defaultColunms);
    table.setPaginationType("server");
    SignLog.table = table.init();
});
