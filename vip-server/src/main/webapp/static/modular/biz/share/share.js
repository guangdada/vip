/**
 * 邀请规则管理初始化
 */
var Share = {
    id: "ShareTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Share.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '赠送积分', field: 'point', visible: true, align: 'center', valign: 'middle'},
        {title: '赠送优惠券', field: 'couponName', visible: true, align: 'center', valign: 'middle'},
        {title: '每月最多邀请人数', field: 'share_count', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Share.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Share.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加邀请规则
 */
Share.openAddShare = function () {
    var index = layer.open({
        type: 2,
        title: '添加邀请规则',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/share/share_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看邀请规则详情
 */
Share.openShareDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '邀请规则详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/share/share_update/' + Share.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除邀请规则
 */
Share.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/share/delete", function (data) {
            Feng.success("删除成功!");
            Share.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("shareId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询邀请规则列表
 */
Share.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Share.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Share.initColumn();
    var table = new BSTable(Share.id, "/share/list", defaultColunms);
    table.setPaginationType("server");
    Share.table = table.init();
});
