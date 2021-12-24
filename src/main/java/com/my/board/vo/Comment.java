package com.my.board.vo;

import java.util.Date;

public class Comment {
	private int cmtIdx;
	private int brdIdx;
	private String cmtContent;
	private int cmtParentIdx;
	private Date cmtCreateAt;
	private String uNickName;
	
	//멤버변수들의 접근제어자를 private으로 하면 setter, getter 메소드들이 필요하고 생성자를 통해서 초기화하는 작업도 필요하다.
	 //매개변수없는 생성자 만듬
	public Comment() {	  
	}
	
	//매개변수들을 초기화해줄 수 있는 생성자 만듬
	public Comment(int cmtIdx, int brdIdx, String cmtContent, int cmtParentIdx, Date cmtCreateAt, String uNickName) {
		super();
		this.cmtIdx = cmtIdx;
		this.brdIdx = brdIdx;
		this.cmtContent = cmtContent;
		this.cmtParentIdx = cmtParentIdx;
		this.cmtCreateAt = cmtCreateAt;
		this.uNickName = uNickName;
	}
		
	//setter, getter메소드 만듬
	
	public int getCmtIdx() {
		return cmtIdx;
	}	
	
	public void setCmtIdx(int cmtIdx) {
		this.cmtIdx = cmtIdx;
	}
	
	public int getBrdIdx() {
		return brdIdx;
	}
	
	public void setBrdIdx(int brdIdx) {
		this.brdIdx = brdIdx;
	}
	
	public String getCmtContent() {
		return cmtContent;
	}
	
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	
	public int getCmtParentIdx() {
		return cmtParentIdx;
	}
	
	public void setCmtParentIdx(int cmtParentIdx) {
		this.cmtParentIdx = cmtParentIdx;
	}
	
	public Date getCmtCreateAt() {
		return cmtCreateAt;
	}	
	
	public void setCmtCreateAt(Date cmtCreateAt) {
		this.cmtCreateAt = cmtCreateAt;
	}
	
	public String getUNickName() {
		return uNickName;
	}
	
	public void setUNickName(String uNickName) {
		this.uNickName = uNickName;
	}
}
