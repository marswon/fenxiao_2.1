package com.kedang.fenxiao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.FXRecharge;
import com.kedang.fenxiao.repository.AddBalanceDao;
import com.kedang.fenxiao.util.JpaQueryUtils;

@Component
@Transactional(readOnly = true)
public class AddBalanceService
{
	@Autowired
	private AddBalanceDao addBalanceDao;

	public FXRecharge addRecharge(FXRecharge recharge)
	{
		return addBalanceDao.save(recharge);
	}

	public Page<FXRecharge> findAllAddBalance(Map<String, Object> params, Pageable pageable)
	{

		return addBalanceDao.findAll(JpaQueryUtils.buildSpecification(FXRecharge.class, params), pageable);
	}

	public FXRecharge findById(String id)
	{
		return addBalanceDao.findOne(Long.parseLong(id));
	}

	@Transactional(readOnly = false)
	public FXRecharge saveRecharge(FXRecharge fxRecharge)
	{
		return addBalanceDao.save(fxRecharge);
	}

	public List<FXRecharge> findAll(Map<String, Object> params)
	{
		List<FXRecharge> recharges = addBalanceDao.findAll(JpaQueryUtils.buildSpecification(FXRecharge.class, params));
		return recharges;
	}

	public List<FXRecharge> getRechargeWithDebt(String eid)
	{
		return addBalanceDao.getRechargeWithDebt(eid);
	}

	public int updateRecharge(Date confirmTime, long confirmUserId, int status, long debt, long amount, long id)
	{
		return addBalanceDao.updateRecharge(confirmTime, confirmUserId, status, debt, amount, id);
	}

	public int updateRechargeDebt(long amount, long id)
	{
		return addBalanceDao.updateRechargeDebt(amount, id);
	}

	public int deleteRecharge(long id)
	{
		return addBalanceDao.deleteRecharge(id);
	}
}
