package com.kedang.fenxiao.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.kedang.fenxiao.entity.FXExceptionOrder;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.FXExceptionOrderService;
import com.kedang.fenxiao.util.DateUtils;

@Controller
@RequestMapping(value = "/ExceptionOrder")
public class ExceptionOrderController
{
	private Logger logger = LoggerFactory.getLogger(ExceptionOrderController.class);

	@Autowired
	private FXExceptionOrderService fxExceptionOrderService;
	@Autowired
	private AdminInfoService adminInfoService;

	/**
	 * 管理员订单管理
	 * @param request
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getOrderList")
	@ResponseBody
	public Page<FXExceptionOrder> getOrderList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows, String startTime,
			String endTime, String isNormal)
	{
		Page<FXExceptionOrder> pageList = null;
		try
		{
			logger.info("====== start FXOrderRecordController.getOrderRecordList ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_clientSubmitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			pageList = fxExceptionOrderService.findAllOrderRecord(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.DESC, "clientSubmitTime"));
			logger.info("====== end FXOrderRecordController.getOrderRecordList ,res[_listFXOrderRecord=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOrderRecordController.getOrderList error[" + e.getCause() + "]");
		}
		return pageList;
	}
}
