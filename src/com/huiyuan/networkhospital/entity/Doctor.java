package com.huiyuan.networkhospital.entity;

public class Doctor {

	private int No;
	private String name;
	private String course;
	private String level;
	private String duty;
	private String introduce;
	/**
	 * 出诊时间
	 */
	private String Schedule;
	public Doctor() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Doctor(int no, String name, String course, String level,
			String duty, String introduce, String schedule) {
		super();
		No = no;
		this.name = name;
		this.course = course;
		this.level = level;
		this.duty = duty;
		this.introduce = introduce;
		Schedule = schedule;
	}



	public String getDuty() {
		return duty;
	}


	public void setDuty(String duty) {
		this.duty = duty;
	}


	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getSchedule() {
		return Schedule;
	}
	public void setSchedule(String schedule) {
		Schedule = schedule;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + No;
		result = prime * result
				+ ((Schedule == null) ? 0 : Schedule.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((duty == null) ? 0 : duty.hashCode());
		result = prime * result
				+ ((introduce == null) ? 0 : introduce.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		if (No != other.No)
			return false;
		if (Schedule == null) {
			if (other.Schedule != null)
				return false;
		} else if (!Schedule.equals(other.Schedule))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (duty == null) {
			if (other.duty != null)
				return false;
		} else if (!duty.equals(other.duty))
			return false;
		if (introduce == null) {
			if (other.introduce != null)
				return false;
		} else if (!introduce.equals(other.introduce))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Doctor [No=" + No + ", name=" + name + ", course=" + course
				+ ", level=" + level + ", duty=" + duty + ", introduce="
				+ introduce + ", Schedule=" + Schedule + "]";
	}

	
}
