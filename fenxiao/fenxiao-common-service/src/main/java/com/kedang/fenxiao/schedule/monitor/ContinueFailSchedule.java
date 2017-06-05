package com.kedang.fenxiao.schedule.monitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXContinueFail;
import com.kedang.fenxiao.entity.FXMessage;
import com.kedang.fenxiao.entity.FXMessageFrequency;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.repository.ContinueFailDao;
import com.kedang.fenxiao.repository.FXMessageDao;
import com.kedang.fenxiao.repository.FXMessageFrequencyDao;
import com.kedang.fenxiao.repository.FXSystemConfigDao;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.schedule.monitor.util.CommonUtil;
import com.kedang.fenxiao.schedule.monitor.util.MessageUtil;
import com.kedang.fenxiao.schedule.monitor.util.WeiChatUtil;

@Component
@Lazy(false)
@Transactional
public class ContinueFailSchedule
{
	private Logger logger = LogManager.getLogger(ContinueFailSchedule.class);
	private String prefix = "CONTINUEFAIL_";
	private String title = "[连续失败]";
	private String creator = "CONTINUEFAIL_SCHEDULE";
	@Autowired
	private ContinueFailDao continueFailDao;
	@Autowired
	private FXMessageDao fxMessageDao;
	@Autowired
	private FXMessageFrequencyDao fxMessageFrequencyDao;
	@Autowired
	private MonitorDao monitorDao;
	@Autowired
	private FXSystemConfigDao fxSystemConfigDao;

	private int scheduleCount = 0;

	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "15/" + Constants.MONITOR_PERIOD + " * * * * ?")
	@Scheduled(cron = "${schedule.failure.count}")
	private void schedule()
	{
		try
		{
			long time = System.currentTimeMillis();
			logger.info("=====连续失败统计开始，已执行(" + (scheduleCount++) + ")次=====");
			String startTimeStr = monitorDao.getSystemValue("statistic_start_time");
			String endTimeStr = monitorDao.getSystemValue("statistic_end_time");
			logger.info("statistic_start_time=" + startTimeStr + ",statistic_end_time=" + endTimeStr);
			if (StringUtils.isBlank(startTimeStr) == false && StringUtils.isBlank(endTimeStr) == false)
			{
				try
				{
					if (CommonUtil.isMessageTime(startTimeStr, endTimeStr) == false)
					{
						logger.info("非消息时间段，不进行定时任务操作");
						return;
					}
				}
				catch (ParseException e)
				{
					logger.error(e.getMessage(), e);
				}
			}
			//初始化配置参数
			List<FXSystemConfig> sysConfigList = monitorDao.getFXSystemConfig();
			if (sysConfigList != null && sysConfigList.size() > 0)
			{
				Map<String, String> sysconfigMap = new HashMap<String, String>();
				for (FXSystemConfig config : sysConfigList)
				{
					sysconfigMap.put(config.getSystemKey(), config.getSystemValue());
				}
				Constants.continuefail_warning_count = Integer
						.valueOf(sysconfigMap.get("continuefail_warning_count") == null ? "20" : sysconfigMap
								.get("continuefail_warning_count"));
				Constants.time_difference = Integer.valueOf(sysconfigMap.get("time_difference") == null ? "3600000"
						: sysconfigMap.get("time_difference"));
				logger.info("Constants.continuefail_warning_count=" + Constants.continuefail_warning_count);
				logger.info("Constants.time_difference=" + Constants.time_difference);
			}
			FXSystemConfig systemconfig = monitorDao.getContinueFailLastStatisticTime("ContinueFailLastStatisticTime");
			Date startTime = null;
			String lastTime = null;
			if (systemconfig != null)
			{
				lastTime = systemconfig.getSystemValue();
			}
			if (lastTime == null || "".equals(lastTime))
			{
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -6);
				startTime = calendar.getTime();
			}
			else
			{
				startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastTime);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, -10);
			Date endTime = calendar.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("统计时间段：[" + dateFormat.format(startTime) + "," + dateFormat.format(endTime) + ")");
			//获取之前统计的连续失败数据，存储在map里
			List<Object[]> list = null;
			Set<String> eIds = null;
			String eIdsStr = monitorDao.getMonitoredEnterprise("MonitoredEId");
			try
			{
				eIds = new Gson().fromJson(eIdsStr, new HashSet<String>().getClass());
				logger.info("ContinueFailSchedule被监控的企业ID为：" + eIds);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			if (eIds != null && eIds.size() > 0)
			{
				list = continueFailDao.statistic(startTime, endTime, eIds);
			}
			else
			{
				list = continueFailDao.statistic(startTime, endTime);
			}
			//			List<Object[]> list = continueFailDao.statistic(startTime, endTime);
			List<FXContinueFail> continueFailList = continueFailDao.getContinueFail();
			Map<String, Integer> continueFailMap = new HashMap<String, Integer>();
			Map<String, Integer> maxContinueFailMap = new HashMap<String, Integer>();
			Map<String, Integer> lastContinueFailMap = new HashMap<String, Integer>();
			if (continueFailList != null && continueFailList.size() > 0)
			{
				for (FXContinueFail fail : continueFailList)
				{
					continueFailMap.put(fail.getId(), fail.getContinueFailCount());
					maxContinueFailMap.put(fail.getId(), fail.getContinueFailCount());
					lastContinueFailMap.put(fail.getId(), fail.getContinueFailCount());
				}
			}
			if (list != null && list.size() > 0)
			{
				//从数据库获取FXMessageFrequency值
				List<FXMessageFrequency> messageFrequencyList = fxMessageFrequencyDao.getMessageFrequency();
				Map<String, Date> frequencyMap = new HashMap<String, Date>();
				if (messageFrequencyList != null && messageFrequencyList.size() > 0)
				{
					for (FXMessageFrequency fre : messageFrequencyList)
					{
						frequencyMap.put(fre.getId(), fre.getLastSendTime());
					}
				}
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(title);
				//遍历统计到的数据				
				//				int dataIndex = 1;
				int messageIndex = 1;
				for (Object[] objArray : list)
				{
					//					logger.info("CONTINUEFAIL[" + (dataIndex++) + "]处理数据：" + JSON.toJSONString(objArray));
					//					String channelName = objArray[0] == null ? "" : objArray[0].toString();
					String channelId = objArray[1] == null ? "" : objArray[1].toString();
					int upstreamStatus = Integer.valueOf(objArray[2] == null ? "0" : objArray[2].toString());
					//如果为失败订单，则在原失败次数上+1，否则失败次数清零
					if (upstreamStatus == 1)
					{
						int failCount = 1;
						if (continueFailMap.get(channelId) != null)
						{
							failCount = continueFailMap.get(channelId) + 1;
						}
						continueFailMap.put(channelId, failCount);
					}
					else
					{
						int failCount = 0;
						continueFailMap.put(channelId, failCount);
					}
					//获取连续失败最大值
					//如果maxContinueFailMap里无值，或continueFailMap.get(channelId) > maxContinueFailMap.get(channelId)，则最大值为continueFailMap.get(channelId)
					if (continueFailMap.get(channelId) != null)
					{
						if (maxContinueFailMap.get(channelId) == null
								|| continueFailMap.get(channelId) > maxContinueFailMap.get(channelId))
						{
							maxContinueFailMap.put(channelId, continueFailMap.get(channelId));
						}
					}
				}
				//开始生成消息
				Set<Entry<String, Integer>> entrySet = maxContinueFailMap.entrySet();
				for (Entry<String, Integer> entry : entrySet)
				{
					String channelId = entry.getKey();
					String key = prefix + channelId;
					//如果数据库中有此通道连续失败发送记录，且半小时内发送过消息，则此次不发送
					if (frequencyMap.containsKey(key)
							&& Math.abs(System.currentTimeMillis() - frequencyMap.get(key).getTime()) < Constants.time_difference)
					{
						logger.info("上次发送时间" + frequencyMap.get(key) + "，此次不再发送");
					}
					else
					{
						int failCount = maxContinueFailMap.get(channelId);
						if (failCount >= Constants.continuefail_warning_count)
						{
							int lastContinueFailCount = 0;
							if (lastContinueFailMap.get(channelId) != null)
							{
								lastContinueFailCount = lastContinueFailMap.get(channelId);
							}
							if (failCount != lastContinueFailCount)
							{
								String content = String.format(Constants.continuefail_message_template,
										monitorDao.getChannelName(channelId), failCount);
								logger.info("此条消息为：" + Constants.lineSeparator + messageIndex + "，" + content);
								stringBuffer.append(Constants.lineSeparator + messageIndex + "，" + content);
								messageIndex++;
								FXMessageFrequency messageFrequency = new FXMessageFrequency();
								messageFrequency.setId(key);
								messageFrequency.setLastSendTime(new Date());
								fxMessageFrequencyDao.save(messageFrequency);
								if (messageIndex == 6)
								{
									//保存消息到数据库
									FXMessage message = new FXMessage();
									message.setContent(stringBuffer.toString());
									message.setCreateTime(new Date());
									message.setCreator(creator);
									message.setDesc("");
									message.setMessageReceivers(MessageUtil.getMessageReceivers(monitorDao));
									message.setMessageStatus(0);
									message.setSendWay(3);
									message.setTitle(title);
									message.setWeichatReceivers(new WeiChatUtil().getMessageReceivers(monitorDao));
									message.setWeichatStatus(0);
									fxMessageDao.save(message);
									logger.info("存储到数据库的数据为:" + JSON.toJSONString(message));
									stringBuffer = new StringBuffer();
									stringBuffer.append(title);
									messageIndex = 1;
								}
							}
							else
							{
								logger.info("上次告警连续失败次数(" + lastContinueFailCount + ")与此次告警连续失败次数(" + failCount
										+ ")相同，此次不发送告警消息");
							}
						}
						else
						{
							logger.info("连续失败次数未达到告警阈值(" + Constants.continuefail_warning_count + ")");
						}
					}
				}
				if (stringBuffer.length() > title.length())
				{
					//保存消息到数据库
					FXMessage message = new FXMessage();
					message.setContent(stringBuffer.toString());
					message.setCreateTime(new Date());
					message.setCreator(creator);
					message.setDesc("");
					message.setMessageReceivers(MessageUtil.getMessageReceivers(monitorDao));
					message.setMessageStatus(0);
					message.setSendWay(3);
					message.setTitle(title);
					message.setWeichatReceivers(new WeiChatUtil().getMessageReceivers(monitorDao));
					message.setWeichatStatus(0);
					fxMessageDao.save(message);
					logger.info("存储到数据库的数据为:" + JSON.toJSONString(message));
				}

			}
			//更新统计时间
			//			continueFailDao.updateSystemConfig(dateFormat.format(endTime), "ContinueFailLastStatisticTime");
			FXSystemConfig sysConfig = new FXSystemConfig();
			sysConfig.setDescription("");
			sysConfig.setSystemKey("ContinueFailLastStatisticTime");
			sysConfig.setSystemValue(dateFormat.format(endTime));
			fxSystemConfigDao.save(sysConfig);
			//更新失败次数
			Set<Entry<String, Integer>> entrySet = continueFailMap.entrySet();
			for (Entry<String, Integer> entry : entrySet)
			{
				FXContinueFail fail = new FXContinueFail();
				fail.setContinueFailCount(entry.getValue());
				fail.setId(entry.getKey());
				continueFailDao.save(fail);
			}

			logger.info("=====连续失败统计結束，耗时" + (System.currentTimeMillis() - time) + "ms=====");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
