<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">${projectName}</a>
<!--         <sec:authorize access="hasAnyRole('ROLE_1','ROLE_2')"> -->
<ul class="nav navbar-nav">
        <li class="dropdown open">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">查詢 <b class="caret"></b></a>
	          <ul class="dropdown-menu" role="menu">
	            
					<li><a href="/LMS/stock/realTime">WMS庫存查詢</a></li>
				
				<li><a href="/LMS/stock/platformStockQuery">庫存查詢</a></li>
		        <li><a href="/LMS/order/query">訂單查詢</a></li>		       
		        <li><a href="/LMS/abnormalOrder/query">異常訂單查詢</a></li>		        
	            <li><a href="/LMS/stockVariance/query">庫存變動數查詢</a></li>
	            <li><a href="/LMS/report/inventory">商品周轉率查詢</a></li>
	            <li><a href="https://www.treemall.com.tw/PMIS/report/index" target="_Blank">其他報表</a></li>
	            <li><a href="/LMS/mailSetting/searchPage">郵件事件</a></li>
	        	
	        		<li><a href="/LMS/stockSyncSetup">平台上架量比例</a></li>	
       	         		        	
	          </ul>
	        </li>
</ul>
<!-- 		</sec:authorize> -->
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${username}<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="j_spring_security_logout">Log Out</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<div style="min-height: 30px;margin-bottom: 30px;"></div>