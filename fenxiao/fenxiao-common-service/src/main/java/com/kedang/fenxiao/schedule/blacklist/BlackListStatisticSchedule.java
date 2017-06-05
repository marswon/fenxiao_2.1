package com.kedang.fenxiao.schedule.blacklist;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.kedang.fenxiao.repository.BlackListStatisticDao;
import com.kedang.fenxiao.util.ResourcesConfig;

@Component
@Lazy(false)
@Transactional
public class BlackListStatisticSchedule
{
	private Logger logger = LogManager.getLogger(BlackListStatisticSchedule.class);

	@Autowired
	private BlackListStatisticDao blackListStatisticDao;

	@Autowired
	private ResourcesConfig resourcesConfig;

	/**
	  * 方法描述：
	  * @author: zhuwanlin
	  * @date: 2017年3月13日 下午6:52:44
	  */
	//@Scheduled(cron = "0/" + Constants.BLACKLIST＿PERIOD + " * * * * ?")
	@Scheduled(cron = "${schedule.add.black.list}")
	public void statistic()
	{
		try
		{
			long time = System.currentTimeMillis();
			logger.info("=====手机号失败次数过多拉黑定时任务开始执行，已执行(" + (Constants.blacklist_statisticScheduleCount++) + ")次=====");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -Integer.valueOf(resourcesConfig
					.getConfigString(Constants.BLACK_LIST_STATISTIC_TIME) == null ? "3" : resourcesConfig
					.getConfigString(Constants.BLACK_LIST_STATISTIC_TIME)));
			//			logger.info("统计时间段：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()) + "--至今");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("统计时间段：[" + dateFormat.format(calendar.getTime()) + "," + dateFormat.format(new Date()) + ")");
			List<Object[]> list = blackListStatisticDao.statistic(calendar.getTime(),
					Constants.BLACK_LIST_DOWNSTREAM_ORDERSTATUS, Integer.valueOf(resourcesConfig
							.getConfigString(Constants.BLACK_LIST_FAIL_COUNT) == null ? "1" : resourcesConfig
							.getConfigString(Constants.BLACK_LIST_FAIL_COUNT)));
			if (list != null && list.size() > 0)
			{
				logger.info("获取到的统计数据为" + JSON.toJSONString(list));
				String blackList = blackListStatisticDao.getBlackList().getConfig();
				logger.info("数据库中已存在的黑名单为：" + blackList);
				Set<String> newBlackListSet = new HashSet<String>();
				for (Object[] objectArray : list)
				{
					String mobile = objectArray[0] == null ? "" : objectArray[0].toString();
					int failCount = Integer.valueOf(objectArray[1] == null ? "0" : objectArray[1].toString());
					logger.info(mobile + "失败订单数已达到" + failCount + "次，将拉黑处理");
					if (blackList != null && blackList.indexOf(mobile) >= 0)
					{
						logger.info(mobile + "已在黑名单中");
					}
					else
					{
						logger.info(mobile + "未在黑名单中");
						newBlackListSet.add(mobile);
					}
				}
				if (newBlackListSet.size() > 0)
				{
					pushToBlackList(newBlackListSet, blackList);
					//					refreshIFMem();
				}

			}
			else
			{
				logger.info("此次统计未找到符合条件的手机号码");
			}
			logger.info("=====手机号失败次数过多拉黑定时任务执行完毕，耗时" + (System.currentTimeMillis() - time) + "ms=====");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	  * 方法描述：将手机号添加到黑名单
	  * @param mobileSet
	  * @param blackList
	  * @author: zhuwanlin
	  * @date: 2017年3月13日 下午6:52:14
	  */
	public void pushToBlackList(Set<String> mobileSet, String blackList)
	{
		try
		{
			logger.info("以下手机号将添加到黑名单");
			logger.info(JSON.toJSONString(mobileSet));
			StringBuffer newBlackListStringBuffer = new StringBuffer();
			for (String str : mobileSet)
			{
				newBlackListStringBuffer.append(str + ",");
			}
			if (blackList != null)
			{
				if (blackList.endsWith(","))
				{
					blackListStatisticDao.updateBlackList(blackList + newBlackListStringBuffer.toString());
				}
				else
				{
					blackListStatisticDao.updateBlackList(blackList + "," + newBlackListStringBuffer.toString());
				}
			}
			else
			{
				blackListStatisticDao.updateBlackList(newBlackListStringBuffer.toString());
			}
			//开启新线程刷新接口黑名单缓存
			new Thread(new RefreshIFMemThread(resourcesConfig)).start();
			logger.info("黑名单添加完成");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	/**
	  * 方法描述：刷新接口黑名单缓存
	  * @author: zhuwanlin
	  * @date: 2017年3月13日 下午6:51:48
	  */
	public void refreshIFMem()
	{
		try
		{
			logger.info("==开始刷新接口黑名单缓存==");
			String configURL = resourcesConfig.getConfigString(Constants.BLACK_LIST_REFRESH_URL);

			if (configURL != null)
			{

				String[] urls = configURL.split(";");
				for (String url : urls)
				{
					if (url != null && url.length() > 0)
					{
						logger.info("url:" + url);
						HttpClient client = new HttpClient();
						client.getParams().setContentCharset("UTF-8");
						PostMethod postMethod = new PostMethod(url);
						client.executeMethod(postMethod);
						String _resp = postMethod.getResponseBodyAsString();
						postMethod.releaseConnection();
						logger.info("返回结果为：" + _resp);
					}
				}
			}
			logger.info("==刷新接口黑名单缓存结束==");
		}
		catch (HttpException e)
		{
			logger.error(e.getMessage(), e);
		}
		catch (IOException e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
