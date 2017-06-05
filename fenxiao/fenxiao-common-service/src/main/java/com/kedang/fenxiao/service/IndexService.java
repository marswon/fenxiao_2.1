package com.kedang.fenxiao.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXAdvert;
import com.kedang.fenxiao.repository.IndexDao;

@Component
@Transactional(readOnly = true)
public class IndexService
{
	@Autowired
	private IndexDao indexDao;
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<FXAdvert> getFive(Pageable pageable)
	{
	
		String sql = "ORDER BY updateTime DESC";
		Query queryPage = em.createQuery("FROM FXAdvert v " + sql);
		queryPage.setFirstResult(0);
		queryPage.setMaxResults(5);
		List<FXAdvert> list = queryPage.getResultList();
		return list;
	}
	
	public Page<FXAdvert> getAllMessage(Pageable pageable){
		return indexDao.findAll(pageable);
	}

	public FXAdvert findMessageById(String id)
	{
		return indexDao.findOne(Long.parseLong(id));
	}

	public FXAdvert saveMessage(FXAdvert adv)
	{
		return indexDao.save(adv);
	}
}
