<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<link rel="stylesheet" href="./css/tab.css">

<ul>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}
%> 
</ul>


<%
if(c != null){
%>   
	<ul class="caltab">
	    <li><a href="#" id="caladd1">ADD+</a></li>
	    <li><a href="#" id="caladd2">ADD+</a></li>
	    <li><a href="#" id="caladd3">ADD+</a></li>
	    <li><a href="#" id="caladd4">ADD+</a></li>
	    <li><a href="#" id="caladd5">ADD+</a></li>
    </ul>
    <ul class="communitytab">
	    <li><a href="ntclist">공지사항</a></li>
		<li><a href="faq.html">FAQ</a></li>
		<li><a href="brdlist">자유게시판</a></li>
	</ul>
<%
}
%>



         
