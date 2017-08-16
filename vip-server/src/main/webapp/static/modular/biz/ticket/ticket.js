/**
 * 小票管理初始化
 */
var Ticket = {
    id: "TicketTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Ticket.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        ];
};

/**
 * 检查是否选中
 */
Ticket.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Ticket.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加小票
 */
Ticket.openAddTicket = function () {
    var index = layer.open({
        type: 2,
        title: '添加小票',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ticket/ticket_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看小票详情
 */
Ticket.openTicketDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '小票详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ticket/ticket_update/' + Ticket.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除小票
 */
Ticket.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ticket/delete", function (data) {
            Feng.success("删除成功!");
            Ticket.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ticketId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询小票列表
 */
Ticket.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Ticket.table.refresh({query: queryData});
};

$(function () {
	$("#error1").css('display','none'); 
	$("#error2").css('display','none'); 
	 $("#title").keyup(function(){
			var title = $("#title").val();
		    $("#ptitle").text($("#title").val());
		    var title_len = $("#title").val().length;
		    if(title){
				$("#error1").css('display','none'); 
			}
		    if(!title){
				$("#error1").css('display','block'); 
			}
		    if(title_len<0 || title_len>10){
		    	$("#error1 p").text("头部标题长度必须在1到10之间！");
		    	$("#error1").css('display','block'); 
		    }
		  });
	 $("#remark").keyup(function(){
			var remark = $("#remark").val();
		    $("#premark").text($("#remark").val());
		    var remark_len = $("#remark").val().length;
		    if(!remark){
				$("#error2").css('display','block');
			}
		    if(remark){
				$("#error2").css('display','none');
			}
		    if(remark_len<0 || remark_len>20){
		    	$("#error2 p").text("底部备注长度必须在1到20之间");
		    	$("#error2").css('display','block'); 
		    }
		  });
	 $('input:radio[name="specType"]').change( function() {
		 var specType = $("input[name='specType']:checked").val();
			if(specType==2){
			$("#ticketWidth").removeClass("small");
			}
			if(specType==1){
			$("#ticketWidth").addClass("small");
			}
	 });
	/*$('#ensure').click(function(){
		 alert("cc");
		 TicketInfoDlg.editSubmit();
	 });*/
    /*var defaultColunms = Ticket.initColumn();
    var table = new BSTable(Ticket.id, "/ticket/list", defaultColunms);
    table.setPaginationType("server");
    Ticket.table = table.init();*/
});
