package com.kedang.fenxiao.service;

import com.kedang.fenxiao.entity.FXRecharge;
import com.kedang.fenxiao.entity.FXWaitValidateRecord;
import com.kedang.fenxiao.repository.FXWaitValidateRecordDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by gegongxian on 17/5/3.
 */
@Component
public class FXWaitValidateRecordService
{
	@Autowired
	private FXWaitValidateRecordDao fxWaitValidateRecordDao;

	@Transactional(readOnly = false)
	public FXWaitValidateRecord save(FXWaitValidateRecord fxWaitValidateRecord)
	{
		return fxWaitValidateRecordDao.save(fxWaitValidateRecord);
	}

	public Page<FXWaitValidateRecord> findAllFXWaitValidateRecord(Map<String, Object> params, Pageable pageable)
	{

		return fxWaitValidateRecordDao.findAll(JpaQueryUtils.buildSpecification(FXWaitValidateRecord.class, params), pageable);
	}

	public FXWaitValidateRecord findOne(Long id)
	{
		return fxWaitValidateRecordDao.findOne(id);
	}
}
