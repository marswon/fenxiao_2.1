/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.Province;
import com.kedang.fenxiao.repository.ProvinceDao;
@Component
@Transactional(readOnly=true)
public class ProvinceService {
	@Autowired
	private ProvinceDao  provinceDao;

	public List<Province> getAllProvince(){
		return (List<Province>) provinceDao.findAll();
	}
	public String getProvinceName(String privinceCode){
		return  provinceDao.findProvinceName(privinceCode);
	}
}
