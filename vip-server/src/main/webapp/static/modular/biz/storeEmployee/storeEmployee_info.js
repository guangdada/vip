/**
 * 初始化员工管理详情对话框
 */
var StoreEmployeeInfoDlg = {
	storeEmployeeInfoData : {},
	validateFields : {
		name : {
			validators : {
				notEmpty : {
					message : '员工姓名不能为空'
				}
			}
		},
		mobile : {
			validators : {
				notEmpty : {
					message : '联系方式不能为空'
				}
			}
		},
		password : {
			validators : {
				notEmpty : {
					message : '密码不能为空'
				}
			}
		}
	}

};


/**
 * 清除数据
 */
StoreEmployeeInfoDlg.clearData = function() {
    this.storeEmployeeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreEmployeeInfoDlg.set = function(key, value) {
    this.storeEmployeeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoreEmployeeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StoreEmployeeInfoDlg.close = function() {
    parent.layer.close(window.parent.StoreEmployee.layerIndex);
}
/**
 * 验证数据是否为空
 */
StoreEmployeeInfoDlg.validate = function () {
    $('#storeEmployeeConfigInfoForm').data("bootstrapValidator").resetForm();
    $('#storeEmployeeConfigInfoForm').bootstrapValidator('validate');
    return $("#storeEmployeeConfigInfoForm").data('bootstrapValidator').isValid();
}
/**
 * 收集数据
 */
StoreEmployeeInfoDlg.collectData = function() {
	var roleType = $("input[name='roleType']:checked").val();
	var store = $("select[name='stores'] option:selected").val();
	var sex = $("input[name='sex']:checked").val();
	/*alert("s"+sex);
	alert("s1"+store);
	alert("s2"+roleType);*/
    this.set('id').set('password').set('mobile').set('store',store).set('name').set('sex',sex).set('roleType',roleType);

}

/**
 * 提交添加
 */
StoreEmployeeInfoDlg.addSubmit = function() {
	if (!this.validate()) {
        return;
    }
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storeEmployee/add", function(data){
        Feng.success("添加成功!");
        window.parent.StoreEmployee.table.refresh();
        StoreEmployeeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeEmployeeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StoreEmployeeInfoDlg.editSubmit = function() {

    this.clearData();
    
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/storeEmployee/update", function(data){
        Feng.success("修改成功!");
        window.parent.StoreEmployee.table.refresh();
        StoreEmployeeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storeEmployeeInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("storeEmployeeConfigInfoForm", StoreEmployeeInfoDlg.validateFields);
});
