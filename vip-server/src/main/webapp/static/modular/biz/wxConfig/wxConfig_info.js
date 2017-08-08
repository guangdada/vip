/**
 * 初始化公众号管理详情对话框
 */
var WxConfigInfoDlg = {
    wxConfigInfoData : {},
    validateFields: {
    	appid: {
            validators: {
                notEmpty: {
                    message: 'appid不能为空'
                }
            }
        },
        mchId: {
            validators: {
                notEmpty: {
                    message: 'mchId不能为空'
                }
            }
        },
        secret: {
            validators: {
                notEmpty: {
                    message: 'secret不能为空'
                }
            }
        },
        encodingAESKey: {
            validators: {
                notEmpty: {
                    message: 'EncodingAESKey不能为空'
                }
            }
        },
        apiKey: {
            validators: {
                notEmpty: {
                    message: 'apiKey不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
WxConfigInfoDlg.clearData = function() {
    this.wxConfigInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WxConfigInfoDlg.set = function(key, val) {
    this.wxConfigInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WxConfigInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WxConfigInfoDlg.close = function() {
    parent.layer.close(window.parent.WxConfig.layerIndex);
}

/**
 * 收集数据
 */
WxConfigInfoDlg.collectData = function() {
    this.set('id').set('appid').set('mchId').set('secret').set('encodingAESKey').set('apiKey');
}

/**
 * 验证数据是否为空
 */
WxConfigInfoDlg.validate = function () {
    $('#wxConfigInfoForm').data("bootstrapValidator").resetForm();
    $('#wxConfigInfoForm').bootstrapValidator('validate');
    return $("#wxConfigInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
WxConfigInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/wxConfig/add", function(data){
        Feng.success("添加成功!");
        window.parent.WxConfig.table.refresh();
        WxConfigInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.wxConfigInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WxConfigInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/wxConfig/update", function(data){
        Feng.success("修改成功!");
        window.parent.WxConfig.table.refresh();
        WxConfigInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.wxConfigInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("wxConfigInfoForm", WxConfigInfoDlg.validateFields);
});
