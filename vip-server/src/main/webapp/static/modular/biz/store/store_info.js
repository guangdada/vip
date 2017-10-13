/**
 * 初始化门店详情对话框
 */
var StoreInfoDlg = {
    storeInfoData : {},
    validateFields: {
    	name: {
            validators: {
                notEmpty: {
                    message: '店铺名称不能为空'
                },
                stringLength:{
                	 max: 30,
                	 message: '长度必须小于30个字符'
                }
            }
        },
        servicePhone: {
            validators: {
                notEmpty: {
                    message: '联系电话不能为空'
                },
                stringLength:{
               	 max: 20,
               	 message: '长度必须小于20个字符'
               }
            }
        },
        area: {
            validators: {
                notEmpty: {
                    message: '所需区域不能为空'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '详细地址不能为空'
                },
                stringLength:{
               	 max: 50,
               	 message: '长度必须小于50个字符'
               }
            }
        },
        openTime: {
            validators: {
                notEmpty: {
                    message: '运营开始时间不能为空'
                },
                stringLength:{
               	 max: 2,
               	 message: '长度必须小于2个字符'
               }
            }
        },
        closeTime: {
            validators: {
                notEmpty: {
                    message: '运营结束时间不能为空'
                },
                stringLength:{
               	 max: 2,
               	 message: '长度必须小于2个字符'
               }
            }
        },
        coordinate: {
            validators: {
                notEmpty: {
                    message: '坐标不能为空'
                },
                stringLength:{
               	 max: 30,
               	 message: '长度必须小于30个字符'
               }
            }
        },
        description: {
            validators: {
                stringLength:{
               	 max: 200,
               	 message: '长度必须小于200个字符'
               }
            }
        },
        storeType: {
            validators: {
                notEmpty: {
                    message: '店铺类型不能为空'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
StoreInfoDlg.clearData = function() {
    this.storeInfoData = {};
}

/**
 * 验证数据是否为空
 */
StoreInfoDlg.validate = function () {
    $('#storeInfoForm').data("bootstrapValidator").resetForm();
    $('#storeInfoForm').bootstrapValidator('validate');
    return $("#storeInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreInfoDlg.set = function(key, value) {
    this.storeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StoreInfoDlg.close = function() {
    //parent.layer.close(window.parent.Store.layerIndex);
	history.back(-1);
}

/**
 * 收集数据
 */
StoreInfoDlg.collectData = function() {
	var coordinate = $("#coordinate").val();
	//var servicePhone = $("#servicePhonePre").val() + "-" + $("#servicePhoneSub").val();
	var provinceId = $("#province option:selected").val();
	var cityId = $("#city option:selected").val();
	var areaId = $("#area option:selected").val();
	var storePic = $("input[name='storePic']");
	var pics = [];
	for(var i =0;i<storePic.length;i++){
		var pic = {'id':storePic[i].id}
		pics.push(pic);
	}
    this.set('id').set('name').set('name').set('servicePhone').set('storeType')
    .set('provinceId',provinceId).set('cityId',cityId).set('areaId',areaId)
    .set('address').set('openTime').set('closeTime').set('description').set('logo').set('website').set('pics',JSON.stringify(pics));
    if(coordinate){
    	var longitude = coordinate.split(',')[0];
    	var	latitude = coordinate.split(',')[1];
    	if(coordinate.split(',')[0]){
    		this.set('longitude',longitude);
    		this.set('latitude',latitude);
		}
	}
}

/**
 * 提交添加
 */
StoreInfoDlg.addSubmit = function() {

    this.clearData();
    if (!this.validate()) {
        return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/store/add", function(data){
        Feng.success("添加成功!");
        StoreInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StoreInfoDlg.editSubmit = function() {

    this.clearData();
    if (!this.validate()) {
        return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/store/update", function(data){
        Feng.success("修改成功!");
        StoreInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeInfoData);
    ajax.start();
}

/**
 * 点击添加门店
 */
StoreInfoDlg.openPic = function (src) {
	var pop = $('#storePicPop');
	pop.find("img").attr("src",src);
	layer.closeAll();
    var index = layer.open({
        type: 1,
        title: false,
        fix:false,
        area: ['500px','500px'], //宽高
        shadeClose:true,
        content: pop
    });
    this.layerIndex = index;
};

StoreInfoDlg.bindEvent = function (){
	$(".upload-preview-img").hover(function() {
		$(this).find(".close-modal").removeClass("hide");
	}, function() {
		$(this).find(".close-modal").addClass("hide");
	});
	
	$(".upload-preview-img").bind("click",function(){
		var src = $(this).find("input:hidden").val();
		StoreInfoDlg.openPic(src);
	});
	
	$(".close-modal").bind("click",function(){
		$(this).parent().remove();
	});
}

$(function() {
	StoreInfoDlg.bindEvent();
	
	var storeId = $("#storeId").val();
	var areaId = $("#areaId").val();
	var provinceId = $("#provinceId").val();
	var cityId = $("#cityId").val();
	$("#areaId").initArea({
		target: $("#areaId"), 
		provinceId: provinceId ? provinceId :'',
		cityId: cityId?cityId:'',
		areaId: areaId?areaId:'',
		province: $("#province"),
		city: $("#city"),
		area: $("#area")
	});
	
	Feng.initValidator("storeInfoForm", StoreInfoDlg.validateFields,{});
	// 店铺logo上传
    var avatarUp = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
    
    // 店铺图片
    var storePic = new $WebUpload("storePic");
    storePic.setAppend(true);
    storePic.setCallBackFun(function(){
    	StoreInfoDlg.bindEvent();
    });
    storePic.setUploadUrl(Feng.ctxPath + '/upload/storePic');
    storePic.setContent('<li class="upload-preview-img"><img src=""> <a class="js-delete-picture close-modal small hide">×</a></li>');
    storePic.init();
});
