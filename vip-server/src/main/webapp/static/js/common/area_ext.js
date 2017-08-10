/**
 * 
 */
(function($){
	var local = window.location;  
	
	var basePath = local.protocol+"//"+local.host;
	
	$.fn.extend({
		options : {
			target: $("#areaId"),
			provinceId : '',
			cityId : '',
			areaId : '',
			province: $("#province"),
			city: $("#city"),
			area: $("#area")
		},
		// 初始化省市区显示
		initArea : function(opts) {
			// 初始化用户选择面板
			if (jQuery.isPlainObject(opts) || opts == undefined) {
				$.extend(this.options, opts);
			}
			
			//设置参数
			var AREA_OPTIONS = this.options;
			
			var AREA_OPTIONS_TARGET = AREA_OPTIONS.target;
			var AREA_OPTIONS_PROVINCE = AREA_OPTIONS.province;
			var AREA_OPTIONS_CITY = AREA_OPTIONS.city;
			var AREA_OPTIONS_AREA = AREA_OPTIONS.area;
			var AREA_OPTIONS_PROVINCE_ID = AREA_OPTIONS.provinceId;
			var AREA_OPTIONS_CITY_ID = AREA_OPTIONS.cityId;
			var AREA_OPTIONS_AREA_ID = AREA_OPTIONS.areaId;
			
			var removeProvince = function() {
				AREA_OPTIONS_PROVINCE.children("option").each(function() {
					if ($(this).val() != "") {
						$(this).remove();
					}
				});
			}
			
			var removeCity = function() {
				AREA_OPTIONS_CITY.children("option").each(function() {
					if ($(this).val() != "") {
						$(this).remove();
					}
				});
			}
			
			var removeArea = function() {
				AREA_OPTIONS_AREA.children("option").each(function() {
					if ($(this).val() != "") {
						$(this).remove();
					}
				});
			}
			
			var setEffectiveAreaId = function() {
				var areaVal = AREA_OPTIONS_AREA.val();
				var cityVal = AREA_OPTIONS_CITY.val();
				var provinceVal = AREA_OPTIONS_PROVINCE.val();
				
				// console.log("areaVal = " + areaVal + " , cityVal = " + cityVal + " , provinceVal = " + provinceVal);
				if (areaVal != '') {
					AREA_OPTIONS_TARGET.val(areaVal);
					return ;
				}
				
				if (cityVal != '') {
					AREA_OPTIONS_TARGET.val(cityVal);
					return ;
				}
				
				if (provinceVal != '') {
					AREA_OPTIONS_TARGET.val(provinceVal);
					return ;
				}
				AREA_OPTIONS_TARGET.val('');
			}
			
			
			
			
			
			AREA_OPTIONS_PROVINCE.change(function() {
				// 省份列表改变
				$.ajax({
					cache: true,
					type: "GET",
					url: basePath + "/area/nextSelect",
					data: {
						parentId: $(this).val()
					}, 
		            beforeSend: function(request) {
		            },
		            error: function(request) {
		                alert('Connection error');
		            },
		            success: function(json) {
		            	removeArea();
		            	removeCity();
		            	//var json = eval('(' + data + ')');
		            	for (var i = 0; i < json.length; i++) {
		            		if (json[i].id == AREA_OPTIONS_CITY_ID) {
		            			AREA_OPTIONS_CITY.append("<option value='" + json[i].id + "' selected='selected'>" + json[i].name + "</option>");
		            		} else {
			            		AREA_OPTIONS_CITY.append("<option value='" + json[i].id + "'>" + json[i].name + "</option>");
		            		}
		            	}
		            },
		            complete: function(request) {
		            	AREA_OPTIONS_CITY.change();
		            }
				});
				setEffectiveAreaId();
			});
			
			AREA_OPTIONS_CITY.change(function() {
				// 城市列表改变
				$.ajax({
					cache: true,
					type: "GET",
					url: basePath + "/area/nextSelect",
					data: {
						parentId: $(this).val()
					}, 
		            beforeSend: function(request) {
		            },
		            error: function(request) {
		                alert('Connection error');
		            },
		            success: function(json) {
		            	removeArea();
		            	//var json = eval('(' + data + ')');
		            	for (var i = 0; i < json.length; i++) {
		            		if (json[i].id == AREA_OPTIONS_AREA_ID) {
		            			AREA_OPTIONS_AREA.append("<option value='" + json[i].id + "' selected='selected'>" + json[i].name + "</option>");
		            		} else {
			            		AREA_OPTIONS_AREA.append("<option value='" + json[i].id + "'>" + json[i].name + "</option>");
		            		}
		            	}
		            },
		            complete: function(request) {
		            	AREA_OPTIONS_AREA.change();
		            }
				});	
				
				setEffectiveAreaId();
			});
			
			AREA_OPTIONS_AREA.change(function() {
				setEffectiveAreaId();
			});
			
			// 省份列表初始
			$.ajax({
				cache: true,
				type: "GET",
				url: basePath + "/area/listSelect",
		        beforeSend: function(request) {
		        },
		        error: function(request) {
		            alert('Connection error');
		        },
		        success: function(json) {
		        	//var json = eval('(' + data + ')');
		        	for (var i = 0; i < json.length; i++) {
		        		if (json[i].id == AREA_OPTIONS_PROVINCE_ID) {
		        			AREA_OPTIONS_PROVINCE.append("<option value='" + json[i].id + "' selected='selected'>" + json[i].name + "</option>");
		        		} else {
		        			AREA_OPTIONS_PROVINCE.append("<option value='" + json[i].id + "'>" + json[i].name + "</option>");
		        		}
		        	}
		        },
		        complete: function(request) {
		        	AREA_OPTIONS_PROVINCE.change();
		        }
			});	
		}
	});
	
})(jQuery);
