<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript"	src="${staticHost}lms/js/jquery-ui.min.js"></script>
<script type="text/javascript"	src="${staticHost}lms/js/ajaxfileupload.js"></script>
<script type="text/javascript"	src="${staticHost}lms/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"	src="${staticHost}lms/js/datePick/jquery.datepick.js"></script>
<script type="text/javascript"	src="${staticHost}lms/js/datePick/jquery.datepick-zh-TW.js"></script>
<script type="text/javascript" src="${staticHost}lms/js/productExpired.js?v20180614"></script>
<script type="text/javascript" language="javascript">
	var errorMessage = '${errorMessage}';
</script>

<div id="container" class="panel panel-default container-md">
	<div id="content" class="ship-file">
		<div class="client-name pull-right">
			<span>客戶名稱：</span><span class="info">${susrName}</span><input id="susrId" type="hidden" value="${susrId}">
		</div>
		<h2 class="page-title">警示效期維護</h2><hr>
		<div class="form-wrapper form-inline">
			<ul class="option-wrapper clearfix">
		        <li>品牌名稱:
		           <select name="cbaNum" id="cbaNum" class="form-control"> 
		                <option value="">全部</option>
		            	<c:forEach var="cbaNameList" items="${cbaNameList}">
							<option value="${cbaNameList.CBA_NUM}">${cbaNameList.CBA_NAME}</option>
						</c:forEach>
		            </select>
		        </li>
		        <li>品牌QC: <input id="productQc" type="text" class="form-control" maxlength="20" ></li>
		        <li><input id="startDataTable" type="button" value="查詢" class="btn btn-done"></li>
	        </ul>
        </div>
		<div class="form-wrapper form-inline">
			<div class="file-name-wrapper">
				<form id="fileUploadForm" name="fileUploadForm"	enctype="multipart/form-data" action="" method="POST">批次更新警示效期:
					<img id="loadingImg" src="${staticHost}lms/images/fileloading.gif"	style="vertical-align: middle; display: none;"> 
						<input name="uploadFile" id="uploadFile" type="file" class="form-control" />
					<input id="fileUploadBtn" type="button" value="匯入檔案" class="btn btn-info" />
					<span id="fileUploadTip" class="required-tip"> *副檔名僅可為.xls、.xlsx </span>
				</form>
				<a id="downloadTemplete" href="/lms/DF/EA_01.xlsx" style="color: blue;" target="_blank">批次修改效期 參考格式下載</a>
			</div>
		</div>
		<div id="upUpdateResult" class="form-wrapper form-inline">
			<span id="notUpdatedQc1"></span><br>
			<span id="notUpdatedQc2"></span>
		</div>
		<hr>
		<div class="result-wrapper table-wrapper">
			<table id="tb-result" class="dataTable">
			</table>
		</div>
	</div>
</div>