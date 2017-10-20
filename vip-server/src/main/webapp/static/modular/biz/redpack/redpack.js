/**
 * 红包管理初始化
 */
var Redpack = {
    id: "RedpackTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Redpack.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '红包名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '红包类型', field: 'pack_type', visible: true, align: 'center', valign: 'middle'},
        {title: '发送方式', field: 'send_type', visible: true, align: 'center', valign: 'middle'},
        {title: '金额', field: 'amount', visible: true, align: 'center', valign: 'middle'},
        {title: '最小金额', field: 'min_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '最大金额', field: 'max_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '活动名称', field: 'act_name', visible: true, align: 'center', valign: 'middle'},
        {title: '祝福语', field: 'wishing', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',formatter:function(index,row,value){
    			return row.status==true ? "未删除" :"已删除"; 
    	}}
        ];
};

/**
 * 检查是否选中
 */
Redpack.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Redpack.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加红包
 */
Redpack.openAddRedpack = function () {
    var index = layer.open({
        type: 2,
        title: '添加红包',
        area: ['800px', '650px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/redpack/redpack_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看红包详情
 */
Redpack.openRedpackDetail = function () {
    if (this.check()) {
    	if(!this.seItem.status){
    		Feng.error("该红包已经删除，不能修改!");
    		return;
    	}
        var index = layer.open({
            type: 2,
            title: '红包详情',
            area: ['800px', '650px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/redpack/redpack_update/' + Redpack.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除红包
 */
Redpack.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/redpack/delete", function (data) {
            Feng.success("删除成功!");
            Redpack.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("redpackId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询红包列表
 */
Redpack.search = function () {
    var queryData = {};
    queryData['redpackName'] = $("#redpackName").val();
    queryData['packType'] = $("#packType").val();
    queryData['sendType'] = $("#sendType").val();
    Redpack.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Redpack.initColumn();
    var table = new BSTable(Redpack.id, "/redpack/list", defaultColunms);
    table.setPaginationType("server");
    Redpack.table = table.init();
});
