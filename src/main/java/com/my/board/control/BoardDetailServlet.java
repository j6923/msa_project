package com.my.board.control;

import java.io.IOException;

import com.my.board.service.BoardService;
import com.my.board.vo.Board;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/brddetail")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String brdIdx = request.getParameter("brdIdx");
		int intBrdIdx = Integer.parseInt(brdIdx);
		String path = "";
		
		//비즈니스로직 호출
		Board b;
		try {
			b = service.findBrdByIdx(intBrdIdx);
			request.setAttribute("b", b);
			path="boarddetailresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			path="failresult.jsp";
		}

		//VIEWER로 이동
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
