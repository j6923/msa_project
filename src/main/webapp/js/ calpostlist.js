//calpostlistresult.jsp에서 이벤트 발생 
/*캘린더포스트에서 날짜 클릭되었을때*/

function daySelectClick(){
	let $daySelectDay = $('div.container>div.body>div[id=calendarForm]');
	 
		$daySelectDay.click(function(){
	        let ajaxUrl = "calpostwrite.jsp"; 
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