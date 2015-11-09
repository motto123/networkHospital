package com.huiyuan.networkhospital.entity.zlk;

/**
 * 复诊拿药的相关
 * @author jack
 *
 */
public class MUserData {

	private String id,uid,did,uPhoto,dPhotoe,uname,dname,state;
	
	public MUserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MUserData(String id, String uPhoto, String dPhotoe, String uname,
			String dname) {
		super();
		this.id = id;
		this.uPhoto = uPhoto;
		this.dPhotoe = dPhotoe;
		this.uname = uname;
		this.dname = dname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getuPhoto() {
		return uPhoto;
	}

	public void setuPhoto(String uPhoto) {
		this.uPhoto = uPhoto;
	}

	public String getdPhotoe() {
		return dPhotoe;
	}

	public void setdPhotoe(String dPhotoe) {
		this.dPhotoe = dPhotoe;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
