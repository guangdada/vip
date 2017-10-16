/**
 * 签到规则管理初始化
 */
var Sign = {
    id: "SignTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Sign.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '每连续领取天数', field: 'times', visible: true, align: 'center', valign: 'middle'},
        {title: '奖励积分', field: 'score', visible: true, align: 'center', valign: 'middle'},
        {title: '仅领一次', field: 'once', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.once ? "是" : "否"
        }},
        {title: '创建时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Sign.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Sign.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加签到规则
 */
Sign.openAddSign = function () {
    var index = layer.open({
        type: 2,
        title: '添加签到规则',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/sign/sign_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看签到规则详情
 */
Sign.openSignDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '签到规则详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/sign/sign_update/' + Sign.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除签到规则
 */
Sign.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/sign/delete", function (data) {
            Feng.success("删除成功!");
            Sign.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("signId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询签到规则列表
 */
Sign.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Sign.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Sign.initColumn();
    var table = new BSTable(Sign.id, "/sign/list", defaultColunms);
    table.setPaginationType("server");
    Sign.table = table.init();
});
