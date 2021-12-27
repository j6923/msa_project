package com.my.calendar.control;

import java.io.IOException;
import java.util.Date;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalPost;
import com.my.exception.AddException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalPostAddServlet
 * @param <CalPost>
 */
@WebServlet("/CalPostAdd") //서블릿url 경로
public class CalPostAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();

	
	/**get요청에 대한 응답을 주는 메소드
	 * @param resultMsg */
	protected void doPost (HttpServletRequest request, HttpServletResponse response, String resultMsg) throws ServletException, IOException {
		String calMainImg = request.getParameter("calMainImg");
		java.sql.Date calDate = request ("calDate");
		String calMemo = request.getParameter("calMemo");
		String calImg1 = request.getParameter("calImg1");
		String calImg2 = request.getParameter("calImg2");
		String calImg3 = request.getParameter("calImg3");
		CalPost calpost  = new CalPost();
	    calpost.setCalMainImg(calMainImg);
	    calpost.setCalDate(calDate);
	    calpost.setCalMemo(calMemo);
	    calpost.setCalImg1(calImg1);
	    calpost.setCalImg2(calImg2);
	    calpost.setCalImg3(calImg3);
		
	    String path = "";
		String msg = "";
		
		try {
			service.addCalPost(calpost);
			System.out.println("캘린더 글이 등록되었습니다");
			resultMsg = "등록 성공";
			request.setAttribute("status", 1);
			path = "callistresult.jsp";
		
		} catch (AddException e) {
			System.out.println(e.getMessage());
			resultMsg="등록 실패";
            request.setAttribute("status", 0);
            request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		}
		
		request.setAttribute("msg", resultMsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}


	private java.sql.Date request(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}


	
