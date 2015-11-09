package com.huiyuan.networkhospital.entity;

public class DvBean {
	String rowId, id, CreateTime, State, Title, Explain, Province, City, Area,
			Address, UID, Dname, DPhone, ATime, IsParticipate, num,Reason;

	
	
	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsParticipate() {
		return IsParticipate;
	}

	public void setIsParticipate(String isParticipate) {
		IsParticipate = isParticipate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		num = num;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getExplain() {
		return Explain;
	}

	public void setExplain(String explain) {
		Explain = explain;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getDname() {
		return Dname;
	}

	public void setDname(String dname) {
		Dname = dname;
	}

	public String getDPhone() {
		return DPhone;
	}

	public void setDPhone(String dPhone) {
		DPhone = dPhone;
	}

	public String getATime() {
		return ATime;
	}

	public void setATime(String aTime) {
		ATime = aTime;
	}

}
