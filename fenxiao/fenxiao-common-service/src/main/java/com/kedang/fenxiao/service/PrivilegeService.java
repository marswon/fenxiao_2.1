/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.repository.PrivilegeDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
@Component
@Transactional(readOnly=true)
public class PrivilegeService {
	@Autowired
	private PrivilegeDao  privilegeDao;
	@PersistenceContext
	private EntityManager em;
	@Transactional(readOnly=false)
	public Privilege save(Privilege privilege){
		privilege.setCreateTime(new Date());
		privilege.setStatus(1);
		privilege.setUpdateTime(new Date());
		return privilegeDao.save(privilege);
	}
	
	public Privilege findOne(Long id){
		return privilegeDao.findOne(id);
	}
	
	/***
	 * 获取权限组
	 * @return
	 */
	public List<Privilege> getPrivilegeList(){
		List<Privilege> list = getParentPrivilegeList();
//		Page<Privilege> page = new PageImpl<Privilege>(list);
		if(list.size() > 0){
		for(Privilege pri : list){
			pri.setChildren(getChildParentPrilegeList(pri.getId()));
		}
		}
		return list;
	}
	/***
	 * 获取权限组
	 * @return
	 */
	public List<Privilege> getParentPrivilegeList(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("EQ_isGroup",1);
		map.put("EQ_status", 1);
		Specification<Privilege> spec = JpaQueryUtils.buildSpecification(Privilege.class, map);
		List<Privilege> list = privilegeDao.findAll(spec);
		return list;
	}
	
	/***
	 * 获取某个权限组下的所有权限
	 * @param id
	 * @return
	 */
	public List<Privilege> getChildParentPrilegeList(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("EQ_parentId",id);
		map.put("EQ_status", 1);
		Specification<Privilege> spec = JpaQueryUtils.buildSpecification(Privilege.class, map);
		List<Privilege> list = privilegeDao.findAll(spec);
		return list;
	}
	/**
	 * 获取所有权限(包括权限组和权限)
	 * @return
	 */
	public List<Privilege> findAll(){
		return (List<Privilege>)privilegeDao.findAll();
	}
	
	/**
	 * 根据角色id(权限组id)查询权限
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findPrivilegeByRoleId(Long roleId){
		if(roleId==null){
			return new ArrayList<Privilege>();
		}
		String hql="from Privilege p where p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId=:roleId))";
		Query query=em.createQuery(hql);
		query.setParameter("roleId", roleId);
		List<Privilege> result=query.getResultList();
		return result;
	}
	
	public List<Map<String,Object>> queryPrivilegeTree(Long roleId){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		List<Privilege> allPrivilege=findAll();
		List<Privilege> rolePrivilege=findPrivilegeByRoleId(roleId);
		for(Privilege p:allPrivilege){
			if(p.getIsGroup()==1){
				Map<String,Object> group=new HashMap<String, Object>();
				List<Map<String,Object>> childList=new ArrayList<Map<String,Object>>();
				for(Privilege pChild:allPrivilege){
					if(p.getId().equals(pChild.getParentId())){
						Map<String,Object> child=new HashMap<String, Object>();
						for (Privilege roleP : rolePrivilege){
							if(roleP.getId().equals(pChild.getId())){
								child.put("checked", "checked");
							}
						}
						child.put("id", pChild.getId());
						child.put("text", pChild.getText());
						child.put("state", "open");
						childList.add(child);
					}
				}
				group.put("id", p.getId());
				group.put("text", p.getText());
				group.put("state", "open");
				group.put("children", childList);
				result.add(group);
			}
		}
		return result;
	}
}
