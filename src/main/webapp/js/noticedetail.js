function noticeModifyClick(){
	
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