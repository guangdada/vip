
/**
 * 初始化会员卡管理详情对话框
 */
var CardInfoDlg = {
    cardInfoData : {},
    num : /^[1-9]\d*$/
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
	var coverType = $("input[name='coverType']:checked").val();
	var coverPic = $("#coverPic").val();
	this.set('id').set('name').set('description').set('servicePhone',servicePhone).set('colorCode').set('termType',termDaysRadio).set('coverType',coverType).set('coverPic',coverPic)
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
	var coverType_help = $(".error_info.coverType").text("").hide();
	var coverType = $("input[name='coverType']:checked").val();
	var coverPic = $("#coverPic").val();
	if(coverType == 1 &&　!coverPic){
		result = false;
		coverType_help.text("请选择封面图片").show();
	}
	
	var name = $("#name").val();
	var name_help = $(".error_info.name").text("").hide();
	if(!name){
		name_help.text("会员卡名称不能为空").show();
		result = false;
	}
	
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

CardInfoDlg.chooseImage = function (){
	var index = layer.open({
	    type: 2,
	    title: '添加会员卡',
	    area: ['800px','440px'], //宽高
	    fix: false, //不固定
	    maxmin: true,
	    content: Feng.ctxPath + '/card/card_add'
	});
	this.layerIndex = index;
	layer.full(index);
}

CardInfoDlg.setBgColor = function (){
	$("input[name='coverType']").eq(0).click();
	var colorValue = $("#colorValue").val();
	if(colorValue){
		$(".card-region").css({"background-image":""});
		$(".card-region").css({"background-color":colorValue});
		$(".card-color-show").css({"background-color":colorValue});
	}
}

CardInfoDlg.setBgImg = function (){
	$("input[name='coverType']").eq(1).click();
	var coverPic = $("#coverPic").val();
	if(coverPic){
		/*var pic = Feng.ctxPath + "/kaptcha/" + coverPic;
		$(".card-region").css({"background-image":"url("+pic+")"});*/
		$(".card-region").css({"background-image":"url("+coverPic+")"});
		$(".card-region").css({"background-color":""});
	}
}

$(function() {
	$("input[name=number],input[name=pointsNum],input[name=termDays],input[name=tradeLimit],input[name=amountLimit],input[name=pointsLimit]").bind("blur",function () {
		var number = $(this).val() ? $(this).val() : "";
		if(!CardInfoDlg.num.test(number)){
			$(this).val("");
		}
	});
	
	$("input[name='termDays']").attr("maxlength",6);
	$("input[name='tradeLimit']").attr("maxlength",6);
	$("input[name='amountLimit']").attr("maxlength",6);
	$("input[name='pointsLimit']").attr("maxlength",6);
	$("input[name='servicePhone']").attr("maxlength",20);
	$("input[name='pointsNum']").attr("maxlength",4);
	$("input[name='number']").attr("maxlength",2);
	$("input[name='discount']").attr("maxlength",2);
	$("input[name='name']").attr("maxlength",9);
	
	$(".card-color-box").bind("click",function(){
		var color = $(this).attr("data-value");
		var code = $(this).attr("data-name");
		$("#colorCode").val(code);
		$("#colorValue").val(color);
		CardInfoDlg.setBgColor();
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
		if(!CardInfoDlg.num.test(discount)){
			$(this).val("");
		}else{
			$(".item-name.discount").text(discount + "折");
		}
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
	
	var id = $("#id").val();
	var hideDiscount = $("#hideDiscount").val();
	var hidePoint = $("#hidePoint").val();
	var hideCoupon = $("#hideCoupon").val();
	$(".membership-item").hide();
	if(hideCoupon){
		$(".item-name.coupon").parent().show();
	}
	if(hidePoint){
		$(".item-name.score").parent().show();
	}
	if(hideDiscount){
		$(".item-name.discount").parent().show();
		$(".item-name.discount").text(hideDiscount + "折");
	}
	
	
	$("input[name='coverType']").bind("change",function(){
		var value = $(this).val();
		if(value == '1'){
			CardInfoDlg.setBgImg();
		}else if(value == '0'){
			CardInfoDlg.setBgColor();
		}
	});
	// 店铺图片
    var coverPic = new $WebUpload("coverPic");
    coverPic.setCallBackFun(function(pictureName){
    	$("#coverPic").val(pictureName);
    	CardInfoDlg.setBgImg();
    });
    coverPic.setUploadUrl(Feng.ctxPath + '/upload/logo');
    coverPic.init();
});
