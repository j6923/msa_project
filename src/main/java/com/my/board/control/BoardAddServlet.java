package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
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
 * Servlet implementation class BoardAddServlet
 */
@WebServlet("/brdadd")
public class BoardAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BoardService service = BoardService.getinstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
			String brdType=request.getParameter("brdType");
			int intBrdType = Integer.parseInt(brdType);
			String brdTitle=request.getParameter("brdTitle");
			String brdContent=request.getParameter("brdContent");
			String brdAttachment=request.getParameter("brdAttachment");
			String brdUNickName = c.getUNickName();
			Board b = new Board();
			b.setBrdIdx(intBrdIdx);
			b.setBrdIdx(intBrdType);
			b.setBrdTitle(brdTitle);
			b.setBrdContent(brdContent);
			b.setBrdAttachment(brdAttachment);
			b.setBrdUNickName(brdUNickName);
		
		try{	
			service.addBrd(b);
			request.setAttribute("status", 1);
			resultmsg="글 추가성공";
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