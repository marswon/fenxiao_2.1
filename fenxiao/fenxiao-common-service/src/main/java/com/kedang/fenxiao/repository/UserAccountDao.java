/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.kedang.fenxiao.entity.UserAccount;
public interface UserAccountDao extends PagingAndSortingRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {
	public UserAccount findByUserId(Long userId);
	
	@Modifying
	@Query("update UserAccount u set u.status=:status where u.userId in(:users)")
	public void freezAccount(@Param("status")int status,@Param("users")List<Long> users);
	@Modifying
	@Query("update UserAccount u set u.walletAmount=u.walletAmount+:walletAmount where u.id=:id")
	public void addWalletAmount(@Param("walletAmount")BigDecimal walletAmount,@Param("id")Long id);
	@Modifying
	@Query("update UserAccount u set u.creditAmount=u.creditAmount+:creditAmount,u.availableAmount=u.availableAmount+:availableAmount where u.id=:id")
	public void updateCAAmount(@Param("creditAmount")BigDecimal creditAmount,@Param("availableAmount")BigDecimal availableAmount,@Param("id")Long id);
	@Modifying
	@Query("update UserAccount u set u.creditAmount=:creditAmount,u.availableAmount=:availableAmount where u.id=:id")
	public void updateAccountAmount(@Param("creditAmount")BigDecimal creditAmount,@Param("availableAmount")BigDecimal availableAmount,@Param("id")Long id);
	@Modifying
	@Query("update UserAccount uu set uu.walletAmount=uu.walletAmount+:walletAmount where uu.userId=:userId")
	public int updateWalletAmount(@Param("walletAmount")BigDecimal walletAmount,@Param("userId")Long userId);
	@Query(" from UserAccount u where u.userId = :userId")
	public List<UserAccount> findUserAccountByUserId(@Param("userId")Long userId);
}
