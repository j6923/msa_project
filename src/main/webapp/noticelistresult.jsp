
<%@page import="java.util.Date"%>
<%@page import="com.my.notice.vo.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="./css/noticelist.css" rel=stylesheet>
  

<!--noticelist서블릿에서 결과값 setting해서 noticelistresult.jsp로 보낸값 받아옴-->
<%
List<Notice> list = (List)request.getAttribute("list");
%>
<!--END-->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="./js/noticelist.js"></script>
<script>
	$(function(){
		<!--공지사항 목록에서 글 쓰기 버튼 클릭되었을때 START-->
		
		<!--공지사항 목록에서 글 쓰기 버튼 클릭되었을때 END-->
		
		<!-- 공지사항 목록에서 글 하나 클릭되었을때 START-->  
			noticeDetail();
		<!-- 공지사항 목록에서 글 하나 클릭되었을때 END-->
		
		<!-- 공지사항 목록에서 검색 클릭시 START-->
			searchClick();
		<!-- 공지사항 목록에서 검색 클릭시 END-->
	}); 

</script>
  
  
  

<h1>공지사항</h1>
  
 
	<div class="search" style="float:right;">           
		<select>
			<option value = "all">전체</option>
			<option value = "title">제목</option>
			<option value = "titlecontent">제목+내용</option>
			<option value = "uNickName">닉네임</option>
		</select>
		
		<input type="text" name="word" placeholder="검색어를 입력하세요" >
		
		<button class="searchButton">검색</button>             
	</div>

 



<!--공지사항 클릭시 출력될 공지사항 글 목록 출력 start-->
<div class="ntctop">
	<table class="community_contents">
		<tr>
			<th>글번호</th>
			<th colspan="3">제목</th>
			<th>닉네임</th>
			<th>조회수</th>
			<th>작성일</th>
		</tr>
	</table>    
</div>



<div class="ntclist">
   
<%for(Notice n: list){
  int ntcIdx = n.getNtcIdx();
  String ntcTitle = n.getNtcTitle();
  String ntcuNickName = n.getNtcUNickName();
  int ntcViews = n.getNtcViews();
  Date ntcCreatAt = n.getNtcCreateAt();
%>
	<div id="<%=ntcIdx%>"> 
	 <ul>
	    <li>
		    <%=ntcIdx%>&nbsp;
		    <%=ntcTitle%>&nbsp;
		    <%=ntcuNickName%>&nbsp;
		    <%=ntcViews%>&nbsp;
		    <%=ntcCreatAt%>
	    </li>
	  </ul>
	</div>
<%} %>
</div>



