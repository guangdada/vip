/**
 * 初始化红包详情对话框
 */
var RedpackInfoDlg = {
    redpackInfoData : {}
};

/**
 * 清除数据
 */
RedpackInfoDlg.clearData = function() {
    this.redpackInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RedpackInfoDlg.set = function(key, value) {
    this.redpackInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RedpackInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RedpackInfoDlg.close = function() {
    parent.layer.close(window.parent.Redpack.layerIndex);
}

/**
 * 收集数据
 */
RedpackInfoDlg.collectData = function() {
	var packType=$("select[name='packType'] option:selected").val();
	var sendType=$("input[name='sendType']:checked").val();
	this.set('id').set('name').set('packType', packType).set('amount')
	.set('minAmount').set('maxAmount').set('sendType',sendType).set('actName').set('wishing').set('remark');
}

/**
 * 提交添加
 */
RedpackInfoDlg.addSubmit = function() {
	var valid = $("#redpackInfoForm").valid();
    if(!valid){
    	return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/redpack/add", function(data){
        Feng.success("添加成功!");
        window.parent.Redpack.table.refresh();
        RedpackInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.redpackInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
RedpackInfoDlg.editSubmit = function() {
	var valid = $("#redpackInfoForm").valid();
    if(!valid){
    	return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/redpack/update", function(data){
        Feng.success("修改成功!");
        window.parent.Redpack.table.refresh();
        RedpackInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.redpackInfoData);
    ajax.start();
}

$(function() {
	$("#redpackInfoForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
			name:{
				required :true,
				maxlength: 30
			},
			packType:{
				required :true
			},
			amount:{
				required :function(){
						var p1=$("input[name='sendType']:checked").val();
						if(p1=='0'){
							return true;
						}else{
							return false;
						}
				},
				checkAmount:true,
				checkAmountFormat : true
			},
			minAmount : {
				required : function() {
					var p1 = $("input[name='sendType']:checked").val();
					if (p1 == '1') {
						return true;
					} else {
						return false;
					}
				},
				checkAmount:true,
				checkAmountFormat : true
			},
			maxAmount:{
				required : function() {
					var p1 = $("input[name='sendType']:checked").val();
					if (p1 == '1') {
						return true;
					} else {
						return false;
					}
				},
				checkAmount:true,
				checkAmountFormat : true
			},
			actName:{
				required :true,
				maxlength: 30
			},
			wishing:{
				required :true,
				maxlength: 30
			},
			remark:{
				required :true,
				maxlength: 200
			}
		},
		messages: {
			name: {
	    		required:"红包名称不能为空",
	    		rangelength:"长度必须小于30个字符"
	    	},
	    	packType: {
	    		required:"红包类型不能为空"
	    	},
	    	amount: {
	    		required:"面额不能为空"
	    	},
	    	minAmount: {
	    		required:"最小金额不能为空"
	    	},
	    	maxAmount: {
	    		required:"最大金额不能为空"
	    	},
	    	storeType: {
	    		required:"店铺类型不能为空"
	    	},
	    	actName: {
	    		required:"活动名称不能为空",
	    		rangelength:"长度必须小于30个字符"
	    	},
	    	wishing: {
	    		required:"祝福语不能为空",
	    		rangelength:"长度必须小于30个字符"
	    	},
	    	remark: {
	    		required:"备注不能为空",
	    		rangelength:"长度必须小于200个字符"
	    	}
		}
	});
	

	// 自定义正则表达示验证方法
	$.validator.addMethod("checkAmount", function(value, element, params) {
		var checkAmount = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
		return this.optional(element) || (checkAmount.test(value));
	}, "请输入正确的面额！"); 
	
	$.validator.addMethod("checkAmountFormat",function(value,element,params){
		if(value>=1 && value<=200){
			return true;
		}else{
			return false;
		}
	},"面额必须大于1小于200！");
	
	$("input[name='sendType']").bind("click", function() {
		var type = $(this).val();
		if (type == '0') {
			$(".amount2").hide();
			$(".amount1").show();
		} else{
			$(".amount1").hide();
			$(".amount2").show();
		}
	});
	
	
});
