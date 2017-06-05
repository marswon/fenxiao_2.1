/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.CustomerConfig;
public interface CustomerConfigDao extends PagingAndSortingRepository<CustomerConfig, Long>, JpaSpecificationExecutor<CustomerConfig> {
	@Modifying
	@Query("update CustomerConfig c set c.configExp =?1 where c.type=?2")
	public void updateConfig(String configExp,int type);
	@Query("from CustomerConfig c where c.type = ?1")
	public List<CustomerConfig> findConfigByType(Integer type);
}
