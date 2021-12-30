

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
	<div class="ntcDetail">
    	<h1>공지사항 </h1>
        
		<ul class="ntcDetail">
			<li>
			<div class="ntcIdx">글번호 : <span id="ntcIdx"><%=ntcIdx %></span></div>
			<div class="ntcTitle"><strong>제목:<span id="ntcTitle"> <%=ntcTitle%></span></strong></div>
			<div class="ntcUNickname">작성자:<span> <%=ntcUNickname %></span></div>
			<div class="ntcCreateA">작성일 :<span> <%=ntcCreateAt %></span></div>
			<div class="ntcViews">조회수:<span id="ntcViews"> <%=ntcViews %></span></div>
			<hr>
			<div class="ntcContent">내용: <span id="ntcContent"> 
				<%=ntcContent %></span></div>
			<hr>
			<div class="ntcAttachment">첨부파일<span id="ntcAttachment"> <%=ntcAttachment %></span></div>
			
			<li>
		</ul>	
		
		
		<div class="NoticeDetailButton">
		<button class="notice_modify" id="<%=ntcIdx %>" >글 수정</button>
         <button class="notice_remove" id="<%=ntcIdx %>" >글 삭제</button>
         <button class="notice_list">글 목록</button>
         </div>
	 </div> 	
         
        
         
		
