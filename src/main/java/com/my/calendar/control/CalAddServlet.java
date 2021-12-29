package com.my.calendar.control;

import java.io.IOException;
import java.util.Map;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
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
 * Servlet implementation class CalAddServlet
 */
@WebServlet("/caladd")
public class CalAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CalendarService service = CalendarService.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path = "";
		
//		int uIdx = c.getUIdx();
//		c.setUIdx(uIdx);
			
//			String uIdx = request.getParameter("uIdx");
//			int intUIdx = Integer.parseInt(uIdx);
//			String calIdx = request.getParameter("calIdx");
//			int intCalIdx = Integer.parseInt(calIdx);
			String calCategory = request.getParameter("calCategory");
			String calThumbnail = request.getParameter("calThumbnail");
			
			CalInfo ci = new CalInfo();
			ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
//			ci.setCalIdx(intCalIdx);
			ci.setCalCategory(calCategory);
			ci.setCalThumbnail(calThumbnail);

			try {
				CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
				System.out.println(calinfo);
				request.setAttribute("ci", calinfo);
					
				path="callistresult.jsp";
			} catch (AddException e) {
				System.out.println(e.getMessage());
				//resultmsg = "캘린더 생성 실패:" + e.getMessage();
				e.getMessage();
				//request.setAttribute("msg", e.getMessage());
				//path="failresult.jsp";
			}
		
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
			
			
	}

}
