// 新规则要求值必须与第一个参数相同
$.validator.addMethod("coverPic" , function(value, element, param) {
	var coverType = $("input[name='coverType']:checked").val();
	var coverPic = $("#coverPic").val();
	if(coverType == 1){
		return coverPic ? true : false;
	}else{
		return true;
	}
});

//新规则要求值必须与第一个参数相同
$.validator.addMethod("compareDate" , function(value, element, param) {
	var type = $("input[name='grantType']:checked").attr("data-name");
	var day = $("#termDaysRadio2").prop("checked");
	var termStartAt = $("#js-stime").val();
	var termEndAt = $("#js-etime").val();
	if(type=='SUB_WX' &&　day && termStartAt){
		return termEndAt > termStartAt;
	}else{
		return true;
	}
});

//新规则要求值必须与第一个参数相同
$.validator.addMethod("grantType" , function(value, element, param) {
	var type = $("input[name='grantType']:checked").attr("data-name");
	var tradeLimit = $("input[name='tradeLimit']").val();
	var amountLimit = $("input[name='amountLimit']").val();
	var pointsLimit = $("input[name='pointsLimit']").val();
	if(type=='RULE'){
		if(!tradeLimit && !amountLimit && !pointsLimit){
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
});

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
		this.set('termStartAt',termStartAt).set('termEndAt',termEndAt);
	}
}

/**
 * 权益值json
 */
CardInfoDlg.rights = [];
/**
 * 初始化权益值json
 */
CardInfoDlg.initRights = function (){
	var rights = [];
	var isDiscount = $("#isDiscount").prop("checked");
	var isCoupon = $("#isCoupon").prop("checked");
	var isPoints = $("#isPoints").prop("checked");
	var discount = $("input[name='discount']").val();
	var pointsNum = $("input[name='pointsNum']").val();
	if(isCoupon){
		var coupon = [];
		var couponSelect = $(".coupon-select");
		if(couponSelect && couponSelect.length > 0){
			$.each(couponSelect,function () {
				var number =  $(this).next("input").val();
				var couponId = $(this).find("option:selected").val();
				coupon.push({couponId:couponId,number:number});
			});
			rights.push({type:'coupon',coupon:coupon});
		}
	}
	if(isDiscount){
		rights.push({type:'discount',discount:discount});
	}
	if(isPoints){
		rights.push({type:'points',points:pointsNum});
	}
	// 设置权益信息
	CardInfoDlg.rights = rights;
}

/**
 * 提交添加
 */
CardInfoDlg.addSubmit = function() {

    this.clearData();
    var valid = $("#cardForm").valid();
    if(!valid){
    	return;
    }
    
    CardInfoDlg.initRights();
    
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
    var valid = $("#cardForm").valid();
    if(!valid){
    	return;
    }
    CardInfoDlg.initRights();
    
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
	$("#cardForm").validate({
		errorPlacement: function(error, element) {
			var name = element.attr("name");
			if(name == "coverType" || name == "pointsNum"){
				error.appendTo(element.parents(".controls:eq(0)"));
			}else if(name == "cardRight"){
				error.appendTo(element.parents(".upgrade-rules"));
			}else if(name == "grantType"){
				error.appendTo($("#tradeLimitError"));
			}else{
				error.appendTo(element.parent());
			}
		},
		rules: {
	    	name: {
	    		required :true,
	    		rangelength:[1,10],
	    		remote: {
	    		    url: Feng.ctxPath + "/card/checkName",     //后台处理程序
	    		    type: "post",               //数据发送方式
	    		    dataType: "json",           //接受数据格式   
	    		    data: {                     //要传递的数据
	    		        cardName: function() {
	    		            return $("#name").val();
	    		        },
	    		        id:function(){
	    		        	var id = $("#id").val();
	    		        	return id ? id : "";
	    		        }
	    		    }
	    		}
	    	},
	    	description:"required",
	    	coverType:"coverPic",
	    	cardRight:{
	    		required:true,
	    		minlength:1
	    	},
	    	discount:{
	    		required:"#isDiscount:checked"
	    	},
	    	number:{
	    		required:"#isCoupon:checked"
	    	},
	    	couponId:{
	    		required:"#isCoupon:checked"
	    	},
	    	pointsNum:{
	    		required:"#isPoints:checked"
	    	},
	    	termDays:{
	    		required :function(){
	    			var type = $("input[name='grantType']:checked").attr("data-name");
	    			var day = $("#termDaysRadio1").prop("checked");
	    			if(type=='SUB_WX' &&　day){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		},
	    		maxlength: 5
	    	},
	    	termStartAt:{
	    		required :function(){
	    			var type = $("input[name='grantType']:checked").attr("data-name");
	    			var day = $("#termDaysRadio2").prop("checked");
	    			if(type=='SUB_WX' &&　day){
	    				return true;
	    			}else{
	    				return false;
	    			}
	    		}
	    	},
	    	termEndAt:{
	    		compareDate:true
	    	},
	    	grantType:"grantType",
	    	level:{
	    		required:"#grantTypeRULE:checked",
	    		remote: {
	    		    url: Feng.ctxPath + "/card/checkLevel",     //后台处理程序
	    		    type: "post",               //数据发送方式
	    		    dataType: "json",           //接受数据格式   
	    		    data: {                     //要传递的数据
	    		        cardLevel: function() {
	    		            return $("#level").val();
	    		        },
	    		        id:function(){
	    		        	var id = $("#id").val();
	    		        	return id ? id : "";
	    		        }
	    		    }
	    		}
	    	},
	    	tradeLimit:{
	    		digits:true
	    	},
	    	amountLimit:{
	    		digits:true
	    	},
	    	pointsLimit:{
	    		digits:true
	    	}
	    },
	    messages: {
	    	name: {
	    		required:"会员卡名称不能为空",
	    		rangelength:"会员卡名称必须在 1-20 个字内",
	    		remote:"会员卡名称已存在 "
	    	},
	    	description: "使用须知不能为空",
	    	coverType: "请选择封面图片",
	    	cardRight: "请至少为会员卡选择一种权益",
	    	discount:"折扣不能为空",
	    	number:"优惠券数量不能为空",
	    	couponId:"请选择优惠券",
	    	pointsNum:"积分不能为空",
	    	termDays:"有效期天数不能为空",
	    	termStartAt:"开始时间不能为空",
	    	termEndAt:"结束时间不能小于开始时间",
	    	grantType:"请至少为会员卡设置一种升级条件",
	    	pointsLimit:"请输入整数",
	    	tradeLimit:"请输入整数",
	    	amountLimit:"请输入整数",
	    	level:{
	    		remote:"该会员卡等级已经存在",
	    		required:"请选择等级"
	    	}
	    }
	});
	
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
