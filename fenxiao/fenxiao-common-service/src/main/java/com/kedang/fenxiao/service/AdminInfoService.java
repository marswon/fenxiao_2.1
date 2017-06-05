/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.repository.AdminInfoDao;
@Component
@Transactional(readOnly=true)
public class AdminInfoService {
	@Autowired
	private AdminInfoDao  adminInfoDao;

	public AdminInfo findOne(Long id){
		if(id==null){
			return null;
		}
		return adminInfoDao.findOne(id);
	}
	
	public AdminInfo findAdminInfoById(Long admin)
	{
		if (null == admin)
		{
			return null;
		}
		AdminInfo adminInfo = adminInfoDao.findById(admin);
		return adminInfo;
	}
	
	public AdminInfo saveAdminInfo(AdminInfo admin){
		
		return adminInfoDao.save(admin);
	}

	/**
	 * 根据用户id查询其权限下的菜单Id列表
	 * @param userId
	 * @return
	 */
	public List<Long> queryAllMenuId(Long userId) {
		// TODO Auto-generated method stub
		return adminInfoDao.queryAllMenuId(userId);
	}
}
