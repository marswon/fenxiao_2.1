package com.kedang.fenxiao.web;

import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.kedang.fenxiao.util.enums.BusinessTypeEnum;
import com.kedang.fenxiao.util.enums.FxFlowType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXCami;
import com.kedang.fenxiao.entity.FXExceptionOrder;
import com.kedang.fenxiao.entity.FXFeeHandle;
import com.kedang.fenxiao.entity.FXHandWorkFlow;
import com.kedang.fenxiao.entity.FXNotifyRecord;
import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.service.CamiService;
import com.kedang.fenxiao.service.FXExceptionOrderService;
import com.kedang.fenxiao.service.FXFeeHandleService;
import com.kedang.fenxiao.service.FXOrderRecordService;
import com.kedang.fenxiao.service.FXWaitOrderService;
import com.kedang.fenxiao.service.FlowPayService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.bean.CallbackResponse;
import com.kedang.fenxiao.util.enums.CamiEnums;
import com.kedang.fenxiao.util.enums.FeeType;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 * Created by gegongxian on 16/10/24.
 */
@Controller
@RequestMapping(value = "handwork")
public class handWorkController extends BaseController
{

	private static Logger logger = LoggerFactory.getLogger(handWorkController.class);

	@Autowired
	private FlowPayService flowPayService;
	@Autowired
	private CamiService camiService;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private FXOrderRecordService fxOrderRecordService;
	@Autowired
	private FXWaitOrderService fxWaitOrderService;
	@Autowired
	private FXFeeHandleService fxFeeHandleService;
	@Autowired
	private FXExceptionOrderService fxExceptionOrderService;

	@RequestMapping(value = "openHandWordView")
	public String openHandWordView()
	{
		return "hand_work/handWorkFlow";
	}

	@ResponseBody
	@RequestMapping(value = "save_handWorkFlow")
	public ResultDo save_handWorkFlow(FXCami fxCami, String phone)
	{
		logger.info("======开始手工充值流量======phone[" + phone + "],flowType[" + fxCami.getFlowType() + "],size["
				+ fxCami.getSize() + "]===");
		if (fxCami.getFlowType() == null)
		{
			return ResultFactory.getFailedResult("请选择流量类型");
		}
		if (fxCami.getSize() <= 0)
		{
			return ResultFactory.getFailedResult("请选择流量大小");
		}
		if (StringUtils.isBlank(phone))
		{
			return ResultFactory.getFailedResult("请输入手机号码");
		}
		if (phone.contains("，"))
		{
			return ResultFactory.getFailedResult("请去除中文逗号（，）");
		}
		String[] phoneData = phone.split(",");
		if (phoneData == null || phoneData.length == 0)
		{
			return ResultFactory.getFailedResult("请输入手机号码");
		}
		for (String p : phoneData)
		{
			if (p != null && p.trim().length() != 11)
			{
				return ResultFactory.getFailedResult("请输入正确手机号码");
			}
		}
		for (String p : phoneData)
		{
			String sql = "where v.status = 0 and size = " + fxCami.getSize() + " and flowType = " + fxCami.getFlowType()
					+ " and businessType = 0";
			Query queryPage = em.createQuery("FROM FXCami v " + sql + " order by createTime desc ");
			queryPage.setFirstResult(0);
			queryPage.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<FXCami> cami = queryPage.getResultList();
			if (CollectionUtils.isNotEmpty(cami))
			{
				FXCami fxcami = cami.get(0);
				logger.info("=====查询到未使用卡密====camiStr[" + fxcami.getCamiStr() + "],开始更新改卡密状态为充值中=====");
				//更新该卡密状态未3，充值中
				fxcami.setUpdateTime(new Date());
				fxcami.setStatus(CamiEnums.czz.getType());
				fxcami = camiService.update(fxcami);
				if (null != fxcami && fxcami.getStatus() == CamiEnums.czz.getType())
				{
					logger.info("=====更新卡密为[充值中]状态成功====camiStr[" + fxcami.getCamiStr() + "]=====");
				}
				else
				{
					logger.error("=====更新卡密为[充值中]状态失败====camiStr[" + fxcami.getCamiStr() + "]=====");
					continue;
				}
				try
				{
					//将改卡密充值的手机号
					ResultDo resultDo = flowPayService.flowPayCaMi(p.trim(), fxcami);
					if (null != resultDo && resultDo.getResultCode() == 1)
					{
						logger.info("====手工充值成功====phone[" + p.trim() + "],camiStr[" + fxcami.getCamiStr()
								+ "],message[" + resultDo.getResultMsg() + "]=====");
					}
					else
					{
						logger.error("====手工充值失败====phone[" + p.trim() + "],camiStr[" + fxcami.getCamiStr()
								+ "],message[" + resultDo.getResultMsg() + "]=====");
					}
					//休眠2s
					Thread.sleep(2000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				catch (ServiceException e)
				{
					return ResultFactory.getFailedResult(e.getMessage());
				}
				catch (Exception e)
				{
					return ResultFactory.getFailedResult("手工充值异常");
				}
			}
			else
			{
				return ResultFactory.getFailedResult("存货不足，请及时补仓");
			}
		}
		return ResultFactory.getSuccessResult("手工充值完成");
	}

	/**
	* 分页查询手工充值记录
	*
	* @param request
	* @param page
	* @param rows
	* @return
	*/
	@ResponseBody
	@RequestMapping(value = "findFXHandWorkFlow")
	public Page<FXHandWorkFlow> findFXHandWorkFlow(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXHandWorkFlow> pageList = null;
		try
		{
			logger.info("====== start handWorkController.findFXHandWorkFlow ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = flowPayService.findAllFXHandWorkFlow(searchParams,
					new PageRequest(page - 1, rows, Sort.Direction.DESC, "createTime"));
			logger.info("====== end handWorkController.findFXHandWorkFlow ,res[pageList=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("handWorkController.findFXHandWorkFlow error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 手工回调订单成功、失败
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "callback")
	public synchronized ResultDo callback(String id, String status)
	{
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String userName = user.loginName;
		logger.info(
				"操作人:" + userName + ",===start===手工回调下游订单状态======status[" + status + "]（1:成功，2:失败）,id[" + id + "]====");
		if (StringUtils.isEmpty(status) || StringUtils.isEmpty(id))
		{
			return ResultFactory.getFailedResult("参数异常");
		}
		List<FXOrderRecord> fxOrderRecord = fxOrderRecordService.findOneById(id);
		if (CollectionUtils.isEmpty(fxOrderRecord))
		{
			return ResultFactory.getFailedResult("订单记录不存在");
		}
		int result = -1;
		//成功
		if (status.equalsIgnoreCase("1"))
		{
			result = 0;
		}
		//失败
		else if (status.equalsIgnoreCase("2"))
		{
			result = 1;
		}
		FXOrderRecord f = fxOrderRecord.get(0);
		logger.info("====准备回调订单:" + f.getDownstreamOrderNo() + ",订单状态:" + (result == 0 ? "成功" : "失败"));
		//判断订单状态是否已完成
		if (f.getUpstreamStatus() != 0 && f.getUpstreamStatus() != 1)
		{
			logger.info("====第一次回调:" + f.getDownstreamOrderNo() + ",订单状态:" + (result == 0 ? "成功" : "失败"));
			//第一次回调
			//更新保存订单状态
			f.setUpstreamStatus(result);
			f.setDownstreamStatus(result);
			f.setErrorCode("88888888");
			f.setErrorDesc("手工回调(" + userName + ")" + f.getErrorDesc());
			f.setReportTime(new Date());
			f = fxOrderRecordService.save(f);
			//失败退款
			if (result == 1)
			{
				FXFeeHandle feeHandle = new FXFeeHandle();
				feeHandle.setOrderRecordkey(f.getOrderRecordkey());
				feeHandle.setAmount(f.getCostMoney());
				feeHandle.setType(FeeType.ORDER_CALLBACK.getType());
				feeHandle.setMillisecond(System.currentTimeMillis());
				feeHandle.setWriteTime(new Date());
				fxFeeHandleService.save(feeHandle);
			}
		}
		else
		{
			logger.info("====订单状态结果已完结，只需回调动作，无需参与其他业务逻辑=====订单号:" + f.getDownstreamOrderNo());
		}
		//删除待处理订单记录
		Integer deleteResult = fxWaitOrderService.deleteBydownstreamOrderNo(f.getDownstreamOrderNo());
		logger.info("删除待处理订单:" + deleteResult);
		//生成回调对象
		FXNotifyRecord fxNotifyRecord = new FXNotifyRecord();
		fxNotifyRecord.setSerialNumber(f.getDownstreamOrderNo());
		fxNotifyRecord.setOrderid(f.getUpstreamOrderNo());
		fxNotifyRecord.setErrorCode(f.getErrorCode());
		fxNotifyRecord.setErrorDesc(f.getErrorDesc());
		fxNotifyRecord.setNotifyUrl(f.getNotifyUrl());
		fxNotifyRecord.setPushTimes(1);
		fxNotifyRecord.setUpdateTime(new Date());
		fxNotifyRecord.setDowmstreamStatus(result);
		CallbackResponse _resp = callbackPost(fxNotifyRecord);
		logger.info("===end===手工回调下游订单状态======");
		if (null != _resp && "0000".equals(_resp.getFs_code() + ""))
		{
			//回调成功
			return ResultFactory.getSuccessResult("回调成功");
		}
		else
		{
			//回调失败
			return ResultFactory.getFailedResult("回调失败");
		}
	}

	@SuppressWarnings(
	{ "unchecked", "deprecation" })
	private static CallbackResponse callbackPost(FXNotifyRecord notifyRecord)
	{
		CallbackResponse _resp = new CallbackResponse();
		try
		{
			if (notifyRecord == null || StringUtils.isBlank(notifyRecord.getNotifyUrl()))
			{
				logger.info("此记录不回调" + new Gson().toJson(notifyRecord));
				return _resp;
			}
			HttpClient client = new HttpClient();
			client.setTimeout(60000);
			client.setConnectionTimeout(60000);
			PostMethod postMethod = new PostMethod(notifyRecord.getNotifyUrl());
			postMethod.getParams().setContentCharset("UTF-8");

			String fs_code = notifyRecord.getDowmstreamStatus() == 0 ? "0000" : "9999";
			String fs_message = notifyRecord.getErrorDesc() == null ? "" : notifyRecord.getErrorDesc();

			StringBuilder sb = new StringBuilder();
			sb.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:notice xmlns:ns2=\"http://callback.webservice.wujun.com/\">");
			sb.append("<ORDER>");
			sb.append("<SERIAL_NUMBER>" + notifyRecord.getSerialNumber() + "</SERIAL_NUMBER>");
			sb.append("<ORDER_ID>" + notifyRecord.getOrderid() + "</ORDER_ID>");
			sb.append("<FS_CODE>" + fs_code + "</FS_CODE>");
			sb.append("<FS_MESSAGE>" + fs_message + "</FS_MESSAGE>");
			sb.append("</ORDER>");
			sb.append("</ns2:notice></soap:Body></soap:Envelope>");

			logger.info("回调地址:" + notifyRecord.getNotifyUrl());
			logger.info("回调报文:" + sb.toString());
			postMethod.setRequestBody(sb.toString());

			HttpMethod httpMethod = postMethod;
			int a = -1000;
			client.executeMethod(httpMethod);
			String temp = httpMethod.getResponseBodyAsString();
			logger.info("返回报文:" + temp);

			httpMethod.releaseConnection();
			a = httpMethod.getStatusCode();
			logger.info("接口状态 :" + a);
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(temp));
			Element root = document.getRootElement();
			List<Element> childElements = root.elements();
			for (Element child : childElements)
			{
				List<Element> result2 = child.elements();
				for (Element c : result2)
				{
					List<Element> c2 = c.elements();
					for (Element ret : c2)
					{
						String result = ret.elementText("FS_CODE");
						String msg = ret.elementText("FS_MESSAGE");
						_resp.setFs_code(result);
						_resp.setFs_message(msg);
						logger.info("回调订单SERIAL_NUMBER:" + notifyRecord.getSerialNumber() + ",返回代码:" + result + ",信息:"
								+ msg);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("回调异常:" + e.getMessage(), e);
		}
		return _resp;
	}

	/**
	 * 手工回调订单成功、失败
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "callbackExceptionOrder")
	public synchronized ResultDo callbackExceptionOrder(String id, String status)
	{
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String userName = user.loginName;
		logger.info(
				"操作人:" + userName + ",===start===手工回调下游订单状态======status[" + status + "]（1:成功，2:失败）,id[" + id + "]====");
		if (StringUtils.isEmpty(status) || StringUtils.isEmpty(id))
		{
			return ResultFactory.getFailedResult("参数异常");
		}
		List<FXExceptionOrder> fxExceptionOrder = fxExceptionOrderService.findOneById(id);
		if (CollectionUtils.isEmpty(fxExceptionOrder))
		{
			return ResultFactory.getFailedResult("订单记录不存在");
		}
		int result = -1;
		//成功
		if (status.equalsIgnoreCase("1"))
		{
			result = 0;
		}
		//失败
		else if (status.equalsIgnoreCase("2"))
		{
			result = 1;
		}
		FXExceptionOrder f = fxExceptionOrder.get(0);
		logger.info("====准备回调订单:" + f.getDownstreamOrderNo() + ",订单状态:" + (result == 0 ? "成功" : "失败"));
		//json info 不为空
		if (StringUtils.isNotBlank(f.getMessage()))
		{
			logger.info("====message:" + f.getMessage());
			JSONObject json = JSONObject.parseObject(f.getMessage());
			if (json == null)
			{
				return ResultFactory.getFailedResult("回调失败:json为空");
			}
			//回调URL
			String callBackUrl = json.getString("notifyUrl");
			//生成回调对象
			FXNotifyRecord fxNotifyRecord = new FXNotifyRecord();
			fxNotifyRecord.setSerialNumber(f.getDownstreamOrderNo());
			fxNotifyRecord.setOrderid(f.getDownstreamOrderNo());
			fxNotifyRecord.setErrorCode("88888888");
			fxNotifyRecord.setErrorDesc("手工回调(" + userName + ")");
			fxNotifyRecord.setNotifyUrl(callBackUrl);
			fxNotifyRecord.setPushTimes(1);
			fxNotifyRecord.setUpdateTime(new Date());
			fxNotifyRecord.setDowmstreamStatus(result);
			CallbackResponse _resp = callbackPost(fxNotifyRecord);
			logger.info("===end===手工回调下游订单状态======");
			if (null != _resp && "0000".equals(_resp.getFs_code() + ""))
			{
				//回调成功
				return ResultFactory.getSuccessResult("回调成功");
			}
			else
			{
				//回调失败
				return ResultFactory.getFailedResult("回调失败");
			}
		}
		else
		{
			logger.info("====订单回调失败,message为空=====订单号:" + f.getDownstreamOrderNo());
			return ResultFactory.getFailedResult("回调失败,message为空");
		}
	}

	/**
	 * 手工充值加油卡
	 * @param amount
	 * @param payNum
	 * @param gasCardAcount
	 * @param description
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save_gasCard_handWorkFlow")
	public ResultDo save_gasCard_handWorkFlow(String amount, String payNum, String gasCardAcount, String description)
	{
		logger.info("======开始手工充值流量======amount[" + amount + "],payNum[" + payNum + "],gasCardAcount[" + gasCardAcount
				+ "]===");
		if (StringUtils.isBlank(amount))
		{
			return ResultFactory.getFailedResult("请选择充值面值");
		}
		if (StringUtils.isBlank(payNum))
		{
			return ResultFactory.getFailedResult("请选择充值个数");
		}
		if (StringUtils.isBlank(gasCardAcount))
		{
			return ResultFactory.getFailedResult("请输入充值卡号");
		}
		if (gasCardAcount.contains("，"))
		{
			return ResultFactory.getFailedResult("请去除中文逗号（，）");
		}
		String[] phoneData = gasCardAcount.split(",");
		if (phoneData.length == 0)
		{
			return ResultFactory.getFailedResult("请输入充值卡号");
		}
		for (String p : phoneData)
		{
			if (p != null && p.trim().length() != 19 && p.trim().length() != 16)
			{
				return ResultFactory.getFailedResult("请输入正确充值卡号：" + p);
			}
		}
		try
		{
			/**
			 * 效验总价小于限额
			 */
			flowPayService.validateCardCrestValue(amount, payNum, phoneData);
			//
			for (String p : phoneData)
			{
				boolean result = flowPayService.submitOrder(amount, payNum, p.trim(), description);
				if (result)
				{
					logger.info("save_gasCard_handWorkFlow 保存待审核纪录成功 phone:" + p);
				}
				else
				{
					logger.error("save_gasCard_handWorkFlow 保存待审核纪录失败 amount:" + amount + ",payNum:" + payNum + ",p:"
							+ p.trim());
					return ResultFactory
							.getFailedResult("保存待审核纪录失败 phone" + amount + ",payNum:" + payNum + ",p:" + p.trim());
				}
			}
		}
		catch (Exception e)
		{
			logger.error("加油卡下单异常");
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("手工充值异常" + e);

		}
		return ResultFactory.getSuccessResult("提交成功，等待审核");
	}

	/**
	 * 手工充值流量
	 * @param flowType
	 * @param flowSize
	 * @param flowAcount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handWorkFlow")
	public ResultDo save_handWorkFlow(String flowType, String flowSize, String flowAcount,String description)
	{
		logger.info("======开始手工充值流量======flowType[" + flowType + "],size[" + flowSize + "],flowAcount[" + flowAcount
				+ "]===");
		if (StringUtils.isBlank(flowType))
		{
			return ResultFactory.getFailedResult("请选择流量类型");
		}
		if (StringUtils.isBlank(flowSize))
		{
			return ResultFactory.getFailedResult("请选择流量大小");
		}
		if (StringUtils.isBlank(flowAcount))
		{
			return ResultFactory.getFailedResult("请输入手机号码");
		}
		if (flowAcount.contains("，"))
		{
			return ResultFactory.getFailedResult("请去除中文逗号（，）");
		}
		String[] phoneData = flowAcount.split(",");
		if (phoneData.length == 0)
		{
			return ResultFactory.getFailedResult("请输入充值卡号");
		}
		for (String p : phoneData)
		{
			if (p != null && p.trim().length() != 11)
			{
				return ResultFactory.getFailedResult("请输入正确充值卡号：" + p);
			}
		}
		try
		{
			String amount = getAmount(flowSize);
			flowPayService.validateFlowCrestValue(amount, phoneData);
			for (String p : phoneData)
			{
				boolean result = flowPayService.flowOrder(flowSize, flowType, p.trim(),description);
				logger.info("流量手工下单结果:" + result + ",flowType:" + flowType + ",size:" + flowSize + ",flowAccount:"
						+ p.trim());
				if (result)
				{
					logger.info("save_handWorkFlow 保存待审核纪录成功 phone:" + p);
				}
				else
				{
					logger.error("save_handWorkFlow 保存待审核纪录失败 amount:" + amount +",p:"
							+ p.trim());
					return ResultFactory
							.getFailedResult("保存待审核纪录失败 phone" + amount + ",p:" + p.trim());
				}
			}
		}
		catch (Exception e)
		{
			logger.error("流量下单异常");
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("手工充值异常" + e);

		}
		return ResultFactory.getSuccessResult("提交成功，等待审核");
	}

	/**
	 * 手工充值话费
	 * @param flowType
	 * @param billSize
	 * @param billAcount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handWorkBill")
	public ResultDo save_handWorkBill(String flowType, String billSize, String billAcount,String description)
	{
		logger.info("======开始手工充值话费======flowType[" + flowType + "],size[" + billSize + "],billAcount[" + billAcount
				+ "]===");
		if (StringUtils.isBlank(flowType))
		{
			return ResultFactory.getFailedResult("请选择话费类型");
		}
		if (StringUtils.isBlank(billSize))
		{
			return ResultFactory.getFailedResult("请选择话费金额");
		}
		if (StringUtils.isBlank(billAcount))
		{
			return ResultFactory.getFailedResult("请输入手机号码");
		}
		if (billAcount.contains("，"))
		{
			return ResultFactory.getFailedResult("请去除中文逗号（，）");
		}
		String[] phoneData = billAcount.split(",");
		if (phoneData.length == 0)
		{
			return ResultFactory.getFailedResult("请输入充值卡号");
		}
		for (String p : phoneData)
		{
			if (p != null && p.trim().length() != 11)
			{
				return ResultFactory.getFailedResult("请输入正确充值卡号：" + p);
			}
		}
		try
		{
			flowPayService.validateFlowCrestValue(billSize, phoneData);
			for (String p : phoneData)
			{
				boolean result = flowPayService.billOrder(billSize, flowType, p.trim(), description);
				logger.info("话费手工下单结果:" + result + ",flowType:" + flowType + ",size:" + billSize + ",flowAccount:"
						+ p.trim());
				if (result)
				{
					logger.info("save_handWorkBill 保存待审核纪录成功 phone:" + p);
				}
				else
				{
					logger.error("save_handWorkBill 保存待审核纪录失败 amount:" + billSize +",p:"
							+ p.trim());
					return ResultFactory
							.getFailedResult("保存待审核纪录失败 phone" + billSize + ",p:" + p.trim());
				}
			}

		}
		catch (Exception e)
		{
			logger.error("流量下单异常");
			logger.error(e.getMessage(), e);
			return ResultFactory.getFailedResult("手工充值异常" + e);

		}
		return ResultFactory.getSuccessResult("提交成功，等待审核");
	}

	public static String getAmount(String size)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("5", "1");
		map.put("10", "2");
		map.put("30", "5");
		map.put("50", "7");
		map.put("70", "9");
		map.put("100", "10");
		map.put("200", "15");
		map.put("300", "20");
		map.put("500", "30");
		map.put("1024", "50");
		map.put("2048", "70");
		map.put("3072", "100");
		return map.get(size);
	}
}
