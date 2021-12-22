<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>RECO</title>
<meta charset="UTF-8">

<link rel="stylesheet" href="../css/index.css">   
<link rel="stylesheet" href="../css/menu.css">
<link rel="stylesheet" href="../css/footer.css">   
 



 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="./js/main.js"></script>
<script>
      $(function(){
    	  signup_js();
          login_js();
          menuClick();
        
      });
   
    </script>
</head>
<body>
	 <header>
      <div class="headerbox"><p class="headertext"><a href="./main.html" class="link">RECO</a></p></div>
       <session>
       
        <jsp:include page="./menu.jsp"/>
        
        
      </session>
     </header>
     <section>
       
       
     	
      <section>
     
     <footer>
     <%@include file="./footer.jsp" %>     <!--맺음말-->
      
    </footer>
</body>
</html>