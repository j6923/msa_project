package com.my.community.vo;

public class Community {
	private int commuIdx;
	private String writerName;
	private int type; //0: 잡담 , 1: 정보, 2: 기타, 3: 공지사항
	private String title;
	private String content;
	private String attachment;
	private int views;
	private int thumbUps; 
	private list<comment> commments 
	public Community() {}

	/**
	 * 게시글 정보를 초기화한다.
	 * @param idx
	 * @param writerName
	 * @param type
	 * @param title
	 * @param content
	 * @param attachment
	 * @param views
	 * @param thumbUps
	 */
	public Community(int commuIdx, String writerName, int type, String title, String content, String attachment, int views, int thumbUps) {
		super();
		this.commuIdx = commuIdx;
		this.writerName = writerName;
		this.type = type;
		this.title = title;
		this.content = content;
		this.attachment = attachment;
		this.views = views;
		this.thumbUps = thumbUps;
	}

	public int getIdx() {
		return commuIdx;
	}

	public void setIdx(int idx) {
		this.commuIdx = idx;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getThumbUps() {
		return thumbUps;
	}

	public void setThumbUps(int thumbUps) {
		this.thumbUps = thumbUps;
	}
}
