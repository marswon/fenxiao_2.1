package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXConfig;

/**
 * 
 * @author wn
 *
 */
public interface BlackWhiteListDao  extends PagingAndSortingRepository<FXConfig, Long>,JpaSpecificationExecutor<FXConfig>{
	
	@Query("from FXConfig where config=?1")
	public List<FXConfig> findOneByConfig(String blackwhite);
	
	/**
	 * 按名单种类查询
	 */
	@Query("from FXConfig where type = ?1")
	public FXConfig findByType(Integer type);
	
	
	/**
	 * 查询黑名单号码 
	 */
	@Query("select config from FXConfig where type=1")
	public List<FXConfig> findBlackList();
	
	/**
	 * 查询白名单号码 
	 */
	@Query("select config from FXConfig where type=2")
	public List<FXConfig> findWhiteList();
}
