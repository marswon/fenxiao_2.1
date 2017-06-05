package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;


/**
 * 推荐人奖励配置
 */
public class RecommendSwitch {
	private int status=0;//推荐人奖励活动开关1:开启0:关闭
	private BigDecimal totalAward=new BigDecimal(0);//奖励额度
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getTotalAward() {
		return totalAward;
	}
	public void setTotalAward(BigDecimal totalAward) {
		this.totalAward = totalAward;
	}
	

}
