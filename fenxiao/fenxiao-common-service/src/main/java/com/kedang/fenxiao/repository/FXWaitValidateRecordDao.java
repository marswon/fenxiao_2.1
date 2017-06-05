package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.FXWaitOrder;
import com.kedang.fenxiao.entity.FXWaitValidateRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by gegongxian on 17/5/3.
 */
public interface FXWaitValidateRecordDao
		extends PagingAndSortingRepository<FXWaitValidateRecord, Long>, JpaSpecificationExecutor<FXWaitValidateRecord>
{
}
