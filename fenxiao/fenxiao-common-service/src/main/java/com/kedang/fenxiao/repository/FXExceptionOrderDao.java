package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXExceptionOrder;

public interface FXExceptionOrderDao extends PagingAndSortingRepository<FXExceptionOrder, Long>,
		JpaSpecificationExecutor<FXExceptionOrder>
{
	@Query("from FXExceptionOrder order by id desc ")
	public List<FXExceptionOrder> findAllFXExceptionOrder();

	@Query("from FXExceptionOrder where id = ?1 ")
	public List<FXExceptionOrder> findOneById(String id);
}
