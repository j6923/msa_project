<%@page import="java.util.List"%>
<%@page import="com.my.board.vo.Comment"%>
<%@page import="com.my.board.vo.Board"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="css./board_content.css" rel=stylesheet>
 
<%
Board b = (Board)request.getAttribute("b");
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
            
       
          
            
           
      <div class="community_contents">
          <div>
        	분류: <%=brdType%>  
          </div>
           <div>
            제목: <%=brdTitle%>   
           </div>
           
           <div> 
            작성자:<%=brdUNickname %>
            </div>
          
            <div>
             조회수 <%=brdViews%>
            </div>    
            
            <div>
             추천수  <%=brdThumbUp%>
            </div>
            
           <div>
             날짜 <%=brdCreateAt %>
            </div>
    
            <div>
               내용: 
               <%=brdContent %>
			</div>

            <div>
            첨부파일 <%=brdAttachment %>
            </div>
            
        </div>   
          
  <hr>        
      <div class="community_comment contents">
			         
         </div>
         	댓글 <%=comments.size()%> <br><br>
         	<% for(Comment comment: comments) {       	   	
        	   		String cmtContent = comment.getCmtContent();        	   	
        	   		Date cmtCreateAt = comment.getCmtCreateAt();
        	   		String cmtUNickName = comment.getCmtUNickName();       	   		
         	%>     
         		   <span><%=cmtUNickName %></span> <span><%=cmtCreateAt %></span> 
         		   <div><%=cmtContent %></div> <br>
         		   
         	<%
         	}
         	%>
         	           	


</html>
 