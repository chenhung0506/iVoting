$(function(){
	window.address.getCities();
	
	var address = {
		city : '',
		district : '',
		zip : ''
	};
	
	$('#citySelect').on('change' , function(){
		window.address.getDistricts($(this).val());		
		if( $('#citySelect option:selected').val() !== '請選擇縣市' && $('#citySelect option:selected').val() !== '99' && $('#citySelect option:selected').val() !== '-1'){
			$('#districtSelect').removeAttr('disabled');
			address.city = $('#citySelect option:selected').text();
		}else{
			$('#districtSelect').attr('disabled', 'disabled');
			address.city = '';
		}
		$('#cmemAddr').val( address.city );
		$('#cmemZip').val('');
	});
	
	$('#districtSelect').on('change' , function(){
		address.district = $('#districtSelect option:selected').text();
		address.zip = $('#districtSelect option:selected').val();
				
		if( $('#districtSelect option:selected').val() !== '請選擇鄉鎮市區' && $('#districtSelect option:selected').val() !== '-1' ){
			$('#cmemZip').val(address.zip);
			$('#cmemAddr').val( address.city + address.district );
		}else{
			$('#cmemZip').val('');
		}
		
		
	});
	
});

(function(win){
	win.address = {			
		getCities : function(){
		    $.ajax({
		        url: '/Tutorial_SpringMVC/address/city.json',
		        type: 'GET',
		        dataType : 'json',
		        cache : false,
		        beforeSend: function(XHR, settings){ 
		        	
		        }
		    })
		    .done(function(response){
		    	if(response.status === 'OK'){
		    		var optionsHtml = '<option value="-1">請選擇縣市</option>';
		    		$.each( response.cities , function(key, item){
		    			optionsHtml += '<option value="'+ item.cityNum +'">' + item.cityName + '</option>'; 
		    		});
		    		$('#citySelect').html(optionsHtml);
		    	}
		    });
		},
		getDistricts : function(cityNum){
		    $.ajax({
		        url: '/Tutorial_SpringMVC/address/district.json',
		        data: { cityNum : cityNum },
		        type: 'GET',
		        dataType : 'json',
		        cache : false,
		        beforeSend: function(XHR, settings){ 
		            
		        }
		    })
		    .done(function(response){
		    	if(response.status === 'OK'){
		    		var optionsHtml = '<option value="-1">請選擇鄉鎮市區</option>';
		    		$.each( response.districts , function(key, item){
		    			optionsHtml += '<option value="'+ item.districtNum +'">' + item.districtName + '</option>'; 
		    		});
		    		$('#districtSelect').html(optionsHtml);
		    	}
		    });
		}
		
	};
}(window));