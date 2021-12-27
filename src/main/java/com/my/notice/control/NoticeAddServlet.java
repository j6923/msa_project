package com.my.notice.control;

import java.io.IOException;

import com.my.customer.vo.Customer;
import com.my.exception.AddException;
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
		String resultmsg = "";
		String path = "";
		String saveDirectory = "./images";
		
		//로그인여부
		if(c == null) {
			resultmsg = "로그인하세요";
		}else {
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
				int ntcIdx = service.addNtc(n);
				request.setAttribute("status", 1);
				resultmsg="글 추가성공";
				path="ntcdetail?ntcIdx="+ntcIdx;
				System.out.println(path);
			} catch(AddException e){
				e.getStackTrace();
				request.setAttribute("status", 0);
				resultmsg = e.getMessage();
			}
		}
		
		request.setAttribute("resultmsg", resultmsg);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
