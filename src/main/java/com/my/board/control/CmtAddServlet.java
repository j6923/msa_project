
package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;

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
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path = "";
		

			String brdIdx=request.getParameter("brdIdx"); 
			int intBrdIdx = Integer.parseInt(brdIdx);
		/*	String cmtIdx=request.getParameter("cmtIdx");
			int intCmtIdx = Integer.parseInt(cmtIdx);	*/		
			String cmtContent=request.getParameter("cmtContent");  
			/* String cmtParentIdx=request.getParameter("cmtParentIdx"); */
			String cmtUNickName = c.getUNickName();
			
			int cmtParentIdx = 0;
			
				System.out.println(cmtParentIdx);
				
			
				Comment comment = new Comment();
				comment.setBrdIdx(intBrdIdx);
			
				comment.setCmtContent(cmtContent);
				comment.setCmtParentIdx(cmtParentIdx);	
				comment.setCmtUNickName(cmtUNickName);
				
				try{
				    Board board = service.addCmt(comment);
					request.setAttribute("b", board);				
					path="boarddetailresult.jsp";
				} catch(AddException e){
					e.getStackTrace();
					path = "failresult.jsp";
				}
					
	
	RequestDispatcher rd = request.getRequestDispatcher(path);
	rd.forward(request, response);

}

}