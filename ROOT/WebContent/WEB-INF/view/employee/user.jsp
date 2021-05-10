<%@page import="com.iVoting.dto.UserDTO"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">

	<!-- =============================Add====================================================== -->
	<!-- Button trigger modal -->
	<button id="btn_addEmployee" type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal_Update">Add</button>
	<!-- =============================Update=================================================== -->
	<!-- Button trigger modal -->
	<!-- 
	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal_Update">Update</button>
	-->
	<!-- Modal -->
	<div class="modal fade" id="myModal_Update" tabindex="-1" role="dialog"	aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>

				<div class="modal-body">
					<form id="formUpdate" name="form3" action="" method="post" class="form">
						<table id="myTable2" class="table table-hover">							
							<tr>								
								<td align="center">Account =</td>
								<td align="center"><input type="text" name="account" id="account" placeholder="necessary"></td>
							</tr>
							<tr>
								<td align="center">Password =</td>
								<td align="center"><input type="text" name="password" id="password" placeholder="necessary"></td>
							</tr>
							<tr>
								<td align="center">Password Hint =</td>
								<td align="center"><input type="password" name="passwordHint" id="passwordHint" placeholder="unnecessary"></td>
							</tr>
							<tr>
								<td align="center">Name =</td>
								<td align="center"><input type="text" name="name" id="name" placeholder="necessary"></td>
							</tr>
							<tr>
								<td align="center">Role =</td>
								<td align="center">
									<select name="roleId" id="roleId">
									　<option value="ROLE_1">ROLE_1</option>
									　<option value="ROLE_2">ROLE_2</option>
									　<option value="ROLE_3">ROLE_3</option>
									　<option value="ROLE_4">ROLE_4</option>
									　<option value="ROLE_5">ROLE_5</option>	
									</select>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button id ="myModalClose" class="btn btn-default" data-dismiss="modal">Close</button>
					<button id="btnUpdate" class="btn btn-primary">Edit</button>
					<button id="btnInsert" class="btn btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>

	<!-- ====================================================================================== -->
<!--  -->
	<table id="myTable" class="table table-bordered">
		<thead>
			<tr>
				<th>ACCOUNT</th>
				<th>PASSWORD</th>
				<th>PASSWORD_HINT</th>
				<th>NAME</th>
				<th>STATUS</th>
				<th>JOIN_DATE</th>
				<th>MODIFY_DATE</th>
				<th>MODIFY_MAN</th>
				<th>EDIT</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${employees}" var="user">
				<tr>
					<td>${user.ACCOUNT}</td>
					<td>${user.PASSWORD}</td>
					<td>${user.PASSWORD_HINT}</td>
					<td>${user.NAME}</td>
					<td>${user.STATUS}</td>
					<td>${user.JOIN_DATE}</td>
					<td>${user.MODIFY_DATE}</td>
					<td>${user.MODIFY_MAN}</td>
					<td>
						<button class="btn btn-info btn-update" data-toggle="modal" data-target="#myModal_Update">Update</button>						
						<a class="btn btn-danger" href="deleteEmployee?id=${user.USER_ID}">Delete</a>	
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
		<div class="result-wrapper table-wrapper">
			<table id="tb-result" class="dataTable">
			</table>
		</div>
</div>
<script type="text/javascript">

</script>
<script>
// var resultTable = null;
// jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages = 10;

// var shipFileTable = function(){

//     resultTable = $("table#tb-result").dataTable({
//         "bProcessing": false,
//         "bServerSide": true,
//         "sAjaxSource": "/ajaxShow",
//         "fnServerParams": function(aoData) {
//             aoData.push({
//                 "name": "cbaNum",
//                 "value": ""
//             });
//             aoData.push({
//                 "name": "QC",
//                 "value": ""
//             });
//         },
//         "fnServerData": function(sSource, aoData, fnCallback) {
//             $.ajax({
//                 "type": "POST",
//                 "url": sSource,
//                 "data": aoData
//             }).done(function(data) {
//  				if( data.msg ){
//  					alert(data.msg);
//  					return;
//  				}
//  				fnCallback(data);
//             });
//         },
//         "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
//             $("td:eq(0), td:eq(1), td:eq(2), td:eq(3), td:eq(4), td:eq(5), td:eq(6), td:eq(7)", nRow).attr("style", "text-align: center;");
//             return nRow;
//         },
//         "aoColumns": [{
//             "sTitle": "品牌名稱",
//             "aTargets": [0],
// 			"mDataProp" : function (aData){
//                 return aData.USER_ID;
//             }, 
// 			"sWidth": "30px",
// 			"bSearchable": false
// 		},{
//             "sTitle": "QC",
//             "aTargets": [1],
// 			"mDataProp" : function (aData){
//                 return aData.ACCOUNT;
//             },
// 			"sWidth": "80px",
// 			"bSearchable": false
// 		},{
//             "sTitle": "商品名稱",
//             "aTargets": [2],
// 			"mDataProp" : function (aData){
//                 return aData.PASSWORD;
//             },
// 			"sWidth": "180px",
// 			"bSearchable": false
// 		},{
//             "sTitle": "良品數量",
//             "aTargets": [3],
// 			"mDataProp" : function (aData){
//                 return aData.PASSWORD_HINT;
//             },
// 			"sWidth": "80px",
// 			"bSearchable": false
	
// 		}],
//         "bPaginate": true,
//         "bAutoWidth": true,
//         "bDestroy": true,
//         "bInfo": false,
//         "bLengthChange": false,
//         "bSort": false,
//         "bFilter": false,
//         "sPaginationType": "full_numbers",
//         "iDisplayLength": 15,
//         "oLanguage": {
//             "oPaginate": {
//                 "sFirst": "第一頁",
//                 "sPrevious": "上一頁",
//                 "sNext": "下一頁",
//                 "sLast": "最末頁"
//             },
//             "sEmptyTable": "沒有符合條件的資料",
//             "sZeroRecords": "沒有符合條件的資料"
//         }
//     });
// };
	$(function() {
		$("#myTable").DataTable();
		
		
		$(".btn-update").click(function(){
			$("#myModal_Update input[name=id]").val($(this).parent().parent().find("td:first-child").text());
			$("#myModal_Update input[name=name]").val($(this).parent().parent().find("td:nth-child(2)").text());
			$("#myModal_Update input[name=password]").val($(this).parent().parent().find("td:nth-child(3)").text());
			$("#myModal_Update input[name=memo]").val($(this).parent().parent().find("td:nth-child(4)").text());
		});	
		
		$("#btn_addEmployee").click(function(){		
			$("#btnUpdate").hide();
			$("#btnInsert").show();
		});
		
		$('#myModal').on('shown.bs.modal', function() {
			$('#updateModal').focus()
		});
		
		$("#btnInsert").click(function() {
			var obj = {};
			obj["account"] = $("#account").val();
			obj["password"] = $("#password").val();
			obj["passwordHint"] = $("#passwordHint").val();
			obj["name"] = $("#name").val();
			obj["roleId"] = $("#roleId").val();
			
			$.ajax({
				url: '/insert',
				type: 'POST',
				dataType:'json',
				data: {
					data: $.toJSON(obj)
				},
				success: function(data) {
					$('#myModalClose').click();
					alert("新增成功");
				},
				error: function(data){
					alert(data.eMessage);
			  	}
			});
		});
		shipFileTable();
	});
</script>