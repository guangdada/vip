/**
 * 领取记录管理初始化
 */
var MemberCard = {
    id: "MemberCardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberCard.initColumn = function () {
    return [
        /*{field: 'selectItem', radio: true},*/
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '头像', field: 'headImg', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	var img = '<img src="'+row.headimgurl+'" width="50px" height="50px"/>';
        	return img;
        }},
        {title: '领取时间', field: 'create_time', visible: true, align: 'center', valign: 'middle'},
        {title: '会员卡号', field: 'card_number', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '会员卡', field: 'cardName', visible: true, align: 'center', valign: 'middle'},
        {title: '类型', field: 'grantType', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'state', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加领取记录
 */
MemberCard.openAddMemberCard = function () {
    var index = layer.open({
        type: 2,
        title: '添加领取记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberCard/memberCard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看领取记录详情
 */
MemberCard.openMemberCardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '领取记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberCard/memberCard_update/' + MemberCard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除领取记录
 */
MemberCard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberCard/delete", function (data) {
            Feng.success("删除成功!");
            MemberCard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberCardId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询领取记录列表
 */
MemberCard.search = function () {
    var queryData = {};
    queryData['cardNumber'] = $("#cardNumber").val();
    queryData['mobile'] = $("#mobile").val();
    queryData['state'] = $("#state").val();
    queryData['grantType'] = $("#grantType").val();
    queryData['cardId'] = $("#cardId").val();
    queryData['nickname'] = $("#nickname").val();
    MemberCard.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = MemberCard.initColumn();
    var table = new BSTable(MemberCard.id, "/memberCard/list", defaultColunms);
    table.setPaginationType("server");
    MemberCard.table = table.init();
});
