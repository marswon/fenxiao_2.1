package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXEnterprise;

public interface FXEnterpriseDao extends PagingAndSortingRepository<FXEnterprise, Long>,
		JpaSpecificationExecutor<FXEnterprise>
{
	@Query("from FXEnterprise order by id desc ")
	public List<FXEnterprise> findAllFxEnterprise();

	@Query("from FXEnterprise where id =?1 order by id desc ")
	public FXEnterprise findEnterpriseById(String id);

	@Query("from FXEnterprise order by mid desc limit 1")
	public List<FXEnterprise> findMaxFxEnterpriseMid();

	@Query("from FXEnterprise where businessType=?1 order by mid desc limit 1")
	public List<FXEnterprise> findByBusinessType(Integer type);

	@Query("from FXEnterprise where businessType!=?1 order by mid desc limit 1")
	public List<FXEnterprise> findNotBusinessType(Integer type);

	@Query("from FXEnterprise where mid =?1 order by id desc ")
	public FXEnterprise findEnterpriseByMid(String mid);
}
