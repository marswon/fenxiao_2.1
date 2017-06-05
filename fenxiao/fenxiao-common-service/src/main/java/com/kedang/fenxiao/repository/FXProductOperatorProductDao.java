package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXProductOperatorsProduct;

public interface FXProductOperatorProductDao extends PagingAndSortingRepository<FXProductOperatorsProduct, Long>,
		JpaSpecificationExecutor<FXProductOperatorsProduct>
{
	@Query("from FXProductOperatorsProduct where proId=? order by provinceId asc ")
	public List<FXProductOperatorsProduct> findAllFXProductOperatorsProduct(String proId);
}
