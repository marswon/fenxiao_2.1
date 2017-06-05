package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXTaobaoProduct;

public interface FXTaobaoProductDao extends
		PagingAndSortingRepository<FXTaobaoProduct, Long>,
		JpaSpecificationExecutor<FXTaobaoProduct> {}
