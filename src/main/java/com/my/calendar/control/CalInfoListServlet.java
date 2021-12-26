package com.my.calendar.control;

import java.io.IOException;
import java.util.List;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class CalInfoListServlet
 */
@WebServlet("/callist")
public class CalInfoListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uIdx = request.getParameter("uIdx");
		int intUIdx = Integer.parseInt(uIdx);
		String calIdx = request.getParameter("calIdx");
		int intCalIdx = Integer.parseInt(calIdx);
		
		
		String quantity = request.getParameter("quantity");
		int intQuantity = Integer.parseInt(quantity);
		String path = "";
		try {
			//비지니스로직호출
			List<CalInfo> list = service.findCalsByUIdxandCalIdx(intUIdx, intCalIdx);
			
			//응답할 결과 요청속성에 설정
			request.setAttribute("list", list);			
			path="after.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			path = "failresult.jsp";
		}
	
		//VIEWER로 이동
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	
	}

}
