<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>RECO</title>
	<meta charset="UTF-8">
	
	<link rel="stylesheet" href="./css/header.css">   
	<link rel="stylesheet" href="./css/section.css">
	<link rel="stylesheet" href="./css/footer.css"> 
	  
	<style>
		header, section,footer{
	    	width:100%;
	    }
	    
	</style> 
	
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="./js/menu.js"></script><!-- menu.jsp -->
	<script src="./js/index.js"></script><!-- index.jsp 로딩시 이벤트 -->
	   
	<script>
	$(function(){
	    menuClick();
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


	<section>
		<div class="articles">
			
		
		<h2>여기에 우리사이트 소개 넣으면 됩니다. 로그인이랑 회원창 팝업으로 만들어야해요. 로고도 중앙으로 옮기기</h2> 
		
		
		</div>
	</section>



    <footer>
     	<%@include file="./footer.jsp" %>     <!--맺음말-->  
	</footer>
</body>
</html>