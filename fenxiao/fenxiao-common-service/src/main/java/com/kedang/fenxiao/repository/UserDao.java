/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.kedang.fenxiao.entity.User;
public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	public User findByMphone(String mphone);
	@Query("from User where idCode = ?1 and authenticateStatus = 2")
	public User findByIdCode(String idCode);
	public User findByName(String name);
	/**
	 * 查询待认证的用户(包括基础和高级待认证)
	 * @param adminId
	 * @param authStatus
	 * @param pageable
	 * @return
	 */
	@Query("from User u where u.authenticateStatus=:authStatus and (u.firstAdmin.id=:adminId or u.secondAdmin.id=:adminId)")
	public Page<User> queryDrz(@Param("adminId")Long adminId,@Param("authStatus")Integer authStatus,Pageable pageable);
	
	@Modifying
	@Query("update User u SET u.admin =:adminId WHERE u.id=:id ")
	public Integer updateUser(@Param("adminId")Long adminId,@Param("id")Long id);
	@Query("select count(*) from User u where u.authenticateStatus=?1")
	public long countUser(int authStatus);
	@Query("select count(*) from User u where u.authenticateStatus=?1 and u.admin=?2")
	public long countUserByAdmin(int type, Long id);
	@Query("select count(*) from User u where u.createTime>=?1 and u.createTime<=?2")
	public long countRegisterUser(Date beginDate,Date endDate);
	@Query("select count(*) from User")
	public long countAllUser();
	@Modifying
	@Query("update User u SET u.recommended =:recommended WHERE u.recommended LIKE :mphone")
	public Integer updateRecommended(@Param("recommended")String recommended,@Param("mphone")String mphone);
	@Query("from User u where u.mphone = :mphone ")
	public User queryByPhone(@Param("mphone")String mphone);
	@Query("from User where id = ?1 ")
	public User findById(Long userId);
	@Query("from User where idCode = ?1 and authenticateStatus = 2")
	public List<User> findByIdCodeAndAuth(String idCode);
	@Modifying
	@Query(" update User u SET u.recommended =:recommended ,u.recommendedRecord =:recommendedRecord WHERE id = :id")
	public Integer addRecommended(@Param("recommended")String recommended,@Param("recommendedRecord")String recommendedRecord,@Param("id")Long id);
	@Query(" from User u where u.id in( ?1 ) ")
	public List<User> findMoreUserInUserId(Set<Long> userIds);
}
