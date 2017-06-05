package com.kedang.fenxiao.schedule.blacklist;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月13日 下午2:53:33 
 */
public class Constants
{
	/**
	 *手机号失败次数过多拉黑统计周期，单位：秒
	 */
	public static final int BLACKLIST＿PERIOD = 60;

	public static final String BLACK_LIST_STATISTIC_TIME = "black.list.statistic.time";

	public static final String BLACK_LIST_FAIL_COUNT = "black.list.fail.count";

	public static final int BLACK_LIST_DOWNSTREAM_ORDERSTATUS = 1;

	public static final String BLACK_LIST_REFRESH_URL = "black.list.refresh.url";

	/**
	 *任务执行次数
	 */
	public static int blacklist_statisticScheduleCount = 0;

}
