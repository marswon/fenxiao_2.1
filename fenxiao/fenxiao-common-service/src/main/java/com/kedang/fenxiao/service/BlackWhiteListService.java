package com.kedang.fenxiao.service;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXConfig;
import com.kedang.fenxiao.repository.BlackWhiteListDao;
import com.kedang.fenxiao.util.exception.ServiceException;

/**
 * 
 * @author wn
 *
 */
@Component
public class BlackWhiteListService {

	
	@Autowired
	private BlackWhiteListDao blackwhitelistDao;
	
	
	/**
	 * 查询黑名单
	 */
	public List<FXConfig> queryblacklist(){	
		List<FXConfig> fxconfig = blackwhitelistDao.findBlackList();		
		return fxconfig;		
	}
	
	
	/**
	 * 查询白名单
	 */
	public List<FXConfig> querywhitelist(){	
		List<FXConfig> list = blackwhitelistDao.findWhiteList();		
		return list;		
	}
	
	
	/**
	 * 保存黑名单
	 * @param fxConfig
	 */
	
	public FXConfig updateBlackFxconfig(String black){
		FXConfig fxconfig = new FXConfig();
		if (StringUtils.isBlank(black)) {
			 throw new ServiceException("手机号不能为空");
		}
		String[] blacklistDate = black.split(",");
	    if (null == blacklistDate || blacklistDate.length==0) {
			throw new ServiceException("手机号不能为空");
		}
	    	    
		try {
			fxconfig = blackwhitelistDao.findByType(1);
			fxconfig.setConfig(black);
			fxconfig.setUpdateTime(new Date());
			fxconfig = blackwhitelistDao.save(fxconfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return fxconfig;
	}
	
	
	
	
	/**
	 * 保存白名单
	 * @param fxConfig
	 */
	
	public FXConfig updateWhiteFxconfig(String white){
		FXConfig fxconfig = new FXConfig();
		if (StringUtils.isBlank(white)) {
			 throw new ServiceException("手机号不能为空");
		}
		String[] blacklistDate = white.split(",");
	    if (null == blacklistDate || blacklistDate.length==0) {
			throw new ServiceException("手机号不能为空");
		}
	    	    
		try {
			fxconfig = blackwhitelistDao.findByType(2);
			fxconfig.setConfig(white);
			fxconfig.setUpdateTime(new Date());
			fxconfig = blackwhitelistDao.save(fxconfig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return fxconfig;
	}
}
