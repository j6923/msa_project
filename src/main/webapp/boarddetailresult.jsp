<%@page import="com.my.customer.vo.Customer"%>
<%@page import="java.util.List"%>
<%@page import="com.my.board.vo.Comment"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


 <link href="./css/board_content.css" rel=stylesheet>
 <script src="./js/boarddetail.js"></script>



 
<%
Board b = (Board)request.getAttribute("b");
int brdIdx = b.getBrdIdx();
int brdType = b.getBrdType();
String brdTitle = b.getBrdTitle();
String brdUNickname = b.getBrdUNickName();
int brdViews = b.getBrdViews();
int brdThumbUp = b.getBrdThumbUp();
Date brdCreateAt = b.getBrdCreateAt();
String brdContent = b.getBrdContent();
String brdAttachment = b.getBrdAttachment();
List<Comment> comments = b.getComments();
%> 


<script>
$(function(){
	let $formObj = $('fieldset>form');
	//게시글 수정버튼 클릭시
		boardModifyClick();
	//게시글 삭제버튼 클릭시
		boardRemoveClick();	
	//게시글 목록버튼 클릭시
		boardListClick();
	//댓글 등록버튼 클릭시
		commentAddClick();
	//댓글 수정버튼 클릭시
	//	commentModifyClick();	
	//댓글 삭제클릭시
		commentRemoveClick();
});

</script>           
       
          
            
           

      <div class="community_wrap">
       <div class="community_wrap1">
     	 
      	<div class="community_contents">
             	
		  <span id="brdIdx" name="brdIdx">
          	<%=brdIdx%>  
          </span>      
          <span id="brdType">
        	분류: <% if(brdType == 0){%>
		    <%="잡담"%>&nbsp;
		    <% }%><% else if(brdType == 1){%>
		    <%="정보"%>&nbsp;
		    <% }%><% else{ brdType =2;%>
		    <%="기타" %>
		    <%} %> 
          </span>
          
           <span id="brdTitle">

            제목: <%=brdTitle%>   
           </span>
           
           <span > 
            작성자:<%=brdUNickname %>
            </span>
          
            <span >
             조회수 <%=brdViews%>
            </span>    
            
            <span >
             추천수  <%=brdThumbUp%>
            </span>
            
           <span >
             날짜 <%=brdCreateAt %>
            </span>

    </div>
    	<div class="community_contents1">
           

    
            <span id="brdContent">

               내용: 
               <%=brdContent %>
			</span>


            <div class="community_contents1">

            <span id="brdAttachment">

            첨부파일 <%=brdAttachment %>

            </span>

            </div>
            </div>
          
 
         
      <div class="community_comment_contents">
			         <%--추후 댓글은 수정,삭제 버튼 넣으려면 댓글에쓴 태그들 수정해야할 수 있음  --%>
		<div>
		
		
<%
Customer c = (Customer) session.getAttribute("loginInfo"); 
%>
<%
if (session.getAttribute("loginInfo") != null) { 
%>
<%
int uAuthCode = c.getUAuthCode(); 
String uNickName = c.getUNickName(); 
%>
	
     <% if(uNickName.equals(brdUNickname) || uAuthCode == 0 ) {%>
		<button class="board_modify" id="<%=brdIdx %>"  style= "visibility:visible">글 수정</button>
         <button class="board_remove" id="<%=brdIdx %>"  style= "visibility:visible">글 삭제</button>
		
		<%} else{%> 
		 <button class="board_modify" id="<%=brdIdx %>"  style= "visibility:hidden">글 수정</button>
         <button class="board_remove" id="<%=brdIdx %>"  style= "visibility:hidden">글 삭제</button>
		<%
		}
		%>
		
<%} else {  %>
<script>location.href="index.jsp";</script>
<%} %>
				
		
         <button class="board_list">글 목록</button> 
       </div>
 <!--게시글 끝-->     
       
       
       
   <!-- 게시글에 달린 댓글 갯수 -->   
<div class="size">댓글 <%=comments.size()%> </div><br>
   <!-- 게시글에 달린 댓글 갯수 end-->   


    <!-- 댓글작성 시작 -->
 <fieldset>
    <form method="post" action="./cmtadd" autocomplete="off">
   		 <div class="textarea"><textarea rows="2" cols="10" name="cmtContent" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
   			 <button class="comment_add">댓글 등록</button>
    </form>
</fieldset>
    <!-- 댓글작성 끝 -->
         	  

         	  
         	  
         <!-- 댓글 시작 -->	  
         	
         	
         	<%if(comments.size() != 0) {%>
	         	<% for(Comment comment: comments) {       	
	         			int cmtIdx = comment.getCmtIdx();
	         			int cmtParentIdx = comment.getCmtParentIdx();
	        	   		String cmtContent = comment.getCmtContent();         	   		
	        	   		Date cmtCreateAt = comment.getCmtCreateAt();
	        	   		String cmtUNickName = comment.getCmtUNickName();       	   		
	         	%>    
	         	<!-- 대댓글 시작 -->
	         	<%if(cmtParentIdx != 0) {%> &emsp;&emsp;
	         	<div class="community_comment" id="<%=cmtIdx%>">

	         		   		<span class="cmt" id="cmtIdx"><%=cmtIdx %></span><div class="cmt"><%=cmtUNickName %></div> <div class="cmt"><%=cmtCreateAt %></div> <br> 
							&emsp;  &emsp;	<div class="cmt"><%=cmtContent %></div> 
							
							<div class="community_comment_button">
		         				<button class="comment_modify" id="<%=cmtIdx %>">대댓글 수정</button>
								<button class="comment_remove" id="<%=cmtIdx %>">대댓글 삭제</button>
	         				</div>
							
							
							</div>
						
	         
	         	
	         	
					         	
	         	
	         	<!-- 대댓글 끝 -->
	         	<!-- 댓글 시작 -->
	         	<%} else{%>   
	         			<br> <div class="community_comment"id="<%=cmtIdx%>"><span class="cmt" id="cmtIdx"><%=cmtIdx %></span><div class="cmt"><%=cmtUNickName %></div> <div class="cmt"><%=cmtCreateAt %></div><br> 
	         		   <div class="cmt"><%=cmtContent %></div>
	         		   	<div class="community_comment_button">
	         		   		<button class="comment_modify" id="<%=cmtIdx %>">댓글 수정</button>
		         		 	<button class="comment_remove" id="<%=cmtIdx %>">댓글 삭제</button>
		         		 </div>
	         		   </div>
	        	
	         		   
	         		   
	         		   
	         		   <%
	         	}
	         		   %>
	         	<!-- 댓글 끝 -->	      
	         	<%
	         	}
	         	%>
			<%} else{%>
					<span>댓글이 없습니다.</span>
				
			<%} %>
        <!--댓글 끝--> 	           	
</div>  
	</div>
        
      </div>
         
       
	  	         	



 