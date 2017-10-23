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
        {title: '微信头像', field: 'headimgurl', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	var img = '<img src="'+row.headimgurl+'" width="50px" height="50px"/>';
        	return img;
        }},
        {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '微信id', field: 'open_id', visible: true, align: 'center', valign: 'middle'},
        {title: '微信昵称', field: 'nickname', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
        {title: '会员卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle'},
        {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle',
        	formatter:function(value,row,index){
        		return	row.birthday==null?"":row.birthday.substring(0,10);
        	}
        },
        {title: '积分', field: 'points', visible: true, align: 'center', valign: 'middle'},
        {title: '交易金额', field: 'trade_amount', visible: true, align: 'center', valign: 'middle'},
        {title: '交易时间', field: 'last_trade_time', visible: true, align: 'center', valign: 'middle'},
        /*{title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},*/
        {title: '是否激活', field: 'isActive', visible: true, align: 'center', valign: 'middle'}
        
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
        area: ['800px', '500px'], //宽高
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
            area: ['800px', '500px'], //宽高
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
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
	        var ajax = new $ax(Feng.ctxPath + "/member/delete", function (data) {
	            Feng.success("删除成功!");
	            Member.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("memberId",Member.seItem.id);
	        ajax.start();
	        layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 查询会员列表
 */
Member.search = function () {
    var queryData = {};
    queryData['memName'] = $.trim($("#memName").val());
    queryData['openId'] =  $.trim($("#openId").val());
    queryData['memMobile']= $.trim($("#memMobile").val());
    queryData['memSex']=$("#memSex").find("option:selected").val();
    queryData['cardId']=$("#cardId").find("option:selected").val();
    queryData['cardNumber']= $.trim($("#cardNumber").val());
    queryData['memNickName']= $.trim($('#memNickName').val());
    queryData['isActive']=$("#isActive").find("option:selected").val();
    Member.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTable(Member.id, "/member/list", defaultColunms);
    table.setPaginationType("server");
    Member.table = table.init();
});
