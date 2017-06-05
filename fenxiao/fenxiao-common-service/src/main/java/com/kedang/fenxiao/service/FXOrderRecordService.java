package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.repository.FXOrderRecordDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;

@Service
public class FXOrderRecordService
{
	@Autowired
	private FXOrderRecordDao fxOrderRecordDao;

	/**
	 * 查询订单表
	 * @param map
	 * @return
	 */
	public Page<FXOrderRecord> findAllOrderRecord(Map<String, Object> params, Pageable pageable)
	{

		Page<FXOrderRecord> fxOrderRecord = fxOrderRecordDao.findAll(
				SearchUtils.buildSpec(FXOrderRecord.class, params), pageable);
		return fxOrderRecord;
	}

	public List<FXOrderRecord> findAllOrderRecord()
	{
		return (List<FXOrderRecord>) fxOrderRecordDao.findAll();
	}

	public List<FXOrderRecord> findAllOrderRecord(Map<String, Object> params)
	{
		List<FXOrderRecord> fxOrderRecord = fxOrderRecordDao.findAll(JpaQueryUtils.buildSpecification(
				FXOrderRecord.class, params));
		return fxOrderRecord;
	}

	public List<FXOrderRecord> findOneById(String id)
	{
		return fxOrderRecordDao.findOneById(id);
	}

	public FXOrderRecord save(FXOrderRecord fxOrderRecord)
	{
		return fxOrderRecordDao.save(fxOrderRecord);
	}

	public Long findCount(Map<String, Object> searchParams)
	{
		return fxOrderRecordDao.count(JpaQueryUtils.buildSpecification(FXOrderRecord.class, searchParams));
	}

	public List<Object[]> getRepeatList(String downstreamOrderNo, String mobile)
	{
		List<Object[]> list = new ArrayList<Object[]>();
		list.addAll(fxOrderRecordDao.getRepeatList(downstreamOrderNo, mobile));
		list.addAll(fxOrderRecordDao.getOrderRecordName(downstreamOrderNo, mobile));
		return list;
	}

}
