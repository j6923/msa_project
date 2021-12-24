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
	
	
	@Override
	public List<Board> findBrdAll() throws FindException {
		Connection con = null; //DB연결
		PreparedStatement pstmt = null; //SQL송신
		ResultSet rs = null; //결과 수신
		String selectAllSQL = "SELECT brdIdx,uNickName,brdType,brdTitle,brdViews,brdThumbUp,brdCreateAt FROM Board ORDER BY brdIdx DESC";
		List<Board> list = new ArrayList<>();
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int brdIdx = rs.getInt("brdIdx");
				String uNickName = rs.getString("uNickName");
				int brdType = rs.getInt("brdType");
				String brdTitle = rs.getString("brdTitle");
				int brdViews = rs.getInt("brdViews");
				int brdThumbUp = rs.getInt("brdThumbUp");
				Date brdCreateAt = rs.getDate("brdCreateAt");
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
								+ "JOIN comment c ON b.brdIdx = c.brdIdx\r\n"
								+ "WHERE b.brdIdx =?;\r\n"
								+ "ORDER BY b.brdIdx DESC"; //댓글도 정렬해야하는데 방법 아직 못찾음
		try {
			con = MyConnection.getConnection();
			pstmt = con.prepareStatement(selectByIdxSQL);
			pstmt.setInt(1, brdIdx);
			rs = pstmt.executeQuery();
			
			Board board = null;
			List<Comment> comments = null;
			if(rs.next()) {
				String uNickName = rs.getString("uNickName");
				int brdType = rs.getInt("brdType");
				String brdTitle = rs.getString("brdTitle");	
				String brdContent = rs.getString("brdContent");	
				String brdAttachment = rs.getString("brdAttachment");	
				int brdViews = rs.getInt("brdViews");
				int brdThumbUp = rs.getInt("brdThumbUp");
				Date brdCreateAt = rs.getDate("brdCreateAt");	
				
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
				int cmtIdx = rs.getInt("cmtIdx");
				String cmtContent = rs.getString("cmtContent");	
				int cmtParentIdx = rs.getInt("cmtParentIdx");
				Date cmtCreateAt = rs.getDate("cmtCreateAt");		
				String cmtUNickName = rs.getString("uNickName");	
				
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
				int ntcIdx =rs.getInt(1);
				String uNickname=rs.getString(2);
				String ntcTitle=rs.getString(3);
				Date ntcCreateAt =rs.getDate(4);
				Notice n = new Notice();
				n.setNtcIdx(ntcIdx);
				n.setUNickName(uNickname);
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
	public void addBrd(Board board) throws AddException {
		

	}

	@Override
	public void addCmt(Comment comment) throws AddException {
		

	}
	
	@Override
	public void modifyBrd(Board board) throws ModifyException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void modifyCmt(Comment comment) throws ModifyException {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public void removeBrd(int brdIdx) throws RemoveException {
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = MyConnection.getConnection();						
			String deleteSQL = "delete from board where brd_idx=?";
			pstmt = con.prepareStatement(deleteSQL);
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
