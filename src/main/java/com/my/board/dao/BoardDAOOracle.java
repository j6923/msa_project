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
	public Board findBrdByIdx(int brdIdx) throws FindException {
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
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectByIdxSQL);
			pstmt.setInt(1, brdIdx);
			rs = pstmt.executeQuery();
			
			Board b = null;
			List<Comment> comments = null;
						
			/*
			 *
9	2	기타제목3	기타내용3	기타파일3	21/12/24 13:13:16.000000000	2	nick9	7	1	9	댓글내용14	0	21/12/24 13:13:49.000000000	치형
9	2	기타제목3	기타내용3	기타파일3	21/12/24 13:13:16.000000000	2	nick9	7	2	9	댓글내용15	0	21/12/24 13:13:49.000000000	다원
9	2	기타제목3	기타내용3	기타파일3	21/12/24 13:13:16.000000000	2	nick9	7	3	9	댓글내용16	0	21/12/24 13:13:49.000000000	정은
9	2	기타제목3	기타내용3	기타파일3	21/12/24 13:13:16.000000000	2	nick9	7	4	9	댓글내용17	0	21/12/24 13:13:49.000000000	혜성
9	2	기타제목3	기타내용3	기타파일3	21/12/24 13:13:16.000000000	2	nick9	7	5	9	댓글내용18	0	21/12/24 13:49:07.000000000	nick9
			 */
			/*
1	0	잡담제목1	내용1	잡담파일1	21/12/24 13:13:16.000000000	1	혜성	11						
			 */
			
			/*
			 * BRD_IDX        NOT NULL NUMBER(10)     
BRD_TYPE       NOT NULL NUMBER(1)      
BRD_TITLE      NOT NULL VARCHAR2(100)  
BRD_CONTENT    NOT NULL VARCHAR2(1000) 
BRD_ATTACHMENT          VARCHAR2(50)   
BRD_CREATEAT            TIMESTAMP(6)   
BRD_THUMBUP             NUMBER(30)     
U_NICKNAME              VARCHAR2(30)   
BRD_VIEWS               NUMBER(10)
			 */
			
			/*
			 * CMT_IDX       NOT NULL NUMBER(10)    
BRD_IDX       NOT NULL NUMBER(10)    
CMT_CONTENT   NOT NULL VARCHAR2(500) 
CMT_PARENTIDX NOT NULL NUMBER(10)    
CMT_CREATEAT           TIMESTAMP(6)  
U_NICKNAME             VARCHAR2(30)  
			 */
			
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
	
	
	
	@Override
	public List<Board> findBrdByWord(String word) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = MyConnection.getConnection();
			String selectSQL = "select brd_idx,brd_title,brd_UNickName,brd_createat from Board where brd_title like ? or brd_content like ?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setString(2, "%"+word+"%");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int brdIdx =rs.getInt(1);
				String brdtitle=rs.getString(2);
				String brdUNickName=rs.getString(3);
				Date brdCreateAt =rs.getDate(4);
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdUNickName(brdUNickName);
				b.setBrdTitle(brdtitle);
				b.setBrdCreateAt(brdCreateAt);
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
			String insertSQL = "insert into board(brd_idx,brd_title,brd_content,brd_attachment,brd_UNickName) values(brd_idx.nextval,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		pstmt.setString(1, b.getBrdTitle());
		pstmt.setString(2, b.getBrdContent());
		pstmt.setString(3, b.getBrdAttachment());
		pstmt.setString(4, b.getBrdUNickName());
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
	public void modifyBrd(Board board) throws ModifyException {
		try {
			List<Board> list = findBrdAll();
			Connection con =null;
			PreparedStatement pstmt = null;
			con = MyConnection.getConnection();
			String modifySQL="update board set brd_title=? where brd_idx=?";
			String modifySQL1="update board set brd_content=? where brd_idx=?";
			String modifySQL2="update board set brd_attachment=? where brd_idx=?";
			
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, board.getBrdTitle());
			pstmt.setInt(2, board.getBrdIdx());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL1);
			pstmt.setString(1, board.getBrdContent());
			pstmt.setInt(2, board.getBrdIdx());
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(modifySQL2);
			pstmt.setString(1, board.getBrdAttachment());
			pstmt.setInt(2, board.getBrdIdx());
			pstmt.executeUpdate();
			
		}catch(FindException e){
			throw new ModifyException(e.getMessage());
		}catch(SQLException e) {
			e.getStackTrace();
		}

	}
	
	@Override
	public void modifyCmt(Comment comment) throws ModifyException {
		try {
			Connection con =null;
			PreparedStatement pstmt = null;
			con = MyConnection.getConnection();
			String modifySQL="update comments set cmt_content=? where cmt_idx=?";
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, comment.getCmtContent());
			pstmt.setInt(2, comment.getCmtIdx());
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.getStackTrace();
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

