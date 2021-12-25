<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script src="./js/tab.js"></script>
<link rel="stylesheet" href="./css/tab.css">

<ul>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}
%> 


<%
if(c != null){
%>    
    <li><a href="#" class="menu_link yellow" id="menu1">ADD</a></li>
	<li><a href="#" class="menu_link yellow" id="menu2">ADD</a></li>
	<li><a href="#" class="menu_link yellow" id="menu3">ADD</a></li>
	<li><a href="#" class="menu_link yellow" id="menu4">ADD</a></li>
	<li><a href="#" class="menu_link yellow" id="menu5">ADD</a></li>
<%
}
%>    
</ul>


         
