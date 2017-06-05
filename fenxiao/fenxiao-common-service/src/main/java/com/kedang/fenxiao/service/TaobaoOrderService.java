package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXTaobaoOrderRecord;
import com.kedang.fenxiao.repository.FXTaobaoOrderRecordDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月28日 下午2:41:12 
 */
@Component
public class TaobaoOrderService
{
	@Autowired
	private FXTaobaoOrderRecordDao dao;

	public Page<FXTaobaoOrderRecord> getBaobeiList(Map<String, Object> params, Pageable pageable)
	{
		Page<FXTaobaoOrderRecord> page = dao
				.findAll(SearchUtils.buildSpec(FXTaobaoOrderRecord.class, params), pageable);
		return page;
	}

	public List<Object[]> getShopList()
	{
		return dao.getShopList();
	}
	
	public Long findCount(Map<String, Object> searchParams)
	{
		return dao.count(JpaQueryUtils.buildSpecification(FXTaobaoOrderRecord.class, searchParams));
	}
	
	public List<FXTaobaoOrderRecord> findAllOrderRecord(Map<String, Object> params)
	{
		List<FXTaobaoOrderRecord> record = dao.findAll(JpaQueryUtils.buildSpecification(
				FXTaobaoOrderRecord.class, params));
		return record;
	}

}
