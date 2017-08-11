/**
 * 会员管理初始化
 */
var Member = {
    id: "MemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Member.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle'},
        {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
        {title: '微信号', field: 'wx_code', visible: true, align: 'center', valign: 'middle'},
        {title: '积分', field: 'points', visible: true, align: 'center', valign: 'middle'},
        {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Member.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Member.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加会员
 */
Member.openAddMember = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/member/member_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看会员详情
 */
Member.openMemberDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '会员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/member_update/' + Member.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除会员
 */
Member.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/member/delete", function (data) {
            Feng.success("删除成功!");
            Member.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询会员列表
 */
Member.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTable(Member.id, "/member/list", defaultColunms);
    table.setPaginationType("server");
    Member.table = table.init();
});