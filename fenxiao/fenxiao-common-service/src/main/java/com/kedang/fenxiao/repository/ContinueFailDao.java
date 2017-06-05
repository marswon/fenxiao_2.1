package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXContinueFail;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 下午3:51:47 
 */
public interface ContinueFailDao extends PagingAndSortingRepository<FXContinueFail, Long>,
		JpaSpecificationExecutor<FXContinueFail>
{
	@Query(nativeQuery = true, value = "select (select name from fx_channel where id=o.channelId) as channelName,o.channelId,o.upstreamStatus from fx_order_record o where o.reportTime>=?1 and o.reportTime<?2 and (o.upstreamStatus=0 or o.upstreamStatus=1) order by o.reportTime")
	List<Object[]> statistic(Date startTime, Date endTime);
	
	@Query(nativeQuery = true, value = "select (select name from fx_channel where id=o.channelId) as channelName,o.channelId,o.upstreamStatus from fx_order_record o where o.reportTime>=?1 and o.reportTime<?2 and (o.upstreamStatus=0 or o.upstreamStatus=1) and o.eId in ?3 order by o.reportTime")
	List<Object[]> statistic(Date startTime, Date endTime,Set<String> eIds);

	@Query("from FXContinueFail")
	List<FXContinueFail> getContinueFail();

	@Modifying
	@Query("update FXSystemConfig set systemValue=?1 where systemKey = ?2")
	void updateSystemConfig(String systemValue, String systemKey);
}
