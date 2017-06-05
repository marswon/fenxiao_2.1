package com.kedang.fenxiao.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXTaobaoGift;
import com.kedang.fenxiao.service.TaobaoActivityConfigService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年5月3日 下午1:58:30 
 */
@Controller
@RequestMapping(value = "taobaoActivityConfig")
public class TaobaoActivityConfigController
{

	private Logger logger = LogManager.getLogger(TaobaoActivityConfigController.class);
	@Autowired
	private TaobaoActivityConfigService service;

	@RequestMapping(value = "toTaobaoActivityConfig")
	public String toTaobaoConfig()
	{
		return "param_config/taobaoActivityConfig";
	}

	@RequestMapping("getAllPro")
	@ResponseBody
	public List<Object[]> getAllPro()
	{
		return service.getAllPro();
	}

	@RequestMapping("addActivity")
	@ResponseBody
	public ResultDo addActivity(String buyspuid, String giftspuid, String activityshop, String startTime, String endTime)
	{
		try
		{
			if (buyspuid.equals(giftspuid))
			{
				return new ResultDo(3, "购买宝贝与赠送宝贝不能相同", null);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			if (start.getTime() >= end.getTime())
			{
				return new ResultDo(4, "开始时间不能大于结束时间", null);
			}
			FXTaobaoGift gift = new FXTaobaoGift();
			gift.setActivityEndTime(end);
			gift.setActivityStartTime(start);
			gift.setCreateTime(new Date());
			gift.setGiftSpuid(giftspuid);
			gift.setSpuid(buyspuid);
			gift.setStatus(0);
			gift.setSupplierid(activityshop);
			FXTaobaoGift result = service.saveGift(gift);
			logger.info("新增的活动为：" + new Gson().toJson(result));
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			return new ResultDo(1, "操作成功", null);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "操作失败" + e, null);
		}
	}

	@RequestMapping("getActivityList")
	@ResponseBody
	public Page<FXTaobaoGift> getActivityList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXTaobaoGift> pageList = null;
		try
		{
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = service.getActivityList(searchParams, new PageRequest(page - 1, rows, Sort.Direction.DESC,
					"createTime"));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return pageList;
	}

	@RequestMapping("deleteTaobaoGift")
	@ResponseBody
	public ResultDo deleteTaobaoGift(String id)
	{
		try
		{
			logger.info("删除宝贝[" + id + "]");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			service.deleteTaobaoGift(Integer.valueOf(id));
			return new ResultDo(1, "操作成功", null);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "操作失败", null);
		}
	}
}
