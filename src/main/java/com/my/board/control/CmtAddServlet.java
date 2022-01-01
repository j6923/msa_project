
package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
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
   
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("cmt서블릿초기");
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path = "";
		
		System.out.println("cmt서블릿초기");	
			String brdIdx=request.getParameter("brdIdx"); 
			int intBrdIdx = Integer.parseInt(brdIdx);
			System.out.println(intBrdIdx);	
			String cmtContent=request.getParameter("cmtContent"); 
			System.out.println(cmtContent);
			String cmtParentIdx = request.getParameter("cmtParentIdx");
			System.out.println(cmtParentIdx);
			int intCmtParentIdx = Integer.parseInt(cmtParentIdx);
			System.out.println(intCmtParentIdx);
			String cmtUNickName = c.getUNickName();
			
	
			
			Comment comment = new Comment();
			comment.setBrdIdx(intBrdIdx);
			comment.setCmtContent(cmtContent);	
			comment.setCmtParentIdx(intCmtParentIdx);	
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