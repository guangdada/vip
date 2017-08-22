/**
 * Created by Jill on 16/11/13.
 */
$(document).ready(function(){
	//大学
	$("._current-college").click(function(){
		$("._sorting-medical").removeClass("fixed-top");
		$("._sorting-address").removeClass("fixed-top");
		if($("._sorting-college").hasClass("fixed-top")){
			$("._sorting-college").removeClass("fixed-top");
            $(this).removeClass("current");
            $('._navbar').attr('style','position: fixed;top:0;');
            $("._current-college span").html($(this).html());
		}else{
			$("._sorting-college").addClass("fixed-top");
            $(this).addClass("current");
            $("._current-address").removeClass("current");
            $("._current-medical").removeClass("current");
            $('._navbar').attr('style','position: fixed;top:0;');
//          var provinces=data.province;
            var h="";
            for(var i =0; i<10;i++){
                h+="<li data-ind='"+i+"'>"+"la"+[i]+"</li>";
            }
            $('.college_first').html(h);
            $("._current-college span").html($(this).html());
		}
	});
	//点击某大学，发生的点击事件
    $(".college_first").delegate("li","click",function(){
        /*if($(this).attr("data-ind")==0){
            var parentVal=$(this).parent().siblings('.medical_first').find('.current').html();
            $("._current-medical span").html(parentVal);
        }
        else{
            $("._current-medical span").html($(this).html());
        }*/
        $("._sorting-medical").removeClass("fixed-top");
        $(this).removeClass("current");
        $('._navbar').attr('style', 'position: fixed;top:0;');
        $("._current-college").removeClass("current");
        $('._sorting-college').removeClass("fixed-top");
        $("._current-college span").html($(this).html());
    });
	
    //点击分类导航-当前位置
    $("._current-address").click(function(){
        $("._sorting-medical").removeClass("fixed-top");
        $("._sorting-college").removeClass("fixed-top");
        if($("._sorting-address").hasClass("fixed-top")){
            $("._sorting-address").removeClass("fixed-top");
            $(this).removeClass("current");
            $('._navbar').attr('style','position: fixed;top:0;');
        }else{
            $("._sorting-address").addClass("fixed-top");
            $(this).addClass("current");
            $("._current-medical").removeClass("current");
            $("._current-college").removeClass("current");
            
            $('._navbar').attr('style','position: fixed;top:0;');
            var provinces=data.province;
            var h="";
            for(var i =0; i<provinces.length;i++){
                h+="<li data-ind='"+i+"'>"+provinces[i]+"</li>";
            }
            $('.address_first').html(h);
        }
    })
    //点击省级
    $(".address_first").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
            $("._current-address span").html($(this).html());
            $(".address_second").css("left","100%");
            $(this).removeClass("current");
            $('._navbar').attr('style', 'position: fixed;top:0;');
            $("._sorting-address").removeClass("fixed-top");
            $("._current-address").removeClass("current");
            return;
        }
        if($(this).hasClass("current")){
            $(this).removeClass("current");
            $(".address_second").css("left","33%");
        }else{
            $(".address_first li").removeClass("current");
            $(this).addClass("current");
            $(".address_second").css("left","33%");
            var citys=data.city;
            var p = parseInt($(this).attr("data-ind"));
            var hn="";
            for(var j =0; j<citys[p].length;j++){
                hn+="<li data-indp='"+p+"' data-ind='"+j+"'>"+citys[p][j]+"</li>";
            }
            $('.address_second').html(hn);
        }
    });
    
    //点击市
    $(".address_second").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
        	alert("dd");
            $("._current-address span").html($(this).html());
          /*  $(".address_second").css("left","100%");*/
            $(this).removeClass("current");
            $('._navbar').attr('style', 'position: fixed;top:0;');
            $("._sorting-address").removeClass("fixed-top");
            $("._current-address").removeClass("current");
            return;
        }
        if($(this).hasClass("current")){
            $(this).removeClass("current");
            $(".address_third").css("left","67%");
        }else{
            $(".address_second li").css("background","white");
            $(this).css("background","#eee");
            $(this).addClass("select");
          /*  $(".address_second li").removeClass("");
            $(this).addClass("current");*/
            $(".address_third").css("left","67%");
            var areas=data.area;
            var p = parseInt($(this).attr("data-ind"));
            var y = parseInt($(this).attr("data-indp"));
            var hn="";
           /* alert(areas[1][1].length);*/
           
           /* for(var j =0; j<areas[p].length;j++){
                hn+="<li data-ind='"+j+"'>"+areas[p][j]+"</li>";
            }*/
            /*alert(p);
            alert(y);*/
            for(var j =0; j<areas[y][p-1].length;j++){
                hn+="<li data-ind='"+j+"'>"+areas[y][p-1][j]+"</li>";
            }
            $('.address_third').html(hn);
        }
    });
    
   //点击区
    $(".address_third").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
            var parentVal=$(this).parent().siblings('.address_second').find('.select').html();
            $("._current-address span").html(parentVal);
        }
        else{
            $("._current-address span").html($(this).html());
        }
        $("._sorting-address").removeClass("fixed-top");
        $(this).removeClass("current");
        $('._navbar').attr('style', 'position: fixed;top:0;');
        $("._current-address").removeClass("current");
       /* $(".address_second").css("left","100%");*/
    });
   /* //点击市
    $(".address_second").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
            var parentVal=$(this).parent().siblings('.address_first').find('.current').html();
            $("._current-address span").html(parentVal);
        }
        else{
            $("._current-address span").html($(this).html());
        }
        $("._sorting-address").removeClass("fixed-top");
        $(this).removeClass("current");
        $('._navbar').attr('style', 'position: fixed;top:0;');
        $("._current-address").removeClass("current");
        $(".address_second").css("left","100%");
    });*/
//点击分类导航-当前科室
    $("._current-medical").click(function(){
        $("._sorting-address").removeClass("fixed-top");
        $("._sorting-college").removeClass("fixed-top");
        if($("._sorting-medical").hasClass("fixed-top")){
            $("._sorting-medical").removeClass("fixed-top");
            $(this).removeClass("current");
            $('._navbar').attr('style','position: fixed;top:0;');
        }else{
            $("._sorting-medical").addClass("fixed-top");
            $(this).addClass("current");
            $("._current-address").removeClass("current");
            $("._current-college").removeClass("current");
            $('._navbar').attr('style','position: fixed;top:0;');
            var departments=data.department;
            var h="";
            for(var i =0; i<departments.length;i++){
                h+="<li data-ind='"+i+"'>"+departments[i]+"</li>";
            }
            $('.medical_first').html(h);
        }
    })
    //点击科室
    $(".medical_first").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
            $("._current-medical span").html($(this).html());
            $(".address_second").css("left","100%");
            $(this).removeClass("current");
            $('._navbar').attr('style', 'position: fixed;top:0;');
            $("._sorting-medical").removeClass("fixed-top");
            $("._current-medical").removeClass("current");
            return;
        }
        if($(this).hasClass("current")){
            $(this).removeClass("current");
            $(".medical_second").css("left","50%");
        }else{
            $(".medical_first li").removeClass("current");
            $(this).addClass("current");
            $(".medical_second").css("left","50%");
            var p = parseInt($(this).attr("data-ind"));
            var subDepartments=data.subDepartment;
            var hn="";
            for(var j =0; j<subDepartments[p].length;j++){
                hn+="<li data-ind='"+j+"'>"+subDepartments[p][j]+"</li>";
            }
            $('.medical_second').html(hn);
        }
    });
    //点击科室详情选项
    $(".medical_second").delegate("li","click",function(){
        if($(this).attr("data-ind")==0){
            var parentVal=$(this).parent().siblings('.medical_first').find('.current').html();
            $("._current-medical span").html(parentVal);
        }
        else{
            $("._current-medical span").html($(this).html());
        }
        $("._sorting-medical").removeClass("fixed-top");
        $(this).removeClass("current");
        $('._navbar').attr('style', 'position: fixed;top:0;');
        $("._current-medical").removeClass("current");
        $(".address_second").css("left","100%");
    });
});