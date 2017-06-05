package com.kedang.fenxiao.schedule.monitor;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.kedang.fenxiao.entity.FXMessage;
import com.kedang.fenxiao.entity.FXMessageFrequency;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.repository.FXMessageDao;
import com.kedang.fenxiao.repository.FXMessageFrequencyDao;
import com.kedang.fenxiao.repository.KadanDao;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.schedule.monitor.util.CommonUtil;
import com.kedang.fenxiao.schedule.monitor.util.MessageUtil;
import com.kedang.fenxiao.schedule.monitor.util.WeiChatUtil;

@Component
@Lazy(false)
@Transactional
public class KadanRateSchedule
{
	private Logger logger = LogManager.getLogger(KadanRateSchedule.class);
	private DecimalFormat format = new DecimalFormat("0.00");
	private String prefix = "KADAN_";
	private String title = "[卡单率高]";
	private String creator = "KADAN_SCHEDULE";
	@Autowired
	private KadanDao kadanDao;
	@Autowired
	private FXMessageDao fxMessageDao;
	@Autowired
	private FXMessageFrequencyDao fxMessageFrequencyDao;
	@Autowired
	private MonitorDao monitorDao;

	private int scheduleCount = 0;

	@SuppressWarnings("unchecked")
	//@Scheduled(cron = "10/" + Constants.MONITOR_PERIOD + " * * * * ?")
	@Scheduled(cron = "${schedule.singlecard.rate}")
	public void schedule()
	{

		try
		{
			int index = 1;
			long time = System.currentTimeMillis();
			logger.info("=====卡单率统计开始，已执行(" + (scheduleCount++) + ")次=====");
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
				Constants.kadan_statistic_minute = Integer
						.valueOf(sysconfigMap.get("kadan_statistic_minute") == null ? "120" : sysconfigMap
								.get("kadan_statistic_minute"));
				Constants.kadan_warning_count = Integer.valueOf(sysconfigMap.get("kadan_warning_count") == null ? "20"
						: sysconfigMap.get("kadan_warning_count"));
				Constants.kadan_warning_rate = Double.valueOf(sysconfigMap.get("kadan_warning_rate") == null ? "0.3"
						: sysconfigMap.get("kadan_warning_rate"));
				Constants.time_difference = Integer.valueOf(sysconfigMap.get("time_difference") == null ? "3600000"
						: sysconfigMap.get("time_difference"));
				logger.info("Constants.kadan_statistic_minute=" + Constants.kadan_statistic_minute);
				logger.info("Constants.kadan_warning_count=" + Constants.kadan_warning_count);
				logger.info("Constants.kadan_statistic_minute=" + Constants.kadan_statistic_minute);
				logger.info("Constants.time_difference=" + Constants.time_difference);

			}
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE, -Constants.kadan_statistic_minute);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info("统计时间段：[" + dateFormat.format(calendar.getTime()) + "," + dateFormat.format(new Date()) + ")");
			List<Object[]> list = null;
			Set<String> eIds = null;
			String eIdsStr = monitorDao.getMonitoredEnterprise("MonitoredEId");
			try
			{
				eIds = new Gson().fromJson(eIdsStr, new HashSet<String>().getClass());
				logger.info("KadanRateSchedule被监控的企业ID为：" + eIds);
			}
			catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
			if (eIds != null && eIds.size() > 0)
			{
				list = kadanDao.statistic(calendar.getTime(), eIds);
			}
			else
			{
				list = kadanDao.statistic(calendar.getTime());
			}

			if (list != null && list.size() > 0)
			{
				logger.info("统计到的数据为：" + JSON.toJSONString(list));
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
				int count = 1;
				//遍历要处理的数据
				for (Object[] objArray : list)
				{
					logger.info("KADAN[" + (count++) + "]处理数据：" + JSON.toJSONString(objArray));
					String channelName = objArray[0] == null ? "" : objArray[0].toString();
					String channelId = objArray[1] == null ? "" : objArray[1].toString();
					int totalCount = Integer.valueOf(objArray[2] == null ? "0" : objArray[2].toString());
					int failCount = Integer.valueOf(objArray[3] == null ? "0" : objArray[3].toString());
					String key = prefix + channelId;
					//如果数据库中有此通道卡单率高发送记录，且半小时内发送过消息，则此次不发送
					if (frequencyMap.containsKey(key)
							&& Math.abs(System.currentTimeMillis() - frequencyMap.get(key).getTime()) < Constants.time_difference)
					{
						logger.info("上次发送时间" + frequencyMap.get(key) + "，此次不再发送");
					}
					else
					{
						if (totalCount >= Constants.kadan_warning_count)
						{
							double failRate = failCount * 1.0 / totalCount;
							if (failRate >= Constants.kadan_warning_rate)
							{

								String timeStr = null;
								if (Constants.kadan_statistic_minute < 60)
								{
									timeStr = Constants.kadan_statistic_minute + "分钟";
								}
								else
								{
									if (Constants.kadan_statistic_minute % 60 == 0)
									{
										timeStr = Constants.kadan_statistic_minute / 60 + "小时";
									}
									else
									{
										timeStr = new DecimalFormat("0.0")
												.format(Constants.kadan_statistic_minute / 60.0) + "小时";
									}
								}
								String content = String.format(Constants.kadan_message_template, channelName, timeStr,
										format.format(failRate * 100) + "%", totalCount);
								logger.info("此条消息为：" + Constants.lineSeparator + index + "，" + content);
								stringBuffer.append(Constants.lineSeparator + index + "，" + content);
								index++;
								FXMessageFrequency messageFrequency = new FXMessageFrequency();
								messageFrequency.setId(key);
								messageFrequency.setLastSendTime(new Date());
								fxMessageFrequencyDao.save(messageFrequency);
								if (index == 6)
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
									index = 1;
								}
							}
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
			logger.info("=====卡单率统计結束，耗时" + (System.currentTimeMillis() - time) + "ms=====");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}

	}
}
