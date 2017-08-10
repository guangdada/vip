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
                }
            }
        },
        servicePhonePre: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        servicePhoneSub: {
            validators: {
                notEmpty: {
                    message: '不能为空'
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
                }
            }
        },
        openTime: {
            validators: {
                notEmpty: {
                    message: '运营开始时间不能为空'
                }
            }
        },
        closeTime: {
            validators: {
                notEmpty: {
                    message: '运营结束时间不能为空'
                }
            }
        },
        coordinate: {
            validators: {
                notEmpty: {
                    message: '坐标不能为空'
                }
            }
        }
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
	var servicePhone = $("#servicePhonePre").val() + "-" + $("#servicePhoneSub").val();
	var provinceId = $("#province option:selected").val();
	var cityId = $("#city option:selected").val();
	var areaId = $("#area option:selected").val();
    this.set('id').set('name').set('name').set('servicePhone',servicePhone)
    .set('provinceId',provinceId).set('cityId',cityId).set('areaId',areaId)
    .set('address').set('openTime').set('closeTime').set('description').set('logo').set('website');
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

$(function() {
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
	// 初始化头像上传
    var avatarUp = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
});
