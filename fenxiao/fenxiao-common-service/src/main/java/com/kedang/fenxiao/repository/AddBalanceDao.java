package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXRecharge;

public interface AddBalanceDao extends PagingAndSortingRepository<FXRecharge, Long>,
		JpaSpecificationExecutor<FXRecharge>
{
	@Query("from FXRecharge where eId=?1 and debt>0 and status=1 order by submitTime")
	List<FXRecharge> getRechargeWithDebt(String eid);

	@Modifying
	@Query(nativeQuery = true, value = "update fx_recharge set confirmTime=?1,confirmUserId=?2,status=?3,debt=?4,amount=?5 where id=?6")
	int updateRecharge(Date confirmTime, long confirmUserId, int status, long debt, long amount, long id);

	@Modifying
	@Query("update FXRecharge set debt=debt-?1 where id=?2")
	int updateRechargeDebt(long amount, long id);

	@Modifying
	@Query("delete from FXRecharge where id=?1")
	int deleteRecharge(long id);
}
