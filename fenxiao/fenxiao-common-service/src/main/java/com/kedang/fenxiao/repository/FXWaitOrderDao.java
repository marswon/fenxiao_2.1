package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXWaitOrder;

public interface FXWaitOrderDao
		extends PagingAndSortingRepository<FXWaitOrder, Long>, JpaSpecificationExecutor<FXWaitOrder>
{
	@Modifying
	@Query("delete from FXWaitOrder where downstreamOrderNo=?1 ")
	public Integer deleteBydownstreamOrderNo(String downstreamOrderNo);
}
