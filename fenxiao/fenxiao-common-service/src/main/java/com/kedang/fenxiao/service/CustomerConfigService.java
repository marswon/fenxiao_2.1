/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */
package com.kedang.fenxiao.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kedang.fenxiao.entity.CustomerConfig;
import com.kedang.fenxiao.repository.CustomerConfigDao;
import com.kedang.fenxiao.service.common.FileManageService;
import com.kedang.fenxiao.util.BigDecimalUtils;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.enums.CustomerConfigType;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ActivityConfiguration;
import com.kedang.fenxiao.util.po.AwardNotice;
import com.kedang.fenxiao.util.po.CycleRate;
import com.kedang.fenxiao.util.po.Delay;
import com.kedang.fenxiao.util.po.Installment;
import com.kedang.fenxiao.util.po.InstallmentCount;
import com.kedang.fenxiao.util.po.MessageModel;
import com.kedang.fenxiao.util.po.OverdueRate;
import com.kedang.fenxiao.util.po.PrizeConfiguration;
import com.kedang.fenxiao.util.po.RecommendAward;
import com.kedang.fenxiao.util.po.RecommendSwitch;
import com.kedang.fenxiao.util.po.Withdraw;
/**
 * 通用配置信息业务类
 */
@Component
@Transactional(readOnly=true)
public class CustomerConfigService {
	private Logger logger=LoggerFactory.getLogger(CustomerConfigService.class);
	@Autowired
	private CustomerConfigDao  customerConfigDao;
	@Autowired
	private FileManageService fileManageService;
	
	@Transactional
	public void updateConfig(Object config,int type){
		try {
			ObjectMapper mapper=new ObjectMapper();
			String configExp=mapper.writeValueAsString(config);
			if(StringUtils.isNotBlank(configExp)){
				customerConfigDao.updateConfig(configExp, type);
			}
		} catch (Exception e) {
			logger.error("通用配置更新失败", e);
		}
	}
	/**
	 * 查询还款周期/费率配置
	 * @return
	 */
	public CycleRate queryCycleRate(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.hkfl.getType());//
		CycleRate cr=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				cr=mapper.readValue(old.getConfigExp(), CycleRate.class);
			}
		} catch (Exception e) {
			logger.error("还款周期/费率配置json字符串解析出错", e);
		}
		return cr;
	}
	/**
	 * 保存还款周期/费率配置
	 * @param cycleRate
	 * @return
	 */
	@Transactional
	public CycleRate saveCycleRate(CycleRate cycleRate){
		CustomerConfig old=findByType(CustomerConfigType.hkfl.getType());
		ObjectMapper mapper=new ObjectMapper();
		String hkfl=null;
		try {
			 hkfl=mapper.writeValueAsString(cycleRate);
		} catch (Exception e) {
			logger.error("还款周期/费率配置json字符串解析出错", e);
			throw new ServiceException("还款周期/费率配置json字符串解析出错");
		}
		if(StringUtils.isBlank(hkfl)){
			throw new ServiceException("还款周期/费率json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(hkfl);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(hkfl);
			old.setType(CustomerConfigType.hkfl.getType());
		}
		customerConfigDao.save(old);
		return cycleRate;
	}
	/**
	 * 提现配置
	 * @param withdraw
	 * @return
	 */
	@Transactional
	public Withdraw saveWithdraw(Withdraw withdraw){
		CustomerConfig old=findByType(CustomerConfigType.tx.getType());
		ObjectMapper mapper=new ObjectMapper();
		String tx=null;
		try {
			tx=mapper.writeValueAsString(withdraw);
		} catch (Exception e) {
			logger.error("提现配置json字符串解析出错", e);
			throw new ServiceException("提现配置json字符串解析出错");
		}
		if(StringUtils.isBlank(tx)){
			throw new ServiceException("提现配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(tx);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(tx);
			old.setType(CustomerConfigType.tx.getType());
		}
		customerConfigDao.save(old);
		return withdraw;
	}
	/**
	 * 获取提现配置
	 * @return
	 */
	public Withdraw queryWithdraw(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.tx.getType());
		Withdraw withdraw=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				withdraw=mapper.readValue(old.getConfigExp(), Withdraw.class);
			}
		} catch (Exception e) {
			logger.error("提现配置json字符串解析出错", e);
			throw new ServiceException("提现配置json字符串解析出错");
		}
		return withdraw;
	}
	/**
	 * 提现配置
	 * @param withdraw
	 * @return
	 */
	@Transactional
	public Withdraw saveShopWithdraw(Withdraw withdraw){
		CustomerConfig old=findByType(CustomerConfigType.shtx.getType());
		ObjectMapper mapper=new ObjectMapper();
		String tx=null;
		try {
			tx=mapper.writeValueAsString(withdraw);
		} catch (Exception e) {
			logger.error("提现配置json字符串解析出错", e);
			throw new ServiceException("提现配置json字符串解析出错");
		}
		if(StringUtils.isBlank(tx)){
			throw new ServiceException("提现配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(tx);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(tx);
			old.setType(CustomerConfigType.shtx.getType());
		}
		customerConfigDao.save(old);
		return withdraw;
	}
	/**
	 * 获取提现配置
	 * @return
	 */
	public Withdraw queryShopWithdraw(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.shtx.getType());
		Withdraw withdraw=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				withdraw=mapper.readValue(old.getConfigExp(), Withdraw.class);
			}
		} catch (Exception e) {
			logger.error("提现配置json字符串解析出错", e);
			throw new ServiceException("提现配置json字符串解析出错");
		}
		return withdraw;
	}
	public CustomerConfig queryBillRemind(){
		//ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.yqtx.getType());
		/*BillRemind br=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				br=mapper.readValue(old.getConfigExp(), BillRemind.class);
			}
		} catch (Exception e) {
			logger.error("逾期账单提醒配置json字符串解析出错", e);
		}*/
		return old;
	}
	@Transactional
	public CustomerConfig saveBillRemind(String content){
		CustomerConfig old=findByType(CustomerConfigType.yqtx.getType());
		/*ObjectMapper mapper=new ObjectMapper();
		String hktx=null;
		try {
			hktx=mapper.writeValueAsString(billRemind);
		} catch (Exception e) {
			logger.error("逾期账单提醒配置json字符串解析出错", e);
		}
		if(StringUtils.isBlank(hktx)){
			throw new ServiceException("逾期账单提醒配置json字符串解析出错,保存失败");
		}*/
		if(old!=null){
			old.setConfigExp(content);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(content);
			old.setType(CustomerConfigType.yqtx.getType());
		}
		customerConfigDao.save(old);
		return old;
	}
	public Installment queryInstallment(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.fqfk.getType());
		Installment im=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				im=mapper.readValue(old.getConfigExp(), Installment.class);
			}
		} catch (Exception e) {
			logger.error("分期费率配置json字符串解析出错", e);
			throw new ServiceException("分期费率配置json字符串解析出错");
		}
		return im;
	}
	@Transactional
	public Installment saveInstallment(Installment installment){
		CustomerConfig old=findByType(CustomerConfigType.fqfk.getType());
		ObjectMapper mapper=new ObjectMapper();
		String fqfk=null;
		try {
			fqfk=mapper.writeValueAsString(installment);
		} catch (Exception e) {
			logger.error("分期付款费率配置json字符串解析出错", e);
			throw new ServiceException("分期付款费率配置json字符串解析出错");
		}
		if(StringUtils.isBlank(fqfk)){
			throw new ServiceException("分期付款费率配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(fqfk);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(fqfk);
			old.setType(CustomerConfigType.fqfk.getType());
		}
		customerConfigDao.save(old);
		return installment;
	}
	/**
	 * 根据配置类型查询通用配置
	 * @param type
	 * @return
	 */
	public CustomerConfig findByType(int type){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("EQ_type", type);
		Specification<CustomerConfig> spec=JpaQueryUtils.buildSpecification(CustomerConfig.class, map);
		List<CustomerConfig> list=customerConfigDao.findAll(spec);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 保存推荐人奖励配置
	 * @param recommendAward
	 * @return
	 */
	@Transactional
	public RecommendAward saveRecommendAward(RecommendAward recommendAward){
		CustomerConfig old=findByType(CustomerConfigType.tjrjl.getType());
		ObjectMapper mapper=new ObjectMapper();
		String tjrjl=null;
		try {
			tjrjl=mapper.writeValueAsString(recommendAward);
		} catch (Exception e) {
			logger.error("推荐人奖励配置json字符串解析出错", e);
			throw new ServiceException("推荐人奖励配置json字符串解析出错");
		}
		if(StringUtils.isBlank(tjrjl)){
			throw new ServiceException("推荐人奖励配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(tjrjl);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(tjrjl);
			old.setType(CustomerConfigType.tjrjl.getType());
		}
		customerConfigDao.save(old);
		return recommendAward;
	}
	/**
	 * 查询推荐人奖励配置
	 * @return
	 */
	public RecommendAward queryRecommendAward(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.tjrjl.getType());
		RecommendAward rec=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				rec=mapper.readValue(old.getConfigExp(), RecommendAward.class);
			}
		} catch (Exception e) {
			logger.error("推荐人奖励配置json字符串解析出错", e);
			throw new ServiceException("推荐人奖励配置json字符串解析出错");
		}
		return rec;
	}
	/**
	 * 保存拼拼现金面值奖励配置
	 * @param cashAward
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String,BigDecimal> addCashAward(BigDecimal amount){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.ppxjjl.getType());
		Map<String,BigDecimal> cash=null;
		if(old==null){
			String ppxjjl=null;
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setType(CustomerConfigType.ppxjjl.getType());
			cash=new HashMap<String, BigDecimal>();
			cash.put(String.valueOf(amount), amount);
			try {
				ppxjjl=mapper.writeValueAsString(cash);
			} catch (Exception e) {
				logger.error("拼拼现金面值奖励配置json字符串解析出错", e);
				throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错");
			}
			if(StringUtils.isBlank(ppxjjl)){
				throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错,保存失败");
			}
			old.setConfigExp(ppxjjl);
		}else{
			String ppxjjl=null;
			try {
				cash=mapper.readValue(old.getConfigExp(), Map.class);
				cash.put(String.valueOf(amount), amount);
				ppxjjl=mapper.writeValueAsString(cash);
			} catch (Exception e) {
				logger.error("拼拼现金面值奖励配置json字符串解析出错", e);
				throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错");
			}
			if(StringUtils.isBlank(ppxjjl)){
				throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错,保存失败");
			}
			old.setUpdateTime(new Date());
			old.setConfigExp(ppxjjl);
		}
		customerConfigDao.save(old);
		return cash;
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public Map<String,BigDecimal> deleteCashAward(String amount){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.ppxjjl.getType());
		Map<String,BigDecimal> cash=null;
		if(old==null){
			throw new ServiceException("拼拼现金面值奖励配置不存在");
		}
		try {
			cash=mapper.readValue(old.getConfigExp(), Map.class);
		} catch (Exception e) {
			logger.error("拼拼现金面值奖励配置json字符串解析出错", e);
			throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错");
		}
		if(cash==null){
			throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错,删除失败");
		}
		if(cash.containsKey(amount)){
			cash.remove(amount);
		}
		try {
			old.setConfigExp(mapper.writeValueAsString(cash));
		} catch (Exception e) {
			logger.error("拼拼现金面值奖励配置json字符串解析出错", e);
			throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错");
		}
		customerConfigDao.save(old);
		return cash;
	}
	/**
	 * 查询拼拼现金面值奖励配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,BigDecimal> queryCashAward(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.ppxjjl.getType());
		Map<String,BigDecimal> cash=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				cash=mapper.readValue(old.getConfigExp(), Map.class);
			}
		} catch (Exception e) {
			logger.error("拼拼现金面值奖励配置json字符串解析出错", e);
			throw new ServiceException("拼拼现金面值奖励配置json字符串解析出错");
		}
		return cash;
	}
	
	/**
	 * 保存钱包提现配置
	 * @param cashAward
	 * @return
	 */
	@Transactional
	public Withdraw saveWalletWithdraw(Withdraw withdraw){
		CustomerConfig old=findByType(CustomerConfigType.qbtx.getType());
		ObjectMapper mapper=new ObjectMapper();
		String qbtx=null;
		try {
			qbtx=mapper.writeValueAsString(withdraw);
		} catch (Exception e) {
			logger.error("钱包提现配置json字符串解析出错", e);
			throw new ServiceException("钱包提现配置json字符串解析出错");
		}
		if(StringUtils.isBlank(qbtx)){
			throw new ServiceException("钱包提现配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(qbtx);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(qbtx);
			old.setType(CustomerConfigType.qbtx.getType());
		}
		customerConfigDao.save(old);
		return withdraw;
	}
	/**
	 * 查询钱包提现配置
	 * @return
	 */
	public Withdraw queryWalletWithdraw(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.qbtx.getType());
		Withdraw w=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				w=mapper.readValue(old.getConfigExp(), Withdraw.class);
			}
		} catch (Exception e) {
			logger.error("钱包提现配置json字符串解析出错", e);
			throw new ServiceException("钱包提现配置json字符串解析出错");
		}
		return w;
	}
	/**
	 * 根据给定金额与分期数计算分期还款详情
	 * @param amount
	 * @param installmentNumber
	 * @return
	 */
	public Map<String,Object> calculateInstallmentDetail(BigDecimal amount,int installmentNumber){
		Map<String,Object> fqxq=new HashMap<String, Object>();
		CycleRate cycleRate=queryCycleRate();
		if(cycleRate==null){
			return null;
		}
		//每一分期的还款周期
		Integer cycle=cycleRate.getBillDay()+cycleRate.getRepayDay();
		if(cycle==0){
			return null;
		}
		Installment installment= queryInstallment();
		if(installment==null){
			return null;
		}
		if(installment.getOverdueRate()==null){
			return null;
		}
		//后台配置的总分期数
		Integer totalNumber=installment.getTotalNumber();
		if(totalNumber==null||totalNumber<=1){
			return null;
		}
		//后台配置的总分期数详情
		Map<String, InstallmentCount> installmentDetail=installment.getInstallmentDetail();
		if(installmentDetail==null){
			return null;
		}
		InstallmentCount ic=installmentDetail.get(""+installmentNumber);
		if(ic==null||ic.getRate()==null){
			return null;
		}
		try{
			BigDecimal mqfl=BigDecimalUtils.mul(amount, BigDecimalUtils.div(ic.getRate(), new BigDecimal(100),6));
			BigDecimal zje=BigDecimalUtils.add(amount, mqfl);
			BigDecimal mqje=BigDecimalUtils.div(zje, new BigDecimal(installmentNumber),2);
			//给定期数金额详情
			fqxq.put("cycle", cycle);
			fqxq.put("number", installmentNumber);
			fqxq.put("amount", mqje);
			fqxq.put("installmentInterest", mqfl);
			fqxq.put("overdue", installment.getOverdueRate());
			fqxq.put("installmentRate", ic.getRate());
		}catch(Exception e){
			logger.error("分期还款手续费计算失败",e);
			return null;
		}
		return fqxq;
	}
	/**
	 * 校验还款周期/费率配置
	 * @param cycleRate
	 */
	public void checkCycleRate(CycleRate cycleRate){
		if(cycleRate==null){
			throw new ServiceException("还款周期未配置");
		}
		if(cycleRate.getBillDay()==null||cycleRate.getBillDay()==0){
			throw new ServiceException("账期天数未配置");
		}
		if(cycleRate.getRepayDay()==null||cycleRate.getRepayDay()==0){
			throw new ServiceException("还款天数未配置");
		}
		OverdueRate overdueRate=cycleRate.getOverdueRate();
		if(overdueRate==null){
			throw new ServiceException("还款费率未配置");
		}
		if(overdueRate.getWithinDay()==null||overdueRate.getWithinDay()==0){
			throw new ServiceException("逾期天数范围未配置");
		}
		if(overdueRate.getWithinDayRate()==null||overdueRate.getWithinDayRate().equals(new BigDecimal(0))){
			throw new ServiceException("逾期天数范围费率未配置");
		}
		if(overdueRate.getIncreaseDay()==null||overdueRate.getIncreaseDay()==0){
			throw new ServiceException("逾期递增天数范围未配置");
		}
		if(overdueRate.getIncreaseDayRate()==null||overdueRate.getIncreaseDayRate().equals(new BigDecimal(0))){
			throw new ServiceException("逾期递增天数范围费率未配置");
		}
	}
	/**
	 * 校验分期账单费率配置
	 * @param installment
	 */
	public void checkInstallment(Installment installment){
		if(installment==null){
			throw new ServiceException("分期付款费率未配置");
		}
		if(installment.getMinAmount()==null||installment.getMinAmount().equals(new BigDecimal(0))){
			throw new ServiceException("最低分期额度未配置");
		}
		if(installment.getTotalNumber()==null||installment.getTotalNumber()==0){
			throw new ServiceException("分期数未配置");
		}
		OverdueRate overdueRate=installment.getOverdueRate();
		if(overdueRate==null){
			throw new ServiceException("分期帐单逾期费率未配置");
		}
		if(overdueRate.getWithinDay()==null||overdueRate.getWithinDay()==0){
			throw new ServiceException("分期帐单逾期天数范围未配置");
		}
		if(overdueRate.getWithinDayRate()==null||overdueRate.getWithinDayRate().equals(new BigDecimal(0))){
			throw new ServiceException("分期帐单逾期天数范围费率未配置");
		}
		if(overdueRate.getIncreaseDay()==null||overdueRate.getIncreaseDay()==0){
			throw new ServiceException("分期帐单逾期递增天数范围未配置");
		}
		if(overdueRate.getIncreaseDayRate()==null||overdueRate.getIncreaseDayRate().equals(new BigDecimal(0))){
			throw new ServiceException("分期帐单逾期递增天数范围费率未配置");
		}
	}
	/**
	 * 保存九宫格图片
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void saveJgg(HttpServletRequest request){
		Map<String,Object> jgg=new HashMap<String, Object>();
		for(int i=1;i<=9;i++){
			String pic=fileManageService.saveLocalFile(request, Constant.UPLOAD_JGG_FOLDER, "pic"+i);
			if(StringUtils.isNotBlank(pic)){
				jgg.put(""+i, pic);
			}
		}
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.jgg.getType());
		Map<String,Object> oldjgg=null;
		if(old==null){
			oldjgg=new HashMap<String, Object>();
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setType(CustomerConfigType.jgg.getType());
		}else{
			old.setUpdateTime(new Date());
			try {
				if(StringUtils.isNotBlank(old.getConfigExp())){
					oldjgg=mapper.readValue(old.getConfigExp(), Map.class);
				}
			} catch (Exception e) {
				logger.error("九宫格背景图片配置json字符串解析出错", e);
				throw new ServiceException("九宫格背景图片配置json字符串解析出错");
			}
		}
		if(oldjgg==null){
			throw new ServiceException("九宫格背景图片配置读取失败");
		}
		for(String key:jgg.keySet()){
			if(oldjgg.get(key)!=null){
				fileManageService.deleteLocalFile(String.valueOf(oldjgg.get(key)));
			}
			oldjgg.put(key, jgg.get(key));
		}
		String jggMap=null;
		try {
			jggMap=mapper.writeValueAsString(oldjgg);
		} catch (Exception e) {
			logger.error("九宫格背景图片配置json字符串解析出错", e);
			throw new ServiceException("九宫格背景图片配置json字符串解析出错");
		}
		if(StringUtils.isBlank(jggMap)){
			throw new ServiceException("九宫格背景图片保存失败");
		}
		old.setConfigExp(jggMap);
		customerConfigDao.save(old);
	}
	@SuppressWarnings("unchecked")
	public Map<String,Object> queryJgg(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.jgg.getType());
		Map<String,Object> jgg=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				jgg=mapper.readValue(old.getConfigExp(), Map.class);
			}
		} catch (Exception e) {
			logger.error("九宫格背景图片配置json字符串解析出错", e);
			throw new ServiceException("九宫格背景图片配置json字符串解析出错");
		}
		return jgg;
	}
	/**
	 * 保存活动时间
	 * @param hdsj
	 * @param cxsj
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public void saveHdsj(String hdsj,Integer cxsj,Integer fpcs){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.sphd.getType());
		Map<String,Object> oldSphd=null;
		if(old==null){
			oldSphd=new HashMap<String, Object>();
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setType(CustomerConfigType.sphd.getType());
		}else{
			old.setUpdateTime(new Date());
			try {
				if(StringUtils.isNotBlank(old.getConfigExp())){
					oldSphd=mapper.readValue(old.getConfigExp(), Map.class);
				}
			} catch (Exception e) {
				logger.error("碎片活动时间配置json字符串解析出错", e);
				throw new ServiceException("碎片活动时间配置json字符串解析出错");
			}
		}
		Date start=DateUtils.getFormatDateTime(DateUtils.getFormatDate(new Date(), "yyyy-MM-dd")+" "+hdsj+":00");
		Date end=DateUtils.getSpecifiedDateTimeBySeconds(new Date(start.getTime()), cxsj*60);
		String endStr=StringUtils.substring(DateUtils.getFormatDateTime(end), 11, 16);
		if(oldSphd==null){
			throw new ServiceException("碎片活动时间配置保存失败");
		}
		for(Entry<String, Object> param:oldSphd.entrySet()){
			Map<String,Object> result=(Map<String,Object>)param.getValue();
			Date s=DateUtils.getFormatDateTime(DateUtils.getFormatDate(new Date(), "yyyy-MM-dd")+" "+String.valueOf(result.get("start"))+":00");
			Date e=DateUtils.getSpecifiedDateTimeBySeconds(new Date(s.getTime()), Integer.valueOf(String.valueOf(result.get("last")))*60);
			if(!(end.before(s)||start.after(e))){
				throw new ServiceException("碎片活动时间重叠");
			}
		}
		Map<String,Object> result=new HashMap<String, Object>();
		result.put("start", hdsj);
		result.put("end", endStr);
		result.put("last", cxsj);
		result.put("count", fpcs);
		oldSphd.put(hdsj, result);
		String sphdMap=null;
		try {
			sphdMap=mapper.writeValueAsString(oldSphd);
		} catch (Exception e) {
			logger.error("碎片活动时间配置json字符串解析出错", e);
			throw new ServiceException("碎片活动时间配置json字符串解析出错");
		}
		if(StringUtils.isBlank(sphdMap)){
			throw new ServiceException("碎片活动时间配置保存失败");
		}
		old.setConfigExp(sphdMap);
		customerConfigDao.save(old);
	}
	@Transactional
	@SuppressWarnings("unchecked")
	public void deleteSphd(String key){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.sphd.getType());
		Map<String,Object> sphd=null;
		if(old==null){
			throw new ServiceException("碎片活动时间配置不存在");
		}
		try {
			sphd=mapper.readValue(old.getConfigExp(), Map.class);
		} catch (Exception e) {
			logger.error("碎片活动时间配置json字符串解析出错", e);
			throw new ServiceException("碎片活动时间配置json字符串解析出错");
		}
		if(sphd==null){
			throw new ServiceException("碎片活动时间配置json字符串解析出错,删除失败");
		}
		sphd.remove(key);
		try {
			old.setConfigExp(mapper.writeValueAsString(sphd));
		} catch (Exception e) {
			logger.error("碎片活动时间配置json字符串解析出错", e);
			throw new ServiceException("碎片活动时间配置json字符串解析出错");
		}
		customerConfigDao.save(old);
	}
	/**
	 * 碎片活动时间配置查询
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> querySphd(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.sphd.getType());
		Map<String,Object> sphd=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				sphd=mapper.readValue(old.getConfigExp(), Map.class);
			}
		} catch (Exception e) {
			logger.error("碎片活动时间配置json字符串解析出错", e);
			throw new ServiceException("碎片活动时间配置json字符串解析出错");
		}
		return sphd;
	}
	/**
	 * 碎片领取公告配置
	 * @param awardNotice
	 */
	@Transactional
	public void saveLqgg(AwardNotice awardNotice){
		CustomerConfig old=findByType(CustomerConfigType.splq.getType());
		ObjectMapper mapper=new ObjectMapper();
		String splq=null;
		try {
			splq=mapper.writeValueAsString(awardNotice);
		} catch (Exception e) {
			logger.error("碎片领取公告配置json字符串解析出错", e);
			throw new ServiceException("碎片领取公告配置json字符串解析出错");
		}
		if(StringUtils.isBlank(splq)){
			throw new ServiceException("碎片领取公告配置json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(splq);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(splq);
			old.setType(CustomerConfigType.splq.getType());
		}
		customerConfigDao.save(old);
	}
	public AwardNotice queryLqgg(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.splq.getType());
		AwardNotice splq=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				splq=mapper.readValue(old.getConfigExp(), AwardNotice.class);
			}
		} catch (Exception e) {
			logger.error("碎片领取公告配置json字符串解析出错", e);
			throw new ServiceException("碎片领取公告配置json字符串解析出错");
		}
		return splq;
	}
	@Transactional
	public CustomerConfig savePphdkg(String value){
		CustomerConfig old=findByType(CustomerConfigType.pphdkg.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.pphdkg.getType());
		}
		return 	customerConfigDao.save(old);
	}
	public CustomerConfig queryPphdkg(){
		return findByType(CustomerConfigType.pphdkg.getType());
	}
	@Transactional
	public void saveTjrjlkg(RecommendSwitch recommendSwitch){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.tjrjlkg.getType());
		String tjrjlkg=null;
		try {
			tjrjlkg=mapper.writeValueAsString(recommendSwitch);
		} catch (Exception e) {
			logger.error("推荐人活动开关配置json字符串解析出错", e);
			throw new ServiceException("推荐人活动开关配置json字符串解析出错");
		}
		if(StringUtils.isBlank(tjrjlkg)){
			throw new ServiceException("推荐人活动开关保存失败");
		}
		if(old!=null){
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setType(CustomerConfigType.tjrjlkg.getType());
		}
		old.setConfigExp(tjrjlkg);
		customerConfigDao.save(old);
	}
	public RecommendSwitch queryTjrjlkg(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.tjrjlkg.getType());
		RecommendSwitch tjrjlkg=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				tjrjlkg=mapper.readValue(old.getConfigExp(), RecommendSwitch.class);
			}
		} catch (Exception e) {
			logger.error("推荐人活动开关配置json字符串解析出错", e);
			throw new ServiceException("推荐人活动开关配置json字符串解析出错");
		}
		return tjrjlkg;
	}
	
//	/***
//	 *活动开始
//	 * @return
//	 */
//	public void pinpinGameStart(){
//		activityService.getAllRandomDistribution();
//	}
	
	/***
	 * 判断时间是否在配置时间段内，并获取翻牌次数
	 * @param date
	 * @param strDateBegin
	 * @param strDateEnd
	 * @return
	 *///0:没有配置时间。     1:-1不在游戏时间内。
//	@RequestMapping(value = "che")
	@SuppressWarnings("rawtypes")
	public String isInDate(){//Date date, String strDateBegin,String strDateEnd
		Map<String,Object> map = querySphd();
		List<Object> list = new ArrayList<Object>();
		for(Map.Entry entry:map.entrySet()){
			list.add(entry.getValue()); 
			}
		/**
		 * modify by ocean
		 * if(list.size()>1){
		 */
		if(list.size()>0){
			Date date = new Date();
			for(Object str : list){
			//System.out.println(str.toString());
			String[] strs = str.toString().split(",");
			String count = strs[0].trim().split("=")[1];
			String strDateBegin = strs[2].trim().split("=")[1];
			String strDateEnd = strs[3].trim().split("=")[1];
			if(strDateEnd.contains("}")){
				strDateEnd = strDateEnd.replace("}", "");
			}
			//System.out.println("count:"+count+"strDateBegin:"+strDateBegin+"strDateEnd:"+strDateEnd);
			if(DateUtils.isInDate(date.getTime(), strDateBegin, strDateEnd)){
				return count;
			}
			}
			return "-1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 提现审核开关
	 */
	@Transactional
	public CustomerConfig saveTxshkg(String value){
		CustomerConfig old=findByType(CustomerConfigType.txshkg.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.txshkg.getType());
		}
		return 	customerConfigDao.save(old);
	}
	
	public CustomerConfig queryTxshkg(){
		return findByType(CustomerConfigType.txshkg.getType());
	}

	/**
	 * 钱包提现审核开关
	 * @return
     */
	public CustomerConfig queryQbtxshkg(){
		return findByType(CustomerConfigType.qbtxshkg.getType());
	}

	/**
	 * 保存钱包提现审核开关
	 * @return
	 */
	@Transactional
	public CustomerConfig saveQbtxshkg(String value){
		CustomerConfig old=findByType(CustomerConfigType.qbtxshkg.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.qbtxshkg.getType());
		}
		return 	customerConfigDao.save(old);
	}
	/**
	 * 学籍认证开关
	 */
	@Transactional
	public CustomerConfig saveXjrzkg(String value){
		CustomerConfig old=findByType(CustomerConfigType.xjrzkg.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.xjrzkg.getType());
		}
		return 	customerConfigDao.save(old);
	}
	
	/**
	 * 学信网认证自动审核开关
	 */
	@Transactional
	public CustomerConfig saveXxwrzzdsh(String value){
		//根据配置类型查询通用配置，如果有数据测修改数据，如果没有则新增数据
		CustomerConfig old=findByType(CustomerConfigType.xxwrzzdsh.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.xxwrzzdsh.getType());
		}
		return 	customerConfigDao.save(old);
	}
	
	public CustomerConfig queryXjrzkg(){
		return findByType(CustomerConfigType.xjrzkg.getType());
	}
	
	public CustomerConfig queryXxwrzzdsh(){
		return findByType(CustomerConfigType.xxwrzzdsh.getType());
	}
	
	public CustomerConfig queryDxqh(){
		return findByType(CustomerConfigType.dxqh.getType());
	}
	
//	/**
//	 * 保存学籍认证额度配置
//	 * @return
//	 */
//	@Transactional
//	public CustomerConfig saveXjrztghed(String xjrztghed){
//		CustomerConfig old=findByType(CustomerConfigType.xjrzedpz.getType());
//		if(old!=null){
//			old.setConfigExp(xjrztghed);
//			old.setUpdateTime(new Date());
//		}else{
//			old=new CustomerConfig();
//			old.setCreateTime(new Date());
//			old.setUpdateTime(old.getCreateTime());
//			old.setConfigExp(xjrztghed);
//			old.setType(CustomerConfigType.xjrzedpz.getType());
//		}
//		customerConfigDao.save(old);
//		return old;
//	}
//	
//	/**
//	 * 查询学籍认证额度配置
//	 * @return
//	 */
//	public CustomerConfig queryXjrztghed(){
//		return findByType(CustomerConfigType.xjrzedpz.getType());
//	}
//	
//	public static void main(String[] args) throws JSONException {
//		List<String> l = new ArrayList<String>();
//		for(int i=0; i<3; i++) {
//			JSONObject josnObj = new JSONObject();
//			josnObj.put("a", 1);
//			josnObj.put("b", "4");
//			l.add(josnObj.toString());
//		}
//		
//		System.out.println(l.toString());
//	}
	
	/**
	 * 任务额度配置
	 * @throws JSONException 
	 */
	@Transactional
	public CustomerConfig saveRwedpz(String list) throws JSONException{
		CustomerConfig old=findByType(CustomerConfigType.rwedpz.getType());
		List<String> rws = new ArrayList<String>();
		String[] lists = list.split(";");
		JSONObject josnObj = new JSONObject();
		for (int i = 0; i < lists.length; i++)
		{
			String[] rw = lists[i].split(":");
			josnObj.put("taskType", rw[0]);
			josnObj.put("taskName", rw[1]);
			josnObj.put("taskMoney", rw[2]);
			rws.add(josnObj.toString());
		}
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(rws.toString());
		}else{
			old=new CustomerConfig();
			old.setConfigExp(rws.toString());
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.rwedpz.getType());
		}
		return 	customerConfigDao.save(old);
	}
	

	/**
	 * 查询提现自动审核参数配置
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> queryTxzdshcspz(int zdshcsType )
	{
		List<Map> list = new ArrayList<Map>();
		try
		{
			CustomerConfig customerConfig = findByType(zdshcsType);
			if(null!=customerConfig && StringUtils.isNotBlank(customerConfig.getConfigExp())){
				JSONArray jsonArray = new JSONArray(customerConfig.getConfigExp());
				for(int i=0; i<jsonArray.length(); i++){
					//获取每一个JsonObject对象
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					Integer paraType = new Integer(jsonObj.getInt("paraType"));
					Integer paraValue = new Integer(jsonObj.getInt("paraValue"));
					String paraName = jsonObj.getString("paraName");
					Map map = new HashMap();
					map.put("paraType",paraType);
					map.put("paraValue",paraValue);
					map.put("paraName",paraName);
					list.add(map);
				}
			}
			return list;
		}
		catch (Exception e)
		{
			logger.error("查询提现自动审核参数配置, 异常:" + e.getCause());
			throw new ServiceException("查询提现自动审核参数配置, 异常");
		}
	}

	/**
	 * 保存提现自动审核参数配置
	 * @throws JSONException
	 */
	@Transactional
	public CustomerConfig saveTxzdshcspz(String list,int txzdshcsType) throws JSONException{
		CustomerConfig old=findByType(txzdshcsType);
		List<String> rws = new ArrayList<String>();
		String[] lists = list.split(";");
		JSONObject josnObj = new JSONObject();
		for (int i = 0; i < lists.length; i++)
		{
			String[] rw = lists[i].split(":");
			josnObj.put("paraType", rw[0]);
			josnObj.put("paraName", rw[1]);
			josnObj.put("paraValue", rw[2]);
			rws.add(josnObj.toString());
		}
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(rws.toString());
		}else{
			old=new CustomerConfig();
			old.setConfigExp(rws.toString());
			old.setCreateTime(new Date());
			old.setType(txzdshcsType);
		}
		return 	customerConfigDao.save(old);
	}
	/**
	 * 账单延期参数配置保存
	 */
	@Transactional
	public CustomerConfig saveZdyqpz(String yqcs, String sxf)
	{
		try
		{
			CustomerConfig old=findByType(CustomerConfigType.zdyq.getType());
			JSONObject josnObj = new JSONObject();
			josnObj.put("delayRate", sxf);
			josnObj.put("delayName", "账单延期服务购买");
			josnObj.put("delayTimes", yqcs);
			josnObj.put("delayType", CustomerConfigType.zdyq.getType());
			
			if(old!=null){
				old.setUpdateTime(new Date());
				old.setConfigExp(josnObj.toString());
			}else{
				old=new CustomerConfig();
				old.setConfigExp(josnObj.toString());
				old.setCreateTime(new Date());
				old.setType(CustomerConfigType.zdyq.getType());
			}
			return 	customerConfigDao.save(old);
		}
		catch (Exception e)
		{
			logger.error("保存账单延期信息异常", e);
		}
		return null;
	}
	
	/**
	 * 查询账单延期配置
	 * @return
	 */
	public Delay queryDelayConfig(){
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.zdyq.getType());
		Delay delay=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				delay=mapper.readValue(old.getConfigExp(), Delay.class);
			}
		} catch (Exception e) {
			logger.error("钱包提现配置json字符串解析出错", e);
			throw new ServiceException("钱包提现配置json字符串解析出错");
		}
		return delay;
	}
	
	/**
	 * 学信网认证自动审核开关
	 */
	@Transactional
	public CustomerConfig saveDxqh(String value){
		//根据配置类型查询通用配置，如果有数据测修改数据，如果没有则新增数据
		CustomerConfig old=findByType(CustomerConfigType.dxqh.getType());
		if(old!=null){
			old.setUpdateTime(new Date());
			old.setConfigExp(value);
		}else{
			old=new CustomerConfig();
			old.setConfigExp(value);
			old.setCreateTime(new Date());
			old.setType(CustomerConfigType.dxqh.getType());
		}
		return 	customerConfigDao.save(old);
	}
	
	/**
	 * 短信切换配置
	 * @return
	 */
	public String queryDxqhpz(){
		CustomerConfig old=findByType(CustomerConfigType.dxqh.getType());
		String rec = null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				rec=old.getConfigExp();
			}
		} catch (Exception e) {
			logger.error("短信切换配置查询出错", e);
			throw new ServiceException("短信切换配置查询出错");
		}
		return rec;
	}
	
	/**
	 * 查询卡牌等活动配置
	 * @return
	 */
	public ActivityConfiguration queryCardactivity()
	{
		ObjectMapper mapper=new ObjectMapper();
		CustomerConfig old=findByType(CustomerConfigType.cardactivity.getType());
		ActivityConfiguration activityConfiguration=null;
		try {
			if(old!=null&&StringUtils.isNotBlank(old.getConfigExp())){
				activityConfiguration=mapper.readValue(old.getConfigExp(), ActivityConfiguration.class);
			}
		} catch (Exception e) {
			logger.error("钱包提现配置json字符串解析出错", e);
			throw new ServiceException("钱包提现配置json字符串解析出错");
		}
		return activityConfiguration;
	}
	
	/**
	 * 活动等配置
	 */
	@Transactional
	public ActivityConfiguration saveActivityTotal(String title,
			String prepareStartTime, String prepareEndTime, String startTime,
			String endTime, String closeTime,String imgUrls, String activityRemark,
			String gameRemark, String actionUrl, String endActionUrl,
			String actionParas, String list){
		CustomerConfig old=findByType(CustomerConfigType.cardactivity.getType());
		ObjectMapper mapper=new ObjectMapper();
		List<PrizeConfiguration> prizeConfigurations = new ArrayList<PrizeConfiguration>();
		String[] lists = list.split(";");
		if(StringUtils.isNotBlank(lists[0])){
			for (int i = 0; i < lists.length; i++)
			{
				PrizeConfiguration prizeConfiguration = new PrizeConfiguration();
				String[] prize = lists[i].split(":");
				prizeConfiguration.setName(prize[1]);
				prizeConfiguration.setType(Integer.valueOf(prize[2]));
				prizeConfiguration.setNumber(Integer.valueOf(prize[3]));
				prizeConfiguration.setEverydayNum(Integer.valueOf(prize[4]));
				prizeConfiguration.setProbability(Double.valueOf(prize[5]));
				prizeConfiguration.setPrizeId(Integer.valueOf(prize[6]));
				prizeConfigurations.add(prizeConfiguration);
			}
		}
		
		ActivityConfiguration activityConfiguration = new ActivityConfiguration();
		activityConfiguration.setTitle(title);
		activityConfiguration.setPrepareStartTime(DateUtils.getFormatDate(prepareStartTime,"yyyy-MM-dd HH:mm:ss"));
		activityConfiguration.setPrepareEndTime(DateUtils.getFormatDate(prepareEndTime,"yyyy-MM-dd HH:mm:ss"));
		activityConfiguration.setStartTime(DateUtils.getFormatDate(startTime,"yyyy-MM-dd HH:mm:ss"));
		activityConfiguration.setEndTime(DateUtils.getFormatDate(endTime,"yyyy-MM-dd HH:mm:ss"));
		activityConfiguration.setCloseTime(DateUtils.getFormatDate(closeTime,"yyyy-MM-dd HH:mm:ss"));
		String[] imgUrl = imgUrls.split(",");
		activityConfiguration.setImgUrls(Arrays.asList(imgUrl));
		activityConfiguration.setActivityRemark(activityRemark);
		activityConfiguration.setGameRemark(gameRemark);
		activityConfiguration.setActionUrl(actionUrl);
		activityConfiguration.setEndActionUrl(endActionUrl);
		activityConfiguration.setActionParas(actionParas);
		activityConfiguration.setPrizeConfiguration(prizeConfigurations);
		String hdpz=null;
		try {
			hdpz=mapper.writeValueAsString(activityConfiguration);
		} catch (Exception e) {
			logger.error("活动等配置json字符串解析出错", e);
			throw new ServiceException("活动等配置json字符串解析出错");
		}
		if(StringUtils.isBlank(hdpz)){
			throw new ServiceException("活动等json字符串解析出错,保存失败");
		}
		if(old!=null){
			old.setConfigExp(hdpz);
			old.setUpdateTime(new Date());
		}else{
			old=new CustomerConfig();
			old.setCreateTime(new Date());
			old.setUpdateTime(old.getCreateTime());
			old.setConfigExp(hdpz);
			old.setType(CustomerConfigType.cardactivity.getType());
		}
		customerConfigDao.save(old);
		return activityConfiguration;
	}

	/**
	 * 查找消息模板
	 * @return
	 * @throws JSONException
	 */
	public List<MessageModel> findMessageModel() {
		List<MessageModel> messageModels = null;
		CustomerConfig customerConfig = findByType(CustomerConfigType.messagemodel
				.getType());
		JSONArray js = null;
		if (null != customerConfig && null != customerConfig.getConfigExp()) {
			try {
				js = new JSONArray(customerConfig.getConfigExp());
				messageModels = new ArrayList<MessageModel>();
				for (int i = 0; i < js.length(); i++) {
					JSONObject j = js.getJSONObject(i);
					if (null != j) {
						if (j.has("id") && j.has("name") && j.has("content")) {
							MessageModel messageModel = new MessageModel();
							messageModel.setId(Integer.parseInt(j
									.optString("id")));
							messageModel.setName(j.optString("name"));
							messageModel.setContent(j.optString("content"));
							messageModels.add(messageModel);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(js);
		}
		return messageModels;
	}
	/**
	 * 根据id查找消息模板
	 * @return
	 * @throws JSONException
	 */
	public String findMessageModel(Integer id) {
		CustomerConfig customerConfig = findByType(CustomerConfigType.messagemodel
				.getType());
		JSONArray js = null;
		if (null != customerConfig && null != customerConfig.getConfigExp()) {
			try {
				js = new JSONArray(customerConfig.getConfigExp());
				for (int i = 0; i < js.length(); i++) {
					JSONObject j = js.getJSONObject(i);
					if (null != j) {
						if (j.has("id") && j.has("name") && j.has("content")
								&& j.optInt("id") == id) {
							return j.optString("content");
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(js);
		}
		return null;
	}

	/**
	 * 根据id查找消息模板
	 * @return
	 * @throws JSONException
	 */
	public MessageModel findMessageById(Integer id)
	{
		CustomerConfig customerConfig = findByType(CustomerConfigType.messagemodel.getType());
		MessageModel messageModel = null;
		JSONArray js = null;

		if (null != customerConfig && null != customerConfig.getConfigExp())
		{
			try
			{
				js = new JSONArray(customerConfig.getConfigExp());
				for (int i = 0; i < js.length(); i++)
				{
					JSONObject j = js.getJSONObject(i);
					if (null != j)
					{
						if (j.has("id") && j.has("name") && j.has("content") && j.optInt("id") == id)
						{
							messageModel = new MessageModel();
							messageModel.setId(j.optInt("id"));
							messageModel.setName(j.optString("name"));
							messageModel.setContent(j.optString("content"));

						}
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		return messageModel;
	}
	/**
	 * 消息模板配置管理
	 * @return
	 */
	public List<MessageModel> queryXxmbpz()
	{
		List<MessageModel> list = new ArrayList<MessageModel>();
		try
		{
			CustomerConfig customerConfig = findByType(CustomerConfigType.messagemodel.getType());
			if(null!=customerConfig && StringUtils.isNotBlank(customerConfig.getConfigExp())){
				JSONArray jsonArray = new JSONArray(customerConfig.getConfigExp());
				for(int i=0; i<jsonArray.length(); i++){
					//获取每一个JsonObject对象
				    JSONObject jsonObj = jsonArray.getJSONObject(i);
				    Integer id = new Integer(jsonObj.getInt("id"));
				    String name = new String(jsonObj.getString("name"));
				    String content = new String(jsonObj.getString("content"));
				    MessageModel messageModel = new MessageModel();
				    messageModel.setId(null==id ? -1 : id);
				    messageModel.setName(StringUtils.isBlank(name) ? "" : name);
				    messageModel.setContent(StringUtils.isBlank(content) ? "" : content);
					list.add(messageModel);
				}
			}
			return list;
		}
		catch (Exception e)
		{
			logger.error("查询消息模板配置管理异常, 异常:" + e.getCause());
			throw new ServiceException("查询消息模板配置管理异常, 异常");
		}
	}
	
	/**
	 * 保存消息模板配置
	 */
	@Transactional
	public CustomerConfig saveMessageModel(String list){
		CustomerConfig old=findByType(CustomerConfigType.messagemodel.getType());
		try {
			List<String> rws = new ArrayList<String>();
			String[] lists = list.split(";");
			JSONObject josnObj = new JSONObject();
			if (StringUtils.isNotBlank(lists[0])) {
				for (int i = 0; i < lists.length; i++)
				{
					String[] rw = lists[i].split("@");
					josnObj.put("id", rw[0]);
					josnObj.put("name", rw[1]);
					josnObj.put("content", rw[2]);
					rws.add(josnObj.toString());
				}
			}
			if(old!=null){
				old.setUpdateTime(new Date());
				old.setConfigExp(rws.toString());
			}else{
				old=new CustomerConfig();
				old.setConfigExp(rws.toString());
				old.setUpdateTime(new Date());
				old.setType(CustomerConfigType.messagemodel.getType());
				old.setCreateTime(new Date());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 	customerConfigDao.save(old);
	}
}
