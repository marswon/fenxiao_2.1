/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.FXRole;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface RoleDao extends PagingAndSortingRepository<FXRole, Long>, JpaSpecificationExecutor<FXRole> {

	@Query("from FXRole a WHERE id =?1")
	FXRole findById(Long id);
}
