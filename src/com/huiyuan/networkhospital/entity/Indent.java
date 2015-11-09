package com.huiyuan.networkhospital.entity;

public class Indent {
	private String paidStyle,num,style,doctor,title,time;
	public Indent() {
		this.paidStyle = "检查费";
		this.num = "10";
		this.style = "已支付";
		this.doctor = "李志斌";
		this.title = "主任医师";
		this.time = "2015年5月20日";
	}

	public Indent(int i) {
		this.paidStyle = "检查费";
		this.num = "10";
		this.style = "未结算";
		this.doctor = "李志斌";
		this.title = "主任医师";
		this.time = "2015年5月20日";
	}
	public String getPaidStyle() {
		return paidStyle;
	}

	public void setPaidStyle(String paidStyle) {
		this.paidStyle = paidStyle;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getTitle() {
		return "("+title+")";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
