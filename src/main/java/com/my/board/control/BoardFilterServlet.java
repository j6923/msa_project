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
 * Servlet implementation class BoardFilterServlet
 */
@WebServlet("/boardfilter")
public class BoardFilterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService service = BoardService.getinstance();
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String brdType = request.getParameter("f"); 
		int intBrdType = Integer.parseInt(brdType);
		String path="";		
		System.out.println(intBrdType);
		
		if(intBrdType == 0) {
			try {
				List<Board> list = service.findBrdByType(intBrdType); 
				request.setAttribute("list", list);
				path = "boardlistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				path = "failresult.jsp";
			}
			
			}else if(intBrdType == 1) { 
					try {
						List<Board> list = service.findBrdByType(intBrdType); 
						request.setAttribute("list", list);
						path = "boardlistresult.jsp";
					} catch (FindException e) {
						e.printStackTrace();
						path = "failresult.jsp";
					}
					
			}else if(intBrdType == 2) { 
				try {
					List<Board> list = service.findBrdByType(intBrdType); 
					request.setAttribute("list", list);
					path = "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					path = "failresult.jsp";
				}
			}else {
					intBrdType = 3;
					try {
						List<Board> list = service.findBrdAll(); 
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