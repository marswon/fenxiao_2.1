package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kedang.fenxiao.entity.FXExceptionOrder;
import com.kedang.fenxiao.repository.FXExceptionOrderDao;
import com.kedang.fenxiao.util.JpaQueryUtils;

@Service
public class FXExceptionOrderService
{
	@Autowired
	private FXExceptionOrderDao fxExceptionOrderDao;

	/**
	 * 查询订单表
	 * @param map
	 * @return
	 */
	public Page<FXExceptionOrder> findAllOrderRecord(Map<String, Object> params, Pageable pageable)
	{

		Page<FXExceptionOrder> fxExceptionOrder = fxExceptionOrderDao.findAll(
				JpaQueryUtils.buildSpecification(FXExceptionOrder.class, params), pageable);
		return fxExceptionOrder;
	}

	public List<FXExceptionOrder> findAllOrderRecord()
	{
		return (List<FXExceptionOrder>) fxExceptionOrderDao.findAll();
	}

	public List<FXExceptionOrder> findAllOrderRecord(Map<String, Object> params)
	{
		List<FXExceptionOrder> fxExceptionOrder = fxExceptionOrderDao.findAll(JpaQueryUtils.buildSpecification(
				FXExceptionOrder.class, params));
		return fxExceptionOrder;
	}

	public List<FXExceptionOrder> findOneById(String id)
	{
		return fxExceptionOrderDao.findOneById(id);
	}
}
