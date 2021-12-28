package com.my.notice.control;

import java.io.IOException;

import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.notice.service.NoticeService;
import com.my.notice.vo.Notice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NoticeModifyServlet
 */
@WebServlet("/ntcmodify")
public class NoticeModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance(); 


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ntcIdx=request.getParameter("ntcIdx");
		int intNtcIdx = Integer.parseInt(ntcIdx);
		String ntcTitle=request.getParameter("ntcTitle");
		String ntcContent=request.getParameter("ntcContent");
		String ntcAttachment=request.getParameter("ntcAttachment");
		Notice n = new Notice();
		n.setNtcIdx(intNtcIdx);
		n.setNtcTitle(ntcTitle);
		n.setNtcContent(ntcContent);
		n.setNtcAttachment(ntcAttachment);
		
		String path="";
		try {
			Notice notice =  service.modifyNtc(n);
			request.setAttribute("n", notice);
			System.out.println("글 수정 성공");
			path="noticedetailresult.jsp";
		} catch (ModifyException e) {
			request.setAttribute("msg", e.getMessage());
			path="failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
		} 
		
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	}

