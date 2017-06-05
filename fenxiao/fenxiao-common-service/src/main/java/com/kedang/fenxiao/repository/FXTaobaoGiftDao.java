package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXTaobaoGift;

public interface FXTaobaoGiftDao extends PagingAndSortingRepository<FXTaobaoGift, Long>,
		JpaSpecificationExecutor<FXTaobaoGift>
{
}
