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
 * Servlet implementation class NoticeSearchListServlet
 */
@WebServlet("/ntcsearch")
public class NoticeSearchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance(); 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String word = request.getParameter("word");
		String path="";
		
		try {
			List<Notice> list= service.findNtcByWord(word);
			request.setAttribute("list", list);
			path = "noticelistresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			path = "failresult.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
