package com.my.notice.control;

import java.io.IOException;

import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.notice.service.NoticeService;
import com.my.notice.vo.Notice;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class NoticeAddServlet
 */
@WebServlet("/ntcadd")
public class NoticeAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	NoticeService service = NoticeService.getinstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Customer c = (Customer)session.getAttribute("loginInfo");
		String path = "";
		
		
			String ntcTitle=request.getParameter("ntcTitle");
			String ntcContent=request.getParameter("ntcContent");
			String ntcAttachment=request.getParameter("ntcAttachment");
			String ntcUNickName = c.getUNickName();
			Notice n = new Notice();
			n.setNtcTitle(ntcTitle);
			n.setNtcContent(ntcContent);
			n.setNtcAttachment(ntcAttachment);
			n.setNtcUNickName(ntcUNickName);
			
			try{
				Notice notice = service.addNtc(n);
				request.setAttribute("n", notice);
				path="noticedetailresult.jsp";
			} catch(AddException e){
				e.getStackTrace();
				path = "failresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
			}
		
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);
	}
}
