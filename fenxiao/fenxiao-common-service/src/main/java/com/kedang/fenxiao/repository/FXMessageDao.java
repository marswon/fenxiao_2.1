package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXMessage;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 下午3:51:47 
 */
public interface FXMessageDao extends PagingAndSortingRepository<FXMessage, Integer>,
		JpaSpecificationExecutor<FXMessage>
{
}
