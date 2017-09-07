/**
 * 
 * @param path 请求根路径
 * @param title 标题
 * @param link 链接
 * @param desc 描述
 * @param imgUrl 图片路径
 * @param id 新闻id
 * @param username 用户名
 * @param headerImage 头像
 */
function getShareConfig(path, title, link, desc, imgUrl, id, username, headerImage) {
	var url = link || location.href.split('#')[0];
	$.ajax({
		url:path+"/web/share/getWxConfig.jhtml",
		dataType : "json",
	    async : false,
	    data:{url:url},
	    success : function(data) {
	        wx.config({
	            appId: data.appId,
	            timestamp: data.timestamp,
	            nonceStr: data.nonceStr,
	            signature: data.signature,
	            jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage']
	        });
	        
	        //修改这里
			var shareData = {
				title : "来玩BALL-" + title,
				link : url,
				img_url : imgUrl,
				desc : desc,
				id : id,
				username : username,
				headerImage : headerImage
			};

			//所有微信事件都要写到ready里
			wx.ready(function() {
				//分享给朋友
				wx.onMenuShareAppMessage({
					title : shareData.title,
					desc : shareData.desc,
					link : shareData.link,
					imgUrl : shareData.img_url,
					trigger : function(res) {
						//alert('用户点击发送给朋友');
					},
					success : function(res) {
						//alert('已分享');
						saveIntefral(path, shareData.id,shareData.username,shareData.headerImage,"friend");
					},
					cancel : function(res) {
						//alert('已取消');
					},
					fail : function(res) {
						//alert(JSON.stringify(res));
					}
				});

				//分享到朋友圈，注意去掉回调里的alert
				wx.onMenuShareTimeline({
					title : shareData.title,
					desc : shareData.desc,
					link : shareData.link,
					imgUrl : shareData.img_url,
					trigger : function(res) {
						//alert('用户点击分享到朋友圈');
					},
					success : function(res) {
						//alert('已分享');
						saveIntefral(path, shareData.id,shareData.username,shareData.headerImage,"friendCircle");
					},
					cancel : function(res) {
						//alert('已取消');
					},
					fail : function(res) {
						//alert(JSON.stringify(res));
					}
				});
			}); //end of ready
	    }
	});
}

function saveIntefral(path, articleId, username, headerImage, type){
	$.post(
		path+"/web/share/save.jhtml", 
		{articleId : articleId, name : username, headerImage : headerImage, type : type}, 
		function(result) {
		  if(result.code == 200) {
			  top.location.href=result.url;
		  }else{
			  alert('分享失败');
		  }
	  });
}