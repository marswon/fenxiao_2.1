package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXChannel;
import com.kedang.fenxiao.service.FXChannelService;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "/channelManege")
public class ChannelManegeController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(ChannelManegeController.class);
	@Autowired
	private FXChannelService fxChannelService;

	@Autowired
	private ResourcesConfig resourcesConfig;

	@RequestMapping(value = "/toChannelManege")
	public String customerFundsTradingPage()
	{
		return "channel_manegement/channelList";
	}

	@ResponseBody
	@RequestMapping(value = "toChannelList", method = RequestMethod.GET)
	public Page<FXChannel> getChannelList(String yysId, String provinceId, String flowTypeByProvinceId,
			String flowSize, String flowName,String businessType, HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{

		Page<FXChannel> pageList = null;
		try
		{

			logger.info("====== start FXChannelController.getChannelList ======req[yysId:" + yysId + ",provinceId:"
					+ provinceId + ",flowTypeByProinceId:" + flowTypeByProvinceId + ",flowSize:" + flowSize
					+ ",flowName:" + flowName);
			// 拼接查询sql
			String sql = fxChannelService.appendSql(yysId, provinceId, flowTypeByProvinceId, flowSize, flowName,businessType);
			pageList = fxChannelService.searchFXChannel(sql, new PageRequest(page - 1, rows));
			logger.info("====== end FXChannelController.getChannelList ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXChannelController.getChannelList error[" + e.getMessage() + "]");
			e.printStackTrace();
		}
		return pageList;
	}

	@ResponseBody
	@RequestMapping(value = "saveChannel")
	public ResultDo saveProduct(FXChannel channel, String value)
	{
		try
		{
			logger.info("====== start FXChannelController.saveChannel ,req[value=" + value + "fxEnterprise:" + channel
					+ "]======");
			// 参数效验
			if (StringUtils.isBlank(channel.getName()))
			{
				return ResultFactory.getFailedResult("流量包名称不能为空");
			}
			if (StringUtils.isBlank(channel.getJavaClassPath()))
			{
				return ResultFactory.getFailedResult("类路径不能为空");
			}
			if (StringUtils.isBlank(channel.getAutoStart() + ""))
			{
				return ResultFactory.getFailedResult("是否自动启动不能为空");
			}
			if (StringUtils.isBlank(channel.getExtended()))
			{
				return ResultFactory.getFailedResult("通道信息不能为空");
			}
			if (StringUtils.isBlank(channel.getYysTypeId()))
			{
				return ResultFactory.getFailedResult("运营商不能为空");
			}
			if (StringUtils.isBlank(channel.getProvinceId()))
			{
				return ResultFactory.getFailedResult("省份不能为空");
			}
			if (StringUtils.isBlank(channel.getStatus() + ""))
			{
				return ResultFactory.getFailedResult("状态不能为空");
			}
			if (StringUtils.isBlank(channel.getNeedQuery() + ""))
			{
				return ResultFactory.getFailedResult("是否需要查询不能为空");
			}

			if (StringUtils.isBlank(channel.getBusinessType() + ""))
			{
				return ResultFactory.getFailedResult("业务类型不能为空");
			}
			if (StringUtils.isBlank(channel.getDiscount() + ""))
			{
				return ResultFactory.getFailedResult("通道折扣不能为空");
			}
			// 保存分销商
			FXChannel channelback = fxChannelService.save(channel);
			logger.info("====== end FXChannelController.save rep[" + channelback + "======");
			if (null == channelback)
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
			logger.error("channelContorller.save error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "getChannelById")
	public ResultDo findById(String id)
	{
		FXChannel channel = null;
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findchannelById ======");
			channel = fxChannelService.findFXChannelById(id);
			logger.info("====== end channelController.findchannelById ,res[fxchannel=" + channel + "] ======");
		}
		catch (Exception e)
		{
			logger.error("channelController.findchannelById error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(channel);
	}

	@ResponseBody
	@RequestMapping(value = "getAllChannel")
	public ResultDo getAllChannel()
	{

		try
		{
			logger.info("====== start OperatorProductController.getAllChannel ======");
			List<FXChannel> _list = fxChannelService.findAllChannel();
			logger.info("====== end OperatorProductController.getAllChannel ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("OperatorProductController.getAllChannel error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("根据省份列表获取该省所有流量包规格异常");
		}
	}

	/**
	 * 获取流量话费通道
	 */
	@ResponseBody
	@RequestMapping(value = "getAllChannelWithOutGas")
	public ResultDo getAllChannelWithOutGas()
	{

		try
		{
			logger.info("====== start OperatorProductController.getAllChannelWithOutGas ======");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			List<Integer> i = new ArrayList<Integer>();
			i.add(0);
			i.add(1);
			searchParams.put("IN_businessType", i);
			List<FXChannel> _list = fxChannelService.findAllChannelWithParam(searchParams);
			logger.info("====== end OperatorProductController.getAllChannelWithOutGas ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("OperatorProductController.getAllChannelWithOutGas error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取流量话费通道异常");
		}
	}
	
	/**
	 * 获取加油卡通道
	 */
	@ResponseBody
	@RequestMapping(value = "getAllGasChannel")
	public ResultDo getAllGasChannel()
	{

		try
		{
			logger.info("====== start OperatorProductController.getAllGasChannel ======");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("EQ_businessType", 3);
			List<FXChannel> _list = fxChannelService.findAllChannelWithParam(searchParams);
			logger.info("====== end OperatorProductController.getAllGasChannel ,res[list : size="
					+ (null != _list ? _list.size() : 0) + "] ======");
			return ResultFactory.getSuccessResult(_list);
		}
		catch (Exception e)
		{
			logger.error("OperatorProductController.getAllGasChannel error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取流量话费通道异常");
		}
	}
	/**
	 * 更新通道状态，0开启，1关闭
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateChannelStatus")
	public ResultDo updateChannelStatus(String id, String status)
	{
		if (StringUtils.isBlank(id) || StringUtils.isBlank(status))
		{
			return ResultFactory.getFailedResult("参数为空");
		}
		int account = fxChannelService.updateChannelStatus(id, status);
		if (account > 0)
		{
			return ResultFactory.getSuccessResult("更新成功");
		}
		else
		{
			return ResultFactory.getFailedResult("更新失败");
		}
	}

	/*
	 * 刷新通道
	 */
	@RequestMapping(value = "freshChannelById")
	@ResponseBody
	public ResultDo freshChannelById(HttpServletRequest request, String id)
	{

		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("参数不能为空");
		}
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");

		String refreshChannelUrl = resourcesConfig.getConfigString(Constant.REFRESH_CHANNEL_URL);
		System.out.println(refreshChannelUrl);
		PostMethod postMethod = new PostMethod(refreshChannelUrl);
		postMethod.addParameter("channelId", id); //渠道
		try
		{
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			if ("0000".equals(result))
			{
				return ResultFactory.getSuccessResult("刷新成功");
			}
			else
			{
				return ResultFactory.getFailedResult("刷新失败");
			}
		}
		catch (Exception e)
		{
			return ResultFactory.getFailedResult("刷新失败");
		}
	}
}
