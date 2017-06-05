package com.kedang.fenxiao.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXWaitValidateRecord;
import com.kedang.fenxiao.util.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kedang.fenxiao.entity.FXCami;
import com.kedang.fenxiao.entity.FXHandWorkFlow;
import com.kedang.fenxiao.repository.FXHandWorkFlowDao;
import com.kedang.fenxiao.service.jiangsukami.JiangSuKaMiFlow;
import com.kedang.fenxiao.service.jiangsukami.JiangSuKaMiResponse;
import com.kedang.fenxiao.util.enums.CamiEnums;
import com.kedang.fenxiao.util.enums.OrderStatus;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ResultDo;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;


import static com.kedang.fenxiao.util.Constant.local_flow;

/**
 * Created by gegongxian on 16/10/23.
 */
@Component
public class FlowPayService
{
	private static final Logger logger = LoggerFactory.getLogger(FlowPayService.class);
	@Autowired
	private CamiService camiService;
	@Autowired
	private FXHandWorkFlowDao fxHandWorkFlowDao;
	@Autowired
	private  FXEnterpriseService enterpriseService;
	@Autowired
	private  FXWaitValidateRecordService fxWaitValidateRecordService;

	@Value("${handWork.gascard.mid}")
	private String gascard_pay_mid;
	@Value("${handWork.flow.mid}")
	private String flow_mid;
	@Value("${handWork.bill.mid}")
	private String bill_mid;

	@Transactional(readOnly = false)
	public ResultDo flowPayCaMi(String phone, FXCami fxcami)
	{
		String ticketNum = fxcami.getCamiStr();
		//参数效验
		if (StringUtils.isBlank(phone) || StringUtils.isBlank(ticketNum))
		{
			return ResultFactory.getFailedResult("手机号或卡密不能为空");
		}
		//充值
		try
		{
			JiangSuKaMiResponse _jiangSuKaMiResponse = JiangSuKaMiFlow.flowPay(phone, ticketNum);
			if (null == _jiangSuKaMiResponse)
			{
				return ResultFactory.getFailedResult("手工充值异常");
			}
			int status = -1;
			//解析充值结果
			/**
			 * ：{"TSR_RESULT":"0","TSR_CODE":"0","TSR_MSG":"订购成功","olId":"10201610210097886438","orderResult":"完成","result":"0"}
			 * 成功与失败都要设置上游下游状态，缺一不可
			 */
			if ("0".equals(_jiangSuKaMiResponse.getTSR_RESULT()))
			{
				//成功，卡密设置已使用
				status = OrderStatus.cg.getType();
				fxcami.setStatus(CamiEnums.ysy.getType());
				fxcami.setUpdateTime(new Date());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.ysy.getType())
				{
					logger.info(phone + "=====卡密更新 [已使用] 状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新 [已使用] 状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
			}
			else if ("100005".equals(_jiangSuKaMiResponse.getTSR_RESULT()))
			{
				//{"TSR_RESULT":"100005","TSR_MSG":"抱歉，本活动仅限电信天翼手机用户参与，感谢您的关注。"}
				// 失败：上游下游状态均设置
				//失败
				status = OrderStatus.sb.getType();
				fxcami.setUpdateTime(new Date());
				fxcami.setStatus(CamiEnums.wsy.getType());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.wsy.getType())
				{
					logger.info(phone + "=====卡密更新 [未使用] 状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新 [未使用] 状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
			}
			else if ("10003".equals(_jiangSuKaMiResponse.getTSR_RESULT()))
			{
				//{"TSR_RESULT":"10003","TSR_MSG":"您输入的券号已使用，请输入未使用的电子券号，谢谢！"}
				//卡密不存在、卡密已使用，本轮结束稍后重试
				//无效，卡密设置无效
				//失败
				status = OrderStatus.sb.getType();
				fxcami.setStatus(CamiEnums.wx.getType());
				fxcami.setUpdateTime(new Date());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.wx.getType())
				{
					logger.info(phone + "=====卡密更新 [无效] 状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新 [已使用] 状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				logger.error("======充值到[江苏卡密][卡密不存在、卡密已使用]，稍后重试=========手机号[" + phone + "],卡密：[" + ticketNum
						+ "]======");

			}
			else if ("BSS_FAILURE_3001".equals(_jiangSuKaMiResponse.getTSR_RESULT() + ""))
			{
				//{"TSR_RESULT":"BSS_FAILURE_3001","TSR_CODE":"BSS_FAILURE_3001","TSR_MSG":"已停机或者账号有欠费产品","olId":"17201610220053312641","orderResult":"已停机或者账号有欠费产品","result":"1"}
				//失败
				status = OrderStatus.sb.getType();
				fxcami.setUpdateTime(new Date());
				fxcami.setStatus(CamiEnums.wsy.getType());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.wsy.getType())
				{
					logger.info(phone + "=====卡密更新状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
			}
			else if ("ZT_REPEAT_120".equals(_jiangSuKaMiResponse.getTSR_RESULT() + ""))
			{
				//{"TSR_RESULT":"ZT_REPEAT_120","TSR_MSG":"in process,Hold,please"}
				//失败
				status = OrderStatus.sb.getType();
				fxcami.setUpdateTime(new Date());
				fxcami.setStatus(CamiEnums.wsy.getType());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.wsy.getType())
				{
					logger.info(phone + "=====卡密更新状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
			}else if("999".equals(_jiangSuKaMiResponse.getTSR_RESULT() + ""))
			{
				status = OrderStatus.sb.getType();
				fxcami.setUpdateTime(new Date());
				fxcami.setStatus(CamiEnums.wsy.getType());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.wsy.getType())
				{
					logger.info(phone + "=====卡密更新状态成功=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
				else
				{
					logger.error(phone + "=====卡密更新状态失败=====camiStr[" + fxcami.getCamiStr() + "],status["
							+ fxcami.getStatus());
				}
			}
			//设置错误代码与错误信息
			FXHandWorkFlow flow = new FXHandWorkFlow();
			flow.setPhone(phone);
			flow.setCamiStr(fxcami.getCamiStr());
			flow.setStatus(status);
			flow.setErrorCode(_jiangSuKaMiResponse.getTSR_CODE());
			flow.setErrorMsg(_jiangSuKaMiResponse.getTSR_MSG());
			flow.setSize(fxcami.getSize());
			flow.setFlowType(fxcami.getFlowType());
			flow.setCreateTime(new Date());
			flow.setUpdateTime(new Date());
			flow = fxHandWorkFlowDao.save(flow);
			logger.info("=====保存手工充值记录====id[" + flow.getId() + "]====");
			if (null == flow || flow.getStatus() == OrderStatus.sb.getType())
			{
				return ResultFactory.getFailedResult("充值失败");
			}
			else
			{
				return ResultFactory.getSuccessResult(flow);
			}
		}
		catch (ServiceException e)
		{
			return ResultFactory.getFailedResult("手工充值异常：" + e.getMessage());
		}
	}

	  /**
     * 查询手工充值记录
     *
     * @param params
     * @param pageable
     * @return
     */
    public Page<FXHandWorkFlow> findAllFXHandWorkFlow(Map<String, Object> params, Pageable pageable) {

        return fxHandWorkFlowDao.findAll(
                JpaQueryUtils.buildSpecification(FXHandWorkFlow.class, params), pageable);
    }

	/**
	 * 加油卡手工充值
	 * @param size
	 * @param buyNum
	 * @param gasCardAccount
	 * @return
	 */
	public boolean submitOrder(String size, String buyNum, String gasCardAccount,String description)
	{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		logger.info("操作人：" + JSON.toJSONString(shiroUser));
		FXWaitValidateRecord fxWaitValidateRecord = saveFXWaitValidateRecord(gascard_pay_mid, Constant.GAS_CARD_TYPE + "", buyNum,
				Constant.other_flow, size, gasCardAccount, shiroUser.getName(),description);
		if (null == fxWaitValidateRecord)
		{
			return false;
		}
		return true;
	}


	/**
	 * 流量手工充值
	 * @param size
	 * @param flowType
	 * @param flowAccount
	 * @param description
	 * @return
	 */
	public boolean flowOrder(String size, String flowType, String flowAccount,String description) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		logger.info("操作人：" + JSON.toJSONString(shiroUser));
		FXWaitValidateRecord fxWaitValidateRecord = saveFXWaitValidateRecord(flow_mid, Constant.FLOW_TYPE + "", Constant.STRING_YES,
				Integer.valueOf(flowType), size, flowAccount, shiroUser.getName(),description);
		if (null == fxWaitValidateRecord)
		{
			return false;
		}
		return true;
	}

	/**
	 * 话费手工充值
	 * @param size
	 * @param flowType
	 * @param billAccount
	 * @param description
	 * @return
	 */
	public boolean billOrder(String size, String flowType, String billAccount, String description)
	{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		logger.info("操作人：" + JSON.toJSONString(shiroUser));
		FXWaitValidateRecord fxWaitValidateRecord = saveFXWaitValidateRecord(bill_mid, Constant.BILL_TYPE + "", Constant.STRING_YES,
				Constant.ROAM_flow, size, billAccount, shiroUser.getName(), description);
		if (null == fxWaitValidateRecord)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * 效验流量限额
	 * @return
	 */
	public void validateFlowCrestValue(String amount, String[] accountValue)
	{
		//流量
		FXEnterprise enterprise = enterpriseService.findEnterpriseByMid(flow_mid);
		if (enterprise == null)
		{
			throw new ServiceException("商户不存在");
		}
		//判断支付金额是否超限
		int balance = enterprise.getBalance().intValue() / 1000;
		int value = accountValue.length * Integer.valueOf(amount);
		if (value > balance)
		{
			throw new ServiceException("商户余额不足");
		}
	}

	/**
	 * 效验加油卡限额
	 * @return
	 */
	public void validateCardCrestValue(String amount, String buyNum, String[] accountValue)
	{
		//流量
		FXEnterprise enterprise = enterpriseService.findEnterpriseByMid(gascard_pay_mid);
		if (enterprise == null)
		{
			throw new ServiceException("商户不存在");
		}
		//判断支付金额是否超限
		int balance = enterprise.getBalance().intValue() / 1000;
		int value = accountValue.length * Integer.valueOf(buyNum) * Integer.valueOf(amount);
		if (value > balance)
		{
			throw new ServiceException("商户余额不足");
		}
	}
	/**
	 * 效验话费限额
	 * @return
	 */
	public void validateBillCrestValue(int businessType,String amount, String buyNum, String[] accountValue)
	{
		if (businessType == 0)
		{
			//流量
			FXEnterprise enterprise = enterpriseService.findEnterpriseByMid(bill_mid);
			if (enterprise == null)
			{
				throw new ServiceException("商户不存在");
			}
			//判断支付金额是否超限
			int balance = enterprise.getBalance().intValue()/1000;
			int value =  accountValue.length*Integer.valueOf(buyNum)*Integer.valueOf(amount);
			if(value>balance){
				throw new ServiceException("商户余额不足");
			}
		}
	}

	/**
	 * 保存待审核表
	 * @param mid
	 * @param businessType
	 * @param buyNum
	 * @param flowType
	 * @param size
	 * @param phone
	 * @param applyOperator
	 */
	public FXWaitValidateRecord saveFXWaitValidateRecord(String mid, String businessType, String buyNum, Integer flowType,
			String size, String phone,  String applyOperator,String description)
	{
		logger.info("=====FlowPayService.saveFXWaitValidateRecord 保存待审核纪录 start mid:" + mid + ",businessType:" + businessType
				+ ",buyNum:" + buyNum + ",flowType:" + flowType + ",size:" + size + ",phone:" + phone
				+ ",applyOperator:" + applyOperator + "====");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());// 日期
		String snum = mid + date + (Math.abs(new Random().nextLong()) + "").substring(0, 6);
		logger.info("订单号:" + snum + ",加油卡充值帐号:");
		FXWaitValidateRecord fxWaitValidateRecord = new FXWaitValidateRecord();
		FXEnterprise enterprise = enterpriseService.findEnterpriseByMid(mid);
		fxWaitValidateRecord.setEnterprise(enterprise);
		fxWaitValidateRecord.setBusinessType(Integer.valueOf(businessType));
		fxWaitValidateRecord.setBuyNum(buyNum);
		fxWaitValidateRecord.setDemo("17767213541");
		fxWaitValidateRecord.setFlowType(flowType);
		fxWaitValidateRecord.setSize(size);
		fxWaitValidateRecord.setPhone(phone);
		fxWaitValidateRecord.setHandWork(Constant.STRING_YES);//1 手工
		fxWaitValidateRecord.setApplyOperator(applyOperator);//申请人
		fxWaitValidateRecord.setCheckOperator("");//审核人
		fxWaitValidateRecord.setSnum(snum);//订单号
		fxWaitValidateRecord.setDescription(description);//描述
		fxWaitValidateRecord.setStatus(Constant.INT_NO);//0 初始值
		fxWaitValidateRecord.setNotifyUrl("http://localhost:8080");// 下游回调地址
		fxWaitValidateRecord.setCreateTime(DateUtils.getCurrDate());
		fxWaitValidateRecord.setUpdateTime(DateUtils.getCurrDate());
		fxWaitValidateRecord = fxWaitValidateRecordService.save(fxWaitValidateRecord);
		if (null != fxWaitValidateRecord)
		{
			logger.info("=====FlowPayService.saveFXWaitValidateRecord end 保存待审核纪录成功=====" + new Gson().toJson(fxWaitValidateRecord));
		}
		return fxWaitValidateRecord;
	}
}
