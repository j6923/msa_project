<!-- ê³µì§ì¬í­ (íì)íì´ì§ìëë¤. -->

<%@page import="java.util.Date"%>
<%@page import="com.my.notice.vo.Notice"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="./css/notice(member).css" rel=stylesheet>
  </head>

<!--noticelist서블릿에서 결과값 setting해서 noticelistresult.jsp로 보낸값 받아옴-->
<%
List<Notice> list = (List)request.getAttribute("list");
%>
<!--END-->

     

<!--  공지사항 목록에서 글 하나 클릭되었을때 START-->  
<!-- $(function(){
  noticelistresult_js();
}); -->
<!-- 공지사항 목록에서 글 하나 클릭되었을때 END-->


  <body>
    <section>
      <div class="wrap">
        <ul class="menu">
        <!--   <li class="menu_item"><a href="#" class="menu_link yellow">공지사항</a></li>
          <li class="menu_item"><a href="#" class="menu_link yellow">FAQ</a></li>
          <li class="menu_item"><a href="#" class="menu_link yellow">자유게시판</a></li> -->
     		
     		<!-- tab.jsp include 시켜줘야될듯!!!!!!!!! -->
        </ul>
      </div>
      
      
<h1>공지사항</h1>

  <!-- 공지사항 목록에서 검색 클릭시 START-->
<!--   $(function(){
    noticelistresult_js();
  }); -->

  <!-- 공지사항 목록에서 검색 클릭시 END-->

  
  <form >   
      <div class="contents">
          
            <div class="search" style="float:right;">
              
              <select name="f">
                <option value = "none">전체</option>
                <option value = "title">제목</option>
                 <option value = "titlecontent">제목+내용</option>
                <option value = "uNickName">닉네임</option>
                </select>

              <input type="text" name="q" value="" placeholder="검색어를 입력하세요" >
            
            
              <!-- <div class="button"> -->
                <button class="search_button" type="submit">검색</button>
                
              <!-- </div> -->
            </div>
        </form>
 


<!--공지사항 클릭시 출력될 공지사항 글 목록 출력 start-->
<div class="ntclist">
  <table class="community_contents">
    <tr>
      <th>글번호</th>
      <th colspan="3">제목</th>
      <th>닉네임</th>
      <th>조회수</th>
      <th>작성일</th>
    </tr>
    
<%for(Notice n: list){
  int ntcIdx = n.getNtcIdx();
  String ntcTitle = n.getNtcTitle();
  String ntcUNickName = n.getNtcUNickName();
  int ntcViews = n.getNtcViews();
  Date ntcCreateAt = n.getNtcCreateAt();
%>
<a >
<div id="<%=ntcIdx%>"> 
  <tr>
    <td><%=ntcIdx%></td>
    <td colspan="3"><%=ntcTitle%></td>
    <td><%=ntcUNickName%></td>
    <td><%=ntcViews%></td>
    <td><%=ntcCreateAt%></td>
  </tr>
</div>
</a>
<%} %>
</div>
<!--커뮤니티/공지사항 클릭시 출력될 공지사항 글 목록 출력 end-->


		        </table>
		      </div>
		   </div>
		</div>  
    </section>
</body>
