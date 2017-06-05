package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.Device;
import com.kedang.fenxiao.entity.FXConfig;

public interface BlackListStatisticDao extends PagingAndSortingRepository<Device, Long>,
		JpaSpecificationExecutor<Device>
{
	@Query("select mobile,count(*) as ct from FXOrderRecord where clientSubmitTime >= ?1 and downstreamStatus = ?2 group by mobile having count(*) > ?3")
	public List<Object[]> statistic(Date startTime, int downstreamStatus, long count);

	@Query("from FXConfig where id = 1")
	public FXConfig getBlackList();

	@Modifying
	@Query("update FXConfig set config = ?1 where id = 1")
	public int updateBlackList(String config);
}
