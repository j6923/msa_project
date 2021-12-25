<%@page import="java.util.Date"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="./css/boardlist.css" rel=stylesheet>


<!--boardlist서블릿에서 결과값 setting해서 boardlistresult.jsp로 보낸값 받아옴-->
<%
List<Board> list = (List)request.getAttribute("list");
%>
<!--END-->

<script>

</script>


<h1>자유게시판</h1>

 
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

 



<!-- 자유게시판 클릭시 출력될 자유게시판글 목록 출력 start-->
<div class="boardtop">
	<table class="community_contents">
		<tr>
			<th>글번호</th>
			<th colspan="3">제목</th>
			<th>닉네임</th>
			<th>조회수</th>
			<th>댓글수</th>
			<th>작성일</th>
		</tr>
	</table>    
</div>



<div class="ntclist">
   
<%for(Board b: list){
  int brdIdx = b.getBrdIdx();
  String brdTitle = b.getBrdTitle();
  String BrdUNickName = b.getBrdUNickName();
  int brdViews = b.getBrdViews();
  int cmtCount = b.getCmtCount();
  Date brdCreatAt = b.getBrdCreateAt();
%>
	<div id="<%=brdIdx%>"> 
	 <ul>
	    <li>
		    <%=brdIdx%>&nbsp;
		    <%=brdTitle%>&nbsp;
		    <%=BrdUNickName%>&nbsp;
		    <%=brdViews%>&nbsp;
		    <%=cmtCount%>&nbsp;
		    <%=brdCreatAt%>
	    </li>
	  </ul>
	</div>
<%} %>
</div>
<!--  end  --> 

