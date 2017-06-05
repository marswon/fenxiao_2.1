/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.AdminRole;

public interface AdminRoleDao extends PagingAndSortingRepository<AdminRole, Long>, JpaSpecificationExecutor<AdminRole>
{

	@Query("from AdminRole role where role.adminInfo.id=?1")
	AdminRole findRoleByAdminId(Long id);
}
