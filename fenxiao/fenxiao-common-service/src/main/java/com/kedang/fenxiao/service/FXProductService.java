package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kedang.fenxiao.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.repository.FXOperatorProductDao;
import com.kedang.fenxiao.repository.FXProductDao;
import com.kedang.fenxiao.repository.FXProductGroupDao;
import com.kedang.fenxiao.repository.FXProductOperatorsProductDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.UrlFormatUtils;
import com.kedang.fenxiao.util.enums.FxYys;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.FXYysPo;

@Component
public class FXProductService
{

	private Logger logger = LoggerFactory.getLogger(FXProductService.class);

	@Autowired
	private FXProductDao fxProductDao;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FXProductGroupDao fxProductGroupDao;

	@Autowired
	private FXProductOperatorsProductDao fxProductOperatorsProductDao;

	@Autowired
	private FXOperatorProductDao operatorProductDao;

	@Autowired
	private FXOperatorProductService fxOperatorProductService;

	@Autowired
	private FXEnterpriseService fxEnterpriseService;

	/**
	 * 查询平台产品列表
	 * 
	 * @param params
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
			String flowName, String businessType,String productGroup_id,Integer selfProductType)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by createTime DESC");
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
			sb.append(" and businessType=").append(businessType);
		}
		if(StringUtils.isNotBlank(productGroup_id)){
			sb.append(" and fxProductGroup.id ='").append(productGroup_id).append("'");
		}
		if(null!=selfProductType){
			sb.append(" and selfProductType=").append(selfProductType);
		}
		sb.append(orderBy);
		return sb.toString();
	}
	/**
	 * 拼接查询对应企业的平台产品sql
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 * @param businessType
	 * @param productGroup_id
	 * @return
	 */
	public String appendEnterpriseSql(String yysId, String provinceId, String flowTypeByProvinceId, String flowSize,
			String flowName, String businessType,String productGroup_id, String eId)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by provinceId,flowType desc,size asc");
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
			sb.append(" and businessType=").append(businessType);
		}
		if (StringUtils.isNotBlank(eId))
		{
			sb.append(" and eId='").append(eId).append("'");
		}
		sb.append(orderBy);
		return sb.toString();
	}

	/**
	 * 拼接查询平台流量话费产品sql
	 * 
	 * @param yysId
	 * @param provinceId
	 * @param flowTypeByProvinceId
	 * @param flowSize
	 * @param flowName
	 */
	public String appendNoGasProductSql(String yysId, String provinceId, String flowTypeByProvinceId, String flowSize,
			String flowName, String businessType,String productGroup_id,Integer selfProductType)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(" WHERE 1=1 ");
		StringBuilder orderBy = new StringBuilder(" order by createTime DESC");
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
			sb.append(" and businessType=").append(businessType);
		}
		if(StringUtils.isNotBlank(productGroup_id)){
			sb.append(" and fxProductGroup.id ='").append(productGroup_id).append("'");
		}
		if(null!=selfProductType){
            sb.append(" and selfProductType= ").append(selfProductType);
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
	public Page<FXProduct> searchFXProduct(String whereClause, Pageable pageable)
	{
		logger.info("SQL : " + "SELECT count(*) FROM FXProduct v " + whereClause);
		Query queryAll = em.createQuery("SELECT count(*) FROM FXProduct v " + whereClause);
		long counts = (Long) queryAll.getSingleResult();
		Page<FXProduct> result = new PageImpl<FXProduct>(new ArrayList<FXProduct>(), pageable, counts);
		Query queryPage = em.createQuery("FROM FXProduct v " + whereClause);
		queryPage.setFirstResult(pageable.getOffset());
		queryPage.setMaxResults(pageable.getPageSize());
		List<FXProduct> _list_product = queryPage.getResultList();
		result = new PageImpl<FXProduct>(_list_product, pageable, counts);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Page<v_fx_enterprise_product_discount> searchFXEnterpriseProduct(String whereClause, Pageable pageable)
	{
		logger.info("SQL : " + "SELECT count(*) FROM v_fx_enterprise_product_discount v " + whereClause);
		Query queryAll = em.createQuery("SELECT count(*) FROM v_fx_enterprise_product_discount v" + whereClause);
		long counts = (Long) queryAll.getSingleResult();
		Page<v_fx_enterprise_product_discount> result = new PageImpl<v_fx_enterprise_product_discount>(new ArrayList<v_fx_enterprise_product_discount>(), pageable, counts);
		Query queryPage = em.createQuery(" FROM v_fx_enterprise_product_discount v" + whereClause);
		queryPage.setFirstResult(pageable.getOffset());
		queryPage.setMaxResults(pageable.getPageSize());
		List<v_fx_enterprise_product_discount> _list_product = queryPage.getResultList();
		result = new PageImpl<v_fx_enterprise_product_discount>(_list_product, pageable, counts);
		return result;
	}

	/**
	 * 根据商户ID查询所有开通的包
	 * 
	 * @param eId
	 * @return
	 */
	public List<Map<String, Object>> queryPrivilegeTreeByBusinessType(String eId, Integer businessType)
	{
		//查询该账户的营销类型
		FXEnterprise enterprise = fxEnterpriseService.findEnterpriseById(eId);
		if(enterprise==null)
		{
			throw new ServiceException("合作商不存在");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// 查找所有产品组
		List<FXProductGroup> fxProductGroups = fxProductGroupDao.findAllFXProductGroup(businessType,enterprise.getSelfProductType());
		// 查找所有产品
		List<FXProduct> fxProductList = (List<FXProduct>) fxProductDao.findAllByBusinessType(businessType,enterprise.getSelfProductType());
		// 查找当前分销商所开通的产品
		List<FXProduct> fxProducts = fxProductDao.findProductByEid(eId);

		// 产品组循环
		for (FXProductGroup pg : fxProductGroups)
		{
			// 组
			Map<String, Object> group = new HashMap<String, Object>();
			// 组中成员
			List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
			for (FXProduct pChild : fxProductList)
			{
				if ((pg.getId() + "").equals(pChild.getFxProductGroup().getId() + ""))
				{
					Map<String, Object> child = new HashMap<String, Object>();
					for (FXProduct roleP : fxProducts)
					{
						if ((roleP.getId() + "").equals(pChild.getId() + ""))
						{
							child.put("checked", "checked");
						}
					}
					child.put("id", "C" + pChild.getId());
					child.put("text", pChild.getName());
					child.put("state", "open");//closed   open
					childList.add(child);
				}
			}

			group.put("id", pg.getId());
			group.put("text", pg.getName());
			group.put("state", "closed");
			group.put("children", childList);
			result.add(group);

		}
		return result;
	}

	/**
	 * 根据分销商ID查询开通的产品包
	 * 
	 * @return
	 */
	public List<FXProduct> findProductByIds(String eId)
	{
		if (StringUtils.isEmpty(eId))
		{
			throw new ServiceException("分销商ID不能为空");
		}
		return fxProductDao.findProductByEid(eId);
	}

	/**
	 * 	保存平台产品，保存平台产品运营商产品映射表
	 * @param params
	 * @param businessType
	 * @param maxProvince
	 * @param status
	 * @param parentId
	 * @param saveName
	 * @param proPrice
	 * @param flowType
	 * @param yysType
	 * @param size
	 * @param open_proId
	 */
	@Transactional(readOnly = false)
	public void saveProductAndPop(String[] params, String businessType, String maxProvince, String status,
			String parentId, String saveName, String proPrice, String flowType, String yysType, String size,
			String open_proId,Integer selfProductType)
	{
		if (StringUtils.isBlank(saveName))
		{
			throw new ServiceException("平台产品名称不能为空");
		}
		if (StringUtils.isBlank(parentId))
		{
			throw new ServiceException("平台产品所属产品组ID不能为空");
		}
		if (StringUtils.isBlank(flowType))
		{
			throw new ServiceException("平台产品流量包类型不能为空");
		}
		if (StringUtils.isBlank(businessType))
		{
			throw new ServiceException("平台产品所属业务类型不能为空");
		}
		if (StringUtils.isBlank(size))
		{
			throw new ServiceException("平台产品流量包大小不能为空");
		}
		try
		{
			//判断平台产品
			FXProduct fxProduct = new FXProduct();
			if (StringUtils.isNotBlank(open_proId))
			{
				fxProduct = fxProductDao.findOneById(open_proId);
				if (fxProduct != null)
				{
					fxProduct.setId(fxProduct.getId());
					//删除所有折扣配置
					int count = fxProductOperatorsProductDao.removeByProId(open_proId);
					logger.info("====删除平台产品运营商产品配置====proId:" + fxProduct.getId() + "===删除个数:" + count);
				}
			}
			//查找平台产品组
			FXProductGroup group = fxProductGroupDao.findOne(parentId);
			//保存平台产品
			fxProduct.setName(saveName);
			fxProduct.setSize(Integer.valueOf(size + ""));
			fxProduct.setPrice(Integer.valueOf(proPrice));
			fxProduct.setFlowType(Integer.valueOf(flowType));
			fxProduct.setYysTypeId(yysType);
			fxProduct.setProvinceId(maxProvince);
			fxProduct.setStatus(Integer.valueOf(status));
			fxProduct.setCreateTime(new Date());
			fxProduct.setOrderCode(size + "");
			fxProduct.setFxProductGroup(group);
			fxProduct.setBusinessType(Integer.valueOf(businessType));
			fxProduct.setCreateTime(new Date());
			fxProduct.setSelfProductType(selfProductType);
			FXProduct fProduct = fxProductDao.save(fxProduct);
			if (null == fProduct)
			{
				throw new ServiceException("平台产品保存异常");
			}
			logger.info("======后台保存平台产品成功===proId:" + fProduct.getId() + "======");
			List<FXProductOperatorsProduct> _listProductOperatorsProducts = new ArrayList<FXProductOperatorsProduct>();
			//循环保存平台产品运营商产品关系表
			for (String param : params)
			{
				if (StringUtils.isNotBlank(param))
				{
					String provinceCode = null;
					String proYysType = null;
					String oproId = null;
					//解析参数
					Map<String, String> map = UrlFormatUtils.urlFormat(param);
					if (map.containsKey("provinceCode"))
					{
						provinceCode = map.get("provinceCode");
					}
					if (map.containsKey("proYysType"))
					{
						proYysType = map.get("proYysType");
					}
					if (map.containsKey("oproId"))
					{
						oproId = map.get("oproId");
					}
					if (StringUtils.isBlank(proPrice))
					{
						throw new ServiceException("平台产品流量包价格不能为空");
					}
					if (StringUtils.isBlank(provinceCode))
					{
						throw new ServiceException("平台产品流量包省份不能为空");
					}
					if (StringUtils.isBlank(proYysType))
					{
						throw new ServiceException("平台产品所属运营商不能为空");
					}
					if (StringUtils.isBlank(oproId))
					{
						throw new ServiceException("运营商产品ID不能为空");
					}
					//根据运营商产品ID查询运营商产品
					FXProductOperatorsProduct fxProductOperatorsProduct = new FXProductOperatorsProduct();
					FXOperatorsProduct fOperatorsProduct = fxOperatorProductService.findFXOperatorsProductById(oproId);
					if (null == fOperatorsProduct)
					{
						throw new ServiceException("运营商产品不存在");
					}
					fxProductOperatorsProduct.setFxOperatorsProduct(fOperatorsProduct);
					fxProductOperatorsProduct.setProId(fProduct.getId());
					fxProductOperatorsProduct.setProvinceId(provinceCode);
					fxProductOperatorsProduct.setYysTypeId(proYysType);
					_listProductOperatorsProducts.add(fxProductOperatorsProduct);
				}
			}
			if (CollectionUtils.isNotEmpty(_listProductOperatorsProducts))
			{
				fxProductOperatorsProductDao.save(_listProductOperatorsProducts);
			}
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
			throw new ServiceException("=======保存平台产品，产品运营商产品关系表异常,error:" + e.getMessage());
		}
		logger.info("=====保存平台产品供应商产品关系表成功======");
	}

	/**
	 * 根据主键ID查询产品信息
	 * @param proId
	 * @return
	 */
	public FXProduct findProductById(String proId)
	{
		if (StringUtils.isBlank(proId))
		{
			throw new ServiceException("分销商ID不能为空");
		}
		return fxProductDao.findOneById(proId);
	}

	/**
	 * 根据主键ID上架下架
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateProductStatus(String status, String id)
	{
		if (StringUtils.isBlank(status) || StringUtils.isBlank(id))
		{
			throw new ServiceException("参数不能为空");
		}
		return fxProductDao.updateProductStatus(Integer.valueOf(status), id);
	}
}
