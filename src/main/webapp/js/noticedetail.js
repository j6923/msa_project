function noticeModifyClick(){
	let $removeBtObj=$('button.notice_modify');
	//console.log($removeBtObj);
	$removeBtObj.click(function(){
		let $ntcIdx = $('#ntcIdx').html();
		let $ntcTitle = $('#ntcTitle').html();
		let $ntcContent = $('#ntcContent').html();
		let $ntcAttachment = $('#ntcAttachemtn').html();
		
		
		$.ajax({
			url:'noticemodify.jsp',
			method:'get',
			data:{ntcIdx:$ntcIdx, ntcTitle:$ntcTitle, ntcContent: $ntcContent,ntcAttachment:$ntcAttachment},
			success:function(responseData){
				
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
			}
		});
		
           
        
        return false;
	});
}

function noticeRemoveClick(){
	let $removeBtObj=$('button.notice_remove');
	$removeBtObj.click(function(){
		let $ntcIdxValue = $(this).attr("id");
		let ajaxUrl = "./ntcremove";
        $.ajax({
            url: ajaxUrl,
			method: ajaxMethod,
			data: {ntcIdx:$ntcIdxValue},
            success:function(responseData){
				 let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
            }
        });
        return false;
	});
}