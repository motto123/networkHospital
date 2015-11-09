package com.huiyuan.networkhospital.entity.zlk;

/**
 * 收到环信的消息，用户为查看，记录消息数量和谁发的
 * @author jack
 *
 */
public class ReminderCount {

	private int dCount;
	
	private String doctor;
	
	private int cCount;
	
	private String coach;
	
	
	public ReminderCount() {
		// TODO Auto-generated constructor stub
	}


	public int getdCount() {
		return dCount;
	}


	public void setdCount(int dCount) {
		this.dCount = dCount;
	}


	public String getDoctor() {
		return doctor;
	}


	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}


	public int getcCount() {
		return cCount;
	}


	public void setcCount(int cCount) {
		this.cCount = cCount;
	}


	public String getCoach() {
		return coach;
	}


	public void setCoach(String coach) {
		this.coach = coach;
	}

	
	
}
