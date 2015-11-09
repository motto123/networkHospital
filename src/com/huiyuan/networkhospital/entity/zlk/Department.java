/**
 * 
 */
package com.huiyuan.networkhospital.entity.zlk;

/**
 * @author Administrator
 *
 */
public class Department {

	//[{"Introduction":"1111111111","ID":3,"name":"11111"}
	private String ID;
	
	private String name;
	
	private String Introduction;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Department(String iD, String name, String introduction) {
		super();
		ID = iD;
		this.name = name;
		Introduction = introduction;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return Introduction;
	}

	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result
				+ ((Introduction == null) ? 0 : Introduction.hashCode());
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
		Department other = (Department) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (Introduction == null) {
			if (other.Introduction != null)
				return false;
		} else if (!Introduction.equals(other.Introduction))
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
		return "Department [ID=" + ID + ", name=" + name + ", Introduction="
				+ Introduction + "]";
	}
	
	

	
}
