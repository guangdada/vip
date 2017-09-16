$(function() {
	var couponId = $("#couponId").val();
	var options = {
			language: 'zh', //设置语言
			showPreview: false,
			maxFileSize:5120, //单位kb,5M
			uploadUrl: Feng.ctxPath + '/coupon/importCode/'+ couponId,
			showUpload: true, //是否显示上传按钮
		    allowedFileExtensions: ["xls", "xlsx"],
		    elErrorContainer: "#errorBlock"
	};
	
	$("#coupon_code_file").fileinput(options)
	.on("filebatchselected", function(event, files) {
        //$(this).fileinput("upload");
    })
    .on("fileuploaded", function(event, data) {
        if(data.response)
        {
        	var code = data.response.code;
        	var message = data.response.message;
        	if(code != '200'){
        		$("#errorBlock").html(message).show();
        	}else{
        		$("#errorBlock").html(message).show();
        	}
        }
    });
});
