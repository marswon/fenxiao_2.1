package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXOperatorsProduct;
import com.kedang.fenxiao.entity.FXProduct;
import com.kedang.fenxiao.repository.FXOperatorProductDao;
import com.kedang.fenxiao.repository.FXProductDao;
import com.kedang.fenxiao.repository.FXProductGroupDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.enums.FxYys;
import com.kedang.fenxiao.util.po.FXYysPo;

@Component
public class FXOperatorProductService
{

	private Logger logger = LoggerFactory.getLogger(FXOperatorProductService.class);

	@Autowired
	private FXProductDao fxProductDao;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FXProductGroupDao fxProductGroupDao;

	@Autowired
	private FXOperatorProductDao operatorProductDao;

	/**
	 * 查询平台产品列表
	 *
	 * @param map
	 * @return
	 */
	public Page<FXProduct> findAllFXProduct(Map<String, Object> params, Pageable pageable)
	{

		Page<FXProduct> fxEnterprise = fxProductDao.findAll(JpaQueryUtils.buildSpecification(FXProduct.class, params),
				pageable);
		return fxEnterprise;
	}

	/**
	 * 根据省份ID查询平台产品列表
	 *
	 * @param proviceId
	 * @return
	 */
	public List<FXOperatorsProduct> getProvinceToSize(String proviceId, int flowType)
	{

		return operatorProductDao.getProvinceToSize(proviceId, flowType);
	}

	/**
	 * 根据省份ID查询平台产品所有流量包类型（漫游，本地）
	 *
	 * @param proviceId
	 * @return
	 */
	public List<FXOperatorsProduct> getFlowTypeByPorinceId(String proviceId)
	{

		return operatorProductDao.getFlowTypeByPorinceId(proviceId);
	}

	/**
	 * 获取运营商
	 */
	public static List<FXYysPo> getYysList()
	{
		List<FXYysPo> _list = new ArrayList<FXYysPo>();
		FXYysPo fxYysPo1 = new FXYysPo();
		fxYysPo1.setId(FxYys.dx.getType());
		fxYysPo1.setName(FxYys.dx.getDes());
		FXYysPo fxYysPo2 = new FXYysPo();
		fxYysPo2.setId(FxYys.yd.getType());
		fxYysPo2.setName(FxYys.yd.getDes());
		FXYysPo fxYysPo3 = new FXYysPo();
		fxYysPo3.setId(FxYys.lt.getType());
		fxYysPo3.setName(FxYys.lt.getDes());
		_list.add(fxYysPo1);
		_list.add(fxYysPo2);
		_list.add(fxYysPo3);
		return _list;
	}

	/**
	 * 拼接查询平台产品sql
	 *
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 */
	public String appendSql(String yysId, String provinceId, String flowTypeByProvinceId, String flowSize,
							String flowName,String businessType)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by channel.id desc");
		if (StringUtils.isNotBlank(yysId))
		{
			sb.append(" and yysTypeId=").append(yysId);
		}
		if (StringUtils.isNotBlank(provinceId))
		{
			if (!"000".equals(provinceId + ""))//000  |
			{
				sb.append(" and ( provinceId='").append(provinceId).append("' or provinceId ='000' ) ");
			}
		}
		if (StringUtils.isNotBlank(flowTypeByProvinceId))
		{
			if("1".equals(flowTypeByProvinceId)){
				sb.append(" and (flowType=").append(flowTypeByProvinceId).append(" or flowType=0) ");

			}else {
				sb.append(" and flowType=").append(flowTypeByProvinceId);

			}
		}
		if (StringUtils.isNotBlank(flowSize))
		{
			sb.append(" and size=").append(flowSize);
		}
		if (StringUtils.isNotBlank(flowName))
		{
			sb.append(" and name like '%").append(flowName).append("%'");
		}
		if (StringUtils.isNotBlank(businessType))
		{
			sb.append(" and businessType=").append(businessType);
		}
		sb.append(orderBy);
		return sb.toString();
	}

	/**
	 * 拼接查询平台产品sql
	 *
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 */
	public String appendOperatorSql(String yysId, String provinceId, String flowTypeByProvinceId, String flowSize,
									String flowName,String businessType)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by channel.id,provinceId,flowType desc,size asc");
		if (StringUtils.isNotBlank(yysId))
		{
			sb.append(" and yysTypeId=").append(yysId);
		}
		if (StringUtils.isNotBlank(provinceId))
		{
			sb.append(" and provinceId='").append(provinceId).append("'");
		}
		if (StringUtils.isNotBlank(flowTypeByProvinceId))
		{
			sb.append(" and flowType=").append(flowTypeByProvinceId);
		}
		if (StringUtils.isNotBlank(flowSize))
		{
			sb.append(" and size=").append(flowSize);
		}
		if (StringUtils.isNotBlank(flowName))
		{
			sb.append(" and name like '%").append(flowName).append("%'");
		}
		if (StringUtils.isNotBlank(businessType))
		{
			sb.append(" and businessType =").append(businessType);
		}
		sb.append(orderBy);
		return sb.toString();
	}

	public String appendNoGasOperatorSql(String yysId, String provinceId, String flowTypeByProvinceId, String flowSize,
										 String flowName,String businessType,String channelId)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by channel.id,provinceId,flowType desc,size asc");
		if (StringUtils.isNotBlank(yysId))
		{
			sb.append(" and yysTypeId=").append(yysId);
		}
		if (StringUtils.isNotBlank(provinceId))
		{
			sb.append(" and provinceId='").append(provinceId).append("'");
		}
		if (StringUtils.isNotBlank(flowTypeByProvinceId))
		{
			sb.append(" and flowType=").append(flowTypeByProvinceId);
		}
		if (StringUtils.isNotBlank(flowSize))
		{
			sb.append(" and size=").append(flowSize);
		}
		if (StringUtils.isNotBlank(flowName))
		{
			sb.append(" and name like '%").append(flowName).append("%'");
		}
		if (StringUtils.isNotBlank(businessType))
		{
			sb.append(" and businessType =").append(businessType);
		}
		if (StringUtils.isNotBlank(channelId))
		{
			sb.append(" and channelId='").append(channelId).append("'");
		}
		sb.append(" and businessType in (0,1)");
		sb.append(orderBy);
		return sb.toString();
	}
	/**
	 * 根据sql条件分页查询平台产品
	 *
	 * @param whereClause
	 * @param pageable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<FXOperatorsProduct> searchFXProduct(String whereClause, Pageable pageable)
	{
		try
		{
			logger.info("SQL : " + "SELECT count(*) FROM FXOperatorsProduct v " + whereClause);
			Query queryAll = em.createQuery("SELECT count(*) FROM FXOperatorsProduct v " + whereClause);
			long counts = (Long) queryAll.getSingleResult();
			Page<FXOperatorsProduct> result = new PageImpl<FXOperatorsProduct>(new ArrayList<FXOperatorsProduct>(),
					pageable, counts);
			Query queryPage = em.createQuery("FROM FXOperatorsProduct v " + whereClause);
			queryPage.setFirstResult(pageable.getOffset());
			queryPage.setMaxResults(pageable.getPageSize());
			List<FXOperatorsProduct> _list_product = queryPage.getResultList();
			result = new PageImpl<FXOperatorsProduct>(_list_product, pageable, counts);
			return result;
		}
		catch (Exception e)
		{
			logger.info(e.getMessage(),e);
		}
		return null;
	}

//	/**
//	 * 根据商户ID查询所有开通的包
//	 * 
//	 * @param eId
//	 * @return
//	 */
//	public List<Map<String, Object>> queryPrivilegeTree(String eId)
//	{
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		// 查找所有产品组
//		List<FXProductGroup> fxProductGroups = fxProductGroupDao.findAllFXProductGroup();
//		// 查找所有产品
//		List<FXProduct> fxProductList = (List<FXProduct>) fxProductDao.findAll();
//		// 查找当前分销商所开通的产品
//		List<FXProduct> fxProducts = fxProductDao.findProductByEid(eId);
//
//		// 产品组循环
//		for (FXProductGroup pg : fxProductGroups)
//		{
//			// 组
//			Map<String, Object> group = new HashMap<String, Object>();
//			// 组中成员
//			List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
//			for (FXProduct pChild : fxProductList)
//			{
//				if ((pg.getId() + "").equals(pChild.getGroupId() + ""))
//				{
//					Map<String, Object> child = new HashMap<String, Object>();
//					for (FXProduct roleP : fxProducts)
//					{
//						if ((roleP.getId() + "").equals(pChild.getId() + ""))
//						{
//							child.put("checked", "checked");
//						}
//					}
//					child.put("id", "C" + pChild.getId());
//					child.put("text", pChild.getName());
//					child.put("state", "open");
//					childList.add(child);
//				}
//			}
//			group.put("id", pg.getId());
//			group.put("text", pg.getName());
//			group.put("state", "open");
//			group.put("children", childList);
//			result.add(group);
//		}
//		return result;
//	}

	/**
	 * 查询运营商产品
	 *
	 * @param params
	 * @param pageable
	 * @return
	 */
	public Page<FXOperatorsProduct> fxOperatorsProductPage(Map<String, Object> params, Pageable pageable)
	{
		Page<FXOperatorsProduct> fxOperatorsProduct = operatorProductDao
				.findAll(SearchUtils.buildSpec(FXOperatorsProduct.class, params), pageable);
		return fxOperatorsProduct;
	}

	public FXOperatorsProduct save(FXOperatorsProduct operatorsProduct)
	{
		return operatorProductDao.save(operatorsProduct);
	}

	public FXOperatorsProduct findFXOperatorsProductById(String id)
	{
		return operatorProductDao.findFXOperatorsProductById(id);
	}
}
