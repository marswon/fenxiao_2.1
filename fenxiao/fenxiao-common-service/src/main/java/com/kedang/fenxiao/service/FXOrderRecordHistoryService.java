package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kedang.fenxiao.entity.FXOrderRecordHistory;
import com.kedang.fenxiao.repository.FXOrderRecordHistoryDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年5月26日 上午11:12:24 
 */
@Service
public class FXOrderRecordHistoryService
{
	@Autowired
	private FXOrderRecordHistoryDao fxOrderRecordHistoryDao;

	public Page<FXOrderRecordHistory> findAllOrderRecord(Map<String, Object> params, Pageable pageable)
	{

		Page<FXOrderRecordHistory> fxOrderRecord = fxOrderRecordHistoryDao.findAll(
				SearchUtils.buildSpec(FXOrderRecordHistory.class, params), pageable);
		return fxOrderRecord;
	}

	public List<FXOrderRecordHistory> findAllOrderRecord()
	{
		return (List<FXOrderRecordHistory>) fxOrderRecordHistoryDao.findAll();
	}

	public List<FXOrderRecordHistory> findAllOrderRecord(Map<String, Object> params)
	{
		List<FXOrderRecordHistory> fxOrderRecord = fxOrderRecordHistoryDao.findAll(JpaQueryUtils.buildSpecification(
				FXOrderRecordHistory.class, params));
		return fxOrderRecord;
	}

	public List<FXOrderRecordHistory> findOneById(String id)
	{
		return fxOrderRecordHistoryDao.findOneById(id);
	}

	public FXOrderRecordHistory save(FXOrderRecordHistory fxOrderRecord)
	{
		return fxOrderRecordHistoryDao.save(fxOrderRecord);
	}

	public Long findCount(Map<String, Object> searchParams)
	{
		return fxOrderRecordHistoryDao
				.count(JpaQueryUtils.buildSpecification(FXOrderRecordHistory.class, searchParams));
	}

	public List<Object[]> getRepeatList(String downstreamOrderNo, String mobile)
	{
		List<Object[]> list = new ArrayList<Object[]>();
		list.addAll(fxOrderRecordHistoryDao.getRepeatList(downstreamOrderNo, mobile));
		list.addAll(fxOrderRecordHistoryDao.getOrderRecordName(downstreamOrderNo, mobile));
		return list;
	}

}
