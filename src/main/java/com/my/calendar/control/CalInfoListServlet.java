package com.my.calendar.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
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
@WebServlet("/callist")   
public class CalInfoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		/*--샘플 로그인된 정보 -- */
//		Customer sampleCustomer = new Customer();
//		sampleCustomer.setuIdx(3);
//		session.setAttribute("loginInfo", sampleCustomer);
		/*-------------------*/
		
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
		String path = "";
		
//		CalInfo calinfo = (CalInfo)request.getAttribute("uIdx");
//		System.out.println(calinfo); //null
//		int intUIdx = Integer.parseInt(request.getParameter("uIdx"));
		
//		String calIdx = request.getParameter("calIdx");
//		int intCalIdx = Integer.parseInt(calIdx);
		
		try {
		
		//비지니스로직호출
			List<CalInfo> list = service.findCalsByUIdx(uIdx);
			
			//응답할 결과 요청속성에 설정
			request.setAttribute("list", list);			
			
		} catch (FindException e) {
			e.printStackTrace();
			//path = "failresult.jsp";
			request.setAttribute("list", new ArrayList<CalInfo>());		
//			request.setAttribute("msg", e.getMessage());
		}
		path="callistresult.jsp";
		//VIEWER로 이동
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}
