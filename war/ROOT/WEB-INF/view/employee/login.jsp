<?xml version="1.0" encoding="UTF-8"?>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>	
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
<title>Insert title here</title>
</head>
<body class="yellow">
	<c:if test="${param.error != null}">
        <h2>${param.error}</h2>
    </c:if>
	<form  action="${projectUrl}/validateCaptcha" method="post">
		<div class="wrap-max body-wrap">
			<div class="area-body main-body">
				<div class="login-area">
					<div class="login-box">
						<div class="title"> ${projectName}後台管理介面</div>
						<div class="login-block">
							<div class="title">帳號：</div>
							<input tabindex="1" autocomplete="off" name="username" id="username" maxlength="10" type="text" >
						</div>
						<div class="login-block">
							<div class="title">密碼：</div>
							<input tabindex="2" autocomplete="off" id="password" name="password" type="password">
						</div>
						<div class="login-block">
						    <div class="title">驗證碼：</div>
						    <input tabindex="3" autocomplete="off" id="captcha" type="text" maxlength="4" name="captcha" htmlescape="true" placeholder="輸入驗證碼">
						    <img id="captchaImage" src="${projectUrl}/captcha" alt="">
						</div>
						<div class="login-block">
							<button class="btn" id="lbt_login" type="submit">登入</button>
							${name}
						</div>
						<div class="login-block">
							<a style="color:green;text-decoration:none;font-size:14px" href="/cas335/getPw" target="_blank">忘記密碼</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
<script>
$(function() {
	//指定版權開始年份
	$("div.copyright span").text( "InThink System Ⓒ " + ( new Date() ).getFullYear() );
	
	$("#lbt_login").click(function() {
	    if ( !$("input#username").val() ) {
	      alert("請輸入帳號！");
	      return false;
	    }
	    if ( !$("input#password").val() ) {
	      alert("請輸入密碼！");
	      return false;
	    }
	});
});
</script>
</html>