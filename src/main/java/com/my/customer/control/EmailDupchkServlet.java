package com.my.customer.control;

import java.io.IOException;

import com.my.customer.service.CustomerService;

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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
