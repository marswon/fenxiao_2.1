package com.kedang.fenxiao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXProductGroup;
import com.kedang.fenxiao.repository.FXProductGroupDao;
import com.kedang.fenxiao.util.JpaQueryUtils;

@Component
public class FXProductGropService
{
	@Autowired
	private FXProductGroupDao fxProductGroupDao;

	/**
	 * 查询平台所有产品组
	 */
	public Page<FXProductGroup> findAllFxProductGroup(Map<String, Object> params, Pageable pageable)
	{
		Page<FXProductGroup> fxProductGroup = fxProductGroupDao.findAll(
				JpaQueryUtils.buildSpecification(FXProductGroup.class, params), pageable);
		return fxProductGroup;
	}


	/**
	 * 查询平台所有产品组
	 */
	public List<FXProductGroup> findAllFxProductGroup(String businessType)
	{
		if(StringUtils.isNotBlank(businessType)){
			return fxProductGroupDao.findAll(Integer.valueOf(businessType));
		}else{
			return (List<FXProductGroup>) fxProductGroupDao.findAll();
		}
	}
	
	/**
	 * 查询平台所有流量话费产品组
	 */
	public List<FXProductGroup> findWithoutGasFxProductGroup(String businessType)
	{
		if(StringUtils.isNotBlank(businessType)){
			return fxProductGroupDao.findWithOutGasAll(Integer.valueOf(businessType));
		}else{
			return (List<FXProductGroup>) fxProductGroupDao.findAll();
		}
	}
	/**
	 * 通过id查询产品组
	 */
	public FXProductGroup findFXFxProductGroupById(String id){
		return fxProductGroupDao.findOne(id);
	}

	/**
	 * 保存产品组
	 */
	public FXProductGroup save(FXProductGroup productGroup)
	{	
		productGroup.setCreate_time(new Date());
		return fxProductGroupDao.save(productGroup);
	}

	
}
