/**************************************************************************
 * Copyright (c) 2014-2023  杭州学信科技有限公司
 * All rights reserved.
 * <p>
 * 项目名称：fyd-cms
 * 版权说明：本软件属杭州学信科技有限公司所有，在未获杭州学信科技有限公司正式授权
 * 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受
 * 知识产权保护的内容。
 ***************************************************************************/
package com.kedang.fenxiao.common;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangqi@fuyidai.me
 * @version V1.0.0
 * @date 2015/12/29
 */
public class RedisConfigure {

    //用户超时时限(参考超时时间单位超时时间单位)
    public static final int USER_TIMEOUT = 604800;
    //用户位置信息失效时间(参考超时时间单位超时时间单位)
    public static final int USER_LOCATION_TIMEOUT = 300;
    //航班信息缓存超时时间(24小时)
    public static final int FLIGHT_CATH_TIMEOUT = 86400;
    //缓存刷新触发时间(一小时)
    public static final int FLIGHT_CATH_REFRESH_TIME = 3600;
    //超时时间单位
    public static final TimeUnit REDIS_TIMEOUT_UNIT = TimeUnit.SECONDS;
    //token属性名
    public static final String FIELD_TOKEN = "token";
    //ostype属性名
    public static final String FIELD_OSTYPE = "osType";
    //账号属性名
    public static final String FIELD_ACCOUNT = "account";
    //设备标识
    public static final String FIELD_IDENTIFIE = "identifie";

}
