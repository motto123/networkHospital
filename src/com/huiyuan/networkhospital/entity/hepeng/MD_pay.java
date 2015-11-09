package com.huiyuan.networkhospital.entity.hepeng;

import java.util.ArrayList;

import com.huiyuan.networkhospital.entity.dengzhaojun.Dv_people;

public class MD_pay {
	private String Province,City,Area,Explain,Title,IsParticipate,Address;
	private ArrayList<Dv_people> listObj;
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
	public String getExplain() {
		return Explain;
	}
	public void setExplain(String explain) {
		Explain = explain;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getIsParticipate() {
		return IsParticipate;
	}
	public void setIsParticipate(String isParticipate) {
		IsParticipate = isParticipate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public ArrayList<Dv_people> getListObj() {
		return listObj;
	}
	public void setListObj(ArrayList<Dv_people> listObj) {
		this.listObj = listObj;
	}
	
}
