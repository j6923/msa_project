<%@page import="com.my.customer.vo.Customer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
Customer c = (Customer)session.getAttribute("loginInfo");%>  
<!DOCTYPE html>
<html>
<head>
	<title>RECO</title>
	<meta charset="UTF-8">
	
	<link rel="stylesheet" href="./css/header.css">   
	<link rel="stylesheet" href="./css/section.css">
	<link rel="stylesheet" href="./css/footer.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>   
	<style>
		header, section,footer{
	    	width:100%;
	    }    
	</style> 
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="./js/menu.js"></script><!-- menu.jsp 이벤트-->
	<script src="./js/index.js"></script><!-- index.jsp 이벤트 -->
	<script src="./js/tab.js"></script><!-- tab.jsp 이벤트 -->
	   
	<script>
	$(function(){
		
		//로그인전은 before.html, 로그인 후는 after.jsp로드
		loadBeforeAfter();
		
		//로그인메뉴버튼클릭시
		loginClick();
		
		//회원가입메뉴버튼시클릭
		signupClick();
		
		communityClick();
		//로그인,회원가입 제외 각메뉴 클릭시 발생하는 이벤트
	    menuClick();
		
		//탭에서 메뉴클릭시 발생하는 이벤트
		tabMenuClick();
		
		//메뉴에서 커뮤니티 클릭시 탭바뀌는 이벤트
		tabChange();
		
	  })
	</script>

</head>


<body>
	 <header>
	      <h1 class="logo">
	      	<a href="index.jsp">RECO</a>		      
		  </h1>    
	      
	      <nav>
	        <jsp:include page="./menu.jsp"/>
	      </nav> 
     </header>
     
	 <div class="tab">
		<jsp:include page="./tab.jsp"/>
	 </div>
	 
	<section>
		<div class="articles">
		</div>
	</section>
	<div style="position:fixed; bottom:40px; right:50px;">
		<a href="#"><img style="width: " src="./images/navi_top.png" title="위로 가기"></a>
	</div>
<hr>
    <footer>
     	<%@include file="./footer.jsp" %>     <!--맺음말-->  
	</footer>
</body>
</html>