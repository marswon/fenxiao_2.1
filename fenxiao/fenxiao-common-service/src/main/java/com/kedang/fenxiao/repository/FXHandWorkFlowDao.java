/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXHandWorkFlow;

public interface FXHandWorkFlowDao extends PagingAndSortingRepository<FXHandWorkFlow, Long>,
		JpaSpecificationExecutor<FXHandWorkFlow>
{

}
