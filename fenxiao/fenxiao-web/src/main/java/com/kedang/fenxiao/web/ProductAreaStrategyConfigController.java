package com.kedang.fenxiao.web;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXProductAreaStrategy;
import com.kedang.fenxiao.service.ProductAreaStrategyService;
import com.kedang.fenxiao.util.ProvinceAreaMapUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * Created by gegongxian on 17/3/30.
 */
@Controller
@RequestMapping(value = "strategyConfig")
public class ProductAreaStrategyConfigController
{

	private Logger logger = LoggerFactory.getLogger(ProductAreaStrategyConfigController.class);
	@Autowired
	private ProductAreaStrategyService productAreaStrategyService;

	/**
	 * 打开管理员订单页面
	 */
	@RequestMapping(value = "/openProductAreaStrategyConfigView")
	public String toOrder()
	{
		return "strategy_config/productAreaStrategyConfig";
	}

	/**
	 * 获得话费流量产品列表
	 *
	 * @param yysId
	 * @param provinceId
	 * @param flowSize
	 * @param flowName
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "productAreaStrategyConfig", method = RequestMethod.GET)
	public Page<FXProductAreaStrategy> getProductAreaStrategy(String yysId, String provinceId,
			String flowTypeByProvinceId, String flowSize, String flowName, String businessType, String productGroup_id,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<FXProductAreaStrategy> pageList = null;
		try
		{
			logger.info("====== start ProductAreaStrategyConfigController.getProductAreaStrategy ======req[yysId:"
					+ yysId + ",provinceId:" + provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId
					+ ",flowSize:" + flowSize + ",flowName:" + flowName + ",businessType:" + businessType);
			//Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			// 拼接查询sql
			pageList = productAreaStrategyService.findAllFXProductAreaStrategy(searchParams,
					new PageRequest(page - 1, rows, Sort.Direction.DESC, "createTime"));
			logger.info("====== end ProductAreaStrategyConfigController.getProductAreaStrategy ,res[_listFXProduct="
					+ pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("ProductAreaStrategyConfigController.getProductAreaStrategy error[" + e.getCause() + "]");
		}
		return pageList;
	}

	//根据省份code返回城市
	@ResponseBody
	@RequestMapping(value = "/areaToProvinceId")
	public ResultDo areaToProvinceId(String provinceId)
	{
		logger.info(
				"====== start ProductAreaStrategyConfigController.areaToProvinceId======rep[provinceId:" + provinceId);
		Map<String, String> map = ProvinceAreaMapUtils.ProvinceAreaMap(provinceId);
		logger.info("====== end ProductAreaStrategyConfigController.areaToProvinceId======rep[provinceId:" + provinceId
				+ ",map:" + map);
		return ResultFactory.getSuccessResult(map);
	}

	//保存策略
	@ResponseBody
	@RequestMapping(value = "/saveProductAreaStrategy")
	public ResultDo saveProductAreaStrategy(String oproId, String businessType, String yysTypeId, String provinceId,
			String size, String flowType, String areaCode)
	{
		logger.info("====== START ProductAreaStrategyConfigController.saveProductAreaStrategy======rep[oproId:" + oproId
				+ ",businessType:" + businessType + ",yysTypeId:" + yysTypeId + ",provinceId:" + provinceId + ",size:"
				+ size + ",flowType:" + flowType + ",areaCode" + areaCode);
		if (StringUtils.isBlank(oproId))
		{
			return ResultFactory.getFailedResult("运营商产品不能为空");
		}
		if (StringUtils.isBlank(businessType))
		{
			return ResultFactory.getFailedResult("业务类型不能为空");
		}
		if (StringUtils.isBlank(yysTypeId))
		{
			return ResultFactory.getFailedResult("运营商不能为空");
		}
		if (StringUtils.isBlank(provinceId))
		{
			return ResultFactory.getFailedResult("省份不能为空");
		}
		if (StringUtils.isBlank(size))
		{
			return ResultFactory.getFailedResult("面值不能为空");
		}
		if (StringUtils.isBlank(areaCode))
		{
			return ResultFactory.getFailedResult("城市不能为空");
		}
		if (StringUtils.isBlank(flowType))
		{
			return ResultFactory.getFailedResult("流量类型不能为空");
		}
		try
		{
			productAreaStrategyService.save(oproId, businessType, yysTypeId, provinceId, size, flowType, areaCode);
		}
		catch (ServiceException e)
		{
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("保存策略异常:" + e.getMessage());
		}
		logger.info("====== end ProductAreaStrategyConfigController.saveProductAreaStrategy======rep[oproId:" + oproId
				+ ",businessType:" + businessType + ",yysTypeId:" + yysTypeId + ",provinceId:" + provinceId + ",size:"
				+ size + ",flowType:" + flowType + ",areaCode" + areaCode);
		return ResultFactory.getSuccessResult("保存成功");
	}

	/**
	 * 平台产品view获取供应商产品，包含000,不区分流量包类型
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAllFxproductAreaStrategy")
	public Page<FXProductAreaStrategy> findAllFxproductAreaStrategy(HttpServletRequest request, String businessType,
			String yysTypeId, String provinceId, String areaCode, String size, String flowType,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXProductAreaStrategy> pageList = null;
		try
		{
			logger.info("====== start ProductAreaStrategyConfigController.findAllFxproductAreaStrategy ======");
			// 拼接查询sql
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("EQ_size", size);
			params.put("EQ_businessType", businessType);
			params.put("EQ_yysTypeId", yysTypeId);
			params.put("EQ_flowType", flowType);
			params.put("EQ_provinceId", provinceId);
			params.put("EQ_areaCode", areaCode);
			pageList = productAreaStrategyService.fxFXProductAreaStrategyPage(params,
					new PageRequest(page - 1, rows, Sort.Direction.DESC, "updateTime"));
			logger.info(
					"====== end ProductAreaStrategyConfigController.findAllFxproductAreaStrategy ,res[FXProductAreaStrategy="
							+ pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			logger.error(
					"ProductAreaStrategyConfigController.findAllFxproductAreaStrategy error[" + e.getCause() + "]");
		}
		return pageList;

	}

	/**
	 * 开启关闭策略
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateFXProductAreaStrategyStatus")
	public ResultDo updateFXProductAreaStrategyStatus(Long id, int status)
	{
		logger.info("====ProductAreaStrategyConfigController.updateFXProductAreaStrategyStatus====req[id:" + id
				+ ",status:" + status + "]=====");
		if (id <= 0)
		{
			return ResultFactory.getFailedResult("参数ID，status不能为空");
		}
		try
		{
			int result = productAreaStrategyService.updateFXProductAreaStrategyStatus(status, id);
			if (result == 1)
			{
				logger.info("====ProductAreaStrategyConfigController.updateFXProductAreaStrategyStatus====rsp[result:"
						+ result + "]=====");
				return ResultFactory.getSuccessResult("成功");
			}
			else
			{
				logger.error("====ProductAreaStrategyConfigController.updateFXProductAreaStrategyStatus====rsp[result:"
						+ result + "]=====");
				return ResultFactory.getFailedResult("更新失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return ResultFactory.getSuccessResult("成功");
	}

	/**
	 * 根据主键删除策略
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteFXProductAreaStrategy")
	public ResultDo deleteFXProductAreaStrategy(long id)
	{
		if (id <= 0)
		{
			return ResultFactory.getFailedResult("id不能为空");
		}
		try
		{
			logger.info("=====start ProductAreaStrategyConfigController.deleteFXProductAreaStrategy 删除配置:id:" + id);
			productAreaStrategyService.deleteFXProductAreaStrategy(id);
			logger.info("=====end ProductAreaStrategyConfigController.deleteFXProductAreaStrategy 删除配置:id:" + id);
		}
		catch (Exception e)
		{
			return ResultFactory.getFailedResult("删除失败");
		}
		return ResultFactory.getSuccessResult("删除成功");
	}
}
