//섹션 
/*-callistresult화면에서 캘린더 썸네일 클릭했을때-*/
function calThumbnailClick(){
	let $calThumbnailObj = $('section>div.articles>ul>li>div.title_wrap>a>img');
	 	//console.log($calThumbnailObj);
		$calThumbnailObj.click(function(){
			let $dateValue = $('section>div.articles>div.nowdate').html();
			console.log($dateValue);

	        let ajaxUrl = "/calpost"; 
	        
				$.ajax({
	            url: ajaxUrl,
	            method : 'get',
				data: {dateValue:$dateValue},
	            dateValue:function(responseData){
				console.log(responseData);
	                let $articlesObj = $('section>div.articles');
	                $articlesObj.empty();
	                $articlesObj.html(responseData);

			            }
		        }); 
		        return false;
				
		});
	}




/*-callistresult화면에서 캘린더 add 클릭했을때-*/	

function caladdClick(){
	$('section>div.articles>ul>li>div.title_add>a>img').click(function(){
		let url = 'calwrite.html';
        let target = 'category+Thbumbnail';
        let features = 'top=300, left=500, width=500px, height=500px';
        window.open(url, target, features);
		return false;
	});
}


/*function caladdClick(){
	let $caladdObj = $('section>div.articles>ul>li>div.title_add>a>img');
 
	$caladdObj.click(function(){
        let menuHref = $(this).attr('src'); 
        let ajaxUrl = ""; 
        
			$.ajax({
            url: ajaxUrl,
            method : 'get',
            success:function(responseData){
                let $articlesObj = $('section>div.articles');
                $articlesObj.empty();
                $articlesObj.html(responseData);
			
				let url = 'calwrite.jsp';
				let target = 'category+Thbumbnail';
		        let features = 'top=300, left=500, width=500px, height=500px';
		        window.open(url, target, features);
				
	            }
        }); 
	        return false;
			
	});
}*/