package com.my.calendar.dao;

import java.util.Date; 
import java.util.List;

import com.my.calendar.vo.CalInfo;
import com.my.calendar.vo.CalPost;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

public interface CalendarDAOInterface {
	/**
	 * 고객의 캘린더 추가한다  
	 * @param calendarInfo
	 * @throws AddException
	 */
	public CalInfo addCal(CalInfo calInfo) throws AddException;


	/**
	 * 고객번호와 캘린더 번호로 캘린더들을 가져온다. 
	 * @param uIdx
	 * @param calIdx
	 * @return
	 * @throws FindException
	 */
	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException;
	
	
	/**
	 * 고객의 캘린더 기본정보(카테고리, 썸네일)를 수정한다
	 * @param calinfo
	 * @throws ModifyException
	 */
	public void modifyCal(CalInfo calinfo) throws ModifyException;
	
	/**
	 * 고객의 캘린더 글을 추가한다
	 * @param calpost
	 * @throws AddException
	 */
	public void addCalPost(CalPost calpost) throws AddException;
	
	/**
	 * 캘린더글을 년/월 기준으로 가져온다
	 * @param uIdx
	 * @param calIdx
	 * @param year
	 * @param month
	 * @return
	 * @throws FindException
	 */
	public List<CalPost> findCalPostByDate(String calDate) throws FindException;
	
	/**
	 * 고객의 캘린더글을 수정한다
	 * @param calpost
	 * @throws ModifyException
	 */
	public void modifyCalPost(CalPost calpost) throws ModifyException;
	
	
	/**
	 * 고객의 캘린더글을 삭제한다 
	 * @param calDate
	 * @throws RemoveException
	 */
	public void removeCalPost(Date calDate) throws RemoveException;


	void removeCal(CalInfo calinfo) throws RemoveException;


	


	


	
	
}