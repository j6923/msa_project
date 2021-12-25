package com.my.customer.control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.customer.service.CustomerService;
import com.my.exception.FindException;

/**
 * Servlet implementation class NickDupchkServlet
 */
@WebServlet("/nickdupchk")
public class NickDupchkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService service = CustomerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nicknameValue = request.getParameter("nickname");
		String resultMsg = "";	
		String path = "jsonresult.jsp";
		
		try {
			service.nickdupchk(nicknameValue);
			resultMsg = "이미 사용중인 닉네임입니다";
			request.setAttribute("status", 0);
		} catch (FindException e) {
			resultMsg = "사용가능한 닉네임입니다";
			request.setAttribute("status", 1);
		}
		
		
		request.setAttribute("msg", resultMsg);
						
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
