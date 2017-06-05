package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXSystemConfig;
import com.kedang.fenxiao.entity.FXTaobaoProduct;
import com.kedang.fenxiao.entity.FXTaobaoShop;
import com.kedang.fenxiao.repository.FXTaobaoProductDao;
import com.kedang.fenxiao.repository.FXTaobaoShopDao;
import com.kedang.fenxiao.repository.TaobaoConfigDao;
import com.kedang.fenxiao.util.SearchUtils;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月18日 下午1:25:45 
 */
@Component
public class TaobaoConfigService
{
	@Autowired
	private TaobaoConfigDao configDao;
	@Autowired
	private FXTaobaoProductDao productDao;
	@Autowired
	private FXTaobaoShopDao shopDao;

	public FXTaobaoShop save(FXTaobaoShop shop)
	{
		return shopDao.save(shop);
	}

	public FXTaobaoProduct save(FXTaobaoProduct product)
	{
		return productDao.save(product);
	}

	public List<Object[]> getShopList()
	{
		return configDao.getShopList();
	}

	public FXTaobaoShop getShop(String shopid)
	{
		return configDao.getShop(shopid);
	}

	public FXSystemConfig getSystemConfig(String systemKey)
	{
		return configDao.getSystemConfig(systemKey);
	}

	public Page<FXTaobaoProduct> getBaobeiList(Map<String, Object> params, Pageable pageable)
	{
		Page<FXTaobaoProduct> fxProductAreaStrategy = productDao.findAll(
				SearchUtils.buildSpec(FXTaobaoProduct.class, params), pageable);
		return fxProductAreaStrategy;
	}

	@Transactional
	public int deleteTaobaoProduct(int id)
	{
		return configDao.deleteTaobaoProduct(id);
	}

	public FXTaobaoProduct toUpdatePro(int id)
	{
		return configDao.toUpdatePro(id);
	}

}
