package com.my.calendar.control;

import java.io.IOException;

import com.my.calendar.service.CalendarService;
import com.my.calendar.vo.CalInfo;
import com.my.exception.ModifyException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalInfoModifyServlet
 */
@WebServlet("/calmodify")
public class CalInfoModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CalendarService service = CalendarService.getInstance();
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CalInfoModifyServlet dopost()");
		//1.요청전달데이터 cal_Category, cal_Thumbnail값 얻기 
		String calCategoryValue = request.getParameter("calCategory");
		String calThumbnailValue = request.getParameter("calThumbnail");
		System.out.println("CalInfoModifyServlet dopost() calCategory=" + calCategoryValue + ",calThumbnail" + calThumbnailValue);
		
		CalInfo calinfo = new CalInfo();
		calinfo.setCalCategory(calCategoryValue);
		calinfo.setCalThumbnail(calThumbnailValue);
		
		String path="";
		String resultMsg = "";
		
		try {
			service.modifyCal(calinfo);
			System.out.println("캘린더 기본 정보 수정 성공");
			
			resultMsg="수정 성공";
			request.setAttribute("status", 1);
			path="after.jsp";
		} catch (ModifyException e) {
			System.out.println(e.getMessage());
			resultMsg="삭제 실패";
			request.setAttribute("status", 0);
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		}
		
			request.setAttribute("msg", resultMsg);
		
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
	}

}
