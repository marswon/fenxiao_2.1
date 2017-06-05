package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXProductAreaStrategy;

/**
 * Created by gegongxian on 17/3/30.
 */
public interface ProductAreaStrategyDao
		extends PagingAndSortingRepository<FXProductAreaStrategy, Long>, JpaSpecificationExecutor<FXProductAreaStrategy>
{

	/**
	 * 根据主键ID开启关闭策略
	 * @param status
	 * @param id
	 * @return
	 */
	@Modifying
	@Query(" update FXProductAreaStrategy set status = ?1 where id= ?2 ")
	public int updateFXProductAreaStrategyStatus(int status, Long id);
}
