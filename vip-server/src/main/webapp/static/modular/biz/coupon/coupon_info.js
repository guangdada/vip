// 新规则要求值必须与第一个参数相同
$.validator.addMethod("conpareDate" , function(value, element, param) {
	var endAt =$("#endAt").val();
	var startAt =$("#startAt").val();
   return endAt > startAt;
},"过期时间必须大于生效时间");
/**
 * 初始化优惠券管理详情对话框
 */
var CouponInfoDlg = {
    couponInfoData : {},
    num : /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/
};

/**
 * 清除数据
 */
CouponInfoDlg.clearData = function() {
    this.couponInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponInfoDlg.set = function(key, val) {
    this.couponInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponInfoDlg.close = function() {
    //parent.layer.close(window.parent.Coupon.layerIndex);
	history.back(-1);
}

/**
 * 收集数据
 */
CouponInfoDlg.collectData = function() {
	var type = $("input[name='type']:checked").val();
	var isAtLeast = $("input[name='is_at_least']:checked").val();
	var isShare = $("input[name='isShare']").is(":checked") ? 1 : 0;
	var startAt = $("#startAt").val();
	var endAt = $("#endAt").val();
	var quota = $("#quota option:selected").val();
	var cardId = $("#user_level option:selected").val();
	var stores = $("input[name=storeIds]:checked");
	var storeIds = [];
	if(stores &&　stores.length > 0){
		for(var i =0;i<stores.length;i++){
			storeIds.push(stores[i].value);
		}
	}
	this.set('id').set('name').set('total').set('value').set('type',type)
	.set('isAtLeast',isAtLeast).set('isShare',isShare).set('atLeast').set('quota',quota).set('cardId',cardId)
	.set('description').set('servicePhone').set('startAt',startAt).set('endAt',endAt).set('storeId').set('storeIds',storeIds.join(","));
}

/**
 * 提交添加
 */
CouponInfoDlg.addSubmit = function() {

    this.clearData();
    var valid = $("#couponForm").valid();
    if(!valid){
    	return;
    }
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/coupon/add", function(data){
        Feng.success("添加成功!");
        //window.parent.Coupon.table.refresh();
        CouponInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponInfoDlg.editSubmit = function() {

    this.clearData();
    var valid = $("#couponForm").valid();
    if(!valid){
    	return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/coupon/update", function(data){
        Feng.success("修改成功!");
        //window.parent.Coupon.table.refresh();
        CouponInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.start();
}

CouponInfoDlg.startDate = function (startAt) {
	var endAt = $("#endAt").val();
	var datestr = "有效期：" + (startAt ? startAt : ' 20xx : 00 : 00') + ' - ' + (endAt ? endAt : ' 20xx : 00 : 00');
	$(".promote-date").text(datestr);
}

CouponInfoDlg.endDate = function (endAt) {
	var startAt = $("#startAt").val();
	var datestr = "有效期：" + (startAt ? startAt : ' 20xx : 00 : 00') + ' - ' + (endAt ? endAt : ' 20xx : 00 : 00');
	$(".promote-date").text(datestr);
}

$(function() {
	 $("#couponForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());  
		},
		rules: {
	    	name: {
	    		required :true,
	    		minlength: 1,
	    		maxlength: 10
	    	},
	    	description:"required",
	    	total:{
	    		required :true,
	    		digits:true,
	    		minlength: 1,
	    		maxlength: 3
	    	},
	    	value:{
	    		required :true,
	    		digits:true,
	    		minlength: 1,
	    		maxlength: 3
	    	},
	    	at_least: {
		        required: function(){
		        	var isAtLeast = $("input[name='is_at_least']:checked").val();
		        	return isAtLeast == 1;
		        }
		    },
		    start_at:"required",
		    end_at:{
		    	conpareDate:true,
		    	required:true
		    }
	    },
	    messages: {
	    	name: "优惠券名称必须在 1-10 个字内",
	    	description: "请输入使用说明",
	    	total: "发放总量必须是1-3位的整数",
	    	value: "优惠券面值必须是1-3位的整数",
	    	at_least:"订单限制金额必须大于等于优惠券的面值",
	    	start_at:"必须选择一个生效时间",
	    	end_at:"过期时间必须大于生效时间"
	    }
	});
	
	 
	$(".card-bgcolor-box").bind("click",function (){
		var bgColor = $(this).attr("data-value");
		$("#bgColor").val(bgColor);
	});
	
	$("#name").bind("blur",function(){
		var name = $(this).val() ? $(this).val() : "优惠券标题";
		$(".promote-card-name").text(name);
	});
	
	$("#value").bind("blur",function(){
		var number = $(this).val() ? $(this).val() : "";
		if(!CouponInfoDlg.num.test(number)){
			$(this).val("");
			$(".promote-value-sign").text("￥0.00");
		}else{
			$(".promote-value-sign").text("￥" + number);
		}
	});
	
	$("input[name='is_at_least']").bind("change",function(){
		var isAtLeast = $(this).val();
		if(isAtLeast == 0){
			$(".promote-limit").text("不限制");
			$("#atLeast").val("");
		}else {
			var atLeast = $("#atLeast").val();
			$(".promote-limit").text("订单满" + (atLeast ? atLeast : 'xx') + "元");
		}
	});
	
	$("#atLeast").bind("blur",function(){
		var atLeast = $(this).val();
		if(!CouponInfoDlg.num.test(atLeast)){
			$(this).val("");
			$(".promote-limit").text("订单满xx元");
		}else{
			$(".promote-limit").text("订单满 " +atLeast+ "元 (含运费)");
		}
	});
	
	$("#description").bind("blur",function(){
		var description = $(this).val();
		$(".js-desc-detail").text(description ? description : "暂无使用说明....");
	});
	
	$("#checkAllStoreIds").bind("change",function(){
		var checked = $(this).prop("checked");
		$("input[name='storeIds']").prop("checked",checked);
	});
	
	
	$("input[name='name']").attr("maxlength",10);
	$("#description").attr("maxlength",250);
	$("#servicePhone").attr("maxlength",20);
	$("#value").attr("maxlength",7);
	$("#atLeast").attr("maxlength",7);
});
