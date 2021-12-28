//callistresult.jsp에서 이벤트 발생 
function calClick(){
	let $calClickObj = $('section>div.articles>li>div.title_wrap>a>img');
	 $calClickObj.click(function(){
        let menuHref = $(this).attr('href'); 
        console.log("메뉴 href=" + menuHref);
		
        let ajaxUrl = ""; 
        
		switch(menuHref){
			//섹션영역에서 썸네일 사진이 클릭되었을때
			case 'ntclist':
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