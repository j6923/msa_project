

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.my.notice.vo.Notice"%>
<%
Notice n= (Notice)request.getAttribute("n");
int ntcIdx = n.getNtcIdx();
String ntcTitle = n.getNtcTitle();
String  ntcContent = n.getNtcContent();
String ntcAttachment = n.getNtcAttachment();
Date ntcCreateAt = n.getNtcCreateAt();
String ntcUNickname = n.getNtcUNickName();
int ntcViews = n.getNtcViews();
%>
    
    <link href="./css/noticedetail.css" rel=stylesheet>
 	<script src="./js/noticedetail.js"></script>
	<script>
	$(function(){
		//수정버튼 클릭시
			noticeModifyClick();
		//삭제버튼 클릭시
			noticeRemoveClick();	
		//목록버튼 클릭시
			noticeListClick();
	});
	</script>
	
    
        
		<ul>
			<li>
				<span id="ntcIdx"><%=ntcIdx %></span>
				<span id="ntcTitle">제목: <%= ntcTitle%></span>
				<span> 작성자:<%=ntcUNickname %></span>
				<span> 날짜 <%=ntcCreateAt %></span>
				<span id="ntcContent"> 내용: 
				<%=ntcContent %></span>
				<span id="ntcAttachment">첨부파일 <%=ntcAttachment %></span>
				<span id="ntcViews">조회수: <%=ntcViews %></span>
			<li>
		</ul>	
		<button class="notice_modify" id="<%=ntcIdx %>" >글 수정</button>
         <button class="notice_remove" id="<%=ntcIdx %>" >글 삭제</button>
         <button class="notice_list">글 목록</button>
	  	
         
        
         
		
