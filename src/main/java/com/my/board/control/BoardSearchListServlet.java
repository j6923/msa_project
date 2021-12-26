package com.my.board.control;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class BoardSearchListServlet
 */
@WebServlet("/brdsearch")
public class BoardSearchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchOption_ = request.getParameter("f");
		String word_ = request.getParameter("q");
		
		String searchOption = "brd_title";
		if(searchOption_ != null)
			searchOption = searchOption_;
		
		String word = "";
		if(word_ != null)
			word = word_;
		
				
		String path="";		
		List<Board> list;
		try {
			list = service.findBrdByWord(searchOption, word); 
		
			request.setAttribute("list", list);
			path = "boardlistresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			path = "failresult.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}
