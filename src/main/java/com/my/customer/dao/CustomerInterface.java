package com.my.customer.dao;

import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;

public interface CustomerInterface {

	/**
	 * 저장소에 회원을 추가한다.
	 * @param c
	 * @throws AddException
	 */
	public void add(Customer c) throws AddException;
	
	/**
	 * 이메일에 해당하는 회원을 찾아 반환한다. 
	 * @param uEmail
	 * @return 회원객체
	 * @throws FindException
	 */
	public Customer findByEmail(String uEmail) throws FindException;
	
	/**
	 * 닉네임에 해당하는 회원을 찾아 반환한다.
	 * @param uNickName
	 * @return 회원객체
	 * @throws FindException
	 */
	public Customer findByNick(String uNickName) throws FindException;
	
	/**
	 * 회원상태를 변경한다(탈퇴)
	 * @param uIdx
	 * @throws ModifyException
	 */
	public void modifyStatus(int uIdx) throws ModifyException;
}
