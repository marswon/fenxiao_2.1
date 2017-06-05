package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 推荐人奖励配置
 */
public class RecommendAward {
	private BigDecimal recAward=new BigDecimal(0);//推荐人奖励
	private BigDecimal invitedAward=new BigDecimal(0);//被邀请人奖励
	private BigDecimal regAward=new BigDecimal(0);//注册成功奖励
	public BigDecimal getRecAward() {
		return recAward;
	}
	public void setRecAward(BigDecimal recAward) {
		this.recAward = recAward;
	}
	public BigDecimal getInvitedAward() {
		return invitedAward;
	}
	public void setInvitedAward(BigDecimal invitedAward) {
		this.invitedAward = invitedAward;
	}
	public BigDecimal getRegAward() {
		return regAward;
	}
	public void setRegAward(BigDecimal regAward) {
		this.regAward = regAward;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("recAward",getRecAward())
			.append("invitedAward",getInvitedAward())
			.append("regAward",getRegAward())
			.toString();
	}

}
