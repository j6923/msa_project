


function tabMenuClick(){
	let $tabMenuObj = $('div.tab>ul>li>a');
	 $tabMenuObj.click(function(){
        let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
        
		switch(menuHref){
			//tab에서 커뮤니티가 클릭되었을때
			case 'ntclist':
                ajaxUrl = menuHref;
                $('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
				return false;
				
			//tab에서 faq가 클릭되었을때
			case 'faq.html':
				ajaxUrl = menuHref;
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;
			
			//tab에서 자유게시판가 클릭되었을때
			case 'brdlist':
				ajaxUrl = menuHref;
                $.ajax({
                    url: ajaxUrl,
                    success:function(){
                        location.href="./index.jsp";
                    },
                    error:function(xhr){
                        alert('응답실패:' + xhr.status);
                    }
                });
                return false;				
          }
	});

}


