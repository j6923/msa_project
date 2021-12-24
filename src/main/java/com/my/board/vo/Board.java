package com.my.board.vo;

import java.util.Date;
import java.util.List;

public class Board {
	private int brdIdx;
	private String uNickName;
	private int brdType; //0: 잡담 , 1: 정보, 2: 기타               3: 공지사항 얘는 이제 분류에서 없애도 될듯!
	private String brdTitle;
	private String brdContent;
	private String brdAttachment;
	private int brdviews;
	private int brdThumbUp; 
	private Date brdCreateAt;
	private List<Comment> comments;
	public Board() {}

	/**
	 * 게시글 정보를 초기화한다.
	 * @param brdIdx
	 * @param uNickName
	 * @param brdType
	 * @param brdTitle
	 * @param brdContent
	 * @param brdAttachment
	 * @param brdviews
	 * @param brdThumbUp
	 * @param comments
	 */
	public Board(int brdIdx, String uNickName, int brdType, String brdTitle, String brdContent, String brdAttachment, int brdviews, int brdThumbUp, Date brdCreateAt, List<Comment> comments) {
		super();
		this.brdIdx = brdIdx;
		this.uNickName = uNickName;
		this.brdType = brdType;
		this.brdTitle = brdTitle;
		this.brdContent = brdContent;
		this.brdAttachment = brdAttachment;
		this.brdviews = brdviews;
		this.brdThumbUp = brdThumbUp;
		this.brdCreateAt = brdCreateAt;
		this.comments = comments;
	}

	public int getBrdIdx() {
		return brdIdx;
	}

	public void setBrdIdx(int brdIdx) {
		this.brdIdx = brdIdx;
	}

	public String getUNickName() {
		return uNickName;
	}

	public void setUNickName(String uNickName) {
		this.uNickName = uNickName;
	}

	public int getBrdType() {
		return brdType;
	}

	public void setBrdType(int brdType) {
		this.brdType = brdType;
	}

	public String getBrdTitle() {
		return brdTitle;
	}

	public void setBrdTitle(String brdTitle) {
		this.brdTitle = brdTitle;
	}

	public String getBrdContent() {
		return brdContent;
	}

	public void setBrdContent(String brdContent) {
		this.brdContent = brdContent;
	}

	public String getBrdAttachment() {
		return brdAttachment;
	}

	public void setBrdAttachment(String brdAttachment) {
		this.brdAttachment = brdAttachment;
	}

	public int getBrdviews() {
		return brdviews;
	}

	public void setBrdviews(int brdviews) {
		this.brdviews = brdviews;
	}

	public int getBrdThumbUp() {
		return brdThumbUp;
	}

	public void setBrdThumbUp(int brdThumbUp) {
		this.brdThumbUp = brdThumbUp;
	}
	
	public Date getBrdCreateAt() {
		return brdCreateAt;
	}
	
	public void setBrdCreateAt(Date brdCreateAt) {
		this.brdCreateAt = brdCreateAt;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments){
		this.comments = comments;
	}

}
