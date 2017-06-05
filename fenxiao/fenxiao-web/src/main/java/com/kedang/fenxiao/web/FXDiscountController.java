package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXDiscount;
import com.kedang.fenxiao.service.FXDiscountService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.enums.BusinessType;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * ******************************************************************
 * Coryright(c) 2014-2024 杭州可当科技有限公司 项目名称: fenxiao-web
 * 
 * @Author: gegongxian
 * @Date: 2016年9月21日
 * @Copyright: 2016 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
 *             情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
 ******************************************************************
 */
@Controller
@RequestMapping(value = "/api/discount")
public class FXDiscountController
{
	private Logger logger = LoggerFactory.getLogger(FXDiscountController.class);
	@Autowired
	private FXDiscountService fxDiscountService;

	/**
	 * 根据省份，分销商，漫游包类型查询流量包
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDiscountByEnterprise")
	public ResultDo findDiscountByEnterprise(String provinceCode, String yysType, String flowType, String eId,
			String businessType)
	{

		logger.info("====== start FXDiscountController.findDiscountByEnterprise req[provinceCode:" + provinceCode
				+ ",yysType:" + yysType + ",flowType:" + flowType + ",eId:" + eId + ",businessType:" + businessType);
		List<FXDiscount> _list = null;
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("分销商主键ID不能为空");
		}
		if (StringUtils.isNotBlank(businessType) && Integer.valueOf(businessType) != BusinessType.liuliang.getType())
		{
			flowType = "-1";
		}
		try
		{
			// 根据分销商ID查询该分销商配置的平台产品包
			_list = fxDiscountService.findEnterpriseDiscount(provinceCode, yysType, flowType, eId);
			logger.info("====== end FXDiscountController.findDiscountByEnterprise ,res[FXDiscount="
					+ (null != _list ? _list.size() : "0") + "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXDiscountController.findDiscountByEnterprise error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(_list);
	}

	/**
	 * 根据省份，分销商，漫游包类型查询流量包
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDiscountByEnterpriseToHuaFei")
	public ResultDo findDiscountByEnterpriseToHuaFei(String provinceCode, String yysType, String flowType, String eId)
	{

		logger.info("====== start FXDiscountController.findDiscountByEnterprise req[provinceCode:" + provinceCode
				+ ",yysType:" + flowType + ",eId" + eId);
		List<FXDiscount> _list = null;
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("分销商主键ID不能为空");
		}
		try
		{
			// 根据分销商ID查询该分销商配置的平台产品包
			_list = fxDiscountService.findEnterpriseDiscount(provinceCode, yysType, flowType, eId);
			logger.info("====== end FXDiscountController.findDiscountByEnterprise ,res[FXDiscount="
					+ (null != _list ? _list.size() : "0") + "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXDiscountController.findDiscountByEnterprise error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(_list);
	}

	/**
	 * 保存流量包折扣配置
	 * 
	 * @param flowValue
	 * @param discount
	 * @param eId
	 * @param provinceCode
	 * @param yysType
	 * @param flowType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveDiscount")
	public ResultDo saveDiscount(String flowValue, String discount, String eId, String provinceCode, String yysType,
			String flowType, String businessType)
	{

		logger.info("====== start FXDiscountController.findDiscountByEnterprise req[provinceCode:" + provinceCode
				+ ",yysType:" + yysType + ",eId:" + eId + ",yysType:" + yysType + ",flowValue:" + flowValue
				+ ",discount:" + discount);

		if (StringUtils.isBlank(flowValue))
		{
			return ResultFactory.getFailedResult("请选择流量包");

		}
		if (StringUtils.isBlank(discount))
		{
			return ResultFactory.getFailedResult("请选择折扣");

		}
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("分销商主键ID不能为空");

		}
		if (StringUtils.isBlank(provinceCode))
		{
			return ResultFactory.getFailedResult("请选择要配置的省份");

		}
		if (StringUtils.isNotBlank(businessType) && !businessType.equals(BusinessType.liuliang.getType() + ""))
		{
			flowType = "-1";
		}
		else
		{
			if (StringUtils.isBlank(flowType) && !businessType.equals(BusinessType.jiayouka.getType() + ""))
			{
				return ResultFactory.getFailedResult("请选择流量类型");
			}
		}
		if(!businessType.equals(BusinessType.jiayouka.getType() + "")){
			if (StringUtils.isBlank(yysType))
			{
				return ResultFactory.getFailedResult("请选择运营商类型");
			}
		}
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("分销商主键ID不能为空");
		}
		try
		{
			List<Integer> flowValues = new ArrayList<Integer>();
			String[] flowData = flowValue.split(",");
			for (String data : flowData)
			{
				flowValues.add(Integer.parseInt(data));
			}
			// 查询改配置是否在数据库中存在
			fxDiscountService.saveDiscountByEidAndProduct(eId, provinceCode, yysType, flowType, flowValues,
					Integer.valueOf(discount), businessType);
			// 根据分销商ID查询该分销商配置的平台产品包
			logger.info("====== end FXDiscountController.findDiscountByEnterprise======");
		}
		catch (Exception e)
		{
			logger.error("FXDiscountController.findDiscountByEnterprise error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 保存流量包折扣配置
	 * 
	 * @param flowValue
	 * @param discount
	 * @param eId
	 * @param provinceCode
	 * @param yysType
	 * @param flowType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "removeDiscount")
	public ResultDo removeDiscount(String id)
	{

		logger.info("====== start FXDiscountController.removeDiscount req[provinceCode:" + ",id:" + id);
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("请选择流量包");
		}
		id = id.replace(",", "");
		try
		{
			// 查询改配置是否在数据库中存在
			Integer result = fxDiscountService.removeDiscountById(Integer.valueOf(id));
			if (null != result && result > 0)
			{
				logger.info("====== end FXDiscountController.removeDiscount======");
				return ResultFactory.getSuccessResult();
			}
			else
			{
				logger.info("====== end FXDiscountController.removeDiscount======");
				return ResultFactory.getFailedResult("删除失败");
			}
			// 根据分销商ID查询该分销商配置的平台产品包
		}
		catch (Exception e)
		{
			logger.error("FXDiscountController.removeDiscount error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("删除失败：" + e.getCause());
		}
	}

	/**
	 * 根据分销商id查询全网折扣
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDiscountByeId")
	public ResultDo findDiscountByeId(String eId)
	{

		logger.info("====== start FXDiscountController.findDiscountByeId req[provinceCode:" + ",id:" + eId);
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("请选择流量包");
		}
		List<FXDiscount> fxDiscounts = null;
		try
		{
			// 查询改配置是否在数据库中存在
			fxDiscounts = fxDiscountService.findDiscountByeId(eId);
			logger.info("====== end FXDiscountController.findDiscountByeId======");
		}
		catch (Exception e)
		{
			logger.error("=====FXDiscountController.findDiscountByeId error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("根据分销商id查询失败：" + e.getCause());
		}
		return ResultFactory.getSuccessResult(fxDiscounts);
	}
}
