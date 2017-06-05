package com.kedang.fenxiao.schedule.monitor.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXPerson;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.repository.FXSystemConfigDao;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.schedule.blacklist.BlackListStatisticSchedule;
import com.kedang.fenxiao.schedule.monitor.Constants;
import com.kedang.fenxiao.schedule.monitor.bean.WeichatAccessToken;
import com.kedang.fenxiao.schedule.monitor.bean.WeichatTextMessage;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月15日 上午10:24:46 
 */
@Component
@Lazy(false)
@Transactional
public class WeiChatUtil
{
	private Logger logger = LogManager.getLogger(BlackListStatisticSchedule.class);

	private FXSystemConfigDao fxSystemConfigDao;
	
	public WeiChatUtil(FXSystemConfigDao fxSystemConfigDao){
		this.fxSystemConfigDao = fxSystemConfigDao;
	}
	public WeiChatUtil(){
	}

	public String getMessageReceivers(MonitorDao monitorDao)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			List<FXPerson> list = monitorDao.getWeichatPerson();
			if (list != null && list.size() > 0)
			{
				for (FXPerson person : list)
				{
					sb.append(person.getWeichatId() + "|");
				}
			}
			String result = sb.toString();
			if (result.length() > 0)
			{
				return result.substring(0, result.length() - 1);
			}
			else
			{
				return result;
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public String sendWeichatMessage(String receivers, String content)
	{
		String result = null;
		try
		{
			String _url = Constants.WEICHAT_SEND_URL + Constants.WEICHAT_ACCESS_TOKEN;
			WeichatTextMessage _testMessage = new WeichatTextMessage();
			_testMessage.setAgentid(0);
			_testMessage.setMsgtype("text");
			_testMessage.setSafe("0");
			_testMessage.getText().setContent(content);
			_testMessage.setTouser(receivers);
			String _data = new Gson().toJson(_testMessage);
			result = PostStreamUtil.sendPostStreamHandler(_url, _data);
			if (result.indexOf(":40014") >= 0 || result.indexOf(":42001") >= 0)
			{
				Thread.sleep(1000);
				logger.info("重新获取accessToken并发送消息");
				Constants.WEICHAT_ACCESS_TOKEN = getAccessToken();
				logger.info("Constants.WEICHAT_ACCESS_TOKEN=" + Constants.WEICHAT_ACCESS_TOKEN);
				sendWeichatMessage(receivers, content);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	private String getAccessToken()
	{
		try
		{
			Map<String, String> _map = new HashMap<String, String>();
			_map.put("corpid", Constants.WEICHAT_CORPID);
			_map.put("corpsecret", Constants.WEICHAT_SECRET);
			String _resp = HttpParamUtil.sendGetParamHandler(Constants.WEICHAT_URL, _map, "UTF-8");
			logger.info("请求地址：" + Constants.WEICHAT_URL);
			logger.info("响应结果：" + _resp);
			WeichatAccessToken _token = JSON.parseObject(_resp, WeichatAccessToken.class);
			//将token保存到数据库
			FXSystemConfig sysConfig = new FXSystemConfig();
			sysConfig.setDescription("");
			sysConfig.setSystemKey("WEICHAT_ACCESS_TOKEN");
			sysConfig.setSystemValue(_token.getAccess_token());
			fxSystemConfigDao.save(sysConfig);
			return _token.getAccess_token();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}
