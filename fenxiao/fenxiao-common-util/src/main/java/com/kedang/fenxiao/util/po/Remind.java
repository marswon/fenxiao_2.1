package com.kedang.fenxiao.util.po;

/**
 * 提醒
 */
public class Remind {
	// 提醒时间间隔
	private Integer interval;
	// App消息推送
	private Integer app;
	// 手机短信
	private Integer sms;
	// 电话催款
	private Integer phone;

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getApp() {
		return app;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	public Integer getSms() {
		return sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

}
