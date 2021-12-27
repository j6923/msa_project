package com.my.notice.control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.my.customer.vo.Customer;
import com.my.exception.RemoveException;
import com.my.notice.service.NoticeService;

/**
 * Servlet implementation class NoticeRemoveServlet
 */
@WebServlet("/ntcremove")
public class NoticeRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService service = NoticeService.getinstance();     

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path="";
		String resultmsg="";
		
		//로그인여부
		if(c == null) {
			resultmsg = "로그인하세요";
		}else {
			String ntcIdx = request.getParameter("ntcIdx");	
			int intNtcIdx = Integer.parseInt(ntcIdx);
			
			try {
				service.removeNtc(intNtcIdx);
				System.out.println("글 삭제 성공");
				
				resultmsg="삭제 성공";
				request.setAttribute("status", 1);
				path="ntclist";
			} catch (RemoveException e) {
				System.out.println(e.getMessage());
				resultmsg="삭제 실패";
				request.setAttribute("status", 0);
				request.setAttribute("msg", e.getMessage());
				path="failresult.jsp";
			}
			
			request.setAttribute("resultmsg", resultmsg);
			
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
		}
	}
}
		
	


