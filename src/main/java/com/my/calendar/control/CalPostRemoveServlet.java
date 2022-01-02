package com.my.calendar.control;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.my.calendar.service.CalendarService;
import com.my.exception.RemoveException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalPostRemoveServlet
 */
@WebServlet("/calpostremove")
public class CalPostRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path="";
		String resultmsg="";
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		String calDate = request.getParameter("calDate");	
		Date CalDate = null;
			try {
				CalDate = sdf.parse(calDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				service.removeCalPost(calDate);
				System.out.println("캘린더 글 삭제 성공");
				
				resultmsg="삭제 성공";
				request.setAttribute("status", 1);
				path="callistresult.jsp";
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
