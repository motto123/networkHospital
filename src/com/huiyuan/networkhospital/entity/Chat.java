package com.huiyuan.networkhospital.entity;

public class Chat {

	private int No;
	
	private int type;
	
	private String msgType;
	
	private String text;
	
	private String pic;
	
	private String video;
	
	private String voice;
	
	private String time;

	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Chat(int no, int type, String msgType, String text, String pic,
			String video, String voice, String time) {
		super();
		No = no;
		this.type = type;
		this.msgType = msgType;
		this.text = text;
		this.pic = pic;
		this.video = video;
		this.voice = voice;
		this.time = time;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public int getNo() {
		return No;
	}

	public void setNo(int no) {
		No = no;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "Chat [No=" + No + ", type=" + type + ", msgType=" + msgType
				+ ", text=" + text + ", pic=" + pic + ", video=" + video
				+ ", voice=" + voice + ", time=" + time + "]";
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + No;
		result = prime * result + ((msgType == null) ? 0 : msgType.hashCode());
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + type;
		result = prime * result + ((video == null) ? 0 : video.hashCode());
		result = prime * result + ((voice == null) ? 0 : voice.hashCode());
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
		Chat other = (Chat) obj;
		if (No != other.No)
			return false;
		if (msgType == null) {
			if (other.msgType != null)
				return false;
		} else if (!msgType.equals(other.msgType))
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (type != other.type)
			return false;
		if (video == null) {
			if (other.video != null)
				return false;
		} else if (!video.equals(other.video))
			return false;
		if (voice == null) {
			if (other.voice != null)
				return false;
		} else if (!voice.equals(other.voice))
			return false;
		return true;
	}


	
	
}
