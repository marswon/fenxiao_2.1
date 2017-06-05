package com.kedang.fenxiao.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXWaitValidateRecord;
import com.kedang.fenxiao.service.FXEnterpriseService;
import com.kedang.fenxiao.service.FXWaitValidateRecordService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.DesUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * Created by gegongxian on 17/5/3.
 */
@Controller
@RequestMapping(value = "handWorkValidate")
public class HandWorkValidateController
{
	private Logger logger = LogManager.getLogger(HandWorkValidateController.class);

	@Autowired
	private FXWaitValidateRecordService fxWaitValidateRecordService;

	@Autowired
	private FXEnterpriseService enterpriseService;

	@RequestMapping(value = "openHandWorkValidateView")
	public String openHandWorkValidateView()
	{
		logger.info("===openHandWorkValidateView===");
		return "hand_work_validate/handWordValidate";
	}

	/**
	 * 分页查询待审核订单
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFXWaitValidateRecord")
	public Page<FXWaitValidateRecord> getFXWaitValidateRecord(HttpServletRequest request, String startTime,
			String endTime, int page, int rows)
	{
		Page<FXWaitValidateRecord> pageList = null;

		try
		{
			logger.info("====== start HandWorkValidateController.getFXWaitValidateRecord ======req[startTime="
					+ startTime + ",endTime=" + endTime);
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			searchParams.put("GTE_createTime", DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_createTime", DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			//多条件排序
			Sort sort = new Sort(Sort.Direction.DESC, "createTime");
			pageList = fxWaitValidateRecordService.findAllFXWaitValidateRecord(searchParams,
					new PageRequest(page - 1, rows, sort));
			logger.info("====== end HandWorkValidateController.getFXWaitValidateRecord ,res[_listFxRecharge=" + pageList
					+ "] ======");
		}
		catch (Exception e)
		{
			logger.error("HandWorkValidateController.getFXWaitValidateRecord error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 同意
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateRechargeAgree")
	public synchronized ResultDo updateRechargeAgree(String id)
	{
		logger.info("====start HandWorkValidateController.updateRechargeAgree====id:" + id);
		try
		{
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("审核手工充值纪录，操作人" + new Gson().toJson(shiroUser));
			FXWaitValidateRecord fxWaitValidateRecord = fxWaitValidateRecordService.findOne(Long.valueOf(id));
			if (null == fxWaitValidateRecord)
			{
				return ResultFactory.getFailedResult("改记录不存在！");
			}
			if (fxWaitValidateRecord.getStatus() != 0)
			{
				logger.error("该记录已经被处理！id:" + id);
				return ResultFactory.getFailedResult("操作失败,改记录已经被处理！");
			}

			String mid = fxWaitValidateRecord.getEnterprise().getMid();
			logger.info("审核手工充值纪录，mid:" + mid);
			//判断参数，并组装充值请求
			String result = sendPost(fxWaitValidateRecord);
			if (StringUtils.isNotBlank(result))
			{
				JSONObject jsonObject = JSONObject.parseObject(result);
				if (jsonObject.containsKey("result") && jsonObject.getString("result").equals("0000"))
				{
					logger.info("下单成功,稍后可查询充值结果:" + result);
				}
				else
				{
					logger.error("下单失败:" + result);
					return ResultFactory.getFailedResult("下单失败:" + jsonObject.getString("msg"));
				}
			}
			else
			{
				return ResultFactory.getFailedResult("下单异常");
			}
			fxWaitValidateRecord.setUpdateTime(new Date());
			fxWaitValidateRecord.setCheckOperator(shiroUser.getName());
			fxWaitValidateRecord.setStatus(Constant.INT_YES);
			fxWaitValidateRecord = fxWaitValidateRecordService.save(fxWaitValidateRecord);
			if (fxWaitValidateRecord != null && fxWaitValidateRecord.getStatus() == 1)
			{
				logger.info("手工充值审核通过 fxWaitValidateRecord:" + new Gson().toJson(fxWaitValidateRecord));
				logger.info("手工充值审核通过 shiroUser:" + new Gson().toJson(shiroUser));
				return ResultFactory.getSuccessResult("操作成功");
			}
			else
			{
				return ResultFactory.getFailedResult("操作失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("操作失败");
		}
	}

	/**
	 * 撤销
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateRechargeDisagree")
	public synchronized ResultDo updateRechargeDisagree(String id)
	{
		logger.info("====start HandWorkValidateController.updateRechargeDisagree====id:" + id);
		try
		{
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			logger.info("审核手工充值纪录，操作人" + new Gson().toJson(shiroUser));
			FXWaitValidateRecord fxWaitValidateRecord = fxWaitValidateRecordService.findOne(Long.valueOf(id));
			if (null == fxWaitValidateRecord)
			{
				return ResultFactory.getFailedResult("改记录不存在！");
			}
			if (fxWaitValidateRecord.getStatus() != 0)
			{
				logger.error("该记录已经被处理！id:" + id);
				return ResultFactory.getFailedResult("操作失败,改记录已经被处理！");
			}
			//更新状态为撤销
			fxWaitValidateRecord.setStatus(Constant.INT_TWO);//失败
			fxWaitValidateRecord.setUpdateTime(new Date());
			fxWaitValidateRecord.setCheckOperator(shiroUser.getName());
			fxWaitValidateRecord = fxWaitValidateRecordService.save(fxWaitValidateRecord);
			if (fxWaitValidateRecord != null && fxWaitValidateRecord.getStatus() == 2)
			{
				logger.info("手工充值审核驳回 fxWaitValidateRecord:" + new Gson().toJson(fxWaitValidateRecord));
				logger.info("手工充值审核驳回 shiroUser:" + new Gson().toJson(shiroUser));
				return ResultFactory.getSuccessResult("操作成功");
			}
			else
			{
				return ResultFactory.getFailedResult("操作失败");
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("操作异常" + e);
		}
	}

	//组装并发送下单请求
	public String sendPost(FXWaitValidateRecord fxWaitValidateRecord)
	{
		String businessType = fxWaitValidateRecord.getBusinessType() + "";
		String phone = fxWaitValidateRecord.getPhone();
		String size = fxWaitValidateRecord.getSize();
		String buyNum = fxWaitValidateRecord.getBuyNum();
		String flowType = fxWaitValidateRecord.getFlowType() + "";
		String snum = fxWaitValidateRecord.getSnum();
		String mid = fxWaitValidateRecord.getEnterprise().getMid();
		String sercretKey = fxWaitValidateRecord.getEnterprise().getPrivateKey();
		String orderUrl = "";
		if (businessType.equals(Constant.FLOW_TYPE + ""))
		{
			orderUrl = Constant.FLOW_ORDER_URL;
		}
		else if (businessType.equals(Constant.BILL_TYPE + ""))
		{
			orderUrl = Constant.BILL_ORDER_URL;
		}
		else if (businessType.equals(Constant.GAS_CARD_TYPE + ""))
		{
			orderUrl = Constant.GAS_CARD_ORDER_URL;
		}
		logger.info("HandWorkValidateController.sendPost 下单参数: " + new Gson().toJson(fxWaitValidateRecord)
				+ ",orderUrl:" + orderUrl);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = sdf.format(new Date());// 日期
		logger.info("订单号:" + snum + ",充值帐号:" + phone);
		String notifyUrl = "http://localhost:8080";// 下游回调地址
		JSONObject js = new JSONObject();
		js.put("mid", mid);
		js.put("date", date);
		js.put("phone", phone);
		js.put("size", size);
		js.put("snum", snum);
		js.put("buyNum", buyNum);
		js.put("notifyUrl", notifyUrl);
		js.put("flowType", flowType);
		js.put("handWork", Constant.INT_YES);//手工充值标记
		logger.info("手工充值参数:" + js.toJSONString());
		String dataDes = null;
		String result = null;
		try
		{
			dataDes = DesUtils.encrypt(js.toJSONString(), sercretKey);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		logger.info(js.toJSONString());
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(orderUrl);
		postMethod.addParameter("mid", mid); // 商户号
		postMethod.addParameter("data", dataDes); // 密文
		try
		{
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
		}
		catch (IOException e)
		{
			logger.error("流量充值io异常");
			logger.error(e.getMessage(), e);
		}
		catch (Exception e1)
		{
			logger.error("流量充值异常");
			logger.error(e1.getMessage(), e1);
		}
		return result;
	}
}
