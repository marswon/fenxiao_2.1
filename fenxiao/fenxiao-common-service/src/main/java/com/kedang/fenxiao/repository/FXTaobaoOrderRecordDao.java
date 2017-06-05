package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXTaobaoOrderRecord;

public interface FXTaobaoOrderRecordDao extends PagingAndSortingRepository<FXTaobaoOrderRecord, String>,
		JpaSpecificationExecutor<FXTaobaoOrderRecord>
{
	@Query("SELECT shopid,name from FXTaobaoShop")
	List<Object[]> getShopList();
}
