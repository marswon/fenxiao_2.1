package com.kedang.fenxiao.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSON;
import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXFeeHandle;
import com.kedang.fenxiao.entity.FXFoundsFlow;
import com.kedang.fenxiao.entity.FXRecharge;
import com.kedang.fenxiao.service.AddBalanceService;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.FXFeeHandleService;
import com.kedang.fenxiao.service.FinancialStatementsService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.enums.FeeType;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "financialManagement")
public class FinancialManagementController
{
	private Logger logger = LoggerFactory.getLogger(FinancialManagementController.class);

	@Autowired
	private AddBalanceService addBalanceService;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private FinancialStatementsService financialStatementService;
	@Autowired
	private FinancialStatementsService financialStatementsService;
	@Autowired
	private FXFeeHandleService fxFeeHandleService;

	/*
	 * 跳转到加款记录页面
	 */
	@RequestMapping(value = "toFinancialManageMent")
	public String toFinancialManageMent()
	{
		return "financial_management/rechargeManage";
	}

	/*
	 * 跳转到财务报表页面
	 */
	@RequestMapping(value = "toFinancialStatements")
	public String toFinancialStatements()
	{
		return "financial_management/financialStatements";
	}

	/*
	 * 跳转到下游报表页面
	 */
	@RequestMapping(value = "toDownstreamFinancialStatements")
	public String toDownstreamFinancialStatements()
	{
		return "financial_management/downstreamFinancialStatements";
	}

	/**
	 * 分页查询分销商列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAddBalanceList")
	public Page<FXRecharge> getAddBalanceList(HttpServletRequest request, String startTime, String endTime, int page,
			int rows)
	{
		Page<FXRecharge> pageList = null;

		try
		{
			logger.info("====== start AddBalanceController.getAddBalanceList ======req[startTime=" + startTime
					+ ",endTime=" + endTime);
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_submitTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_submitTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			//多条件排序 
			Sort sort = new Sort(Sort.Direction.DESC, "submitTime").and(new Sort(Sort.Direction.ASC, "addBalanceType"));
			pageList = addBalanceService.findAllAddBalance(searchParams, new PageRequest(page - 1, rows, sort));
			logger.info("====== end AddBalanceController.getAddBalanceList ,res[_listFxRecharge=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("AddBalanceController.getAddBalanceList error[" + e.getCause() + "]");
		}
		return pageList;
	}

	@ResponseBody
	@RequestMapping(value = "updateRechargeStatus")
	public synchronized ResultDo updateRechargeStatus(String id)
	{
		logger.info("====start FinancialManagementController.updateRechargeStatus====id:" + id);
		try
		{
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			FXRecharge fxRecharge = addBalanceService.findById(id);
			if (null != fxRecharge && fxRecharge.getStatus() != 0)
			{
				logger.error("该记录已经被处理！id:" + id);
				return ResultFactory.getFailedResult("改记录已经被处理！");
			}
			//加款
			if (fxRecharge.getAddBalanceType() == 1)
			{
				//获取此客户授信未还清的记录
				List<FXRecharge> debtRechargeList = addBalanceService.getRechargeWithDebt(fxRecharge.getFxEnterprise()
						.getId());
				logger.info("未还清的授信记录为：" + JSON.toJSONString(debtRechargeList));
				long addAmount = fxRecharge.getAmount();
				Map<Long, Long> debtOffsetMap = new HashMap<Long, Long>();
				Map<FXRecharge, Long> debtOffsetRechargeMap = new HashMap<FXRecharge, Long>();
				for (FXRecharge recharge : debtRechargeList)
				{
					//如果充值金额比此笔订单债务多，则此单债务全部抵消，否则抵消充值金额值
					if (addAmount >= recharge.getDebt())
					{
						debtOffsetMap.put(recharge.getId(), recharge.getDebt());
						debtOffsetRechargeMap.put(recharge, recharge.getDebt());
						addAmount = addAmount - recharge.getDebt();
					}
					else
					{
						debtOffsetMap.put(recharge.getId(), addAmount);
						debtOffsetRechargeMap.put(recharge, addAmount);
						addAmount = 0;
					}
					if (addAmount == 0)
					{
						break;
					}
				}

				fxRecharge.setConfirmTime(new Date());
				Long before = fxRecharge.getFxEnterprise().getBalance();
				fxRecharge.setBeforeRechargeBalance(0);
				fxRecharge.setAfterRechargeBalance(0);
				//更新商户余额
				//				FXEnterprise fxEnterprise = fxRecharge.getFxEnterprise();
				//				fxEnterprise.setBalance(1500L);
				fxRecharge.setFxEnterprise(null);
				shiroUser.getId();
				AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
				fxRecharge.setConfirmUser(admin);
				fxRecharge.setStatus(1);
				//				FXRecharge recharge = addBalanceService.saveRecharge(fxRecharge);

				//生成fee_handle记录
				Date confirmDate = new Date();
				FXFeeHandle feeHandle = new FXFeeHandle();
				feeHandle.setAmount(addAmount);
				feeHandle.setMillisecond(confirmDate.getTime());
				feeHandle.setOrderRecordkey(fxRecharge.getId() + "");
				feeHandle.setType(FeeType.ENTERPRISE_ADD_PRICE.getType());
				feeHandle.setWriteTime(confirmDate);
				fxFeeHandleService.save(feeHandle);

				//更新授信记录债务
				for (Long rechargeId : debtOffsetMap.keySet())
				{
					addBalanceService.updateRechargeDebt(debtOffsetMap.get(rechargeId), rechargeId);
				}
				//生成授信抵消记录
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String lineSeparator = System.getProperty("line.separator");
				for (FXRecharge recharge : debtOffsetRechargeMap.keySet())
				{
					FXRecharge tempRecharge = fxRecharge.clone();
					tempRecharge.setId(null);
					tempRecharge.setDebt(0);
					tempRecharge.setAmount(debtOffsetRechargeMap.get(recharge));
					tempRecharge.setBeforeRechargeBalance(before);
					tempRecharge.setAfterRechargeBalance(before);
					tempRecharge.setDescription(tempRecharge.getDescription() + lineSeparator + " 备注：抵消"
							+ sdf.format(recharge.getSubmitTime()) + "授信(ID=" + recharge.getId() + ")，抵消金额￥"
							+ new DecimalFormat("0.000").format(tempRecharge.getAmount() / 1000.0));
					tempRecharge.setAddBalanceType(3);
					tempRecharge.setConfirmTime(new Date());
					tempRecharge.setConfirmUser(admin);
					addBalanceService.addRecharge(tempRecharge);
					logger.info("生成授信抵消记录为：" + JSON.toJSONString(tempRecharge));
				}
				if (addAmount == 0)
				{
					logger.info("授信抵消后，可加款金额为0，删除原充值记录，id=" + fxRecharge.getId());
					//如果授信抵消后加款金额为0.则删除原充值记录
					addBalanceService.deleteRecharge(fxRecharge.getId());
				}
				else
				{
					logger.info("授信抵消后，可加款金额为" + addAmount / 1000.0 + "，更新原充值记录，id=" + fxRecharge.getId());
					//更新充值记录
					addBalanceService.updateRecharge(new Date(), shiroUser.getId(), 1, 0L, addAmount,
							fxRecharge.getId());
				}
				//		if (recharge != null)
				//		{//添加流水
				//			FXFoundsFlow foundsFlow = new FXFoundsFlow();
				//			foundsFlow.setAmount(recharge.getAmount());
				//			foundsFlow.setBusinessType(BusinessTypeEnum.addBalance.getType());//订单
				//			foundsFlow.setFlowType(FoundsFlowEnum.lr.getType());//流入
				//			foundsFlow.setFxRecharge(recharge);//订单ID
				//			foundsFlow.setCreatTime(new Date());
				//			foundsFlow.setEnterprise(recharge.getFxEnterprise());//所属企业ID
				//			foundsFlow = financialStatementsService.save(foundsFlow);
				//			logger.info("====start FinancialManagementController.updateRechargeStatus====foundsFlow_id:"
				//					+ foundsFlow.getId() + ",Amount:" + foundsFlow.getAmount());
				//		}
				//		else
				//		{
				//			return ResultFactory.getFailedResult("更新失败");
				//		}
			}
			//授信
			else if (fxRecharge.getAddBalanceType() == 2)
			{
				addBalanceService.updateRecharge(new Date(), shiroUser.getId(), 1, fxRecharge.getAmount(),
						fxRecharge.getAmount(), fxRecharge.getId());

				//生成fee_handle记录
				Date confirmDate = new Date();
				FXFeeHandle feeHandle = new FXFeeHandle();
				feeHandle.setAmount(fxRecharge.getAmount());
				feeHandle.setMillisecond(confirmDate.getTime());
				feeHandle.setOrderRecordkey(fxRecharge.getId() + "");
				feeHandle.setType(FeeType.ENTERPRISE_ADD_PRICE.getType());
				feeHandle.setWriteTime(confirmDate);
				fxFeeHandleService.save(feeHandle);
			}
			return ResultFactory.getSuccessResult("操作成功");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("操作失败");
		}
	}

	@RequestMapping(value = "updateRecharge")
	@ResponseBody
	public synchronized ResultDo disagree(String id)
	{
		logger.info("====start FinancialManagementController.disagree====foundsFlow_id:" + id);
		FXRecharge fxRecharge = addBalanceService.findById(id);
		if (null != fxRecharge && fxRecharge.getStatus() != 0)
		{
			logger.error("该记录已经被处理！id:" + id);
			return ResultFactory.getFailedResult("该记录已经被处理！");
		}
		fxRecharge.setConfirmTime(new Date());
		Long before = fxRecharge.getFxEnterprise().getBalance();
		fxRecharge.setBeforeRechargeBalance(before);
		fxRecharge.setAfterRechargeBalance(before);
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
		fxRecharge.setConfirmUser(admin);
		fxRecharge.setStatus(2);
		//驳回的订单不产生债务
		fxRecharge.setDebt(0);
		FXRecharge recharge = addBalanceService.saveRecharge(fxRecharge);
		logger.info("====start FinancialManagementController.updateRechargeStatus====");
		if (recharge != null)
		{
			return ResultFactory.getSuccessResult("操作成功");
		}
		else
		{
			return ResultFactory.getFailedResult("操作失败");
		}
	}

	/*
	 * 显示财务报表记录
	 */
	@RequestMapping(value = "getFormList")
	@ResponseBody
	public Page<FXFoundsFlow> getFormList(HttpServletRequest request, String startTime, String endTime,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXFoundsFlow> pageList = null;
		try
		{
			logger.info("====== start financialManagementController.getFormList, req[startTime=" + startTime
					+ ",endTime=" + endTime + ",page=" + page + ",rows=" + rows + "] ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_creatTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_creatTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			pageList = financialStatementService.findAll(searchParams, new PageRequest(page - 1, rows, Direction.DESC,
					"creatTime"));
			logger.info("====== end financialManagementController.getFormList ======");
			return pageList;
		}
		catch (Exception e)
		{
			logger.error("financialManagementController.getFormList error[" + e.getCause() + "]");
			return pageList;
		}
	}

	@RequestMapping(value = "getDownstreamFinancialStatements")
	@ResponseBody
	public Page<FXFoundsFlow> getDownstreamFinancialStatements(HttpServletRequest request, String startTime,
			String endTime, int page, int rows)
	{
		Page<FXFoundsFlow> pageList = null;
		try
		{
			logger.info("====== start financialManagementController.getFormList, req[startTime=" + startTime
					+ ",endTime=" + endTime + ",page=" + page + ",rows=" + rows + "] ======");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
			FXEnterprise fxEnterprise = admin.getFxEnterprise();
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("EQ_enterprise.id", fxEnterprise.getId());
			searchParams.put("GTE_creatTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_creatTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			pageList = financialStatementService.findAll(searchParams, new PageRequest(page - 1, rows, Direction.DESC,
					"creatTime"));
			logger.info("====== end financialManagementController.getFormList ======");
			return pageList;
		}
		catch (Exception e)
		{
			logger.error("financialManagementController.getFormList error[" + e.getCause() + "]");
			return pageList;
		}
	}
}
