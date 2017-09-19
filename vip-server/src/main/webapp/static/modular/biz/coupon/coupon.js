/**
 * 优惠券管理管理初始化
 */
var Coupon = {
    id: "CouponTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    qrcode : null  // 二维码
};

/**
 * 初始化表格的列
 */
Coupon.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '优惠券名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '价值(元)', field: 'value', visible: true, align: 'center', valign: 'middle'},
        {title: '领取限制(张)', field: 'quota', visible: true, align: 'center', valign: 'middle'},
        {title: '剩余(张)', field: 'stock', visible: true, align: 'center', valign: 'middle'},
        {title: '生效/失效时间', field: 'start_at', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	return row.start_at + "<br/>" + row.end_at;
        }},
        {title: '状态', field: 'is_invalid', visible: true, align: 'center', valign: 'middle'},
        {title: '领取次数', field: 'get_count', visible: true, align: 'center', valign: 'middle'},
        {title: '领取人数', field: 'get_count_user', visible: true, align: 'center', valign: 'middle'},
        {title: '已使用', field: 'use_count', visible: true, align: 'center', valign: 'middle'},
        {title: '操作', field: 'operate', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
        	var qrcode = '<button type="button"';
        	if(row.is_invalid == "未生效"){
        		qrcode += ' disabled title="优惠券还未生效，不能传播" class="btn btn-default btn-xs"';
        	}else if(row.type == 1){
        		qrcode += ' disabled title="现金券只能兑换，不能传播" class="btn btn-default btn-xs"';
        	}else{
        		qrcode += ' class="btn btn-primary btn-xs"';
        	}
        	qrcode += ' onclick="Coupon.showQr('+row.id+',\''+row.url+'\')"><i class="fa fa-qrcode" aria-hidden="true"></i>&nbsp;发券</button>';
        	return qrcode;
        	
        	/*var qrcode = '<a type="button" class="btn btn-xs btn-info"  onclick="Coupon.showQr('+row.id+',\''+row.url+'\')">推广</a>';
        	var valid = '<a type="button" class="btn btn-xs btn-warning" onclick="Coupon.setValid('+row.id+',\''+row.url+'\')">生效</a>';
        	return qrcode +"&nbsp;" + valid;*/
        }}
    ];
};

/**
 * 复制文本
 * @param id 复制节点Id   该节点不能隐藏
 */
Coupon.copyText = function (id){
    //获取节点
    var n = $("#" + id);
    if(n == null){
        //节点为空直接返回
        return;
    }
    try {
        //取得浏览器的userAgent字符串
        var userAgent = navigator.userAgent;
        //判断Ie
        if (userAgent.indexOf("MSIE") > 0){
            window.clipboardData.setData('text', n.val());
            var copyContent = window.clipboardData.getData("text");
            //判断复制成功，ie复制可选择是否允许
            if(copyContent == n.val()){
            	layer.msg("复制成功。现在您可以粘贴到微信中了。");
                return;
            }
        }else{
            n.select();
            therange = void 0;
            if (n.createTextRange) therange = n.createTextRange();
            therange = therange ? therange : document;
            if (therange.execCommand("Copy")) {
            	layer.msg("复制成功。现在您可以粘贴到微信中了。");
                return;
            }
        }
    } catch (i) {
    }
    layer.msg("您使用的浏览器不支持此复制功能，请使用Ctrl+C或鼠标右键。");
}

Coupon.copyCoponUrl = function(id,url){
	Coupon.copyText("url"+id);
}

/**
 * 显示二维码
 * url 领取链接
 */
Coupon.showQr = function (id,url) {
	Coupon.qrcode.clear();
	Coupon.qrcode.makeCode(url);
	var src = $("#qrcode").find("img").prop("src");
	var content = [];
	content.push('<div>');
	
	content.push('<div class="text-center">');
	content.push('<img src="'+src+'"/>');
	content.push('</div>');
	
	content.push('<div class="form-group top-little">');
	content.push('<div class="col-sm-10 no-left no-right text-center">');
	content.push('<input type="text" class="form-control" id="url'+id+'" readonly value="'+url+'">');
	content.push('</div>');
	content.push('<div class="col-sm-2 no-left no-right text-right">');
	content.push('<a type="button" value="" onclick="Coupon.copyCoponUrl('+id+',\''+url+'\')" class="btn btn-info btn-sm both-little">复制链接</a>');
	content.push('</div>');
	content.push('</div>');
	
	content.push('</div>');
	
	layer.open({
	  title:"二维码",
	  content: content.join(''),
	  area: ['295px', '376px'], //宽高
  	  btn: ''
  	});
}

/**
 * 检查是否选中
 */
Coupon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Coupon.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加优惠券管理
 */
Coupon.openAddCoupon = function () {
    /*var index = layer.open({
        type: 2,
        title: '添加优惠券管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/coupon/coupon_add'
    });
    this.layerIndex = index;
    layer.full(index);*/
	location.href= Feng.ctxPath + '/coupon/coupon_add';
};

/**
 * 打开查看优惠券管理详情
 */
Coupon.openCouponDetail = function () {
    if (this.check()) {
        /*var index = layer.open({
            type: 2,
            title: '优惠券详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/coupon_update/' + Coupon.seItem.id
        });
        this.layerIndex = index;
        layer.full(index);*/
    	location.href= Feng.ctxPath + '/coupon/coupon_update/' + Coupon.seItem.id;
    }
};

/**
 * 删除优惠券管理
 */
Coupon.delete = function () {
    if (this.check()) {
    	if(Coupon.seItem.is_invalid == '已生效'){
    		Feng.info("优惠券已经生效，不能删除");
    		return;
    	}
    	//询问框
    	layer.confirm('确认要删除吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
    		var ajax = new $ax(Feng.ctxPath + "/coupon/delete", function (data) {
	            Feng.success("删除成功!");
	            Coupon.table.refresh();
	        }, function (data) {
	            Feng.error("删除失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("couponId",Coupon.seItem.id);
	        ajax.start();
    		layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 生效优惠券
 */
Coupon.valid = function () {
    if (this.check()) {
    	if(Coupon.seItem.is_invalid == '已生效'){
    		Feng.info("优惠券已经生效");
    		return;
    	}
    	//询问框
    	layer.confirm('优惠券生效后不能修改,确认要生效吗？', {
    	  btn: ['确认','取消'] //按钮
    	}, function(index){
    		var ajax = new $ax(Feng.ctxPath + "/coupon/valid", function (data) {
	            Feng.success("生效成功!");
	            Coupon.table.refresh();
	        }, function (data) {
	            Feng.error("生效失败!" + data.responseJSON.message + "!");
	        });
	        ajax.set("couponId",Coupon.seItem.id);
	        ajax.start();
    		layer.close(index);
    	}, function(){
    	});
    }
};

/**
 * 券号导入
 */
Coupon.importCode = function () {
    if (this.check()) {
    	if(Coupon.seItem.type != '1'){
    		Feng.info("现金券才能导入券号");
    		return;
    	}
    	
        var index = layer.open({
            type: 2,
            title: '券号导入',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/coupon_importCode/' + Coupon.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 查询优惠券管理列表
 */
Coupon.search = function () {
    var queryData = {};
    queryData['couponName'] = $("#name").val();
    queryData['type'] = $("#type").val();
    queryData['storeId'] = $("#storeId").val();
    queryData['isExpired'] = $("#isExpired").val();
    queryData['isInvalid'] = $("#isInvalid").val();
    Coupon.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Coupon.initColumn();
    var table = new BSTable(Coupon.id, "/coupon/list", defaultColunms);
    table.setPaginationType("server");
    Coupon.table = table.init();
    
    // 初始化二维码对象
    Coupon.qrcode = new QRCode("qrcode","test");
});
