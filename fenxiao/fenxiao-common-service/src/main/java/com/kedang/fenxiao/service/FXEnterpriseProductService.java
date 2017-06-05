package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXEnterpriseProduct;
import com.kedang.fenxiao.entity.FXProduct;
import com.kedang.fenxiao.repository.FXEnterpriseProductDao;
import com.kedang.fenxiao.util.exception.ServiceException;

@Component
public class FXEnterpriseProductService {

	private Logger logger = LoggerFactory
			.getLogger(FXEnterpriseProductService.class);

	@Autowired
	private FXEnterpriseProductDao fxEnterpriseProductDao;
	@Autowired
	private FXProductService fxProductService;

	/**
	 * 根据分销商ID删除产品,并保存新配置平台产品
	 * 
	 * @param eId
	 * @return
	 */
	public void removeFXEnterpriseProduct(String eId, List<String> productId) {

		try {
			if (StringUtils.isEmpty(eId)) {
				throw new ServiceException("分销商ID不能为空");
			}
			fxEnterpriseProductDao.removeFXEnterpriseProduct(eId);

			List<FXEnterpriseProduct> _list = new ArrayList<FXEnterpriseProduct>();
			for (String pId : productId) {

				FXEnterpriseProduct fxEnterpriseProduct = new FXEnterpriseProduct();
				fxEnterpriseProduct.seteId(eId);
				fxEnterpriseProduct.setProId(pId);
				_list.add(fxEnterpriseProduct);
			}
			fxEnterpriseProductDao.save(_list);
		} catch (ServiceException e) {
			logger.error("更新分销商配置平台产品异常，异常：" + e.getMessage());
			throw new ServiceException("更新分销商配置平台产品异常，异常：" + e.getMessage());
		}
	}

	/**
	 * 根据平台分销商ID查询已开通产品
	 * 
	 * @param eId
	 * @return
	 */
	public List<FXProduct> findEnterpriseProduct(String eId) {
		return fxProductService.findProductByIds(eId);
	}
}
