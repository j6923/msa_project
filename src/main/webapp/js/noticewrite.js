function noticeSubmit($formObj){
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');
		let ajaxMethod = $(this).attr('method');
		let sendData = $(this).serialize();
		
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseObj){
				if(responseObj.status == 0){ //저장실패  
					alert(responseObj.msg);
                }
			},error:function(xhr){
                alert("응답실패:" + xhr.status);
            }
		});
		return false;
	});
}