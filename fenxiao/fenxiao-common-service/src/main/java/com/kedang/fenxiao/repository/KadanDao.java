package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.Device;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 下午2:34:10 
 */
public interface KadanDao extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor<Device>
{
	@Query(nativeQuery = true, value = "select (select name from fx_channel where id=o.channelId) as channelName,o.channelId,sum(1),sum(case when upstreamStatus = 2 then 1 else 0 end) from fx_order_record o where o.systemSubmitTime>=?1 group by o.channelId")
	List<Object[]> statistic(Date systemSubmitTime);

	@Query(nativeQuery = true, value = "select (select name from fx_channel where id=o.channelId) as channelName,o.channelId,sum(1),sum(case when upstreamStatus = 2 then 1 else 0 end) from fx_order_record o where o.systemSubmitTime>=?1 and o.eId in ?2 group by o.channelId")
	List<Object[]> statistic(Date systemSubmitTime, Set<String> eIds);
}
