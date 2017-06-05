package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXSystemConfig;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月16日 下午2:06:19 
 */
public interface FXSystemConfigDao extends PagingAndSortingRepository<FXSystemConfig, Long>,
		JpaSpecificationExecutor<FXSystemConfig>
{

}
