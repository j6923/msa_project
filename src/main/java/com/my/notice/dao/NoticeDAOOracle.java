package com.my.notice.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.vo.Notice;
import com.my.exception.FindException;
import com.my.sql.MyConnection;


public class NoticeDAOOracle implements NoticeDAOInteface {
	
	private static NoticeDAOOracle dao = new NoticeDAOOracle();
	private NoticeDAOOracle() {	
	}
	public static NoticeDAOOracle getinstance() {
		return dao;
	}
	
	@Override
	public List<Notice> findNtcAll() throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select ntc_idx,ntc_title,ntc_createat,ntc_unickname,ntc_views from notice order by ntc_idx desc";
			pstmt = con.prepareStatement(selectSQL);
			rs = pstmt.executeQuery(selectSQL);
			List<Notice> list = new ArrayList<>();
			while(rs.next()) {
				int ntcIdx=rs.getInt(1);
				String ntcTitle =rs.getString(2);
				Date ntcCreateAt =rs.getDate(3);
				String ntcUNickname =rs.getString(4);
				int ntcViews = rs.getInt(5);
				Notice n = new Notice();
				n.setNtcIdx(ntcIdx);
				n.setNtcTitle(ntcTitle);
				n.setNtcCreateAt(ntcCreateAt);
				n.setNtcUNickName(ntcUNickname);
				n.setNtcViews(ntcViews);
				list.add(n);
			}
			if(list.size() == 0) {
				throw new FindException("저장된 글이 없습니다");
			}
			return list;
		}catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}
	
	@Override
	public void addNtc(Notice n) throws AddException{
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "insert into notice(ntc_idx,ntc_title,ntc_content,ntc_attachment,ntc_unickname) values(ntc_idx.nextval,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		
		pstmt.setString(1, n.getNtcTitle());
		pstmt.setString(2, n.getNtcContent());
		pstmt.setString(3, n.getNtcAttachment());
		pstmt.setString(4, n.getNtcUNickName());
		pstmt.executeUpdate();//실행
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}
}

@Override
public Notice findNtcByIdx(int ntcIdx) throws FindException {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
		con = MyConnection.getConnection();
		String selectSQL = "select * from notice where ntc_idx=?";
		pstmt = con.prepareStatement(selectSQL);
		pstmt.setInt(1, ntcIdx);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			String ntcTitle = rs.getString("ntc_title");
			String ntcContent = rs.getString("ntc_content");
			String ntcAttachment =rs.getString("ntc_attachment");
			Date ntcCreateAt = rs.getDate("ntc_createat");
			String ntcUNickName = rs.getString("ntc_unickname");
			
			Notice n = new Notice();
			n.setNtcTitle(ntcTitle);
			n.setNtcContent(ntcContent);
			n.setNtcAttachment(ntcAttachment);
			n.setNtcCreateAt(ntcCreateAt);
			n.setNtcUNickName(ntcUNickName);
			return n;
		}
		throw new FindException("글번호에 해당하는 공지사항글이 없습니다.");
	}catch (SQLException e) {
		throw new FindException(e.getMessage());
	}finally {
		MyConnection.close(rs, pstmt, con);
	}
}

@Override
public List<Notice> findNtcByWord(String word) throws FindException{
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	List<Notice> list = new ArrayList<>();
	
	try {
		con = MyConnection.getConnection();
		String selectSQL = "select ntc_idx,ntc_unickname,ntc_title,ntc_createat from notice where ntc_title like ? or ntc_content like ?";
		pstmt = con.prepareStatement(selectSQL);
		pstmt.setString(1, "%"+word+"%");
		pstmt.setString(2, "%"+word+"%");
		rs = pstmt.executeQuery();
	
		while(rs.next()) {
			int ntcIdx =rs.getInt(1);
			String ntcUNickName=rs.getString(2);
			String ntcTitle=rs.getString(3);
			Date ntcCreateAt =rs.getDate(4);
			Notice n = new Notice();
			n.setNtcIdx(ntcIdx);
			n.setNtcUNickName(ntcUNickName);
			n.setNtcTitle(ntcTitle);
			n.setNtcCreateAt(ntcCreateAt);
			list.add(n);
		}
		if(list.size() == 0) {
			throw new FindException("단어를 포함하는 글이 없습니다.");
		}
		return list;
		
	} catch (SQLException e) {
		throw new FindException(e.getMessage());
	} finally {
		MyConnection.close(rs, pstmt, con);
	}
	
}





@Override
public void modifyNtc(Notice n) throws ModifyException{
	Connection con =null;
	PreparedStatement pstmt = null;
	
	try {
		findNtcByIdx(n.getNtcIdx());
		con = MyConnection.getConnection();
		String modifySQL="update notice set ntc_title=? where ntc_idx=?";
		String modifySQL1="update notice set ntc_content=? where ntc_idx=?";
		String modifySQL2="update notice set ntc_attachment=? where ntc_idx=?";
		
		pstmt = con.prepareStatement(modifySQL);
		pstmt.setString(1, n.getNtcTitle());
		pstmt.setInt(2, n.getNtcIdx());
		pstmt.executeUpdate();
		pstmt = con.prepareStatement(modifySQL1);
		pstmt.setString(1, n.getNtcContent());
		pstmt.setInt(2, n.getNtcIdx());
		pstmt.executeUpdate();
		pstmt = con.prepareStatement(modifySQL2);
		pstmt.setString(1, n.getNtcAttachment());
		pstmt.setInt(2, n.getNtcIdx());
		pstmt.executeUpdate();
		
	}catch(FindException e){
		throw new ModifyException(e.getMessage());
	}catch(SQLException e) {
		e.getStackTrace();
	}finally {
		MyConnection.close(pstmt, con);
	}	
}


@Override
public void removeNtc(int ntcidx) throws RemoveException {
	Connection con =null;
	PreparedStatement pstmt = null;
	
	try {
		con = MyConnection.getConnection();
		String deleteSQL = "delete from notice where ntc_idx=?";
		pstmt = con.prepareStatement(deleteSQL);
		pstmt.setInt(1, ntcidx);
		pstmt.executeUpdate();//실행
		
		int deleterow = pstmt.executeUpdate();
		if(deleterow == 0) {
			System.out.println("해당 공지사항이 존재하지 않습니다.");
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
