package com.kedang.fenxiao.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXProductOperatorsProduct;
import com.kedang.fenxiao.repository.FXProductOperatorProductDao;

@Component
public class FXProductOperatorProductService
{

	@Autowired
	private FXProductOperatorProductDao fxProductOperatorProductDao;

	/**
	 * 根据平台产品ID查询该平台产品配置的 和分销商产品关系
	 * @param id
	 */
	public List<FXProductOperatorsProduct> findPopByProId(String id)
	{
		if (StringUtils.isBlank(id))
		{
			return null;
		}
		return fxProductOperatorProductDao.findAllFXProductOperatorsProduct(id);
	}
}
