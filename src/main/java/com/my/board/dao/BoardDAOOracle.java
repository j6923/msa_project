package com.my.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

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
	public List<Board> findBrdByType(int intBrdType) throws FindException{
		Connection con = null; //DB연결
		PreparedStatement pstmt = null; //SQL송신
		ResultSet rs = null; //결과 수신
		String selectTypeSQL = "SELECT b.brd_Idx, b.brd_UNickName,b.brd_Type,b.brd_Title,b.brd_Views,b.brd_ThumbUp,b.brd_CreateAt,(SELECT count(*) FROM comments c WHERE c.brd_idx = b.brd_idx) as cmt_count\r\n"
				+ "FROM board b\r\n"
				+ "WHERE brd_type=?\r\n"
				+ "ORDER BY b.brd_Idx DESC";
		List<Board> list = new ArrayList<>();
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectTypeSQL);
			pstmt.setInt(1, intBrdType);
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
		String selectByIdxSQL ="SELECT * FROM (SELECT c.brd_idx,cmt_idx, cmt_content, nvl(c.cmt_parentidx, 0) cmt_parentidx,  cmt_createat, cmt_unickname,\r\n"
				+ "brd_type, brd_title, brd_content, brd_attachment, brd_createat, brd_thumbup, brd_unickname, brd_views\r\n"
				+ "FROM comments c RIGHT JOIN  board b ON b.brd_Idx = c.brd_Idx\r\n"
				+ "WHERE  b.brd_idx=?) \r\n"
				+ "START WITH  cmt_parentidx = 0\r\n"
				+ "CONNECT BY PRIOR cmt_idx = cmt_parentidx ";
				
		String updateSQL = "UPDATE board set brd_views = BRD_VIEWS+1 where brd_Idx=?";
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectByIdxSQL);
			pstmt.setInt(1, intBrdIdx);
			rs = pstmt.executeQuery();
			pstmt = con.prepareStatement(updateSQL);
			pstmt.setInt(1, intBrdIdx);
			pstmt.executeUpdate();
			
			Board b = null;
			List<Comment> comments = null;		
			//int  rowcnt = 0;
			while(rs.next()) {
				//if(rowcnt == 0) { //첫행 인경우는 Board객체생성 
				if(b == null) {
					System.out.println("다른곳에서 온 3"+intBrdIdx );
					String brdUNickName = rs.getString("brd_UNickName");
					System.out.println("다른곳에서 온 4"+intBrdIdx );
					int brdType = rs.getInt("BRD_TYPE");
					System.out.println("다른곳에서 온 5"+intBrdIdx );
					String brdTitle = rs.getString("BRD_TITLE");	
					String brdContent = rs.getString("BRD_CONTENT");	
					String brdAttachment = rs.getString("BRD_ATTACHMENT");	
					int brdViews = rs.getInt("BRD_VIEWS");
					int brdThumbUp = rs.getInt("BRD_THUMBUP");
					Date brdCreateAt = rs.getDate("BRD_CREATEAT");	
					
					b = new Board();
					b.setBrdIdx(intBrdIdx);
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
				System.out.println("다른곳에서 온 10"+intBrdIdx );
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
			System.out.println("다른곳에서 온 11"+intBrdIdx );
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
	public Board addBrd(Board b) throws AddException{
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "insert into board(brd_idx,brd_type,brd_title,brd_content,brd_attachment,brd_UNickName) values(brd_idx.nextval,?,?,?,?,?)"; 		
			String selectSQL = "SELECT MAX(brd_idx) FROM board";
					
			pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
			pstmt.setInt(1, b.getBrdType());
			pstmt.setString(2, b.getBrdTitle());
			pstmt.setString(3, b.getBrdContent());
			pstmt.setString(4, b.getBrdAttachment());
			pstmt.setString(5, b.getBrdUNickName());
			pstmt.executeUpdate();//실행
			System.out.println(b);
			
			pstmt = con.prepareStatement(selectSQL);
			rs = pstmt.executeQuery();			
			
			rs.next(); //?
			int brdIdx = rs.getInt(1);
			Board board = dao.findBrdByIdx(brdIdx);
			return board;
	
		} catch (SQLException e) {
			throw new AddException(e.getMessage());
		} catch (FindException e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			MyConnection.close(rs, pstmt, con);
		}

	}

	@Override
	public Board addCmt(Comment comment) throws AddException {
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select NVL(MAX(c.cmt_idx),0)+1 from comments c join board b on c.brd_idx=b.brd_idx where b.brd_idx=9"; 
			String insertSQL = "insert into comments(brd_idx,cmt_idx,cmt_content,cmt_parentidx,cmt_UNickName) values(?,?,?,?,?)"; 
		
		pstmt = con.prepareStatement(selectSQL);
		rs = pstmt.executeQuery();	
		rs.next();
		int cmtIdx = rs.getInt(1); //해당 글에 최대 댓글번호+1 값 얻음
				
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.				
		pstmt.setInt(1, comment.getBrdIdx());
		pstmt.setInt(2, cmtIdx);
		pstmt.setString(3, comment.getCmtContent());
		pstmt.setInt(4, comment.getCmtParentIdx());
		pstmt.setString(5, comment.getCmtUNickName());
		pstmt.executeUpdate();//실행
		Board board=findBrdByIdx(comment.getBrdIdx());   //방글 
		return board;
	} catch (SQLException e) {
		throw new AddException(e.getMessage());
	} catch (FindException e) {
		throw new AddException(e.getMessage());
	}finally {
		MyConnection.close(pstmt, con);
	}
}
	
	@Override
	public Board modifyBrd(Board b) throws ModifyException {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();			
			String modifySQL = "UPDATE board SET brd_title = ?, brd_content=?, brd_attachment=?, brd_type=? WHERE brd_idx=?";
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, b.getBrdTitle());
			pstmt.setString(2, b.getBrdContent());
			pstmt.setString(3, b.getBrdAttachment());
			pstmt.setInt(4, b.getBrdType());
			
			System.out.println("in boarddaooracle modifyBrd: b.brdIdx=" + b.getBrdIdx());
			pstmt.setInt(5, b.getBrdIdx());
			int modifySQLRowCnt = pstmt.executeUpdate();
			if(modifySQLRowCnt == 0) {
				throw new ModifyException("수정실패 : 글번호에 해당하는 게시글이 없습니다.");
			}
			Board board = dao.findBrdByIdx(b.getBrdIdx());
			return board;
		}catch(FindException e){
			throw new ModifyException(e.getMessage());
		}catch(SQLException e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
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
		ResultSet rs = null;
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

			String selectSQL = "select count(*) from comments where brd_idx=? and cmt_parentidx=0";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, brdIdx);
			rs = pstmt.executeQuery();	
			rs.next();
			int cmtParentIdxcnt = rs.getInt(1);
			
			if(cmtParentIdxcnt == 0) {
				String deleteSQL2 = "delete from comments where brd_idx=? "; 
				pstmt = con.prepareStatement(deleteSQL2);// sql구문을 미리준비.
				pstmt.setInt(1, brdIdx);
				pstmt.executeUpdate();
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



