/**
 * 初始化积分管理详情对话框
 */
var PointInfoDlg = {
    pointInfoData : {},
    validateFields: {
        points: {
            validators: {
                notEmpty: {
                    message: '奖励分值不能为空'
                }
            }
        },
        pointsLimit1:{
            validators: {
                
            }
        },
        pointsLimit2:{
            validators: {
                
            }
        }
    }
};

/**
 * 清除数据
 */
PointInfoDlg.clearData = function() {
    this.pointInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PointInfoDlg.set = function(key, val) {
    this.pointInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PointInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PointInfoDlg.close = function() {
    parent.layer.close(window.parent.Point.layerIndex);
}

/**
 * 收集数据
 */
PointInfoDlg.collectData = function() {
    this.set('id').set('points').set('pointsLimitTemp').set('ruleType');
}

/**
 * 验证数据是否为空
 */
PointInfoDlg.validate = function () {
    $('#pointInfoForm').data("bootstrapValidator").resetForm();
    $('#pointInfoForm').bootstrapValidator('validate');
    return $("#pointInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 验证其他需要条件判断的项目
 */
PointInfoDlg.validateOther = function (){
	$("#ruleTypeBlock").hide();
	$("#pointsLimit1Block").hide();
	$("#pointsLimit2Block").hide();
	var ruleType = $("input[name='ruleType']:checked");
	if(!ruleType || ruleType.length == 0){
		$("#ruleTypeBlock").show();
	}else{
		var value = ruleType.val();
		if(value == '0'){
			$("#ruleType").val("0");
			$("#pointsLimitTemp").val("0");
			return true;
		}else if(value == '1'){
			var pointsLimit1 = $("#pointsLimit1").val();
			if(!pointsLimit1){
				$("#pointsLimit1Block").show();
				return false;
			}else{
				$("#ruleType").val("1");
				$("#pointsLimitTemp").val(pointsLimit1);
				return true;
			}
		}else if(value == '2'){
			var pointsLimit2 = $("#pointsLimit2").val();
			if(!pointsLimit2){
				$("#pointsLimit2Block").show();
				return false;
			}else{
				$("#ruleType").val("2");
				$("#pointsLimitTemp").val(pointsLimit2);
				return true;
			}
		}
	}
}

/**
 * 提交添加
 */
PointInfoDlg.addSubmit = function() {
    this.clearData();
    if (!this.validate() || !this.validateOther()) {
        return;
    }
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/point/add", function(data){
        Feng.success("添加成功!");
        window.parent.Point.table.refresh();
        PointInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pointInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PointInfoDlg.editSubmit = function() {

    this.clearData();
    if (!this.validate() || !this.validateOther() ) {
        return;
    }
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/point/update", function(data){
        Feng.success("修改成功!");
        window.parent.Point.table.refresh();
        PointInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pointInfoData);
    ajax.start();
}

PointInfoDlg.changeRule = function (ruleType){
	if(ruleType == 0){
		$("#ruleType0").prop("checked","checked");
		$("#ruleType1").removeProp("checked");
		$("#ruleType2").removeProp("checked");
		$("#pointsLimit1").val("");
		$("#pointsLimit2").val("");
	}else if(ruleType == 1){
		$("#ruleType1").prop("checked","checked");
		$("#ruleType0").removeProp("checked");
		$("#ruleType2").removeProp("checked");
		$("#pointsLimit2").val("");
	}else if(ruleType == 2){
		$("#ruleType2").prop("checked","checked");
		$("#ruleType1").removeProp("checked");
		$("#ruleType0").removeProp("checked");
		$("#pointsLimit1").val("");
	}
}

$(function() {
	Feng.initValidator("pointInfoForm", PointInfoDlg.validateFields,{});
});
