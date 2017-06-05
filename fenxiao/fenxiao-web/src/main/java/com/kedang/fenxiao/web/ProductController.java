package com.kedang.fenxiao.web;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.FXMobileArea;
import com.kedang.fenxiao.entity.FXProduct;
import com.kedang.fenxiao.entity.v_fx_enterprise_product_discount;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.FXMobileAreaService;
import com.kedang.fenxiao.service.FXProductService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.enums.FxFlowType;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.FXYysPo;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "product")
public class ProductController
{

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private FXProductService fxProductService;
	@Autowired
	private FXMobileAreaService fxMobileAreaService;
	@Autowired
	private AdminInfoService adminInfoService;
	
	@RequestMapping(value = "productView")
	public String productView(Model model)
	{
		return "product/product";
	}
	/**
	 * 跳转至加油卡产品管理
	 */
	@RequestMapping(value = "gasCardProductView")
	public String gasCardProductView(Model model)
	{
		return "product/gasCardProduct";
	}
	
	/**
	 * 分页查询分销商列表
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
	@RequestMapping(value = "getProductList", method = RequestMethod.GET)
	public Page<FXProduct> getProductList(String yysId, String provinceId, String flowTypeByProvinceId,
			String flowSize, String flowName, String businessType, String productGroup_id,Integer selfProductType,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<FXProduct> pageList = null;
		try
		{
			logger.info("====== start ProductController.getProductList ======req[yysId:" + yysId + ",provinceId:"
					+ provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:" + flowSize
					+ ",flowName:" + flowName + ",businessType:" + businessType);
			// 拼接查询sql
			String sql = fxProductService.appendSql(yysId, provinceId, flowTypeByProvinceId, flowSize, flowName,
					businessType, productGroup_id,selfProductType);
			pageList = fxProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end ProductController.getProductList ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("ProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	
	/**
	 * 查询对应分销商产品
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 * @param businessType
	 * @param productGroup_id
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getEnterpriseProductList", method = RequestMethod.GET)
	public Page<v_fx_enterprise_product_discount> getEnterpriseProductList(String yysId, String provinceId, String flowTypeByProvinceId,
			String flowSize, String flowName, String businessType, String productGroup_id,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<v_fx_enterprise_product_discount> pageList = null;
		try
		{
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
			String eId = admin.getFxEnterprise().getId();
			logger.info("====== start ProductController.getEnterpriseProductList ======req[yysId:" + yysId + ",provinceId:"
					+ provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:" + flowSize
					+ ",flowName:" + flowName + ",businessType:" + businessType + ",eId:" + eId );
			// 拼接查询sql
			String sql = fxProductService.appendEnterpriseSql(yysId, provinceId, flowTypeByProvinceId, flowSize, flowName,
					businessType, productGroup_id, eId);
			pageList = fxProductService.searchFXEnterpriseProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end ProductController.getEnterpriseProductList ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("ProductController.getEnterpriseProductList error[" + e.getCause() + "]");
		}
		return pageList;
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
	@RequestMapping(value = "getProductListWithOutGas", method = RequestMethod.GET)
	public Page<FXProduct> getProductListWithOutGas(String yysId, String provinceId, String flowTypeByProvinceId,
			String flowSize, String flowName, String businessType, String productGroup_id,Integer selfProductType,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<FXProduct> pageList = null;
		try
		{
			logger.info("====== start ProductController.getProductList ======req[yysId:" + yysId + ",provinceId:"
					+ provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:" + flowSize
					+ ",flowName:" + flowName + ",businessType:" + businessType);
			// 拼接查询sql
			String sql = fxProductService.appendNoGasProductSql(yysId, provinceId, flowTypeByProvinceId, flowSize, flowName,
					businessType, productGroup_id,selfProductType);
			pageList = fxProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end ProductController.getProductList ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("ProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	/**
	 * 获取加油卡产品列表
	 */
	@ResponseBody
	@RequestMapping(value = "getGasProductList", method = RequestMethod.GET)
	public Page<FXProduct> getGasProductList(String yysId, String provinceId, String flowTypeByProvinceId,
			String flowSize, String flowName, String businessType, String productGroup_id,Integer selfProductType,
			HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<FXProduct> pageList = null;
		try
		{
			logger.info("====== start ProductController.getProductList ======req[yysId:" + yysId + ",provinceId:"
					+ provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:" + flowSize
					+ ",flowName:" + flowName + ",businessType:" + businessType+",selfProductType:"+selfProductType);
			// 拼接查询sql
			businessType = "3";
			String sql = fxProductService.appendSql(yysId, provinceId, flowTypeByProvinceId, flowSize, flowName,
					businessType, productGroup_id,selfProductType);
			pageList = fxProductService.searchFXProduct(sql, new PageRequest(page - 1, rows));
			logger.info("====== end ProductController.getProductList ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("ProductController.getProductList error[" + e.getCause() + "]");
		}
		return pageList;
	}
	/***
	 * 获取省份列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllProvince")
	public ResultDo getAllProvince()
	{
		try
		{
			logger.info("====== start ProductController.getAllProvince ======");
			List<FXMobileArea> list = fxMobileAreaService.getAllProvince();
			logger.info("====== end ProductController.getAllProvince ,res[list : size="
					+ (null != list ? list.size() : 0) + "] ======");
			FXMobileArea fxMobileArea = new FXMobileArea();
			fxMobileArea.setPrefix("000");
			fxMobileArea.setProvinceId("000");
			fxMobileArea.setProvinceName("全国");
			list.add(0, fxMobileArea);
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("ProductController.getAllProvince error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取省份列表异常");
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
			List<FXProduct> list = fxProductService.getProvinceToSize(proviceId, flowType);
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
			List<FXProduct> _list = fxProductService.getFlowTypeByPorinceId(proviceId);
			if (!CollectionUtils.isEmpty(_list))
			{
				for (FXProduct list : _list)
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
	 * 根据省份ID获取该省所有流量包规格
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllYys")
	public ResultDo getAllYys()
	{

		try
		{
			logger.info("====== start ProductController.getAllYys ======");
			List<FXYysPo> _list = FXProductService.getYysList();
			logger.info("====== end ProductController.getAllYys ,res[list : size=" + (null != _list ? _list.size() : 0)
					+ "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("ProductController.getAllYys error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("根据省份列表获取该省所有流量包规格异常");
		}
	}

	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getProductPage")
	public ResultDo getProductPage(String eId, Integer businessType)
	{
		try
		{
			logger.info("====== start ProductController.getProductPage ,req[eId=" + eId + ",businessType:"
					+ businessType + "]======");
			List<Map<String, Object>> list = fxProductService.queryPrivilegeTreeByBusinessType(eId, businessType);
			logger.info("====== end ProductController.getProductPage ,res[list=" + list + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("ProductController.getProductPage error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取权限异常");
		}
	}

	/**
	 * 保存平台产品、平台产品运营商产品关系表
	 * @param saveName	平台产品名称
	 * @param proPrice	平台产品价格
	 * @param open_proId	运营商产品ID
	 * @param maxProvince	省份code
	 * @param size	大小
	 * @param flowType	类型
	 * @param status	状态0可用，1不可用
	 * @param parentId	产品组ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveProductAndOpro")
	public ResultDo saveProductAndOpro(String paramValue, String status, String businessType_save, String maxProvince,
			String parentId, String saveName, String proPrice, String flowType, String yysType, String size,
			String open_proId, Integer save_selfProductType)
	{
		logger.info("====== start ProductController.saveProductAndOpro ,req[status:" + status + ",paramValue:"
				+ paramValue + ",businessType:" + businessType_save + ",maxProvince:" + maxProvince + ",parentId:"
				+ parentId + ",saveName:" + proPrice + ",flowType:" + flowType + ",yysType:" + yysType + ",size:"
				+ size + ",open_proId:" + open_proId + ",selfProductType:"+save_selfProductType+"]======");
		//解析参数
		if (StringUtils.isBlank(paramValue))
		{
			//	return ResultFactory.getFailedResult("参数不能为空");
		}
		String[] params = paramValue.split("#####");
		//循环读取参数
		if (StringUtils.isBlank(saveName))
		{
			return ResultFactory.getFailedResult("平台产品名称不能为空");
		}
		if (StringUtils.isBlank(parentId))
		{
			return ResultFactory.getFailedResult("平台产品组不能为空");
		}
		if (StringUtils.isBlank(businessType_save))
		{
			return ResultFactory.getFailedResult("平台产品所属业务类型不能为空");
		}
		if (StringUtils.isBlank(maxProvince))
		{
			return ResultFactory.getFailedResult("平台产品省份类型不能为空");
		}
		if (StringUtils.isBlank(flowType))
		{
			return ResultFactory.getFailedResult("平台产品流量类型不能为空");
		}
		if (StringUtils.isBlank(yysType))
		{
			return ResultFactory.getFailedResult("平台产品运营商类型不能为空");
		}
		if("3".equals(businessType_save)){
			if (StringUtils.isBlank(size))
			{
				return ResultFactory.getFailedResult("平台产品加油卡大小不能为空");
			}
		}else{
			if (StringUtils.isBlank(size))
			{
				return ResultFactory.getFailedResult("平台产品流量包大小不能为空");
			}
		}
		if(null==save_selfProductType){
			return ResultFactory.getFailedResult("自营、非自营为空");
		}
		try
		{
			//保存入库
			fxProductService.saveProductAndPop(params, businessType_save, maxProvince, status, parentId, saveName,
					proPrice, flowType, yysType, size, open_proId,save_selfProductType);
		}
		catch (ServiceException e)
		{
			logger.error("====== end ProductController.saveProductAndOpro=======error:" + e.getMessage());
			return ResultFactory.getFailedResult("保存平台产品异常,error:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("====== end ProductController.saveProductAndOpro=======error:" + e.getMessage());
			return ResultFactory.getFailedResult("保存平台产品异常,error:" + e.getMessage());
		}
		logger.info("====== end ProductController.saveProductAndOpro=======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 保存平台产品、平台产品运营商产品关系表
	 * @param saveName	平台产品名称
	 * @param proPrice	平台产品价格
	 * @param open_proId	运营商产品ID
	 * @param maxProvince	省份code
	 * @param size	大小
	 * @param status	状态0可用，1不可用
	 * @param parentId	产品组ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveHuaFeiProductAndOpro")
	public ResultDo saveHuaFeiProductAndOpro(String paramValue, String status, String businessType_save,
			String maxProvince, String parentId, String saveName, String proPrice, String yysType, String size,
			String open_proId,Integer save_selfProductType)
	{
		logger.info("====== start ProductController.saveProductAndOpro ,req[status:" + status + ",paramValue:"
				+ paramValue + ",businessType:" + businessType_save + ",maxProvince:" + maxProvince + ",parentId:"
				+ parentId + ",saveName:" + proPrice + ",yysType:" + yysType + ",size:" + size + ",open_proId:"
				+ open_proId + ",save_selfProductType:"+save_selfProductType+"]======");
		//解析参数
		if (StringUtils.isBlank(paramValue))
		{
			//	return ResultFactory.getFailedResult("参数不能为空");
		}
		String[] params = paramValue.split("#####");
		//循环读取参数
		if (StringUtils.isBlank(saveName))
		{
			return ResultFactory.getFailedResult("平台产品名称不能为空");
		}
		if (StringUtils.isBlank(parentId))
		{
			return ResultFactory.getFailedResult("平台产品组不能为空");
		}
		if (StringUtils.isBlank(businessType_save))
		{
			return ResultFactory.getFailedResult("平台产品所属业务类型不能为空");
		}
		if (StringUtils.isBlank(maxProvince))
		{
			return ResultFactory.getFailedResult("平台产品省份类型不能为空");
		}
		if (StringUtils.isBlank(yysType))
		{
			return ResultFactory.getFailedResult("平台产品运营商类型不能为空");
		}
		if (StringUtils.isBlank(size))
		{
			return ResultFactory.getFailedResult("平台产品面值大小不能为空");
		}
		if(null==save_selfProductType)
		{
			return ResultFactory.getFailedResult("自营、非自营为空");
		}
		try
		{
			//保存入库
			fxProductService.saveProductAndPop(params, businessType_save, maxProvince, status, parentId, saveName,
					proPrice, "-1", yysType, size, open_proId,save_selfProductType);
		}
		catch (ServiceException e)
		{
			logger.error("====== end ProductController.saveProductAndOpro=======error:" + e.getMessage());
			return ResultFactory.getFailedResult("保存平台产品异常,error:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("====== end ProductController.saveProductAndOpro=======error:" + e.getMessage());
			return ResultFactory.getFailedResult("保存平台产品异常,error:" + e.getMessage());
		}
		logger.info("====== end ProductController.saveProductAndOpro=======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 根据平台产品ID查询平台产品数据
	 * proId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findOneProduct")
	public ResultDo findOneProduct(String proId)
	{
		logger.info("====ProductController.findOneProduct====req[proId:" + proId + "]=====");
		if (StringUtils.isBlank(proId))
		{
			return ResultFactory.getFailedResult("分销商ID不能为空");
		}
		FXProduct fxProduct = fxProductService.findProductById(proId);
		if (null == fxProduct)
		{
			return ResultFactory.getFailedResult("未查询到平台产品信息");
		}
		logger.info("====ProductController.findOneProduct====res[fxProduct:" + fxProduct + "]=====");
		return ResultFactory.getSuccessResult(fxProduct);
	}

	/**
	 * 上架下架平台产品
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateProductStatus")
	public ResultDo updateProductStatus(String id, String status)
	{
		logger.info("====ProductController.updateProductStatus====req[id:" + id + ",status:" + status + "]=====");
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(status))
		{
			return ResultFactory.getFailedResult("参数ID，status不能为空");
		}
		int result = fxProductService.updateProductStatus(status, id);
		if (result == 1)
		{
			logger.info("====ProductController.updateProductStatus====rsp[result:" + result + "]=====");
			return ResultFactory.getSuccessResult("更新成功");
		}
		else
		{
			logger.error("====ProductController.updateProductStatus====rsp[result:" + result + "]=====");
			return ResultFactory.getFailedResult("更新失败");
		}
	}
}
