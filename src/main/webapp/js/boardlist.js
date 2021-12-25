/**
 <!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 START-->
 */

 

 /**
 <!--자유게시판 목록에서 글 쓰기 버튼 클릭되었을때 END-->
  */


 /**
 <!-- 자유게시판 목록에서 글 하나 클릭되었을때 START-->  
 */    




/**
 <!-- 자유게시판 목록에서 글 하나 클릭되었을때 END-->
 */

 function boardDetail(){
    let $boardObj = $('div.brdlist>div');
    $boardObj.click(function(){
        let brdIdx = $(this).attr('id');
        let ajaxUrl = './brddetail';
        $.ajax({
            url: ajaxUrl,
            method : 'get',
            data : {brdIdx: brdIdx},
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
 <!-- 자유게시판 목록에서 검색 클릭시 START-->
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

		let ajaxUrl = "./brdsearch";
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
 

 /**
 <!-- 자유게시판 목록에서 검색 클릭시 END-->
  */