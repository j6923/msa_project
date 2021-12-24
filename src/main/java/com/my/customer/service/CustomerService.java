package com.my.customer.service;

import com.my.customer.dao.CustomerDAOOracle;
import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;

public class CustomerService {
	private CustomerDAOOracle dao = CustomerDAOOracle.getInstance(); 
	private static CustomerService service = new CustomerService();
	private CustomerService() {	}
	public static CustomerService getInstance() {
		return service;
	}
	
	
	public Customer login(String uEmail, String uPwd) throws FindException{
		try {
		Customer c = dao.findByEmail(uEmail);
		if(c.getUPwd().equals(uPwd)) {
			return c;
		}
		throw new FindException();
		}catch(FindException e) {
			throw new FindException("로그인 실패");
		}
	}
	
	public void signup(Customer c) throws AddException{
		dao.add(c);
	}
	
	public void emaildupchk(String uEmail) throws FindException{
		dao.findByEmail(uEmail);
	}
	
	public void nickdupchk(String uNickName) throws FindException{
		dao.findByNick(uNickName);
	}
}



