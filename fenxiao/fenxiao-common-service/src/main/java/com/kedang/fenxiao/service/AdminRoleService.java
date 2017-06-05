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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.AdminRole;
import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.entity.RolePrivilege;
import com.kedang.fenxiao.repository.AdminRoleDao;
import com.kedang.fenxiao.repository.PrivilegeDao;
import com.kedang.fenxiao.repository.RolePrivilegeDao;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;
@Component
@Transactional(readOnly=true)
public class AdminRoleService {
	@Autowired
	private AdminRoleDao  adminRoleDao;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	@Autowired
	private PrivilegeDao privilegeDao;
	@PersistenceContext
	private EntityManager em;
	public Page<AdminRole> getAdminRoleInformation(Map<String, Object> params,Pageable pageable){
		 Page<AdminRole> adminRoles = adminRoleDao.findAll(JpaQueryUtils.buildSpecification(AdminRole.class, params),pageable);
		 return adminRoles;
	}
	
	public AdminRole getAdminRole(Long id){
		return adminRoleDao.findOne(id);
	}
	
	public List<AdminRole> findByAdminId(Long adminId){
		AdminInfo ai = adminInfoService.findOne(adminId);
		if(ai==null){
			throw new ServiceException("管理员不存在");
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("EQ_adminInfo.id", ai.getId());
		Specification<AdminRole> spec = JpaQueryUtils.buildSpecification(AdminRole.class, map);
		return adminRoleDao.findAll(spec);
	}
	
	/***
	 * 修改管理员
	 * @param adrId
	 * @param roleId
	 * @param realName
	 * @param mphone
	 * @return
	 */
	@Transactional(readOnly=false)
	public AdminRole updateAdminRole(Long adrId,Long roleId,String realName,String mphone){
		AdminRole adr = adminRoleDao.findOne(adrId);
		FXRole role = roleService.getRole(roleId);
		if(role == null){
			throw new ServiceException("输入分组id有误");
		}
		adr.setRole(role);
//		AdminInfo adi = adr.getAdminInfo();
//		adi.setRealName(realName);
//		adi.setMphone(mphone);
//		adr.setAdminInfo(adi);
		adr.setUpdateTime(new Date());
		return adminRoleDao.save(adr);
	}
	
	/***
	 * 保存管理员
	 * @param id
	 * @param adinfo
	 * @return
	 */
	@Transactional(readOnly=false)
	public AdminRole saveAdminRole(Long id,AdminInfo adminfo){
		FXRole role = roleService.getRole(id);
		AdminRole adro = new AdminRole();
		adro.setRole(role);
		adro.setAdminInfo(adminfo);
		adro.setCreateTime(new Date());
		adro.setUpdateTime(new Date());
		return adminRoleDao.save(adro);
	}
	
	/***
	 *获取角色权限列表 
	 * @param roleId
	 * @return
	 */
	public List<RolePrivilege> getRolePrivilegeListByRoleId(Long roleId){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("EQ_roleId", roleId);
		Specification<RolePrivilege> spec = JpaQueryUtils.buildSpecification(RolePrivilege.class, map);
		List<RolePrivilege> list = rolePrivilegeDao.findAll(spec);
		return list;
	}
	
	/**
	 * 获得所有权限
	 * @return
	 */
	public List<Privilege> findAllPrivilege(){
		return privilegeDao.findByIsGroup(Constant.INT_NO);
	}
	
	public AdminRole findRoleByAdminId(Long id){
		return adminRoleDao.findRoleByAdminId(id);
	}
	
	
	/**
	 * 根据管理员id查询
	 * @param adminId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findPrivilegeByAdminId(Long adminId){
		if(adminId==null){
			return new ArrayList<Privilege>();
		}
		String hql="from Privilege p where p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId in(select ar.role.id from AdminRole ar where ar.adminInfo.id=:adminId))";
		Query query=em.createQuery(hql);
		query.setParameter("adminId", adminId);
		List<Privilege> result=query.getResultList();
		return result;
	}
	/**
	 * 查询管理员是否拥有给定权限编码的权限
	 * @param adminId
	 * @param privilegeCode
	 * @return
	 */
	public boolean existsPrivilegeByAdminIdAndPrivilegeCode(Long adminId,String privilegeCode){
		if(adminId==null||StringUtils.isBlank(privilegeCode)){
			return false;
		}
		String hql="select count(*) from Privilege p where p.priCode=:privilegeCode and p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId in(select ar.role.id from AdminRole ar where ar.adminInfo.id=:adminId))";
		Query query=em.createQuery(hql);
		query.setParameter("privilegeCode", privilegeCode);
		query.setParameter("adminId", adminId);
		Object result=query.getSingleResult();
		if(result!=null){
			long count=(Long)result;
			if(count>0){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据管理员id查询该用户没有的权限组
	 * @param adminId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findPrivilegGroupByAdminId(Long adminId){
		if(adminId==null){
			return new ArrayList<Privilege>();
		}
		String hql=	" FROM Privilege pri WHERE pri.id NOT IN (select parentId from Privilege p where p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId in(select ar.role.id from AdminRole ar where ar.adminInfo.id=:adminId)) ) AND pri.isGroup = 1";
		Query query=em.createQuery(hql);
		query.setParameter("adminId", adminId);
		List<Privilege> result=query.getResultList();
		return result;
	}
}
