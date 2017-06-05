package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXOperatorsProduct;

public interface FXOperatorProductDao extends
PagingAndSortingRepository<FXOperatorsProduct, Long>,
JpaSpecificationExecutor<FXOperatorsProduct>{
	@Query("from FXOperatorsProduct where id =?1 order by id desc ")
	public FXOperatorsProduct findFXOperatorsProductById(String id);

	@Query("from FXOperatorsProduct where provinceId =?1 group by flowType ")
	public List<FXOperatorsProduct> getFlowTypeByPorinceId(String proviceId);

	@Query("from FXOperatorsProduct where provinceId =?1 and flowType =?2 group by size ")
	public List<FXOperatorsProduct> getProvinceToSize(String proviceId, int flowType);
}
