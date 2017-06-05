package com.kedang.fenxiao.schedule.monitor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.kedang.fenxiao.entity.FXMessage;
import com.kedang.fenxiao.repository.FXMessageDao;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.schedule.monitor.bean.StatisticBean;
import com.kedang.fenxiao.schedule.monitor.util.MessageUtil;
import com.kedang.fenxiao.schedule.monitor.util.WeiChatUtil;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月30日 下午8:03:41 
 */
@Component
@Lazy(false)
@Transactional
public class StatisticSchedule
{
	private Logger logger = LogManager.getLogger(StatisticSchedule.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DecimalFormat format = new DecimalFormat("0.00");
	private String lineSeparator = System.getProperty("line.separator");
	@Autowired
	private MonitorDao monitorDao;
	@Autowired
	private FXMessageDao fxMessageDao;
	private String title = "[销量统计]";
	private String creator = "STATISTIC_SCHEDULE";
	private Map<Integer, String> scaleMap = new HashMap<Integer, String>();

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${schedule.order.statistic}")
	public void schedule()
	{
		logger.info("=====开始进行统计任务=====");
		String useScaleStr = monitorDao.getSystemValue("statistic_use_scale");
		logger.info("statistic_use_scale=" + useScaleStr);
		int useScale = StringUtils.isBlank(useScaleStr) ? 1 : Integer.valueOf(useScaleStr);
		String scaleMapStr = monitorDao.getSystemValue("statistic_scale");
		logger.info("statistic_scale=" + scaleMapStr);
		if (StringUtils.isBlank(scaleMapStr) == false)
		{
			scaleMap = JSON.parseObject(scaleMapStr, scaleMap.getClass());
		}
		long time = System.currentTimeMillis();
		String businessTypeStr = monitorDao.getSystemValue("businessType");
		Map<Integer, String> businessTypeMap = JSON.parseObject(businessTypeStr,
				new HashMap<Integer, String>().getClass());
		logger.info("businessTypeMap = " + JSON.toJSONString(businessTypeMap));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		//得到今天零点时间
		Date todayZero = calendar.getTime();
		logger.info("todayZero = " + sdf.format(todayZero));
		//得到昨天零点时间
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date yestodayZero = calendar.getTime();
		logger.info("yestodayZero = " + sdf.format(yestodayZero));
		//得到本月最后一天
		int thisMonthLastDay = calendar.get(Calendar.DAY_OF_MONTH);
		//得到昨天所属月1号零点
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date thisMonthZero = calendar.getTime();
		logger.info("thisMonthZero = " + sdf.format(thisMonthZero));
		//得到上月时间
		Calendar calendarTwo = Calendar.getInstance();
		calendarTwo.set(Calendar.HOUR_OF_DAY, 0);
		calendarTwo.set(Calendar.MINUTE, 0);
		calendarTwo.set(Calendar.SECOND, 0);
		calendarTwo.add(Calendar.MONTH, -1);
		int lastMonthLastDay = calendarTwo.getActualMaximum(Calendar.DAY_OF_MONTH);
		//如果上月也有今天，则计算上月今天的时间段
		Date lastMonthTodayZero = null;
		Date lastMonthYestodayZero = null;
		if (lastMonthLastDay >= thisMonthLastDay)
		{
			logger.info("上月也有今天");
			lastMonthTodayZero = calendarTwo.getTime();
			calendarTwo.add(Calendar.DAY_OF_MONTH, -1);
			lastMonthYestodayZero = calendarTwo.getTime();
		}
		calendarTwo.set(Calendar.DAY_OF_MONTH, 1);
		Date lastMonthZero = calendarTwo.getTime();
		logger.info("lastMonthTodayZero = " + (lastMonthTodayZero == null ? "NULL" : sdf.format(lastMonthTodayZero)));
		logger.info("lastMonthYestodayZero = "
				+ (lastMonthYestodayZero == null ? "NULL" : sdf.format(lastMonthYestodayZero)));
		logger.info("lastMonthZero = " + sdf.format(lastMonthZero));
		//开始生成统计数据
		//月总销量
		BigDecimal thisMonthCost = monitorDao.getCostMoney(thisMonthZero, todayZero);
		if (thisMonthCost == null)
		{
			thisMonthCost = new BigDecimal(0);
		}

		BigDecimal lastMonthCost = new BigDecimal(0);
		if (lastMonthTodayZero != null && lastMonthYestodayZero != null)
		{
			logger.info("上月总销量计算时间段：[" + sdf.format(lastMonthZero) + "," + sdf.format(lastMonthTodayZero) + ")");
			lastMonthCost = monitorDao.getCostMoney(lastMonthZero, lastMonthTodayZero);
		}
		else
		{
			logger.info("上月总销量计算时间段：[" + sdf.format(lastMonthZero) + "," + sdf.format(thisMonthZero) + ")");
			lastMonthCost = monitorDao.getCostMoney(lastMonthZero, thisMonthZero);
		}
		double monthIncreaseRate = 0;
		if (lastMonthCost.longValue() != 0)
		{
			monthIncreaseRate = (thisMonthCost.longValue() - lastMonthCost.longValue()) * 100.0
					/ lastMonthCost.longValue();
		}
		//统计本天各业务数据
		Map<Integer, StatisticBean> todayCostMoneyMap = new HashMap<Integer, StatisticBean>();
		for (Integer businessType : businessTypeMap.keySet())
		{
			StatisticBean tempBean = new StatisticBean();
			BigDecimal todayCostMoney = monitorDao.getCostMoney(yestodayZero, todayZero, businessType);
			if (todayCostMoney == null)
			{
				todayCostMoney = new BigDecimal(0);
			}
			BigDecimal lastMonthTodayCostMoney = new BigDecimal(0);
			if (lastMonthTodayZero != null && lastMonthYestodayZero != null)
			{
				lastMonthTodayCostMoney = monitorDao.getCostMoney(lastMonthYestodayZero, lastMonthTodayZero,
						businessType);

			}
			double increaseRate = 0;
			if (lastMonthTodayCostMoney != null && lastMonthTodayCostMoney.longValue() != 0)
			{
				increaseRate = (todayCostMoney.longValue() - lastMonthTodayCostMoney.longValue()) * 100.0
						/ lastMonthTodayCostMoney.longValue();
			}
			tempBean.setBusinessType(businessType);
			tempBean.setBusinessTypeName(businessTypeMap.get(businessType));
			tempBean.setDateType(0);
			tempBean.setEndTime(todayZero);
			tempBean.setIncreaseRate(increaseRate);
			tempBean.setLastMonthTodayCostMoney(lastMonthTodayCostMoney);
			tempBean.setStartTime(yestodayZero);
			tempBean.setTodayCostMoney(todayCostMoney);
			todayCostMoneyMap.put(businessType, tempBean);
		}
		//天总销量
		BigDecimal thisMonthDayCost = monitorDao.getCostMoney(yestodayZero, todayZero);
		if (thisMonthDayCost == null)
		{
			thisMonthDayCost = new BigDecimal(0);
		}
		BigDecimal lastMonthDayCost = new BigDecimal(0);
		if (lastMonthTodayZero != null && lastMonthYestodayZero != null)
		{
			logger.info("上月当天总销量计算时间段：[" + sdf.format(lastMonthYestodayZero) + "," + sdf.format(lastMonthTodayZero)
					+ ")");
			lastMonthDayCost = monitorDao.getCostMoney(lastMonthYestodayZero, lastMonthTodayZero);
		}
		if (lastMonthDayCost == null)
		{
			lastMonthDayCost = new BigDecimal(0);
		}
		double increaseRate = 0;
		if (lastMonthDayCost.longValue() != 0)
		{
			increaseRate = (thisMonthDayCost.longValue() - lastMonthDayCost.longValue()) * 100.0
					/ lastMonthDayCost.longValue();
		}
		StringBuffer sb = new StringBuffer();
		long lastMonthDayCostVal = lastMonthDayCost.longValue();
		long lastMonthCostVal = lastMonthCost.longValue();
		sb.append("【可当科技】");
		sb.append(lineSeparator);
		sb.append(new SimpleDateFormat("MM-dd").format(yestodayZero) + "数据统计,消费额:");
		sb.append(getMoneyStr(thisMonthDayCost.longValue(), useScale));
		//上月无今天，则不需要统计增长率
		if (lastMonthTodayZero != null && lastMonthYestodayZero != null)
		{
			sb.append("(上月同天" + getMoneyStr(lastMonthDayCostVal, useScale) + "，增长" + formatRate(increaseRate) + "%)");
		}
		sb.append("。");
		sb.append("截止到" + new SimpleDateFormat("MM-dd HH:mm").format(todayZero) + ",本月消费总额");
		sb.append(getMoneyStr(thisMonthCost.longValue(), useScale));
		sb.append("(上月同时" + getMoneyStr(lastMonthCostVal, useScale) + "，增长" + formatRate(monthIncreaseRate) + "%)");
		sb.append("。");
		sb.append(lineSeparator);
		sb.append("[" + new SimpleDateFormat("MM-dd").format(yestodayZero) + "销量详情如下]");
		sb.append(lineSeparator);
		//		String template = 
		//				"【可当科技】" + lineSeparator + 
		//				new SimpleDateFormat("MM-dd").format(yestodayZero) + "数据统计,消费额:"
		//				+ getMoneyStr(thisMonthDayCost.longValue(), useScale) + "(上月同天"
		//				+ getMoneyStr(lastMonthDayCost.longValue(), useScale) + "，增长" + format.format(increaseRate) + "%)。截止到"
		//				+ new SimpleDateFormat("MM-dd HH:mm").format(todayZero) + ",本月消费总额"
		//				+ getMoneyStr(thisMonthCost.longValue(), useScale) + "(上月同时"
		//				+ getMoneyStr(lastMonthCost.longValue(), useScale) + "，增长" + format.format(monthIncreaseRate) + "%)。";
		//		template = template + lineSeparator + "[" + new SimpleDateFormat("MM-dd").format(yestodayZero) + "销量详情如下]"
		//				+ lineSeparator;
		int index = 1;
		for (Integer businessType : businessTypeMap.keySet())
		{
			long value = todayCostMoneyMap.get(businessType).getTodayCostMoney().longValue();
			String businessName = businessTypeMap.get(businessType);
			sb.append(index + "，[" + businessName + "]");
			sb.append("销量：" + getMoneyStr(value, useScale) + "。");
			sb.append(lineSeparator);
			index++;
		}
		//如果配置了指定人接收统计消息，则只发给指定人，否则发给全部开启微信的人
		String receiverStr = monitorDao.getSystemValue("statistic_weichat_receivers");
		logger.info("统计消息指定接收人：" + receiverStr);
		Set<String> receiverSet = new HashSet<String>();
		if (StringUtils.isBlank(receiverStr) == false)
		{
			receiverSet = JSON.parseObject(receiverStr, receiverSet.getClass());
		}
		String receivers = "";
		if (receiverSet != null && receiverSet.size() > 0)
		{
			for (String each : receiverSet)
			{
				receivers += each + "|";
			}
			receivers = receivers.substring(0, receivers.length() - 1);
		}
		else
		{
			receivers = new WeiChatUtil().getMessageReceivers(monitorDao);
		}
		//开始生成消息记录
		FXMessage message = new FXMessage();
		message.setContent(sb.toString());
		message.setCreateTime(new Date());
		message.setCreator(creator);
		message.setDesc("");
		message.setMessageReceivers(MessageUtil.getMessageReceivers(monitorDao));
		message.setMessageStatus(0);
		message.setSendWay(3);
		message.setTitle(title);
		message.setWeichatReceivers(receivers);
		message.setWeichatStatus(0);
		fxMessageDao.save(message);
		logger.info("存储到数据库的数据为:" + JSON.toJSONString(message));
		logger.info("=====统计任务結束，耗时" + (System.currentTimeMillis() - time) + "ms=====");
	}

	private String getMoneyStr(long value, int scale)
	{
		String scaleName = scaleMap.get(scale) == null ? "元" : scaleMap.get(scale);
		return format.format(value / 1000.0 / scale) + scaleName;
	}

	private String formatRate(double rate)
	{
		return format.format(rate);
	}
}
