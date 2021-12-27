package com.my.notice.service;

import java.util.List;

import com.my.board.vo.Board;
import com.my.exception.AddException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.notice.dao.NoticeDAOOracle;
import com.my.notice.vo.Notice;
import com.my.exception.FindException;

public class NoticeService {
	private NoticeDAOOracle dao = NoticeDAOOracle.getinstance();
	private static NoticeService service = new NoticeService();
	private NoticeService() {
		
	}
	public static NoticeService getinstance() {
		return service;
	}
	
	
	//관리자만 글 수정,삭제 ,추가 가능하게 바꿔야함.
	public void addNtc(Notice n) throws AddException{
		dao.addNtc(n);
	}
	
	public List<Notice> findNtcAll() throws FindException {
		return dao.findNtcAll();
	}
	
	public Notice findNtcByIdx(int ntcIdx) throws FindException {
		try {
			Notice n=dao.findNtcByIdx(ntcIdx);
			return n;
		} catch (FindException e) {
			throw new FindException("해당글이 없습니다.");
		}
	}
	
	//공지사항 제목 검색
	public List<Notice> findNtcByTitle(String word) throws FindException{
		return dao.findNtcByTitle(word);
	}
	
	//공지사항 제목+내용 검색
	public List<Notice> findNtcByWord(String word) throws FindException{
		return dao.findNtcByWord(word);
	}
	
	public void modifyNtc(Notice n) throws ModifyException{
		dao.modifyNtc(n);
	}
	
	public void removeNtc(int ntcIdx) throws RemoveException{
		dao.removeNtc(ntcIdx);
	}
}
