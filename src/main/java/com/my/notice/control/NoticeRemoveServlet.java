package com.my.notice.control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.exception.RemoveException;
import com.my.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeRemoveServlet
 */
@WebServlet("/ntcremove")
public class NoticeRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance();     

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
			String path="";
			String ntcIdx = request.getParameter("ntcIdx");	
			int intNtcIdx = Integer.parseInt(ntcIdx);
			
			try {
				service.removeNtc(intNtcIdx);
				System.out.println("글 삭제 성공");
				
				request.setAttribute("status", 1);
				path="./ntclist";
			} catch (RemoveException e) {
				System.out.println(e.getMessage());
				request.setAttribute("status", 0);
				request.setAttribute("msg", e.getMessage());
				path="failresult.jsp";
			}
			
			
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
	}
}
		
	


