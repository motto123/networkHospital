package com.huiyuan.networkhospital.entity;

public class Course {

	private int No;
	
	private String course;
	
	private String content;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(int no, String course, String content) {
		super();
		No = no;
		this.course = course;
		this.content = content;
	}

	public int getNo() {
		return No;
	}

	public void setNo(int no) {
		No = no;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + No;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
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
		Course other = (Course) obj;
		if (No != other.No)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Course [No=" + No + ", course=" + course + ", content="
				+ content + "]";
	}
	
	
}
