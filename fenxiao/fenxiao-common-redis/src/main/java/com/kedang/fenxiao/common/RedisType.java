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

/**
 * @descption redis种类
 * @author zhangqi@fuyidai.me
 * @version V1.0.0
 * @date 2015/12/29
 */
public enum RedisType {
    client,//客户
    shop,//商店
    employee,//后台登录人员
    withdraw_apply_map,
    warning_list,//预警名单
    black_list//黑名单
}
