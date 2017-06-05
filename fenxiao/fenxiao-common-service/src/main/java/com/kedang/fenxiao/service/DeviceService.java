/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.Device;
import com.kedang.fenxiao.repository.DeviceDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
@Component
@Transactional(readOnly=true)
public class DeviceService {
	@Autowired
	private DeviceDao  deviceDao;
	
	public Device findOne(String token,Integer deviceType,Integer type){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("EQ_token", token);
		map.put("EQ_deviceType", deviceType);
		map.put("EQ_type", type);
		List<Device> result=deviceDao.findAll(JpaQueryUtils.buildSpecification(Device.class, map));
		if(result.size()>0){
			return result.get(0);
		}else{
			return null;
		}
	}
	@Transactional
	public Device save(Device device){
		if(device.getId()==null){
			device.setCreateTime(new Date());
		}
		device.setUpdateTime(new Date());
		return deviceDao.save(device);
	}
	public List<Device> findByUserIdAdnType(Long userId,Integer type){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("EQ_userId", userId);
		map.put("EQ_type", type);
		return  deviceDao.findAll(JpaQueryUtils.buildSpecification(Device.class, map));
	}
	public List<Device> findByUserId(Long userId)
	{
		return deviceDao.findByUserId(userId);
	}
}
