
package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Comment;
import com.my.customer.vo.Customer;
import com.my.exception.AddException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CmtAddServlet
 */
@WebServlet("/cmtadd")
public class CmtAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String resultmsg = "";
		String path = "";
		
		//로그인여부
		if(c == null) {
			resultmsg = "로그인하세요";
		}else {
			String brdIdx=request.getParameter("brdIdx");
			int intBrdIdx = Integer.parseInt(brdIdx);
			String cmtIdx=request.getParameter("cmtIdx");
			int intCmtIdx = Integer.parseInt(cmtIdx);			
			String cmtContent=request.getParameter("cmtContent");
			String cmtParentIdx=request.getParameter("cmtParentIdx");
			int intCmtParentIdx = Integer.parseInt(cmtParentIdx);			
			String cmtUNickName = c.getUNickName();
			Comment comment = new Comment();
			comment.setBrdIdx(intBrdIdx);
			comment.setCmtIdx(intCmtIdx);
			comment.setCmtContent(cmtContent);
			comment.setCmtParentIdx(intCmtParentIdx);
			comment.setCmtUNickName(cmtUNickName);
			

			try{
				service.addCmt(comment);
				request.setAttribute("status", 1);
				resultmsg="댓글 추가성공";
				path="boarddetailresult.jsp";
			} catch(AddException e){
				e.getStackTrace();
				request.setAttribute("status", 0);
				resultmsg = e.getMessage();
				
	}

}
		request.setAttribute("resultmsg", resultmsg);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
