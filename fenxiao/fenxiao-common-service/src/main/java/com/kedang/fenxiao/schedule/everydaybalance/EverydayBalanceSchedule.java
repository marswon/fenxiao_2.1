package com.kedang.fenxiao.schedule.everydaybalance;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXEverydayBalance;
import com.kedang.fenxiao.repository.FXEverydayBalanceDao;

/**
 * 类描述：每日零点余额统计
 * @author: zhuwanlin
 * @date: 2017年4月15日 下午2:42:38 
 */
@Component
@Lazy(false)
@Transactional
public class EverydayBalanceSchedule
{
	private Logger logger = LogManager.getLogger(EverydayBalanceSchedule.class);
	@Autowired
	private FXEverydayBalanceDao everydayBalanceDao;

	private int scheduleCount = 0;

	@Scheduled(cron = "${schedule.everyday.balance}")
	private void schedule()
	{
		try
		{
			long time = System.currentTimeMillis();
			logger.info("=====每日余额统计开始，已执行(" + (scheduleCount++) + ")次=====");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);

			List<Object[]> list = everydayBalanceDao.getBalance();
			logger.info("得到的统计数据为：" + new Gson().toJson(list));
			if (list != null && list.size() > 0)
			{
				for (Object[] objArr : list)
				{
					FXEverydayBalance eb = new FXEverydayBalance();
					eb.setBalance(Long.valueOf(objArr[1].toString()));
					eb.seteId(objArr[0].toString());
					eb.setStatisticTime(calendar.getTime());
					everydayBalanceDao.save(eb);
				}
			}
			logger.info("=====每日余额统计結束，耗时" + (System.currentTimeMillis() - time) + "ms=====");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
