package com.huiyuan.networkhospital.entity.zlk;

/**
 * 教练指导的相关
 * @author jack
 *
 */
public class SUserData {

	private String id,uid,cid,uPhoto,cPhoto,uname,cname,state;
	
	public SUserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SUserData(String id, String uPhoto, String cPhoto, String uname,
			String cname) {
		super();
		this.id = id;
		this.uPhoto = uPhoto;
		this.cPhoto = cPhoto;
		this.uname = uname;
		this.cname = cname;
	}
	
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getcPhoto() {
		return cPhoto;
	}

	public void setcPhoto(String cPhoto) {
		this.cPhoto = cPhoto;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	
	
}
