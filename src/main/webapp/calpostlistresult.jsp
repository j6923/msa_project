<%@page import="com.my.calendar.vo.CalPost"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>



<meta name="viewport" content="width=device-width">
<title>RECO</title>
<link rel=stylesheet href="./css/calendar.css" >

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}
%> 

<%
if(c != null){
	List<CalPost> list = (List)request.getAttribute("list");
	int uIdx = c.getUIdx();	
	//for(CalPost cp : list){
}%>

	<div class="container">
	    <div class="body">
	        <div id="calendarForm">
		        <div>  
		        	<script src="./js/calendar.js"></script>
		        	 <%-- <a>
		        	 <img src="./images/calimages/c_<%=cp.getCalDate()%>.<%=cp.getCalMainImg() %>">
		       		 </a> --%>
		       		
		      	</div>
		    </div>
		</div>
	</div>

	