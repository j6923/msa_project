<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link rel="stylesheet" href="./css/callist.css">
    <script src="./js/tab.js"></script><!-- tab.jsp 이벤트 -->
    <script src="./js/callist.js"></script>
    <script>
	    $(function(){
			//탭에서 메뉴클릭시 발생하는 이벤트(공지사항/faq/자유게시판)
			tabMenuClick();
			                   
			//메뉴에서 커뮤니티 클릭시 탭바뀌는 이벤트
			tabChange(); 
			
			//탭에서 생성된 캘린더 카테고리 클릭시 발생하는 이벤트 
			calMenuClick();
			
			//탭에서 캘린더 add 클릭시 발생하는 이벤트 
			tabaddClick();
			
			//섹션영역에 캘린더 썸네일 클릭시 발생하는 이벤트 
			calThumbnailClick();
			
			//섹션영역에 캘린더 add 클릭시 발생하는 이벤트 
			caladdClick();
	
	    })	
	</script>
 <div class="tab"><!-- tab.js -->
  	<jsp:include page="./tab.jsp"/>
</div> 

<section><!--callist.js -->
<div class="articles">
	 <ul class="title_list"> 
	  	<jsp:include page="./title_list.jsp"/>
	 </ul>
 </div>
</section>

<hr>
<footer>
     	<%@include file="./footer.jsp" %>     <!--맺음말-->  
</footer>
 