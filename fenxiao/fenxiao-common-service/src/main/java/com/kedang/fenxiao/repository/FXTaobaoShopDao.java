package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXTaobaoShop;

public interface FXTaobaoShopDao extends
		PagingAndSortingRepository<FXTaobaoShop, Long>,
		JpaSpecificationExecutor<FXTaobaoShop> {}
