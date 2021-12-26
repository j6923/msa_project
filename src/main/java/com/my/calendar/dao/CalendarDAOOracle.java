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
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.sql.MyConnection;

public class CalendarDAOOracle implements CalendarDAOInterface {
	//싱글톤 
	private static CalendarDAOOracle dao = new CalendarDAOOracle();
	private CalendarDAOOracle() {}
	public static CalendarDAOOracle getInstance() {
		return dao;
	}
	
	@Override
	public void addCal(CalInfo calInfo) throws AddException{
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
			}else {
				throw new AddException("고객번호에 해당하는 cal_info행이 없습니다");
			}
			System.out.println("uIdx=" + uIdx + ", calIdx =" + calIdx);
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
			MyConnection.close(pstmt, con);
		}
	}
	
	@Override
	public List<CalInfo> findCalsByUIdxandCalIdx(int uIdx, int calIdx) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = MyConnection.getConnection();
			//con.setAutoCommit(false);
			
			String selectCalsByUIdxandCalIdxSQL = "select cal_Category, cal_Thumbnail \r\n"
					+ "from cal_info\r\n"
					+ "where u_idx = ? and cal_idx = ? \r\n"
					+ "order by cal_idx DESC";
			pstmt = con.prepareStatement(selectCalsByUIdxandCalIdxSQL);
			pstmt.setInt(1, uIdx);
			pstmt.setInt(2, calIdx);
			rs = pstmt.executeQuery();
			
			List<CalInfo> list = new ArrayList<>();
			while(rs.next()) {
				uIdx = rs.getInt("uIdx");
				calIdx = rs.getInt("calIdx");
				String calCategory = rs.getString("cal_Category");
				String calThumbnail = rs.getString("cal_Thumbnail");
				
				CalInfo calinfo = new CalInfo();
				calinfo.setCalCategory(calCategory);
				calinfo.setCalThumbnail(calThumbnail);
				
				list.add(calinfo);
			}
			if(list.size() == 0) {
				throw new FindException("회원번호에 해당하는 캘린더가 없습니다.");
			}
			return list;
		} catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
		
	}
	
	@Override
	public void modifyCal(CalInfo calinfo) throws ModifyException{
		
		int uIdx = calinfo.getCustomer().getUIdx();
		int calIdx = calinfo.getCalIdx();
		try {
			List<CalInfo> list = findCalsByUIdxandCalIdx(uIdx, calIdx);
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
	public void addCalPost(CalPost calpost) throws AddException{
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "INSERT INTO calpost(cal_date,cal_memo,cal_img1,cal_img2,cal_img3,cal_main_img) VALUES (?,?,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL); // 동적쿼리
		pstmt.setDate(1, (java.sql.Date) calpost.getCalDate()); //년,월,일 만 불러옴
		pstmt.setString(2, calpost.getCalMemo());
		pstmt.setString(3, calpost.getCalImg1());
		pstmt.setString(4, calpost.getCalImg2());
		pstmt.setString(5, calpost.getCalImg3());
		pstmt.setString(6, calpost.getCalMainImg());
		pstmt.executeUpdate();
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}
	}
	
	@Override
	public List<CalPost> findCalsByUIdx(int uIdx, int calIdx, Date year, Date month) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select TO_CHAR(cal_Date,'YYYY'),TO_CHAR(cal_Date,'MM'), cal_Main_Img\r\n"
					+ "from cal_post_2_1\r\n"
					+ "where to_char(cal_date,'yy/mm') = '21/12'\r\n"
					+ "order by TO_CHAR(CAL_DATE,'yy/mm/dd')";
			pstmt = con.prepareStatement(selectSQL);
			rs = pstmt.executeQuery(selectSQL);
			List<CalPost> list = new ArrayList<>();
			
			//결과처리
			while(rs.next()) {
				uIdx = rs.getInt(1);
				calIdx = rs.getInt(2);
				year = rs.getDate(3);
				month = rs.getDate(4);
				
			
			}
			if(list.size() == 0) {
				throw new FindException("저장된 캘린더 글이 없습니다");	
			}
			return list;
		}catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}
	
	@Override
	public void modifyCalPost(CalPost calpost) throws ModifyException{
		Connection con =null;
		PreparedStatement pstmt = null;
		
		int uIdx = calpost.getCustomer().getUIdx();
		int calIdx = calpost.getCalinfo().getCalIdx();
		Date year = null;
		Date month = null;
		
		try {
			
			try {
				List<CalPost> list = findCalsByUIdx(uIdx, calIdx, year, month);
			} catch (FindException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			con = MyConnection.getConnection();
			String modifySQL="update calpost set cal_memo where cal_date=?";
			String modifySQL1="update calpost set cal_Img1 where cal_date=?";
			String modifySQL2="update calpost set cal_Img2 where cal_date=?";
			String modifySQL3="update calpost set cal_Img3 where cal_date=?";
			String modifySQL4="update calpost set main_Img where cal_date=?";

				
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, calpost.getCalMemo());
			pstmt.setDate(2, (java.sql.Date) calpost.getCalDate());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL1);
			pstmt.setString(1, calpost.getCalImg1());
			pstmt.setDate(2, (java.sql.Date) calpost.getCalDate());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL2);
			pstmt.setString(1, calpost.getCalImg2());
			pstmt.setDate(2, (java.sql.Date) calpost.getCalDate());
			pstmt = con.prepareStatement(modifySQL3);
			pstmt.setString(1, calpost.getCalImg3());
			pstmt.setDate(2, (java.sql.Date) calpost.getCalDate());
			pstmt = con.prepareStatement(modifySQL4);
			pstmt.setString(1, calpost.getCalMainImg());
			pstmt.setDate(2, (java.sql.Date) calpost.getCalDate());
			
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}
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
			String deledtCalInfoSQL = "delete from Cal_post" + uIdx + "_" + calIdx + "where cal_Date = ?";
			pstmt = con.prepareStatement(deledtCalInfoSQL);
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


	

	public static void main(String[] args) {
		System.out.println();
	}
	
}
//	public static void main(String[] args) {
//		CalendarDAOInterface dao =  CalendarDAOOracle.getInstance();
//		//calTitle, calThumbnail은 요청전달데이터 
//		//uidx 세션로그인정보
//		CalInfo calInfo = new CalInfo();
//		int uIdx = 2; //혜성 :1 , 다원:3 정은:2
//		Customer c = new Customer();
//		c.setuIdx(uIdx);
//		calInfo.setCustomer(c);
//		String calCategory = "쇼핑";
//		calInfo.setCalCategory(calCategory);
//		
//		String calThumbnail = "shop.jpg";
//		calInfo.setCalThumbnail(calThumbnail);
//		try {
//			dao.addCal(calInfo);
//		} catch (AddException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//}
