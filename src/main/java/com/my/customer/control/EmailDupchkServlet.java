package com.my.customer.control;

import java.io.IOException;

import com.my.customer.service.CustomerService;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmailDupchkServlet
 */
@WebServlet("/emaildupchk")
public class EmailDupchkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService service = CustomerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailValue = request.getParameter("email");
		String resultMsg = "";	
		String path = "jsonresult.jsp";
		
		try {
			service.emaildupchk(emailValue);
			resultMsg = "이미 사용중인 이메일입니다";
			request.setAttribute("status", 0);
		} catch (FindException e) {
			resultMsg = "사용가능한 이메일입니다";
			request.setAttribute("status", 1);
		}
		
		
		request.setAttribute("msg", resultMsg);
						
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
