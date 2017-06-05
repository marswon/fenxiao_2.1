package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXMobileArea;
import com.kedang.fenxiao.service.FXDiscountService;
import com.kedang.fenxiao.service.FXEnterpriseProductService;
import com.kedang.fenxiao.service.FXEnterpriseService;
import com.kedang.fenxiao.service.FXMobileAreaService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 *******************************************************************
 * Coryright (c) 2014-2024 杭州可当科技有限公司 项目名称: fenxiao-web
 * 
 * @Author: gegongxian
 * @Date: 2016年9月19日
 * @Copyright: 2016 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
 *             情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
 ******************************************************************
 */
@Controller
@RequestMapping(value = "/api/enterprise")
public class EnterpriseController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(EnterpriseController.class);

	@Autowired
	private FXEnterpriseService fxEnterpriseService;
	@Autowired
	private FXEnterpriseProductService fxEnterpriseProductService;
	@Autowired
	private FXDiscountService fxDiscountService;
	@Autowired
	private FXMobileAreaService fxMobileAreaService;

	/**
	 * 分销商管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "getEnterpriseListView")
	public String getEnterpriseListView()
	{
		return "enterprise/enterprise";
	}

	/**
	 * 加油卡合作商
	 */
	@RequestMapping(value = "getGasEnterpriseListView")
	public String getGasCardEnterpriseListView()
	{
		return "enterprise/gasCardEnterprise";
	}

	/**
	 * 分销商余额页面
	 *
	 * @return
	 */
	@RequestMapping(value = "getEnterpriseListBalanceView")
	public String getEnterpriseListBalanceView()
	{
		return "enterprise/enterpriseBalance";
	}

	/**
	 * 分页查询分销商列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getEnterpriseList")
	public Page<FXEnterprise> getEnterpriseList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXEnterprise> pageList = null;

		try
		{
			logger.info("====== start EnterpriseController.getEnterpriseList ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			List<Integer> i = new ArrayList<Integer>();
			i.add(0);
			i.add(1);
			searchParams.put("IN_businessType", i);
			System.out.println(searchParams);
//			if("".equals(searchParams.get("EQ_businessType"))){
//				
//			}
			pageList = fxEnterpriseService.findAllFxEnterprise(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.ASC, "id"));
			logger.info("====== end EnterpriseController.getEnterpriseList ,res[_listFxEnterprises=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.getRoleList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	
	/**
	 * 获取流量话费所属企业
	 */
	@ResponseBody
	@RequestMapping(value = "getAllEnterpriseWithOutGas")
	public ResultDo getAllEnterpriseWithOutGas()
	{

		try
		{
			logger.info("====== start EnterpriseController.getAllEnterpriseWithOutGas ======");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			List<Integer> i = new ArrayList<Integer>();
			i.add(0);
			i.add(1);
			searchParams.put("IN_businessType", i);
			List<FXEnterprise> _list = fxEnterpriseService.findAllEnterPriseWithParam(searchParams);
			logger.info("====== end EnterpriseController.getAllEnterpriseWithOutGas ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("OperatorProductController.getAllChannelWithOutGas error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取流量话费所属企业异常");
		}
	}
	
	/**
	 * 加油卡所属企业
	 */
	@ResponseBody
	@RequestMapping(value = "getAllGasEnterprise")
	public ResultDo getAllGasEnterprise()
	{
		try
		{
			logger.info("====== start EnterpriseController.getAllGasEnterprise ======");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("EQ_businessType", "3");
			List<FXEnterprise> _list = fxEnterpriseService.findAllEnterPriseWithParam(searchParams);
			logger.info("====== end EnterpriseController.getAllGasEnterprise ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("OperatorProductController.getAllGasEnterprise error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取流量话费所属企业异常");
		}
	}
	/**
	 * 加油卡列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGasCardEnterpriseList")
	public Page<FXEnterprise> getGasCardEnterpriseList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXEnterprise> pageList = null;

		try
		{
			logger.info("====== start EnterpriseController.getEnterpriseList ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("EQ_businessType", "3");
			pageList = fxEnterpriseService.findAllFxEnterprise(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.ASC, "id"));
			logger.info("====== end EnterpriseController.getEnterpriseList ,res[_listFxEnterprises=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.getRoleList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	/**
	 * 根据合作商主键查询分销商记录
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findEnterpriseById")
	public ResultDo findEnterpriseById(String id)
	{
		FXEnterprise fxEnterprise = null;
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("主键ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findEnterpriseById ======");
			fxEnterprise = fxEnterpriseService.findEnterpriseById(id);
			logger.info("====== end EnterpriseController.findEnterpriseById ,res[fxEnterprise=" + fxEnterprise
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.findEnterpriseById error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(fxEnterprise);
	}

	/**
	 * 保存分销商信息，分销商开通产品
	 * 
	 * @param fxEnterprise
	 * @param value
	 * @param dx
	 *            电信折扣
	 * @param yd
	 *            移动折扣
	 * @param lt
	 *            联通折扣（全国）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveEnterpriseAndProductToDiscount")
	public ResultDo saveEnterpriseAndProductToDiscount(FXEnterprise fxEnterprise, String value, Integer dx, Integer yd,
			Integer lt,String businessType)
	{

		try
		{
			logger.info("====== start EnterpriseController.saveEnterpriseAndProductToDiscount ,req[value=" + value
					+ "fxEnterprise:" + fxEnterprise + "]======");
			// 参数效验
			if (StringUtils.isBlank(fxEnterprise.getName()))
			{
				return ResultFactory.getFailedResult("合作商名称不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getLinkMan()))
			{
				return ResultFactory.getFailedResult("联系人不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getLinkPhone()))
			{
				return ResultFactory.getFailedResult("联系电话不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getAddress()))
			{
				return ResultFactory.getFailedResult("联系地址不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getEmail()))
			{
				return ResultFactory.getFailedResult("电子邮箱不能为空");
			}
			// 保存分销商
			FXEnterprise enterprise = fxEnterpriseService.saveEnterpriseAndProductToDiscount(fxEnterprise, value, dx,
					yd, lt, businessType);
			logger.info("====== end EnterpriseController.saveEnterpriseAndProductToDiscount rep[" + enterprise
					+ "======");
			if (null == enterprise)
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
			logger.error("EnterpriseController.saveEnterpriseAndProductToDiscount error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

	/**
	 * 查询分销商开通的分省
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findFXMobileAreaByEid")
	public ResultDo findFXMobileAreaByEid(String eId, Integer businessType)
	{

		List<FXMobileArea> fxMobileArea = null;
		if (StringUtils.isBlank(eId))
		{
			return ResultFactory.getFailedResult("主键ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findFXMobileAreaByEid ======");
			// 根据分销商ID查询该分销商配置的平台产品包
			fxMobileArea = fxMobileAreaService.findFXMobileAreaByEid(eId);

			// _list_fxDiscount = fxDiscountService.findEnterpriseDiscount(eId);
			logger.info("====== end EnterpriseController.findFXMobileAreaByEid ,res[fxMobileArea=" + fxMobileArea
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.findFXMobileAreaByEid error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(fxMobileArea);
	}

	/**
	 * 保存分销商信息，分销商开通产品
	 * 
	 * @param fxEnterprise
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveEnterprise")
	public ResultDo saveEnterprise(FXEnterprise fxEnterprise, String value)
	{

		try
		{
			logger.info("====== start EnterpriseController.saveEnterprise ,req[value=" + value + "fxEnterprise:"
					+ fxEnterprise + "]======");
			// 参数效验
			if (StringUtils.isBlank(fxEnterprise.getName()))
			{
				return ResultFactory.getFailedResult("合作商名称不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getLinkMan()))
			{
				return ResultFactory.getFailedResult("联系人不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getLinkPhone()))
			{
				return ResultFactory.getFailedResult("联系电话不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getAddress()))
			{
				return ResultFactory.getFailedResult("联系地址不能为空");
			}
			if (StringUtils.isBlank(fxEnterprise.getEmail()))
			{
				return ResultFactory.getFailedResult("电子邮箱不能为空");
			}
			if(null==fxEnterprise.getSelfProductType())
			{
				return ResultFactory.getSuccessResult("营销类型不能为空");
			}
			// 保存分销商
			FXEnterprise enterprise = fxEnterpriseService.saveEnterprise(fxEnterprise);
			logger.info("====== end EnterpriseController.saveEnterprise rep[" + enterprise + "======");
			if (null == enterprise)
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
			logger.error("EnterpriseController.saveEnterpriseAndProductToDiscount error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

	/**
	 * 分页查询分销商列表
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getEnterpriseListBalance")
	public Page<FXEnterprise> getEnterpriseListBalance(HttpServletRequest request,
												@RequestParam(value = "page", defaultValue = "1", required = false) int page,
												@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXEnterprise> pageList = null;
		try
		{
			logger.info("====== start EnterpriseController.getEnterpriseList ======");
			pageList = fxEnterpriseService.findAllFxEnterprise(new HashMap<String, Object>(), new PageRequest(page - 1, rows,
					Sort.Direction.ASC, "id"));
			logger.info("====== end EnterpriseController.getEnterpriseList ,res[_listFxEnterprises=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.getRoleList error[" + e.getCause() + "]");
		}
		return pageList;
	}

}
