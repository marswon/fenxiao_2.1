package com.kedang.fenxiao.util.po;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 活动配置
 */
public class ActivityConfiguration {
	
	private String title;				//活动标题
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date prepareStartTime;		//预热开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date prepareEndTime;		//活动预热结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date startTime;				//活动正式开始时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date endTime;				//活动正式结束时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date closeTime;				//活动完全关闭时间
	private List<String> imgUrls;		//活动图片链接
	private String activityRemark;		//活动说明
	private String gameRemark;			//游戏说明
	private String actionUrl;			//活动执行方法
	private String endActionUrl;		//活动结束调用url地址
	private String actionParas;			//活动进行中需要的参数名称
	
	private List<PrizeConfiguration> prizeConfiguration; //游戏配置
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getPrepareStartTime() {
		return prepareStartTime;
	}
	public void setPrepareStartTime(Date prepareStartTime) {
		this.prepareStartTime = prepareStartTime;
	}
	public Date getPrepareEndTime() {
		return prepareEndTime;
	}
	public void setPrepareEndTime(Date prepareEndTime) {
		this.prepareEndTime = prepareEndTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public List<String> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(List<String> imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getActivityRemark() {
		return activityRemark;
	}
	public void setActivityRemark(String activityRemark) {
		this.activityRemark = activityRemark;
	}
	public String getGameRemark() {
		return gameRemark;
	}
	public void setGameRemark(String gameRemark) {
		this.gameRemark = gameRemark;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getEndActionUrl() {
		return endActionUrl;
	}
	public void setEndActionUrl(String endActionUrl) {
		this.endActionUrl = endActionUrl;
	}
	public String getActionParas() {
		return actionParas;
	}
	public void setActionParas(String actionParas) {
		this.actionParas = actionParas;
	}
	public List<PrizeConfiguration> getPrizeConfiguration() {
		return prizeConfiguration;
	}
	public void setPrizeConfiguration(List<PrizeConfiguration> prizeConfiguration) {
		this.prizeConfiguration = prizeConfiguration;
	}
}
