
$(function(){
	var _li = $("li.mainMenu")
	$("li.mainMenu").click(function(){
		_li.removeClass("slt");
//		$(".subMenu").hide();
		$(this).addClass("slt").children(".subMenu").slideDown();
		})
	})
	
jQuery.dropMenu = function(slt_id,input_id) {
	var inputselect = $(input_id);
	$(slt_id+" span").click(function(){
		var dropList = $(slt_id+" .drop-list");
		if(dropList.css("display")=="none"){
			dropList.slideDown("fast");
		}else{
			dropList.slideUp("fast");
		}
	});
	$(slt_id+" .drop-list li").click(function(){
		var txt = $(this).text();
		$(slt_id+" span").html(txt);
		var value = $(this).attr("li_value");
		inputselect.val(value);
		$(slt_id+" .drop-list").hide();
	});
	$(document).click(function(){
		$(slt_id+" .drop-list").hide();
	});
};
	$(function(){
		$(".window").click(function(){
			$(".zhezhao").fadeIn(300);
			$(".Mask").fadeIn(300);
		})
		$(".zhezhao").click(function(){
			$(".zhezhao").fadeOut(300);
			$(".Mask").fadeOut(300);
		})
		$(".Mask").click(function(){
			$(".zhezhao").fadeOut(300);
			$(".Mask").fadeOut(300);
		})
	})

	$(function(){
		$(".window2").click(function(){
			$(".zhezhao").fadeIn(300);
			$(".Mask2").fadeIn(300);
		})
		$(".zhezhao").click(function(){
			$(".zhezhao").fadeOut(300);
			$(".Mask2").fadeOut(300);
		})
		$(".Mask").click(function(){
			$(".zhezhao").fadeOut(300);
			$(".Mask2").fadeOut(300);
		})
		$(".btn_cancel").click(function(){
			$(".zhezhao").fadeOut(300);
			$(".Mask2").fadeOut(300);
		})
	})
	$(function(){
	  	$(".tableClss ").find("tr td:last").attr("style","border-right:none");
	});