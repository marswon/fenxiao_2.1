package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXTaobaoShop;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年5月3日 下午2:16:15 
 */
public interface TaobaoAcivityConfigDao extends PagingAndSortingRepository<FXTaobaoShop, Long>,
		JpaSpecificationExecutor<FXTaobaoShop>
{
	@Query("SELECT spuid,name FROM FXTaobaoProduct")
	List<Object[]> getAllPro();
	
	@Modifying
	@Query("DELETE FROM FXTaobaoGift where id=?1")
	int deleteTaobaoGift(int id);
}
