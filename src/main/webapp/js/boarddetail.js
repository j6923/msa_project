
/**
 자유게시판 글 상세보기에서 글 수정 버튼 클릭
 */
function boardModifyClick(){
	let $modifyBtObj=$('button.board_modify'); 
	//console.log($removeBtObj);
	$modifyBtObj.click(function(){
		let $brdIdx = $('#brdIdx').html().trim();
		let $brdType = $('#brdType').html();
		let $brdTitle = $('#brdTitle').html();
		let $brdContent = $('#brdContent').html();
		let $brdAttachment = $('#brdAttachment').html();
		
		
		$.ajax({
			url:'boardmodify.jsp',
			method:'get',
			data:{brdIdx:$brdIdx, brdType:$brdType, brdTitle: $brdTitle,brdContent:$brdContent,brdAttachment:$brdAttachment},
			success:function(responseData){				
				let $articlesObj = $('section>div.articles');
               	 $articlesObj.empty();
                 $articlesObj.html(responseData);
		         window.scrollTo(0, 0);
			}
		});
          
        return false;
	});
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
				 window.scrollTo(0, 0);
            }
        });
        return false;
	});
}




function boardListClick(){
	$('button.board_list').click(function(){
		let ajaxUrl = "./brdlist";
        let method = "get";
		$.ajax({
            url: ajaxUrl,
            method: method,
            success:function(responseData){
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				window.scrollTo(0, 0);
            }
        });
        return false;
	});
}

function commentAddClick(){
	let $formObj = $('fieldset>form');
	
	$formObj.submit(function(){
		let ajaxUrl = $(this).attr('action');		
		let ajaxMethod = $(this).attr('method');
		let sendCmt = $(this).serialize();	
		console.log(sendCmt);
		
		$.ajax({
			url:ajaxUrl,
            method:ajaxMethod,
            data:sendCmt,
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
	


function commentModifyClick(){
	
}

function commentRemoveClick(){
	$('button.comment_remove').click(function(){
		let ajaxUrl = "./cmtremove";
        let method = "get";
		let $brdIdx = $('#brdIdx').html().trim();
		let $cmtIdx = $(this).parent().parent().children('span').html();
		console.log($brdIdx);
		console.log($cmtIdx);
		$.ajax({
            url: ajaxUrl,
            method: method,
			data: {brdIdx:$brdIdx, cmtIdx:$cmtIdx},
            success:function(responseData){
				let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
				
            }
        });
        return false;
	});	
}


