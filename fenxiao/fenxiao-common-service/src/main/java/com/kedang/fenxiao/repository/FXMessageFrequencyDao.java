package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kedang.fenxiao.entity.FXMessageFrequency;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 下午3:51:47 
 */
@Repository
public interface FXMessageFrequencyDao extends PagingAndSortingRepository<FXMessageFrequency, String>,
		JpaSpecificationExecutor<FXMessageFrequency>
{
	@Query("from FXMessageFrequency")
	List<FXMessageFrequency> getMessageFrequency();
}
