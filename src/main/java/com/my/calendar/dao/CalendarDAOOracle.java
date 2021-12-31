package com.my.calendar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.calendar.vo.CalInfo;
import com.my.calendar.vo.CalPost;
import com.my.customer.vo.Customer;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.sql.MyConnection;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

public class CalendarDAOOracle implements CalendarDAOInterface {
	//싱글톤 
	private static CalendarDAOOracle dao = new CalendarDAOOracle();
	private CalendarDAOOracle() {}
	public static CalendarDAOOracle getInstance() {
		return dao;
	}
	
	@Override
	public CalInfo addCal(CalInfo calInfo) throws AddException{
		//DB와 연결
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
			/*
			 UIDX
			 CALIDX
			 CALCATEGORY
			 CALTHUMBNAIL
			 CALCREATEAT*/
			
			int uIdx = calInfo.getCustomer().getUIdx();
			//---다음캘린더 글번호 얻기
			int calIdx;
			String selectCalIdxByUIdx = "SELECT NVL(MAX(cal_Idx), 0)+1 \r\n"
					+ "FROM cal_info \r\n"
					+ "WHERE u_Idx = ?";
			
			pstmt = con.prepareStatement(selectCalIdxByUIdx);
			pstmt.setInt(1, uIdx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				calIdx  = rs.getInt(1);
				calInfo.setCalIdx(calIdx); //캘린더번호를 calInfo객체에 설정 
			}else {
				throw new AddException("고객번호에 해당하는 cal_info행이 없습니다");
			}
			System.out.println("addCal함수 : uIdx=" + uIdx + ", calIdx =" + calIdx);
			//----------------------------------------------------------------------
			//--cal_info테이블에 추가 
			String calCategory = calInfo.getCalCategory();
			String calThumbnail = calInfo.getCalThumbnail();
			//Cal_INFO테이블에 행추가
			String insertCalInfoSQL = "INSERT INTO cal_info (u_Idx, cal_Idx,  cal_Category, cal_Thumbnail) values (?, ?, ?, ?)";
			pstmt = con.prepareStatement(insertCalInfoSQL);
			pstmt.setInt(1, uIdx);
			pstmt.setInt(2, calIdx);
			pstmt.setString(3, calCategory);
			pstmt.setString(4, calThumbnail);
			pstmt.executeUpdate(); 
			//----------------------------------------------------------------------			
			//CAL_POST_uIdx값_calIdx값 이름의 테이블 생성 
			String postTableName = "CAL_POST_" + uIdx + "_"  + calIdx;
			String createCalPostSQL = "CREATE TABLE " + postTableName +"(\r\n"
					+ "   --calIdx NUMBER(1),\r\n"
					+ "   --uIdx NUMBER(5),\r\n"
					+ "   cal_Date DATE CONSTRAINT cal_post_" + uIdx + "_" + calIdx + "_pk PRIMARY KEY,\r\n"
					+ "   cal_Memo VARCHAR2(1000) NOT NULL,\r\n"
					+ "   cal_Main_Img VARCHAR2(50) NOT NULL,\r\n"
					+ "   cal_Img1 VARCHAR2(50),\r\n"
					+ "   cal_Img2 VARCHAR2(50),\r\n"
					+ "   cal_Img3 VARCHAR2(50),\r\n"
					+ "   cal_Post_CreateAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP\r\n"
					+ ") ";
			
			pstmt = con.prepareStatement(createCalPostSQL);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new AddException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
		return calInfo;
		
	}
	
	
	@Override
	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String selectCalsByUIdxSQL = "select u_idx, cal_idx, cal_Category, cal_Thumbnail \r\n"
					+ "from cal_info\r\n"
					+ "where u_idx = ? \r\n"
					+ "order by cal_idx ASc";
			con = MyConnection.getConnection();
			//con.setAutoCommit(false);
			pstmt = con.prepareStatement(selectCalsByUIdxSQL);
			pstmt.setInt(1, uIdx);
//			pstmt.setString(1, calCategory);
//			pstmt.setString(2, calThumbnail);
			rs = pstmt.executeQuery();
			
			/*
			 3	1	운동	ex.jpg
			 3	2	책	book.jpg
			 3	3	음식	food1.jpg
			 */
			List<CalInfo> list = new ArrayList<>();
			
			while(rs.next()) {
				int calIdx = rs.getInt("cal_Idx");
				String calCategory = rs.getString("cal_Category");
				String calThumbnail = rs.getString("cal_Thumbnail");
				
				CalInfo calinfo = new CalInfo();
				calinfo.setCalCategory(calCategory);
				calinfo.setCalThumbnail(calThumbnail);
				calinfo.setCalIdx(calIdx);
//				Customer customer = new Customer();
//				customer.setuIdx(uIdx);
//				calinfo.setCustomer(customer);
				
				list.add(calinfo);
			}
			if(list.size() == 0) {
				throw new FindException("회원번호에 해당하는 캘린더가 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
		
	}
	
	@Override
	public void modifyCal(CalInfo calinfo) throws ModifyException{
		
		int uIdx = calinfo.getCustomer().getUIdx();
		int calIdx = calinfo.getCalIdx();
//		String calCategory = calinfo.getCalCategory();
//		String calThumbnail = calinfo.getCalThumbnail();
		try {
			List<CalInfo> list = findCalsByUIdx(uIdx);
			Connection con = null;
			PreparedStatement pstmt = null;
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
			String modifySQL = "update cal_info set cal_Category = ? where cal_idx = ?";
			String modifySQL1 = "update cal_info set cal_Thumbnail = ? where cal_idx = ?";
			
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, calinfo.getCalCategory());
			pstmt.setInt(2, calinfo.getCalIdx());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL1);
			pstmt.setString(1, calinfo.getCalThumbnail());
			pstmt.setInt(2, calinfo.getCalIdx());
			pstmt.executeUpdate();
			con.commit();
		} catch (FindException e) {
			throw new ModifyException(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void removeCal(CalInfo calinfo) throws RemoveException {
		// "drop table Cal_post_" + uIdx + "_" + calIdx;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
			
			int uIdx = calinfo.getCustomer().getUIdx();
			//---다음캘린더 글번호 얻기
			int calIdx = calinfo.getCalIdx();
			
//			CalInfo calinfo = null;
//			uIdx = calinfo.getCustomer().getUIdx();
//			int calIdx = calinfo.getCalIdx();
			//---다음캘린더 글번호 얻기

			System.out.println("uIdx=" + uIdx + ", calIdx =" + calIdx);
			//----------------------------------------------------------------------

			//Cal_Post 행삭제
			String dropCalInfoSQL = "drop table Cal_post_" + uIdx + "_" + calIdx;
			
			pstmt = con.prepareStatement(dropCalInfoSQL);
//			pstmt.setInt(1, uIdx);
//			pstmt.setInt(2, calIdx);
			pstmt.executeUpdate(); 
			
			int droptable = pstmt.executeUpdate();
			
			if(droptable == 0) {
				System.out.println("해당 캘린더가 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}
	}

	
	@Override
	public void addCalPost(CalPost cp) throws AddException{
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "INSERT INTO calpost(cal_date,cal_memo,cal_img1,cal_img2,cal_img3,cal_main_img) VALUES (?,?,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL); // 동적쿼리
		pstmt.setString(1,  cp.getCalDate()); //년,월,일 만 불러옴
		pstmt.setString(2, cp.getCalMemo());
		pstmt.setString(6, cp.getCalMainImg());
		pstmt.executeUpdate();
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}
	}
	
	
	
	
	@Override
	public List<CalPost> findCalPostByDate(String calDate) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int uIdx = ci.getCustomer().getUIdx();                         
		int calIdx = ci.getCalIdx();
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select cal_date, cal_Main_Img\r\n"
					+ "from cal_post_" + uIdx + "_" + calIdx + "\r\n"
					+ "where to_char(cal_date,'yyyy/mm') = ? \r\n"
					+ "order by cal_date,'yyyy/mm' asc";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1,calDate);
			rs = pstmt.executeQuery();
			List<CalPost> list = new ArrayList<>();
			//결과처리
			while(rs.next()) {
				String calDate= rs.getString("cal_Date");
				String calMainImg = rs.getString("cal_Main_Img");				
				uIdx = rs.getInt("u_Idx");
				calIdx = rs.getInt("cal_Idx");
				
				CalPost calpost = new CalPost();
				calpost.setCalMainImg(calMainImg);
				calpost.setCalDate(calDate);	
				calpost.setCalinfo(calinfo);
				
				list.add(calpost);
			}
		
			if(list.size() == 0) { //
				throw new FindException();	
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}
	
	
	
	
	
	@Override
	public void modifyCalPost(CalPost calpost) throws ModifyException{
//		Connection con =null;
//		PreparedStatement pstmt = null;
//		
//		int uIdx = calpost.getCustomer().getUIdx();
//		int calIdx = calpost.getCalinfo().getCalIdx();
//		Date calDate = calpost.getCalDate();
//		
//		try {
//			
//			try {
//				List<CalPost> list = findCalsByDate(caldate);
//			} catch (FindException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			con = MyConnection.getConnection();
////			String modifySQL="update calpost set cal_memo,cal_Img1,cal_Img2,cal_Img3, main_Img  where cal_date=?";
//						
//				
//			pstmt = con.prepareStatement(modifySQL);
//			pstmt.setDate(1, (java.sql.Date) calpost.getCalDate());
//			
//			
//			pstmt.executeUpdate();
//			
//		}catch(SQLException e) {
//			e.getStackTrace();
//		}finally {
//			MyConnection.close(pstmt, con);
//		}
	}
	

	@Override
	public void removeCalPost(Date calDate) throws RemoveException{
		//" delete from Cal_post_1_1 where cal_Date = ? ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		//ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			con.setAutoCommit(false);
			/*
			 CALDATE
			 CALMEMO
			 CALMAINIMG
			 CALIMG1
			 CALIMG2
			 CALIMG3
			 CALPOSTCREATEAT
			 */
			
			CalInfo calinfo = null;
			int uIdx = calinfo.getCustomer().getUIdx();
			int calIdx = calinfo.getCalIdx();
			//---다음캘린더 글번호 얻기

			System.out.println("uIdx=" + uIdx + ", calIdx =" + calIdx);
			//----------------------------------------------------------------------

			//Cal_Post 행삭제
			String deledtCalPostSQL = "delete from Cal_post_" + uIdx + "_" + calIdx + " where cal_Date = ?";
			pstmt = con.prepareStatement(deledtCalPostSQL);
			pstmt.setInt(1, uIdx);
			pstmt.setInt(2, calIdx);
			pstmt.setDate(3, (java.sql.Date) calDate);
			pstmt.executeUpdate(); 
			
			int deleterow = pstmt.executeUpdate();
			if(deleterow == 0) {
				System.out.println("해당 캘린더글이 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}
	}


//
//public static void main(String[] args) {
//	System.out.println();
//
//}}


	
	//테이블 생성 
	public static void main(String[] args) {
		CalendarDAOInterface dao =  CalendarDAOOracle.getInstance();
		//calTitle, calThumbnail은 요청전달데이터 
		//uidx 세션로그인정보
		CalInfo calInfo = new CalInfo();
		int uIdx = 2; //혜성 :1 , 다원:3 정은:2
		Customer c = new Customer();
		c.setUIdx(uIdx);
		calInfo.setCustomer(c);
		String calCategory = "독서";
		calInfo.setCalCategory(calCategory);
		
		String calThumbnail = "jpg";
		calInfo.setCalThumbnail(calThumbnail);
		try {
			dao.addCal(calInfo);
		} catch (AddException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	//테이블 삭제 
//	public static void main(String[] args) {
//	CalendarDAOInterface dao =  CalendarDAOOracle.getInstance();
//	//calTitle, calThumbnail은 요청전달데이터 
//	//uidx 세션로그인정보
//	CalInfo calInfo = new CalInfo();
//	int uIdx = 2; //혜성 :1 , 다원:3 정은:2
//	Customer c = new Customer();
//	c.setUIdx(uIdx);
//	calInfo.setCustomer(c);
//	int calIdx = 3;
//	calInfo.setCalIdx(calIdx);
//		try {
//			dao.removeCal(calInfo);
//		} catch (RemoveException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//}

	
	
}
