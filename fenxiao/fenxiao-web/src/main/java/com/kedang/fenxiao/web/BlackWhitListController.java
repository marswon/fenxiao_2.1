package com.kedang.fenxiao.web;

import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXConfig;
import com.kedang.fenxiao.service.BlackWhiteListService;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * 
 * @author wn
 *
 *
 */

@Controller
@RequestMapping(value = "blackwhitelist")
public class BlackWhitListController extends BaseController
{

	private Logger logger = LoggerFactory.getLogger(BlackWhitListController.class);

	@Autowired
	private BlackWhiteListService blackwhitelistService;

	@Autowired
	private ResourcesConfig resourcesConfig;

	@RequestMapping(value = "openblackWhiteList")
	public String openblackWhiteList()
	{
		return "blackwhitelist/blackWhiteList";
	}

	/**
	 * 查询黑名单
	 */
	@ResponseBody
	@RequestMapping(value = "queryBlackList")
	public List<FXConfig> queryBlackList()
	{
		List<FXConfig> fxconfig = blackwhitelistService.queryblacklist();
		return fxconfig;
	}

	/**
	 * 查询白名单
	 */
	@ResponseBody
	@RequestMapping(value = "queryWhiteList")
	public List<FXConfig> queryWhiteList()
	{
		List<FXConfig> phonelist = blackwhitelistService.querywhitelist();
		return phonelist;
	}

	/**
	 * 保存黑名单
	 * @param fxconfig
	 * @return
	 * 
	 */
	@RequestMapping(value = "saveBlackList")
	@ResponseBody
	public ResultDo saveBlackList(String black)
	{
		try
		{
			if (black.contains("，"))
			{
				return ResultFactory.getFailedResult("请去除中文逗号（，）");
			}

			FXConfig fxconfig = blackwhitelistService.updateBlackFxconfig(black);
			if (fxconfig != null)
			{
				logger.info("====== end BlackWhiteListController.saveBlackList ======");
				sendPost(Constant.REFRESH_BLACK_WHITE_LIST_A);
				sendPost(Constant.REFRESH_BLACK_WHITE_LIST_B);
				return ResultFactory.getSuccessResult("保存成功");
			}

		}
		catch (Exception e)
		{
			logger.info("====== err BlackWhiteListController.saveBlackList ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end BlackWhiteListController.saveBlackList ======");
		return ResultFactory.getFailedResult("保存失败");
	}

	/**
	 * 保存白名单
	 * @param fxconfig
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "saveWhiteList")
	public ResultDo savaWhiteList(String white)
	{
		try
		{
			if (StringUtils.isEmpty(white))
			{
				return ResultFactory.getFailedResult("请输入手机号码");
			}
			if (white.contains("，"))
			{
				return ResultFactory.getFailedResult("请去除中文逗号（，）");
			}
			//			if (white != null && white.trim().length() != 11)
			//			{
			//				return ResultFactory.getFailedResult("请输入正确手机号码");
			//			}
			//			if (StringUtils.isEmpty(white)) {
			//				return ResultFactory.getFailedResult("请输入手机号码");
			//			}
			FXConfig fxconfig = blackwhitelistService.updateWhiteFxconfig(white);
			if (fxconfig != null)
			{
				logger.info("====== end BlackWhiteListController.saveWhitekList ======");
				sendPost(Constant.REFRESH_BLACK_WHITE_LIST_A);
				sendPost(Constant.REFRESH_BLACK_WHITE_LIST_B);
				return ResultFactory.getSuccessResult("保存成功");
			}
		}
		catch (Exception e)
		{
			logger.info("====== err BlackWhiteListController.saveWhitekList ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end BlackWhiteListController.saveWhitekList ======");
		return ResultFactory.getFailedResult("保存失败");
	}

	private void sendPost(String url)
	{
		String refreshBlackWhiteUrl = resourcesConfig.getConfigString(url);
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(refreshBlackWhiteUrl);
		try
		{
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			if ("OK".equals(result))
			{
				logger.info("刷新黑白名单成功");
			}
			else
			{
				logger.info("刷新黑白名单异常");
			}
		}
		catch (Exception e)
		{
			logger.error("刷新黑白名单异常");
		}
	}
}
