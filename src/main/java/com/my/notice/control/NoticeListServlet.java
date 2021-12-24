package com.my.notice.control;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/ntclist")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance(); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path="";		
		try {
			List<Notice> list=service.findNtcAll();
			request.setAttribute("list", list);
			path="noticelistresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "failresult.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
