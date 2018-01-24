/**
 * 初始化小票详情对话框
 */
var TicketInfoDlg = {
    ticketInfoData : {}
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
   // parent.layer.close(window.parent.Ticket.layerIndex);
	history.back(-1);
}

/**
 * 收集数据
 */
TicketInfoDlg.collectData = function() {
	var specType = $("input[name='specType']:checked").val();
	var storeId = $("select[name='stores'] option:selected").val();
    this.set('id').set('name').set('specType',specType).set('title').set('remark').set('storeId',storeId);
}

/**
 * 提交添加
 */
TicketInfoDlg.addSubmit = function() {
	var valid = $("#ticketForm").valid();
    if(!valid){
    	return;
    }
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ticket/add", function(data){
        Feng.success("添加成功!");
       // window.parent.Ticket.table.refresh();
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
	var valid = $("#ticketForm").valid();
    if(!valid){
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
        //window.parent.Ticket.table.refresh();
        TicketInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ticketInfoData);
    ajax.start();
}

$(function() {
	$("#title").keyup(function() {
		var title = $("#title").val();
		$("#ptitle").text($("#title").val());
	});
	$("#remark").keyup(function() {
		var remark = $("#remark").val();
		$("#premark").text($("#remark").val());
	});
	$('input:radio[name="specType"]').change(function() {
		var specType = $("input[name='specType']:checked").val();
		if (specType == 2) {
			$("#ticketWidth").removeClass("small");
		}
		if (specType == 1) {
			$("#ticketWidth").addClass("small");
		}
	});
	
	$("#ticketForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
	    	title: {
	    		required :true,
	    		rangelength:[1,50]
	    	},
	    	remark: {
	    		required :true,
	    		rangelength:[1,50]
	    	},
	    	name: {
	    		required :true,
	    		rangelength:[1,50]
	    	},
	    	stores:{
	    		required:true,
	    		remote: {
	    		    url: Feng.ctxPath + "/ticket/checkStore",     //后台处理程序
	    		    type: "post",               //数据发送方式
	    		    dataType: "json",           //接受数据格式   
	    		    data: {                     //要传递的数据
	    		        storeId: function() {
	    		            return $("#stores").val();
	    		        }
	    		    }
	    		}
	    	}
	    },
	    messages: {
	    	title: {
	    		required:"小票名称不能为空",
	    		rangelength:"小票名称必须在 1-50 个字内"
	    	},
	    	name: {
	    		required:"小票抬头不能为空",
	    		rangelength:"小票抬头必须在 1-50 个字内"
	    	},
	    	remark: {
	    		required:"小票底部备注不能为空",
	    		rangelength:"小票底部备注必须在 1-50 个字内"
	    	},
	    	stores: {
	    		required:"请选择店铺",
	    		remote:"所选店铺已经添加了小票信息 "
	    	}
	    }
	});
	
});
