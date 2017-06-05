package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXProductOperatorsProduct;

public interface FXProductOperatorsProductDao extends PagingAndSortingRepository<FXProductOperatorsProduct, Long>,
		JpaSpecificationExecutor<FXProductOperatorsProduct>
{

	/**
	 * 删除折扣配置
	 * @param open_proId
	 */
	@Modifying
	@Query(" delete FXProductOperatorsProduct where proId=?1")
	public Integer removeByProId(String open_proId);

}
