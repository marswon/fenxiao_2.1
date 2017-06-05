package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXFeeHandle;

public interface FXFeeHandleDao extends PagingAndSortingRepository<FXFeeHandle, Long>,
		JpaSpecificationExecutor<FXFeeHandle>
{

}
