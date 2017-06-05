package com.kedang.fenxiao.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.kedang.fenxiao.entity.FXTaobaoOrderRecord;
import com.kedang.fenxiao.service.TaobaoOrderService;
import com.kedang.fenxiao.util.DateUtils;

@Controller
@RequestMapping(value = "/taobaoOrder")
public class TaobaoOrderController
{
	private Logger logger = LoggerFactory.getLogger(TaobaoOrderController.class);

	@Autowired
	private TaobaoOrderService service;

	@RequestMapping(value = "toOrder")
	public String toOrder()
	{
		return "order/taobaoOrder";
	}

	@RequestMapping("getTaobaoOrderList")
	@ResponseBody
	public Page<FXTaobaoOrderRecord> getBaobeiList(HttpServletRequest request, String spuid, String name,
			String businessType, String flowType, String startTime, String endTime,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXTaobaoOrderRecord> pageList = null;
		try
		{
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_timeStart", DateUtils.getFormatDate(startTime, "yyyy-MM-dd"));
			if (StringUtils.isBlank(endTime) == false)
			{
				Date end = DateUtils.getFormatDate(endTime, "yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				searchParams.put("LT_timeStart", calendar.getTime());
			}
			pageList = service.getBaobeiList(searchParams, new PageRequest(page - 1, rows, Sort.Direction.DESC,
					"timeStart"));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return pageList;
	}

	@RequestMapping("getShopList")
	@ResponseBody
	public List<Object[]> getShopList()
	{
		try
		{
			return service.getShopList();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
