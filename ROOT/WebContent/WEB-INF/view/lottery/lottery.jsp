<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<html lang="zh-TW">
<head>
  <meta charset="utf-8">  
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <META HTTP-EQUIV="EXPIRES" CONTENT="0">
  <META HTTP-EQUIV="KIBEN" CONTENT="NO-CACHE">
  <title>2019臺北市參與式預算提案投票活動</title>
  <link href="/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="/js/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="/css/voting.css" rel="stylesheet">
  <link rel="stylesheet" href="/css/loading.css" />
  <script src="js/jquery/jquery-3.4.1.min.js"></script>
  <script src="/js/jquery.json-2.3.min.js"></script>
  <script src="/js/jquery-easing/jquery.easing.min.js"></script>
  <script src="/js/bootstrap/js/bootstrap.bundle.min.js"></script>
</head>

<body>
<script type="text/javascript" language="javascript">
$(document).ready(function() {
	var zipArray = new Array('100' ,'103', '104', '105', '106', '108', '110', '111', '112', '114', '115', '116');
	var reward1Count = 0;
	var reward2Count = 0;
	var reward3Count = 0;
	var content1 = "";
	var content2 = "";
	var content3 = "";

    var rewtab = "";
	var rewtabList = ['<li class="nav-item"><a class="nav-link" id="1-tab" data-toggle="tab" href="#tab-1" role="tab" aria-controls="1" aria-selected="true">1-20</a></li>'
	                 ,'<li class="nav-item"><a class="nav-link" id="2-tab" data-toggle="tab" href="#tab-2" role="tab" aria-controls="2" aria-selected="false">21-40</a></li>'
	                 ,'<li class="nav-item"><a class="nav-link" id="3-tab" data-toggle="tab" href="#tab-3" role="tab" aria-controls="3" aria-selected="false">41-60</a></li>'
	                 ,'<li class="nav-item"><a class="nav-link" id="4-tab" data-toggle="tab" href="#tab-4" role="tab" aria-controls="4" aria-selected="false">61-80</a></li>'
	                 ,'<li class="nav-item"><a class="nav-link" id="5-tab" data-toggle="tab" href="#tab-5" role="tab" aria-controls="5" aria-selected="false">81-100</a></li>'];

    var rewtabContent = "";
    var rewtabContentList = ['<div class="tab-pane fade" id="tab-1" role="tabpanel" aria-labelledby="1-tab"><table class="table"><thead><tr><th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th></tr></thead><tbody id="bodyReward3-0"></tbody></table></div>'
						    ,'<div class="tab-pane fade" id="tab-2" role="tabpanel" aria-labelledby="2-tab"><table class="table"><thead><tr><th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th></tr></thead><tbody id="bodyReward3-1"></tbody></table></div>'
						    ,'<div class="tab-pane fade" id="tab-3" role="tabpanel" aria-labelledby="3-tab"><table class="table"><thead><tr><th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th></tr></thead><tbody id="bodyReward3-2"></tbody></table></div>'
						    ,'<div class="tab-pane fade" id="tab-4" role="tabpanel" aria-labelledby="4-tab"><table class="table"><thead><tr><th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th></tr></thead><tbody id="bodyReward3-3"></tbody></table></div>'
						    ,'<div class="tab-pane fade" id="tab-5" role="tabpanel" aria-labelledby="5-tab"><table class="table"><thead><tr><th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th></tr></thead><tbody id="bodyReward3-4"></tbody></table></div>'];

    function displayTab(number){
    	$(".nav-link").removeClass("active");
    	$(".nav-link").attr("aria-selected",false);
    	$(".tab-pane").removeClass("show").removeClass("active");
//     	setTimeout(function() {
    	$("#"+number+"-tab").addClass("active");
    	$("#"+number+"-tab").attr("aria-selected",true);
    	$("#tab-"+number).attr("class","tab-pane fade active show in");    	
//     	}, 1000);
    };
    
    function drawContent(data, content, obj){
    	data.forEach(function(element){
    		content = content + "<tr><td>"+element['NAME'].replace(/.(?=.)/g, '*')+"</td><td>"+element['PHONE'].replace(/(\d{3})(\d{4})/, '$1****')+"</td><td>"+element['DISTRICT']+"</td></tr>";
    	});
    	obj.html(content);
    	return content;
    };

    function view3Init(data){
    	var count = Math.ceil(data['award3'].length/20);
    	for (j = 1; j < count+1; j++) {
			$("#rewtab").html(rewtab + rewtabList[reward3Count]);
			$("#rewtabContent").html(rewtabContent + rewtabContentList[reward3Count]);
			var content = "";
			for (i = (20*j-20) ; i < (j==count?data['award3'].length:(20*j)); i++) {
				content = content + "<tr><td>"+data['award3'][i]['NAME'].replace(/.(?=.)/g, '*')+"</td><td>"+data['award3'][i]['PHONE'].replace(/(\d{3})(\d{4})/, '$1****')+"</td><td>"+data['award3'][i]['DISTRICT']+"</td></tr>";
			};
			$("#bodyReward3-"+reward3Count).html(content);
			rewtab = $("#rewtab").html();
			rewtabContent = $("#rewtabContent").html();
			reward3Count++;
    	};
    	displayTab(reward3Count);
    };

	function viewInit(){
		$.ajax({
			url: '/go/queryAward',
			type: 'POST',
			dataType:'json',
			success: function(data) {
				content1 = drawContent(data['award1'], content1, $("#bodyReward1"));
				content2 = drawContent(data['award2'], content1, $("#bodyReward2"));
				reward1Count = data['award1'].length;
				reward2Count = data['award2'].length;
				view3Init(data);
			},
			error: function(data){
				alert(data.eMessage);
		  	}
		});
	};

	viewInit();

	$("#drawReward1").on("click", function () {
		if ( reward1Count++ > 1 ) {
			if (confirm("頭獎名額已抽完，確定要繼續抽獎?")) {
			} else {
				return;
			};
		};
		$.ajax({
			url: '/go/drawReward',
			type: 'POST',
			dataType:'json',
			data: {
				prize : "1",
				limit: "1"
			},
			success: function(data) {
				content1 = drawContent(data, content1, $("#bodyReward1"));
			},
			error: function(data){
				alert(data.eMessage);
		  	}
		});
	});

	$("#drawReward2").on("click", function () {
		if ( reward2Count > 11 ) {
			if (confirm("二獎名額已抽完，確定要繼續抽獎?")) {
			} else {
				return;
			};
		};
		$.ajax({
			url: '/go/drawReward',
			type: 'POST',
			dataType:'json',
			data: {
				prize : "2",
				limit: "1",
				zip: reward2Count > 11 ? zipArray[Math.floor( Math.random() * zipArray.length )] : zipArray[reward2Count++]
			},
			success: function(data) {
				content2 = drawContent(data, content2, $("#bodyReward2"));
			},
			error: function(data){
				alert(data.eMessage);
		  	}
		});
	});

	$("#drawReward3").on("click", function () {
		if ( reward3Count > 4 ) {
			alert("三獎名額皆已抽完");
			return;
		};
		content3 = "";
		$.ajax({
			url: '/go/drawReward',
			type: 'POST',
			dataType:'json',
			data: {
				prize : "3",
				limit: "20"
			},
			success: function(data) {
				$("#rewtab").html(rewtab + rewtabList[reward3Count]);
				$("#rewtabContent").html(rewtabContent + rewtabContentList[reward3Count]);
				content3 = drawContent(data, content3, $("#bodyReward3-"+reward3Count));
				rewtab = $("#rewtab").html();
				rewtabContent = $("#rewtabContent").html();
				displayTab(++reward3Count);				
			},
			error: function(data){
				alert(data.eMessage);
		  	}
		});
	});

	$("#reset1").on("click", function () {
		if (confirm("確定清掉[頭獎]得獎名單重新抽獎?")) {
			$.ajax({
				url: '/go/resetFlag',
				type: 'POST',
				dataType:'json',
				data: {
					prize : "1",
				},
				success: function(data) {
					$("#bodyReward1").html("");
					reward1Count = 0;
					content1="";
				},
				error: function(data){
					alert(data.eMessage);
			  	}
			});
		} else {
			return;
		};
	});

	$("#reset2").on("click", function () {
		if (confirm("確定清掉[二獎]得獎名單重新抽獎?")) {
			$.ajax({
				url: '/go/resetFlag',
				type: 'POST',
				dataType:'json',
				data: {
					prize : "2",
				},
				success: function(data) {
					$("#bodyReward2").html("");
					reward2Count = 0;
					content2="";
				},
				error: function(data){
					alert(data.eMessage);
			  	}
			});
		} else {
			return;
		};

	});

	$("#reset3").on("click", function () {
		if (confirm("確定清掉[三獎]得獎名單重新抽獎?")) {
			$.ajax({
				url: '/go/resetFlag',
				type: 'POST',
				dataType:'json',
				data: {
					prize : "3",
				},
				success: function(data) {
					$("#rewtab").html("");
					rewtab ="";
					$("#rewtabContent").html("");
					rewtabContent="";
					reward3Count = 0;
					content3="";
				},
				error: function(data){
					alert(data.eMessage);
			  	}
			});
		} else {
			return;
		};
	});
});
</script>
  <!-- Header -->
  <header class="masthead bg-yellow text-white text-center">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <img class="img-fluid mb-5 d-block mx-auto" src="/images/voting.png?v20190611" alt="">
        </div>
      </div>
    </div>
  </header>
  
  <!-- 三獎 -->
  <section class="" id="rewthree">
    <div class="container">
      <div class="row justify-content-between reward">
        <div class="col-md-4 text-center">
            <h2 class="text-center text-uppercase text-secondary mb-0">三獎</h2>
            <hr class="star-dark mb-5">
            <div class="form-group">
              <button type="button" class="btn btn-info btn-block" id="drawReward3">抽獎</button>
              <button type="button" class="btn btn-default btn-small" id="reset3"> <i class="fas fa-sync-alt"></i></button>
              <img class="img-fluid mb-5 d-block mx-auto" src="/images/adw03.png?v20190613" alt="">
            </div>
        </div>
        <div class="col-md-7 text-center">
          <ul class="nav nav-tabs" id="rewtab" role="tablist">
          </ul>
          <div class="tab-content" id="rewtabContent">
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 二獎 -->
  <section class="bg-primary text-white mb-0" id="rewtwo">
    <div class="container">
      <div class="row justify-content-between reward">
        <div class="col-md-4 text-center">
            <h2 class="text-center text-uppercase text-white">二獎</h2>
            <hr class="star-light mb-5">
            <div class="form-group">
              <button type="button" class="btn btn-warning btn-block" id="drawReward2">抽獎</button>
              <button type="button" class="btn btn-default btn-small" id="reset2"> <i class="fas fa-sync-alt"></i></button>
              <img class="img-fluid mb-5 d-block mx-auto" src="/images/adw02.png?v20190613" alt="">
            </div>
        </div>
        <div class="col-md-7 text-center">
          <!--table-->
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th>
                </tr>
              </thead>
			  <tbody id="bodyReward2">
              </tbody>
            </table>
          <!--end table-->
        </div>
      </div>
    </div>
  </section>

  <!-- 頭獎 -->
  <section id="rewone">
    <div class="container">
      <div class="row justify-content-between reward">
        <div class="col-md-4 text-center">
            <h2 class="text-center text-uppercase text-secondary mb-0">頭獎</h2>
            <hr class="star-dark mb-5">
            <div class="form-group">
              <button type="button" class="btn btn-success btn-block" id="drawReward1">抽獎</button>
              <button type="button" class="btn btn-default btn-small" id="reset1"> <i class="fas fa-sync-alt"></i></button>
			  <img class="img-fluid mb-5 d-block mx-auto" src="/images/adw01.png" alt="">
            </div>
        </div>
        <div class="col-md-7 text-center">
          <!--table-->
            <table class="table">
              <thead>
                <tr>
                  <th scope="col">姓名</th><th scope="col">電話</th><th scope="col">區域</th>
                </tr>
              </thead>
              <tbody id="bodyReward1">

              </tbody>
            </table>
          <!--end table-->
        </div>
      </div>
    </div>
  </section>

  <!-- Footer -->
  <footer class="footer text-center">
    <div class="container">
      <div class="row">
        <div class="col-md-4 mb-5 mb-lg-0">
          <h4 class="text-uppercase mb-4">主辦單位</h4>
          <p class="lead mb-0">臺北市政府</p>
        </div>
        <div class="col-md-4 mb-5 mb-lg-0">
          <h4 class="text-uppercase mb-4">承辦單位</h4>
          <p class="lead mb-0">臺北市政府民政局及各區公所</p>
        </div>
        <div class="col-md-4 mb-5 mb-lg-0">
          <h4 class="text-uppercase mb-4">協辦單位</h4>
          <p class="lead mb-0">臺北市政府研究發展考核委員會</p>
          <p class="lead mb-0">臺北市政府資訊局</p>
          <p class="lead mb-0">臺北市政府教育局</p>
          <p class="lead mb-0">臺北市大眾捷運股份有限公司</p>
        </div>
      </div>
    </div>
  </footer>

  <div class="copyright py-4 text-center text-white">
    <div class="container">
      <small>Copyright &copy; 2019</small>
    </div>
  </div>

  <!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
  <div class="scroll-to-top d-lg-none position-fixed ">
    <a class="js-scroll-trigger d-block text-center text-white rounded" href="#page-top">
      <i class="fa fa-chevron-up"></i>
    </a>
  </div>

  <!-- Bootstrap core JavaScript -->
   	<div id="loading-mask">
		<div id="divMaskFrame">
			<div id="divProgress">
				<img src="/images/tenor.gif?v20190614" class="img-fluid" style="max-width:500px">
				<div class="load-text">抽取幸運民眾</div>
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
            $('#divProgress').append('<div class="more-info load-text">就快好了！請再等一下下！</div>');
        } else if ( loadingCount == 60 ) {
            $('#divProgress .more-info').html('就快好了！請再等一下下下！！！');
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
    	var reloginUrl = '/';
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
</body>
</html>