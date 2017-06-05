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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXChannel;
import com.kedang.fenxiao.entity.FXOperatorsProduct;
import com.kedang.fenxiao.service.FXChannelService;
import com.kedang.fenxiao.service.FXOperatorProductService;
import com.kedang.fenxiao.service.FXProductService;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.enums.FxFlowType;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "/operatorProduct")
public class FXOperatorProductController
{

	private Logger logger = LoggerFactory.getLogger(FXOperatorProductController.class);

	@Autowired
	private FXOperatorProductService fxOperatorProductService;

	@Autowired
	private FXChannelService fxChannelService;

	@Autowired
	private FXProductService fxProductService;
	/**
	 * 跳转至流量话费供应商页面
	 * @return
	 */
	@RequestMapping(value = "/operatorProduct")
	public String customerFundsTradingPage()
	{
		return "operatorProduct/operatorProduct";
	}
	/**
	 * 跳转至加油卡供应商页面
	 */
	@RequestMapping(value = "/gasCardOperatorProduct")
	public String togasCardOperatorProduct()
	{
		return "operatorProduct/gasCardOperatorProduct";
	}

	/**
	 * 平台产品view获取供应商产品，包含000
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProductList", method = RequestMethod.GET)
	public Page<FXOperatorsProduct> getProductList(String yysId_liuliang, String provinceId_liuliang,
			String flowTypeByProvinceId_liuliang, String flowSize, String flowName, HttpServletRequest request,
			String businessType, String yysId_huafei, String provinceId_huafei, String _opflowSize_huafei,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXOperatorsProduct> pageList = null;
		try
		{
			logger.info("====== start FXOperatorProductController.getProductList ======req[yysId_liuliang:"
					+ yysId_liuliang + ",provinceId:" + provinceId_liuliang + ",flowTypeByProvinceId_liuliang:"
					+ flowTypeByProvinceId_liuliang + ",flowSize:" + flowSize + ",flowName:" + flowName
					+ ",_opflowSize_huafei:" + _opflowSize_huafei + ",businessType:" + businessType);
			if (StringUtils.isNotBlank(businessType) && businessType.equals("1"))
			{
				//话费
				flowSize = _opflowSize_huafei;
				yysId_liuliang = yysId_huafei;
				provinceId_liuliang = provinceId_huafei;
			}
			// 拼接查询sql
			String sql = fxOperatorProductService.appendSql(yysId_liuliang, provinceId_liuliang,
					flowTypeByProvinceId_liuliang, flowSize, flowName, businessType);
			logger.info("====FXOperatorProductController.getProductList sql:" + sql);
			pageList = fxOperatorProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end FXOperatorProductController.getProductList ,res[_listFXProduct=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(),e);
			logger.error("FXOperatorProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 获取供应商产品
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOperatorProductList", method = RequestMethod.GET)
	public Page<FXOperatorsProduct> getOperatorProductList(String yysId, String provinceId,
			String flowTypeByProvinceId, String flowSize, String flowName, String businessType,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXOperatorsProduct> pageList = null;
		try
		{
			logger.info("====== start FXOperatorProductController.getProductList ======req[yysId:" + yysId
					+ ",provinceId:" + provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:"
					+ flowSize + ",flowName:" + flowName + ",businessType:" + businessType);
			// 拼接查询sql
			String sql = fxOperatorProductService.appendOperatorSql(yysId, provinceId, flowTypeByProvinceId, flowSize,
					flowName, businessType);
			pageList = fxOperatorProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end FXOperatorProductController.getProductList ,res[_listFXProduct=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOperatorProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 获取流量话费供应商产品
	 */
	@ResponseBody
	@RequestMapping(value = "getOperatorProductListWithOutGas", method = RequestMethod.GET)
	public Page<FXOperatorsProduct> getOperatorProductListWithOutGas(String yysId, String provinceId_search,
			String flowTypeByProvinceId, String flowSize, String flowName, String businessType,String channelId,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXOperatorsProduct> pageList = null;
		try
		{
			logger.info("====== start FXOperatorProductController.getProductList ======req[yysId:" + yysId
					+ ",provinceId:" + provinceId_search + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:"
					+ flowSize + ",flowName:" + flowName + ",businessType:" + businessType+ ",channelId:" + channelId);
			// 拼接查询sql
			String sql = fxOperatorProductService.appendNoGasOperatorSql(yysId, provinceId_search, flowTypeByProvinceId, flowSize,
					flowName, businessType,channelId);
			pageList = fxOperatorProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end FXOperatorProductController.getProductList ,res[_listFXProduct=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOperatorProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	/**
	 * 获取加油卡供应商产品
	 */
	@ResponseBody
	@RequestMapping(value = "getGasOperatorProductList", method = RequestMethod.GET)
	public Page<FXOperatorsProduct> getGasOperatorProductList(String yysTypeIdQ, String provinceIdQ,
			String flowTypeByProvinceId, String flowSize, String flowName, String businessType,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXOperatorsProduct> pageList = null;
		try
		{
			logger.info("====== start FXOperatorProductController.getProductList ======req[yysId:" + yysTypeIdQ
					+ ",provinceId:" + provinceIdQ + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:"
					+ flowSize + ",flowName:" + flowName + ",businessType:" + businessType);
			businessType = "3";
			// 拼接查询sql
			String sql = fxOperatorProductService.appendOperatorSql(yysTypeIdQ, provinceIdQ, flowTypeByProvinceId, flowSize,
					flowName, businessType);
			pageList = fxOperatorProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end FXOperatorProductController.getProductList ,res[_listFXProduct=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXOperatorProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	/**
	 * 保存分销商信息，分销商开通产品
	 *
	 * @param fxEnterprise
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveOperatorProduct")
	public ResultDo saveProduct(FXOperatorsProduct operatorProduct, HttpServletRequest request, String priceOp)
	{
		String channelId = request.getParameter("channelId");
		try
		{
			logger.info("====== start operatorProductController.saveProduct ,req[fxEnterprise:" + operatorProduct
					+ "]======");
			// 参数效验如果业务类型为加油卡，运营商默认为其他-1
			if("3".equals(operatorProduct.getBusinessType()+"")){
				operatorProduct.setYysTypeId("-1");
			}
			if (StringUtils.isBlank(operatorProduct.getName()))
			{
				return ResultFactory.getFailedResult("流量包名称不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getSize() + ""))
			{
				return ResultFactory.getFailedResult("流量包大小不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getFlowType() + ""))
			{
				return ResultFactory.getFailedResult("流量类型不能为空");
			}
			if (StringUtils.isBlank(priceOp + ""))
			{
				return ResultFactory.getFailedResult("价格不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getYysTypeId()))
			{
				return ResultFactory.getFailedResult("运营商不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getProvinceId()))
			{
				return ResultFactory.getFailedResult("省份不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getStatus() + ""))
			{
				return ResultFactory.getFailedResult("状态不能为空");
			}
			if (StringUtils.isBlank(channelId))
			{
				return ResultFactory.getFailedResult("通道不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getOrderCode()))
			{
				return ResultFactory.getFailedResult("订购编码不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getBusinessType() + ""))
			{
				return ResultFactory.getFailedResult("业务类型不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getChannelDiscount() + ""))
			{
				return ResultFactory.getFailedResult("通道折扣不能为空");
			}
			if (0 != operatorProduct.getBusinessType())
			{
				operatorProduct.setFlowType(Constant.OTHRER_FLOWTYPE);
			}
			operatorProduct.setRemarks(operatorProduct.getRemarks());
			FXChannel channel = fxChannelService.findFXChannelById(channelId);
			operatorProduct.setChannel(channel);
			operatorProduct.setPrice((long) (Float.valueOf(priceOp) * 1000));
			// 保存分销商
			FXOperatorsProduct operatorProductback = fxOperatorProductService.save(operatorProduct);
			logger.info("====== end fxOperatorProductController.save rep[" + operatorProductback + "======");
			if (null == operatorProductback)
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
			logger.error("operatorProductController.save error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

	/**
	 * 保存加油卡分销商信息，分销商开通产品
	 *
	 * @param fxEnterprise
	 * @param value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveGasOperatorProduct")
	public ResultDo saveGasProduct(FXOperatorsProduct operatorProduct, HttpServletRequest request, String priceOp)
	{
		String channelId = request.getParameter("channelId");
		try
		{
			logger.info("====== start operatorProductController.saveProduct ,req[fxEnterprise:" + operatorProduct
					+ "]======");
			if (StringUtils.isBlank(operatorProduct.getName()))
			{
				return ResultFactory.getFailedResult("加油卡名称不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getSize() + ""))
			{
				return ResultFactory.getFailedResult("加油卡大小不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getFlowType() + ""))
			{
				return ResultFactory.getFailedResult("类型不能为空");
			}
			if (StringUtils.isBlank(priceOp + ""))
			{
				return ResultFactory.getFailedResult("价格不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getYysTypeId()))
			{
				return ResultFactory.getFailedResult("运营商不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getProvinceId()))
			{
				return ResultFactory.getFailedResult("省份不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getStatus() + ""))
			{
				return ResultFactory.getFailedResult("状态不能为空");
			}
			if (StringUtils.isBlank(channelId))
			{
				return ResultFactory.getFailedResult("通道不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getOrderCode()))
			{
				return ResultFactory.getFailedResult("订购编码不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getBusinessType() + ""))
			{
				return ResultFactory.getFailedResult("业务类型不能为空");
			}
			if (StringUtils.isBlank(operatorProduct.getChannelDiscount() + ""))
			{
				return ResultFactory.getFailedResult("通道折扣不能为空");
			}
			if (0 != operatorProduct.getBusinessType())
			{
				operatorProduct.setFlowType(Constant.OTHRER_FLOWTYPE);
			}
			operatorProduct.setRemarks(operatorProduct.getRemarks());
			FXChannel channel = fxChannelService.findFXChannelById(channelId);
			operatorProduct.setChannel(channel);
			operatorProduct.setPrice((long) (Float.valueOf(priceOp) * 1000));
			// 保存分销商
			FXOperatorsProduct operatorProductback = fxOperatorProductService.save(operatorProduct);
			logger.info("====== end fxOperatorProductController.save rep[" + operatorProductback + "======");
			if (null == operatorProductback)
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
			logger.error("operatorProductController.save error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}
	@ResponseBody
	@RequestMapping(value = "getProductById")
	public ResultDo findById(String id)
	{
		FXOperatorsProduct operatorProduct = null;
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findEnterpriseById ======");
			operatorProduct = fxOperatorProductService.findFXOperatorsProductById(id);
			logger.info("====== end EnterpriseController.findEnterpriseById ,res[fxEnterprise=" + operatorProduct
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.findEnterpriseById error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(operatorProduct);
	}

	/***
	 * 根据省份ID获取该省份的所有流量包类型(0,1)
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFlowTypeByPorinceId")
	public ResultDo getFlowTypeByPorinceId(String proviceId)
	{

		try
		{
			logger.info("====== start ProductController.getFlowTypeByPorinceId ======");
			List<FXOperatorsProduct> _list = fxOperatorProductService.getFlowTypeByPorinceId(proviceId);
			if (!CollectionUtils.isEmpty(_list))
			{
				for (FXOperatorsProduct list : _list)
				{
					if (list.getFlowType() == 0)
					{
						list.setProductFlowName(FxFlowType.roam.getDes());
					}
					else
					{
						list.setProductFlowName(FxFlowType.local.getDes());
					}
				}
			}
			logger.info("====== end ProductController.getFlowTypeByPorinceId ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("ProductController.getFlowTypeByPorinceId error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("根据省份列表获取该省所有流量包规格异常");
		}
	}

	/***
	 * 根据省份ID和流量包类型 获取该省所有流量包规格
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProvinceToSize")
	public ResultDo getProvinceToSize(String proviceId, Integer flowType)
	{
		try
		{
			logger.info("====== start ProductController.getProvinceToSize ======");
			List<FXOperatorsProduct> list = fxOperatorProductService.getProvinceToSize(proviceId, flowType);
			logger.info("====== end ProductController.getProviceToSize ,res[list : size="
					+ (null != list ? list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("ProductController.getProviceToSize error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("根据省份列表获取该省所有流量包规格异常");
		}
	}

	/**
	 * 平台产品view获取供应商产品，包含000,不区分流量包类型
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProductListNoHasFlowType", method = RequestMethod.GET)
	public Page<FXOperatorsProduct> getProductListNoHasFlowType(HttpServletRequest request, String businessType,
			String yysTypeId, String provinceId, String size,String flowType,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXOperatorsProduct> pageList = null;
		try
		{
			logger.info("====== start FXOperatorProductController.getProductListNoHasFlowType ======");
			// 拼接查询sql
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("EQ_size", size);
			params.put("EQ_businessType", businessType);
			params.put("EQ_yysTypeId", yysTypeId);
			//漫游职能匹配漫游
			if(StringUtils.isNotBlank(flowType)&&"0".equals(flowType)){
				params.put("EQ_flowType",flowType);
			}
			List<String> province = new ArrayList<>();
			province.add(provinceId);
			province.add("000");
			params.put("IN_provinceId", province);
			pageList = fxOperatorProductService.fxOperatorsProductPage(params,
					new PageRequest(page - 1, rows, Sort.Direction.ASC, "id"));
			logger.info("====== end FXOperatorProductController.getProductListNoHasFlowType ,res[_listFXProduct="
					+ pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			logger.error("FXOperatorProductController.getProductListNoHasFlowType error[" + e.getCause() + "]");
		}
		return pageList;
	}

}
