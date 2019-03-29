/*
$(function(){


    // 适配
    // iframe
    $(".AccountManagement_c_iframe").css("height",$(document).height());
    // 导航高度
    $(".nav_side").css("height",$(document).height()-100);

    // iframe  链接
    $(".AccountManagement").click(function(){

        $(".AccountManagement_c_iframe iframe").attr("src","page/AccountManagement.html")
    })
    $(".ColumnManagement").click(function(){
        $(".AccountManagement_c_iframe iframe").attr("src","page/ColumnManagement.html")
    })
    $(".NewsManagement").click(function(){
        $(".AccountManagement_c_iframe iframe").attr("src","page/NewsManagement.html")
    })


	// pull page   翻页
	$(".pull_page ul li").click(function(){
		var index =$(this).index();
		if( $(this).hasClass("pull_page_df_btn")){
			return;
		}
		$(this).addClass("on").siblings().removeClass("on");

	})

	// nav
	$(".nav_side>div").click(function(){
		var index = $(this).index();
		$(this).addClass("active").siblings().removeClass("active");
		$(".main_fx>div").eq(index).show().siblings().hide();
        if( index == 0 ){
            $(".user_location span").text("账户管理");
        }else if( index == 1){
            $(".user_location span").text("栏目管理");
        }else if( index == 2){
            $(".user_location span").text("新闻管理");
        }
	})




    // 栏目管理 -
    $("#add_column_btn").click(function(){
        $("#add_column").fadeIn(100);
    })
    // 编辑栏目
    $("#edit_column_btn").click(function(){
        $("#edit_column").fadeIn(100);
    })
    // 删除栏目
    $("#delete_column_btn").click(function(){
        $("#delete_column").fadeIn(100);
    })

    // 确定 btn
    $("#add_Account_ok_btn").click(function(){
        $("#add_Account").fadeOut(100);
    })
    $("#user_column_ok_btn").click(function(){
        $("#user_column").fadeOut(100);
    })
    $("#delete_Account_ok_btn").click(function(){
        $("#delete_Account").fadeOut(100);
    })
    $("#ac_Account_ok_btn").click(function(){
        $("#ac_Account").fadeOut(100);
    })
    $("#add_column_ok_btn").click(function(){
        $("#add_column ").fadeOut(100);
    })
    $("#edit_column_ok_btn").click(function(){
        $("#edit_column ").fadeOut(100);
    })

    $("#delete_column_ok_btn").click(function(){
        $("#delete_column").fadeOut(100);
    })


})
*/
