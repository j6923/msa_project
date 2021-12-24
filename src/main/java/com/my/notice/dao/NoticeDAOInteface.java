package com.my.notice.dao;

import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.vo.Notice;

public interface NoticeDAOInteface {
	

	
	/**
	 * 공지사항 글들을 모두 불러온다.
	 * @param ntcidx
	 * @return
	 */
	public List<Notice> findNtcAll() throws FindException;  
	
	
	
	/**
	 * 공지사항 상세보기를 한다.
	 * @param ntcidx
	 * @return
	 */
	public Notice findNtcByIdx(int ntcIdx) throws FindException;
	
	
	
	/**
	 * 단어를 포함한 제목이나 내용을 갖는 글을 반환한다. 
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public List<Notice> findNtcByWord(String word) throws FindException; 
	
	
	
	/**
	 * 저장소에 공지사항글을 추가한다.
	 * @param community
	 * @throws AddException
	 */
	public void addNtc(Notice notice) throws AddException;
	
	
	
	/**
	 * 저장소의 공지사항글을 수정한다.
	 * @param community
	 * @throws ModifyException
	 */
	public void modifyNtc(Notice notice) throws ModifyException;
	
	
	
	/**
	 * 저장소의 공지사항글을 삭제한다.
	 * @param ntcidx
	 * @throws RemoveException
	 */
	public void removeNtc(int ntcIdx) throws RemoveException;
	
	
}
