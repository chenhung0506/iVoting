<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
   	<div id="loading-mask">
		<div id="divMaskFrame">
			<div id="divProgress">
				<img src="/images/loading.gif">
				<div class="load-text">資料處理中</div>
			</div>
		</div>
    </div>
<script>
	showMessage('${message}');
	
	$("#source_select").change(function() {
		$("#source_form").submit();
	});
	
	//show process message
	function showMessage(message) {
		if( message !== undefined && message !== null && message !== 'null' && message !== '') {
			console.log(message);
			alert(message);
		}
	}
    var loadingProgressTimer = function() {
        loadingCount++;

        if ( loadingCount == 30 ) {
            $('#divProgress').append('<div class="more-info load-text">資料較為龐大，請耐心等待！</div>');
        } else if ( loadingCount == 60 ) {
            $('#divProgress .more-info').html('就快好了！請再等一下下！！');
        }
    };

    var stopLoadingTimer = function() {
        clearInterval(loadingTimer);
        loadingCount = 0;
        $('#divProgress .more-info').remove();
    };	
	//////////////////////////////
	HideProgressBar();
	var loadingTimer;
    var loadingCount = 0;

    //顯示讀取遮罩
    function ShowProgressBar() {
        displayProgress();
        displayMaskFrame();
    }

    // 隱藏讀取遮罩
    function HideProgressBar() {
        var progress = $('#divProgress');
        var maskFrame = $('#divMaskFrame');
        progress.hide();
        maskFrame.hide();
        stopLoadingTimer();
    }
    // 顯示讀取畫面
    function displayProgress() {
        var w = $(document).width();
        var h = $(window).height();
        var progress = $('#divProgress');
//         progress.css({ "z-index": 999999, "top": (h / 2) - (progress.height() / 2), "left": (w / 2) - (progress.width() / 2) });
        progress.show();

        loadingTimer = setInterval(function(){ loadingProgressTimer() },1000);
    }
    // 顯示遮罩畫面
    function displayMaskFrame() {
        var w = $(window).width();
        var h = $(document).height();
        var maskFrame = $("#divMaskFrame");
//         maskFrame.css({ "z-index": 999998, "opacity": 0.7, "width": w, "height": h });
        maskFrame.show();
    }

    var isBlock = true, $isAlert = false;

    $(document).ajaxStart(function(){
        if (isBlock) {
            ShowProgressBar();
        }
    }).ajaxStop(function(){
        if (isBlock) {
            HideProgressBar();
        }
    }).ajaxError(function(event, jqxhr, settings, thrownError) {
    	var $status = jqxhr.status;
    	var reloginUrl = '/LMS/ajaxReLogin';
    	if ($status != 200) {
    		$isAlert = false;
    		if (!$isAlert) {
    			$isAlert = true;
    			if ($status == 302 || $status == 12150) {
    				alert("S閒置時間過久或目前已登出，請重新登入！");
    				var newwin = window.open();
    				setTimeout(function(){
    					newwin.location = reloginUrl;
    				}, 100);
    			} else if ($status == 0) {
    				if ($.browser.msie) {
    					alert("S閒置時間過久或目前已登出，請重新登入！");
    					var newwin = window.open();
    					setTimeout(function(){
        					newwin.location = reloginUrl;
        				}, 100);
    					//var newwindow = window.open("/LMS/ajaxReLogin", "_blank", "resizable=yes, scrollbars=yes, titlebar=yes, width=800, height=900, top=10, left=10");
    				}
    			} else {
    				alert("S系統異常，請稍後再試！");
    			}
    		}
    	}
    });
    
    HideProgressBar();
</script>