package com.my.customer.control;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.my.customer.service.CustomerService;

/**
 * Servlet implementation class NickDupchkServlet
 */
@WebServlet("/nickdupchk")
public class NickDupchkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerService service = CustomerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
