package com.kedang.fenxiao.schedule.monitor;

public class Constants
{

	public static String lineSeparator = System.getProperty("line.separator");

	/**
	 *相同消息通知时间间隔 
	 */
	public static int time_difference = 30 * 60 * 1000;

	/*=====================失败率高==========================*/
	/**
	 *总订单数至少为10条才会告警
	 */
	public static int failRate_warning_count = 10;

	public static double failRate_warning_rate = 0.30;

	public static String failRate_message_template = "[%s]%s内失败率为%s,总订单量为%s。";

	public static int failRate_statistic_minute = 120;

	/*=====================失败率高==========================*/

	/*=====================卡单率高==========================*/
	/**
	 *总订单数至少为10条才会告警
	 */
	public static int kadan_warning_count = 10;

	public static double kadan_warning_rate = 0.30;

	public static String kadan_message_template = "[%s]%s内卡单率为%s,总订单量为%s。";

	public static int kadan_statistic_minute = 720;

	/*=====================卡单率高==========================*/

	/*=====================连续失败==========================*/

	public static int continuefail_warning_count = 50;

	public static String continuefail_message_template = "[%s]已连续失败%s单。";

	/*=====================连续失败==========================*/

	public static final int MONITOR_PERIOD = 60;

	//
	public static String WEICHAT_CORPID;
	public static String WEICHAT_SECRET;
	public static String WEICHAT_URL;
	public static String WEICHAT_SEND_URL;
	public static String WEICHAT_ACCESS_TOKEN;

}
