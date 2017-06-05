package com.kedang.fenxiao.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.service.SysRoleService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;
import com.kedang.fenxiao.util.util.PageUtils;
import com.kedang.fenxiao.util.util.Query;
import com.kedang.fenxiao.util.util.R;

/** 
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月29日 上午10:11:19   
 */
@Controller
@RequestMapping(value = "sys/role")
public class SysRoleController {
	
	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	
	@Autowired
	private SysRoleService sysRoleService;
	
	/**
	 * 打开菜单视图
	 */
	@RequestMapping(value = "")
	public String role()
	{
		return "role/role";
	}
	
	
	
	/**
	 * 查询所有菜单
	 */
	@ResponseBody
	@RequestMapping(value = "getRole")
	public Page<FXRole> getRole(
			HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows) {
		Page<FXRole> pageList = null;

		try {
			logger.info("====== start SysRoleController.getRole ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = sysRoleService.findAllMenu(searchParams,new PageRequest(page - 1, rows, Sort.Direction.ASC, "id"));
			logger.info("====== end SysRoleController.getRole ,res[_listFxProductGroup="
					+ pageList + "] ======");
		} catch (Exception e) {
			logger.error("SysRoleController.getRole error[" + e.getCause()
					+ "]");
		}
		return pageList;
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object>params ){	
		
		//params = new String(params.getBytes("ISO8859-1"), "UTF-8");
		
		//查询列表数据
		Query query = new Query(params);
		
		
		if (params.get("roleName")!=null)
		{
			String pa = params.get("roleName").toString();
						
			try
			{
				pa =  new String(pa.getBytes("ISO8859-1"), "UTF-8");
				List<FXRole> list = sysRoleService.queryListByRoleName(pa);
				int total = list.size();			
				PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
				return R.ok().put("page", pageUtil);
			}
			catch (UnsupportedEncodingException e)
			{
				return R.error();
			}		
			
		}		
		List<FXRole> list = sysRoleService.queryList(query);
		int total = list.size();		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());		
		return R.ok().put("page", pageUtil);
	}
	
	@RequestMapping(value = "getRoleList")
	@ResponseBody
	public ResultDo getRoleList() {
		try {
			logger.info("====== start SysRoleController.getRoleList ======");
			List<FXRole> menus = sysRoleService.getRoleList();
			logger.info("====== end SysRoleController.getRoleList ======");
			return ResultFactory.getSuccessResult(menus);
		} catch (Exception e) {
			logger.error("SysMenuController.getMenuList error[" + e.getCause()
					+ "]");
			return ResultFactory.getFailedResult("获取角色列表异常");
		}
	}
	
	
	
	/**
	 * 删除角色
	 */
	@RequestMapping(value = "delRole")
	@ResponseBody
	public ResultDo delRole(Long id) {
		try {
			logger.info("====== start SysRoleController.delRole ======");
			sysRoleService.delRole(id);
			logger.info("====== end SysRoleController.delRole ======");
			return ResultFactory.getSuccessResult();
		} catch (Exception e) {
			logger.error("SysRoleController.delRole error[" + e.getCause()
					+ "]");
			return ResultFactory.getFailedResult("删除角色异常");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
