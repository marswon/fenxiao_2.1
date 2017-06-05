package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXNotifyRecord;

public interface FXNotifyRecordDao extends PagingAndSortingRepository<FXNotifyRecord, Long>,
		JpaSpecificationExecutor<FXNotifyRecord>
{
}
