package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXRepeatRecharge;
import com.kedang.fenxiao.repository.RepeatRechargeDao;
import com.kedang.fenxiao.util.po.RepeatRechargePo;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月30日 下午1:59:26 
 */
@Component
public class RepeatRechargeService
{
	private Logger logger = LogManager.getLogger(RepeatRechargeService.class);
	@Autowired
	private RepeatRechargeDao repeatRechargeDao;
	@PersistenceContext
	private EntityManager em;

	public FXRepeatRecharge saveRepeate(FXRepeatRecharge repeatRecharge)
	{
		return repeatRechargeDao.save(repeatRecharge);
	}

	public String getPassEid(String systemKey)
	{
		return repeatRechargeDao.getPassEid(systemKey);
	}

	public List<Object[]> getRepeatRechargeList(String yysTypeId, String provinceId, String flowType)
	{
		return repeatRechargeDao.getRepeatRechargeList(yysTypeId, provinceId, flowType);
	}

	public List<Object[]> getEnterpriseList()
	{
		return repeatRechargeDao.getEnterpriseList();
	}

	@Transactional
	public void updateSystemConfig(String systemVal, String systemKey)
	{
		repeatRechargeDao.updatePasseid(systemVal);
		repeatRechargeDao.updateSystemConfig(systemVal, systemKey);
	}

	public List<Object[]> searchChannel(String yysTypeId, String provinceId, int flowType)
	{
		return repeatRechargeDao.searchChannel(yysTypeId, provinceId, flowType);
	}

	@Transactional
	public int deleteRepeatRecharge(String yysTypeId, String provinceId, int flowType)
	{
		return repeatRechargeDao.deleteRepeatRecharge(yysTypeId, provinceId, flowType);
	}

	public List<FXRepeatRecharge> selectRepeatRecharge(String yysTypeId, String provinceId, int flowType)
	{
		return repeatRechargeDao.selectRepeatRecharge(yysTypeId, provinceId, flowType);
	}

	public List<Object[]> searchExistRepeatRehcarge(String yysTypeId, String provinceId, int flowType)
	{
		return repeatRechargeDao.searchExistRepeatRehcarge(yysTypeId, provinceId, flowType);
	}

	@SuppressWarnings("unchecked")
	public List<RepeatRechargePo> getRepeatRechargeListNew(String yysTypeId, String provinceId, String flowType)
	{
		try
		{
			logger.info("======开始查询复充配置======");
			List<RepeatRechargePo> result = new ArrayList<RepeatRechargePo>();
			String hql = "FROM FXRepeatRecharge WHERE 1=1";
			if (StringUtils.isNotBlank(yysTypeId))
			{
				hql += " AND yystypeid='" + yysTypeId + "'";
			}
			if (StringUtils.isNotBlank(provinceId))
			{
				hql += " AND provinceid='" + provinceId + "'";
			}
			if (StringUtils.isNotBlank(flowType))
			{
				hql += " AND flowtype=" + flowType;
			}
			hql += "  GROUP BY yystypeid,provinceid,flowtype ORDER BY yystypeid,provinceid,flowtype";
			logger.info("hql=" + hql);
			Query queryPage = em.createQuery(hql);
			List<FXRepeatRecharge> list = queryPage.getResultList();
			//			logger.info("list=" + JSON.toJSONString(list));
			if (list != null && list.size() > 0)
			{
				for (FXRepeatRecharge each : list)
				{
					List<String> temp = new ArrayList<String>();
					RepeatRechargePo po = new RepeatRechargePo();
					po.setFlowType(each.getFlowType());
					po.setProvinceId(each.getProvinceId());
					po.setYysTypeId(each.getYysTypeId());
					po.setRepeatList(temp);
					String tempHQL = "FROM FXRepeatRecharge WHERE yystypeid='" + each.getYysTypeId()

					+ "' AND provinceid='" + each.getProvinceId()

					+ "' AND flowtype=" + each.getFlowType()

					+ " ORDER BY serialnum";
					logger.info("tempHQL=" + tempHQL);
					Query tempQueryPage = em.createQuery(tempHQL);
					List<FXRepeatRecharge> tempList = tempQueryPage.getResultList();
					//					logger.info("tempList.size=" + tempList.size());
					if (tempList != null && tempList.size() > 0)
					{
						for (FXRepeatRecharge recharge : tempList)
						{
							String flowTypeStr = recharge.getCurFlowType() == 0 ? " [漫游]" : " [本地]";
							temp.add(recharge.getCurChannel().getName() + flowTypeStr);
						}
					}
					result.add(po);
				}
			}
			logger.info("======始查询复充配置结束======");
			return result;
		}
		catch (Exception e)
		{
			logger.info(e.getMessage(), e);
		}
		return null;
	}
}
