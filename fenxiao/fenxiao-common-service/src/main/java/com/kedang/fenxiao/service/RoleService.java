/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.repository.RoleDao;
import com.kedang.fenxiao.util.exception.ServiceException;
@Component
@Transactional(readOnly=true)
public class RoleService {
	@Autowired
	private RoleDao  roleDao;
	
	public List<FXRole> getRoleList(){
		return (List<FXRole>) roleDao.findAll(new Sort(Direction.ASC, "id"));
	}

	public FXRole getRole(Long id){
		FXRole rol = roleDao.findById(id);
		if(rol == null){
			throw new ServiceException("没有对应分组");
		}
		return rol;
	}
	
	public FXRole getRole(String id){
		FXRole rol = roleDao.findById( Long.parseLong(id));
		if(rol == null){
			throw new ServiceException("没有对应分组");
		}
		return rol;
	}
	public FXRole saveRole(FXRole role){
		return roleDao.save(role);
	}
}
