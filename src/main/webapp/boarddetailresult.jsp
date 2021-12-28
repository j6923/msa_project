<%@page import="java.util.List"%>
<%@page import="com.my.board.vo.Comment"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


 <link href="./css/board_content.css" rel=stylesheet>
 
=======
 <link href="css./board_content.css" rel=stylesheet>
 <script src="./js/boarddetail.js"></script>
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>

 
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
	//수정버튼 클릭시
	boardModifyClick();
	//삭제버튼 클릭시
	boardRemoveClick();
	
});
</script>            
       
          
            
           
      <div class="community_wrap">
     	 
      	<div>
          <div class="community_contents">
        	분류: <%=brdType%>  
          </div>
           <div class="community_contents">
            제목: <%=brdTitle%>   
           </div>
           
           <div class="community_contents"> 
            작성자:<%=brdUNickname %>
            </div>
          
            <div class="community_contents">
             조회수 <%=brdViews%>
            </div>    
            
            <div class="community_contents">
             추천수  <%=brdThumbUp%>
            </div>
            
           <div class="community_contents">
             날짜 <%=brdCreateAt %>
            </div>
    </div>
    	<div class="community_contents1">
            <div class="community_contents1">
               내용: 
               <%=brdContent %>
			</div>

            <div class="community_contents1">
            첨부파일 <%=brdAttachment %>
            </div>
            </div>
           </div>
            
        
          
         
      <div class="community_comment_contents">
			         <%--추후 댓글은 수정,삭제 버튼 넣으려면 댓글에쓴 태그들 수정해야할 수 있음  --%>
		
       
         	<div class="size">댓글 <%=comments.size()%> </div><br><br>
         	<div class="textarea"><textarea rows="2" cols="10" placeholder="당신의 소중한 댓글을 적어주세요."></textarea></div>
         	<% for(Comment comment: comments) {       	
         			int cmtIdx = comment.getCmtIdx();
         			int cmtParentIdx = comment.getCmtParentIdx();
        	   		String cmtContent = comment.getCmtContent();         	   		
        	   		Date cmtCreateAt = comment.getCmtCreateAt();
        	   		String cmtUNickName = comment.getCmtUNickName();       	   		
         	%>    
         	<%if(cmtParentIdx != 0) {%> &emsp;&emsp;
         	<div class="community_comment">
         		   		<span><%=cmtUNickName %></span> <span><%=cmtCreateAt %></span> <br> 
         	&emsp;  &emsp;	<span><%=cmtContent %></span> </div>
         		
         	<%} else{%>   <br> <div class="community_comment"><span><%=cmtUNickName %></span> <span><%=cmtCreateAt %></span><br> 
         		   <span><%=cmtContent %></span></div> 
         		 
         		   <%
         	}
         		   %>   
         	<%
         	}
         	%>
         	           	
</div>
	         	댓글 <%=comments.size()%> <br><br>
	         	<% for(Comment comment: comments) {       	
	         			int cmtIdx = comment.getCmtIdx();
	         			int cmtParentIdx = comment.getCmtParentIdx();
	        	   		String cmtContent = comment.getCmtContent();         	   		
	        	   		Date cmtCreateAt = comment.getCmtCreateAt();
	        	   		String cmtUNickName = comment.getCmtUNickName();       	   		
	         	%>     
	         	<%if(cmtParentIdx != 0) {%> &emsp;&emsp;
	         		   		<span><%=cmtUNickName %></span> <span><%=cmtCreateAt %></span> <br> 
	         	&emsp;  &emsp;	<span><%=cmtContent %></span> <br>
	         		
	         	<%} else{%>   <br> <span><%=cmtUNickName %></span> <span><%=cmtCreateAt %></span><br> 
	         		   <span><%=cmtContent %></span> <br><br>
	         		 
	         		   <%
	         	}
	         		   %>   
	         	<%
	         	}
	         	%>
         
            <button class="board_modify" id="<%=brdIdx%>">글 수정</button>
            <button class="board_remove" id="<%=brdIdx %>">글 삭제</button> 	          	

</html>
 