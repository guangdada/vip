/**
 * 初始化邀请规则详情对话框
 */
var ShareInfoDlg = {
    shareInfoData : {}
};

/**
 * 清除数据
 */
ShareInfoDlg.clearData = function() {
    this.shareInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShareInfoDlg.set = function(key, value) {
    this.shareInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ShareInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ShareInfoDlg.close = function() {
    parent.layer.close(window.parent.Share.layerIndex);
}

/**
 * 收集数据
 */
ShareInfoDlg.collectData = function() {
	var couponId = $("#couponId").find("option:selected").val();
    this.set('id').set('point').set('couponId',couponId).set('shareCount');
}

/**
 * 提交添加
 */
ShareInfoDlg.addSubmit = function() {

    this.clearData();
    var valid = $("#shareForm").valid();
    if(!valid){
    	return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/share/add", function(data){
        Feng.success("添加成功!");
        window.parent.Share.table.refresh();
        ShareInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shareInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ShareInfoDlg.editSubmit = function() {

    this.clearData();
    var valid = $("#shareForm").valid();
    if(!valid){
    	return;
    }
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/share/update", function(data){
        Feng.success("修改成功!");
        window.parent.Share.table.refresh();
        ShareInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.shareInfoData);
    ajax.start();
}

$(function() {
	$("#shareForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());
		},
		rules: {
	    	point: {
	    		required :function(){
		        	var couponId = $("#couponId").find("option:selected").val();
		        	return !couponId;
		        },
	    		digits:true
	    	},
	    	couponId:{
	    		required :function(){
		        	var point = $("#point").val();
		        	return !point;
		        }
	    	},
	    	shareCount:{
	    		required :true,
	    		digits:true
	    	}
	    },
	    messages: {
	    }
	});
});
