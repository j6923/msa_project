package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
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
 * Servlet implementation class BoardAddServlet
 */
@WebServlet("/brdadd")
public class BoardAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService service = BoardService.getinstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path = "";
		
			String brdType=request.getParameter("brdType");
			int intBrdType = Integer.parseInt(brdType);
			String brdTitle=request.getParameter("brdTitle");
			String brdContent=request.getParameter("brdContent");
			String brdAttachment=request.getParameter("brdAttachment");
			String brdUNickName = c.getUNickName();
			Board b = new Board();
			b.setBrdIdx(intBrdType);
			b.setBrdTitle(brdTitle);
			b.setBrdContent(brdContent);
			b.setBrdAttachment(brdAttachment);
			b.setBrdUNickName(brdUNickName);
		
		try{	
			Board board= service.addBrd(b);
			System.out.println(board);
			request.setAttribute("b",board);
			path="boardlistresult.jsp";
		} catch(AddException e){
			e.getStackTrace();
			path = "failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
		}
	
					
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	
	}

}