package com.kedang.fenxiao.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.entity.FXTaobaoProduct;
import com.kedang.fenxiao.entity.FXTaobaoShop;
import com.kedang.fenxiao.service.TaobaoConfigService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月18日 下午1:25:15 
 */
@Controller
@RequestMapping(value = "taobaoConfig")
public class TaobaoConfigController
{
	private Logger logger = LogManager.getLogger(TaobaoConfigController.class);
	@Autowired
	private TaobaoConfigService configService;

	@SuppressWarnings("unchecked")
	@RequestMapping("addTaobaoShop")
	@ResponseBody
	public ResultDo addTaobaoShop(String supplierId, String name, String businessType, String remark)
	{
		try
		{
			FXSystemConfig config = configService.getSystemConfig("TAOBAO_bizType");
			Map<Integer, String> map = JSON.parseObject(config.getSystemValue(),
					new HashMap<Integer, String>().getClass());
			FXTaobaoShop shop = new FXTaobaoShop();
			shop.setBizType(map.get(Integer.valueOf(businessType)));
			shop.setBusinessType(Integer.valueOf(businessType));
			shop.setName(name);
			shop.setRemark(remark);
			shop.setShopid(supplierId);
			shop.setStatus(0);
			FXTaobaoShop result = configService.save(shop);
			logger.info("新增的店铺为：" + new Gson().toJson(result));
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			return new ResultDo(1, "操作成功", null);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "操作失败", null);
		}

	}

	@RequestMapping("addTaobaoProduct")
	@ResponseBody
	public ResultDo addTaobaoProduct(HttpServletRequest request)
	{
		try
		{
			String id = request.getParameter("id");
			String spuid = request.getParameter("spuid");
			String baobeiname = request.getParameter("baobeiname");
			String size = request.getParameter("size");
			String belongto = request.getParameter("belongto");
			String flowtype = request.getParameter("flowtype");
			String bizType = request.getParameter("bizType");
			FXTaobaoProduct product = new FXTaobaoProduct();
			if (StringUtils.isBlank(id) == false)
			{
				product.setId(Integer.valueOf(id));
			}
			product.setBizType(bizType);
			product.setCreateTime(new Date());
			product.setFlowType(Integer.valueOf(StringUtils.isBlank(flowtype) ? "0" : flowtype));
			product.setModifyTime(new Date());
			product.setName(baobeiname);
			product.setSize(Integer.valueOf(size));
			product.setSpuid(spuid);
			if (StringUtils.isBlank(id))
			{
				product.setStatus(0);
			}
			product.setSupplierid("");
			product.setBusinessType(Integer.valueOf(belongto));
			FXTaobaoProduct result = configService.save(product);
			if (StringUtils.isBlank(id) == false)
			{
				logger.info("修改宝贝为：" + new Gson().toJson(result));
			}
			else
			{
				logger.info("新增的宝贝为：" + new Gson().toJson(result));
			}
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			return new ResultDo(1, "操作成功", null);

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "操作失败", null);
		}
	}

	@RequestMapping("getShopList")
	@ResponseBody
	public List<Object[]> getShopList()
	{
		return configService.getShopList();
	}

	@RequestMapping("getShop")
	@ResponseBody
	public FXTaobaoShop getShop(String shopid)
	{
		return configService.getShop(shopid);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("getBusinessType")
	@ResponseBody
	public Map<Integer, String> getBusinessType()
	{
		String str = configService.getSystemConfig("businessType").getSystemValue();
		return JSON.parseObject(str, new HashMap<Integer, String>().getClass());
	}

	@RequestMapping("getBaobeiList")
	@ResponseBody
	public Page<FXTaobaoProduct> getBaobeiList(HttpServletRequest request, String spuid, String name,
			String businessType, String flowType,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXTaobaoProduct> pageList = null;
		try
		{
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LIKE_spuid", spuid);
			params.put("LIKE_name", name);
			params.put("EQ_businessType", businessType);
			params.put("EQ_flowType", flowType);
			pageList = configService.getBaobeiList(params, new PageRequest(page - 1, rows, Sort.Direction.ASC, "id"));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return pageList;
	}

	@RequestMapping("deleteTaobaoProduct")
	@ResponseBody
	public ResultDo deleteTaobaoProduct(String id)
	{
		try
		{
			logger.info("删除宝贝[" + id + "]");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			configService.deleteTaobaoProduct(Integer.valueOf(id));
			return new ResultDo(1, "操作成功", null);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "操作失败", null);
		}
	}

	@RequestMapping("toUpdatePro")
	@ResponseBody
	public FXTaobaoProduct toUpdatePro(int id)
	{
		return configService.toUpdatePro(id);
	}
}
