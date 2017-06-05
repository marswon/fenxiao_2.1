package com.kedang.fenxiao.util.bean;

public class commonBean {
	private String code;
	private Object msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	public commonBean(String code, Object msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

}
