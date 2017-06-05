package com.kedang.fenxiao.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXWaitOrder;
import com.kedang.fenxiao.repository.FXWaitOrderDao;
import com.kedang.fenxiao.util.SearchUtils;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FXWaitOrderService
{
	@Autowired
	private FXWaitOrderDao fxWaitOrderDao;

	/**
	 * 查询待处理订单记录
	 * @param searchParams
	 * @param pageable
	 * @return
	 */
	public Page<FXWaitOrder> findAll(Map<String, Object> searchParams, Pageable pageable)
	{
		return fxWaitOrderDao.findAll(SearchUtils.buildSpec(FXWaitOrder.class, searchParams), pageable);
	}

	@Transactional(readOnly = false)
	public Integer deleteBydownstreamOrderNo(String downstreamOrderNo){
		return fxWaitOrderDao.deleteBydownstreamOrderNo(downstreamOrderNo);
	}
}
