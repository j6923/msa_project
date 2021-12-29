package com.my.calendar.vo;
/*
 * 캘린더 글쓰기 객체용 클래스 
 * 캘린더 글쓰기 정보( 글인덱스, 지정날짜, 리뷰내용, 대표사진, 서브사진 3개, 캘린더번호(카테고리용), 회원번호)
 */
import java.util.Date;

import com.my.customer.vo.Customer;
import com.my.calendar.vo.CalInfo;

public class CalPost {

//
//	private int uIdx;
//	private int calIdx;
	private String calDate;
	private String calMemo;
	private String calMainImg; // 원본메인사진명
	private String scalMainImg; // 저장된메인사진명
	private String calImg1;
	private String calImg2;
	private String calImg3;
	private Date calPostCreateAt;
	
	
	public CalPost() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CalPost(String calDate, String calMemo, String calMainImg, String scalMainImg,
			String calImg1, String calImg2,String calImg3, Date calPostCreateAt) {
		super();
//		this.uIdx = uIdx;
//		this.calIdx = calIdx;
		this.calDate = calDate;
		this.calMemo = calMemo;
		this.calMainImg = calMainImg;
		this.calImg1 = calImg1;
		this.calImg2 = calImg2;
		this.calImg3 = calImg3;
		this.calPostCreateAt = calPostCreateAt;
	}


//	public int getuIdx() {
//		return uIdx;
//	}
//
//
//	public void setuIdx(int uIdx) {
//		this.uIdx = uIdx;
//	}
//
//
//	public int getCalIdx() {
//		return calIdx;
//	}
//
//
//	public void setCalIdx(int calIdx) {
//		this.calIdx = calIdx;
//	}
//

	public String getCalDate() {
		return calDate;
	}


	public void setCalDate(String calDate) {
		this.calDate = calDate;
	}


	public String getCalMemo() {
		return calMemo;
	}


	public void setCalMemo(String calMemo) {
		this.calMemo = calMemo;
	}


	public String getCalMainImg() {
		return calMainImg;
	}


	public void setCalMainImg(String calMainImg) {
		this.calMainImg = calMainImg;
	}


	public String getScalMainImg() {
		return scalMainImg;
	}


	public void setScalMainImg(String scalMainImg) {
		this.scalMainImg = scalMainImg;
	}


	public String getCalImg1() {
		return calImg1;
	}


	public void setCalImg1(String calImg1) {
		this.calImg1 = calImg1;
	}


	public String getCalImg2() {
		return calImg2;
	}


	public void setCalImg2(String calImg2) {
		this.calImg2 = calImg2;
	}


	public String getCalImg3() {
		return calImg3;
	}


	public void setCalImg3(String calImg3) {
		this.calImg3 = calImg3;
	}


	public Date getCalPostCreateAt() {
		return calPostCreateAt;
	}


	public void setCalPostCreateAt(Date calPostCreateAt) {
		this.calPostCreateAt = calPostCreateAt;
	}
	
}
	



	