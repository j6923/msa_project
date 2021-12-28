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
%>
	
	<%for(CalPost cp : list){
	 String calMainImg = cp.getCalImg1();
/* 	 Date month = cp.getCalDate();
	 Date year = cp.getCalDate();
	 Date day = cp.getCalDate(); */
	%> 
	<div class="container">
	    <div class="body">
	        <div id="calendarForm">
		        <div>  
		        	 <script src="./js/calpostlist.js"></script>
		        	 <a>
		        	 <img src="./images/calimages/c_<%=uIdx%>.<%=cp.getCalImg1() %>">
		       		 </a> 
		       		
		      	</div>
		    </div>
		</div>
	</div>
<%}%> <% } %> 

<script>
	$(function(){
		<!--캘린더 달력 화면에서 날짜 칸 클릭 되었을 때 start-->
		daySelectClick()
		<!--캘린더 달력 화면에서 날짜 칸 클릭 되었을 때 end-->
	});
</script>