/**
 * 初始化券码管理详情对话框
 */
var CouponCodeInfoDlg = {
    couponCodeInfoData : {}
};

/**
 * 清除数据
 */
CouponCodeInfoDlg.clearData = function() {
    this.couponCodeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponCodeInfoDlg.set = function(key, val) {
    this.couponCodeInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponCodeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CouponCodeInfoDlg.close = function() {
    parent.layer.close(window.parent.CouponCode.layerIndex);
}

/**
 * 收集数据
 */
CouponCodeInfoDlg.collectData = function() {
    this.set('id').set('num');
}

/**
 * 提交添加
 */
CouponCodeInfoDlg.addSubmit = function() {

    this.clearData();
    
    var valid = $("#couponCodeForm").valid();
    if(!valid){
    	return;
    }
    
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponCode/add", function(data){
        Feng.success("添加成功!");
        window.parent.CouponCode.table.refresh();
        CouponCodeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponCodeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CouponCodeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/couponCode/update", function(data){
        Feng.success("修改成功!");
        window.parent.CouponCode.table.refresh();
        CouponCodeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponCodeInfoData);
    ajax.start();
}

$(function() {
	$("#couponCodeForm").validate({
		errorPlacement: function(error, element) {
			error.appendTo(element.parent());  
		},
		rules: {
	    	num: {
	    		required :true,
	    		digits:true
	    	}
	    }
	});
});
