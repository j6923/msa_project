package com.my.notice.control;

import java.io.IOException;

import com.my.exception.FindException;
import com.my.notice.service.NoticeService;
import com.my.notice.vo.Notice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeDetailServlet
 */
@WebServlet("/ntcdetail")
public class NoticeDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ntcIdx =request.getParameter("ntcIdx");
		int intNtcIdx=Integer.parseInt(ntcIdx);
		String path = "";
		
		try {
			Notice n = service.findNtcByIdx(intNtcIdx);
			request.setAttribute("n", n);
			path="noticedetailresult.jsp";
		} catch (FindException e) {
			e.getStackTrace();
			path = "failresult.jsp";
		}
		//VIEWER로 이동
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
