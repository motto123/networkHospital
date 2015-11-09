package com.huiyuan.networkhospital.entity.dengzhaojun;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ScBean implements Serializable {
	private String id,title, CreateTime, UID, CID, state, Score, Evaluation, CPhoto,
	CPrice, CAddress, CName,cPhone;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String cID) {
		CID = cID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScore() {
		return Score;
	}

	public void setScore(String score) {
		Score = score;
	}

	public String getEvaluation() {
		return Evaluation;
	}

	public void setEvaluation(String evaluation) {
		Evaluation = evaluation;
	}

	public String getCPhoto() {
		return CPhoto;
	}

	public void setCPhoto(String cPhoto) {
		CPhoto = cPhoto;
	}

	public String getCPrice() {
		return CPrice;
	}

	public void setCPrice(String cPrice) {
		CPrice = cPrice;
	}

	public String getCAddress() {
		return CAddress;
	}

	public void setCAddress(String cAddress) {
		CAddress = cAddress;
	}

	public String getCName() {
		return CName;
	}

	public void setCName(String cName) {
		CName = cName;
	}

	public String getcPhone() {
		return cPhone;
	}

	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}

}
