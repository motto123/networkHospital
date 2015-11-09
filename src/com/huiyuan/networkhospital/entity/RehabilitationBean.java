package com.huiyuan.networkhospital.entity;

public class RehabilitationBean {
	public String title,contents,time;

	public RehabilitationBean(){
		this.time="2015年5月28";
		this.contents="针灸视频";
		this.title="针灸";
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
