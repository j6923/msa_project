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
<link rel="stylesheet" href="../css/tab.css">   
<link rel="stylesheet" href="../css/session.css">   



 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="../js/main.js"></script>
<script>
$(function(){
    signup_js();
    login_js();
    toggle_js();
    add_js();
    
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
       <jsp:include page="./tab.jsp"/>
       <ul class="title_list">
       
        <li>
          <div class="title_wrap">
            
                <img id= "food" src="../image/add.jpg" alt="ADD" title="ADD" >
                  <div class="hidden_title">
                    <div class="title_detail">
                      <p class="title_name">+</p>
                    </div>
                  </div>
              </a>
            </div>
            <div class="title_info">
              <p class="title_front">ADD</p>
             
            </div>
          </li>
        

          
          <li>
            <div class="title_wrap">
              
                <img src="../image/add.jpg" alt="ADD" title="ADD" >
                  <div class="hidden_title">
                    <div class="title_detail">
                      <p class="title_name">+</p>
                    </div>
                  </div>
                  </a>
                </div>
            <div class="title_info">
              <p class="title_front">ADD</p>
             
            </div>
          </li>


          
        <li>
          <div class="title_wrap">
          
               <img src="../image/add.jpg" alt="ADD" title="ADD">
                  <div class="hidden_title">
                    <div class="title_detail">
                      <p class="title_name">+</p>
                    </div>
                  </div>
              </a>
            </div>
            <div class="title_info">
              <p class="title_front">WORK OUT</p>
              
            </div>
        </li>
        

        
        <li>
          <div class="title_wrap" id="title4">
           
              <img src="../image/add.jpg" alt="ADD" title="ADD">
               <div class="hidden_title">
                 <div class="title_detail">
                   <p class="title_name">+</p>
                 </div>
               </div>
            </a>
         </div>
         <div class="title_info">
            <p class="title_front">ADD</p>
            
        </div>
      </li>


      
      <li>
        <div class="title_wrap" id="title5">
         
              <img src="../image/add.jpg" alt="ADD" title="ADD">
                <div class="hidden_title">
                  <div class="title_detail">
                    <p class="title_name">+</p>
                  </div>
                </div>
            </a>
          </div>
          <div class="title_info">
            <p class="title_front">ADD</p>
            
          </div>
        </li>
      </ul>
     	
      <section>
     
     <footer>
     <%@include file="./footer.jsp" %>     <!--맺음말-->
      
    </footer>
</body>
</html>