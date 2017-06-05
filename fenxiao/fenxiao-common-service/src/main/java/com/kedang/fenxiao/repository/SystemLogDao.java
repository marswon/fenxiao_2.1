/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.SystemLog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface SystemLogDao extends PagingAndSortingRepository<SystemLog, Long>, JpaSpecificationExecutor<SystemLog> {
}
