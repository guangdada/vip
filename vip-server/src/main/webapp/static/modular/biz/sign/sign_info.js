/**
 * 初始化签到规则详情对话框
 */
var SignInfoDlg = {
    signInfoData : {}
};

/**
 * 清除数据
 */
SignInfoDlg.clearData = function() {
    this.signInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SignInfoDlg.set = function(key, value) {
    this.signInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SignInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SignInfoDlg.close = function() {
    parent.layer.close(window.parent.Sign.layerIndex);
}

/**
 * 收集数据
 */
SignInfoDlg.collectData = function() {
	var once = $("#once").prop("checked"); 
    this.set('id').set("times").set("score").set("once",once);
}

/**
 * 提交添加
 */
SignInfoDlg.addSubmit = function() {

    this.clearData();
    
    var valid = $("#signForm").valid();
    if(!valid){
    	return;
    }
    
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sign/add", function(data){
        Feng.success("添加成功!");
        window.parent.Sign.table.refresh();
        SignInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SignInfoDlg.editSubmit = function() {

    this.clearData();
    
    var valid = $("#signForm").valid();
    if(!valid){
    	return;
    }
    
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/sign/update", function(data){
        Feng.success("修改成功!");
        window.parent.Sign.table.refresh();
        SignInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.signInfoData);
    ajax.start();
}

$(function() {
	$("#signForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
	    	times: {
	    		required :true,
	    		digits:true
	    	},
	    	score:{
	    		required :true,
	    		digits:true
	    	}
	    },
	    messages: {
	    }
	});
});
