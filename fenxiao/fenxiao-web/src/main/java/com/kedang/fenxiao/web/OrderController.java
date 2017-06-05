package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.FXOrderRecordService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.DateUtils;

@Controller
@RequestMapping(value = "/order")
public class OrderController
{
	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private FXOrderRecordService fxOrderRecordService;
	@Autowired
	private AdminInfoService adminInfoService;

	/**
	 * 打开管理员订单页面
	 */
	@RequestMapping(value = "/toOrder")
	public String toOrder()
	{
		return "order/order";
	}

	/**
	 * 打开合作商订单页面
	 */
	@RequestMapping(value = "/enterpriseOrderView")
	public String toEnterpriseOrder()
	{
		return "order/enterpriseOrder";
	}

	/**
	 * 打开加油卡订单管理
	 */
	@RequestMapping(value = "/toGasCardOrder")
	public String toGasCardOrder()
	{
		return "order/gasCardOrder";
	}

	/**
	 * 管理员流量话费订单管理
	 * @param request
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getOrderList")
	@ResponseBody
	public Page<FXOrderRecord> getOrderList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows, String startTime,
			String endTime, String systemStartTime, String systemEndTime, String isNormal,String repeatRechargeStatus)
	{
		Page<FXOrderRecord> pageList = null;

		try
		{
			logger.info("====== start FXOrderRecordController.getOrderRecordList ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			if("0".equals(repeatRechargeStatus)){
				searchParams.put("EQ_repeatRechargeCount", repeatRechargeStatus);
			}else if("1".equals(repeatRechargeStatus)){
				searchParams.put("GTE_repeatRechargeCount", repeatRechargeStatus);
			}
			searchParams.put("GTE_clientSubmitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("GTE_systemSubmitTime", DateUtils.getFormatDate(systemStartTime, "yyyy-MM-dd"));
			if (StringUtils.isBlank(systemEndTime) == false)
			{
				Date end = DateUtils.getFormatDate(systemEndTime, "yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				searchParams.put("LT_systemSubmitTime", calendar.getTime());
			}
			String sortStr = "clientSubmitTime";
			if(StringUtils.isBlank(startTime) == true && StringUtils.isBlank(endTime) == true && StringUtils.isBlank(systemEndTime) == false){
				sortStr = "systemSubmitTime";
			}
			List<Integer> i = new ArrayList<Integer>();
			i.add(0);
			i.add(1);
			searchParams.put("IN_businessType", i);
			pageList = fxOrderRecordService.findAllOrderRecord(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.DESC, sortStr));
			logger.info("====== end FXOrderRecordController.getOrderRecordList ,res[_listFXOrderRecord=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOrderRecordController.getOrderList error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 管理员加油卡订单管理
	 * @param request
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getGasOrderList")
	@ResponseBody
	public Page<FXOrderRecord> getGasOrderList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows, String startTime,
			String endTime, String isNormal)
	{
		Page<FXOrderRecord> pageList = null;

		try
		{
			logger.info("====== start FXOrderRecordController.getGasOrderList ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_clientSubmitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("EQ_businessType", "3");
			pageList = fxOrderRecordService.findAllOrderRecord(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.DESC, "clientSubmitTime"));
			logger.info("====== end FXOrderRecordController.getGasOrderList ,res[_listFXOrderRecord=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOrderRecordController.getGasOrderList error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 合作商订单管理
	 * @param request
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/getOrderListByEnterprise")
	@ResponseBody
	public Page<FXOrderRecord> getOrderListByEnterprise(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows, String startTime,
			String endTime)
	{
		Page<FXOrderRecord> pageList = null;

		try
		{

			logger.info("====== start FXOrderRecordController.getOrderListByEnterprise ======");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("EQ_fxEnterprise.id", admin.getFxEnterprise().getId());
			searchParams.put("GTE_clientSubmitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			pageList = fxOrderRecordService.findAllOrderRecord(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.DESC, "clientSubmitTime"));
			logger.info("====== end FXOrderRecordController.getOrderListByEnterprise ,res[_listFXOrderRecord="
					+ pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOrderRecordController.getOrderListByEnterprise error[" + e.getCause() + "]");
		}
		return pageList;
	}

	@RequestMapping(value = "getRepeatList")
	@ResponseBody
	public List<Object[]> getRepeatList(String downstreamOrderNo, String mobile)
	{
		return fxOrderRecordService.getRepeatList(downstreamOrderNo, mobile);
	}

}
