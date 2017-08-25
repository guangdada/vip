/**
 * 初始化图片详情对话框
 */
var PictureInfoDlg = {
    pictureInfoData : {}
};

/**
 * 清除数据
 */
PictureInfoDlg.clearData = function() {
    this.pictureInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PictureInfoDlg.set = function(key, val) {
    this.pictureInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PictureInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PictureInfoDlg.close = function() {
	history.back(-1);
    //parent.layer.close(window.parent.Picture.layerIndex);
}

/**
 * 收集数据
 */
PictureInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
PictureInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/picture/add", function(data){
        Feng.success("添加成功!");
        window.parent.Picture.table.refresh();
        PictureInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pictureInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PictureInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/picture/update", function(data){
        Feng.success("修改成功!");
        window.parent.Picture.table.refresh();
        PictureInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.pictureInfoData);
    ajax.start();
}

var options = {
        language: 'zh', //设置语言
        uploadUrl: "${contextPath}/management/sports/peoplePic/uploadInputFile", //上传的地址
        maxFileCount: 5, //表示允许同时上传的最大文件个数
        allowedPreviewTypes: ['image'],
        allowedFileExtensions : ['jpg', 'png','gif'],//接收的文件后缀
        showUpload: true, //是否显示上传按钮
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式             
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
		deleteExtraData:{},
		uploadExtraData: {pid:"${people.id}"},
		showClose:false,
		showCancel:true,
		autoReplace:false,
		fileActionSettings:{
			showZoom:false,
			showUpload:false
		}
    };

$(function() {
	var control = $('#img_file'); 
    control.fileinput(options);
	// 初始化头像上传
/*    var avatarUp = new $WebUpload("logo");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();*/
});
