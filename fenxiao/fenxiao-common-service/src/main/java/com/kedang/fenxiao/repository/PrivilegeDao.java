/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.Privilege;
public interface PrivilegeDao extends PagingAndSortingRepository<Privilege, Long>, JpaSpecificationExecutor<Privilege> {
	public List<Privilege> findByIsGroup(int isGroup);
}
