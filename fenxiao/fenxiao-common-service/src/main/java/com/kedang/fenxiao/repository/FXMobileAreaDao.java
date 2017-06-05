package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXMobileArea;

public interface FXMobileAreaDao extends
		PagingAndSortingRepository<FXMobileArea, Long>,
		JpaSpecificationExecutor<FXMobileArea> {

	@Query("from FXMobileArea group by provinceId")
	List<FXMobileArea> findAllGroupByProvince();
	
	@Query("from FXMobileArea  m where m.provinceId in(select p.provinceId from FXProduct p where p.id in(select ep.proId from FXEnterpriseProduct ep where eId=?1) GROUP BY p.provinceId) GROUP BY m.provinceId")
	List<FXMobileArea> findFXMobileAreaByEid(String eId);
	
	@Query("from FXMobileArea m where m.prefix=?1")
	List<FXMobileArea> findFXMobileAreaByPrefix(String prefix);
}
