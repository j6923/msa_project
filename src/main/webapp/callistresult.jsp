<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script src="./js/tab.js"></script><!-- tab.jsp 이벤트 -->
    <script>
	    $(function(){
			//탭에서 메뉴클릭시 발생하는 이벤트(캘린더/공지사항/자유게시판)
			tabMenuClick();
			
			//메뉴에서 커뮤니티 클릭시 탭바뀌는 이벤트
			tabChange(); 
			
			//섹션영역에서 캘린더 이미지 클릭시 발생하는 이벤트 
			calMenuClick();
			
	
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
 