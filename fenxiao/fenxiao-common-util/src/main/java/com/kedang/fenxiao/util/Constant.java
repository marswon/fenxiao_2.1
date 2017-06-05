package com.kedang.fenxiao.util;

/**
 * 自定义全局常量
 */
public abstract class Constant
{
	// 全国电信包
	public static final Integer[] dxData =
	{ 5, 10, 20, 30, 50, 60, 70, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1024, 2000, 2048, 3000, 3072,
			4000, 4096, 5000, 5120, 6144, 7168, 8192, 9216, 10240, 11264, 12288 };
	// 全国移动包
	public static final Integer[] ydData =
	{ 5, 10, 20, 30, 50, 60, 70, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1024, 2000, 2048, 3000, 3072,
			4000, 4096, 5000, 5120, 6144, 7168, 8192, 9216, 10240, 11264, 12288 };
	// 全国联通包
	public static final Integer[] ltData =
	{ 5, 10, 20, 30, 50, 60, 70, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1024, 2000, 2048, 3000, 3072,
			4000, 4096, 5000, 5120, 6144, 7168, 8192, 9216, 10240, 11264, 12288 };

	public static final Integer[] huafeiData =
	{ 1, 2, 3, 5, 10, 20, 30, 50, 70, 100, 150, 200, 300, 500 };
	public static final long ADMIN_NUMBER = 1;
	/**
	 * Hibernate版本号
	 */
	public static final String HIBERNATE_VERSION = "4.1.8.Final";
	/**
	 * 通用状态位(整形):two
	 */
	public static final int INT_TWO = 2;
	/**
	 * 通用状态位(整形):yes
	 */
	public static final int INT_YES = 1;
	/**
	 * 通用状态位(整形):no
	 */
	public static final int INT_NO = 0;
	/**
	 * 通用状态位(字符串型):yes
	 */
	public static final String STRING_YES = "1";
	/**
	 * 通用状态位(字符串型):no
	 */
	public static final String STRING_NO = "0";
	/**
	 * 会员用户登录key
	 */
	public static final String LOGIN_USER = "loginUser";
	/**
	 * 商户用户登录key
	 */
	public static final String LOGIN_SHOP = "loginShop";
	/**
	 * 保存上传文件的根目录
	 */
	public static final String UPLOAD_BASE = "app.upload.base";
	/**
	 * 上传的九宫格图片文件夹
	 */
	public static final String UPLOAD_JGG_FOLDER = "jgg";
	/**
	 * 上传的商户头像文件夹
	 */
	public static final String UPLOAD_SHTX_FOLDER = "shtx";
	/**
	 * 上传的excel字段名
	 */
	public static final String EXCEL_ATTRIBUTE = "ea";
	/**
	 * 分配任务时间
	 */
	public static final String DISTRIBUTIONTIME = "tasking.distributionTime";
	public static final String CLEARADMINLISTTIME = "tasking.clearadminlistTime";

	// 当前环境配置
	// TODO 上线时别忘了修改此处配置
	// public static final String CURRENT_ENV = "prod";
	//public static final String CURRENT_ENV = "test";

	//刷新通道信息
	public static final String REFRESH_CHANNEL_URL = "channel.refresh.url";

	//验证码位置
	public static final String CAPTCHA_CODE_PATH = "captcha.code.path";

	//黑白名单
	public static final String REFRESH_BLACK_WHITE_LIST_A = "refresh.black.white.list.url.a";
	public static final String REFRESH_BLACK_WHITE_LIST_B = "refresh.black.white.list.url.b";
	//其它类型
	public static final int OTHRER_FLOWTYPE=-1;
	public static final int FLOW_TYPE=0;//流量
	public static final int BILL_TYPE=1;//话费
	public static final int GAS_CARD_TYPE=3;//加油卡
	public static final int ROAM_flow = 0;//漫游
	public static final int local_flow=1;//本地
	public static final int other_flow = -1;//其他流量类型
//	public static final String HOST_URL = "http://localhost:8021";
	public static final String HOST_URL = "http://interface.kedang.net:8080";
	public static final String FLOW_ORDER_URL = HOST_URL + "/fenxiao-if/General/order";
	public static final String BILL_ORDER_URL = HOST_URL + "/fenxiao-if/General/bill";
	public static final String GAS_CARD_ORDER_URL = HOST_URL + "/fenxiao-if/General/gascard";

}
