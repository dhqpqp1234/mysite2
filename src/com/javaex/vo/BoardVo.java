package com.javaex.vo;

public class BoardVo extends UserVo {
	
	//필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	
	//생성자
	public BoardVo(){
		
	}
	
	
	
	public BoardVo(String title, String content) {
		this.title = title;
		this.content = content;
	}

	

	public BoardVo(String title, String content, int userNo) {
		super();
		this.title = title;
		this.content = content;
		this.userNo = userNo;
	}



	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo) {
	this.no = no;
	this.title = title;
	this.content = content;
	this.hit = hit;
	this.regDate = regDate;
	this.userNo = userNo;
	}
	
	public BoardVo(int no, String title, String content, String name, int hit, String regDate, int userNo) {
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regDate = regDate;
		this.userNo = userNo;
		super.setName(name);
		}
	//메소드 gs

	public int getNo() {
		return no;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public void setNo(int no) {
		this.no = no;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	

	
	
	//메소드 일반
	
	@Override
	public String toString() {
		return "BorderVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regDate="
				+ regDate + ", userNo=" + userNo + "]";
	}
	
}
