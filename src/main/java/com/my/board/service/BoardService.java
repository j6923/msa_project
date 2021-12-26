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
	
	
	
	
	public void addBrd(Board b) throws AddException{
		dao.addBrd(b);
	}
	
	public void addCmt(Comment comment) throws AddException{
		dao.addCmt(comment);
	}
	
	public Board findBrdByIdx(int brdIdx) throws FindException{
		try {
		Board b =dao.findBrdByIdx(brdIdx);
		return b;
		}catch(FindException e){
			throw new FindException("해당글이 없습니다");
		}
	}
	
	public List<Board> findBrdByWord(String word) throws FindException{
		return dao.findBrdByWord(word);
	}
	
	public List<Board> findBrdAll() throws FindException{
		return dao.findBrdAll();
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
