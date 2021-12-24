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
		String selectAllSQL = "SELECT brd_Idx,u_NickName,brd_Type,brd_Title,brd_Views,brd_ThumbUp,brd_CreateAt FROM Board ORDER BY brd_Idx DESC";
		List<Board> list = new ArrayList<>();
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int brdIdx = rs.getInt("brd_Idx");
				String uNickName = rs.getString("u_NickName");
				int brdType = rs.getInt("brd_Type");
				String brdTitle = rs.getString("brd_Title");
				int brdViews = rs.getInt("brd_Views");
				int brdThumbUp = rs.getInt("brd_ThumbUp");
				Date brdCreateAt = rs.getDate("brd_CreateAt");
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setUNickName(uNickName);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdViews(brdViews);
				b.setBrdThumbUp(brdThumbUp);
				b.setBrdCreateAt(brdCreateAt);
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
								+ "FROM board b\r\n"
								+ "JOIN comment c ON b.brd_Idx = c.brd_Idx\r\n"
								+ "WHERE b.brd_Idx =?;\r\n"
								+ "ORDER BY b.brd_Idx DESC"; //댓글도 정렬해야하는데 방법 아직 못찾음
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectByIdxSQL);
			pstmt.setInt(1, brdIdx);
			rs = pstmt.executeQuery();
			
			Board board = null;
			List<Comment> comments = null;
			if(rs.next()) {
				String uNickName = rs.getString("u_NickName");
				int brdType = rs.getInt("brd_Type");
				String brdTitle = rs.getString("brd_Title");	
				String brdContent = rs.getString("brd_Content");	
				String brdAttachment = rs.getString("brd_Attachment");	
				int brdViews = rs.getInt("brd_Views");
				int brdThumbUp = rs.getInt("brd_ThumbUp");
				Date brdCreateAt = rs.getDate("brd_CreateAt");	
				
				board = new Board();
				board.setUNickName(uNickName);
				board.setBrdType(brdType);
				board.setBrdTitle(brdTitle);
				board.setBrdContent(brdContent);
				board.setBrdAttachment(brdAttachment);
				board.setBrdViews(brdViews);
				board.setBrdThumbUp(brdThumbUp);
				board.setBrdCreateAt(brdCreateAt);
				
				comments = new ArrayList<>();
				board.setComments(comments);
				
				
			while(rs.next()) {
				int cmtIdx = rs.getInt("cmt_Idx");
				String cmtContent = rs.getString("cmt_Content");	
				int cmtParentIdx = rs.getInt("cmt_ParentIdx");
				Date cmtCreateAt = rs.getDate("cmt_CreateAt");		
				String cmtUNickName = rs.getString("u_NickName");	
				
				Comment comment = new Comment();
				comment.setCmtIdx(cmtIdx);
				comment.setCmtContent(cmtContent);
				comment.setCmtParentIdx(cmtParentIdx);
				comment.setCmtCreateAt(cmtCreateAt);
				comment.setUNickName(cmtUNickName);
				comments.add(comment);
				
			}
							
				return new Board(brdIdx, uNickName, brdType, brdTitle, brdContent, brdAttachment, brdViews, brdThumbUp, brdCreateAt, comments);
			}
			throw new FindException("글번호에 해당하는 게시글이 없습니다.");
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
			String selectSQL = "select brd_idx,brd_title,u_nickname,brd_createat from Board where brd_title like ? or brd_content like ?";
			pstmt = con.prepareStatement(selectSQL);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setString(2, "%"+word+"%");
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int brdIdx =rs.getInt(1);
				String brdtitle=rs.getString(2);
				String uNickname=rs.getString(3);
				Date brdCreateAt =rs.getDate(4);
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setUNickName(uNickname);
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
			String insertSQL = "insert into board(brd_idx,brd_title,brd_content,brd_attachment,u_nickname) values(?,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		pstmt.setInt(1, b.getBrdIdx());//1번 바인드변수는 id값으로 설정.
		pstmt.setString(2, b.getBrdTitle());//2번 바인드변수는 pwd값으로 설정.
		pstmt.setString(3, b.getBrdContent());
		pstmt.setString(4, b.getBrdAttachment());
		pstmt.setString(5, b.getUNickName());
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
			String insertSQL = "insert into comments(cmt_idx,brd_idx,cmt_content,cmt_parentidx,u_nickname) values(?,?,?,?,?)"; 
		pstmt = con.prepareStatement(insertSQL);// sql구문을 미리준비.
		pstmt.setInt(1, comment.getCmtIdx());//1번 바인드변수는 id값으로 설정.
		pstmt.setInt(2, comment.getBrdIdx());//2번 바인드변수는 pwd값으로 설정.
		pstmt.setString(3, comment.getCmtContent());
		pstmt.setInt(4, comment.getCmtParentIdx());
		pstmt.setString(5, comment.getUNickName());
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

	
	
	
}
