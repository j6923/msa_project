
let $tabMenuObj = $('div.tab>ul>li a');

function tabMenuClick(){
	 $tabMenuObj.click(function(){
        let menuHref = $(this).attr('href'); //href속성값 얻기(여기서 this는 클릭된 메뉴 객체를 의미)
        console.log("메뉴 href=" + menuHref);

		
        let ajaxUrl = ""; //요청할URL
        //let ajaxMethod = ""; //요청방식
	});

}


