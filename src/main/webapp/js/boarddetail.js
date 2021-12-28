
/**
 자유게시판 글 상세보기에서 글 수정 버튼 클릭
 */
function boardModifyClick(){
	let $modifyBtObj=$('button.board_modify'); 
	//console.log($removeBtObj);
	$modifyBtObj.click(function(){
		let $brdIdx = $('#brdIdx').html();
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
			}
		});
          
        return false;
	});

function boardModifyClick(){

}



/**
 자유게시판 글 상세보기에서 글 삭제 버튼 클릭  (아직 수정안함)
 */
function boardRemoveClick(){
	let $removeBtObj=$('button.board_remove');
	$removeBtObj.click(function(){
		let $ntcIdxValue = $(this).attr("id");
		let ajaxUrl = "./ntcremove";
        $.ajax({
            url: ajaxUrl,
			method: ajaxMethod,
			data: {ntcIdx:$ntcIdxValue},

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