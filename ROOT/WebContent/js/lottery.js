$(document).ready(function() {
	if ( errorMessage ) {
	    alert(errorMessage);
	}
	$("#upUpdateResult").hide();
	
	$(document).on('click', '.btn-update', function(){
        $(this).parent().parent().find("input").attr("disabled", false);
        $(this).parent().find("span.btn-confirm").show();
        $(this).parent().find("span.btn-cancel").show();
        $(this).hide();
	});

	$(document).on('click', '.btn-cancel', function(){
        $(this).parent().parent().find("input").attr("disabled", true);
        $(this).parent().find("span.btn-update").show();
        $(this).parent().find("span.btn-confirm").hide();
        $(this).hide();
	});

	$(document).on('click', '.btn-confirm', function(){
	    $.ajax({
	        url: '/LMS/productMaintain/updateProductExpired',
	        type: 'POST',
	        data: {
	        	"cansell": $(this).parent().parent().find("input.cansell").val(),
	            "expired": $(this).parent().parent().find("input.expired").val(),
	            "qc": $(this).data("productqc")
	        }
	    }).done(function(response) {
	    	if(response.msg){
	    		alert(response.msg);
	    	}
	    	shipFileTable();
	    });
	    $(this).parent().parent().find("input").attr("disabled", true);
	    $(this).parent().find("span.btn-cancel").hide();
	    $(this).parent().find("span.btn-update").show();
	    $(this).hide();
	});
	
	$("#startDataTable").on("click", function () {
		shipFileTable();
	});	
	
	$("#fileUploadBtn").on("click", function () {
		var validFileExts = new Array(".xls",".xlsx");
		var uploadFile = $("#uploadFile").val();
		var ldfNum = $("#ldfNum").val();
		var sorId = $("#sorId").val();
		
		uploadFile = uploadFile.substring(uploadFile.lastIndexOf('.'));
		
		if(uploadFile == ""){
			alert("您未選擇任何PDF檔案!");
			return false;
		}
//		if (validFileExts.indexOf(uploadFile) < 0) {
//			alert("上傳副檔名僅可為:"+ validPdfExts.toString());
//			return false;
//		}

		$("#fileUploadBtn").attr( "disabled" );
		
		$.ajaxFileUpload({
			url: '/LMS/productMaintain/uploadFile',
			type: 'POST',
			secureuri: false,
			fileElementId: 'uploadFile',
			dataType:'json',
			data: {
				fileKey : "uploadFile",
			},
			success: function(data) {
				console.log(data.eMessage);
				if ( data.eMessage ) {
					alert(data.eMessage);
					$("#upUpdateResult").show();
					$("#notUpdatedQc1").text("查無QC===>" + data.notUpdatedQc1);
					$("#notUpdatedQc2").text("參數錯誤===>" + data.notUpdatedQc2);
					if (data.notUpdatedQc1 == "[]" && data.notUpdatedQc2 == "[]"){
						$("#upUpdateResult").hide();	
					}
				} else {
					alert("檔案上傳成功!");
				}
			},
			error: function(data){
				alert(data.eMessage);
		  	}
		});
	});
});

var resultTable = null;
jQuery.fn.dataTableExt.oPagination.iFullNumbersShowPages = 10;

var shipFileTable = function(){

    resultTable = $("table#tb-result").dataTable({
        "bProcessing": false,
        "bServerSide": true,
        "sAjaxSource": "/LMS/productMaintain/getProductExpired",
        "fnServerParams": function(aoData) {
            aoData.push({
                "name": "cbaNum",
                "value": $("#cbaNum").val()
            });
            aoData.push({
                "name": "QC",
                "value": $("#productQc").val()
            });
        },
        "fnServerData": function(sSource, aoData, fnCallback) {
            $.ajax({
                "type": "POST",
                "url": sSource,
                "data": aoData
            }).done(function(data) {
 				if( data.msg ){
 					alert(data.msg);
 					return;
 				}
 				fnCallback(data);
            });
        },
        "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            $("td:eq(0), td:eq(1), td:eq(2), td:eq(3), td:eq(4), td:eq(5), td:eq(6), td:eq(7)", nRow).attr("style", "text-align: center;");
            return nRow;
        },
        "aoColumns": [{
            "sTitle": "品牌名稱",
            "aTargets": [0],
			"mDataProp" : function (aData){
                return aData.CBA_NAME;
            }, 
			"sWidth": "30px",
			"bSearchable": false
		},{
            "sTitle": "QC",
            "aTargets": [1],
			"mDataProp" : function (aData){
                return aData.PRODUCTQC;
            },
			"sWidth": "80px",
			"bSearchable": false
		},{
            "sTitle": "商品名稱",
            "aTargets": [2],
			"mDataProp" : function (aData){
                return aData.PRODUCTNAME;
            },
			"sWidth": "180px",
			"bSearchable": false
		},{
            "sTitle": "良品數量",
            "aTargets": [3],
			"mDataProp" : function (aData){
                return aData.PROGOODQUANTITY;
            },
			"sWidth": "80px",
			"bSearchable": false
		},{
            "sTitle": "警示效期",
            "aTargets": [4],
			"mDataProp" : function (aData){
				return '<input type="text" name="expired" class="expired" value="'+ ( (null==aData.EXPIRED_ALERT) ? "" : aData.EXPIRED_ALERT ) +'" maxlength="4" disabled="disabled" "style="text-align:center;">';
            },
			"sWidth": "40px",
			"bSearchable": false
		},{
            "sTitle": "不良品效期",
            "aTargets": [5],
			"mDataProp" : function (aData){
				return '<input type="text" name="cansell" class="cansell" value="'+ aData.CAN_SELL +'" maxlength="4" disabled="disabled" "style="text-align:center;">';
            },
			"sWidth": "80px",
			"bSearchable": false
		},{
            "sTitle": "操作",
            "aTargets": [6],
			"mDataProp" : function (aData){
				var text = 
					'<span class="btn btn-update">更新</span>'+
					'<span class="btn btn-info btn-confirm" data-productqc="'+ aData.PRODUCTQC + '" style="display:none;">確認</span>'+
					'<span class="btn btn-danger btn-cancel" style="display:none;">取消</span>';
                return text;
            },
			"sWidth": "80px",
			"bSearchable": false		
		}],
        "bPaginate": true,
        "bAutoWidth": true,
        "bDestroy": true,
        "bInfo": false,
        "bLengthChange": false,
        "bSort": false,
        "bFilter": false,
        "sPaginationType": "full_numbers",
        "iDisplayLength": 15,
        "oLanguage": {
            "oPaginate": {
                "sFirst": "第一頁",
                "sPrevious": "上一頁",
                "sNext": "下一頁",
                "sLast": "最末頁"
            },
            "sEmptyTable": "沒有符合條件的資料",
            "sZeroRecords": "沒有符合條件的資料"
        }
    });
};