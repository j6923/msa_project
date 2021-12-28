package com.my.board.service;

import java.util.List;

import com.my.board.dao.BoardDAOOracle;
import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

public class BoardService {
	private BoardDAOOracle dao = BoardDAOOracle.getinstance();
	private static BoardService service = new BoardService();
	private BoardService() {
		
	}
	public static BoardService getinstance() {
		return service;
	}
	
	
	
	
	public Board addBrd(Board b) throws AddException,FindException{
		return(dao.addBrd(b));
	}
	
	public void addCmt(Comment comment) throws AddException{
		dao.addCmt(comment);
	}
	
	public Board findBrdByIdx(int intBrdIdx) throws FindException{
		Board b =dao.findBrdByIdx(intBrdIdx);	
		return b;
		
	}
	
	//자유게시판 제목 검색
		public List<Board> findBrdByTitle(String word) throws FindException{
			return dao.findBrdByTitle(word);
		}
		
	//자유게시판 제목+내용 검색
	public List<Board> findBrdByWord(String word) throws FindException{
		return dao.findBrdByWord(word);		
	}

	//자유게시판 닉네임 검색
	public List<Board> findBrdByUNickName(String word) throws FindException{
		return dao.findBrdByUNickName(word);
	}
	
	//자유게시판 글 목록 출력
	public List<Board> findBrdAll() throws FindException{
		return dao.findBrdAll();
	}
	
	//자유게시판 글 분류별 목록 출력
	public List<Board> findBrdByType(int intBrdType) throws FindException{
		return dao.findBrdByType(intBrdType);
	}
	
	public void modifyBrd(Board b) throws ModifyException{
		dao.modifyBrd(b);
	}
	
	public void modifyCmt(Comment comment) throws ModifyException{
		dao.modifyCmt(comment);
	}
	
	public void removeBrd(int brdIdx) throws RemoveException{
		dao.removeBrd(brdIdx);
	}
	
	
	public void removeCmt(int brdIdx, int cmtIdx) throws RemoveException{
		dao.removeCmt(brdIdx,cmtIdx);
	}
	

	/*
	 * public Comment findCmtByIdx(int brdIdx, int cmtIdx) throws FindException{
	 * Comment comment = dao.findCmtByIdx(brdIdx, cmtIdx); return comment; }
	 */
}
