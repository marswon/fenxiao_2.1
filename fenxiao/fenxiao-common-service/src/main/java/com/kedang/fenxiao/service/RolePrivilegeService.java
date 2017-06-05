/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.entity.RolePrivilege;
import com.kedang.fenxiao.repository.RolePrivilegeDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

@Component
@Transactional(readOnly = true)
public class RolePrivilegeService {
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PrivilegeService privilegeService;

	/***
	 * 保存分配
	 * 
	 * @param roleId
	 * @param privilegeId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveroleprivilege(Long roleId, List<Long> privilegeIds) {
		FXRole rol = roleService.getRole(roleId);
		if (rol == null) {
			throw new ServiceException("角色不存在");
		}
		// 清空角色原来拥有的权限
		rolePrivilegeDao.deleteRolePrivilegeListByRoleId(roleId);
		for (Long pId : privilegeIds) {
			Privilege priviege = privilegeService.findOne(pId);
			if (priviege == null) {
				throw new ServiceException("权限不存在");
			}
			if (priviege.getIsGroup().equals(1)) {// 排除权限组
				continue;
			}
			RolePrivilege rpg = new RolePrivilege();
			//rpg.setRoleId(Long.parseLong(rol.getId()));
			rpg.setRoleId(rol.getId());
			rpg.setPrivilege(priviege);
			rpg.setCreateTime(new Date());
			rpg.setUpdateTime(new Date());
			rolePrivilegeDao.save(rpg);
		}
	}

	/***
	 * 根据角色id删除角色权限
	 * 
	 * @param roleId
	 */
	@Transactional(readOnly = false)
	public void deleteRolePrivilegeListByRoleId(Long roleId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_roleId", roleId);
		Specification<RolePrivilege> spec = JpaQueryUtils.buildSpecification(
				RolePrivilege.class, map);
		List<RolePrivilege> list = rolePrivilegeDao.findAll(spec);
		for (RolePrivilege rpl : list) {
			rolePrivilegeDao.delete(rpl.getId());
		}
	}

	/***
	 * 根据权限id获取角色
	 * 
	 * @param privilegeId
	 * @return
	 */
	public List<RolePrivilege> getRolePrivilegeListByPrivilegeId(
			Long privilegeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_privilege.id", privilegeId);
		Specification<RolePrivilege> spec = JpaQueryUtils.buildSpecification(
				RolePrivilege.class, map);
		List<RolePrivilege> list = rolePrivilegeDao.findAll(spec);
		return list;
	}

	/**
	 * 根据权限编号获取角色
	 * 
	 * @param privilegeCode
	 * @return
	 */
	public List<RolePrivilege> getRolePrivilegeListByPrivilegeCode(
			String privilegeCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_privilege.priCode", privilegeCode);
		Specification<RolePrivilege> spec = JpaQueryUtils.buildSpecification(
				RolePrivilege.class, map);
		List<RolePrivilege> list = rolePrivilegeDao.findAll(spec);
		return list;
	}

}
