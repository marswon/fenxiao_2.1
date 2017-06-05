package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXDiscount;
import com.kedang.fenxiao.repository.FXDiscountDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

@Component
public class FXDiscountService {

	@Autowired
	private FXDiscountDao fxDiscountDao;
	@PersistenceContext
	private EntityManager em;

	public List<FXDiscount> findEnterpriseDiscount(String eId) {

		return fxDiscountDao.findEnterpriseDiscount(eId);
	}

	/**
	 * 根据参数查询折扣
	 * 
	 * @param provinceCode
	 * @param yysType
	 * @param flowType
	 * @param eId
	 * @return
	 */
	public List<FXDiscount> findEnterpriseDiscount(String provinceCode,
			String yysType, String flowType, String eId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from FXDiscount where 1=1 ");
		if (StringUtils.isNotBlank(eId)) {
			sb.append(" and eId ='" + eId + "'");
		} else {
			throw new ServiceException("分销商ID不能为空");
		}
		if (StringUtils.isNotBlank(provinceCode)) {
			sb.append(" and provinceId ='" + provinceCode + "'");
		}
		if (StringUtils.isNotBlank(yysType)) {
			sb.append(" and yysTypeId ='" + yysType + "'");
		}
		if (StringUtils.isNotBlank(flowType)) {
			sb.append(" and flowType ='" + flowType + "'");
		}
		sb.append(" order by size desc ");

		Query queryPage = em.createQuery(sb.toString());
		@SuppressWarnings("unchecked")
		List<FXDiscount> _list_product = queryPage.getResultList();
		return _list_product;
	}

	/**
	 * 根据流量包类型查询是否配置过此类包
	 * 
	 * @param eId
	 * @param provinceId
	 * @param yysTypeId
	 * @param flowType
	 * @param size
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveDiscountByEidAndProduct(String eId, String provinceId,
			String yysTypeId, String flowType, List<Integer> size, int discount,String businessType) {
		try {
			List<FXDiscount> _list = fxDiscountDao.findDiscountByEidAndProduct(
					eId, provinceId, yysTypeId, Integer.parseInt(flowType),
					size);
			if (CollectionUtils.isEmpty(_list)) {
				// 保存入库
				List<FXDiscount> _listFxDiscounts = new ArrayList<FXDiscount>();
				for (Integer i : size) {
					FXDiscount fxDiscount = new FXDiscount();
					fxDiscount.setDiscount(discount);
					fxDiscount.setProvinceId(provinceId);
					fxDiscount.setYysTypeId(yysTypeId);
					fxDiscount.setFlowType(Integer.parseInt(flowType));
					fxDiscount.setSize(i);
					fxDiscount.seteId(eId);
					fxDiscount.setBusinessType(Integer.valueOf(businessType));
					_listFxDiscounts.add(fxDiscount);
				}
				fxDiscountDao.save(_listFxDiscounts);
			} else {
				List<Integer> flowValues = new ArrayList<Integer>();
				for (FXDiscount dis : _list) {
					// 已经配置过折扣的流量包
					flowValues.add(dis.getSize());
				}
				Iterator<Integer> iter = size.iterator();
				while (iter.hasNext()) {
					Integer s = iter.next();
					for (Integer f : flowValues) {
						if ((s + "").equals((f + ""))) {
							iter.remove();
						}
					}
				}
				System.out.println(size);
				// 更新已配置流量包折扣
				fxDiscountDao.updateDiscountBySize(discount, eId, provinceId,
						yysTypeId, Integer.parseInt(flowType), flowValues);
				// 未配置流量包规格的包
				// 保存入库
				List<FXDiscount> _listFxDiscounts = new ArrayList<FXDiscount>();
				for (Integer i : size) {
					FXDiscount fxDiscount = new FXDiscount();
					fxDiscount.setDiscount(discount);
					fxDiscount.setProvinceId(provinceId);
					fxDiscount.setYysTypeId(yysTypeId);
					fxDiscount.setFlowType(Integer.parseInt(flowType));
					fxDiscount.setSize(i);
					fxDiscount.seteId(eId);
					_listFxDiscounts.add(fxDiscount);
				}
				fxDiscountDao.save(_listFxDiscounts);
			}
		} catch (ServiceException e) {
			throw new ServiceException("保存折扣异常：" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id删除折扣
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public Integer removeDiscountById(int id) {
		return fxDiscountDao.removeDiscountById(id);
	}

	/**
	 * 根据分销商id查询全网折扣
	 * 
	 * @param eId
	 * @return
	 */
	public List<FXDiscount> findDiscountByeId(String eId) {
		if (StringUtils.isBlank(eId)) {
			throw new ServiceException("分销商id不能为空");
		}
		return fxDiscountDao.findDiscountByeId(eId);
	}
}
