package com.huiyuan.networkhospital.entity.dengzhaojun;

import java.io.Serializable;

public class OrderDoctor implements Serializable{
	String Id, Name, Occupation, OutpatientTime, Price, Introduction,Type;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getOccupation() {
		return Occupation;
	}

	public void setOccupation(String occupation) {
		Occupation = occupation;
	}

	public String getOutpatientTime() {
		return OutpatientTime;
	}

	public void setOutpatientTime(String outpatientTime) {
		OutpatientTime = outpatientTime;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getIntroduction() {
		return Introduction;
	}

	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
	
	
}
