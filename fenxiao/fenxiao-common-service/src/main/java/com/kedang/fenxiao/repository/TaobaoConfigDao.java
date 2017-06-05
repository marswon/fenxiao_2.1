package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.entity.FXTaobaoProduct;
import com.kedang.fenxiao.entity.FXTaobaoShop;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月18日 下午1:26:31 
 */
public interface TaobaoConfigDao extends PagingAndSortingRepository<FXTaobaoShop, Long>,
		JpaSpecificationExecutor<FXTaobaoShop>
{
	@Query("SELECT shopid,name FROM FXTaobaoShop where status=0")
	List<Object[]> getShopList();

	@Query("FROM FXTaobaoShop where shopid=?1 AND status=0")
	FXTaobaoShop getShop(String shopid);

	@Query("FROM FXSystemConfig where systemKey=?1")
	FXSystemConfig getSystemConfig(String systemKey);

	@Modifying
	@Query("UPDATE FXTaobaoProduct set status=1 where id=?1")
	int deleteTaobaoProduct(int id);

	@Query("FROM FXTaobaoProduct where id=?1")
	FXTaobaoProduct toUpdatePro(int id);

}
