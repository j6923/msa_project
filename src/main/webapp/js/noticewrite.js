function noticeSubmit($formObj){
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');
		let ajaxMethod = $(this).attr('method');
		let sendData = $(this).serialize();
		console.log(sendData);
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendData,
			success:function(responseData){
				console.log(responseData);
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
			},error:function(xhr){
                alert("응답실패:" + xhr.status);
            }
		});
		return false;
	});
}