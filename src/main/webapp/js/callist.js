//callistresult.jsp에서 이벤트 발생 
/*-callistresult화면에서 캘린더 썸네일 클릭했을때-*/
function calThumbnailClick(){
	let $calThumbnailObj = $('section>div.articles>ul>li>div.title_wrap>a>img');
	 
		$calThumbnailObj.click(function(){
	        let menuHref = $(this).attr('src'); 
	        let ajaxUrl = "calpostlistresult.jsp"; 
	        
				$.ajax({
	            url: ajaxUrl,
	            method : 'get',
	            success:function(responseData){
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);
			            }
		        }); 
		        return false;
				
		});
	}
	
	