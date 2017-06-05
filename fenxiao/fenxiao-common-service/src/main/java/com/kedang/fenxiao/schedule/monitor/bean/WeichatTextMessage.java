package com.kedang.fenxiao.schedule.monitor.bean;

public class WeichatTextMessage {
	private int agentid;
	private String touser;
	private String toparty;
	private String totag;
	private String msgtype;
	private String safe;
	private WeichatTextMessageContent text = new WeichatTextMessageContent();

	public WeichatTextMessageContent getText() {
		return text;
	}

	public void setText(WeichatTextMessageContent text) {
		this.text = text;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getToparty() {
		return toparty;
	}

	public void setToparty(String toparty) {
		this.toparty = toparty;
	}

	public String getTotag() {
		return totag;
	}

	public void setTotag(String totag) {
		this.totag = totag;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

}
