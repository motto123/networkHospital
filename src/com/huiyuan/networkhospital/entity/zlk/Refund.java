package com.huiyuan.networkhospital.entity.zlk;

public class Refund {
	/*
	 * name				姓名
AID				预约ID
sex				性别
Phone				联系电话
Brief				病情简介
createTime				创建时间
UID
				创建人ID
IsRefund
				0正常1已退款
Age				年龄
Number
				订单号
id				人员详细信息ID   必传

	 */
	private String name,AID,sex,Phone,Brief,createTime,UID,IsRefund,Age,Number,id;

	public Refund() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getIsRefund() {
		return IsRefund;
	}

	public void setIsRefund(String isRefund) {
		IsRefund = isRefund;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Refund [name=" + name + ", AID=" + AID + ", sex=" + sex
				+ ", Phone=" + Phone + ", Brief=" + Brief + ", createTime="
				+ createTime + ", UID=" + UID + ", IsRefund=" + IsRefund
				+ ", Age=" + Age + ", Number=" + Number + ", id=" + id + "]";
	}




}
