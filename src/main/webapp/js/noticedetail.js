function noticeModifyClick(){
	let $removeBtObj=$('button.notice_modify');
	$removeBtObj.click(function(){
		let $ntcTitle = $('ul>li>span[id=ntcTitle]');
		let $ntcContent = $('ul>li>span[id=ntcContent]');
		let $ntcAttachment = $('ul>li>span[id=ntcAttachemtn]');
		
		let ajaxUrl = "./noticemodify.jsp";
        $.ajax({
            url: ajaxUrl,
			data: {ntcTitle:$ntcTitle, },
            success:function(responseData){
			console.log(responseData);
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
	console.log(responseData);
				 let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
            }
        });
        return false;
	});
}