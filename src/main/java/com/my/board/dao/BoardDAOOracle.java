package com.my.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.vo.Notice;
import com.my.sql.MyConnection;


public class BoardDAOOracle implements BoardDAOInterface {
	
	private static BoardDAOOracle dao = new BoardDAOOracle();
	private BoardDAOOracle() {	
	}
	public static BoardDAOOracle getinstance() {
		return dao;
	}
	
	@Override
	public List<Board> findBrdAll() throws FindException {
		Connection con = null; //DB연결
		PreparedStatement pstmt = null; //SQL송신
		ResultSet rs = null; //결과 수신
		String selectAllSQL = "SELECT b.brd_Idx, b.brd_UNickName,b.brd_Type,b.brd_Title,b.brd_Views,b.brd_ThumbUp,b.brd_CreateAt,(SELECT count(*) FROM comments c WHERE c.brd_idx = b.brd_idx) as cmt_count\r\n"
				+ "FROM board b\r\n"
				+ "ORDER BY b.brd_Idx DESC";
		List<Board> list = new ArrayList<>();
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int brdIdx = rs.getInt("brd_Idx");
				String brdUNickName = rs.getString("brd_UNickName");
				int brdType = rs.getInt("brd_Type");
				String brdTitle = rs.getString("brd_Title");
				int brdViews = rs.getInt("brd_Views");
				int brdThumbUp = rs.getInt("brd_ThumbUp");
				Date brdCreateAt = rs.getDate("brd_CreateAt");
				int cmtCount = rs.getInt("cmt_count");
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdUNickName(brdUNickName);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdViews(brdViews);
				b.setBrdThumbUp(brdThumbUp);
				b.setBrdCreateAt(brdCreateAt);
				b.setCmtCount(cmtCount);
				list.add(b);
			}
			if(list.size() == 0) {
				throw new FindException("게시글 목록이 없습니다");
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
	public Board findBrdByIdx(int intBrdIdx) throws FindException {
		Connection con = null; //DB연결
		PreparedStatement pstmt = null; //SQL송신
		ResultSet rs = null; //결과 수신
		String selectByIdxSQL = "SELECT *\r\n"
				+ "FROM\r\n"
				+ "(SELECT c.*, brd_type, brd_title, brd_content, brd_attachment, brd_createat, brd_thumbup, brd_unickname, brd_views\r\n"
				+ "FROM comments c RIGHT JOIN  board b ON b.brd_Idx = c.brd_Idx\r\n"
				+ "WHERE  c.brd_idx=?) \r\n"
				+ "START WITH  cmt_parentidx = 0\r\n"
				+ "CONNECT BY PRIOR cmt_idx = cmt_parentidx"; 
		String updateSQL = "UPDATE board set brd_views = BRD_VIEWS+1 where brd_Idx=?";
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectByIdxSQL);
			pstmt.setInt(1, intBrdIdx);
			rs = pstmt.executeQuery();
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setInt(1, intBrdIdx);
			pstmt.executeQuery();
			
			Board b = null;
			List<Comment> comments = null;
						
			//int  rowcnt = 0;
			while(rs.next()) {
				//if(rowcnt == 0) { //첫행 인경우는 Board객체생성 
				if(b == null) {
					String brdUNickName = rs.getString("brd_UNickName");
					int brdType = rs.getInt("BRD_TYPE");
					String brdTitle = rs.getString("BRD_TITLE");	
					String brdContent = rs.getString("BRD_CONTENT");	
					String brdAttachment = rs.getString("BRD_ATTACHMENT");	
					int brdViews = rs.getInt("BRD_VIEWS");
					int brdThumbUp = rs.getInt("BRD_THUMBUP");
					Date brdCreateAt = rs.getDate("BRD_CREATEAT");	
					
					b = new Board();
					b.setBrdUNickName(brdUNickName);
					b.setBrdType(brdType);
					b.setBrdTitle(brdTitle);
					b.setBrdContent(brdContent);
					b.setBrdAttachment(brdAttachment);
					b.setBrdViews(brdViews); 
					b.setBrdThumbUp(brdThumbUp);
					b.setBrdCreateAt(brdCreateAt);
					comments = new ArrayList<>();
					b.setComments(comments);
				}
				
				int cmtIdx = rs.getInt("CMT_IDX"); //column값이 null인 경우 자바 int얻어오면 0을 반환 
				System.out.println(cmtIdx);
				if(cmtIdx !=  0) { //댓글이 있는  경우 
					String cmtContent = rs.getString("CMT_CONTENT");	
					int cmtParentIdx = rs.getInt("CMT_PARENTIDX");
					Date cmtCreateAt = rs.getDate("CMT_CREATEAT");		
					String cmtUNickName = rs.getString("cmt_UNickName");	
					
					Comment comment = new Comment();
					comment.setCmtIdx(cmtIdx);
					comment.setCmtContent(cmtContent);
					comment.setCmtParentIdx(cmtParentIdx);
					comment.setCmtCreateAt(cmtCreateAt);
					comment.setCmtUNickName(cmtUNickName);
					comments.add(comment);
				}
				
				//rowcnt++; //행수증가 
				
			}
			//if(rowcnt == 0) {			
			if(b == null) {
				throw new FindException("글번호에 해당하는 게시글이 없습니다.");	
			}else {				
				return b;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}
	
	
	
	@Override //제목으로 검색
	public List<Board> findBrdByTitle(String word) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "SELECT b.brd_Idx, b.brd_UNickName,b.brd_Type,b.brd_Title,b.brd_Views,b.brd_ThumbUp,b.brd_CreateAt,(SELECT count(*) FROM comments c WHERE c.brd_idx = b.brd_idx) as cmt_comment\r\n"
					+ "FROM board b\r\n"
					+ "WHERE brd_title like ? \r\n"
					+ "ORDER BY b.brd_Idx DESC";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, "%"+word+"%");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int brdIdx =rs.getInt(1);
				String brdUNickName=rs.getString(2);
				int brdType=rs.getInt(3);
				String brdTitle=rs.getString(4);
				int brdViews=rs.getInt(5);
				int brdThumbUp=rs.getInt(6);
				Date brdCreateAt =rs.getDate(7);
				int cmtCount=rs.getInt(8);
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdUNickName(brdUNickName);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdViews(brdViews);
				b.setBrdThumbUp(brdThumbUp);
				b.setBrdCreateAt(brdCreateAt);
				b.setCmtCount(cmtCount);
				list.add(b);
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
	
	
	
	@Override //제목+내용으로 검색했을때
	public List<Board> findBrdByWord(String word) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "SELECT b.brd_Idx, b.brd_UNickName,b.brd_Type,b.brd_Title,b.brd_Views,b.brd_ThumbUp,b.brd_CreateAt,(SELECT count(*) FROM comments c WHERE c.brd_idx = b.brd_idx) as cmt_comment\r\n"
					+ "FROM board b\r\n"
					+ "WHERE brd_content like ?  or brd_content like ? \r\n"
					+ "ORDER BY b.brd_Idx DESC";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setString(2, "%"+word+"%");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int brdIdx =rs.getInt(1);
				String brdUNickName=rs.getString(2);
				int brdType=rs.getInt(3);
				String brdTitle=rs.getString(4);
				int brdViews=rs.getInt(5);
				int brdThumbUp=rs.getInt(6);
				Date brdCreateAt =rs.getDate(7);
				int cmtCount=rs.getInt(8);
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdUNickName(brdUNickName);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdViews(brdViews);
				b.setBrdThumbUp(brdThumbUp);
				b.setBrdCreateAt(brdCreateAt);
				b.setCmtCount(cmtCount);
				list.add(b);
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
	public List<Board> findBrdByUNickName(String word) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "SELECT b.brd_Idx, b.brd_UNickName,b.brd_Type,b.brd_Title,b.brd_Views,b.brd_ThumbUp,b.brd_CreateAt,(SELECT count(*) FROM comments c WHERE c.brd_idx = b.brd_idx) as cmt_comment\r\n"
					+ "FROM board b\r\n"
					+ "WHERE brd_UNickName like ? \r\n"
					+ "ORDER BY b.brd_Idx DESC";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, "%"+word+"%");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int brdIdx =rs.getInt(1);
				String brdUNickName=rs.getString(2);
				int brdType=rs.getInt(3);
				String brdTitle=rs.getString(4);
				int brdViews=rs.getInt(5);
				int brdThumbUp=rs.getInt(6);
				Date brdCreateAt =rs.getDate(7);
				int cmtCount=rs.getInt(8);
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdUNickName(brdUNickName);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdViews(brdViews);
				b.setBrdThumbUp(brdThumbUp);
				b.setBrdCreateAt(brdCreateAt);
				b.setCmtCount(cmtCount);
				list.add(b);
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
	public void addBrd(Board b) throws AddException {
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "insert into board(brd_idx,brd_type,brd_title,brd_content,brd_attachment,brd_UNickName) values(brd_idx.nextval,?,?,?,?,?)"; 		
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		pstmt.setString(1, b.getBrdTitle());
		pstmt.setInt(2, b.getBrdType());
		pstmt.setString(3, b.getBrdContent());
		pstmt.setString(4, b.getBrdAttachment());
		pstmt.setString(5, b.getBrdUNickName());
		pstmt.executeUpdate();//실행
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}

	}

	@Override
	public void addCmt(Comment comment) throws AddException {
		Connection con =null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "insert into comments(brd_idx,cmt_idx,cmt_content,cmt_parentidx,cmt_UNickName) values(?,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		pstmt.setInt(1, comment.getBrdIdx());//1번 바인드변수는 id값으로 설정.
		pstmt.setInt(2, comment.getCmtIdx());//2번 바인드변수는 pwd값으로 설정.
		pstmt.setString(3, comment.getCmtContent());
		pstmt.setInt(4, comment.getCmtParentIdx());
		pstmt.setString(5, comment.getCmtUNickName());
		pstmt.executeUpdate();//실행
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}

	}
	
	@Override
	public void modifyBrd(Board b) throws ModifyException {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			findBrdByIdx(b.getBrdIdx());
			con = MyConnection.getConnection();
			String modifySQL="update board set brd_title=? where brd_idx=?";
			String modifySQL1="update board set brd_content=? where brd_idx=?";
			String modifySQL2="update board set brd_attachment=? where brd_idx=?";
			String modifySQL3="update board set brd_type=? where brd_idx=?";

			
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, b.getBrdTitle());
			pstmt.setInt(2, b.getBrdIdx());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL1);
			pstmt.setString(1, b.getBrdContent());
			pstmt.setInt(2, b.getBrdIdx());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL2);
			pstmt.setString(1, b.getBrdAttachment());
			pstmt.setInt(2, b.getBrdIdx());
			pstmt.executeUpdate();	
			pstmt = con.prepareStatement(modifySQL3);
			pstmt.setInt(1, b.getBrdType());
			pstmt.setInt(2, b.getBrdIdx());
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
	public void modifyCmt(Comment comment) throws ModifyException{
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			findCmtByIdx(comment.getBrdIdx(), comment.getCmtIdx());
			con = MyConnection.getConnection();
			String modifySQL="update comments set cmt_content=? where brd_idx=? and cmt_idx=?";
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setInt(1, comment.getBrdIdx());
			pstmt.setInt(2, comment.getCmtIdx());
			pstmt.executeUpdate();
		}catch (FindException e) {
			throw new ModifyException(e.getMessage());			
		}catch(SQLException e) {
			e.getStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}			
}
	
	
	@Override
	public void removeBrd(int brdIdx) throws RemoveException {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();						
			String deleteSQL = "delete from comments where brd_idx=?";
			String deleteSQL1 = "delete from board where brd_idx=?";
			
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, brdIdx);
			pstmt.executeUpdate();//실행
			
			pstmt = con.prepareStatement(deleteSQL1);
			pstmt.setInt(1, brdIdx);
			pstmt.executeUpdate();//실행
			int deleterow = pstmt.executeUpdate();
			
			if(deleterow == 0) {
				System.out.println("해당 게시글이 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}	

	}
	
	@Override
	public void removeCmt(int brdIdx, int cmtIdx) throws RemoveException {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();						
			String deleteSQL = "delete from comments where brd_idx=? and cmt_idx=?"; 
			pstmt = con.prepareStatement(deleteSQL);// sql구문을 미리준비.
			pstmt.setInt(1, brdIdx);
			pstmt.setInt(2, cmtIdx);
			pstmt.executeUpdate();//실행
						
			int deleterow = pstmt.executeUpdate();
			if(deleterow == 0) {
				System.out.println("해당 댓글이 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MyConnection.close(pstmt, con);
		}	

	}
	
	@Override
	public Comment findCmtByIdx(int brdIdx, int cmtIdx) throws FindException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select * from Comment where brd_idx=? and cmt_idx=?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, brdIdx);
			pstmt.setInt(2, cmtIdx);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String cmtContent = rs.getString("cmt_content");
				int cmtParentIdx =rs.getInt("cmt_ParentIdx");
				Date cmtCreateAt = rs.getDate("cmt_createat");
				String cmtUNickName = rs.getString("cmt_unickname");
				
				Comment comment = new Comment();
				comment.setCmtContent(cmtContent);
				comment.setCmtParentIdx(cmtParentIdx);
				comment.setCmtCreateAt(cmtCreateAt);
				comment.setCmtUNickName(cmtUNickName);
				return comment;
			}
			throw new FindException("글번호에 해당하는 댓글이 없습니다.");
		}catch (SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}
	}
	
	
	public static void main(String[] args) {
		BoardDAOOracle dao = new BoardDAOOracle();
		Board b;
		try {
			b = dao.findBrdByIdx(9);
			System.out.println(b.getBrdTitle() + ":댓글 수 = " + b.getComments().size());
		
		} catch (FindException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
		
}



