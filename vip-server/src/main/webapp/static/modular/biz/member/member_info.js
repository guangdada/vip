/**
 * 初始化会员详情对话框
 */
var MemberInfoDlg = {
    memberInfoData : {},
    validateFields : {
		mobile : {
			validators : {
				notEmpty : {
					message : '手机号码不能为空'
				},
				stringLength : {
					min : 11,
					max : 11,
					message : '请输入11位手机号码'
				},
				regexp : {
					regexp : /^1[3|5|8]{1}[0-9]{9}$/,
					message : '请输入正确的手机号码'
				}
			}
		},
		
		point:{
			validators : {
				notEmpty : {
					message : '请输入正确的积分'
				},
				regexp: {
					 regexp: /^(0|[1-9][0-9]*|-[1-9][0-9]*)$/,
			         message: '请输入正确的积分'
				}
			}
		},
		
	}
};

/**
 * 清除数据
 */
MemberInfoDlg.clearData = function() {
	  this.memberInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.set = function(key, value) {
    this.memberInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MemberInfoDlg.close = function() {
    parent.layer.close(window.parent.Member.layerIndex);
}

/**
 * 收集数据
 */
MemberInfoDlg.collectData = function() {
	$("select[id='cards'] option:selected").removeAttr("disabled");
	var cardId = $("select[id='cards'] option:selected").val();
	var sex = $("select[id='sex'] option:selected").val();
    this.set('id').set('name').set('mobile').set('cardId',cardId).set('wxCode').set('sex',sex).set("tips").set('birthday').set('points').set('point');
}

/**
 * 验证数据是否为空
 */
MemberInfoDlg.validate = function () {
    $('#memberForm').data("bootstrapValidator").resetForm();
    $('#memberForm').bootstrapValidator('validate');
    return $("#memberForm").data('bootstrapValidator').isValid();
}
/**
 * 提交添加
 */
MemberInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/member/add", function(data){
        Feng.success("添加成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MemberInfoDlg.editSubmit = function() {
	
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/member/update", function(data){
        Feng.success("修改成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("memberForm", MemberInfoDlg.validateFields);
});
