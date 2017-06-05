package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXOrderRecordHistory;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年5月26日 上午11:12:29 
 */
public interface FXOrderRecordHistoryDao extends PagingAndSortingRepository<FXOrderRecordHistory, Long>,
		JpaSpecificationExecutor<FXOrderRecordHistory>
{
	@Query("from FXOrderRecordHistory order by id desc ")
	public List<FXOrderRecordHistory> findAllFXOrderRecord();

	@Query("from FXOrderRecordHistory where id = ?1 ")
	public List<FXOrderRecordHistory> findOneById(String id);

	@Query(nativeQuery = true, value = "SELECT (SELECT name from fx_channel c where c.id=rrr.channelId),(SELECT op.name from fx_operators_product op where op.id=rrr.oProId),rrr.upstreamdiscount,1 from fx_repeat_recharge_record rrr where downstreamOrderNo=?1 AND mobile=?2 ORDER BY rrr.serialNum")
	public List<Object[]> getRepeatList(String downstreamOrderNo, String mobile);

	@Query(nativeQuery = true, value = "SELECT (SELECT name from fx_channel c where c.id=rrr.channelId),(SELECT op.name from fx_operators_product op where op.id=rrr.oProId),rrr.upstreamdiscount,rrr.downstreamStatus from fx_order_record_history rrr where downstreamOrderNo=?1 AND mobile=?2")
	public List<Object[]> getOrderRecordName(String downstreamOrderNo, String mobile);
}
