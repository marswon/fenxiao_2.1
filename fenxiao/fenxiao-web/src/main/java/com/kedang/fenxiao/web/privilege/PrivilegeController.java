package com.kedang.fenxiao.web.privilege;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.service.AdminRoleService;
import com.kedang.fenxiao.service.PrivilegeService;
import com.kedang.fenxiao.service.RolePrivilegeService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;
import com.kedang.fenxiao.web.BaseController;

@Controller
@RequestMapping(value = "privilege")
public class PrivilegeController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(PrivilegeController.class);
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	@Autowired
	private AdminRoleService adminRoleService;

	@RequestMapping(value = "privilege")
	public String privilegePage()
	{
		return "admin_management/privilege";
	}
	


	@RequestMapping(value = "addprivilege")
	public String addPrivilegePage(Model model)
	{
		return "admin_management/addprivilege";
	}

	@RequestMapping(value = "getPrivilegePage")
	@ResponseBody
	public ResultDo getPrivilegePage(Long roleId)
	{
		try
		{
			logger.info("====== start PrivilegeController.getPrivilegePage ,req[roleId=" + roleId + "]======");
			List<Map<String, Object>> list = privilegeService.queryPrivilegeTree(roleId);
			logger.info("====== end PrivilegeController.getPrivilegePage ,res[list=" + list + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("PrivilegeController.getPrivilegePage error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取权限异常");
		}
	}

	/***
	 * 获取权限组List
	 * @return
	 */
	@RequestMapping(value = "getParentPrivilegeList")
	@ResponseBody
	public ResultDo getParentPrivilegeList()
	{
		try
		{
			logger.info("====== start PrivilegeController.getParentPrivilegeList ======");
			List<Privilege> list = privilegeService.getParentPrivilegeList();
			logger.info("====== end PrivilegeController.getParentPrivilegeList ,res[list=" + list + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("PrivilegeController.getParentPrivilegeList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取权限组List异常");
		}
	}

	/***
	 * 保存权限
	 * @param privilege
	 * @return
	 */
	@RequestMapping(value = "savePrivilege")
	@ResponseBody
	public ResultDo savePrivilege(Privilege privilege)
	{
		try
		{
			logger.info("====== start PrivilegeController.savePrivilege ,req[privilege=" + privilege + "] ======");
			if (StringUtils.isBlank(privilege.getPriCode()))
			{
				logger.info("====== end PrivilegeController.savePrivilege ======");
				return ResultFactory.getFailedResult("请输入权限编码");
			}
			if (privilegeService.save(privilege) == null)
			{
				logger.info("====== end PrivilegeController.savePrivilege ======");
				return ResultFactory.getFailedResult("保存失败");
			}
			logger.info("====== end PrivilegeController.savePrivilege ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("PrivilegeController.savePrivilege error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存权限异常");
		}
	}

	/***
	 * 分配权限
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "allocation")
	@ResponseBody
	public ResultDo allocation(String value)
	{
		try
		{
			logger.info("====== start PrivilegeController.allocation ,req[value=" + value + "]======");
			String[] val = value.split("#");
			if (val.length < 1)
			{
				logger.info("====== end PrivilegeController.allocation ======");
				return ResultFactory.getFailedResult("请选择角色信息");
			}
			Long rolId = Long.parseLong(val[1]);
			if (StringUtils.isBlank(val[0]))
			{
				logger.info("====== end PrivilegeController.allocation ======");
				return ResultFactory.getFailedResult("请选择权限信息");
			}
			if (StringUtils.contains(val[0], "clearAll"))
			{
				rolePrivilegeService.saveroleprivilege(rolId, new ArrayList<Long>());
			}
			else
			{
				String[] pris = val[0].split(",");
				if (pris.length < 1)
				{
					logger.info("====== end PrivilegeController.allocation ======");
					return ResultFactory.getFailedResult("请选择权限信息");
				}
				List<Long> prisList = new ArrayList<Long>();
				for (String s : pris)
				{
					prisList.add(Long.parseLong(s));
				}
				rolePrivilegeService.saveroleprivilege(rolId, prisList);
			}
			logger.info("====== end PrivilegeController.allocation ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("PrivilegeController.allocation error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}
	
	
	/**
	 * 新增权限角色
	 */
/*	@RequestMapping(value = "addlocation")
	@ResponseBody
	public ResultDo addlocation(String value)
	{
		try
		{
			value = new String(value.getBytes("ISO8859-1"), "UTF-8");
			logger.info("====== start PrivilegeController.allocation ,req[value=" + value + "]======");
			String[] val = value.split("#");
			if (val.length < 1)
			{
				logger.info("====== end PrivilegeController.allocation ======");
				return ResultFactory.getFailedResult("请添加角色名称");
			}
			//Long rolName = Long.parseLong(val[1]);
			String rolName = val[1];
			
			if (StringUtils.isBlank(val[0]))
			{
				logger.info("====== end PrivilegeController.allocation ======");
				return ResultFactory.getFailedResult("请选择权限信息");
			}
			if (StringUtils.contains(val[0], "clearAll"))
			{
				rolePrivilegeService.addroleprivilege(rolName, new ArrayList<Long>());
			}
			else
			{
				String[] pris = val[0].split(",");
				if (pris.length < 1)
				{
					logger.info("====== end PrivilegeController.allocation ======");
					return ResultFactory.getFailedResult("请选择权限信息");
				}
				List<Long> prisList = new ArrayList<Long>();
				for (String s : pris)
				{
					prisList.add(Long.parseLong(s));
				}
				rolePrivilegeService.addroleprivilege(rolName, prisList);
			}
			logger.info("====== end PrivilegeController.allocation ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("PrivilegeController.allocation error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}*/
	

	/**
	 * 根据用户ID查询用户所屏蔽的目录
	 * @param principals
	 * @return
	 */
	@RequestMapping(value = "findAllPrivilegeGroupById")
	@ResponseBody
	public ResultDo findAllPrivilegeGroupById()
	{
		logger.info("====start PrivilegeController.findAllPrivilegeGroupById====");
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (shiroUser.id != Constant.ADMIN_NUMBER)
		{
			logger.info("====end PrivilegeController.findAllPrivilegeGroupById====");
			return ResultFactory.getSuccessResult(adminRoleService.findPrivilegGroupByAdminId(shiroUser.id));
		}
		logger.info("====end PrivilegeController.findAllPrivilegeGroupById====");
		return ResultFactory.getSuccessResult();
	}

}
