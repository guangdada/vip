/**
 * 初始化会员卡管理详情对话框
 */
var CardInfoDlg = {
    cardInfoData : {}
};

/**
 * 清除数据
 */
CardInfoDlg.clearData = function() {
    this.cardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardInfoDlg.set = function(key, value) {
    this.cardInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CardInfoDlg.close = function() {
    //parent.layer.close(window.parent.Card.layerIndex);
	history.back(-1);
}

/**
 * 收集数据
 */
CardInfoDlg.collectData = function() {
	var termDays = $("input[name='termDays']").val();
	var termStartAt = $("#js-stime").val();
	var termEndAt = $("#js-etime").val();
	var termToCardId = $("#termToCardId option:selected").val();
	var tradeLimit = $("input[name='tradeLimit']").val();
	var amountLimit = $("input[name='amountLimit']").val();
	var pointsLimit = $("input[name='pointsLimit']").val();
	var level =  $("#level option:selected").val();
	var termDaysRadio = $("input[name='termDaysRadio']:checked").val();
	var grantType = $("input[name='grantType']:checked").val();
	var servicePhone = $("#servicePhone").val();
	
	this.set('id').set('name').set('description').set('servicePhone',servicePhone).set('colorCode').set('termType',termDaysRadio)
	.set('grantType',grantType).set('termToCardId',termToCardId).set('cardLevel',level).set('pointsLimit',pointsLimit).set('amountLimit',amountLimit).set('tradeLimit',tradeLimit)
    .set('rights',JSON.stringify(CardInfoDlg.rights));
	
	if (termDaysRadio == '1'){
		this.set('termDays',termDays)
	}else if (termDaysRadio == '2'){
		this.set('termStartAtStr',termStartAt).set('termEndAtStr',termEndAt);
	}
}

/**
 * 权益值json
 */
CardInfoDlg.rights = [];

/**
 * 验证其他需要条件判断的项目
 */
CardInfoDlg.validateOther = function (){
	var result = true;
	var name = $("#name").val();
	var name_help = $("#name").siblings(".error_info").text("").hide();
	if(!name){
		//$("#name").focus();
		name_help.text("会员卡名称不能为空").show();
		result = false;
	}
	
	
	var rights = [];
	var isDiscount = $("input[name='isDiscount']").prop("checked");
	var isCoupon = $("input[name='isCoupon']").prop("checked");
	var isPoints = $("input[name='isPoints']").prop("checked");
	var discount = $("input[name='discount']").val();
	var pointsNum = $("input[name='pointsNum']").val();
	var rules_help = $(".upgrade-rules").next(".error_info").text("").hide();
	var term_help = $(".term-block").children(".error_info").text("").hide();
	var level_help = $("#level").siblings(".error_info").text("").hide();
	var pointsLimit_help = $("input[name='pointsLimit']").next(".error_info").text("").hide();
	
	if(!isCoupon && !isDiscount && !isPoints){
		result = false;
		rules_help.text("请至少为会员卡选择一种权益").show();
	}
	if(isCoupon){
		var coupon = [];
		var couponSelect = $(".coupon-select");
		if(couponSelect && couponSelect.length > 0){
			$.each(couponSelect,function () {
				var number =  $(this).next("input").val();
				var couponId = $(this).find("option:selected").val();
				if(!number){
					result = false;
					rules_help.text("优惠券数量不能为空").show();
				} else if(couponId == 0){
					result = false;
					rules_help.text("请选择要赠送的优惠券").show();
				} else{
					coupon.push({couponId:couponId,number:number});
				}
			});
			rights.push({type:'coupon',coupon:coupon});
		}
		if(coupon.length == 0){
			result = false;
			rules_help.text("优惠券信息填写不正确").show();
		}
	}
	if(isDiscount){
		if(!discount){
			result = false;
			rules_help.text("折扣不能为空").show();
		}else{
			rights.push({type:'discount',discount:discount});
		}
	}
	if(isPoints){
		if(!pointsNum){
			result = false;
			rules_help.text("积分不能为空").show();
		}else{
			rights.push({type:'points',points:pointsNum});
		}
	}
	// 设置权益信息
	CardInfoDlg.rights = rights;
	
	var description = $("#description").val();
	var description_help = $("#description").next(".error_info").text("").hide();
	if(!description){
		description_help.text("使用须知不能为空").show();
		result = false;
	}
	
	var grantType = $("input[name='grantType']:checked").attr('data-name');
	if(grantType=='NO_RULE' || grantType=='SUB_WX'){
		var term = $("input[name='termDaysRadio']:checked").val();
		if(term == '1'){
			var termDays = $("input[name='termDays']").val();
			if(!termDays){
				result = false;
				term_help.text("会员卡有效期天数不能为空").show();
			}
		}else if(term == '2'){
			var termStartAt = $("input[name='termStartAt']").val();
			var termEndAt = $("input[name='termEndAt']").val();
			if(!termStartAt){
				result = false;
				term_help.text("会员卡有效期开始时间不能为空").show();
			}else if(!termEndAt){
				result = false;
				term_help.text("会员卡有效期结束时间不能为空").show();
			}
		}
	}else if(grantType=='RULE'){
		var tradeLimit = $("input[name='tradeLimit']").val();
		var amountLimit = $("input[name='amountLimit']").val();
		var pointsLimit = $("input[name='pointsLimit']").val();
		var level =  $("#level option:selected").val();
		if(!tradeLimit && !amountLimit && !pointsLimit){
			result = false;
			pointsLimit_help.text("请至少为会员卡设置一种升级条件").show();
		}
		if(!level || level == '0'){
			result = false;
			level_help.text("请选择等级").show();
		}
	}
	
	
	return result;
}

/**
 * 提交添加
 */
CardInfoDlg.addSubmit = function() {

    this.clearData();
    if (!this.validateOther()) {
        return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/card/add", function(data){
        Feng.success("添加成功!");
        //window.parent.Card.table.refresh();
        CardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CardInfoDlg.editSubmit = function() {

    this.clearData();
    if (!this.validateOther()) {
        return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/card/update", function(data){
        Feng.success("修改成功!");
        //window.parent.Card.table.refresh();
        CardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardInfoData);
    ajax.start();
}

CardInfoDlg.chageTerm = function (value) {
	var expiry = $(".expiry-date span");
	if(value == 'infinite' ){
		$("input[name='termDays']").val("");
		$("#js-stime,#js-etime").val("");
		expiry.text("无限期");
	}else if(value== 'days'){
		$("#js-stime,#js-etime").val("");
		var termDays = $("input[name='termDays']").val();
		expiry.text(termDays + "天");
	}else if(value == 'range'){
		$("input[name='termDays']").val("");
		var termStartAt = $("input[name='termStartAt']").val();
		var termEndAt = $("input[name='termEndAt']").val();
		expiry.text((termStartAt ? termStartAt : "") + "至" + (termEndAt ? termEndAt : ""));
	}
}

CardInfoDlg.chooseStart = function (value) {
	var range = $("input[name='termDaysRadio']")[2].click();
}


$(function() {
	$(".card-color-box").bind("click",function(){
		var color = $(this).attr("data-value");
		var code = $(this).attr("data-name");
		$("#colorCode").val(code);
		$(".card-region").css({"background-color":color});
		$(".card-color-show").css({"background-color":color});
		
	});
	
	$("#name").bind("blur",function(){
		var name = $(this).val() ? $(this).val() : "";
		$(".member-type").text(name);
	});
	
    $("input[name='termDays']").on("blur",function(){
		var range = $("input[name='termDaysRadio']")[1].click();
	});
	
	$("#description").bind("blur",function(){
		var description = $(this).val() ? $(this).val() : "";
		$(".js-block-sub-desc").text(description);
	});
	
	$("input[name=isPostageFree]").bind("change",function () {
		var check = $(this).prop("checked");
		if(check){
			$(".item-name.free-shipping").parent().show();
		}else{
			$(".item-name.free-shipping").parent().hide();
		}
	});
	
	$("input[name=isDiscount]").bind("change",function () {
		var check = $(this).prop("checked");
		if(check){
			$(".item-name.discount").parent().show();
		}else{
			$(".item-name.discount").parent().hide();
		}
	});
	
	$("input[name=discount]").bind("blur",function () {
		var discount = $(this).val() ? $(this).val() : "";
		$(".item-name.discount").text(discount + "折");
	});
	
	$("input[name=isCoupon]").bind("change",function () {
		var check = $(this).prop("checked");
		if(check){
			$(".item-name.coupon").parent().show();
		}else{
			$(".item-name.coupon").parent().hide();
		}
	});
	
	$("input[name=isPoints]").bind("change",function () {
		var check = $(this).prop("checked");
		if(check){
			$(".item-name.score").parent().show();
		}else{
			$(".item-name.score").parent().hide();
		}
	});
	
	$("input[name='grantType']").bind("click",function(){
		var code = $(this).val();
		var type = $(this).attr("data-name");
		if(type=='NO_RULE'){
			$(".rule-group").hide();
			$(".rule-no-group").show();
		}else if(type=='SUB_WX'){
			$(".rule-group").hide();
			$(".rule-no-group").show();
		}else if(type=='RULE'){
			$(".rule-group").show();
			$(".rule-no-group").hide();
		}
	});
	
	$(".error_info").hide();
	$(".membership-item").hide();
});