package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXFoundsFlow;
import com.kedang.fenxiao.repository.FinancialStatementsDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;

@Component
public class FinancialStatementsService
{
	@Autowired
	private FinancialStatementsDao financialStatementsDao;
	public Page<FXFoundsFlow> findAll(Map<String, Object> searchParams, Pageable pageable)
	{
		return financialStatementsDao.findAll(SearchUtils.buildSpec(FXFoundsFlow.class, searchParams), pageable);
	}
	
	public FXFoundsFlow save(FXFoundsFlow foundsFlow)
	{
		return financialStatementsDao.save(foundsFlow);
	}
	
	public List<FXFoundsFlow> findAll(Map<String, Object> params){
		List<FXFoundsFlow> records = financialStatementsDao.findAll(
				JpaQueryUtils.buildSpecification(FXFoundsFlow.class, params));
		return records;
	}

	public Long findCount(Map<String, Object> searchParams) {
		return financialStatementsDao.count(JpaQueryUtils.buildSpecification(FXFoundsFlow.class, searchParams));
	}
}
