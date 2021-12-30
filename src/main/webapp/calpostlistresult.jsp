<%@page import="com.my.calendar.vo.CalPost"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>



<meta name="viewport" content="width=device-width">
<title>RECO</title>
<link rel=stylesheet href="./css/calendar.css" >
<script src="./js/calendar.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 

	List<CalPost> list = (List)request.getAttribute("list");
	
%>	<%-- <%for(CalPost cp : list){
	           
	%>   --%>
	<!-- <div class="container">
	    <div class="body"> -->
	    	
	        <div id="calendarForm">
		        <div>  
		        	 <a>
		        	 	<%-- <img src="./images/calimages/c_<%=uIdx%>.<%=calMainImg%>"> --%>
		       		 </a>  
		       		
		      	</div>
		    </div>
			    
		<!-- </div>
	</div> -->
<%--  <%} %>  <% } %>  --%>
