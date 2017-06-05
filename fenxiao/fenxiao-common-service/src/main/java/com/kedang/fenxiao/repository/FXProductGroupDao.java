package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXProductGroup;

public interface FXProductGroupDao extends
		PagingAndSortingRepository<FXProductGroup, Long>,
		JpaSpecificationExecutor<FXProductGroup> {
	@Query("from FXProductGroup where businessType = ?1 order by id desc ")
	public List<FXProductGroup> findAllFXProductGroup(Integer businessType);

	@Query("from FXProductGroup where id = ?1")
	public FXProductGroup findOne(String id);

	@Query("from FXProductGroup where businessType = ?1")
	public List<FXProductGroup> findAll(Integer businessType);
	@Query("from FXProductGroup where businessType != ?1")
	public List<FXProductGroup> findWithOutGasAll(Integer businessType);

	@Query("from FXProductGroup where businessType = ?1 and selfProductType = ?2 order by id desc ")
	public List<FXProductGroup> findAllFXProductGroup(Integer businessType,Integer selfProductType);
}
