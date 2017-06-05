package com.kedang.fenxiao.web;

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

import com.kedang.fenxiao.entity.FXProductGroup;
import com.kedang.fenxiao.service.FXProductGropService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "/productGroup")
public class FXProductGroupController
{
	private static final Logger logger = LoggerFactory.getLogger(FXProductGroupController.class);
	@Autowired
	private FXProductGropService fxProductGropService;

	/**
	 * 打开平台产品组视图
	 */

	@RequestMapping(value = "productGroupView")
	public String productGroupView()
	{
		return "productGroup/productGroup";
	}

	/**
	 * 查询所有产品组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFXProductGroup")
	public Page<FXProductGroup> getFXProductGroup(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXProductGroup> pageList = null;

		try
		{
			logger.info("====== start FXProductGroupController.getFXProductGroup ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = fxProductGropService.findAllFxProductGroup(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.ASC, "id"));
			logger.info("====== end FXProductGroupController.getFXProductGroup ,res[_listFxProductGroup=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXProductGroupController.getFXProductGroup error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 查询所有产品组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllFXProductGroup")
	public ResultDo getAllFXProductGroup(HttpServletRequest request)
	{
		List<FXProductGroup> pageList = null;
		String businessType = request.getParameter("businessType");
		try
		{
			logger.info("====== start FXProductGroupController.getAllFXProductGroup ======");
			pageList = fxProductGropService.findAllFxProductGroup(businessType);
			logger.info("====== end FXProductGroupController.getAllFXProductGroup ,res[_listFxProductGroup=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXProductGroupController.getAllFXProductGroup error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(pageList);
	}
	
	/**
	 * 查询所有加油卡产品组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGasFXProductGroup")
	public ResultDo getGasFXProductGroup(HttpServletRequest request)
	{
		List<FXProductGroup> pageList = null;
		String businessType = request.getParameter("businessType");
		try
		{
			logger.info("====== start FXProductGroupController.getAllFXProductGroup ======");
			pageList = fxProductGropService.findAllFxProductGroup(businessType);
			logger.info("====== end FXProductGroupController.getAllFXProductGroup ,res[_listFxProductGroup=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXProductGroupController.getAllFXProductGroup error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(pageList);
	}
	/**
	 * 查询所有流量话费产品组
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWithOutGasFXProductGroup")
	public ResultDo getWithoutGasFXProductGroup(HttpServletRequest request)
	{
		List<FXProductGroup> pageList = null;
		String businessType = "3";
		try
		{
			logger.info("====== start FXProductGroupController.getAllFXProductGroup ======");
			pageList = fxProductGropService.findWithoutGasFxProductGroup(businessType);
			logger.info("====== end FXProductGroupController.getAllFXProductGroup ,res[_listFxProductGroup=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXProductGroupController.getAllFXProductGroup error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(pageList);
	}
	
	@ResponseBody
	@RequestMapping(value = "getProductGroupById")
	public ResultDo findById(String id)
	{
		FXProductGroup productGroup = null;
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findEnterpriseById ======");
			productGroup = fxProductGropService.findFXFxProductGroupById(id);
			logger.info("====== end EnterpriseController.findEnterpriseById ,res[fxEnterprise=" + productGroup
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.findEnterpriseById error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(productGroup);
	}

	@ResponseBody
	@RequestMapping(value = "saveProductGroup")
	public ResultDo saveProduct(HttpServletRequest request, String value)
	{
		FXProductGroup productGroup = new FXProductGroup();
		try
		{
			String name = request.getParameter("productGroupName");
			String desc = request.getParameter("desc");
			String selfProductType = request.getParameter("selfProductType");
			String businessType = request.getParameter("businessType");
			if (request.getParameter("id") != "")
			{
				productGroup.setId(request.getParameter("id"));
			}
			productGroup.setName(name);
			productGroup.setDescription(desc);
			productGroup.setBusinessType(Integer.valueOf(businessType));
			productGroup.setSelfProductType(null!=selfProductType?Integer.valueOf(selfProductType):0);
			logger.info("====== start productGroupController.saveProductGroup ,req[value=" + value + "FXProductGroup:"
					+ productGroup + "]======");
			// 参数效验
			if (StringUtils.isBlank(productGroup.getName()))
			{
				return ResultFactory.getFailedResult("流量包名称不能为空");
			}
			if(StringUtils.isBlank(selfProductType)){
				return ResultFactory.getFailedResult("运营类型不能为空");
			}
			// 保存分销商
			FXProductGroup productGroupback = fxProductGropService.save(productGroup);
			logger.info("====== end productGroupController.save rep[" + productGroupback + "======");
			if (null == productGroupback)
			{
				return ResultFactory.getFailedResult("保存失败");

			}
			else
			{
				return ResultFactory.getSuccessResult("保存成功!");
			}
		}
		catch (Exception e)
		{
			logger.error("productGroupController.save error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

}
