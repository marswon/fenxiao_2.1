package com.kedang.fenxiao.web;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.web.Servlets;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.FXFoundsFlow;
import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.entity.FXOrderRecordHistory;
import com.kedang.fenxiao.entity.FXRecharge;
import com.kedang.fenxiao.entity.FXTaobaoOrderRecord;
import com.kedang.fenxiao.service.AddBalanceService;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.FXOrderRecordHistoryService;
import com.kedang.fenxiao.service.FXOrderRecordService;
import com.kedang.fenxiao.service.FinancialStatementsService;
import com.kedang.fenxiao.service.TaobaoOrderService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.DateUtil;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.ExcelView;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "export")
public class ExportExcelController {
	@Autowired
	private FinancialStatementsService financialStatementService;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private FXOrderRecordService fxOrderRecordService;
	@Autowired
	private FXOrderRecordHistoryService fxOrderRecordHistoryService;
	@Autowired
	private AddBalanceService addBalanceService;
	@Autowired
	private TaobaoOrderService taobaoOrderService;
	private static final Logger logger = LoggerFactory
			.getLogger(ExportExcelController.class);

	/**
	 * 判断管理员订单页面导出条数是否大于5万
	 */
	@RequestMapping(value = "isOutOfManageOrderExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfManageOrderExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,@ModelAttribute("systemStartTime") String systemStartTime,
			@ModelAttribute("systemEndTime") String systemEndTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String downStatus = request.getParameter("downStatus");
		String upStatus = request.getParameter("upStatus");
		String mobile = request.getParameter("mphone");
		String orderNo = request.getParameter("orderNo");
		String eId = request.getParameter("eId");
		String channelId = request.getParameter("channelId");
		String provinceId = request.getParameter("provinceId");
		String isNormal = request.getParameter("isNormal");
		String businessType = request.getParameter("businessType");
		String yysTypeId = request.getParameter("yysTypeId");
		String handwork = request.getParameter("handWork");
		String areaCode = request.getParameter("areaCode");
		searchParams.put("LIKE_downstreamOrderNo", orderNo);
		searchParams.put("LIKE_mobile", mobile);
		searchParams.put("EQ_upstreamStatus", upStatus);
		searchParams.put("EQ_downstreamStatus", downStatus);
		searchParams.put("EQ_provinceId", provinceId);
		searchParams.put("EQ_isNormal", isNormal);
		searchParams.put("EQ_businessType", businessType);
		searchParams.put("EQ_yysTypeId", yysTypeId);
		searchParams.put("EQ_handWork", handwork);
		searchParams.put("EQ_areaCode",areaCode);
		// 得到当前用户所在企业
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
		searchParams.put("EQ_fxEnterprise.id", eId);
		searchParams.put("EQ_fxChannel.id", channelId);
		// 添加时间条件
		searchParams.put("GTE_clientSubmitTime",
				DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
		searchParams.put("LTE_clientSubmitTime",
				DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
		//
		searchParams.put("GTE_systemSubmitTime",
				DateUtils.getFormatDate(systemStartTime, "yyyy-MM-dd"));
		if (StringUtils.isBlank(systemEndTime) == false)
		{
			Date end = DateUtils.getFormatDate(systemEndTime, "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			searchParams.put("LT_systemSubmitTime", calendar.getTime());
		}
		Long count = fxOrderRecordService.findCount(searchParams);

		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}
	
	@RequestMapping(value = "isOutOfManageOrderHistoryExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfManageOrderHistoryExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,@ModelAttribute("systemStartTime") String systemStartTime,
			@ModelAttribute("systemEndTime") String systemEndTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String downStatus = request.getParameter("downStatus");
		String upStatus = request.getParameter("upStatus");
		String mobile = request.getParameter("mphone");
		String orderNo = request.getParameter("orderNo");
		String eId = request.getParameter("eId");
		String channelId = request.getParameter("channelId");
		String provinceId = request.getParameter("provinceId");
		String isNormal = request.getParameter("isNormal");
		String businessType = request.getParameter("businessType");
		String yysTypeId = request.getParameter("yysTypeId");
		String handwork = request.getParameter("handWork");
		String areaCode = request.getParameter("areaCode");
		searchParams.put("LIKE_downstreamOrderNo", orderNo);
		searchParams.put("LIKE_mobile", mobile);
		searchParams.put("EQ_upstreamStatus", upStatus);
		searchParams.put("EQ_downstreamStatus", downStatus);
		searchParams.put("EQ_provinceId", provinceId);
		searchParams.put("EQ_isNormal", isNormal);
		searchParams.put("EQ_businessType", businessType);
		searchParams.put("EQ_yysTypeId", yysTypeId);
		searchParams.put("EQ_handWork", handwork);
		searchParams.put("EQ_areaCode",areaCode);
		// 得到当前用户所在企业
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
		searchParams.put("EQ_fxEnterprise.id", eId);
		searchParams.put("EQ_fxChannel.id", channelId);
		// 添加时间条件
		searchParams.put("GTE_clientSubmitTime",
				DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
		searchParams.put("LTE_clientSubmitTime",
				DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
		//
		searchParams.put("GTE_systemSubmitTime",
				DateUtils.getFormatDate(systemStartTime, "yyyy-MM-dd"));
		if (StringUtils.isBlank(systemEndTime) == false)
		{
			Date end = DateUtils.getFormatDate(systemEndTime, "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			searchParams.put("LT_systemSubmitTime", calendar.getTime());
		}
		Long count = fxOrderRecordHistoryService.findCount(searchParams);

		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}
	
	
	/**
	  * 方法描述：判断导出导出订单数是否大于5W
	  * @param startTime
	  * @param endTime
	  * @param request
	  * @param response
	  * @return
	  * @author: zhuwanlin
	  * @date: 2017年5月1日 上午11:20:08
	  */
	@RequestMapping(value = "isOutOfTaobaoOrderExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfTaobaoOrderExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String spuid = request.getParameter("spuid");
		String tbOrderNo = request.getParameter("tbOrderNo");
		String supplierid = request.getParameter("supplierid");
		String accountVal = request.getParameter("accountVal");
		String isJiaChong = request.getParameter("isJiaChong");
		String businessType = request.getParameter("businessType");
		String coopOrderStatus = request.getParameter("coopOrderStatus");
		searchParams.put("LIKE_spuid", spuid);
		searchParams.put("LIKE_tbOrderNo", tbOrderNo);
		searchParams.put("LIKE_accountVal", accountVal);
		searchParams.put("EQ_supplierid", supplierid);
		searchParams.put("EQ_isJiaChong", isJiaChong);
		searchParams.put("EQ_taobaoPro.businessType", businessType);
		searchParams.put("EQ_coopOrderStatus", coopOrderStatus);
		// 得到当前用户所在企业
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
		// 添加时间条件
		searchParams.put("GTE_timeStart",
				DateUtils.getFormatDate(startTime, "yyyy-MM-dd"));
		if (StringUtils.isBlank(endTime) == false)
		{
			Date end = DateUtils.getFormatDate(endTime, "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			searchParams.put("LT_timeStart", calendar.getTime());
		}
		Long count = taobaoOrderService.findCount(searchParams);

		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}

	/**
	 * 判断订单页面导出条数是否大于5万
	 */
	@RequestMapping(value = "isOutOfEnterpriseOrderExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfEnterpriseOrderExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String orderStatus = request.getParameter("orderStatus");
		String mobile = request.getParameter("mphone");
		String orderNo = request.getParameter("orderNo");
		String provinceId = request.getParameter("provinceId");
		String yysTypeId = request.getParameter("yysTypeId");
		searchParams.put("LIKE_downstreamOrderNo", orderNo);
		searchParams.put("LIKE_mobile", mobile);
		searchParams.put("EQ_downstreamStatus", orderStatus);
		// 得到当前用户所在企业
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipal();
		AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
		searchParams.put("EQ_fxEnterprise.id", admin.getFxEnterprise()
				.getId());
		// 添加时间条件
		searchParams.put("GTE_clientSubmitTime",
				DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
		searchParams.put("LTE_clientSubmitTime",
				DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
		searchParams.put("EQ_fxProduct.provinceId", provinceId);
		searchParams.put("EQ_yysTypeId", yysTypeId);
		Long count = fxOrderRecordService.findCount(searchParams);
		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}
	/**
	 * 判断财务报表管理导出条数是否大于5万
	 */
	@RequestMapping(value = "isOutOfManageFinancialExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfManageFinancialExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String businessType = request.getParameter("businessType");
		String mobile = request.getParameter("mphone");
		String orderNo = request.getParameter("orderNo");
		String eId = request.getParameter("eId");
		searchParams.put("LIKE_fxOrderRecord.downstreamOrderNo", orderNo);
		searchParams.put("LIKE_fxOrderRecord.mobile", mobile);
		searchParams.put("EQ_businessType", businessType);
		searchParams.put("EQ_enterprise.id", eId);
		// 添加时间条件
		if (StringUtils.isNotBlank(startTime)) {
			searchParams.put("GTE_creatTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			searchParams.put("LTE_creatTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
		}
		Long count = financialStatementService.findCount(searchParams);
		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}
	
	/**
	 * 判断财务报表导出条数是否大于5万
	 */
	@RequestMapping(value = "isOutOfFinancialExportExcel", method = RequestMethod.GET)
	@ResponseBody
	public ResultDo isOutOfFinancialExportExcel(@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(
				request, "search_");
		String businessType = request.getParameter("businessType");
		String mobile = request.getParameter("mphone");
		String orderNo = request.getParameter("orderNo");
		searchParams.put("LIKE_fxOrderRecord.downstreamOrderNo", orderNo);
		searchParams.put("LIKE_fxOrderRecord.mobile", mobile);
		searchParams.put("EQ_businessType", businessType);
		// 得到当前用户所在企业
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
				.getPrincipal();
		AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
		searchParams.put("EQ_enterprise.id", admin.getFxEnterprise()
				.getId());
		// 添加时间条件
		searchParams.put("GTE_creatTime",
				DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
		searchParams.put("LTE_creatTime",
				DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
		Long count = financialStatementService.findCount(searchParams);
		if (count > 50000) {
			return ResultFactory.getFailedResult("导出记录大于5万条");
		} else {
			return ResultFactory.getSuccessResult("可以导出");
		}
	}
	
	/**
	 * 导出订单页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "exportOrderExcel", method = RequestMethod.GET)
	public ModelAndView exportExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "订单导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String orderStatus = request.getParameter("orderStatus");
			String mobile = request.getParameter("mphone");
			String orderNo = request.getParameter("orderNo");
			String provinceId = request.getParameter("provinceId");
			String yysTypeId = request.getParameter("yysTypeId");
			if (mobile != null && !"".equals(mobile)) {
				condition += " 手机号码:" + mobile;
			}
			if (yysTypeId != null && yysTypeId != "") {
				if ("1".equals(yysTypeId)) {
					condition += " 运营商:" + "电信";
				} else if ("2".equals(yysTypeId)) {
					condition += " 运营商:" + "移动";
				} else if ("3".equals(yysTypeId)) {
					condition += " 运营商:" + "联通";
				}
			}
			if (orderStatus != null && orderStatus != "") {
				if ("0".equals(orderStatus)) {
					condition += " 订单状态:" + "成功";
				} else if ("1".equals(orderStatus)) {
					condition += " 订单状态:" + "失败";
				} else if ("2".equals(orderStatus)) {
					condition += " 订单状态:" + "充值中";
				} else if ("3".equals(orderStatus)) {
					condition += " 订单状态:" + "提交成功";
				}
			}
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (orderNo != null && !"".equals(orderNo)) {
				condition += " 订单号码:" + orderNo;
			}
			if (provinceId != null && !"".equals(provinceId)) {
				condition += " 省份:" + showProvince(provinceId);
			}
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"企业名称", "订单编号", "办理号码", "下单时间", "回调时间", "运营商", "产品名称",
					"原价", "实际消费", "账户余额", "订单状态"));
			List<FXOrderRecord> orders = new ArrayList<FXOrderRecord>();

			searchParams.put("LIKE_downstreamOrderNo", orderNo);
			searchParams.put("LIKE_mobile", mobile);
			searchParams.put("EQ_downstreamStatus", orderStatus);
			// 得到当前用户所在企业
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipal();
			AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			searchParams.put("EQ_fxEnterprise.id", admin.getFxEnterprise()
					.getId());
			// 添加时间条件
			searchParams.put("GTE_clientSubmitTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("EQ_fxProduct.provinceId", provinceId);
			searchParams.put("EQ_yysTypeId", yysTypeId);
			orders = fxOrderRecordService.findAllOrderRecord(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			excelView.buildExcelDocument(
					map,
					exportCzinstanceOrderList(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.exceptionInfo("查询订单导出 异常信息:"+e.getCause().getMessage());
		}
		return new ModelAndView(excelView);
	}

	/**
	 * @Description: 订单查询页面的导出
	 * @param title
	 * @param rows
	 * @param sheetName
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook exportCzinstanceOrderList(List<String> title,
			List<FXOrderRecord> rows, String sheetName, String condition)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 设置列宽
		sheet.setColumnWidth(0, 8 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 35 * 256);
		sheet.setColumnWidth(4, 8 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 14 * 256);
		// 大标题
		HSSFCellStyle bigTitle = workbook.createCellStyle();
		bigTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont bigFont = workbook.createFont();
		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bigFont.setFontHeight((short) 300);
		bigTitle.setFont(bigFont);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		HSSFRow titleR = sheet.createRow(0);
		HSSFCell cellT = titleR.createCell(0);
		cellT.setCellValue(sheetName);
		cellT.setCellStyle(bigTitle);

		// 加粗居中
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);

		HSSFCell cell = null;

		// 查询条件 加粗
		HSSFCellStyle conditionStyle = workbook.createCellStyle();
		HSSFFont conditionFont = workbook.createFont();
		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conditionStyle.setFont(font);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		HSSFRow conditionR = sheet.createRow(1);
		cell = conditionR.createCell(0);
		cell.setCellStyle(conditionStyle);
		cell.setCellValue(condition);

		int j = 0;
		// 小标题
		HSSFRow titleRow = sheet.createRow(2);
		for (String str : title) {
			cell = titleRow.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(str);
			j++;
		}

		// 水平居中
		HSSFCellStyle center = workbook.createCellStyle();
		center.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		int i = 0;
		for (FXOrderRecord order : rows) {
			HSSFRow row = sheet.createRow(i + 3);
			// 序号
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue(i + 1);
			// 企业名称
			cell = row.createCell(1);
			cell.setCellStyle(center);
			if(order.getFxEnterprise() == null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(order.getFxEnterprise().getName());
			}
			// 订单编号
			cell = row.createCell(2);
			cell.setCellStyle(center);
			cell.setCellValue(order.getDownstreamOrderNo());
			// 办理号码
			cell = row.createCell(3);
			cell.setCellStyle(center);
			cell.setCellValue(order.getMobile());
			// 下单时间
			cell = row.createCell(4);
			cell.setCellStyle(center);
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			cell.setCellValue(sdf.format(order.getClientSubmitTime()));
			// 回调时间
			cell = row.createCell(5);
			cell.setCellStyle(center);
			if (order.getReportTime() != null) {
				cell.setCellValue(sdf.format(order.getReportTime()));
			} else {
				cell.setCellValue("");
			}
			// 运营商名称
			cell = row.createCell(6);
			cell.setCellStyle(center);
			if ("1".equals(order.getYysTypeId())) {
				cell.setCellValue(showProvince(order.getProvinceId()) + "电信");
			} else if ("2".equals(order.getYysTypeId())) {
				cell.setCellValue(showProvince(order.getProvinceId()) + "移动");
			} else {
				cell.setCellValue(showProvince(order.getProvinceId()) + "联通");
			}
			// 产品名称
			cell = row.createCell(7);
			cell.setCellStyle(center);
			if(order.getFxProduct() == null){
				cell.setCellValue("");
			}
			else{
				cell.setCellValue(order.getFxProduct().getName());
			}

			// //原价
			cell = row.createCell(8);
			cell.setCellStyle(center);
			cell.setCellValue("¥" + order.getCostMoney() / order.getDiscount());
			// 实际消费
			cell = row.createCell(9);
			cell.setCellStyle(center);
			cell.setCellValue("¥" + order.getCostMoney() * 0.001);
			// 账户余额
			cell = row.createCell(10);
			cell.setCellStyle(center);
			cell.setCellValue("¥" + order.getAfterBalance() * 0.001);

			// 订单状态
			cell = row.createCell(11);
			cell.setCellStyle(center);
			if ("0".equals(order.getUpstreamStatus() + "")) {
				cell.setCellValue("成功");
			} else if ("1".equals(order.getUpstreamStatus() + "")) {
				cell.setCellValue("失败");
			} else if ("2".equals(order.getUpstreamStatus() + "")) {
				cell.setCellValue("充值中");
			} else {
				cell.setCellValue("提交成功");
			}
			i++;
		}
		return workbook;
	}

	public static String showProvince(String pid)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("000", "全国");
		map.put("010", "北京");
		map.put("020", "广东");
		map.put("021", "上海");
		map.put("022", "天津");
		map.put("023", "重庆");
		map.put("025", "江苏");
		map.put("026", "青海");
		map.put("027", "海南");
		map.put("028", "四川");
		map.put("029", "陕西");
		map.put("030", "山西");
		map.put("035", "河北");
		map.put("039", "河南");
		map.put("040", "内蒙古");
		map.put("041", "辽宁");
		map.put("045", "吉林");
		map.put("046", "黑龙江");
		map.put("050", "安徽");
		map.put("055", "浙江");
		map.put("059", "福建");
		map.put("060", "山东");
		map.put("070", "广西");
		map.put("071", "湖北");
		map.put("073", "湖南");
		map.put("075", "江西");
		map.put("080", "云南");
		map.put("085", "贵州");
		map.put("089", "西藏");
		map.put("090", "宁夏");
		map.put("093", "甘肃");
		map.put("095", "新疆");
		return map.get(pid);
	}

	public static String showArea(String pid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("000","未知");
		map.put("010","北京");
		map.put("020","广州");
		map.put("021","上海");
		map.put("022","天津");
		map.put("023","重庆");
		map.put("024","沈阳");
		map.put("025","南京");
		map.put("027","武汉");
		map.put("028","眉山");
		map.put("029","西安");
		map.put("0310","邯郸");
		map.put("0311","石家庄");
		map.put("0312","保定");
		map.put("0313","张家口");
		map.put("0314","承德");
		map.put("0315","唐山");
		map.put("0316","廊坊");
		map.put("0317","沧州");
		map.put("0318","衡水");
		map.put("0319","邢台");
		map.put("0335","秦皇岛");
		map.put("0349","朔州");
		map.put("0350","忻州");
		map.put("0351","太原");
		map.put("0352","大同");
		map.put("0353","阳泉");
		map.put("0354","晋中");
		map.put("0355","长治");
		map.put("0356","晋城");
		map.put("0357","临汾");
		map.put("0358","吕梁");
		map.put("0359","运城");
		map.put("0370","商丘");
		map.put("0371","郑州");
		map.put("0372","安阳");
		map.put("0373","新乡");
		map.put("0374","许昌");
		map.put("0375","平顶山");
		map.put("0376","信阳");
		map.put("0377","南阳");
		map.put("0378","开封");
		map.put("0379","洛阳");
		map.put("0391","焦作");
		map.put("0392","鹤壁");
		map.put("0393","濮阳");
		map.put("0394","周口");
		map.put("0395","漯河");
		map.put("0396","驻马店");
		map.put("0398","三门峡");
		map.put("0410","铁岭");
		map.put("0411","大连");
		map.put("0412","鞍山");
		map.put("0413","抚顺");
		map.put("0414","本溪");
		map.put("0415","丹东");
		map.put("0416","锦州");
		map.put("0417","营口");
		map.put("0418","阜新");
		map.put("0419","辽阳");
		map.put("0421","朝阳");
		map.put("0427","盘锦");
		map.put("0429","葫芦岛");
		map.put("0431","长春");
		map.put("0432","吉林");
		map.put("0433","延边");
		map.put("0434","四平");
		map.put("0435","通化");
		map.put("0436","白城");
		map.put("0437","辽源");
		map.put("0438","松原");
		map.put("0439","白山");
		map.put("0451","哈尔滨");
		map.put("0452","齐齐哈尔");
		map.put("0453","牡丹江");
		map.put("0454","佳木斯");
		map.put("0455","绥化");
		map.put("0456","黑河");
		map.put("0457","大兴安岭");
		map.put("0458","伊春");
		map.put("0459","大庆");
		map.put("0464","七台河");
		map.put("0467","鸡西");
		map.put("0468","鹤岗");
		map.put("0469","双鸭山");
		map.put("0470","呼伦贝尔");
		map.put("0471","呼和浩特");
		map.put("0472","包头");
		map.put("0473","乌海");
		map.put("0474","乌兰察布");
		map.put("0475","通辽");
		map.put("0476","赤峰");
		map.put("0477","鄂尔多斯");
		map.put("0478","巴彦淖尔");
		map.put("0479","锡林郭勒盟");
		map.put("0482","兴安盟");
		map.put("0483","阿拉善盟");
		map.put("0510","无锡");
		map.put("0511","镇江");
		map.put("0512","苏州");
		map.put("0513","南通");
		map.put("0514","扬州");
		map.put("0515","盐城");
		map.put("0516","徐州");
		map.put("0517","淮安");
		map.put("0518","连云港");
		map.put("0519","常州");
		map.put("0523","泰州");
		map.put("0527","宿迁");
		map.put("0530","菏泽");
		map.put("0531","济南");
		map.put("0532","青岛");
		map.put("0533","淄博");
		map.put("0534","德州");
		map.put("0535","烟台");
		map.put("0536","潍坊");
		map.put("0537","济宁");
		map.put("0538","泰安");
		map.put("0539","临沂");
		map.put("0543","滨州");
		map.put("0546","东营");
		map.put("0550","滁州");
		map.put("0551","合肥");
		map.put("0552","蚌埠");
		map.put("0553","芜湖");
		map.put("0554","淮南");
		map.put("0555","马鞍山");
		map.put("0556","安庆");
		map.put("0557","宿州");
		map.put("0558","阜阳");
		map.put("0559","黄山");
		map.put("0561","淮北");
		map.put("0562","铜陵");
		map.put("0563","宣城");
		map.put("0564","六安");
		map.put("0565","巢湖");
		map.put("0566","池州");
		map.put("0570","衢州");
		map.put("0571","杭州");
		map.put("0572","湖州");
		map.put("0573","嘉兴");
		map.put("0574","宁波");
		map.put("0575","绍兴");
		map.put("0576","台州");
		map.put("0577","温州");
		map.put("0578","丽水");
		map.put("0579","金华");
		map.put("0580","舟山");
		map.put("0591","福州");
		map.put("0592","厦门");
		map.put("0593","宁德");
		map.put("0594","莆田");
		map.put("0595","泉州");
		map.put("0596","漳州");
		map.put("0597","龙岩");
		map.put("0598","三明");
		map.put("0599","南平");
		map.put("0631","威海");
		map.put("0632","枣庄");
		map.put("0633","日照");
		map.put("0634","莱芜");
		map.put("0635","聊城");
		map.put("0660","汕尾");
		map.put("0662","阳江");
		map.put("0663","揭阳");
		map.put("0668","茂名");
		map.put("0691","西双版纳");
		map.put("0692","德宏");
		map.put("0701","鹰潭");
		map.put("0710","襄阳");
		map.put("0711","鄂州");
		map.put("0712","孝感");
		map.put("0713","黄冈");
		map.put("0714","黄石");
		map.put("0715","咸宁");
		map.put("0716","荆州");
		map.put("0717","宜昌");
		map.put("0718","恩施");
		map.put("0719","十堰");
		map.put("0722","随州");
		map.put("0724","荆门");
		map.put("0728","仙桃");
		map.put("0730","岳阳");
		map.put("0731","长沙");
		map.put("0734","衡阳");
		map.put("0735","郴州");
		map.put("0736","常德");
		map.put("0737","益阳");
		map.put("0738","娄底");
		map.put("0739","邵阳");
		map.put("0743","吉首");
		map.put("0744","张家界");
		map.put("0745","怀化");
		map.put("0746","永州");
		map.put("0750","江门");
		map.put("0751","韶关");
		map.put("0752","惠州");
		map.put("0753","梅州");
		map.put("0754","汕头");
		map.put("0755","深圳");
		map.put("0756","珠海");
		map.put("0757","佛山");
		map.put("0758","肇庆");
		map.put("0759","湛江");
		map.put("0760","中山");
		map.put("0762","河源");
		map.put("0763","清远");
		map.put("0766","云浮");
		map.put("0768","潮州");
		map.put("0769","东莞");
		map.put("0770","防城港");
		map.put("0771","南宁");
		map.put("0772","柳州");
		map.put("0773","桂林");
		map.put("0774","梧州");
		map.put("0775","玉林");
		map.put("0776","百色");
		map.put("0777","钦州");
		map.put("0778","河池");
		map.put("0779","北海");
		map.put("0790","新余");
		map.put("0791","南昌");
		map.put("0792","九江");
		map.put("0793","上饶");
		map.put("0794","抚州");
		map.put("0795","宜春");
		map.put("0796","吉安");
		map.put("0797","赣州");
		map.put("0798","景德镇");
		map.put("0799","萍乡");
		map.put("0812","攀枝花");
		map.put("0813","自贡");
		map.put("0816","绵阳");
		map.put("0817","南充");
		map.put("0818","达州");
		map.put("0825","遂宁");
		map.put("0826","广安");
		map.put("0827","巴中");
		map.put("0830","泸州");
		map.put("0831","宜宾");
		map.put("0832","内江");
		map.put("0833","乐山");
		map.put("0834","西昌");
		map.put("0835","雅安");
		map.put("0836","甘孜");
		map.put("0837","阿坝");
		map.put("0838","德阳");
		map.put("0839","广元");
		map.put("0851","贵阳");
		map.put("0852","遵义");
		map.put("0853","安顺");
		map.put("0854","黔南");
		map.put("0855","黔东南");
		map.put("0856","铜仁");
		map.put("0857","毕节");
		map.put("0858","六盘水");
		map.put("0859","黔西南");
		map.put("0870","昭通");
		map.put("0871","昆明");
		map.put("0872","大理");
		map.put("0873","红河");
		map.put("0874","曲靖");
		map.put("0875","保山");
		map.put("0876","文山");
		map.put("0877","玉溪");
		map.put("0878","楚雄");
		map.put("0879","普洱");
		map.put("0883","临沧");
		map.put("0886","怒江");
		map.put("0887","迪庆");
		map.put("0888","丽江");
		map.put("0891","拉萨");
		map.put("0892","日喀则");
		map.put("0893","山南");
		map.put("0894","林芝");
		map.put("0895","昌都");
		map.put("0896","那曲");
		map.put("0897","阿里");
		map.put("0898","海口");
		map.put("0899","三亚");
		map.put("0901","塔城");
		map.put("0902","哈密");
		map.put("0903","和田");
		map.put("0906","阿勒泰");
		map.put("0908","克州");
		map.put("0909","博乐");
		map.put("0910","咸阳");
		map.put("0911","延安");
		map.put("0912","榆林");
		map.put("0913","渭南");
		map.put("0914","商洛");
		map.put("0915","安康");
		map.put("0916","汉中");
		map.put("0917","宝鸡");
		map.put("0919","铜川");
		map.put("0930","临夏");
		map.put("0931","兰州");
		map.put("0932","定西");
		map.put("0933","平凉");
		map.put("0934","庆阳");
		map.put("0935","武威");
		map.put("0936","张掖");
		map.put("0937","嘉峪关");
		map.put("0938","天水");
		map.put("0939","陇南");
		map.put("0941","甘南");
		map.put("0943","白银");
		map.put("0951","银川");
		map.put("0952","石嘴山");
		map.put("0953","吴忠");
		map.put("0954","固原");
		map.put("0955","中卫");
		map.put("0970","海北");
		map.put("0971","西宁");
		map.put("0972","海东");
		map.put("0973","黄南");
		map.put("0974","共和");
		map.put("0975","果洛");
		map.put("0976","玉树");
		map.put("0977","德令哈");
		map.put("0979","格尔木");
		map.put("0990","克拉玛依");
		map.put("0991","乌鲁木齐");
		map.put("0992","奎屯");
		map.put("0993","石河子");
		map.put("0994","昌吉");
		map.put("0995","吐鲁番");
		map.put("0996","库尔勒");
		map.put("0997","阿克苏");
		map.put("0998","喀什");
		map.put("0999","伊犁");
		return map.get(pid);
	}
	/**
	 * 导出管理员订单页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "exportOrderHistoryManageExcel", method = RequestMethod.GET)
	public ModelAndView exportOrderHistoryManageExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			@ModelAttribute("systemStartTime") String systemStartTime,
			@ModelAttribute("systemEndTime") String systemEndTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "订单导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String downStatus = request.getParameter("downStatus");
			String upStatus = request.getParameter("upStatus");
			String mobile = request.getParameter("mphone");
			String orderNo = request.getParameter("orderNo");
			String eId = request.getParameter("eId");
			String eName = request.getParameter("eName");
			String channelId = request.getParameter("channelId");
			String provinceId = request.getParameter("provinceId");
			String isNormal = request.getParameter("isNormal");
			String businessType = request.getParameter("businessType");
			String channelName = request.getParameter("channelName");
			String yysTypeId = request.getParameter("yysTypeId");
			String handwork = request.getParameter("handWork");
			String areaCode = request.getParameter("areaCode");
			if (yysTypeId != null && yysTypeId != "") {
				if ("1".equals(yysTypeId)) {
					condition += " 运营商:" + "电信";
				} else if ("2".equals(yysTypeId)) {
					condition += " 运营商:" + "移动";
				} else if ("3".equals(yysTypeId)) {
					condition += " 运营商:" + "联通";
				} else if ("4".equals(yysTypeId)) {
					condition += " 运营商:" + "中石化";
				} else if ("5".equals(yysTypeId)) {
					condition += " 运营商:" + "中石油";
				} 
			}
			if (mobile != null && !"".equals(mobile)) {
				condition += " 号码:" + mobile;
			}
			if (handwork != null && handwork != "") {
				if ("0".equals(handwork)) {
					condition += " 充值类型:" + "接口";
				} else if ("1".equals(yysTypeId)) {
					condition += " 运营商:" + "手工";
				}
			}
			if (upStatus != null && upStatus != "") {
				if ("0".equals(upStatus)) {
					condition += " 上游状态:" + "成功";
				} else if ("1".equals(upStatus)) {
					condition += " 上游状态:" + "失败";
				} else if ("2".equals(upStatus)) {
					condition += " 上游状态:" + "充值中";
				} else if ("3".equals(upStatus)) {
					condition += " 上游状态:" + "提交成功";
				}
			}
			if (downStatus != null && downStatus != "") {
				if ("0".equals(downStatus)) {
					condition += " 下游状态:" + "成功";
				} else if ("1".equals(downStatus)) {
					condition += " 下游状态:" + "失败";
				} else if ("2".equals(downStatus)) {
					condition += " 下游状态:" + "充值中";
				} else if ("3".equals(downStatus)) {
					condition += " 下游状态:" + "提交成功";
				}
			}
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (systemStartTime != null && !"".equals(systemStartTime)) {
				condition += " 系统提交开始时间:" + systemStartTime;
			}
			if (systemEndTime != null && !"".equals(systemEndTime)) {
				condition += " 系统提交结束时间:" + systemEndTime;
			}
			if (orderNo != null && !"".equals(orderNo)) {
				condition += " 订单号码:" + orderNo;
			}
			if (provinceId != null && !"".equals(provinceId)) {
				condition += " 省份:" + showProvince(provinceId);
			}
			if(areaCode !=null&&!"".equals(areaCode)){
				condition += " 地市:" +showArea(areaCode);
			}
			if (eName != null && !"".equals(eName)) {
				condition += " 企业名称:" + eName;
			}
			if (channelName != null && !"".equals(channelName)) {
				condition += " 通道名称:" + channelName;
			}
			if (isNormal != null && !"".equals(isNormal)) {
				if ("0".equals(isNormal)) {
					condition += " 是否异常:正常";
				} else if ("1".equals(isNormal)) {
					condition += " 是否异常:异常";
				}
			}
			if (businessType != null && !"".equals(businessType)) {
				if ("0".equals(businessType)) {
					condition += " 订单类型:" + "流量";
				} else if("1".equals(businessType)){
					condition += " 订单类型:" + "话费";
				} else if("3".equals(businessType)){
					condition += " 订单类型:" + "加油卡";
				} 
			}
			logger.info("==导出订单记录：condition：" + condition);
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"企业名称", "下游订单编号", "上游订单编号", "办理号码", "客户下单时间", "回调时间", "运营商","省份","地市",
					"产品名称","通道类型", "通道名称", "实际消费", "成本", "原价", "账户余额", "业务类型", "下游状态",
					"上游状态", "描述", "系统下单时间", "所属店铺"));
			List<FXOrderRecordHistory> orders = new ArrayList<FXOrderRecordHistory>();

			searchParams.put("LIKE_downstreamOrderNo", orderNo);
			searchParams.put("LIKE_mobile", mobile);
			searchParams.put("EQ_upstreamStatus", upStatus);
			searchParams.put("EQ_downstreamStatus", downStatus);
			searchParams.put("EQ_provinceId", provinceId);
			searchParams.put("EQ_isNormal", isNormal);
			searchParams.put("EQ_businessType", businessType);
			searchParams.put("EQ_yysTypeId", yysTypeId);
			searchParams.put("EQ_handWork", handwork);
			searchParams.put("EQ_areaCode",areaCode);
			// 得到当前用户所在企业
			// ShiroUser shiroUser = (ShiroUser)
			// SecurityUtils.getSubject().getPrincipal();
			// AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			searchParams.put("EQ_fxEnterprise.id", eId);
			searchParams.put("EQ_fxChannel.id", channelId);
			// 添加时间条件
			searchParams.put("GTE_clientSubmitTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("GTE_systemSubmitTime",
					DateUtils.getFormatDate(systemStartTime, "yyyy-MM-dd"));
			if (StringUtils.isBlank(systemEndTime) == false)
			{
				Date end = DateUtils.getFormatDate(systemEndTime, "yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				searchParams.put("LT_systemSubmitTime", calendar.getTime());
			}
			orders = fxOrderRecordHistoryService.findAllOrderRecord(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			logger.info("==导出订单记录：order_size："
					+ (CollectionUtils.isEmpty(orders) ? 0 : orders.size())
					+ "条==");
			excelView.buildExcelDocumentXSSF(
					map,
					exportOrderHistoryManageListXSSF(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			logger.error("订单导出 异常信息:");
			logger.error(e.getMessage(), e);

		}
		return new ModelAndView(excelView);
	}
	
	@RequestMapping(value = "exportOrderManageExcel", method = RequestMethod.GET)
	public ModelAndView exportOrderManageExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			@ModelAttribute("systemStartTime") String systemStartTime,
			@ModelAttribute("systemEndTime") String systemEndTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "订单导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String downStatus = request.getParameter("downStatus");
			String upStatus = request.getParameter("upStatus");
			String mobile = request.getParameter("mphone");
			String orderNo = request.getParameter("orderNo");
			String eId = request.getParameter("eId");
			String eName = request.getParameter("eName");
			String channelId = request.getParameter("channelId");
			String provinceId = request.getParameter("provinceId");
			String isNormal = request.getParameter("isNormal");
			String businessType = request.getParameter("businessType");
			String channelName = request.getParameter("channelName");
			String yysTypeId = request.getParameter("yysTypeId");
			String handwork = request.getParameter("handWork");
			String areaCode = request.getParameter("areaCode");
			if (yysTypeId != null && yysTypeId != "") {
				if ("1".equals(yysTypeId)) {
					condition += " 运营商:" + "电信";
				} else if ("2".equals(yysTypeId)) {
					condition += " 运营商:" + "移动";
				} else if ("3".equals(yysTypeId)) {
					condition += " 运营商:" + "联通";
				} else if ("4".equals(yysTypeId)) {
					condition += " 运营商:" + "中石化";
				} else if ("5".equals(yysTypeId)) {
					condition += " 运营商:" + "中石油";
				} 
			}
			if (mobile != null && !"".equals(mobile)) {
				condition += " 号码:" + mobile;
			}
			if (handwork != null && handwork != "") {
				if ("0".equals(handwork)) {
					condition += " 充值类型:" + "接口";
				} else if ("1".equals(yysTypeId)) {
					condition += " 运营商:" + "手工";
				}
			}
			if (upStatus != null && upStatus != "") {
				if ("0".equals(upStatus)) {
					condition += " 上游状态:" + "成功";
				} else if ("1".equals(upStatus)) {
					condition += " 上游状态:" + "失败";
				} else if ("2".equals(upStatus)) {
					condition += " 上游状态:" + "充值中";
				} else if ("3".equals(upStatus)) {
					condition += " 上游状态:" + "提交成功";
				}
			}
			if (downStatus != null && downStatus != "") {
				if ("0".equals(downStatus)) {
					condition += " 下游状态:" + "成功";
				} else if ("1".equals(downStatus)) {
					condition += " 下游状态:" + "失败";
				} else if ("2".equals(downStatus)) {
					condition += " 下游状态:" + "充值中";
				} else if ("3".equals(downStatus)) {
					condition += " 下游状态:" + "提交成功";
				}
			}
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (systemStartTime != null && !"".equals(systemStartTime)) {
				condition += " 系统提交开始时间:" + systemStartTime;
			}
			if (systemEndTime != null && !"".equals(systemEndTime)) {
				condition += " 系统提交结束时间:" + systemEndTime;
			}
			if (orderNo != null && !"".equals(orderNo)) {
				condition += " 订单号码:" + orderNo;
			}
			if (provinceId != null && !"".equals(provinceId)) {
				condition += " 省份:" + showProvince(provinceId);
			}
			if(areaCode !=null&&!"".equals(areaCode)){
				condition += " 地市:" +showArea(areaCode);
			}
			if (eName != null && !"".equals(eName)) {
				condition += " 企业名称:" + eName;
			}
			if (channelName != null && !"".equals(channelName)) {
				condition += " 通道名称:" + channelName;
			}
			if (isNormal != null && !"".equals(isNormal)) {
				if ("0".equals(isNormal)) {
					condition += " 是否异常:正常";
				} else if ("1".equals(isNormal)) {
					condition += " 是否异常:异常";
				}
			}
			if (businessType != null && !"".equals(businessType)) {
				if ("0".equals(businessType)) {
					condition += " 订单类型:" + "流量";
				} else if("1".equals(businessType)){
					condition += " 订单类型:" + "话费";
				} else if("3".equals(businessType)){
					condition += " 订单类型:" + "加油卡";
				} 
			}
			logger.info("==导出订单记录：condition：" + condition);
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"企业名称", "下游订单编号", "上游订单编号", "办理号码", "客户下单时间", "回调时间", "运营商","省份","地市",
					"产品名称","通道类型", "通道名称", "实际消费", "成本", "原价", "账户余额", "业务类型", "下游状态",
					"上游状态", "描述", "系统下单时间", "所属店铺"));
			List<FXOrderRecord> orders = new ArrayList<FXOrderRecord>();

			searchParams.put("LIKE_downstreamOrderNo", orderNo);
			searchParams.put("LIKE_mobile", mobile);
			searchParams.put("EQ_upstreamStatus", upStatus);
			searchParams.put("EQ_downstreamStatus", downStatus);
			searchParams.put("EQ_provinceId", provinceId);
			searchParams.put("EQ_isNormal", isNormal);
			searchParams.put("EQ_businessType", businessType);
			searchParams.put("EQ_yysTypeId", yysTypeId);
			searchParams.put("EQ_handWork", handwork);
			searchParams.put("EQ_areaCode",areaCode);
			// 得到当前用户所在企业
			// ShiroUser shiroUser = (ShiroUser)
			// SecurityUtils.getSubject().getPrincipal();
			// AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			searchParams.put("EQ_fxEnterprise.id", eId);
			searchParams.put("EQ_fxChannel.id", channelId);
			// 添加时间条件
			searchParams.put("GTE_clientSubmitTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_clientSubmitTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("GTE_systemSubmitTime",
					DateUtils.getFormatDate(systemStartTime, "yyyy-MM-dd"));
			if (StringUtils.isBlank(systemEndTime) == false)
			{
				Date end = DateUtils.getFormatDate(systemEndTime, "yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				searchParams.put("LT_systemSubmitTime", calendar.getTime());
			}
			orders = fxOrderRecordService.findAllOrderRecord(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			logger.info("==导出订单记录：order_size："
					+ (CollectionUtils.isEmpty(orders) ? 0 : orders.size())
					+ "条==");
			excelView.buildExcelDocumentXSSF(
					map,
					exportOrderManageListXSSF(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			logger.error("订单导出 异常信息:");
			logger.error(e.getMessage(), e);

		}
		return new ModelAndView(excelView);
	}
	
	@RequestMapping(value = "exportTaobaoOrderExcel", method = RequestMethod.GET)
	public ModelAndView exportTaobaoOrderExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "订单导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(
					request, "search_");
			String spuid = request.getParameter("spuid");
			String tbOrderNo = request.getParameter("tbOrderNo");
			String supplierid = request.getParameter("supplierid");
			String accountVal = request.getParameter("accountVal");
			String isJiaChong = request.getParameter("isJiaChong");
			String businessType = request.getParameter("businessType");
			String coopOrderStatus = request.getParameter("coopOrderStatus");
			searchParams.put("LIKE_spuid", spuid);
			searchParams.put("LIKE_tbOrderNo", tbOrderNo);
			searchParams.put("LIKE_accountVal", accountVal);
			searchParams.put("EQ_supplierid", supplierid);
			searchParams.put("EQ_isJiaChong", isJiaChong);
			searchParams.put("EQ_taobaoPro.businessType", businessType);
			searchParams.put("EQ_coopOrderStatus", coopOrderStatus);
			// 添加时间条件
			searchParams.put("GTE_timeStart",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd"));
			if (StringUtils.isBlank(endTime) == false)
			{
				Date end = DateUtils.getFormatDate(endTime, "yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(end);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				searchParams.put("LT_timeStart", calendar.getTime());
			}
			// 标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"订单号", "店铺名称", "宝贝编码", "宝贝名称", "时间", "数量", "售价","分账金额","充值账号",
					"下单编码","充值限时", "订单状态", "处理状态", "是否假充", "假充时间"));
			List<FXTaobaoOrderRecord> orders = new ArrayList<FXTaobaoOrderRecord>();
			// 添加时间条件
			orders = taobaoOrderService.findAllOrderRecord(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			logger.info("==导出订单记录：order_size："
					+ (CollectionUtils.isEmpty(orders) ? 0 : orders.size())
					+ "条==");
			excelView.buildExcelDocumentXSSF(
					map,
					exportTaobaoOrderListXSSF(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			logger.error("订单导出 异常信息:");
			logger.error(e.getMessage(), e);

		}
		return new ModelAndView(excelView);
	}

//	public static HSSFWorkbook exportOrderManageList(List<String> title,
//			List<FXOrderRecord> rows, String sheetName, String condition)
//			throws Exception {
//		HSSFWorkbook workbook = new HSSFWorkbook();
//		HSSFSheet sheet = workbook.createSheet(sheetName);
//
//		// 设置列宽
//		sheet.setColumnWidth(0, 8 * 256);
//		sheet.setColumnWidth(1, 30 * 256);
//		sheet.setColumnWidth(2, 18 * 256);
//		sheet.setColumnWidth(3, 35 * 256);
//		sheet.setColumnWidth(4, 8 * 256);
//		sheet.setColumnWidth(5, 20 * 256);
//		sheet.setColumnWidth(6, 14 * 256);
//		// 大标题
//		HSSFCellStyle bigTitle = workbook.createCellStyle();
//		bigTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		HSSFFont bigFont = workbook.createFont();
//		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		bigFont.setFontHeight((short) 300);
//		bigTitle.setFont(bigFont);
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
//		HSSFRow titleR = sheet.createRow(0);
//		HSSFCell cellT = titleR.createCell(0);
//		cellT.setCellValue(sheetName);
//		cellT.setCellStyle(bigTitle);
//
//		// 加粗居中
//		HSSFCellStyle titleStyle = workbook.createCellStyle();
//		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		HSSFFont font = workbook.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		titleStyle.setFont(font);
//
//		HSSFCell cell = null;
//
//		// 查询条件 加粗
//		HSSFCellStyle conditionStyle = workbook.createCellStyle();
//		HSSFFont conditionFont = workbook.createFont();
//		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		conditionStyle.setFont(font);
//		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
//		HSSFRow conditionR = sheet.createRow(1);
//		cell = conditionR.createCell(0);
//		cell.setCellStyle(conditionStyle);
//		cell.setCellValue(condition);
//
//		int j = 0;
//		// 小标题
//		HSSFRow titleRow = sheet.createRow(2);
//		for (String str : title) {
//			cell = titleRow.createCell(j);
//			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//			cell.setCellStyle(titleStyle);
//			cell.setCellValue(str);
//			j++;
//		}
//
//		// 水平居中
//		HSSFCellStyle center = workbook.createCellStyle();
//		center.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//		int i = 0;
//		for (FXOrderRecord order : rows) {
//			HSSFRow row = sheet.createRow(i + 3);
//			// 序号
//			cell = row.createCell(0);
//			cell.setCellStyle(center);
//			cell.setCellValue(i + 1);
//			// 企业名称
//			cell = row.createCell(1);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getFxEnterprise().getName());
//			// 订单编号
//			cell = row.createCell(2);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getDownstreamOrderNo());
//			// 办理号码
//			cell = row.createCell(3);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getMobile());
//			// 下单时间
//			cell = row.createCell(4);
//			cell.setCellStyle(center);
//			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
//			cell.setCellValue(sdf.format(order.getClientSubmitTime()));
//			// 回调时间
//			cell = row.createCell(5);
//			cell.setCellStyle(center);
//			if (order.getReportTime() != null) {
//				cell.setCellValue(sdf.format(order.getReportTime()));
//			} else {
//				cell.setCellValue("");
//			}
//			// 运营商名称
//			cell = row.createCell(6);
//			cell.setCellStyle(center);
//			if ("1".equals(order.getFxProduct().getYysTypeId())) {
//				cell.setCellValue(showProvince(order.getFxProduct()
//						.getProvinceId()) + "电信");
//			} else if ("2".equals(order.getFxProduct().getYysTypeId())) {
//				cell.setCellValue(showProvince(order.getFxProduct()
//						.getProvinceId()) + "移动");
//			} else {
//				cell.setCellValue(showProvince(order.getFxProduct()
//						.getProvinceId()) + "联通");
//			}
//			// 产品名称
//			cell = row.createCell(7);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getFxProduct().getName());
//
//			// 通道名称
//			cell = row.createCell(8);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getFxChannel().getName());
//
//			// 实际消费
//			cell = row.createCell(9);
//			cell.setCellStyle(center);
//			cell.setCellValue("¥" + order.getCostMoney() * 0.001);
//
//			// 成本
//			cell = row.createCell(10);
//			cell.setCellStyle(center);
//			cell.setCellValue("¥"
//					+ order.getOperatorProduct().getChannelDiscount() * 0.001
//					* (order.getOperatorProduct().getPrice() * 0.001));
//
//			// 原价
//			cell = row.createCell(11);
//			cell.setCellStyle(center);
//			cell.setCellValue("¥" + order.getFxProduct().getPrice() * 0.001);
//			// 账户余额
//			cell = row.createCell(12);
//			cell.setCellStyle(center);
//			cell.setCellValue("¥" + order.getAfterBalance() * 0.001);
//
//			// 订单状态
//			cell = row.createCell(13);
//			cell.setCellStyle(center);
//			if ("0".equals(order.getDownstreamStatus() + "")) {
//				cell.setCellValue("成功");
//			} else if ("1".equals(order.getDownstreamStatus() + "")) {
//				cell.setCellValue("失败");
//			} else if ("2".equals(order.getDownstreamStatus() + "")) {
//				cell.setCellValue("充值中");
//			} else {
//				cell.setCellValue("提交成功");
//			}
//			// 上游状态
//			cell = row.createCell(14);
//			cell.setCellStyle(center);
//			if ("0".equals(order.getUpstreamStatus() + "")) {
//				cell.setCellValue("成功");
//			} else if ("1".equals(order.getUpstreamStatus() + "")) {
//				cell.setCellValue("失败");
//			} else if ("2".equals(order.getUpstreamStatus() + "")) {
//				cell.setCellValue("充值中");
//			} else {
//				cell.setCellValue("提交成功");
//			}
//			// 描述
//			cell = row.createCell(15);
//			cell.setCellStyle(center);
//			cell.setCellValue(order.getErrorDesc());
//
//			i++;
//		}
//		return workbook;
//	}

	/**
	 * XSSF
	 * 
	 * @param title
	 * @param rows
	 * @param sheetName
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static XSSFWorkbook exportOrderManageListXSSF(List<String> title,
			List<FXOrderRecord> rows, String sheetName, String condition)
			throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet(sheetName);
		XSSFFont font = workBook.createFont();
		font.setColor(XSSFFont.COLOR_NORMAL);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		XSSFCellStyle param = workBook.createCellStyle();// 创建格式
		param.setFont(font);
		param.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		param.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		XSSFRow titile = sheet.createRow((short) 0);
		XSSFCell cellTitile = titile.createCell(0);
		cellTitile.setCellStyle(cellStyle);
		cellTitile.setCellValue(sheetName);
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		XSSFRow titleRowSearch = sheet.createRow((short) 1);
		XSSFCell cellSearch = titleRowSearch.createCell(0);
		cellSearch.setCellStyle(param);
		cellSearch.setCellValue(condition);

		// 第一行标题
		XSSFRow titleRow = sheet.createRow((short) 2);
		for (int i = 0, size = title.size(); i < size; i++) {
			// 创建标题单元格
			sheet.setColumnWidth(i, 3000);
			XSSFCell cell = titleRow.createCell(i, 0);
			cell.setCellStyle(cellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.get(i));
		}
		// 从第二行开始写入数据
		// 注：此处如果数据过多，会抛出java.lang.IllegalStateException异常：The maximum number of
		// cell styles was exceeded.
		// You can define up to 4000 styles in a .xls workbook。这是是由于cell
		// styles太多create造成，故一般可以把cellstyle设置放到循环外面
		if (rows != null && !rows.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			XSSFCellStyle style = workBook.createCellStyle();// 创建格式
			for (int i = 0, size = rows.size(); i < size; i++) {
				FXOrderRecord order = rows.get(i);
				XSSFRow row = sheet.createRow(i + 3);
				for (int j = 0, length = title.size(); j < length; j++) {
					XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
					switch (j) {
					// 在单元格中输入一些内容
					case 0:// 序号
						cell.setCellValue(i + 1);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 1:// 企业名称
						if(order.getFxEnterprise() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxEnterprise().getName());
						}
						
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 2:// 订单编号
						cell.setCellValue(order.getDownstreamOrderNo());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 3:// 上游订单编号
						cell.setCellValue(order.getUpstreamOrderNo());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 4:// 办理号码
						cell.setCellValue(order.getMobile());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 5:// 下单时间
						cell.setCellValue(sdf.format(order
								.getClientSubmitTime()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 6:// 回调时间
						if (order.getReportTime() != null) {
							cell.setCellValue(sdf.format(order.getReportTime()));
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 7:// 运营商名称
						if ("1".equals(order.getYysTypeId())) {
							cell.setCellValue("电信");
						} else if ("2".equals(order.getYysTypeId())) {
							cell.setCellValue("移动");
						} else if ("3".equals(order.getYysTypeId())){
							cell.setCellValue("联通");
						}else if ("4".equals(order.getYysTypeId())){
							cell.setCellValue("中石化");
						}else if ("5".equals(order.getYysTypeId())){
							cell.setCellValue("中石油");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 8:// 省份
						cell.setCellValue(showProvince(order.getProvinceId()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 9:// 地市
						cell.setCellValue(showArea(order.getAreaCode()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 10:// 产品名称
						if(order.getFxProduct()== null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxProduct().getName());
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 11:// 产品组名称
						if(order.getFxProduct() !=null ){
							if ("1".equals(order.getFxProduct().getYysTypeId())) {
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "电信");
							} else if ("2".equals(order.getFxProduct()
									.getYysTypeId())) {
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "移动");
							} else if ("3".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "联通");
							}else if ("4".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "中石化");
							}else if ("5".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "中石油");
							}
						}else{
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 12:// 通道名称
						if(order.getFxChannel() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxChannel().getName());
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 13:// 实际消费
						cell.setCellValue("¥" + order.getCostMoney() * 0.001);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 14:// 成本
						if(order.getOperatorProduct() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue("¥"
									+ order.getUpstreamDiscount()
									* 0.001
									* (order.getOperatorProduct().getPrice() * 0.001));
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 15:// 原价
						if(order.getFxProduct() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue("¥" + order.getFxProduct().getPrice()
									* 0.001);
						}
						
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 16:// 账户余额
						cell.setCellValue("¥" + order.getAfterBalance() * 0.001);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 17:// 订单类型
						if (order.getBusinessType() == 0) {
							cell.setCellValue("流量");
						} else if (order.getBusinessType() == 1) {
							cell.setCellValue("话费");
						} else if (order.getBusinessType() == 3) {
							cell.setCellValue("加油卡");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 18:// 订单状态
						if ("0".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("成功");
						} else if ("1".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("失败");
						} else if ("2".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("充值中");
						} else {
							cell.setCellValue("提交成功");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 19:// 上游状态
						if ("0".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("成功");
						} else if ("1".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("失败");
						} else if ("2".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("充值中");
						} else {
							cell.setCellValue("提交成功");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 20:
						// 描述
						cell.setCellValue(order.getErrorDesc());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 21:
						// 系统下单时间
						if (order.getSystemSubmitTime() != null) {
							cell.setCellValue(sdf.format(order.getSystemSubmitTime()));
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 22:
						// 所属店铺
						if (order.getFxTaobaoOrderRecord() != null && order.getFxTaobaoOrderRecord().getTaobaoShop() != null) {
							cell.setCellValue(order.getFxTaobaoOrderRecord().getTaobaoShop().getName());
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					}
				}
			}
		}
		return workBook;
	}
	
	public static XSSFWorkbook exportOrderHistoryManageListXSSF(List<String> title,
			List<FXOrderRecordHistory> rows, String sheetName, String condition)
			throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet(sheetName);
		XSSFFont font = workBook.createFont();
		font.setColor(XSSFFont.COLOR_NORMAL);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		XSSFCellStyle param = workBook.createCellStyle();// 创建格式
		param.setFont(font);
		param.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		param.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		XSSFRow titile = sheet.createRow((short) 0);
		XSSFCell cellTitile = titile.createCell(0);
		cellTitile.setCellStyle(cellStyle);
		cellTitile.setCellValue(sheetName);
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		XSSFRow titleRowSearch = sheet.createRow((short) 1);
		XSSFCell cellSearch = titleRowSearch.createCell(0);
		cellSearch.setCellStyle(param);
		cellSearch.setCellValue(condition);

		// 第一行标题
		XSSFRow titleRow = sheet.createRow((short) 2);
		for (int i = 0, size = title.size(); i < size; i++) {
			// 创建标题单元格
			sheet.setColumnWidth(i, 3000);
			XSSFCell cell = titleRow.createCell(i, 0);
			cell.setCellStyle(cellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.get(i));
		}
		// 从第二行开始写入数据
		// 注：此处如果数据过多，会抛出java.lang.IllegalStateException异常：The maximum number of
		// cell styles was exceeded.
		// You can define up to 4000 styles in a .xls workbook。这是是由于cell
		// styles太多create造成，故一般可以把cellstyle设置放到循环外面
		if (rows != null && !rows.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			XSSFCellStyle style = workBook.createCellStyle();// 创建格式
			for (int i = 0, size = rows.size(); i < size; i++) {
				FXOrderRecordHistory order = rows.get(i);
				XSSFRow row = sheet.createRow(i + 3);
				for (int j = 0, length = title.size(); j < length; j++) {
					XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
					switch (j) {
					// 在单元格中输入一些内容
					case 0:// 序号
						cell.setCellValue(i + 1);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 1:// 企业名称
						if(order.getFxEnterprise() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxEnterprise().getName());
						}
						
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 2:// 订单编号
						cell.setCellValue(order.getDownstreamOrderNo());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 3:// 上游订单编号
						cell.setCellValue(order.getUpstreamOrderNo());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 4:// 办理号码
						cell.setCellValue(order.getMobile());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 5:// 下单时间
						cell.setCellValue(sdf.format(order
								.getClientSubmitTime()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 6:// 回调时间
						if (order.getReportTime() != null) {
							cell.setCellValue(sdf.format(order.getReportTime()));
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 7:// 运营商名称
						if ("1".equals(order.getYysTypeId())) {
							cell.setCellValue("电信");
						} else if ("2".equals(order.getYysTypeId())) {
							cell.setCellValue("移动");
						} else if ("3".equals(order.getYysTypeId())){
							cell.setCellValue("联通");
						}else if ("4".equals(order.getYysTypeId())){
							cell.setCellValue("中石化");
						}else if ("5".equals(order.getYysTypeId())){
							cell.setCellValue("中石油");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 8:// 省份
						cell.setCellValue(showProvince(order.getProvinceId()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 9:// 地市
						cell.setCellValue(showArea(order.getAreaCode()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 10:// 产品名称
						if(order.getFxProduct()== null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxProduct().getName());
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 11:// 产品组名称
						if(order.getFxProduct() !=null ){
							if ("1".equals(order.getFxProduct().getYysTypeId())) {
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "电信");
							} else if ("2".equals(order.getFxProduct()
									.getYysTypeId())) {
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "移动");
							} else if ("3".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "联通");
							}else if ("4".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "中石化");
							}else if ("5".equals(order.getFxProduct()
									.getYysTypeId())){
								cell.setCellValue(showProvince(order.getFxProduct()
										.getProvinceId()) + "中石油");
							}
						}else{
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 12:// 通道名称
						if(order.getFxChannel() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue(order.getFxChannel().getName());
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 13:// 实际消费
						cell.setCellValue("¥" + order.getCostMoney() * 0.001);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 14:// 成本
						if(order.getOperatorProduct() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue("¥"
									+ order.getUpstreamDiscount()
									* 0.001
									* (order.getOperatorProduct().getPrice() * 0.001));
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 15:// 原价
						if(order.getFxProduct() == null){
							cell.setCellValue("");
						}else{
							cell.setCellValue("¥" + order.getFxProduct().getPrice()
									* 0.001);
						}
						
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 16:// 账户余额
						cell.setCellValue("¥" + order.getAfterBalance() * 0.001);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 17:// 订单类型
						if (order.getBusinessType() == 0) {
							cell.setCellValue("流量");
						} else if (order.getBusinessType() == 1) {
							cell.setCellValue("话费");
						} else if (order.getBusinessType() == 3) {
							cell.setCellValue("加油卡");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 18:// 订单状态
						if ("0".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("成功");
						} else if ("1".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("失败");
						} else if ("2".equals(order.getDownstreamStatus() + "")) {
							cell.setCellValue("充值中");
						} else {
							cell.setCellValue("提交成功");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 19:// 上游状态
						if ("0".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("成功");
						} else if ("1".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("失败");
						} else if ("2".equals(order.getUpstreamStatus() + "")) {
							cell.setCellValue("充值中");
						} else {
							cell.setCellValue("提交成功");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 20:
						// 描述
						cell.setCellValue(order.getErrorDesc());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 21:
						// 系统下单时间
						if (order.getSystemSubmitTime() != null) {
							cell.setCellValue(sdf.format(order.getSystemSubmitTime()));
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 22:
						// 所属店铺
						if (order.getFxTaobaoOrderRecord() != null && order.getFxTaobaoOrderRecord().getTaobaoShop() != null) {
							cell.setCellValue(order.getFxTaobaoOrderRecord().getTaobaoShop().getName());
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					}
				}
			}
		}
		return workBook;
	}
	
	
	/**
	  * 方法描述：
	  * @param title
	  * @param rows
	  * @param sheetName
	  * @param condition
	  * @return
	  * @throws Exception
	  * @author: zhuwanlin
	  * @date: 2017年5月1日 上午11:55:03
	  */
	public static XSSFWorkbook exportTaobaoOrderListXSSF(List<String> title,
			List<FXTaobaoOrderRecord> rows, String sheetName, String condition)
			throws Exception {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet(sheetName);
		XSSFFont font = workBook.createFont();
		font.setColor(XSSFFont.COLOR_NORMAL);
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		XSSFCellStyle param = workBook.createCellStyle();// 创建格式
		param.setFont(font);
		param.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		param.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		XSSFRow titile = sheet.createRow((short) 0);
		XSSFCell cellTitile = titile.createCell(0);
		cellTitile.setCellStyle(cellStyle);
		cellTitile.setCellValue(sheetName);
		// 查询条件
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		XSSFRow titleRowSearch = sheet.createRow((short) 1);
		XSSFCell cellSearch = titleRowSearch.createCell(0);
		cellSearch.setCellStyle(param);
		cellSearch.setCellValue(condition);

		// 第一行标题
		XSSFRow titleRow = sheet.createRow((short) 2);
		for (int i = 0, size = title.size(); i < size; i++) {
			// 创建标题单元格
			sheet.setColumnWidth(i, 3000);
			XSSFCell cell = titleRow.createCell(i, 0);
			cell.setCellStyle(cellStyle);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(title.get(i));
		}
		// 从第二行开始写入数据
		// 注：此处如果数据过多，会抛出java.lang.IllegalStateException异常：The maximum number of
		// cell styles was exceeded.
		// You can define up to 4000 styles in a .xls workbook。这是是由于cell
		// styles太多create造成，故一般可以把cellstyle设置放到循环外面
		if (rows != null && !rows.isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			XSSFCellStyle style = workBook.createCellStyle();// 创建格式
			for (int i = 0, size = rows.size(); i < size; i++) {
				FXTaobaoOrderRecord order = rows.get(i);
				XSSFRow row = sheet.createRow(i + 3);
				for (int j = 0, length = title.size(); j < length; j++) {
					XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
//					"序号",
//					"订单号", "店铺名称", "宝贝编码", "宝贝名称", "时间", "数量", "售价","分账金额","充值账号",
					switch (j) {
					// 在单元格中输入一些内容
					case 0:// 序号
						cell.setCellValue(i + 1);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 1:// 订单号
						cell.setCellValue(order.getTbOrderNo());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 2:// 店铺名称
						if (order.getTaobaoShop()!= null) {
							cell.setCellValue(order.getTaobaoShop().getName());
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 3:// 宝贝编码
						cell.setCellValue(order.getSpuid());
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 4:// 宝贝名称
						if (order.getTaobaoPro()!= null) {
							cell.setCellValue(order.getTaobaoPro().getName());
						} else {
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
						break;
					case 5:// 时间
						cell.setCellValue(sdf.format(order.getTimeStart()));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 6:// 数量
						cell.setCellValue(order.getBuyNum());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 7:// 售价
						cell.setCellValue("¥" + decimalFormat.format(order.getUnitPrice()/100.0));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 8:// 分账金额
						cell.setCellValue("¥" + decimalFormat.format(order.getSplitPrice()/100.0));
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 9:// 充值账号
						cell.setCellValue(order.getAccountVal());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
//						"下单编码","充值限时", "订单状态", "处理状态", "是否假充", "假充时间"
					case 10:// 下单编码
						if(order.getTaobaoPro()!=null){
							cell.setCellValue(order.getTaobaoPro().getSize());
						}else{
							cell.setCellValue("");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 11:// 充值限时
						if(order.getTimeLimit() % 3600 == 0){
							cell.setCellValue(order.getTimeLimit() / 3600 + " 小时");
						}else{
							DecimalFormat tempFormat = new DecimalFormat("0.0");
							cell.setCellValue(tempFormat.format(order.getTimeLimit() / 3600.0) + " 小时");
						}
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 12:// 订单状态
						String temp = null;
						switch(order.getCoopOrderStatus()){
							case "SUCCESS":temp="充值成功";break;
							case "UNDERWAY":temp="充值中";break;
							case "FAILED":temp="充值失败";break;
							default:temp="未知";break;
						}
						cell.setCellValue(temp);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 13:// 处理状态
						String HandleStatusTemp = null;
						switch(order.getHandleStatus()){
							case 0:HandleStatusTemp="待处理";break;
							case 1:HandleStatusTemp="已处理";break;
							default:HandleStatusTemp="未知";break;
						}
						cell.setCellValue(HandleStatusTemp);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 14:// 是否假充
						String jiaChongTemp = null;
						switch(order.getIsJiaChong()){
							case 0:jiaChongTemp="否";break;
							case 1:jiaChongTemp="是";break;
							default:jiaChongTemp="未知";break;
						}
						cell.setCellValue(jiaChongTemp);
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					case 15:// 假充时间
						if(order.getJiaChongTime()!=null){
							cell.setCellValue(sdf.format(order.getJiaChongTime()));
						}else{
							cell.setCellValue("");
						}
						
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					default:
						cell.setCellValue("");
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						break;
					}
				}
			}
		}
		return workBook;
	}

	/**
	 * 导出 财务报表
	 * 
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "financialExportExcel", method = RequestMethod.GET)
	public ModelAndView exportFinancialManageExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "财务报表导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String businessType = request.getParameter("businessType");
			String mobile = request.getParameter("mphone");
			String orderNo = request.getParameter("orderNo");
			if (mobile != null && !"".equals(mobile)) {
				condition += " 手机号码:" + mobile;
			}
			if (businessType != null && businessType != "") {
				condition += " 业务类型:"
						+ ("2".equals(businessType) ? "加款" : "订单");
			}
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (orderNo != null && !"".equals(orderNo)) {
				condition += " 订单号码:" + orderNo;
			}
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"所属企业", "订单ID", "产品名称", "充值账号", "创建时间", "业务类型", "收入", "支出",
					"账号余额", "状态"));
			List<FXFoundsFlow> orders = new ArrayList<FXFoundsFlow>();

			searchParams.put("LIKE_fxOrderRecord.downstreamOrderNo", orderNo);
			searchParams.put("LIKE_fxOrderRecord.mobile", mobile);
			searchParams.put("EQ_businessType", businessType);
			// 得到当前用户所在企业
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipal();
			AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			searchParams.put("EQ_enterprise.id", admin.getFxEnterprise()
					.getId());
			// 添加时间条件
			searchParams.put("GTE_creatTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_creatTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			orders = financialStatementService.findAll(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			excelView.buildExcelDocument(
					map,
					exportFinancialManageList(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.exceptionInfo("查询订单导出 异常信息:"+e.getCause().getMessage());
		}
		return new ModelAndView(excelView);
	}

	/**
	 * @Description: 财务报表页面的导出
	 * @param title
	 * @param rows
	 * @param sheetName
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook exportFinancialManageList(List<String> title,
			List<FXFoundsFlow> rows, String sheetName, String condition)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 设置列宽
		sheet.setColumnWidth(0, 8 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 35 * 256);
		sheet.setColumnWidth(4, 8 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 14 * 256);
		// 大标题
		HSSFCellStyle bigTitle = workbook.createCellStyle();
		bigTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont bigFont = workbook.createFont();
		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bigFont.setFontHeight((short) 300);
		bigTitle.setFont(bigFont);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		HSSFRow titleR = sheet.createRow(0);
		HSSFCell cellT = titleR.createCell(0);
		cellT.setCellValue(sheetName);
		cellT.setCellStyle(bigTitle);

		// 加粗居中
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);

		HSSFCell cell = null;

		// 查询条件 加粗
		HSSFCellStyle conditionStyle = workbook.createCellStyle();
		HSSFFont conditionFont = workbook.createFont();
		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conditionStyle.setFont(font);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		HSSFRow conditionR = sheet.createRow(1);
		cell = conditionR.createCell(0);
		cell.setCellStyle(conditionStyle);
		cell.setCellValue(condition);

		int j = 0;
		// 小标题
		HSSFRow titleRow = sheet.createRow(2);
		for (String str : title) {
			cell = titleRow.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(str);
			j++;
		}

		// 水平居中
		HSSFCellStyle center = workbook.createCellStyle();
		center.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		int i = 0;
		for (FXFoundsFlow order : rows) {
			HSSFRow row = sheet.createRow(i + 3);
			// 序号
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue(i + 1);
			// 企业名称
			cell = row.createCell(1);
			cell.setCellStyle(center);
			cell.setCellValue(null==order.getEnterprise()?"":order.getEnterprise().getName());
			// 订单ID
			cell = row.createCell(2);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(null==order.getFxOrderRecord()?"":order.getFxOrderRecord()
						.getDownstreamOrderNo());
			} else {
				cell.setCellValue("");
			}
			// 产品名称
			cell = row.createCell(3);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(null==order.getFxOrderRecord()?"":order.getFxOrderRecord().getFxProduct()
						.getName());
			} else {
				cell.setCellValue("");
			}

			// 充值账号
			cell = row.createCell(4);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(null==order.getFxOrderRecord()?"":order.getFxOrderRecord().getMobile());
			} else {
				cell.setCellValue("");
			}
			// 创建时间
			cell = row.createCell(5);
			cell.setCellStyle(center);
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			cell.setCellValue(null==order.getCreatTime()?"":sdf.format(order.getCreatTime()));
			// 业务类型
			cell = row.createCell(6);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				cell.setCellValue("订单");
			} else {
				cell.setCellValue("加款");
			}
			// 收入
			cell = row.createCell(7);
			cell.setCellStyle(center);
			if ("2".equals(order.getBusinessType() + "")) {
				cell.setCellValue("¥" + order.getAmount() * 0.001);
			} else {
				cell.setCellValue("");
			}

			// 支出
			cell = row.createCell(8);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				cell.setCellValue("¥" + order.getAmount() * 0.001);
			} else {
				cell.setCellValue("");
			}

			// 账户余额
			cell = row.createCell(9);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				if (order.getFxOrderRecord() != null) {
					cell.setCellValue("¥"
							+ (null!=order.getFxOrderRecord()?"":order.getFxOrderRecord().getAfterBalance() / 1000));
				}
			} else {
				cell.setCellValue("¥"
						+ (null==order.getFxRecharge()?"":order.getFxRecharge().getAfterRechargeBalance()
						/ 1000));
			}

			// 订单状态
			cell = row.createCell(10);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				if ("1".equals(order.getFlowType() + "")) {
					cell.setCellValue("成功");
				} else {
					cell.setCellValue("退款");
				}
			} else {
				if (null!=order.getFxRecharge()&&"1".equals(order.getFxRecharge().getStatus() + "")) {
					cell.setCellValue("成功");
				} else if (null!=order.getFxRecharge()&&"0".equals(order.getFxRecharge().getStatus() + "")) {
					cell.setCellValue("未处理");
				} else {
					cell.setCellValue("失败");
				}
			}
			i++;
		}
		return workbook;
	}

	/**
	 * /** 导出加款记录
	 * 
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "exportBalanceManageExcel", method = RequestMethod.GET)
	public ModelAndView exportBalanceManageExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "加款记录导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String eId = request.getParameter("eId");
			String eName = request.getParameter("eName");
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (eId != null && !"".equals(eId)) {
				condition += " 所属企业:" + eName;
			}
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"所属银行", "充值金额(元)", "申请人", "备注", "企业名称 ", "确认时间", "确认人",
					"状态"));
			List<FXRecharge> orders = new ArrayList<FXRecharge>();

			searchParams.put("EQ_fxEnterprise.id", eId);
			// 添加时间条件
			searchParams.put("GTE_submitTime",
					DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			searchParams.put("LTE_submitTime",
					DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			orders = addBalanceService.findAll(searchParams);
			excelView.buildExcelDocument(
					map,
					exportBalanceList(title, orders,
							DateUtil.getDateFormatS(new Date()) + "加款记录导出",
							condition), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.exceptionInfo("查询订单导出 异常信息:"+e.getCause().getMessage());
		}
		return new ModelAndView(excelView);
	}

	/**
	 * @Description: 财务报表页面的导出
	 * @param title
	 * @param rows
	 * @param sheetName
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook exportBalanceList(List<String> title,
			List<FXRecharge> rows, String sheetName, String condition)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 设置列宽
		sheet.setColumnWidth(0, 8 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 35 * 256);
		sheet.setColumnWidth(4, 8 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 14 * 256);
		// 大标题
		HSSFCellStyle bigTitle = workbook.createCellStyle();
		bigTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont bigFont = workbook.createFont();
		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bigFont.setFontHeight((short) 300);
		bigTitle.setFont(bigFont);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		HSSFRow titleR = sheet.createRow(0);
		HSSFCell cellT = titleR.createCell(0);
		cellT.setCellValue(sheetName);
		cellT.setCellStyle(bigTitle);

		// 加粗居中
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);

		HSSFCell cell = null;

		// 查询条件 加粗
		HSSFCellStyle conditionStyle = workbook.createCellStyle();
		HSSFFont conditionFont = workbook.createFont();
		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conditionStyle.setFont(font);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		HSSFRow conditionR = sheet.createRow(1);
		cell = conditionR.createCell(0);
		cell.setCellStyle(conditionStyle);
		cell.setCellValue(condition);

		int j = 0;
		// 小标题
		HSSFRow titleRow = sheet.createRow(2);
		for (String str : title) {
			cell = titleRow.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(str);
			j++;
		}

		// 水平居中
		HSSFCellStyle center = workbook.createCellStyle();
		center.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		int i = 0;
		for (FXRecharge order : rows) {
			HSSFRow row = sheet.createRow(i + 3);
			// 序号
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue(i + 1);
			// 所属银行
			cell = row.createCell(1);
			cell.setCellStyle(center);
			cell.setCellValue(order.getBankName());
			// 充值金额
			cell = row.createCell(2);
			cell.setCellStyle(center);
			cell.setCellValue(order.getAmount() * 0.001);

			// 申请人
			cell = row.createCell(3);
			cell.setCellStyle(center);
			cell.setCellValue(order.getAccountName());

			// 备注
			cell = row.createCell(4);
			cell.setCellStyle(center);
			cell.setCellValue(order.getDescription());

			// 企业名称
			cell = row.createCell(5);
			cell.setCellStyle(center);
			cell.setCellValue(null==order.getFxEnterprise()?"":order.getFxEnterprise().getName());
			// 确认时间
			cell = row.createCell(6);
			cell.setCellStyle(center);
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			cell.setCellValue(null==order.getConfirmTime()?"":sdf.format(order.getConfirmTime()));

			// 确认人
			cell = row.createCell(7);
			cell.setCellStyle(center);
			cell.setCellValue(null==order.getConfirmUser()?"":order.getConfirmUser().getRealName());

			// 状态
			cell = row.createCell(8);
			cell.setCellStyle(center);
			if ("1".equals(order.getStatus() + "")) {
				cell.setCellValue("通过");
			} else if ("0".equals(order.getStatus() + "")) {
				cell.setCellValue("未处理");
			} else {
				cell.setCellValue("驳回");
			}

			i++;
		}
		return workbook;
	}

	/**
	 * 导出 财务管理报表
	 * 
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "exportFinancialManageExcel", method = RequestMethod.GET)
	public ModelAndView exportFinancialExcel(
			@ModelAttribute("startTime") String startTime,
			@ModelAttribute("endTime") String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		ExcelView excelView = new ExcelView();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "财务管理报表导出" + DateUtil.getDateFormatS(new Date())
					+ ".xls");
			String condition = "查询条件:";
			Map<String, Object> searchParams = Servlets
					.getParametersStartingWith(request, "search_");
			String businessType = request.getParameter("businessType");
			String mobile = request.getParameter("mphone");
			String orderNo = request.getParameter("orderNo");
			String eId = request.getParameter("eId");
			String eName = request.getParameter("eName");
			if (mobile != null && !"".equals(mobile)) {
				condition += " 手机号码:" + mobile;
			}
			if (eName != null && !"".equals(eName)) {
				condition += " 所属企业:" + eName;
			}
			if (businessType != null && businessType != "") {
				condition += " 业务类型:"
						+ ("2".equals(businessType) ? "加款" : "订单");
			}
			if (startTime != null && !"".equals(startTime)) {
				condition += " 开始时间:" + startTime;
			}
			if (endTime != null && !"".equals(endTime)) {
				condition += " 结束时间:" + endTime;
			}
			if (orderNo != null && !"".equals(orderNo)) {
				condition += " 订单号码:" + orderNo;
			}
			// 小标题
			List<String> title = new ArrayList<String>(Arrays.asList("序号",
					"所属企业", "订单ID", "产品名称", "充值账号", "创建时间", "业务类型", "成本金额",
					"消费金额", "状态"));
			List<FXFoundsFlow> orders = new ArrayList<FXFoundsFlow>();

			searchParams.put("LIKE_fxOrderRecord.downstreamOrderNo", orderNo);
			searchParams.put("LIKE_fxOrderRecord.mobile", mobile);
			searchParams.put("EQ_businessType", businessType);
			searchParams.put("EQ_enterprise.id", eId);
			// 添加时间条件
			if (StringUtils.isNotBlank(startTime)) {
				searchParams.put("GTE_creatTime",
						DateUtils.getFormatDate(startTime, "yyyy-MM-dd HH:mm"));
			}
			if (StringUtils.isNotBlank(endTime)) {
				searchParams.put("LTE_creatTime",
						DateUtils.getFormatDate(endTime, "yyyy-MM-dd HH:mm"));
			}
			orders = financialStatementService.findAll(searchParams);
			// orders=fxOrderRecordService.findAllOrderRecord();
			excelView.buildExcelDocument(
					map,
					exportFinancialList(title, orders,
							DateUtil.getDateFormatS(new Date()) + "查询订单导出",
							condition), request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.exceptionInfo("查询订单导出 异常信息:"+e.getCause().getMessage());
		}
		return new ModelAndView(excelView);
	}

	/**
	 * @Description: 财务报表页面的导出
	 * @param title
	 * @param rows
	 * @param sheetName
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook exportFinancialList(List<String> title,
			List<FXFoundsFlow> rows, String sheetName, String condition)
			throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName);

		// 设置列宽
		sheet.setColumnWidth(0, 8 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 18 * 256);
		sheet.setColumnWidth(3, 35 * 256);
		sheet.setColumnWidth(4, 8 * 256);
		sheet.setColumnWidth(5, 20 * 256);
		sheet.setColumnWidth(6, 14 * 256);
		// 大标题
		HSSFCellStyle bigTitle = workbook.createCellStyle();
		bigTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont bigFont = workbook.createFont();
		bigFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		bigFont.setFontHeight((short) 300);
		bigTitle.setFont(bigFont);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.size() - 1));
		HSSFRow titleR = sheet.createRow(0);
		HSSFCell cellT = titleR.createCell(0);
		cellT.setCellValue(sheetName);
		cellT.setCellStyle(bigTitle);

		// 加粗居中
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);

		HSSFCell cell = null;

		// 查询条件 加粗
		HSSFCellStyle conditionStyle = workbook.createCellStyle();
		HSSFFont conditionFont = workbook.createFont();
		conditionFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		conditionStyle.setFont(font);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, title.size() - 1));
		HSSFRow conditionR = sheet.createRow(1);
		cell = conditionR.createCell(0);
		cell.setCellStyle(conditionStyle);
		cell.setCellValue(condition);

		int j = 0;
		// 小标题
		HSSFRow titleRow = sheet.createRow(2);
		for (String str : title) {
			cell = titleRow.createCell(j);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(titleStyle);
			cell.setCellValue(str);
			j++;
		}

		// 水平居中
		HSSFCellStyle center = workbook.createCellStyle();
		center.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		int i = 0;
		for (FXFoundsFlow order : rows) {
			HSSFRow row = sheet.createRow(i + 3);
			// 序号
			cell = row.createCell(0);
			cell.setCellStyle(center);
			cell.setCellValue(i + 1);
			// 企业名称
			cell = row.createCell(1);
			cell.setCellStyle(center);
			cell.setCellValue(order.getEnterprise().getName());
			// 订单ID
			cell = row.createCell(2);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(order.getFxOrderRecord()
						.getDownstreamOrderNo());
			} else {
				cell.setCellValue("");
			}
			// 产品名称
			cell = row.createCell(3);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(order.getFxOrderRecord().getFxProduct()
						.getName());
			} else {
				cell.setCellValue("");
			}

			// 充值账号
			cell = row.createCell(4);
			cell.setCellStyle(center);
			if (order.getFxOrderRecord() != null) {
				cell.setCellValue(order.getFxOrderRecord().getMobile());
			} else {
				cell.setCellValue("");
			}
			// 创建时间
			cell = row.createCell(5);
			cell.setCellStyle(center);
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			cell.setCellValue(sdf.format(order.getCreatTime()));
			// 业务类型
			cell = row.createCell(6);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				cell.setCellValue("订单");
			} else {
				cell.setCellValue("加款");
			}
			// 成本金额
			cell = row.createCell(7);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				if ("1".equals(order.getFlowType() + "")) {
					cell.setCellValue("¥"
							+ order.getFxOrderRecord().getOperatorProduct()
									.getChannelDiscount()
							* 0.001
							* (order.getFxOrderRecord().getOperatorProduct()
									.getPrice() * 0.001));
				} else {
					cell.setCellValue("");
				}
			} else {
				cell.setCellValue("");
			}

			// 消费金额
			cell = row.createCell(8);
			cell.setCellStyle(center);
			cell.setCellValue("¥" + order.getAmount() * 0.001);
			// 订单状态
			cell = row.createCell(9);
			cell.setCellStyle(center);
			if ("1".equals(order.getBusinessType() + "")) {
				if ("1".equals(order.getFlowType() + "")) {
					cell.setCellValue("成功");
				} else {
					cell.setCellValue("退款");
				}
			} else {
				if (null != order.getFxRecharge()) {
					if ("1".equals(order.getFxRecharge().getStatus() + "")) {
						cell.setCellValue("成功");
					} else if ("0".equals(order.getFxRecharge().getStatus()
							+ "")) {
						cell.setCellValue("未处理");
					} else {
						cell.setCellValue("失败");
					}
				}
			}
			i++;
		}
		return workbook;
	}
}
