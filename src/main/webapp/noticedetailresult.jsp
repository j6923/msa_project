

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.my.notice.vo.Notice"%>
<%
Notice n= (Notice)request.getAttribute("n");
String ntcTitle = n.getNtcTitle();
String  ntcContent = n.getNtcContent();
String ntcAttachment = n.getNtcAttachment();
Date ntcCreateAt = n.getNtcCreateAt();
String ntcUNickname = n.getNtcUNickName();
%>
    
    <meta name="viewport" content="width=device-width">
    <title>RECO</title>
    <link href="css./notice_content.css" rel=stylesheet>
 
 
            
       
          
            
           
        <div class="community_contents">
          <div>
            제목: <%= ntcTitle%>   
           </div>
           <div> 
            작성자:<%=ntcUNickname %>
            </div>
          
          
           <div>
             날짜 <%=ntcCreateAt %>
            </div>
                 
          
         
            <div>
               내용: 
               <%=ntcContent %>
		</div>

            <div>
            첨부파일 <%=ntcAttachment %>
            </div>
            
        </div>   
          


</html>
