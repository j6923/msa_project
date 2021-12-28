/* 캘린더 추가 등록/수정 버튼 클릭 되었을 때 */

  /*--캘린더 추가 등록버튼이 클릭되었을때 START--*/
function addCalClick(){
		let $addCalObj = $('fieldset>form');
		
		$addCalObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
        	let thumbnailValue = $(this).find('input[name=thumbnail]').val();
        	let categoryValue = $(this).find('input[name=category]').val();


			$.ajax({
				url: ajaxUrl,
				method: ajaxMethod,
				data:sendDate,//{thumbnail:thumbnailValue, category:categoryValue},
				success: function(responseObj){
					if(responseObj.status == 0){//등록 실패
						alert(responseObj.msg);
					}else{
						opener.parent.location.reload();
						window.self.close();	
					}
				},
				error: function(xhr){
					alert("응답실패 status:" + xhr.status);
				}
			});
			return false;
		});
}

/*--캘린더 수정 버튼이 클릭되었을때 START--*/
/*function modifyCalClick(){
		let $modifyCalObj = $('fieldset>form');
		
		$modifyCalObj.submit(function(){
			let ajaxUrl = $(this).attr('action');
       		let ajaxMethod = $(this).attr('method');
        	let thumbnailValue = $(this).find('input[name=thumbnail]').val();
        	let categoryValue = $(this).find('input[name=category]').val();



			$.ajax({
				url: ajaxUrl,
				method: ajaxMethod,
				data:{thumbnail:thumbnailValue, category:categoryValue},
				success: function(responseObj){
					if(responseObj.status == 0){//등록 실패
						alert(responseObj.msg);
					}else{
						opener.parent.location.reload();
						window.self.close();	
					}
				},
				error: function(xhr){
					alert("응답실패 status:" + xhr.status);
				}
			});
			return false;
		});
}*/