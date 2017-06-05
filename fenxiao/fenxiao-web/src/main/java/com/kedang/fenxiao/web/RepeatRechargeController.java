package com.kedang.fenxiao.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.kedang.fenxiao.entity.FXRepeatRecharge;
import com.kedang.fenxiao.service.RepeatRechargeService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.po.RepeatRechargePo;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月30日 下午1:59:45 
 */
@Controller
@RequestMapping(value = "repeatrecharge")
public class RepeatRechargeController
{
	private Logger logger = LoggerFactory.getLogger(RepeatRechargeController.class);

	@Autowired
	private RepeatRechargeService repeatRechargeService;

	@RequestMapping(value = "getRepeatRechargeList")
	@ResponseBody
	public List<Object[]> getRepeatRechargeList(String yysTypeId, String provinceId, String flowType)
	{
		return repeatRechargeService.getRepeatRechargeList(yysTypeId, provinceId, flowType);
	}
	
	@RequestMapping(value = "getRepeatRechargeListNew")
	@ResponseBody
	public List<RepeatRechargePo> getRepeatRechargeListNew(String yysTypeId, String provinceId, String flowType)
	{
		return repeatRechargeService.getRepeatRechargeListNew(yysTypeId, provinceId, flowType);
	}

	@RequestMapping(value = "getEnterpriseList")
	@ResponseBody
	public List<Object[]> getEnterpriseList(String id)
	{
		return repeatRechargeService.getEnterpriseList();
	}

	@RequestMapping(value = "updateSystemConfig")
	@ResponseBody
	public ResultDo updateSystemConfig(String systemValue)
	{
		try
		{
			logger.info("修改repeatRechargePassEid=" + systemValue);
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			repeatRechargeService.updateSystemConfig(systemValue, "repeatRechargePassEid");
			return new ResultDo(1, "操作成功", null);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "系统异常", null);
		}
	}

	@RequestMapping(value = "getPassEid")
	@ResponseBody
	public String getPassEid()
	{
		return repeatRechargeService.getPassEid("repeatRechargePassEid");
	}

	@RequestMapping("searchChannel")
	@ResponseBody
	public List<Object[]> searchChannel(String yysTypeId, String provinceId, String flowType)
	{

		try
		{
			return repeatRechargeService.searchChannel(yysTypeId, provinceId, Integer.valueOf(flowType));
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping("saveRepeatRecharge")
	@ResponseBody
	public ResultDo saveRepeatRecharge(String yysTypeId, String provinceId, String flowType, String channelIdStr)
	{
		try
		{
			logger.info("======保存复充配置开始======");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("操作人：" + JSON.toJSONString(shiroUser));
			String[] channelIdArray = channelIdStr.split(",");
			logger.info("以下复充配置将删除");
			logger.info(JSON.toJSONString(repeatRechargeService.selectRepeatRecharge(yysTypeId, provinceId,
					Integer.valueOf(flowType))));
			repeatRechargeService.deleteRepeatRecharge(yysTypeId, provinceId, Integer.valueOf(flowType));
			logger.info("新保存的复充配置如下：");
			if (channelIdArray != null && channelIdArray.length > 0)
			{
				String passEid = repeatRechargeService.getPassEid("repeatRechargePassEid");
				int length = channelIdArray.length;
				for (int index = 0; index < length; index++)
				{
					if (StringUtils.isBlank(channelIdArray[index]) == false)
					{
						String[] temp = channelIdArray[index].split("_");
						FXRepeatRecharge repeatRecharge = new FXRepeatRecharge();
						repeatRecharge.setCurChannelId(temp[0]);
						repeatRecharge.setCurFlowType(Integer.valueOf(temp[1]));
						repeatRecharge.setFlowType(Integer.valueOf(flowType));
						repeatRecharge.setIsHead(index == 0 ? 1 : 0);
						repeatRecharge.setPassEid(passEid);
						repeatRecharge.setPreChannelId(index > 0 ? channelIdArray[index - 1].split("_")[0] : "");
						repeatRecharge
								.setPreFlowType(index > 0 ? Integer.valueOf(channelIdArray[index - 1].split("_")[1])
										: 0);
						repeatRecharge.setProvinceId(provinceId);
						repeatRecharge.setSerialNum(index);
						repeatRecharge.setStatus(0);
						repeatRecharge.setTargetChannelId(index < length - 1 ? channelIdArray[index + 1].split("_")[0]
								: "");
						repeatRecharge.setTargetFlowType(index < length - 1 ? Integer.valueOf(channelIdArray[index + 1]
								.split("_")[1]) : 0);
						repeatRecharge.setYysTypeId(yysTypeId);
						repeatRechargeService.saveRepeate(repeatRecharge);
						logger.info(JSON.toJSONString(repeatRecharge));
					}
				}
			}
			logger.info("======保存复充配置结束======");
			return new ResultDo(1, "操作成功", null);
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new ResultDo(2, "系统异常", null);
		}

	}

	@RequestMapping("searchExistRepeatRehcarge")
	@ResponseBody
	public List<Object[]> searchExistRepeatRehcarge(String yysTypeId, String provinceId, String flowType)
	{
		return repeatRechargeService.searchExistRepeatRehcarge(yysTypeId, provinceId, Integer.valueOf(flowType));
	}
}
