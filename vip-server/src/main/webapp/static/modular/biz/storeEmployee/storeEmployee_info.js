/**
 * 初始化员工管理详情对话框
 */
var StoreEmployeeInfoDlg = {
	storeEmployeeInfoData : {},
	/*validateFields : {
		name : {
			validators : {
				notEmpty : {
					message : '员工姓名不能为空'
				},
				 stringLength: {
                     min: 2,
                     max: 20,
                     message: '用户名长度必须在2到20之间'
                 },
			}
		},
		mobile : {
			validators : {
				notEmpty : {
					message : '联系方式不能为空'
				},
		   stringLength: {
            min: 11,
            max: 11,
            message: '请输入11位手机号码'
          },
        regexp: {
            regexp: /^1[3|5|8]{1}[0-9]{9}$/,
            message: '请输入正确的手机号码'
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
	}*/

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
	var roleId = $("input[name='roleType']:checked").val();
	var storeId = $("select[name='stores'] option:selected").val();
	var sex = $("input[name='sex']:checked").val();
    this.set('id').set('password').set('mobile').set('storeId',storeId).set('name').set('sex',sex).set('roleId',roleId);
}
/**
 * 提交添加
 */
StoreEmployeeInfoDlg.addSubmit = function() {
	var valid = $("#storeEmployeeConfigInfoForm").valid();
    if(!valid){
    	return;
    }
    this.clearData();
    this.collectData();
   /* if (!this.validate()) {
        return;
    }*/
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
	var valid = $("#storeEmployeeConfigInfoForm").valid();
    if(!valid){
    	return;
    }
    this.clearData();
    this.collectData();
   /* if (!this.validate()) {
        return;
    }*/
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
	$("#storeEmployeeConfigInfoForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
	    	stores: {
	    		required :true
	    	},
	    	mobile: {
	    		required :true,
	    		rangelength:[11,11],
	    		checkMobile:true
	    	},
	    	name: {
	    		required :true,
	    		rangelength:[1,10]
	    	},
	    	password :{
	    		required:true,
	    		maxlength:20
	    		
	    	}
	    },
	    messages: {
	    	stores:{
	    		required:"店铺不能为空"
	    	},
	    	name: {
	    		required:"姓名不能为空",
	    		rangelength:"长度必须小于20个字符"
	    	},
	    	mobile: {
	    		required:"账号不能为空",
	    		rangelength:"长度11个字符"
	    	},
	    	password: {
	    		required:"密码不能为空",
	    		maxlength:"长度必须小于20个字符"
	    	}
	    }
	});
	
	// 自定义正则表达示验证方法
	$.validator.addMethod("checkMobile", function(value, element, params) {
		var checkMobile = /^1[3|5|8]{1}[0-9]{9}$/;
		return this.optional(element) || (checkMobile.test(value));
	}, "请输入正确的手机号码！"); 
	Feng.initValidator("storeEmployeeConfigInfoForm", StoreEmployeeInfoDlg.validateFields);
});
