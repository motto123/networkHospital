package com.huiyuan.networkhospital.entity;

public class Medicine {

	//药品编号
	private String id;
	private String Vid;
	private String DrugsID;
	private String name;
	private String num;
	private String price;
	private String CreateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVid() {
		return Vid;
	}
	public void setVid(String vid) {
		Vid = vid;
	}
	public String getDrugsID() {
		return DrugsID;
	}
	public void setDrugsID(String drugsID) {
		DrugsID = drugsID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
}
