package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import com.kedang.fenxiao.util.Constant;


/**
 *	版本号
 */
public abstract class Version implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String hibernateVersion=Constant.HIBERNATE_VERSION;

	public String getHibernateVersion() {
		return hibernateVersion;
	}

	public void setHibernateVersion(String hibernateVersion) {
		this.hibernateVersion = hibernateVersion;
	}
	
	
}
