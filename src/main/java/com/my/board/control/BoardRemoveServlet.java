package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.exception.RemoveException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardRemoveServlet
 */
@WebServlet("/brdremove")
public class BoardRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path="";
		String resultmsg="";
		String brdIdx = request.getParameter("brdIdx");	
		int intBrdIdx = Integer.parseInt(brdIdx);
		

		try {
			service.removeBrd(intBrdIdx);
			System.out.println("글 삭제 성공");
			
			resultmsg="삭제 성공";
			request.setAttribute("status", 1);
			path="brdlist";
		} catch (RemoveException e) {
			System.out.println(e.getMessage());
			resultmsg="삭제 실패";
			request.setAttribute("status", 0);
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		}
		
		request.setAttribute("resultmsg", resultmsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}