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
	private CalendarService service = CalendarService.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		int status = 0; //실패
		String resultmsg = "";
		String path = "";
		
		//1. 로그인여부 확인 
				
			if(c == null) {
				resultmsg = "로그인하세요";
			}else {
				//2. 캘린더 존재여부 확인
				Map<String, String> calendar= (Map)session.getAttribute("calendar");
				if(calendar == null ) {
					resultmsg = "캘린더가 없습니다";
				}else {
					String calCategory = request.getParameter("calCategory");
					String calThumbnail = request.getParameter("calThumbnail");
					
					CalInfo calinfo = new CalInfo();
					calinfo.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
					calinfo.setCalCategory(calCategory);
					calinfo.setCalThumbnail(calThumbnail);

					try {
						service.addCal(calinfo); //calInfo를 service의 add()메소드 인자로 사용
						request.setAttribute("status", 1);
						resultmsg = "캘린더 생성 성공";
						path="after.jsp";
					} catch (AddException e) {
						System.out.println(e.getMessage());
						
						resultmsg = "캘린더 생성 실패:" + e.getMessage();
						request.setAttribute("status", 0);
						request.setAttribute("msg", e.getMessage());
						path="failresult.jsp";
					}
				}
				request.setAttribute("status", status);
				request.setAttribute("resultmsg", resultmsg);
				RequestDispatcher rd = request.getRequestDispatcher(path);
				rd.forward(request, response);
				
				
			}
    }
}
