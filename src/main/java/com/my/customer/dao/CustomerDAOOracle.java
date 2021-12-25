package com.my.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.sql.MyConnection;

public class CustomerDAOOracle implements CustomerInterface {
	private static CustomerDAOOracle dao = new CustomerDAOOracle(); 
	private CustomerDAOOracle() {
	}
	public static CustomerDAOOracle getInstance() {
		return dao;
	}
	
	@Override
	public void add(Customer customer) throws AddException {
		Connection con = null;
		PreparedStatement pstmt = null;
		String insertSQL = "INSERT INTO customer(u_idx,u_name,u_nickname,u_email,u_pwd,u_authcode,u_status) VALUES (u_idx.nextval,?,?,?,?,1,1)";
		
		try {
			con = MyConnection.getConnection(); //Connection : DB연결
			pstmt = con.prepareStatement(insertSQL); //PrepatedStatement : SQL송신
			pstmt.setString(1, customer.getUName());
			pstmt.setString(2, customer.getUNickName());
			pstmt.setString(3, customer.getUEmail());
			pstmt.setString(4, customer.getUPwd());
			pstmt.executeUpdate(); //바인드변수 setting 후 DB로 송신
		} catch (SQLException e) {
			//ID가 중복된 경우(PK위배)에는 오라클오류번호1번이 발생한다
			int errorCode = e.getErrorCode();
			if(errorCode == 1) {
				throw new AddException("이미 존재하는 아이디입니다");
			}else {
				e.printStackTrace();
				throw new AddException(e.getMessage());
			}
		}finally {
			MyConnection.close(pstmt, con);
		}

	}

	@Override
	public Customer findByEmail(String uEmail) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String selectAllSQL = "select u_idx,u_name,u_nickname,u_email,u_pwd "
					+ "from customer "
					+ "where u_email=?";
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setString(1, uEmail);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				int uIdx = rs.getInt("u_idx");
				String uName = rs.getString("u_name");
				String uNickName = rs.getString("u_nickname");
				String uPwd = rs.getString("U_pwd");
				
				Customer c = new Customer();
				c.setUIdx(uIdx);
				c.setUName(uName);
				c.setUNickName(uNickName);
				c.setUPwd(uPwd);
				c.setUEmail(uEmail);
				return c;
			}else {
				throw new FindException("이메일에 해당하는 고객이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}

	@Override
	public Customer findByNick(String uNickName) throws FindException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String selectAllSQL = "select u_idx,u_name,u_nickname,u_email,u_pwd "
					+ "from customer "
					+ "where u_nickname=?";
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setString(1, uNickName);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {				
				int uIdx = rs.getInt("u_idx");
				String uName = rs.getString("u_name");
				String uEmail = rs.getString("u_email");
				String uPwd = rs.getString("u_pwd");
				
				Customer c = new Customer();
				c.setUIdx(uIdx);
				c.setUName(uName);
				c.setUNickName(uNickName);
				c.setUPwd(uPwd);
				c.setUEmail(uEmail);
				return c;
			}else {
				throw new FindException("닉네임에 해당하는 고객이 없습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}

	@Override
	public void modifyStatus(int uIdx) throws ModifyException {
		
		

	}

}
