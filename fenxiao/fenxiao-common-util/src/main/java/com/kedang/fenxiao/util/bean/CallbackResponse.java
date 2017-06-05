package com.kedang.fenxiao.util.bean;

public class CallbackResponse {

	private String fs_code;
	private String fs_message;
	
	public String getFs_code() {
		return fs_code;
	}
	public void setFs_code(String fsCode) {
		fs_code = fsCode;
	}
	public String getFs_message() {
		return fs_message;
	}
	public CallbackResponse(String fs_code, String fs_message) {
		super();
		this.fs_code = fs_code;
		this.fs_message = fs_message;
	}
	public void setFs_message(String fsMessage) {
		fs_message = fsMessage;
	}
	public CallbackResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CallbackResponse [fs_code=" + fs_code + ", fs_message="
				+ fs_message + "]";
	}
}