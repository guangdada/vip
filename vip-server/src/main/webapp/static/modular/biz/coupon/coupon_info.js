/**
 * 初始化优惠券管理详情对话框
 */
var CouponInfoDlg = {
    couponInfoData : {},
    num : /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/,
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '优惠券名称不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '优惠券名称必须在 1-10 个字内'
                }
            }
        },
        total:{
            validators: {
            	notEmpty: {
                    message: '发放总量不能为空'
                }
            }
        },
        value:{
            validators: {
            	notEmpty: {
                    message: '面值不能为空'
                }
            }
        },
        startAt:{
            validators: {
            	notEmpty: {
                    message: '生效时间不能为空'
                }
            }
        },
        endAt:{
            validators: {
            	notEmpty: {
                    message: '过期时间不能为空'
                }
            }
        },
        description:{
            validators: {
            	notEmpty: {
                    message: '使用说明不能为空'
                }
            }
        }
    }
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
	this.set('id').set('name').set('total').set('value').set('type',type)
	.set('isAtLeast',isAtLeast).set('isShare',isShare).set('atLeast').set('quota',quota).set('cardId',cardId)
	.set('description').set('servicePhone').set('startAtStr',startAt).set('endAtStr',endAt).set('storeId');
}

/**
 * 提交添加
 */
CouponInfoDlg.addSubmit = function() {

    this.clearData();
    if (!this.validateOther()) {
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
    if (!this.validateOther()) {
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

CouponInfoDlg.hideHelp = function (){
	$(".help-desc.error_info").text("").hide();
}
/**
 * 验证其他需要条件判断的项目
 */
CouponInfoDlg.validateOther = function (){
	var result = true;
	var name = $("#name").val();
	var name_help = $("#name").next("p").text("").hide();
	
	var total = $("#total").val();
	var total_help = $("#total").siblings("p").text("").hide();
	
	var value = $("#value").val();
	var value_help = $("#value").next("p").text("").hide();
	
	var is_at_least = $("input[name='is_at_least']:checked");
	var atLeast = $("#atLeast").val();
	var atLeast_help = $("#atLeast").parent().next("p").text("").hide();
	
	var startAt = $("#startAt").val();
	var startAt_help = $("#startAt").next("p").text("").hide();
	
	var endAt = $("#endAt").val();
	var endAt_help = $("#endAt").next("p").text("").hide();
	
	var description = $("#description").val();
	var description_help = $("#description").next("p").text("").hide();
	
	if(!name){
		name_help.text("优惠券名称必须在 1-10 个字内").show();
		result = false;
	}
	
	if(!total){
		total_help.text("发放总量必须是一个整数").show();
		result = false;
	}
	
	if(!value){
		value_help.text("优惠券面值必须大于等于 1 元").show();
		result = false;
	}
	
	if(is_at_least && is_at_least.val() == 1 && !atLeast){
		atLeast_help.text("订单限制金额必须大于等于优惠券的面值").show();
		result = false;
	}
	
	if(!startAt){
		startAt_help.text("必须选择一个生效时间").show();
		result = false;
	}
	
	if(!endAt){
		endAt_help.text("必须选择一个过期时间").show();
		result = false;
	}
	
	if(!description){
		description_help.text("使用说明不能为空").show();
		result = false;
	}
	return result;
}


$(function() {
	CouponInfoDlg.hideHelp();
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
	
	$("input[name='name']").attr("maxlength",10);
	$("#description").attr("maxlength",250);
	$("#servicePhone").attr("maxlength",20);
	$("#servicePhone").attr("maxlength",20);
	$("#value").attr("maxlength",7);
	$("#atLeast").attr("maxlength",7);
});
