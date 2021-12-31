package com.my.calendar.control;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
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
@WebServlet("/calpost")  //서블릿url 경로
public class CalPostListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo"); // 세션에 저장되어있는 로그인정보를 가져오기
		//int uIdx = c.getUIdx();
		CalInfo calinfo = new CalInfo();
		calinfo.setCustomer(c);
		
		String cIdx = request.getParameter("calIdx");
		calinfo.setCalIdx(Integer.parseInt(cIdx));
		
		String calDate = request.getParameter("dateValue"); //요청전달데이터로 년/월정보가 없으면 오늘날짜기준의 년/월값으로 설정한다 
		if(calDate == null ||calDate.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			calDate = sdf.format(new Date());
		}
		
		String path = "";
		System.out.println("in CalPostListServlet cIdx = " + cIdx +", calDate=" + calDate );
		try {
			
			List<CalPost> list = service.findCalsByDate(calinfo,calDate);
			request.setAttribute("list", list);			
			path="calpostlistresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
//			path="failresult.jsp";
			path="calpostlistresult.jsp";	
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

	
}
