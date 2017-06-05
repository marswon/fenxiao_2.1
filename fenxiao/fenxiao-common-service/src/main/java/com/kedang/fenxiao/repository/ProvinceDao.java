/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.Province;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProvinceDao extends PagingAndSortingRepository<Province, Long>, JpaSpecificationExecutor<Province>
{
	@Query("select name from Province p where p.privinceCode=?1")
	public String findProvinceName(String privinceCode);
}
