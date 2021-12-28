function boardModifyClick(){
	
}



function boardRemoveClick(){
	let $removeBtObj=$('button.board_remove');
	$removeBtObj.click(function(){
		let $brdIdxValue = $(this).attr("id");
		let ajaxUrl = "./brdremove";
        $.ajax({
            url: ajaxUrl,
			method: ajaxMethod,
			data: {brdIdx:$brdIdxValue},
            success:function(responseData){
				 let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
            }
        });
        return false;
	});
}