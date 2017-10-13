/**
 * 分享日志管理初始化
 */
var ShareLog = {
    id: "ShareLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ShareLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '邀请人id', field: 'shareOpenid', visible: true, align: 'center', valign: 'middle'},
        {title: '邀请人', field: 'shareName', visible: true, align: 'center', valign: 'middle'},
        {title: '受邀人id', field: 'receiveOpenid', visible: true, align: 'center', valign: 'middle'},
        {title: '受邀人', field: 'receiveName', visible: true, align: 'center', valign: 'middle'},
        {title: '邀请状态', field: 'receiveStatus', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.receiveStatus == true ? "邀请成功" : "邀请中";
        }},
        {title: '邀请时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '受邀时间', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ShareLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ShareLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加分享日志
 */
ShareLog.openAddShareLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加分享日志',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/shareLog/shareLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看分享日志详情
 */
ShareLog.openShareLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '分享日志详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/shareLog/shareLog_update/' + ShareLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除分享日志
 */
ShareLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/shareLog/delete", function (data) {
            Feng.success("删除成功!");
            ShareLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("shareLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询分享日志列表
 */
ShareLog.search = function () {
    var queryData = {};
    queryData['shareName'] = $("#shareName").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['receiveName'] = $("#receiveName").val();
    queryData['receiveStatus'] = $("#receiveStatus").val();
    ShareLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ShareLog.initColumn();
    var table = new BSTable(ShareLog.id, "/shareLog/list", defaultColunms);
    table.setPaginationType("server");
    ShareLog.table = table.init();
});
