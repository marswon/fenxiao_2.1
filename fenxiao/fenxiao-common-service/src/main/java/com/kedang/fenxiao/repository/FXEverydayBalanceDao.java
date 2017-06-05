package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXEverydayBalance;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月15日 下午3:00:12 
 */
public interface FXEverydayBalanceDao extends PagingAndSortingRepository<FXEverydayBalance, Long>,
		JpaSpecificationExecutor<FXEverydayBalance>
{
	@Query("SELECT id,balance FROM FXEnterprise")
	List<Object[]> getBalance();
}
