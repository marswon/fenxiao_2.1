package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_app")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXApp implements Serializable {

	private static final long serialVersionUID = 8743013129423376301L;

	@Id
	@Column(length = 32)
	private String appId;

	@Column(nullable = true, length = 32)
	private String ip;

	@Column(nullable = true, length = 32)
	private String port;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
