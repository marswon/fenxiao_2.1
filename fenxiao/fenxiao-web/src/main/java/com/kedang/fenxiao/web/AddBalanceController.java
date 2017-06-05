package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.AdminRole;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXRecharge;
import com.kedang.fenxiao.service.AddBalanceService;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.AdminRoleService;
import com.kedang.fenxiao.service.FXEnterpriseService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "addBalance")
public class AddBalanceController
{
	private Logger logger = LoggerFactory.getLogger(AddBalanceController.class);

	@Autowired
	private AdminInfoService adminInfoService;

	@Autowired
	private AdminRoleService adminRoleService;

	@Autowired
	private FXEnterpriseService fxEnterpriseService;

	@Autowired
	private AddBalanceService addBalanceService;

	@Autowired
	private ResourcesConfig resourcesConfig;
	/**
	 * 打开加款页面
	 * @return
	 */
	@RequestMapping(value = "openAddBalanceView")
	public String openAddBalanceView()
	{
		return "enterprise/addBalance";
	}

	@RequestMapping(value = "findEnterprise")
	@ResponseBody
	public ResultDo checkUser()
	{
		try
		{
			logger.info("====== start AddBalanceController.findEnterprise ======");

			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

			AdminRole adminRole = adminRoleService.findRoleByAdminId(shiroUser.getId());
			System.out.println(adminRole + "=====================");
			String roleId = adminRole.getRole().getId().toString();

			if ("6".equals(roleId) || "5".equals(roleId))
			{
				List<FXEnterprise> list = fxEnterpriseService.getEnterpriseList();
				logger.info("====== end AddBalanceController.getEnterpriseList  ======");
				return ResultFactory.getSuccessResult(list);
			}
			else if("7".equals(roleId)){
				List<FXEnterprise> list = fxEnterpriseService.getGasEnterpriseList(3);
				logger.info("====== end AddBalanceController.getGasEnterpriseList  ======");
				return ResultFactory.getSuccessResult(list);
			}
			else if("3".equals(roleId)){
				List<FXEnterprise> list = fxEnterpriseService.getNoGasEnterpriseList(3);
				logger.info("====== end AddBalanceController.getGasEnterpriseList  ======");
				return ResultFactory.getSuccessResult(list);
			}
			else
			{
				logger.info("====== end AddBalanceController.getEnterpriseList  ======");
				List<Object> list1 = new ArrayList<Object>();
				list1.add(adminRole.getAdminInfo().getFxEnterprise());
				return ResultFactory.getSuccessResult(list1);
			}

		}
		catch (Exception e)
		{
			logger.error("AdminManageController.getRoleList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取角色列表异常");
		}
	}

	@RequestMapping(value = "saveAddBalance")
	@ResponseBody
	public ResultDo saveAddBalance(HttpServletRequest request, String mode)
	{
		logger.info("====== start saveAddBalanceController.saveAddBalance" + "]======");
		try
		{
			String eId = request.getParameter("eId");
			String accountName = request.getParameter("accountName");
			String addMode = mode;
			String amount = request.getParameter("amount");
			String checkAmount = request.getParameter("checkAmount");
			String desc = request.getParameter("desc");
			String addBalanceType = request.getParameter("addType");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			// 参数效验
			if (StringUtils.isBlank(eId))
			{
				return ResultFactory.getFailedResult("请选择加款企业");
			}
			if (StringUtils.isBlank(addBalanceType))
			{
				return ResultFactory.getFailedResult("请选择加款类型");
			}
			if ("1".equals(addBalanceType) && StringUtils.isBlank(addMode))
			{
				return ResultFactory.getFailedResult("请选择加款方式");
			}
			if ("1".equals(addBalanceType) && StringUtils.isBlank(accountName))
			{
				return ResultFactory.getFailedResult("请填写打款人姓名");
			}
			if (StringUtils.isBlank(desc))
			{
				return ResultFactory.getFailedResult("请填写备注");
			}
			

			// 注释该行，允许负数加款
			//			if(!amount.matches("[\\d.]+")){
			//				return ResultFactory.getFailedResult("加款金额不正确");
			//			}
			//			if(Double.parseDouble(amount)<=0){
			//				return ResultFactory.getFailedResult("加款金额必须大于0");
			//			}
			if (!amount.equals(checkAmount))
			{
				return ResultFactory.getFailedResult("打款金额不一致");
			}
			//			if ("".equals(addMode))
			//			{
			//				return ResultFactory.getFailedResult("未选择加款方式");
			//			}
			FXEnterprise enterprise = fxEnterpriseService.findEnterpriseById(eId);
			Integer crestValue = enterprise.getCrestValue();
			Long balance = enterprise.getBalance();
			if(eId.equals(resourcesConfig.getConfigString("crestValue.flow")) && (Integer.parseInt(amount)*1000 + balance > crestValue && crestValue!=0)){
				return ResultFactory.getFailedResult("该账户余额上限："+ crestValue/1000 +"元");
			}
			if(eId.equals(resourcesConfig.getConfigString("crestValue.bill")) && (Integer.parseInt(amount)*1000 + balance > crestValue && crestValue!=0)){
				return ResultFactory.getFailedResult("该账户余额上限："+ crestValue/1000 +"元");
			}
			if(eId.equals(resourcesConfig.getConfigString("crestValue.gasCard")) && (Integer.parseInt(amount)*1000 + balance > crestValue && crestValue!=0)){
				return ResultFactory.getFailedResult("该账户余额上限："+ crestValue/1000 +"元");
			}
			FXRecharge recharge = new FXRecharge();
			recharge.setAmount((long) (Double.parseDouble(amount) * 1000));
			if ("1".equals(addBalanceType))
			{
				recharge.setAccountName(accountName);
				String[] s = addMode.split("，");
				String str = s[1];
				String[] message = str.split("（");
				message[1] = message[1].substring(0, message[1].length() - 1);
				System.out.println("银行名称" + message[0] + ",银行账号" + message[1]);
				recharge.setAccount(message[1]);
				recharge.setBankName(message[0]);
				if ("支付宝".equals(message[0]))
				{
					recharge.setType(1);
				}
				else
				{
					recharge.setType(0);
				}
			}
			else if ("2".equals(addBalanceType))
			{
				recharge.setAccountName("--");
				recharge.setAccount("--");
				recharge.setBankName("--");
			}

			//			FXEnterprise fxEnterprise = fxEnterpriseService.findEnterpriseById(eId);
			//			recharge.setFxEnterprise(fxEnterprise);
			recharge.seteId(eId);
			recharge.setStatus(0);
			recharge.setDescription(desc);
			recharge.setSubmitTime(new Date());
			Long userId = shiroUser.getId();
			AdminInfo admin = adminInfoService.findAdminInfoById(userId);
			admin.setEmail("123456@qq.com");
			recharge.setAdminInfo(admin);

			//设置加款类型1加款2授信
			recharge.setAddBalanceType(Integer.valueOf(addBalanceType));
			//审核通过前，授信债务为0
			recharge.setDebt(0);

			FXRecharge fxRecharge = addBalanceService.addRecharge(recharge);
			logger.info("====== end AddBalanceController.save ======");

			if (null == fxRecharge)
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
			logger.error("fxRechargeController.save error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}
}
