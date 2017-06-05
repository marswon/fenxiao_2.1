package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXFoundsFlow;

public interface FinancialStatementsDao extends PagingAndSortingRepository<FXFoundsFlow, Long>, JpaSpecificationExecutor<FXFoundsFlow>
{

}
