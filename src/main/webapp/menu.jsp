<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<ul>
<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>
    <li><a class="login" href="login.html">로그인</a></li>
    <li><a class="signup" href="signup.html">회원가입</a></li>
    <li><a href="login.html">커뮤니티</a></li>
<%
}else{
%>  <li><%=c.getUNickName()%>님 반갑습니다.</li>
<%
}
%> 
<%
if(c != null){
%>    
    <li><a href="logout">로그아웃</a></li>
    <li><a href="mypage">마이페이지</a></li>
    <li><a href="ntclist">커뮤니티</a></li>
<%
}
%>    
  
</ul>

