/**
 * 初始化商户详情对话框
 */
var MerchantInfoDlg = {
    merchantInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '商户名称不能为空'
                }
            }
        },
        mobile: {
            validators: {
                notEmpty: {
                    message: '手机号码不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
MerchantInfoDlg.clearData = function() {
    this.merchantInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MerchantInfoDlg.set = function(key, val) {
    this.merchantInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MerchantInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MerchantInfoDlg.close = function() {
    parent.layer.close(window.parent.Merchant.layerIndex);
}

/**
 * 收集数据
 */
MerchantInfoDlg.collectData = function() {
	this.set('id').set('name').set('mobile').set('tips').set('qq').set('headImg').set('qrcode');
}

/**
 * 验证数据是否为空
 */
MerchantInfoDlg.validate = function () {
    $('#merchantInfoForm').data("bootstrapValidator").resetForm();
    $('#merchantInfoForm').bootstrapValidator('validate');
    return $("#merchantInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
MerchantInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/merchant/add", function(data){
        Feng.success("添加成功!");
        window.parent.Merchant.table.refresh();
        MerchantInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.merchantInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MerchantInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/merchant/update", function(data){
        Feng.success("修改成功!");
        window.parent.Merchant.table.refresh();
        MerchantInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.merchantInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("merchantInfoForm", MerchantInfoDlg.validateFields);
	
	// 店铺logo上传
    var avatarUp = new $WebUpload("headImg");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
    
    // 公众号二维码上传
    var qrcode = new $WebUpload("qrcode");
    qrcode.setUploadBarId("progressBar");
    qrcode.init();
});
