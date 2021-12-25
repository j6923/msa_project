package com.my.customer.control;

import java.io.IOException;

import com.my.customer.service.CustomerService;
import com.my.customer.vo.Customer;
import com.my.exception.FindException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CustomerService service = CustomerService.getInstance();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uEmail = request.getParameter("email");
		String uPwd = request.getParameter("pwd");
		System.out.println("LoginServlet의 doPost() id=" + uEmail +", pwd="+ uPwd);
		
		String resultMsg = "";
		
		HttpSession session = request.getSession();
		session.removeAttribute("loginInfo");
		
		String path = "jsonresult.jsp";
		
		try {
			Customer c = service.login(uEmail, uPwd);
			System.out.println("로그인 성공");
			session.setAttribute("loginInfo", c);
			
			resultMsg="로그인 성공";
			request.setAttribute("status", 1);	
			
		} catch (FindException e) {
			System.out.println(e.getMessage());
			resultMsg = "아이디 또는 비밀번호가 틀렸습니다.";
			request.setAttribute("status", 0);
		}
		
		request.setAttribute("msg", resultMsg);
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
