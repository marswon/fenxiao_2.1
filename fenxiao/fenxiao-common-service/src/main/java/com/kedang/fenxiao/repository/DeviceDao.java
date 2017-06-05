/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.util.List;

import com.kedang.fenxiao.entity.Device;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface DeviceDao extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor<Device> {

	@Query("from Device where userId = ?1")
	public List<Device> findByUserId(Long userId);
	
	@Query("from Device where userId = ?1 order by updateTime desc")
	public List<Device> findByUserIdAndTime(Long userId);
}
