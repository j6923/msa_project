package com.my.customer.vo;
/**
 * ����ü�� Ŭ�����̴�
 * ������(�ε���, �̸�, �г���, �̸���, ��й�ȣ)
 * @author Hyeseong
 * 
 */
public class Customer{
	private int idx;
	private String name;
	private String nickname;
	private String email;
	private String pwd;
	
	public Customer(){}
	
	/**
	 * �������� �ʱ�ȭ�Ѵ�.
	 * @param idx
	 * @param name
	 * @param nickname
	 * @param email
	 * @param pwd
	 */
	public Customer(int idx, String name, String nickname, String email, String pwd) {
		super();
		this.idx = idx;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.pwd = pwd;
	}

	/**
	 * �������� ����Ѵ�
	 */
	@Override
	public String toString() {
		return "Customer [idx=" + idx + ", name=" + name + ", nickname=" + nickname + ", email=" + email + ", pwd=" + pwd + "]";
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}