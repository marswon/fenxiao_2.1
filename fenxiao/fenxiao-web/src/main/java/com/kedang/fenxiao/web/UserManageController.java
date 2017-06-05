package com.kedang.fenxiao.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.AdminRole;
import com.kedang.fenxiao.entity.FXChannel;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.service.AdminRoleService;
import com.kedang.fenxiao.service.FXChannelService;
import com.kedang.fenxiao.service.RoleService;
import com.kedang.fenxiao.service.UserRoleService;
import com.kedang.fenxiao.service.account.AccountService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "userManage")
public class UserManageController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(UserManageController.class);
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FXChannelService fxChannelService;

	@RequestMapping(value = "userList")
	public String findAllUser()
	{
		return "user_management/userList";
	}

	@RequestMapping(value = "getChannelList")
	@ResponseBody
	public ResultDo getChannelList()
	{
		try
		{
			logger.info("====== start UserManageController.getChannelList ======");
			List<FXChannel> channelList = fxChannelService.findAllChannel();
			logger.info("====== end UserManageController.getChannelList ,res[ChannelList=" + channelList + "] ======");
			return ResultFactory.getSuccessResult(channelList);
		}
		catch (Exception e)
		{
			logger.error("UserManageController.getChannelList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取通道列表异常");
		}
	}
	
	@RequestMapping(value = "getEnterpriseList")
	@ResponseBody
	public ResultDo getRoleList(String businessType)
	{
		try
		{
			System.out.println(businessType);
			logger.info("====== start UserManageController.getEnterpriseList ======");
			List<FXEnterprise> roles = userService.getRoleList();
			logger.info("====== end UserManageController.getEnterpriseList ,res[enterprise=" + roles + "] ======");
			return ResultFactory.getSuccessResult(roles);
		}
		catch (Exception e)
		{
			logger.error("UserManageController.getRoleList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取用户列表异常");
		}
	}
	@RequestMapping(value = "getEnterpriseListByBusinessType")
	@ResponseBody
	public ResultDo getRoleListByBusinessType(String businessType)
	{
		try
		{
			System.out.println(businessType);
			logger.info("====== start UserManageController.getEnterpriseList ======");
			List<FXEnterprise> roles = userService.getRoleListByBusinessType(businessType);
			logger.info("====== end UserManageController.getEnterpriseList ,res[enterprise=" + roles + "] ======");
			return ResultFactory.getSuccessResult(roles);
		}
		catch (Exception e)
		{
			logger.error("UserManageController.getRoleList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取用户列表异常");
		}
	}

	@RequestMapping(value = "getUserRoleList")
	@ResponseBody
	public Page<AdminRole> getUserRoleList(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<AdminRole> pageList = null;
		try
		{
			logger.info("====== start UserManageController.getUserRoleList, req[page=" + page + ",rows=" + rows
					+ "] ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = userService.getUserRoleInformation(searchParams, new PageRequest(page - 1, rows,
					Sort.Direction.ASC, "id"));
			logger.info("====== end UserManageController.getUserRoleList ======");
			return pageList;
		}
		catch (Exception e)
		{
			logger.error("UserManageController.getUserRoleList error[" + e.getCause() + "]");
			return pageList;
		}
	}

	@RequestMapping(value = "getUser")
	@ResponseBody
	public ResultDo getAdmin(Long id)
	{
		try
		{
			logger.info("====== start UserManageController.getUser, req[id=" + id + "] ======");
			AdminRole role = adminRoleService.getAdminRole(id);
			logger.info("====== end UserManageController.getUser ,res[role=" + role + "] ======");
			return ResultFactory.getSuccessResult(role);
		}
		catch (Exception e)
		{
			logger.error("UserManageController.getUser error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取管理员信息异常");
		}
	}

	/***
	 * 更新管理员信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateUser")
	@ResponseBody
	public ResultDo updateAdmin(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start UserManageController.updateUser ======");
			request.setCharacterEncoding("utf-8");
			String name = request.getParameter("name");
			String userId = request.getParameter("fxUserId");

			String email = request.getParameter("email");
			String roleId = request.getParameter("roleId");
			String adminRoleId = request.getParameter("adminRoleId");
			String password = request.getParameter("password");
			String account = request.getParameter("account");
			String ip = getIpAddr(request);
			String eId = null;
			if (roleId.equals("2"))
			{
				eId = (null != request.getParameter("eId") ? request.getParameter("eId") : null);
			}else{
				eId = "10000";
			}
			String mphone = request.getParameter("mphone");
			int status = Integer.parseInt(request.getParameter("status"));
			if (StringUtils.isBlank(roleId))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请选择分组");
			}
			if (StringUtils.isBlank(name))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请输入名称");
			}
			if (StringUtils.isBlank(email))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请输入邮箱");
			}
			if (StringUtils.isBlank(password))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请输入密码");
			}
			if (StringUtils.isBlank(mphone))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请输入手机");
			}
			if (StringUtils.isBlank(account))
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getFailedResult("请输入账号");
			}
			AdminRole adro = userService.updateAdminRole(eId, roleId, name, mphone, email, status, userId, adminRoleId,
					password, account, ip);
			if (adro != null)
			{
				logger.info("====== end UserManageController.updateUser ======");
				return ResultFactory.getSuccessResult();
			}
		}
		catch (Exception e)
		{
			logger.info("====== err UserManageController.updateUser ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end UserManageController.updateUser ======");
		return ResultFactory.getFailedResult("更新失败");
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "saveAdminInfo")
	@ResponseBody
	public ResultDo saveAdminInfo(HttpServletRequest request, AdminInfo adinfo)
	{
		try
		{
			logger.info("====== start AdminManageController.saveAdminInfo, req[adinfo=" + adinfo + "] ======");
			if (StringUtils.isBlank(adinfo.getLoginName()))
			{
				logger.info("====== end AdminManageController.saveAdminInfo ======");
				return ResultFactory.getFailedResult("请输入用户名");
			}
			if (StringUtils.isBlank(adinfo.getPwd()))
			{
				logger.info("====== end AdminManageController.saveAdminInfo ======");
				return ResultFactory.getFailedResult("请输入密码");
			}
			String roleId = request.getParameter("roleId");
			if (StringUtils.isBlank(roleId))
			{
				logger.info("====== end AdminManageController.saveAdminInfo ======");
				return ResultFactory.getFailedResult("请选择分组");
			}
			AdminInfo aif = accountService.registerAdmin(adinfo);
			//			adminRoleService.saveAdminRole(Long.parseLong(roleId),aif);
			logger.info("====== end AdminManageController.saveAdminInfo ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.saveAdminInfo error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存管理员信息异常");
		}
	}

	@RequestMapping(value = "saveRole")
	@ResponseBody
	public ResultDo saveRole(HttpServletRequest request, FXRole role)
	{
		try
		{
			logger.info("====== start AdminManageController.saveRole, req[role=" + role + "] ======");
			if (StringUtils.isBlank(role.getName()))
			{
				logger.info("====== end AdminManageController.saveRole ======");
				return ResultFactory.getFailedResult("请输入名称");
			}
			roleService.saveRole(role);
			logger.info("====== end AdminManageController.saveRole ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.saveRole error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存角色信息异常");
		}
	}

	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取当前登录用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCurrentUser")
	public ResultDo getCurrentUser(){
		logger.info("====start UserManageController.getCurrentUser====");
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		logger.info("====end UserManageController.getCurrentUser====");
		return ResultFactory.getSuccessResult(shiroUser);
	}
}
