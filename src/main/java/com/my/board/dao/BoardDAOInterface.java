package com.my.board.dao;

import java.util.List;

import com.my.board.vo.Board;
import com.my.board.vo.Comment;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

public interface BoardDAOInterface {
	
	
	/**
	 * 자유게시판글 목록을 모두 불러온다
	 * @return 자유게시판
	 * @throws FindException  발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                        검색할 수 없는 경우 예외발생한다
	 *                        또 자유게시판글이 하나도 없는경우에도 발생함
	 */
	public List<Board> findBrdAll() throws FindException;
	
	
	/**
	 * 자유게시판 상세보기를 한다
	 * @param brdIdx 자유게시판 글번호
	 * @return 클릭한 자유게시판 글
	 * @throws FindException   발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                         검색할 수 없는 경우 예외발생한다
	 */
	public Board findBrdByIdx(int brdIdx) throws FindException;  
	
	
	/**
	 * 글제목+내용에 해다하는 글들을 검색한다
	 * @param word 자유게시판 글제목+내용에 포함될 단어
	 * @return 제목+내용에 자유게시판 글목록 반환
	 * @throws FindException  발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                        검색할 수 없는 경우 예외발생한다
	 */
	public List<Board> findBrdByWord(String searchOption, String word) throws FindException; //이름중에서도 정확한 상품이름을 모르는 경우나 상품의름의 일부만 아는경우나 또는 상품번호를 정확히 아는 사용자는 많지않으므로 상품번호의 일부분만 알 수 있도록 상품번호로도 검색할 수 있고 상품이름으로도 검색할 수 있도록 메소드 만듬
	
	/**
	 * 글제목에 해당하는 글들을 검색한다
	 * @param word 자유게시판 글제목에 포함될 단어
	 * @return  제목에 해당하는 자유게시판 글목록 반환
	 * @throws FindException
	 */
	public List<Board> findBrdByTitle(String searchOption, String word) throws FindException;
	
	/**
	 * 닉네임에 해당하는 글들을 검색한다
	 * @param word 자유게시판 닉네임에 포함될 단어
	 * @return  닉네임에 해당하는 자유게시판 글목록 반환
	 * @throws FindException
	 */
	public List<Board> findBrdByUNickName(String searchOption, String word) throws FindException;
	/**
	 * 저장소에 자유게시판글을 추가한다.
	 * @param Board
	 * @throws AddException
	 */
	public void addBrd(Board board) throws AddException;
	
	
	/**
	 * 저장소의 자유게시판글에 댓글을 등록한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public void addCmt(Comment comment) throws AddException;
	
	
	/**
	 * 저장소의 자유게시판글을 수정한다.
	 * @param Board
	 * @throws ModifyException
	 */
	public void modifyBrd(Board board) throws ModifyException;
	
	
	/**
	 * 저장소의 자유게시판 글에달린 댓글을 수정한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public void modifyCmt(Comment comment) throws ModifyException;
	
	
	/**
	 * 저장소의 자유게시판글을 삭제한다.
	 * @param brdIdx
	 * @throws RemoveException
	 */
	public void removeBrd(int brdIdx) throws RemoveException;	
	
	
	/**
	 * 저장소의 자유게시판 글에달린 댓글을 삭제한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public void removeCmt(int brdIdx, int cmtIdx) throws RemoveException;
	
	public Comment findCmtByIdx(int brdIdx, int cmtIdx) throws FindException;
	
}
