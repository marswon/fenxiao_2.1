package com.kedang.fenxiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXOperatorsProduct;
import com.kedang.fenxiao.entity.FXProductAreaStrategy;
import com.kedang.fenxiao.repository.FXOperatorProductDao;
import com.kedang.fenxiao.repository.ProductAreaStrategyDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

/**
 * Created by gegongxian on 17/3/30.
 */
@Component
public class ProductAreaStrategyService
{

	@Autowired
	private ProductAreaStrategyDao productAreaStrategyDao;
	@Autowired
	private FXOperatorProductDao fxOperatorProductDao;

	public Page<FXProductAreaStrategy> findAllFXProductAreaStrategy(Map<String, Object> params, Pageable pageable)
	{
		return productAreaStrategyDao.findAll(JpaQueryUtils.buildSpecification(FXProductAreaStrategy.class, params),
				pageable);
	}

	@Transactional(readOnly = false)
	public void save(String oproId, String businessType, String yysTypeId, String provinceId, String size,
			String flowType, String areaCode)
	{
		//判断该策略是否存在
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_businessType", businessType);
		map.put("EQ_yysTypeId", yysTypeId);
		map.put("EQ_provinceId", provinceId);
		map.put("EQ_size", size);
		map.put("EQ_flowType", flowType);
		map.put("EQ_areaCode", areaCode);
		FXProductAreaStrategy fxProductAreaStrategy = null;
		List<FXProductAreaStrategy> fxProductAreaStrategyList = productAreaStrategyDao.findAll(JpaQueryUtils.buildSpecification(FXProductAreaStrategy.class, map));
		if (CollectionUtils.isNotEmpty(fxProductAreaStrategyList))
		{
			fxProductAreaStrategy = fxProductAreaStrategyList.get(0);
		}else {
			 fxProductAreaStrategy = new FXProductAreaStrategy();
		}
		//根据oproId 查询运营商产品
		FXOperatorsProduct fxOperatorsProduct = fxOperatorProductDao.findFXOperatorsProductById(oproId);
		if (fxOperatorsProduct == null)
		{
			throw new ServiceException("平台产品不存在");
		}
		fxProductAreaStrategy.setBusinessType(Integer.valueOf(businessType));
		fxProductAreaStrategy.setYysTypeId(Integer.valueOf(yysTypeId));
		fxProductAreaStrategy.setProvinceId(provinceId);
		fxProductAreaStrategy.setAreaCode(areaCode);
		fxProductAreaStrategy.setSize(Integer.valueOf(size));
		fxProductAreaStrategy.setOperatorsProduct(fxOperatorsProduct);
		fxProductAreaStrategy.setFlowType(Integer.valueOf(flowType));
		fxProductAreaStrategy.setChannelId(fxOperatorsProduct.getChannel().getId());
		fxProductAreaStrategy.setStatus(0);//默认关闭
		fxProductAreaStrategy.setCreateTime(new Date());
		fxProductAreaStrategy.setUpdateTime(new Date());
		productAreaStrategyDao.save(fxProductAreaStrategy);
	}

	/**
	 * 查询已配置策略
	 *
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<FXProductAreaStrategy> fxFXProductAreaStrategyPage(Map<String, Object> params, Pageable pageable)
	{
		Page<FXProductAreaStrategy> fxProductAreaStrategy = productAreaStrategyDao
				.findAll(SearchUtils.buildSpec(FXProductAreaStrategy.class, params), pageable);
		return fxProductAreaStrategy;
	}

	/**
	 * 根据主键ID开启关闭策略
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateFXProductAreaStrategyStatus(int status, Long id)
	{
		if (id <= 0)
		{
			throw new ServiceException("参数不能为空");
		}
		return productAreaStrategyDao.updateFXProductAreaStrategyStatus(status, id);
	}

	@Transactional(readOnly = false)
	public void deleteFXProductAreaStrategy(long id)
	{
		productAreaStrategyDao.delete(id);
	}
}
