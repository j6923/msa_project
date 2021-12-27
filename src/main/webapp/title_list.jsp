<%@page import="com.my.calendar.vo.CalInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<link rel="stylesheet" href="./css/title_list.css">
<script src="./js/callist.js"></script>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}
%> 

<%
if(c != null){
	List<CalInfo> list = (List)request.getAttribute("list");
	int uIdx = c.getUIdx();
	for(CalInfo ci : list){
%> 	
<li>
  <div class="title_wrap" id="title5">
    <a href="calpostlistresult.jsp">
     <img src="./images/calimages/cal_post_<%=uIdx%>_<%=ci.getCalIdx()%>.<%=ci.getCalThumbnail() %>" alt="ADD" title="ADD">
    
        <!-- <div class="hidden_title">
          <div class="title_detail">
            <p class="title_name">+</p>
          </div>
        </div> -->
    </a>
  </div>
  
  <div class="title_info">
    <p class="title_front"><%=ci.getCalCategory() %></p>
    
  </div>
</li>
<%} //end for 
	for(int i=list.size(); i<5; i++){
%> 
<li>
  <div class="title_wrap" id="title5">
    <a href="caltt.html" 
	 		onclick="window.open(this.href, '_blank','top=300, left=600, width=300px, height=300px');return false;">
      <img src="./images/add.jpg" alt="ADD" title="ADD">
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

<%}//end for
%>    
     
	
<%
}
%>