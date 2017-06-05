package com.kedang.fenxiao.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.kedang.fenxiao.entity.FXWaitOrder;
import com.kedang.fenxiao.service.FXWaitOrderService;
import com.kedang.fenxiao.util.DateUtils;

/**
 *******************************************************************
* Coryright (c) 2014-2024 杭州可当科技有限公司
* 项目名称: fenxiao-web
* @Author: gegongxian
* @Date: 2016年10月4日
* @Copyright: 2016 
* 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
* 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
******************************************************************
 */
@Controller
@RequestMapping(value = "waitOrder")
public class FXWaitOrderController
{
	private static final Logger logger = LoggerFactory.getLogger(FXWaitOrderController.class);

	@Autowired
	private FXWaitOrderService fxWaitOrderService;

	/**
	 * 待处理订单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "openWaitOrderView")
	public String openWaitOrderView()
	{
		return "wait_order/waitOrder";
	}

	/**
	 * 查询待处理订单记录
	 * @param request
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findWaitOrder")
	public Page<FXWaitOrder> findWaitOrder(HttpServletRequest request, String startTime,
			String endTime, int page, int rows)
	{
		Page<FXWaitOrder> pageList = null;
		try
		{
			logger.info("====== start FXWaitOrderController.findWaitOrder, req[startTime="
					+ startTime + ",endTime=" + endTime + ",page=" + page + ",rows=" + rows + "] ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_clientSubmitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			pageList = fxWaitOrderService.findAll(searchParams, new PageRequest(page - 1, rows, Direction.DESC, "id"));
			logger.info("====== end FXWaitOrderController.findWaitOrder ======");
			return pageList;
		}
		catch (Exception e)
		{
			logger.error("FXWaitOrderController.findWaitOrder error[" + e.getCause() + "]");
			return pageList;
		}
	}
}
