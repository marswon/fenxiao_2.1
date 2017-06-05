package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXEnterpriseProduct;

public interface FXEnterpriseProductDao extends
		PagingAndSortingRepository<FXEnterpriseProduct, Long>,
		JpaSpecificationExecutor<FXEnterpriseProduct> {

	/**
	 * 根据分销商ID删除分销商开通的所有产品
	 * 
	 * @param proviceId
	 * @return
	 */
	@Modifying
	@Query("delete from FXEnterpriseProduct where eId =?1")
	public int removeFXEnterpriseProduct(String eId);
}
