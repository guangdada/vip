/**
 * 会员卡管理管理初始化
 */
var Card = {
    id: "CardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Card.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '会员卡名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '领取方式', field: 'grant_type', visible: true, align: 'center', valign: 'middle'},
        {title: '等级', field: 'card_level', visible: true, align: 'center', valign: 'middle'},
        {title: '使用须知', field: 'description', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '修改时间', field: 'update_time', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Card.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Card.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员卡管理
 */
Card.openAddCard = function () {
    /*var index = layer.open({
        type: 2,
        title: '添加会员卡',
        area: ['800px','440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/card/card_add'
    });
    this.layerIndex = index;
    layer.full(index);*/
    location.href =Feng.ctxPath + '/card/card_add';
};

/**
 * 点击添加会员卡管理
 */
/*Card.toAddCard = function () {
	location.href = Feng.ctxPath + '/card/card_add.html';
};*/

/**
 * 打开查看会员卡管理详情
 */
Card.openCardDetail = function () {
    if (this.check()) {
        /*var index = layer.open({
            type: 2,
            title: '会员卡管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/card/card_update/' + Card.seItem.id
        });
        this.layerIndex = index;*/
        location.href = Feng.ctxPath + '/card/card_update/' + Card.seItem.id;
    }
};

/**
 * 删除会员卡管理
 */
Card.delete = function () {
    if (this.check()) {
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/card/delete", function (data) {
	            Feng.success("删除成功!");
	            Card.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("cardId",Card.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询会员卡管理列表
 */
Card.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Card.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Card.initColumn();
    var table = new BSTable(Card.id, "/card/list", defaultColunms);
    table.setPaginationType("server");
    Card.table = table.init();
});
