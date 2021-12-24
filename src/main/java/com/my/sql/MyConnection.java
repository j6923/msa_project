package com.my.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	static {
		//1.jdbc드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		//2.db와의 연결
		Connection con = null;
		String url= "jdbc:oracle:thin:@localhost:1521:xe"; // localhost대신 ip값도 가능.
		String user= "cal";
		String password= "cal";
		
		con = DriverManager.getConnection(url, user, password);
		System.out.println("DB와 연결 성공");
		return con;
	}
	
	public static void close(Statement stmt) {
		if(stmt !=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs ) {
		if(rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static void close(Connection con) {
		if(con !=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection con) {
		close(rs);
		close(stmt);
		close(con);
	} 
	public static void close(Statement stmt, Connection con) {
		close(stmt);
		close(con);
	} 
}//end of myconnection