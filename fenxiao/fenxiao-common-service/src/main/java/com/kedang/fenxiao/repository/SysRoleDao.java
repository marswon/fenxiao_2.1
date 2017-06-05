package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXRole;

/**
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月29日 上午10:15:29
 */
public interface SysRoleDao extends PagingAndSortingRepository<FXRole, Long>,
		JpaSpecificationExecutor<FXRole> {

	@Query("from FXRole a WHERE id =?1")
	FXRole findById(Long id);

	@Query("from FXRole a WHERE name =?1")
	List<FXRole> findByName(String name);

	@Query("from FXRole a WHERE name like ?1")
	List<FXRole> findLikeName(String name);

}
