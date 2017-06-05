/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.RolePrivilege;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface RolePrivilegeDao extends PagingAndSortingRepository<RolePrivilege, Long>, JpaSpecificationExecutor<RolePrivilege> {
	/**
	 * 根据角色id删除权限
	 * @param roleId
	 */
	@Modifying
	@Query("delete from RolePrivilege rp where rp.roleId=?1")
	void deleteRolePrivilegeListByRoleId(Long roleId);
}
