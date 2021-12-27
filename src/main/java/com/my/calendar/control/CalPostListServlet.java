package com.my.calendar.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalPost;
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CalInfoListServlet
 */
@WebServlet("/calmain")  //서블릿url 경로
public class CalPostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();
	
	/**get요청에 대한 응답을 주는 메소드
	 * @param calIdx 
	 * @param year 
	 * @param month 
	 * @param resultMsg */
	protected void doGet(HttpServletRequest request, HttpServletResponse response, int calIdx, Date year, Date month) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
		String path = "";
		
		
		try {
			List<CalPost> list = service.findCalsByUIdx (uIdx, calIdx, year, month);
			request.setAttribute("list", list);			
			
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("list", new ArrayList<CalPost>());		

		}
		path="callistresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}
