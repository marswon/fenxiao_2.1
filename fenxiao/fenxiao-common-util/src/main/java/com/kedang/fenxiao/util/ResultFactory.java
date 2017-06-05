package com.kedang.fenxiao.util;

import com.kedang.fenxiao.util.po.ResultDo;

/**
 **************************************************************************************
 * Coryright (c) 2014-2024 杭州可当科技有限公司 项目名称: fenxiao-common-util
 * 
 * @Author: gegongxian
 * @Date: 2016年9月2日
 * @Copyright: 2016 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
 *             情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
 *************************************************************************************
 */
public class ResultFactory {

	public static ResultDo getFailedResult(String msg) {
		return new ResultDo(ResultDo.failed_code, msg, null);
	}

	public static ResultDo getFailedResult(Integer code, String msg) {
		return new ResultDo(code, msg, null);
	}

	public static ResultDo getSuccessResult() {
		return new ResultDo(ResultDo.success_code, "", null);
	}

	public static ResultDo getSuccessResult(String msg) {
		return new ResultDo(ResultDo.success_code, msg, null);
	}

	public static ResultDo getSuccessResult(Object object) {
		return new ResultDo(ResultDo.success_code, "", object);
	}

	public static ResultDo getSuccessResult(Object object, String msg) {
		return new ResultDo(ResultDo.success_code, msg, object);
	}
}
