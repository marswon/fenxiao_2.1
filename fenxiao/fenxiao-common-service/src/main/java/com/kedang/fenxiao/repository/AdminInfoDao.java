/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.kedang.fenxiao.entity.AdminInfo;
public interface AdminInfoDao extends PagingAndSortingRepository<AdminInfo, Long>, JpaSpecificationExecutor<AdminInfo> {
	/**
	 * 根据登录名查找管理员
	 * @param loginName
	 * @return
	 */
	AdminInfo findByLoginName(String loginName);
	
	@Query("from AdminInfo where id = ?1")
	public AdminInfo findById(Long userId);
	@Modifying
	@Query("select id from AdminInfo where realName like :realName ")
	public List<Long> findIdsByName(@Param("realName")String realName);

	/**
	 * 根据用户Id查询权限下菜单列表
	 * @param userId
	 * @return  distinct
	 */	
	@Query(nativeQuery=true,value="select distinct rm.menu_id from admin_role ur left join sys_role_menu rm on ur.role_id = rm.role_id where ur.user_id =?1 ")
	List<Long> queryAllMenuId(Long userId);
}
