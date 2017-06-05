package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXDiscount;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.repository.FXDiscountDao;
import com.kedang.fenxiao.repository.FXEnterpriseDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.enums.YysType;
import com.kedang.fenxiao.util.exception.ServiceException;

//import static org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id;

@Component
public class FXEnterpriseService
{

	@Autowired
	private FXEnterpriseDao fxEnterpriseDao;
	@Autowired
	private FXEnterpriseProductService fxEnterpriseProductService;
	@Autowired
	private FXDiscountDao fxDiscountDao;
	@PersistenceContext
	private EntityManager em;
	/**
	 * 查询合作商列表
	 *
	 * @return
	 */
	public Page<FXEnterprise> findAllFxEnterprise(Map<String, Object> params, Pageable pageable)
	{

		Page<FXEnterprise> fxEnterprise = fxEnterpriseDao.findAll(
				SearchUtils.buildSpec(FXEnterprise.class, params), pageable);
		return fxEnterprise;
	}
	
	public List<FXEnterprise> findAllEnterPriseWithParam(Map<String, Object> params)
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findAll(SearchUtils.buildSpec(FXEnterprise.class, params));
	}
//	@SuppressWarnings("unchecked")
//	public Page<FXEnterprise> searchFXEnterprise(Pageable pageable)
//	{
//		String whereClause = " where businessType in (0,1)";
//		Query queryAll = em.createQuery("SELECT count(*) FROM FXEnterprise v " + whereClause);
//		long counts = (Long) queryAll.getSingleResult();
//		Page<FXEnterprise> result = new PageImpl<FXEnterprise>(new ArrayList<FXEnterprise>(), pageable, counts);
//		Query queryPage = em.createQuery("FROM FXEnterprise v " + whereClause);
//		queryPage.setFirstResult(pageable.getOffset());
//		queryPage.setMaxResults(pageable.getPageSize());
//		List<FXEnterprise> _list_product = queryPage.getResultList();
//		result = new PageImpl<FXEnterprise>(_list_product, pageable, counts);
//		return result;
//	}
	
	/**
	 * 查询主键ID查询合作商
	 *
	 * @return
	 */
	public FXEnterprise findEnterpriseById(String id)
	{
		return fxEnterpriseDao.findEnterpriseById(id);
	}

	/**
	 * 保存合作商信息，产品配置，折扣配置 方法无并发，因业务需要保证mid＋1可加锁
	 *
	 * @param fxEnterprise
	 * @param lt
	 * @param yd
	 * @param dx
	 * @return
	 */
	@Transactional(readOnly = false)
	public synchronized FXEnterprise saveEnterpriseAndProductToDiscount(FXEnterprise fxEnterprise, String value,
			Integer dx, Integer yd, Integer lt, String businessType)
	{
		FXEnterprise enterprise = null;
		try
		{
			// 判断ID与商户编号是否为空
			if (StringUtils.isEmpty(fxEnterprise.getId()) || StringUtils.isEmpty(fxEnterprise.getMid()))
			{
				// 获取mid 最大值＋1
				List<FXEnterprise> fxE = fxEnterpriseDao.findMaxFxEnterpriseMid();
				if (CollectionUtils.isEmpty(fxE))
				{
					fxEnterprise.setMid("10000");
				}
				else
				{
					// mid
					fxEnterprise.setMid(Integer.valueOf(fxE.get(0).getMid()) + 1 + "");
				}
				// privateKey
				String privateKey = UUID.randomUUID().toString().replace("-", "");
				System.out.println(privateKey.length());
				fxEnterprise.setPrivateKey(privateKey);
				fxEnterprise.setBalance(0l);
				fxEnterprise.setBalanceReminder(0l);
				fxEnterprise.setCreditTopBalance(0l);
				fxEnterprise.setBusinessType(Integer.valueOf(businessType));
				enterprise = fxEnterpriseDao.save(fxEnterprise);
			}
			else
			{
				enterprise = fxEnterpriseDao.findEnterpriseById(fxEnterprise.getId());
				enterprise.setAddress(fxEnterprise.getAddress());
				enterprise.setAuthIP(fxEnterprise.getAuthIP());
				enterprise.setBalanceReminder(fxEnterprise.getBalanceReminder());
				enterprise.setEmail(fxEnterprise.getEmail());
				enterprise.setId(fxEnterprise.getId());
				enterprise.setLinkMan(fxEnterprise.getLinkMan());
				enterprise.setLinkPhone(fxEnterprise.getLinkPhone());
				enterprise.setName(fxEnterprise.getName());
				enterprise.setStatus(fxEnterprise.getStatus());
				enterprise.setCrestValue(fxEnterprise.getCrestValue()*1000);
				enterprise.setOpenAreaStrategy(fxEnterprise.getOpenAreaStrategy());
				long credit = fxEnterprise.getCreditTopBalance() * 1000;
				long account = enterprise.getBalance() + enterprise.getCreditTopBalance();
				if (credit >= 0 && account > credit)
				{
					enterprise.setCreditTopBalance(credit);
					enterprise.setBalance(account - credit);
				}
				else if (enterprise.getBalance() < credit || credit < 0)
				{
					throw new ServiceException("冻结金额不能大于余额，不能为负数或小数");
				}
				enterprise = fxEnterpriseDao.save(enterprise);
			}
			if (null == enterprise)
			{
				throw new ServiceException("保存分销商信息失败");
			}
			// 保存需要订购的平台产品包ID
			List<String> productId = new ArrayList<String>();
			// 判断给商户配置的平台产品
			if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("clearAll"))
			{
				fxEnterpriseProductService.removeFXEnterpriseProduct(enterprise.getId(), productId);
			}
			else if (StringUtils.isNotEmpty(value) && value.equalsIgnoreCase("no"))
			{
				// 无操作
			}
			else
			{
				// 所有cheked平台产品包与组
				String[] val = value.split(",");
				for (String v : val)
				{
					if (v.contains("C"))
					{
						productId.add(v.replace("C", ""));
					}
				}//根据分销商ID删除产品,并保存新配置平台产品
				fxEnterpriseProductService.removeFXEnterpriseProduct(enterprise.getId(), productId);
			}
//			//话费
//			if (Integer.valueOf(businessType) == 0)
//			{
//				List<String> _list = new ArrayList<String>();
//				// 删除用户所有000对应供应商折扣
//				// 保存要删除电信当前配置000的所有折扣
//				_list.add(YysType.dx.getType());
//				_list.add(YysType.yd.getType());
//				_list.add(YysType.lt.getType());
//				if (CollectionUtils.isNotEmpty(_list))
//				{
//					// 删除改分销商配置的全网折扣
//					fxDiscountDao.removeDiscountByEidAndYysType(enterprise.getId(), _list,
//							Integer.valueOf(businessType));
//				}
//				List<FXDiscount> fxDiscounts = createProvince000(dx, yd, lt, enterprise.getId(), businessType);
//				if (CollectionUtils.isNotEmpty(fxDiscounts))
//				{
//					fxDiscountDao.save(fxDiscounts);
//				}
//			}
		}
		catch (ServiceException e)
		{
			throw new ServiceException("保存分销商信息与产品配置异常，异常:" + e.getMessage());
		}
		return enterprise;
	}

	/**
	 * 生成折扣配置
	 *
	 * @param dx dianxin
	 * @param yd yidong
	 * @param lt liantong
	 * @return
	 */
	public static List<FXDiscount> createProvince000(Integer dx, Integer yd, Integer lt, String eId, String businessType)
	{
		List<FXDiscount> fxDiscounts = new ArrayList<FXDiscount>();
		int flowType = 0;
		Integer[] dxData = Constant.dxData;
		Integer[] ydData = Constant.ydData;
		Integer[] ltData = Constant.ltData;

		if (StringUtils.isNotBlank(businessType) && !businessType.equals("0"))
		{
			flowType = -1;
			dxData = ydData = ltData = Constant.huafeiData;
		}
		// dx 折扣
		if (null != dx && dx > 0)
		{

			for (int size : dxData)
			{
				FXDiscount fxDiscount = new FXDiscount();
				fxDiscount.setDiscount(Integer.valueOf(dx));
				fxDiscount.seteId(eId);
				fxDiscount.setFlowType(flowType);
				fxDiscount.setProvinceId("000");
				fxDiscount.setYysTypeId(YysType.dx.getType());
				fxDiscount.setSize(size);
				fxDiscount.setBusinessType(Integer.valueOf(businessType));
				fxDiscounts.add(fxDiscount);
			}
		}
		// yd 折扣
		if (null != yd && yd > 0)
		{
			for (int size : ydData)
			{
				FXDiscount fxDiscount = new FXDiscount();
				fxDiscount.setDiscount(Integer.valueOf(yd));
				fxDiscount.seteId(eId);
				fxDiscount.setFlowType(flowType);
				fxDiscount.setProvinceId("000");
				fxDiscount.setYysTypeId(YysType.yd.getType());
				fxDiscount.setSize(size);
				fxDiscount.setBusinessType(Integer.valueOf(businessType));
				fxDiscounts.add(fxDiscount);
			}
		}
		// lt折扣
		if (null != lt && lt > 0)
		{
			for (int size : ltData)
			{
				FXDiscount fxDiscount = new FXDiscount();
				fxDiscount.setDiscount(Integer.valueOf(lt));
				fxDiscount.seteId(eId);
				fxDiscount.setFlowType(flowType);
				fxDiscount.setProvinceId("000");
				fxDiscount.setYysTypeId(YysType.lt.getType());
				fxDiscount.setSize(size);
				fxDiscount.setBusinessType(Integer.valueOf(businessType));
				fxDiscounts.add(fxDiscount);
			}
		}
		return fxDiscounts;
	}

	public List<FXEnterprise> getEnterpriseList()
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findAll(new Sort(Direction.ASC, "id"));
	}
	
	public List<FXEnterprise> getGasEnterpriseList(Integer businessType)
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findByBusinessType(businessType);
	}
	public List<FXEnterprise> getNoGasEnterpriseList(Integer businessType)
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findNotBusinessType(businessType);
	}

	/**
	 * 保存合作商信息，产品配置，折扣配置 方法无并发，因业务需要保证mid＋1可加锁
	 *
	 * @param fxEnterprise
	 * @param lt
	 * @param yd
	 * @param dx
	 * @return
	 */
	@Transactional(readOnly = false)
	public synchronized FXEnterprise saveEnterprise(FXEnterprise fxEnterprise)
	{
		FXEnterprise enterprise = null;
		try
		{
			// 判断ID与商户编号是否为空
			if (StringUtils.isEmpty(fxEnterprise.getId()) || StringUtils.isEmpty(fxEnterprise.getMid()))
			{
				// 获取mid 最大值＋1
				List<FXEnterprise> fxE = fxEnterpriseDao.findMaxFxEnterpriseMid();
				if (CollectionUtils.isEmpty(fxE))
				{
					fxEnterprise.setMid("10000");
				}
				else
				{
					// mid
					fxEnterprise.setMid(Integer.valueOf(fxE.get(0).getMid()) + 1 + "");
				}
				// privateKey
				String privateKey = UUID.randomUUID().toString().replace("-", "");
				System.out.println(privateKey.length());
				fxEnterprise.setPrivateKey(privateKey);
				fxEnterprise.setBusinessType(fxEnterprise.getBusinessType());
				fxEnterprise.setSelfProductType(null!=fxEnterprise.getSelfProductType()?fxEnterprise.getSelfProductType():0);
				fxEnterprise.setIsLockSnumFormat(0);
				fxEnterprise.setBalance(0l);
				fxEnterprise.setBalanceReminder(0l);
				fxEnterprise.setCreditTopBalance(0l);
				fxEnterprise.setOpenAreaStrategy(0);
				fxEnterprise.setCrestValue(0);
				enterprise = fxEnterpriseDao.save(fxEnterprise);
			}
			if (null == enterprise)
			{
				throw new ServiceException("保存分销商信息失败");
			}
		}
		catch (ServiceException e)
		{
			throw new ServiceException("保存分销商信息与产品配置异常，异常:" + e.getMessage());
		}
		return enterprise;
	}

	@SuppressWarnings("unchecked")
	public Page<FXEnterprise> searchGasCardFXEnterprise(PageRequest pageable) {
		String whereClause = " where businessType=3";
		Query queryAll = em.createQuery("SELECT count(*) FROM FXEnterprise v " + whereClause);
		long counts = (Long) queryAll.getSingleResult();
		Page<FXEnterprise> result = new PageImpl<FXEnterprise>(new ArrayList<FXEnterprise>(), pageable, counts);
		Query queryPage = em.createQuery("FROM FXEnterprise v " + whereClause);
		queryPage.setFirstResult(pageable.getOffset());
		queryPage.setMaxResults(pageable.getPageSize());
		List<FXEnterprise> _list_product = queryPage.getResultList();
		result = new PageImpl<FXEnterprise>(_list_product, pageable, counts);
		return result;
	}

	/**
	 * 查询主键mID查询合作商
	 *
	 * @return
	 */
	public FXEnterprise findEnterpriseByMid(String mId)
	{
		return fxEnterpriseDao.findEnterpriseByMid(mId);
	}
}
