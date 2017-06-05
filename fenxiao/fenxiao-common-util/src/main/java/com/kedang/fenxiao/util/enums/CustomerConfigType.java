package com.kedang.fenxiao.util.enums;


/**
 * 通用配置类型
 */
public enum CustomerConfigType {
	hkfl(1, "还款周期/费率"),
	yqtx(2, "逾期账单提醒"),
	fqfk(3, "分期付款费率"),
	tx(4, "提现配置"),
	shtx(5,"商户提现配置"),
	tjrjl(6,"推荐人奖励配置"),
	ppxjjl(7,"拼拼现金奖励配置"),
	qbtx(8,"钱包提现配置"),
	jgg(9,"九宫格配置"),
	sphd(10,"碎片活动时间配置"),
	splq(11,"碎片领取公告配置"),
	pphdkg(12,"拼拼活动开关"),
	tjrjlkg(13,"推荐人奖励开关"),
	txshkg(14,"额度提现审核开关"),
	xjrzkg(15,"学籍认证开关"),
	rwedpz(16,"任务额度配置"),
	xxwrzzdsh(17,"学信网认证自动审核"),
	zdyq(18,"账单延期"),
	dxqh(19,"短信切换"),
	txzdshsb(20,"提现自动审核失败"),//20160107 add by zhangqi
	txzdshtg(21,"提现自动审核通过"),//20160107 add by zhangqi
	qbtxshkg(22,"钱包提现审核开关"),//20160122 add by zhangqi
	cardactivity(23,"卡牌等活动配置"),
	messagemodel(24,"消息模板");
	private CustomerConfigType(int type, String des) {
		this.type = type;
		this.des = des;
	}
	private int type;
	private String des;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
