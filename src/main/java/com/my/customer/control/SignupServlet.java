package com.my.customer.control;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.customer.service.CustomerService;
import com.my.customer.vo.Customer;
import com.my.exception.AddException;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService service = CustomerService.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uName = request.getParameter("name");
		String uNickName= request.getParameter("nickname");
		String uEmail = request.getParameter("email");
		String uPwd = request.getParameter("pwd");
		Customer c = new Customer();
		c.setUName(uName);
		c.setUNickName(uNickName);
		c.setUEmail(uEmail);
		c.setUPwd(uPwd);
		
		String path = "jsonresult.jsp";
		String msg = "";
		try {
			service.signup(c);
//			path = "success";
			request.setAttribute("status", 1);
			msg = "가입성공";
			
		} catch (AddException e) {
			e.printStackTrace();
//			path = "fail";
			request.setAttribute("status", 0);
			msg = e.getMessage();
			
		}
		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}


