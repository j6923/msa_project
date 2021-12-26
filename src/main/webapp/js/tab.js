


function tabMenuClick(){
	let $tabMenuObj = $('div.tab>ul>li>a');
	 $tabMenuObj.click(function(){
        let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
        
		switch(menuHref){
			//tab에서 공지사항이 클릭되었을때
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
			case 'faqlist.html':
				ajaxUrl = menuHref;
				
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;
			
			//tab에서 자유게시판이 클릭되었을때
			case 'brdlist':
				ajaxUrl = menuHref;
               	$('section>div.articles').empty();
                $('section>div.articles').load(ajaxUrl,function(responseText, textStatus, jqXHR){
                    if(jqXHR.status != 200){
                        alert('응답실패:' + jqXHR.status);
                    }
                });
                return false;				
          }
	});
}


function tabChange(){
	let $communityBtObj = $('header>nav>ul>li>a[href=ntclist]');
	$communityBtObj.click(function(){
		$('div.tab>ul.communitytab').css('display','table');
		$('div.tab>ul.caltab').css('display','none');
	});
}


