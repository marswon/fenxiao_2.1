package com.kedang.fenxiao.schedule.monitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXMessage;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.repository.FXSystemConfigDao;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.schedule.monitor.util.CommonUtil;
import com.kedang.fenxiao.schedule.monitor.util.MessageUtil;
import com.kedang.fenxiao.schedule.monitor.util.WeiChatUtil;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 上午11:36:33 
 */
@Component
@Lazy(false)
@Transactional
public class SendMessageSchedule
{
	private Logger logger = LogManager.getLogger(SendMessageSchedule.class);

	@Autowired
	private MonitorDao monitorDao;

	@Autowired
	private FXSystemConfigDao fxSystemConfigDao;

	//@Scheduled(cron = "0/2 * * * * ?")
	@Scheduled(cron = "${schedule.send.sms}")
	public void sendMessage()
	{
		List<FXMessage> list = monitorDao.getMessage();
		if (list != null && list.size() > 0)
		{
			try
			{
				logger.info("=====开始发送短信消息=====");
				int index = 1;
				for (FXMessage message : list)
				{
					String receivers = message.getMessageReceivers();
					String content = message.getContent();
					if (CommonUtil.isBlank(receivers) == false && CommonUtil.isBlank(content) == false)
					{
						logger.info("SENDMESSAGE[" + index + "]接收成员：" + receivers);
						logger.info("SENDMESSAGE[" + index + "]消息内容：" + content);
						String result = MessageUtil.sendMessage(receivers, content);
						logger.info("SENDMESSAGE[" + index + "]发送结果：" + result);
					}
					index++;
					//更新消息状态
					monitorDao.updateMessageStatus(message.getId());
				}
				logger.info("=====短信消息发送结束=====");

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

	//@Scheduled(cron = "0/2 * * * * ?")
	@Scheduled(cron = "${schedule.send.sms}")
	public void sendWeichatMessage()
	{
		try
		{

			List<FXMessage> list = monitorDao.getWeichatMessage();
			if (list != null && list.size() > 0)
			{
				logger.info("=====开始发送微信消息=====");
				//初始化配置参数
				List<FXSystemConfig> sysConfigList = monitorDao.getFXSystemConfig();
				if (sysConfigList != null && sysConfigList.size() > 0)
				{
					Map<String, String> sysconfigMap = new HashMap<String, String>();
					for (FXSystemConfig config : sysConfigList)
					{
						sysconfigMap.put(config.getSystemKey(), config.getSystemValue());
					}
					Constants.WEICHAT_ACCESS_TOKEN = sysconfigMap.get("WEICHAT_ACCESS_TOKEN");
					Constants.WEICHAT_CORPID = sysconfigMap.get("WEICHAT_CORPID");
					Constants.WEICHAT_SECRET = sysconfigMap.get("WEICHAT_SECRET");
					Constants.WEICHAT_SEND_URL = sysconfigMap.get("WEICHAT_SEND_URL");
					Constants.WEICHAT_URL = sysconfigMap.get("WEICHAT_URL");

					logger.info("Constants.WEICHAT_ACCESS_TOKEN=" + Constants.WEICHAT_ACCESS_TOKEN);
					logger.info("Constants.WEICHAT_CORPID=" + Constants.WEICHAT_CORPID);
					logger.info("Constants.WEICHAT_SECRET=" + Constants.WEICHAT_SECRET);
					logger.info("Constants.WEICHAT_SEND_URL=" + Constants.WEICHAT_SEND_URL);
					logger.info("Constants.WEICHAT_URL=" + Constants.WEICHAT_URL);
					if (CommonUtil.isBlank(Constants.WEICHAT_ACCESS_TOKEN)
							|| CommonUtil.isBlank(Constants.WEICHAT_CORPID)
							|| CommonUtil.isBlank(Constants.WEICHAT_SECRET)
							|| CommonUtil.isBlank(Constants.WEICHAT_SEND_URL)
							|| CommonUtil.isBlank(Constants.WEICHAT_URL))
					{
						logger.info("微信参数缺失，不发送消息");
						return;
					}
				}
				int index = 1;
				for (FXMessage message : list)
				{
					String receivers = message.getWeichatReceivers();
					String content = message.getContent();
					if (CommonUtil.isBlank(receivers) == false && CommonUtil.isBlank(content) == false)
					{
						logger.info("SENDWEICHAT[" + index + "]接收成员：" + receivers);
						logger.info("SENDWEICHAT[" + index + "]消息内容：" + content);
						String result = new WeiChatUtil(fxSystemConfigDao).sendWeichatMessage(receivers, content);
						logger.info("SENDWEICHAT[" + index + "]发送结果：" + result);
					}
					index++;
					//更新消息状态
					monitorDao.updateWeichatStatus(message.getId());
				}
				logger.info("=====微信消息发送结束=====");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
