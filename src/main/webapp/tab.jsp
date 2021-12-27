<%@page import="com.my.calendar.vo.CalInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.my.customer.vo.Customer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="./css/tab.css">

<%
Customer c = (Customer)session.getAttribute("loginInfo"); 
if(c == null){ //로그인 안된 경우
%>

<%
}else if(c != null){
%>
<ul class="caltab">
<%	List<CalInfo> list = (List)request.getAttribute("list");
	
for(CalInfo ci : list){
%>   <li><a href="calpostlistresult.jsp" id="trigger_cal"><%=ci.getCalCategory() %></a></li>
<%} //end for 
	for(int i=list.size(); i<5; i++){
%>
	 <li><a href="caltt.html" 
	 		onclick="window.open(this.href, '_blank','top=300, left=600, width=300px, height=300px');return false;">ADD+</a></li>
<%}//end for
%>
</ul> 
    <ul class="communitytab">
	    <li><a href="ntclist">공지사항</a></li>
		<li><a href="faqlist.html">FAQ</a></li>
		<li><a href="brdlist">자유게시판</a></li>
	</ul>
<%
}
%>