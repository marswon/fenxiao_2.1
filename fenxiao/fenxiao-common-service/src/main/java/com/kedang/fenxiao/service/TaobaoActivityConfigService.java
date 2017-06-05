package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXTaobaoGift;
import com.kedang.fenxiao.repository.FXTaobaoGiftDao;
import com.kedang.fenxiao.repository.TaobaoAcivityConfigDao;
import com.kedang.fenxiao.util.SearchUtils;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年5月3日 下午2:16:23 
 */
@Component
public class TaobaoActivityConfigService
{
	@Autowired
	private TaobaoAcivityConfigDao dao;

	@Autowired
	private FXTaobaoGiftDao giftDao;

	public List<Object[]> getAllPro()
	{
		return dao.getAllPro();
	}

	public FXTaobaoGift saveGift(FXTaobaoGift gift)
	{
		try
		{
			return giftDao.save(gift);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public Page<FXTaobaoGift> getActivityList(Map<String, Object> params, Pageable pageable)
	{
		Page<FXTaobaoGift> page = giftDao.findAll(SearchUtils.buildSpec(FXTaobaoGift.class, params), pageable);
		return page;
	}
	
	@Transactional
	public int deleteTaobaoGift(int id)
	{
		return dao.deleteTaobaoGift(id);
	}
}
