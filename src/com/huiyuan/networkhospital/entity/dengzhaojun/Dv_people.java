package com.huiyuan.networkhospital.entity.dengzhaojun;

import java.io.Serializable;

import com.huiyuan.networkhospital.NApplication;

import android.content.IntentSender;

public class Dv_people implements Serializable {
	String name, AID, sex, Phone, Brief, createTime, UID=NApplication.userid,IsRefund="0"
			,Number,Age;

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

	public String getIsRefund() {
		return IsRefund;
	}

	public void setIsRefund(String isRefund) {
		IsRefund = isRefund;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAID() {
		return AID;
	}

	public void setAID(String aID) {
		AID = aID;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getBrief() {
		return Brief;
	}

	public void setBrief(String brief) {
		Brief = brief;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

}
