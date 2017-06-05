package com.kedang.fenxiao.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
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
import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.AdminRoleService;
import com.kedang.fenxiao.service.RoleService;
import com.kedang.fenxiao.service.account.AccountService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "adminManage")
public class AdminManageController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(AdminManageController.class);
	@Autowired
	private AdminRoleService adminRoleService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private AccountService accountService;
	@RequestMapping(value="adminList")
	public String customerFundsTradingPage() {
		return "admin_management/adminList";
	}
	
	@RequestMapping(value = "getRoleList")
	@ResponseBody
	public ResultDo getRoleList(){
		try {
			logger.info("====== start AdminManageController.getRoleList ======");
			List<FXRole> roles = roleService.getRoleList();
			logger.info("====== end AdminManageController.getRoleList ,res[roles=" + roles + "] ======");
			return ResultFactory.getSuccessResult(roles);
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.getRoleList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取角色列表异常");
		}
	}
	
	
	@RequestMapping(value = "getAdminRoleList")
	@ResponseBody
	public Page<AdminRole> getAdminRoleList(HttpServletRequest request,@RequestParam(value="page",defaultValue ="1",required = false) int page,
							@RequestParam(value="rows",defaultValue ="10",required = false) int rows) {
		Page<AdminRole> pageList = null;
		try {
			logger.info("====== start AdminManageController.getAdminRoleList, req[page=" + page + ",rows=" + rows + "] ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = adminRoleService.getAdminRoleInformation(searchParams,new PageRequest(page - 1, rows, Sort.Direction.ASC, "id"));
			logger.info("====== end AdminManageController.getAdminRoleList ======");
			return pageList;
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.getAdminRoleList error[" + e.getCause() + "]");
			return pageList;
		}
	}
	
	@RequestMapping(value = "getAdmin")
	@ResponseBody
	public ResultDo getAdmin(Long id){
		try {
			logger.info("====== start AdminManageController.getAdmin, req[id=" + id + "] ======");
			AdminRole role = adminRoleService.getAdminRole(id);
			logger.info("====== end AdminManageController.getAdmin ,res[role=" +role + "] ======");
			return ResultFactory.getSuccessResult(role);
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.getAdmin error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取管理员信息异常");
		}
	}
	
	@RequestMapping(value = "admin/info")
	@ResponseBody
	public ResultDo  getAdmininfo(){
		try {
			Subject subject = ThreadContext.getSubject();
			Object user = subject.getPrincipal();
			System.out.println("##########################################"+user);

			return ResultFactory.getSuccessResult(user) ;
		}
		catch (Exception e)
		{
			logger.error("AdminManageController.getAdmin error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取登录信息异常");
		}
	}
	
	/***
	 * 更新管理员信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateAdmin")
	@ResponseBody
	public ResultDo updateAdmin(HttpServletRequest request){
		try{
		logger.info("====== start AdminManageController.updateAdmin ======");
		request.setCharacterEncoding("utf-8");
		String roleId = request.getParameter("roleId");
		
		String adminRoleId = request.getParameter("adminRoleId");
		String realname = request.getParameter("realname");
		String mphone = request.getParameter("mphone");
		if(StringUtils.isBlank(roleId)){
			logger.info("====== end AdminManageController.updateAdmin ======");
			return ResultFactory.getFailedResult("请选择分组");
		}
		AdminRole adro = adminRoleService.updateAdminRole(Long.parseLong(adminRoleId), Long.parseLong(roleId), realname, mphone);
		if(adro != null){	
			logger.info("====== end AdminManageController.updateAdmin ======");
			return ResultFactory.getSuccessResult();
		}
		}catch(Exception e){
			logger.info("====== err AdminManageController.updateAdmin ======"+e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}
		logger.info("====== end AdminManageController.updateAdmin ======");
		return ResultFactory.getFailedResult("更新失败");
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "saveAdminInfo")
	@ResponseBody
	public ResultDo saveAdminInfo(HttpServletRequest request,AdminInfo adinfo){
		try {
			logger.info("====== start AdminManageController.saveAdminInfo, req[adinfo=" + adinfo + "] ======");
			if(StringUtils.isBlank(adinfo.getLoginName())){
				logger.info("====== end AdminManageController.saveAdminInfo ======");
				return ResultFactory.getFailedResult("请输入用户名");
			}
			if(StringUtils.isBlank(adinfo.getPwd())){
				logger.info("====== end AdminManageController.saveAdminInfo ======");
				return ResultFactory.getFailedResult("请输入密码");
			}
			String roleId = request.getParameter("roleId");
			if(StringUtils.isBlank(roleId)){
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
	public ResultDo saveRole(HttpServletRequest request,FXRole role){
		try {
			logger.info("====== start AdminManageController.saveRole, req[role=" + role + "] ======");
			if(StringUtils.isBlank(role.getName())){
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
	
}
