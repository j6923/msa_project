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
		String searchOption = request.getParameter("f"); 
		String word = request.getParameter("q");				
		String path="";		
		System.out.println(searchOption);
		System.out.println(word);
		
		if(searchOption.equals("brd_title")) {
			try {
				List<Board> list = service.findBrdByTitle(word); 
				request.setAttribute("list", list);
				path = "boardlistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				path = "failresult.jsp";
			}
			
			}else if(searchOption.equals("brd_content")) { 
					try {
						List<Board> list = service.findBrdByWord(word); 
						request.setAttribute("list", list);
						path = "boardlistresult.jsp";
					} catch (FindException e) {
						e.printStackTrace();
						path = "failresult.jsp";
					}
			
			}else {
					searchOption = "brd_UNickName";
					try {
						List<Board> list = service.findBrdByUNickName(word); 
						request.setAttribute("list", list);
						path = "boardlistresult.jsp";
					} catch (FindException e) {
						e.printStackTrace();
						path = "failresult.jsp";
					}	
		}
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}
