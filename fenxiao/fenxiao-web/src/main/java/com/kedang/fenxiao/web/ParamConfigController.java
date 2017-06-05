package com.kedang.fenxiao.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.CustomerConfig;
import com.kedang.fenxiao.entity.FXAdvert;
import com.kedang.fenxiao.service.CustomerConfigService;
import com.kedang.fenxiao.service.IndexService;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.ResultFactory;
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
import com.kedang.fenxiao.util.po.RecommendAward;
import com.kedang.fenxiao.util.po.RecommendSwitch;
import com.kedang.fenxiao.util.po.ResultDo;
import com.kedang.fenxiao.util.po.Withdraw;

/**
 * 参数配置控制器
 */
@Controller
@RequestMapping(value = "paramConfig")
public class ParamConfigController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(ParamConfigController.class);
	@Autowired
	private CustomerConfigService customerConfigService;

	@Autowired
	private IndexService indexService;
	@PersistenceContext
	private EntityManager em;

	/**
	 * 还款/费率配置页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "repayRatePage")
	public String repayRatePage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.repayRatePage ======");
			CycleRate cycleRate = customerConfigService.queryCycleRate();
			CustomerConfig billRemind = customerConfigService.queryBillRemind();
			Installment installment = customerConfigService.queryInstallment();
			Withdraw withdraw = customerConfigService.queryWithdraw();
			Withdraw shopWithdraw = customerConfigService.queryShopWithdraw();
			model.addAttribute("cycleRate", cycleRate);
			model.addAttribute("billRemind", billRemind);
			model.addAttribute("installment", installment);
			model.addAttribute("withdraw", withdraw);
			model.addAttribute("shopWithdraw", shopWithdraw);
			logger.info("====== end ParamConfigController.repayRatePage ,res[cycleRate=" + cycleRate + ",billRemind="
					+ billRemind + ",installment=" + installment + ",withdraw=" + withdraw + ",shopWithdraw="
					+ shopWithdraw + "] ======");
			return "param_config/repayRate";
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.repayRatePage error[" + e.getCause() + "]");
			return e.getMessage();
		}
	}

	/**
	 * 钱包参数配置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "walletPage")
	public String walletPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.walletPage ======");
			RecommendAward recommendAward = customerConfigService.queryRecommendAward();
			Map<String, BigDecimal> cashAward = customerConfigService.queryCashAward();
			if (cashAward != null)
			{
				model.addAttribute("cashAward", cashAward.keySet());
			}
			Withdraw walletWithdraw = customerConfigService.queryWalletWithdraw();
			model.addAttribute("recommendAward", recommendAward);
			model.addAttribute("walletWithdraw", walletWithdraw);
			logger.info("====== end ParamConfigController.walletPage ,res[recommendAward=" + recommendAward
					+ ",cashAward=" + cashAward + ",walletWithdraw=" + walletWithdraw + "] ======");
			return "param_config/wallet";
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.walletPage error[" + e.getCause() + "]");
			return e.getMessage();
		}
	}

	/**
	 * 拼拼活动配置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "pinpinPage")
	public String pinpinPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.pinpinPage ======");
			Map<String, Object> jgg = customerConfigService.queryJgg();
			if (jgg == null)
			{
				jgg = new HashMap<String, Object>();
			}
			for (int i = 1; i <= 9; i++)
			{
				Object pic = jgg.get("" + i);
				if (pic == null)
				{
					jgg.put("" + i, "static/images/jgg_bg_default.jpg");
				}
				else
				{
					jgg.put("" + i, "upload" + pic);
				}
			}
			model.addAttribute("jgg", jgg);
			Map<String, Object> sphd = customerConfigService.querySphd();
			if (sphd == null)
			{
				sphd = new HashMap<String, Object>();
			}
			model.addAttribute("sphd", sphd);
			AwardNotice awardNotice = customerConfigService.queryLqgg();
			model.addAttribute("awardNotice", awardNotice);
			logger.info("====== end ParamConfigController.pinpinPage ,res[jgg=" + jgg + ",awardNotice=" + awardNotice
					+ "] ======");
			return "param_config/pinpin";
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.pinpinPage error[" + e.getCause() + "]");
			return e.getMessage();
		}
	}

	/**
	 * 活动开关页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tdkgPage")
	public String tdkgPage(Model model)
	{
		logger.info("====== start.end ParamConfigController.tdkgPage ======");
		return "channel_manegement/channelConfig";
	}

	/**
	 * 保存还款周期/费率配置
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveCycleRate")
	public ResultDo saveCycleRate(HttpServletRequest request)
	{
		CycleRate crt = null;
		try
		{
			logger.info("====== start ParamConfigController.saveCycleRate ======");
			Integer billDay = stringToInteger(request.getParameter("billDay"));
			Integer repayDay = stringToInteger(request.getParameter("repayDay"));
			Integer withinDay = stringToInteger(request.getParameter("withinDay"));
			BigDecimal withinDayRate = stringToBigDecimal(request.getParameter("withinDayRate"));
			Integer increaseDay = stringToInteger(request.getParameter("increaseDay"));
			BigDecimal increaseDayRate = stringToBigDecimal(request.getParameter("increaseDayRate"));
			if (billDay == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("账期天数须大于等于0");
			}
			if (repayDay == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("还款天数须大于等于0");
			}
			if (withinDay == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("账单逾期天数须大于等于0");
			}
			if (withinDayRate == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("费率须大于等于0");
			}
			if (increaseDay == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("递增天数须大于等于0");
			}
			if (increaseDayRate == null)
			{
				logger.info("====== end ParamConfigController.saveCycleRate ======");
				return ResultFactory.getFailedResult("递增费率须大于等于0");
			}
			OverdueRate overdueRate = new OverdueRate();
			overdueRate.setIncreaseDay(increaseDay);
			overdueRate.setIncreaseDayRate(increaseDayRate);
			overdueRate.setWithinDay(withinDay);
			overdueRate.setWithinDayRate(withinDayRate);
			CycleRate cycleRate = new CycleRate();
			cycleRate.setOverdueRate(overdueRate);
			cycleRate.setBillDay(billDay);
			cycleRate.setRepayDay(repayDay);
			crt = customerConfigService.saveCycleRate(cycleRate);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveCycleRate ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveCycleRate ,res[crt=" + crt + "]======");
		return ResultFactory.getSuccessResult(crt);
	}

	/**
	 * 保存逾期账单提醒配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveBillRemind")
	public ResultDo saveBillRemind(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveBillRemind ======");
			String content = request.getParameter("content");
			if (StringUtils.isBlank(content))
			{
				logger.info("====== end ParamConfigController.saveBillRemind ======");
				return ResultFactory.getFailedResult("提醒内容不能为空");
			}
			CustomerConfig old = customerConfigService.saveBillRemind(content);
			logger.info("====== end ParamConfigController.saveBillRemind ,res[old=" + old + "]======");
			return ResultFactory.getSuccessResult(old);
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveBillRemind error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存逾期账单提醒配置异常");
		}
	}

	/**
	 * 保存分期付款费率配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveInstallment")
	public ResultDo saveInstallment(HttpServletRequest request)
	{
		Installment installm = null;
		try
		{
			logger.info("====== start ParamConfigController.saveInstallment ======");
			BigDecimal minAmount = stringToBigDecimal(request.getParameter("minAmount"));
			if (minAmount == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("最低分期额度须大于等于0");
			}
			Integer totalNumber = stringToInteger(request.getParameter("totalNumber"));
			if (totalNumber == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("分期数须大于等于0");
			}
			Integer yqts = stringToInteger(request.getParameter("yqts"));
			if (yqts == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("账单逾期天数须大于等于0");
			}
			BigDecimal yqtsfl = stringToBigDecimal(request.getParameter("yqtsfl"));
			if (yqtsfl == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("费率须大于等于0");
			}
			Integer dzts = stringToInteger(request.getParameter("dzts"));
			if (dzts == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("递增天数须大于等于0");
			}
			BigDecimal dztsfl = stringToBigDecimal(request.getParameter("dztsfl"));
			if (dztsfl == null)
			{
				logger.info("====== end ParamConfigController.saveInstallment ======");
				return ResultFactory.getFailedResult("递增费率须大于等于0");
			}
			OverdueRate overdueRate = new OverdueRate();
			overdueRate.setWithinDay(yqts);
			overdueRate.setWithinDayRate(yqtsfl);
			overdueRate.setIncreaseDay(dzts);
			overdueRate.setIncreaseDayRate(dztsfl);
			Installment installment = new Installment();
			installment.setMinAmount(minAmount);
			installment.setTotalNumber(totalNumber);
			installment.setOverdueRate(overdueRate);
			Map<String, InstallmentCount> map = new HashMap<String, InstallmentCount>();
			for (int i = 2; i <= totalNumber; i++)
			{
				BigDecimal fqfl = stringToBigDecimal(request.getParameter("fqfl" + i));
				if (fqfl == null)
				{
					logger.info("====== end ParamConfigController.saveInstallment ======");
					return ResultFactory.getFailedResult("第" + i + "期分期费率须大于等于0");
				}
				InstallmentCount im = new InstallmentCount();
				im.setNumber(i);
				im.setRate(fqfl);
				map.put("" + i, im);
			}
			installment.setInstallmentDetail(map);
			installm = customerConfigService.saveInstallment(installment);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveInstallment ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveInstallment ,res[installm=" + installm + "]======");
		return ResultFactory.getSuccessResult(installm);
	}

	/**
	 * 保存用户提现配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveWithdraw")
	public ResultDo saveWithdraw(HttpServletRequest request)
	{
		Withdraw withdr = null;
		try
		{
			logger.info("====== start ParamConfigController.saveWithdraw ======");
			BigDecimal zded = stringToBigDecimal(request.getParameter("zded"));
			if (zded == null)
			{
				logger.info("====== end ParamConfigController.saveWithdraw ======");
				return ResultFactory.getFailedResult("最低可提现额度须大于等于0");
			}
			BigDecimal txed = stringToBigDecimal(request.getParameter("txed"));
			if (txed == null)
			{
				logger.info("====== end ParamConfigController.saveWithdraw ======");
				return ResultFactory.getFailedResult("提现总额度比例须大于等于0");
			}
			BigDecimal txsxf = stringToBigDecimal(request.getParameter("txsxf"));
			if (txsxf == null)
			{
				logger.info("====== end ParamConfigController.saveWithdraw ======");
				return ResultFactory.getFailedResult("提现手续费率须大于等于0");
			}
			Integer txcs = stringToInteger(request.getParameter("txcs"));
			if (txcs == null)
			{
				logger.info("====== end ParamConfigController.saveWithdraw ======");
				return ResultFactory.getFailedResult("当日可提现次数须大于等于0");
			}
			Withdraw withdraw = new Withdraw();
			withdraw.setCreditsPercentage(txed);
			withdraw.setRate(txsxf);
			withdraw.setCount(txcs);
			withdraw.setLowestCredits(zded);
			withdr = customerConfigService.saveWithdraw(withdraw);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveWithdraw ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveWithdraw ,res[withdr=" + withdr + "]======");
		return ResultFactory.getSuccessResult(withdr);
	}

	/**
	 * 保存商户提现配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveShopWithdraw")
	public ResultDo saveShopWithdraw(HttpServletRequest request)
	{
		Withdraw withdr = null;
		try
		{
			logger.info("====== start ParamConfigController.saveShopWithdraw ======");
			BigDecimal zded = stringToBigDecimal(request.getParameter("zded"));
			if (zded == null)
			{
				logger.info("====== end ParamConfigController.saveShopWithdraw ======");
				return ResultFactory.getFailedResult("最低可提现额度须大于等于0");
			}
			BigDecimal txsxf = stringToBigDecimal(request.getParameter("txsxf"));
			if (txsxf == null)
			{
				logger.info("====== end ParamConfigController.saveShopWithdraw ======");
				return ResultFactory.getFailedResult("提现手续费率须大于等于0");
			}
			Integer txcs = stringToInteger(request.getParameter("txcs"));
			if (txcs == null)
			{
				logger.info("====== end ParamConfigController.saveShopWithdraw ======");
				return ResultFactory.getFailedResult("当日可提现次数须大于等于0");
			}
			Withdraw withdraw = new Withdraw();
			withdraw.setRate(txsxf);
			withdraw.setCount(txcs);
			withdraw.setLowestCredits(zded);
			withdr = customerConfigService.saveShopWithdraw(withdraw);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveShopWithdraw ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveShopWithdraw ,res[withdr=" + withdr + "]======");
		return ResultFactory.getSuccessResult(withdr);
	}

	public BigDecimal stringToBigDecimal(String val)
	{
		try
		{
			logger.info("====== start ParamConfigController.stringToBigDecimal, req[val=" + val + "] ======");
			if (StringUtils.isBlank(val))
			{
				logger.info("====== end ParamConfigController.stringToBigDecimal ======");
				return null;
			}
			try
			{
				BigDecimal bigDecimal = new BigDecimal(val);
				if (bigDecimal.compareTo(new BigDecimal(0)) == -1)
				{
					logger.info("====== end ParamConfigController.stringToBigDecimal ======");
					return null;
				}
				logger.info("====== end ParamConfigController.stringToBigDecimal ,res[bigDecimal=" + bigDecimal
						+ "]======");
				return bigDecimal;
			}
			catch (Exception e)
			{
				logger.info("====== end ParamConfigController.stringToBigDecimal ======");
				return null;
			}
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.stringToBigDecimal error[" + e.getCause() + "]");
			return null;
		}
	}

	public Integer stringToInteger(String val)
	{
		logger.info("====== start ParamConfigController.stringToInteger, req[val=" + val + "] ======");
		if (StringUtils.isBlank(val))
		{
			logger.info("====== end ParamConfigController.stringToInteger ======");
			return null;
		}
		try
		{
			Integer integer = Integer.parseInt(val);
			if (integer < 0)
			{
				logger.info("====== end ParamConfigController.stringToInteger ======");
				return null;
			}
			logger.info("====== end ParamConfigController.stringToInteger ,res[integer=" + integer + "]======");
			return integer;
		}
		catch (Exception e)
		{
			logger.info("====== end ParamConfigController.stringToInteger ======");
			logger.error("ParamConfigController.stringToInteger error[" + e.getCause() + "]");
			return null;
		}
	}

	/**
	 * 保存推荐人奖励配置
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "saveRecommendAward")
	public ResultDo saveRecommendAward(HttpServletRequest request)
	{
		RecommendAward recommendAwa = null;
		try
		{
			logger.info("====== start ParamConfigController.saveRecommendAward ======");
			BigDecimal tjrje = stringToBigDecimal(request.getParameter("tjrje"));
			if (tjrje == null)
			{
				logger.info("====== end ParamConfigController.saveRecommendAward ======");
				return ResultFactory.getFailedResult("推荐人奖励金额须大于等于0");
			}
			BigDecimal byqrje = stringToBigDecimal(request.getParameter("byqrje"));
			if (byqrje == null)
			{
				logger.info("====== end ParamConfigController.saveRecommendAward ======");
				return ResultFactory.getFailedResult("被邀请人奖励金额须大于等于0");
			}
			BigDecimal zccgjl = stringToBigDecimal(request.getParameter("zccgjl"));
			if (zccgjl == null)
			{
				logger.info("====== end ParamConfigController.saveRecommendAward ======");
				return ResultFactory.getFailedResult("注册成功奖励金额须大于等于0");
			}
			RecommendAward recommendAward = new RecommendAward();
			recommendAward.setRecAward(tjrje);
			recommendAward.setInvitedAward(byqrje);
			recommendAward.setRegAward(zccgjl);
			recommendAwa = customerConfigService.saveRecommendAward(recommendAward);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveRecommendAward ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveRecommendAward ,res[recommendAwa=" + recommendAwa + "]======");
		return ResultFactory.getSuccessResult(recommendAwa);
	}

	/**
	 * 保存拼拼现金面值奖励配置
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "addCashAward")
	public ResultDo addCashAward(HttpServletRequest request)
	{
		Map<String, BigDecimal> cash = null;
		try
		{
			logger.info("====== start ParamConfigController.addCashAward ======");
			BigDecimal xjjlmz = stringToBigDecimal(request.getParameter("xjjlmz"));
			if (xjjlmz == null)
			{
				logger.info("====== end ParamConfigController.addCashAward ======");
				return ResultFactory.getFailedResult("现金奖励面值须大于等于0");
			}
			cash = customerConfigService.addCashAward(xjjlmz);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.addCashAward ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.addCashAward ,res[cash=" + cash + "]======");
		return ResultFactory.getSuccessResult(cash);
	}

	/**
	 * 删除拼拼现金面值奖励配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteCashAward")
	public ResultDo deleteCashAward(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start ParamConfigController.deleteCashAward ======");
			String xjjlmz = request.getParameter("xjjlmz");
			if (StringUtils.isNotBlank(xjjlmz))
			{
				customerConfigService.deleteCashAward(xjjlmz);
			}
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.deleteCashAward ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.deleteCashAward ======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 保存钱包提现配置
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "saveWalletWithdraw")
	public ResultDo saveWalletWithdraw(HttpServletRequest request)
	{
		Withdraw Withdr = null;
		try
		{
			logger.info("====== start ParamConfigController.saveWalletWithdraw ======");
			BigDecimal zded = stringToBigDecimal(request.getParameter("zded"));
			if (zded == null)
			{
				logger.info("====== end ParamConfigController.saveWalletWithdraw ======");
				return ResultFactory.getFailedResult("最低可提现额度须大于等于0");
			}
			BigDecimal txsxf = stringToBigDecimal(request.getParameter("txsxf"));
			if (txsxf == null)
			{
				logger.info("====== end ParamConfigController.saveWalletWithdraw ======");
				return ResultFactory.getFailedResult("提现手续费率须大于等于0");
			}
			Integer txcs = stringToInteger(request.getParameter("txcs"));
			if (txcs == null)
			{
				logger.info("====== end ParamConfigController.saveWalletWithdraw ======");
				return ResultFactory.getFailedResult("当日可提现次数须大于等于0");
			}
			Withdraw withdraw = new Withdraw();
			withdraw.setRate(txsxf);
			withdraw.setCount(txcs);
			withdraw.setLowestCredits(zded);
			Withdr = customerConfigService.saveWalletWithdraw(withdraw);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveWalletWithdraw ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveWalletWithdraw ,res[Withdr=" + Withdr + "]======");
		return ResultFactory.getSuccessResult(Withdr);
	}

	/**
	 * 保存九宫格图片
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "saveJgg")
	public ResultDo saveJgg(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveJgg ======");
			customerConfigService.saveJgg(request);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveJgg ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveJgg ======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 保存活动时间
	 * @param hdsj
	 * @param cxsj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveHdsj")
	public ResultDo saveHdsj(String hdsj, String cxsj, String fpcs)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveHdsj, req[hdsj=" + hdsj + ",cxsj=" + cxsj + ",fpcs="
					+ fpcs + "] ======");
			if (StringUtils.isBlank(hdsj))
			{
				logger.info("====== end ParamConfigController.saveHdsj ======");
				return ResultFactory.getFailedResult("开始时间不能为空");
			}
			hdsj = StringUtils.trim(hdsj);
			Date realHdsj = DateUtils.getFormatDateTime(DateUtils.getFormatDate(new Date(), "yyyy-MM-dd") + " " + hdsj
					+ ":00");
			if (realHdsj == null)
			{
				logger.info("====== end ParamConfigController.saveHdsj ======");
				return ResultFactory.getFailedResult("开始时间格式有误");
			}
			Integer cxsjInt = stringToInteger(cxsj);
			if (cxsjInt == null || cxsjInt == 0)
			{
				logger.info("====== end ParamConfigController.saveHdsj ======");
				return ResultFactory.getFailedResult("持续时间须大于等于1");
			}
			Integer fpcsInt = stringToInteger(fpcs);
			if (fpcsInt == null || fpcsInt == 0)
			{
				logger.info("====== end ParamConfigController.saveHdsj ======");
				return ResultFactory.getFailedResult("翻牌次数须大于等于1");
			}
			/**
			 * count check
			 */
			if (fpcsInt != null && fpcsInt > 9)
			{
				logger.info("====== end ParamConfigController.saveHdsj ======");
				return ResultFactory.getFailedResult("翻牌次数最多为9次");
			}
			customerConfigService.saveHdsj(hdsj, cxsjInt, fpcsInt);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveHdsj ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveHdsj ======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 删除活动时间
	 * @param hdsj
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteHdsj")
	public ResultDo deleteHdsj(String hdsj)
	{
		/**
		 * 拼拼活动关闭时 check
		 */
		try
		{
			logger.info("====== start ParamConfigController.deleteHdsj, req[hdsj=" + hdsj + "] ======");
			if (Integer.parseInt(customerConfigService.isInDate()) > 0)
			{
				logger.info("====== end ParamConfigController.deleteHdsj ======");
				return ResultFactory.getFailedResult("活动开始前十分钟和活动进行中不允许删除活动");
			}
			if (StringUtils.isNotBlank(hdsj))
			{
				customerConfigService.deleteSphd(hdsj);
			}
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.deleteHdsj  ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.deleteHdsj ======");
		return ResultFactory.getSuccessResult();
	}

	@ResponseBody
	@RequestMapping(value = "saveLqgg")
	public ResultDo saveLqgg(String price, String ifNotice)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveLqgg, req[price=" + price + ",ifNotice=" + ifNotice
					+ "] ======");
			BigDecimal priceB = stringToBigDecimal(price);
			if (priceB == null)
			{
				logger.info("====== end ParamConfigController.saveLqgg ======");
				return ResultFactory.getFailedResult("商品单价须大于0");
			}
			Integer ifNoticeInt = stringToInteger(ifNotice);
			if (ifNoticeInt != null)
			{
				ifNoticeInt = Constant.INT_YES;
			}
			else
			{
				ifNoticeInt = Constant.INT_NO;
			}
			AwardNotice awardNotice = new AwardNotice();
			awardNotice.setCompositeIfNotice(ifNoticeInt);
			awardNotice.setPrice(priceB);
			customerConfigService.saveLqgg(awardNotice);
		}
		catch (Exception e)
		{
			logger.info("====== err ParamConfigController.saveLqgg ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveLqgg ======");
		return ResultFactory.getSuccessResult();
	}

	@ResponseBody
	@RequestMapping(value = "savePphdkg")
	public ResultDo savePphdkg(String status)
	{
		logger.info("====== start ParamConfigController.savePphdkg, req[status=" + status + "] ======");
		if (StringUtils.equalsIgnoreCase(status, "1"))
		{
			status = "0";
		}
		else
		{
			status = "1";
		}
		/**
		 * 拼拼活动关闭时 check
		 */
		if (status != null && "0".equals(status.trim()) && Integer.parseInt(customerConfigService.isInDate()) > 0)
		{
			logger.info("====== end ParamConfigController.savePphdkg ======");
			return ResultFactory.getFailedResult("活动时间内不允许关闭");
		}
		CustomerConfig customerConfig = customerConfigService.savePphdkg(status);
		logger.info("====== end ParamConfigController.savePphdkg ,res[customerConfig=" + customerConfig + "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	@ResponseBody
	@RequestMapping(value = "saveTjrjlkg")
	public ResultDo saveTjrjlkg(String status, String amount)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveTjrjlkg, req[status=" + status + ",amount=" + amount
					+ "] ======");
			Integer statusInt = stringToInteger(status);
			if (statusInt == null)
			{
				logger.info("====== end ParamConfigController.saveTjrjlkg ======");
				return ResultFactory.getFailedResult("开关值只能为1或0");
			}
			if (statusInt.intValue() == Constant.INT_YES)
			{
				statusInt = 0;
			}
			else
			{
				statusInt = 1;
			}
			BigDecimal amountB = stringToBigDecimal(amount);
			if (amountB == null)
			{
				logger.info("====== end ParamConfigController.saveTjrjlkg ======");
				return ResultFactory.getFailedResult("投放奖励金额须大于等于0");
			}
			RecommendSwitch recommendSwitch = new RecommendSwitch();
			recommendSwitch.setStatus(statusInt);
			recommendSwitch.setTotalAward(amountB);
			customerConfigService.saveTjrjlkg(recommendSwitch);
		}
		catch (Exception e)
		{
			logger.info("====== err AdActivityController.saveTjrjlkg ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end ParamConfigController.saveTjrjlkg ======");
		return ResultFactory.getSuccessResult();
	}

	/**
	 * 额度提现审核开关
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTxshkg", method = RequestMethod.POST)
	public ResultDo saveTxshkg(String status)
	{
		logger.info("====== start ParamConfigController.saveTxshkg, req[status=" + status + "] ======");
		CustomerConfig customerConfig = null;
		try
		{
			if (StringUtils.equalsIgnoreCase(status, "1"))
			{
				status = "0";
			}
			else
			{
				status = "1";
			}
			customerConfig = customerConfigService.saveTxshkg(status);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveTxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现审核开关异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveTxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现审核开关异常");
		}
		logger.info("====== end ParamConfigController.saveTxshkg ,res[customerConfig=" + customerConfig + "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	/**
	 * 钱包提现审核开关
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveQbtxshkg", method = RequestMethod.POST)
	public ResultDo saveQbtxshkg(String status)
	{
		logger.info("====== start ParamConfigController.saveQbtxshkg, req[status=" + status + "] ======");
		CustomerConfig customerConfig = null;
		try
		{
			if (StringUtils.equalsIgnoreCase(status, "1"))
			{
				status = "0";
			}
			else
			{
				status = "1";
			}
			customerConfig = customerConfigService.saveQbtxshkg(status);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveQbtxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("钱包提现审核开关异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveQbtxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("钱包提现审核开关异常");
		}
		logger.info("====== end ParamConfigController.saveTxshkg ,res[customerConfig=" + customerConfig + "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	/**
	 * 学籍认证开关
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveXjrzkg", method = RequestMethod.POST)
	public ResultDo saveXjrzkg(String status)
	{
		logger.info("====== start ParamConfigController.saveTxshkg, req[xxwrzStatus=" + status + "] ======");
		CustomerConfig customerConfig = null;
		try
		{
			if (StringUtils.equalsIgnoreCase(status, "1"))
			{
				status = "0";
			}
			else
			{
				status = "1";
			}
			customerConfig = customerConfigService.saveXjrzkg(status);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveTxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学籍认证开关异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveTxshkg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学籍认证开关异常");
		}
		logger.info("====== end ParamConfigController.saveTxshkg ,res[customerConfig=" + customerConfig + "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	/**
	 * 学信网认证自动审核开关
	 * @param status 开关状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveXxwrzzdshKg", method = RequestMethod.POST)
	public ResultDo saveXxwrzzdshKg(String status)
	{
		logger.info("====== start ParamConfigController.saveXxwrzzdshKg, req[xxwrzzdshStatus=" + status + "] ======");
		CustomerConfig customerConfig = null;
		try
		{
			//1为开关开启，0为关闭，当传入状态为1时，把状态改为0，反之改为1
			if (StringUtils.equalsIgnoreCase(status, "1"))
			{
				status = "0";
			}
			else
			{
				status = "1";
			}
			customerConfig = customerConfigService.saveXxwrzzdsh(status);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveXxwrzzdshKg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学信网认证自动审核开关异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveXxwrzzdshKg error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学信网认证自动审核开关异常");
		}
		logger.info("====== end ParamConfigController.saveXxwrzzdshKg ,res[customerConfig=" + customerConfig
				+ "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	/**
	 * 提现自动审核失败配置
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "txzdshsbPage", method = RequestMethod.GET)
	public String txzdshsbPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.txzdshsbPage ======");
			List<Map> list = customerConfigService.queryTxzdshcspz(CustomerConfigType.txzdshsb.getType());
			if (null != list && list.size() > 0)
			{
				model.addAttribute("list", list);
			}
			else
			{
				model.addAttribute("list", null);
			}
			logger.info("====== end ParamConfigController.txzdshsbPage ======");
			return "param_config/txzdshsb";
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.txzdshsbPage error[" + e.getCause() + "]");
			return "提现自动审核失败配置异常:" + e.getMessage();
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.txzdshsbPage error[" + e.getCause() + "]");
			return "提现自动审核失败配置异常:";
		}
	}

	/**
	 * 提现自动审核通过配置
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "txzdshtgPage", method = RequestMethod.GET)
	public String txzdshtgPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.txzdshtgPage ======");
			List<Map> list = customerConfigService.queryTxzdshcspz(CustomerConfigType.txzdshtg.getType());
			if (null != list && list.size() > 0)
			{
				model.addAttribute("list", list);
			}
			else
			{
				model.addAttribute("list", null);
			}
			logger.info("====== end ParamConfigController.txzdshtgPage ======");
			return "param_config/txzdshtg";
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.txzdshtgPage error[" + e.getCause() + "]");
			return "提现自动审核通过配置异常:" + e.getMessage();
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.txzdshtgPage error[" + e.getCause() + "]");
			return "提现自动审核通过配置异常:";
		}
	}

	//	/**
	//	 * 保存学籍认证额度配置
	//	 * @param request
	//	 * @return
	//	 */
	//	@ResponseBody
	//	@RequestMapping(value = "saveXjrztghed")
	//	public ResultDo saveXjrztghed(HttpServletRequest request) {
	//		try {
	//			logger.info("====== start ParamConfigController.saveXjrztghed ======");
	//			String xjrztghed= request.getParameter("xjrztghed");
	//			if(StringUtils.isBlank(xjrztghed)){
	//				logger.info("====== end ParamConfigController.saveXjrztghed ======");
	//				return ResultFactory.getFailedResult("学籍认证额度配置数值不能为空");
	//			}
	//			CustomerConfig old = customerConfigService.saveXjrztghed(xjrztghed);
	//			logger.info("====== end ParamConfigController.saveXjrztghed ,res[old=" + old + "]======");
	//			return ResultFactory.getSuccessResult(old);
	//		}
	//		catch (Exception e)
	//		{
	//			logger.error("ParamConfigController.saveXjrztghed error[" + e.getCause() + "]");
	//			return ResultFactory.getFailedResult("学籍认证额度配置异常");
	//		}
	//	}

	/**
	 * 任务额度配置
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveRwedpz", method = RequestMethod.POST)
	public ResultDo saveRwedpz(String list)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveRwedpz ======");
			CustomerConfig customerConfig = customerConfigService.saveRwedpz(list);
			logger.info("====== end ParamConfigController.saveRwedpz ,res[customerConfig=" + customerConfig + "]======");
			return ResultFactory.getSuccessResult(customerConfig);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveRwedpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("任务额度配置异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveRwedpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("任务额度配置异常");
		}
	}

	/**
	 * 20160107 add by zhangqi
	 * 提现自动审核失败保存
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTxzdshsbpz", method = RequestMethod.POST)
	public ResultDo saveTxzdshsbpz(String list)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveTxzdshsbpz ======");
			CustomerConfig customerConfig = customerConfigService.saveTxzdshcspz(list,
					CustomerConfigType.txzdshsb.getType());
			logger.info("====== end ParamConfigController.saveTxzdshsbpz ,res[customerConfig=" + customerConfig
					+ "]======");
			return ResultFactory.getSuccessResult(customerConfig);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveTxzdshsbpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现自动审核失败保存异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveTxzdshsbpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现自动审核失败保存异常");
		}
	}

	/**
	 * 20160107 add by zhangqi
	 * 保存提现自动审核通过配置
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveTxzdshtgpz", method = RequestMethod.POST)
	public ResultDo saveTxzdshtgpz(String list)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveTxzdshtgpz ======");
			CustomerConfig customerConfig = customerConfigService.saveTxzdshcspz(list,
					CustomerConfigType.txzdshtg.getType());
			logger.info("====== end ParamConfigController.saveTxzdshtgpz ,res[customerConfig=" + customerConfig
					+ "]======");
			return ResultFactory.getSuccessResult(customerConfig);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveTxzdshtgpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现自动审核通过配置异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveTxzdshtgpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("提现自动审核通过配置异常");
		}
	}

	/**
	 * 额度配置管理
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "zdyqpzPage", method = RequestMethod.GET)
	public String zdyqpzPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.zdyqpzPage ======");
			Delay delay = customerConfigService.queryDelayConfig();
			if (null != delay)
			{
				model.addAttribute("delay", delay);
			}
			else
			{
				model.addAttribute("list", null);
			}
			logger.info("====== end ParamConfigController.zdyqpzPage ======");
			return "param_config/billDelaypz";
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.zdyqpzPage error[" + e.getMessage() + "]");
			return "额度配置管理异常:" + e.getMessage();
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.zdyqpzPage error[" + e.getCause() + "]");
			return "额度配置管理异常:";
		}
	}

	/**
	 * 账单延期参数配置保存
	 * @param yqcs 延期次数
	 * @param sxf 每次延期手续费
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveZdyqpz", method = RequestMethod.POST)
	public ResultDo saveZdyqpz(String yqcs, String sxf)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveZdyqpz ======");
			CustomerConfig customerConfig = customerConfigService.saveZdyqpz(yqcs, sxf);
			logger.info("====== end ParamConfigController.saveZdyqpz ,res[customerConfig=" + customerConfig + "]======");
			return ResultFactory.getSuccessResult(customerConfig);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveZdyqpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("账单延期配置异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveZdyqpz error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("账单延期配置异常");
		}
	}

	/**
	 * 短信切换页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "dxqhPage")
	public String dxqhPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.dxqhPage ======");
			CustomerConfig dxqh = customerConfigService.queryDxqh();
			if (dxqh == null)
			{
				model.addAttribute("dxqh", 0);
				model.addAttribute("dxqhStatus", "亿美短信");
			}
			else
			{
				if (StringUtils.equalsIgnoreCase(dxqh.getConfigExp(), "1"))
				{
					model.addAttribute("dxqh", 1);
					model.addAttribute("dxqhStatus", "创蓝短信");
				}
				else
				{
					model.addAttribute("dxqh", 0);
					model.addAttribute("dxqhStatus", "亿美短信");
				}
			}
			logger.info("====== end ParamConfigController.dxqhPage ======");
			return "param_config/dxqh";
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.dxqhPage error[" + e.getCause() + "]");
			return e.getMessage();
		}
	}

	/**
	 * 短信切换
	 * @param status 短信切换 0.亿美短信 1.创蓝短信
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveDxqh", method = RequestMethod.POST)
	public ResultDo saveDxqh(String status)
	{
		logger.info("====== start ParamConfigController.saveDxqh, req[saveDxqh=" + status + "] ======");
		CustomerConfig customerConfig = null;
		try
		{
			//0.亿美短信 1.创蓝短信，当传入状态为1时，把状态改为0，反之改为1
			if (StringUtils.equalsIgnoreCase(status, "1"))
			{
				status = "0";
			}
			else
			{
				status = "1";
			}
			customerConfig = customerConfigService.saveDxqh(status);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveDxqh error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学信网认证自动审核开关异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveDxqh error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("学信网认证自动审核开关异常");
		}
		logger.info("====== end ParamConfigController.saveDxqh ,res[customerConfig=" + customerConfig + "]======");
		return ResultFactory.getSuccessResult(customerConfig);
	}

	/**
	 * 卡牌活动奖品配置
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "kphdpzglPage", method = RequestMethod.GET)
	public String kphdpzglPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.kphdpzglPage ======");
			ActivityConfiguration activityConfiguration = customerConfigService.queryCardactivity();
			if (activityConfiguration != null)
			{
				List<String> url = activityConfiguration.getImgUrls();
				if (null == url || url.size() == 0 || StringUtils.isBlank(url.get(0)))
				{
					model.addAttribute("imgUrls", "");
				}
				else
				{
					String urls = "";
					for (int i = 0; i < url.size(); i++)
					{
						if ((i + 1) == url.size())
						{
							urls = urls + url.get(i);
						}
						else
						{
							urls = urls + url.get(i) + ",";
						}
					}
					model.addAttribute("imgUrls", urls);
				}
			}
			//查询各个奖品今日掉落总个数
			//获取当前日期
			Date date = DateUtils.getCurrDate();
			String begin = DateUtils.getFormatDateTime(DateUtils.dayBegin(date));
			String end = DateUtils.getFormatDateTime(DateUtils.dayEnd(date));
			String sql = "SELECT count(*),prize_id,card_char from fyd_card_game_result WHERE created_time > '" + begin
					+ "' and created_time < '" + end + "' GROUP BY prize_id ";
			List<?> list = em.createNativeQuery(sql).getResultList();
			if (CollectionUtils.isNotEmpty(list))
			{
				int size = list.size();
				for (int i = 0; i < size; i++)
				{
					Object[] oArr = (Object[]) list.get(i);
					if (null != oArr && oArr.length > 2)
					{
						if (null != oArr[1])
						{
							model.addAttribute("name" + String.valueOf(oArr[1]), (null != oArr[0] ? oArr[0] : 0));
						}
					}
				}
			}
			model.addAttribute("activityConfiguration", activityConfiguration);
			logger.info("====== end ParamConfigController.kphdpzglPage ======");
			return "param_config/kphdpzgl";
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.kphdpzglPage error[" + e.getCause() + "]");
			return "卡牌活动配置异常:" + e.getMessage();
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.kphdpzglPage error[" + e.getCause() + "]");
			return "卡牌活动配置异常:";
		}
	}

	/**
	 * 保存活动等配置
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveActivityTotal", method = RequestMethod.POST)
	public ResultDo saveActivityTotal(String title, String prepareStartTime, String prepareEndTime, String startTime,
			String endTime, String closeTime, String imgUrls, String activityRemark, String gameRemark,
			String actionUrl, String endActionUrl, String actionParas, String list)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveActivityTotal ======");
			ActivityConfiguration activityConfiguration = customerConfigService.saveActivityTotal(title,
					prepareStartTime, prepareEndTime, startTime, endTime, closeTime, imgUrls, activityRemark,
					gameRemark, actionUrl, endActionUrl, actionParas, list);
			logger.info("====== end ParamConfigController.saveActivityTotal ======");
			return ResultFactory.getSuccessResult(activityConfiguration);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveActivityTotal error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存活动等配置异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveActivityTotal error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存活动等配置异常");
		}
	}

	/**
	 * 查找短信，push模板
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findMessageModel")
	public ResultDo findMessageModel(Integer id)
	{
		List<MessageModel> messageModels = null;
		logger.info("ParamConfigController.findMessageModel start");
		try
		{
			if (null != id && id >= 0)
			{
				return ResultFactory.getSuccessResult(customerConfigService.findMessageModel(id));
			}
			else
			{
				messageModels = customerConfigService.findMessageModel();
			}
		}
		catch (ServiceException e)
		{

		}
		catch (Exception e)
		{
		}
		logger.info("ParamConfigController.findMessageModel end");
		return ResultFactory.getSuccessResult(messageModels);
	}

	/**
	 * 消息模板配置管理
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "xxmbglPage", method = RequestMethod.GET)
	public String xxmbglPage(Model model)
	{
		try
		{
			logger.info("====== start ParamConfigController.xxmbglPage ======");
			List<MessageModel> list = customerConfigService.queryXxmbpz();
			if (null != list && list.size() > 0)
			{
				model.addAttribute("list", list);
			}
			else
			{
				model.addAttribute("list", null);
			}
			logger.info("====== end ParamConfigController.xxmbglPage ======");
			return "param_config/xxmbgl";
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.xxmbglPage error[" + e.getCause() + "]");
			return "消息模板配置管理异常:" + e.getMessage();
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.xxmbglPage error[" + e.getCause() + "]");
			return "消息模板配置管理异常:";
		}
	}

	/**
	 * 保存消息模板配置
	 * @param list
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveMessageModel", method = RequestMethod.POST)
	public ResultDo saveMessageModel(String list)
	{
		try
		{
			logger.info("====== start ParamConfigController.saveMessageModel ======");
			CustomerConfig customerConfig = customerConfigService.saveMessageModel(list);
			logger.info("====== end ParamConfigController.saveMessageModel ======");
			return ResultFactory.getSuccessResult(customerConfig);
		}
		catch (ServiceException e)
		{
			logger.error("ParamConfigController.saveMessageModel error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存消息模板配置异常:" + e.getMessage());
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveMessageModel error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存消息模板配置异常");
		}
	}

	/**
	 * 跳转消息推送页面
	 */
	@RequestMapping(value = "toSendMessage")
	public String toSendMessage()
	{
		return "param_config/sendMessage";
	}

	@RequestMapping(value = "getSendMessage")
	@ResponseBody
	public Page<FXAdvert> getSendMessage(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXAdvert> pageList = null;
		try
		{

			logger.info("====== start ParamConfig.getSendMessage ======req");
			// 拼接查询sql
			pageList = indexService.getAllMessage(new PageRequest(page - 1, rows, Sort.Direction.DESC, "updateTime"));
			logger.info("====== end ParamConfig.getSendMessage ,res[_listFXProduct=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("FXChannelController.getChannelList error[" + e.getMessage() + "]");
			e.printStackTrace();
		}
		return pageList;
	}

	@RequestMapping(value = "findMessageById")
	@ResponseBody
	public ResultDo findMessageById(String id)
	{
		FXAdvert adv = null;
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("主键ID不能为空");
		}
		try
		{
			logger.info("====== start EnterpriseController.findEnterpriseById ======");
			adv = indexService.findMessageById(id);
			logger.info("====== end EnterpriseController.findEnterpriseById ,res[fxEnterprise=" + adv + "] ======");
		}
		catch (Exception e)
		{
			logger.error("EnterpriseController.findEnterpriseById error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(adv);
	}

	@RequestMapping(value = "saveMessage")
	@ResponseBody
	@Transactional(readOnly = false)
	public ResultDo saveMessage(HttpServletRequest request)
	{
		try
		{
			String content = request.getParameter("ta1");
			String id = request.getParameter("id");
			logger.info("====== start ParamConfigController.saveMessage======");
			FXAdvert advback = new FXAdvert();
			FXAdvert adv = new FXAdvert();
			if (!"".equals(id))
			{
				adv = indexService.findMessageById(id);
				adv.setContent(content);
				adv.setUpdateTime(new Date());
				// 保存
				advback = indexService.saveMessage(adv);
			}
			else
			{
				adv.setContent(content);
				adv.setCreateTime(new Date());
				adv.setStatus(0);
				adv.setUpdateTime(new Date());
				advback = indexService.saveMessage(adv);
			}
			logger.info("====== end ParamConfigController.saveMessage======");
			if (null == advback)
			{
				return ResultFactory.getFailedResult("保存失败");

			}
			else
			{
				return ResultFactory.getSuccessResult("保存成功!");
			}
		}
		catch (Exception e)
		{
			logger.error("ParamConfigController.saveMessage error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}
	/**
	 * 跳转黑白名单页面
	 */
	@RequestMapping(value = "toBlackWhite")
	public String toBlackWhite()
	{
		return "param_config/blackWhiteList";
	}
	
	@RequestMapping(value = "toYuyuexiugaizhekou")
	public String toYuyuexiugaizhekou()
	{
		return "param_config/yuyuexiugaizhekou";
	}
	
	@RequestMapping(value = "toYuyuekaiguantongdao")
	public String toYuyuekaiguantongdao()
	{
		return "param_config/yuyuekaiguantongdao";
	}
	
	@RequestMapping(value = "toRepeatRecharge")
	public String toRepeatRecharge()
	{
		return "param_config/repeatRecharge";
	}
	
	@RequestMapping(value = "toTaobaoConfig")
	public String toTaobaoConfig()
	{
		return "param_config/taobaoConfig";
	}
}
