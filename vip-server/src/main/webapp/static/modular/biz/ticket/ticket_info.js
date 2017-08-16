/**
 * 初始化小票详情对话框
 */
var TicketInfoDlg = {
    ticketInfoData : {},
   /* validateFields : {
		title : {
			validators : {
				notEmpty : {
					message : '头部标题不能为空'
				},
			}
		},
		remark : {
			validators : {
				notEmpty : {
					message : '手机号码不能为空'
				}
			}
		}
	}*/
};

/**
 * 清除数据
 */
TicketInfoDlg.clearData = function() {
    this.ticketInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TicketInfoDlg.set = function(key, value) {
    this.ticketInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TicketInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TicketInfoDlg.close = function() {
    parent.layer.close(window.parent.Ticket.layerIndex);
}

/**
 * 收集数据
 */
TicketInfoDlg.collectData = function() {
	var specType = $("input[name='specType']:checked").val();
    this.set('id').set('specType',specType).set('title').set('remark');
}

/**
 * 提交添加
 */
TicketInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticket/add", function(data){
        Feng.success("添加成功!");
        window.parent.Ticket.table.refresh();
        TicketInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ticketInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TicketInfoDlg.editSubmit = function() {
	var title = $("#title").val();
	var remark = $("#remark").val();
	var title_len = $("#title").val().length;
	var remark_len = $("#remark").val().length;
	if(!title && !remark){
		$("#error1").css('display','block'); 
		$("#error2").css('display','block');
		return;
	}
	if(!title){
		$("#error1").css('display','block');
	    return;
	}
	if(!remark){
		$("#error2").css('display','block');
		return;
	}
	 if(title_len>10){
	    	$("#error1 p").text("头部标题长度必须在1到10之间！");
	    	$("#error1").css('display','block'); 
	    	return;
	    }
	  if(remark_len>20){
	    	$("#error2 p").text("底部备注长度必须在1到20之间");
	    	$("#error2").css('display','block'); 
	    	return;
	    }
	  if(remark_len>20 && title_len>10){
		    $("#error1 p").text("头部标题长度必须在1到10之间！");
	    	$("#error1").css('display','block'); 
	    	$("#error2 p").text("底部备注长度必须在1到20之间");
	    	$("#error2").css('display','block'); 
	    	return;
	    }
    this.clearData();
    this.collectData();
   /* if (!this.validate()) {
        return;
    }*/
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticket/update", function(data){
        Feng.success("修改成功!");
        window.parent.Ticket.table.refresh();
        TicketInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ticketInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("ticketForm", TicketInfoDlg.validateFields);
});
