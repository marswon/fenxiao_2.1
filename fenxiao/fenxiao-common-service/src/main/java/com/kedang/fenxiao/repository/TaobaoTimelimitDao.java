package com.kedang.fenxiao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.entity.FXSystemConfig;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月19日 上午11:40:03 
 */
public interface TaobaoTimelimitDao extends PagingAndSortingRepository<FXOrderRecord, String>,
		JpaSpecificationExecutor<FXOrderRecord>
{
	@Query(nativeQuery = true, value = "SELECT tborderno,timestart,timeLimit,biztype,supplierid FROM fx_taobao_order_record WHERE date_add(timestart, interval (timeLimit-?1) second) < ?2 AND cooporderstatus=?3 AND biztype=?4")
	List<Object[]> statistic(int warning, Date curDate, String orderStatus, String biztype);

	@Query("FROM FXSystemConfig where systemKey=?1")
	FXSystemConfig getSystemConfig(String systemkey);

	@Modifying
	@Query(value = "update FXOrderRecord set downstreamStatus=?1 where downstreamOrderNo=?2")
	int updateOrderRecord(int downstreamStatus, String downstreamOrderNo);

	@Modifying
	@Query(value = "update FXTaobaoOrderRecord set isjiachong=?1,jiaChongTime=?2 where tborderno=?3")
	int updateJiaChong(int isjiachong, Date jiaChongTime, String tborderno);

}
