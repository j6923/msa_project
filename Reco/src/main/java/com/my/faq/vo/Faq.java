package com.my.faq.vo;

public class Faq {
	private int faqIdx;
	private String content;
	private String reply;
	
	public Faq() {}

	/**
	 * faq 정보를 초기화한다.
	 * @param faqIdx
	 * @param content
	 * @param reply
	 */
	public Faq(int faqIdx, String content, String reply) {
		super();
		this.faqIdx = faqIdx;
		this.content = content;
		this.reply = reply;
	}

	public int getFaqIdx() {
		return faqIdx;
	}

	public void setFaqIdx(int faqIdx) {
		this.faqIdx = faqIdx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	
}
