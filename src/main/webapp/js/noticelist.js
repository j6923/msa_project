/**
 * 공지사항 목록에서 글쓰기 버튼이 클릭되었을때
 */







/**
 *  공지사항 목록에서 글 하나 클릭되었을때 
 */


function noticeDetail(){
    let $noticeObj = $('div.ntclist>div');
    $noticeObj.click(function(){
        let ntcIdx = $(this).attr('id');
        let ajaxUrl = './ntcdetail';
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {ntcIdx: ntcIdx},
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
            }
        }); 
        return false;
    });
}


/**
 *  공지사항 목록에서 검색버튼 클릭했을때
 */

 function searchClick(){
    let $searchButtonObj = $('div.search>button.searchButton');
	let $searchWordObj = $('div.search>input[name=word]');
    $searchButtonObj.click(function(){
		if($searchWordObj.val().trim() == ''){
            alert('검색어를 입력하세요');
            $searchWordObj.focus();
            return false;
        }

		let ajaxUrl = "./ntcsearch";
		let word = $searchWordObj.val().trim();
		
		$.ajax({
			url : ajaxUrl,
			method:'get',
			data : {word:word},
			success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
            },
			error:function(xhr){
				alert("응답실패"+xhr.status);
			}
		});

        return false;	
    
    });
}    



