package com.kedang.fenxiao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXFeeHandle;
import com.kedang.fenxiao.repository.FXFeeHandleDao;

@Component
public class FXFeeHandleService
{

	@Autowired
	private FXFeeHandleDao feeHandleDao;

	@Transactional(readOnly = false)
	public FXFeeHandle save(FXFeeHandle feeHandle)
	{
		return feeHandleDao.save(feeHandle);
	}
}
