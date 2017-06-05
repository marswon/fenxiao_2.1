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
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXChannel;
import com.kedang.fenxiao.entity.FXProduct;
import com.kedang.fenxiao.repository.FXChannelDao;
import com.kedang.fenxiao.repository.FXProductDao;
import com.kedang.fenxiao.repository.FXProductGroupDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.enums.FxYys;
import com.kedang.fenxiao.util.po.FXYysPo;

@Component
public class FXChannelService
{
	private Logger logger = LoggerFactory.getLogger(FXOperatorProductService.class);

	@Autowired
	private FXProductDao fxProductDao;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FXProductGroupDao fxProductGroupDao;

	@Autowired
	private FXChannelDao fxChannelDao;

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
	public List<FXProduct> getProvinceToSize(String proviceId, int flowType)
	{

		return fxProductDao.getProvinceToSize(proviceId, flowType);
	}

	/**
	 * 根据省份ID查询平台产品所有流量包类型（漫游，本地）
	 * 
	 * @param proviceId
	 * @return
	 */
	public List<FXProduct> getFlowTypeByPorinceId(String proviceId)
	{

		return fxProductDao.getFlowTypeByPorinceId(proviceId);
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
			String flowName, String businessType)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" WHERE 1=1 ");
		//		StringBuilder orderBy = new StringBuilder(" order by createTime DESC");
		if (StringUtils.isNotBlank(yysId))
		{
			sb.append(" and yysTypeId=").append(yysId);
		}
		if (StringUtils.isNotBlank(provinceId))
		{
			sb.append(" and provinceId=").append(provinceId);
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
			sb.append(" and name like '%").append(flowName).append("%'")/*.append(orderBy)*/;
		}
		if (StringUtils.isNotBlank(businessType))
		{
			sb.append(" and businessType=").append(businessType);
		}
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
	public Page<FXChannel> searchFXChannel(String whereClause, Pageable pageable)
	{
		logger.info("SQL : " + "SELECT count(*) FROM FXChannel v " + whereClause);
		Query queryAll = em.createQuery("SELECT count(*) FROM FXChannel v " + whereClause);
		long counts = (Long) queryAll.getSingleResult();
		Page<FXChannel> result = new PageImpl<FXChannel>(new ArrayList<FXChannel>(), pageable, counts);
		Query queryPage = em.createQuery("FROM FXChannel v " + whereClause);
		queryPage.setFirstResult(pageable.getOffset());
		queryPage.setMaxResults(pageable.getPageSize());
		List<FXChannel> _list_product = queryPage.getResultList();
		result = new PageImpl<FXChannel>(_list_product, pageable, counts);
		System.out.println("coming");
		return result;
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
//
//			group.put("id", pg.getId());
//			group.put("text", pg.getName());
//			group.put("state", "open");
//			group.put("children", childList);
//			result.add(group);
//
//		}
//		return result;
//	}

	public FXChannel save(FXChannel channel)
	{
		return fxChannelDao.save(channel);
	}

	public FXChannel findFXChannelById(String id)
	{
		return fxChannelDao.findFXChannelById(id);
	}

	public List<FXChannel> findAllChannel()
	{
		return (List<FXChannel>) fxChannelDao.findAll();
	}

	public List<FXChannel> findAllChannelWithParam(Map<String, Object> params)
	{
		return (List<FXChannel>) fxChannelDao.findAll(SearchUtils.buildSpec(FXChannel.class, params));
	}
	/**
	 * 根据ID，状态，更新该ID点status状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateChannelStatus(String id, String status)
	{
		return fxChannelDao.updateChannelStatus(Integer.valueOf(status), id);
	}
}
