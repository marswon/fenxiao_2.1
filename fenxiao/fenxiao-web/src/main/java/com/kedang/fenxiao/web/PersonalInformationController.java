package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "personalInformation")
public class PersonalInformationController
{
	private Logger logger = LoggerFactory.getLogger(PersonalInformationController.class);

	@Autowired
	private AdminInfoService adminInfoService;
	private static final int SALT_SIZE = 8;
	public static final int HASH_INTERATIONS = 1024;

	@RequestMapping(value="toChangePassword")
	public String toChangePwdView(){
		return "personal_information/changePassword";
	}
	
	@RequestMapping(value="toShowProduct")
	public String toShowProduct(){
		return "personal_information/EnterpriseProductInformation";
	}
	
	@RequestMapping(value="changePwd")
	@ResponseBody
	@Transactional(readOnly=false)
	public ResultDo changePwd(HttpServletRequest request){
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String checkPwd = request.getParameter("checkPwd");
		try {
			logger.info("====== start PersonalInformationController.changePwd()======");
			if(StringUtils.isBlank(oldPwd)){
				logger.info("====== PersonalInformationController.changePwd() ======");
				return ResultFactory.getFailedResult("请输入原密码");
			}
			if(StringUtils.isBlank(newPwd)){
				logger.info("====== PersonalInformationController.changePwd() ======");
				return ResultFactory.getFailedResult("请输入新密码");
			}
			if(StringUtils.isBlank(checkPwd)){
				logger.info("====== PersonalInformationController.changePwd() ======");
				return ResultFactory.getFailedResult("请输入确认密码");
			}
			if(!checkPwd.equals(newPwd)){
				logger.info("====== PersonalInformationController.changePwd() ======");
				return ResultFactory.getFailedResult("两次密码输入不一致");
			}
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
			List<String> list = entryptPassword(oldPwd, admin.getSalt());
	
			if(!list.get(0).equals(admin.getPwd())){
				logger.info("====== PersonalInformationController.changePwd() ======");
				return ResultFactory.getFailedResult("原密码不正确");
			}
			List<String> list1 = entryptPassword(newPwd, null);

			admin.setPwd(list1.get(0));
			admin.setSalt(list1.get(1));
			AdminInfo adminBack = adminInfoService.saveAdminInfo(admin);
			if(adminBack!=null){
				logger.info("====== end PersonalInformationController.changePwd() ======");
				return ResultFactory.getSuccessResult();
			}
//			adminRoleService.saveAdminRole(Long.parseLong(roleId),aif);	
			logger.info("====== end PersonalInformationController.changePwd() ======");
			return ResultFactory.getFailedResult("更新失败");
		}
		catch (Exception e)
		{
			logger.error("PersonalInformationController.changePwd error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存管理员信息异常");
		}
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public List<String> entryptPassword(String password, String salt)
	{
		List<String> list = new ArrayList<String>();
		byte[] byteSalt = null;
		if (StringUtils.isBlank(salt))
		{
			byteSalt = Digests.generateSalt(SALT_SIZE);
			salt = Encodes.encodeHex(byteSalt);
		}
		else
		{
			byteSalt = Encodes.decodeHex(salt);
		}
		byte[] hashPassword = Digests.sha1(password.getBytes(), byteSalt, HASH_INTERATIONS);
		list.add(Encodes.encodeHex(hashPassword));
		list.add(salt);
		return list;
	}
	
}
