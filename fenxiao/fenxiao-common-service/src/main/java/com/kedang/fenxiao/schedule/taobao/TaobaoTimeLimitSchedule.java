package com.kedang.fenxiao.schedule.taobao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXNotifyRecord;
import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.repository.FXNotifyRecordDao;
import com.kedang.fenxiao.repository.TaobaoTimelimitDao;

/**
 * 类描述：淘宝超时订单处理
 * @author: zhuwanlin
 * @date: 2017年4月19日 上午11:24:37 
 */
@Component
@Lazy(false)
@Transactional
public class TaobaoTimeLimitSchedule
{
	private Logger logger = LogManager.getLogger(TaobaoTimeLimitSchedule.class);

	@Autowired
	private FXNotifyRecordDao notifyDao;

	@Autowired
	private TaobaoTimelimitDao timelimitDao;

	private int scheduleCount = 0;

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${schedule.taobao.timelimit}")
	public void schedule()
	{
		try
		{
			long time = System.currentTimeMillis();
			logger.info("=====淘宝超时订单统计开始，已执行(" + (scheduleCount++) + ")次=====");
			Calendar calendar = Calendar.getInstance();
			logger.info("统计时间点：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
			FXSystemConfig config = timelimitDao.getSystemConfig("TAOBAO_timelimit");
			logger.info("获取到的配置为：" + (config == null ? "NULL" : new Gson().toJson(config)));
			List<Object[]> list = new ArrayList<Object[]>();
			if (config != null)
			{
				Map<String, Double> map = new HashMap<String, Double>();
				map = new Gson().fromJson(config.getSystemValue(), map.getClass());
				for (String key : map.keySet())
				{
					List<Object[]> temp = timelimitDao.statistic(map.get(key).intValue(), calendar.getTime(),
							"UNDERWAY", key);
					list.addAll(temp);
				}
			}
			logger.info("待处理的订单为：" + new Gson().toJson(list));
			logger.info("生成回调记录如下：");
			Map<String, String> notifyUrlMap = new HashMap<String, String>();
			for (Object[] objArray : list)
			{
				String supplierId = objArray[4].toString();
				if (notifyUrlMap.containsKey(supplierId) == false)
				{
					FXSystemConfig supplierConfig = timelimitDao.getSystemConfig("TAOBAO_" + objArray[4].toString());
					Map<String, Object> temp = new Gson().fromJson(supplierConfig.getSystemValue(),
							new HashMap<String, Object>().getClass());
					notifyUrlMap.put(supplierId, temp.get("notifyUrl").toString());
				}

				FXNotifyRecord notify = new FXNotifyRecord();
				notify.setDowmstreamStatus(0);
				notify.setErrorCode("0000");
				notify.setErrorDesc("充值成功");
				notify.setNotifyUrl(notifyUrlMap.get(supplierId));
				notify.setOrderid("SYSTEM_CREATE");
				notify.setPushTimes(0);
				notify.setSerialNumber(objArray[0].toString());
				notify.setUpdateTime(new Date());
				FXNotifyRecord result = notifyDao.save(notify);
				logger.info(new Gson().toJson(result));
				int updateResult = timelimitDao.updateJiaChong(1, new Date(), objArray[0].toString());
				logger.info("更新淘宝订单假充状态，更新结果[" + updateResult + "]");
			}
			logger.info("=====淘宝超时订单统计結束，耗时" + (System.currentTimeMillis() - time) + "ms=====");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

}
