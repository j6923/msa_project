package com.my.calendar.service;

import java.util.Date; 
import java.util.List;

import com.my.calendar.dao.CalendarDAOOracle;
import com.my.calendar.vo.CalInfo;
import com.my.calendar.vo.CalPost;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;

public class CalendarService {
	private CalendarDAOOracle dao = CalendarDAOOracle.getInstance();
	private static CalendarService service = new CalendarService();
	private CalendarService() {		
	
	}
	
	public static CalendarService getInstance() {
		return service;
	}
	
	
	public CalInfo addCal(CalInfo calinfo) throws AddException{
		dao.addCal(calinfo);
		return calinfo;
	}

	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException{
		
		return dao.findCalsByUIdx(uIdx);
	}
	
	public void modifyCal(CalInfo calinfo) throws ModifyException{
		dao.modifyCal(calinfo);
	}
	
	public void addCalPost(CalPost calpost) throws AddException{
		dao.addCalPost(calpost);
	}
	
	public List<CalPost> findCalsByUIdx(int uIdx, int calIdx, Date year, Date month) throws FindException{
		return dao.findCalsByUIdx(uIdx, calIdx, year, month);
	}
	
	public void modifyCalPost(CalPost calpost) throws ModifyException{
		dao.modifyCalPost(calpost);
	}
	
	
	public void removeCalPost(Date calDate) throws RemoveException{
		dao.removeCalPost(calDate);
	}


	
}
