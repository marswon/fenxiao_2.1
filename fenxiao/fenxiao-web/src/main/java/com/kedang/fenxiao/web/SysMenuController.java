package com.kedang.fenxiao.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kedang.fenxiao.util.po.JsonTreeData;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.entity.SysMenuEntity;
import com.kedang.fenxiao.service.SysMenuService;
import com.kedang.fenxiao.service.SysRoleService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;
import com.kedang.fenxiao.util.util.Constant.MenuType;
import com.kedang.fenxiao.util.util.PageUtils;
import com.kedang.fenxiao.util.util.Query;
import com.kedang.fenxiao.util.util.R;
import com.kedang.fenxiao.util.util.RRException;

/**
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月23日 下午3:49:11
 */
@Controller
@RequestMapping(value = "sys/menu")
public class SysMenuController
{

	private static final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 打开菜单视图
	 */
	@RequestMapping(value = "")
	public String menuView()
	{
		return "menu/menu";
	}

	/**
	 * 查询所有菜单
	 */
	@ResponseBody
	@RequestMapping(value = "getMenu")
	public Page<SysMenuEntity> getMenu(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<SysMenuEntity> pageList = null;

		try
		{
			logger.info("====== start SysMenuController.getMenu ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			pageList = sysMenuService.findAllMenu(searchParams, new PageRequest(page - 1, rows, Sort.Direction.ASC,
					"id"));
			if (pageList != null && pageList.getContent() != null && pageList.getContent().size() > 0)
			{
				for (SysMenuEntity menu : pageList.getContent())
				{
					menu.setParentName(sysMenuService.queryName(menu.getParentId()));
				}
			}
			logger.info("====== end SysMenuController.getMenu ,res[_listFxProductGroup=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getMenu error[" + e.getCause() + "]");
		}
		return pageList;
	}

	
	/**
	 * 所有菜单列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public R list(@RequestParam Map<String, Object> params){					
		//查询列表数据
		Query query = new Query(params);				
		List<SysMenuEntity> menuList = sysMenuService.queryList(query);
		
		for (SysMenuEntity menu : menuList)
		{
			menu.setParentName(sysMenuService.queryName(menu.getParentId()));
		}
			
		List<SysMenuEntity> menuList1 = sysMenuService.queryList();

		int total = menuList1.size();
		
		PageUtils pageUtil = new PageUtils(menuList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@ResponseBody
	public R select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();
		
		//添加顶级菜单
		/*SysMenuEntity root = new SysMenuEntity();
		root.setId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);*/
		
		return R.ok().put("menuList", menuList);
	}
	
	
	
	
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@ResponseBody
	public R save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);		
		sysMenuService.save(menu);		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody	public R update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);			
		SysMenuEntity menu1 = sysMenuService.findOne(menu.getId());	
		menu1.setParentId(menu.getParentId());
		menu1.setName(menu.getName());
		menu1.setUrl(menu.getUrl());
		menu1.setPerms(menu.getPerms());
		menu1.setType(menu.getType());
		menu1.setIcon(menu.getIcon());
		menu1.setOrderNum(menu.getOrderNum());
		menu1.setSort(menu.getSort());		
		sysMenuService.save(menu1);
		
		return R.ok();
	}
	
	/**
	 * 菜单信息
	 */
	@RequestMapping("/info/{menuId}")
	@ResponseBody
	public R info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.queryObject(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public R delete(@RequestBody Long[] menuIds){
		for(Long menuId : menuIds){
			if(menuId.longValue() <= 30){
				return R.error("系统菜单，不能删除");
			}
		}
		sysMenuService.deleteBatch(menuIds);
		
		return R.ok();
	}
	
	/**
	 * 角色授权菜单
	 */
	@RequestMapping(value = "perms")
	@ResponseBody
	public ResultDo getPerms(Long roleId)
	{
		try
		{
			logger.info("====== start SysMenuController.getPerms======");
			List<JsonTreeData> list = sysMenuService.queryPrivilegeTree(roleId);
			logger.info("====== end SysMenuController.getPerms ,res[list=" + list + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getPerms error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取权限异常");
		}
	}

/*	@RequestMapping("perms")
	@ResponseBody
	public R perms(){
		//查询列表数据
		List<SysMenuEntity> menuList = null;
		
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		//只有超级管理员，才能查看所有管理员列表
		if(shiroUser.getId() == Constant.SUPER_ADMIN){
			menuList = sysMenuService.queryList(new HashMap<String, Object>());
		}else{
			menuList = sysMenuService.queryUserList(getUserId());
		}
		menuList = sysMenuService.queryUserList(shiroUser.getId());

		  List<Integer> nullArr = new ArrayList<Integer>();  
		  nullArr.add(null); 
		  
		  menuList.removeAll(nullArr);  
		
		return R.ok().put("menuList", menuList);
	}*/
	
	
	/**
	 * 更新角色授权菜单
	 */
/*	@RequestMapping(value = "getPermsPage")
	@ResponseBody
	public ResultDo getPermsPage(Long roleId)
	{
		try
		{
			logger.info("====== start SysMenuController.getPermsPage======");
			List<Map<String, Object>> list = sysMenuService.queryPrivilegeTree(roleId);
			logger.info("====== end SysMenuController.getPermsPage ,res[list=" + list + "] ======");
			return ResultFactory.getSuccessResult(list);
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getPermsPage error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取权限异常");
		}
	}*/

	/**
	 * 获取角色信息
	 */
	@RequestMapping(value = "getPermsInfo")
	@ResponseBody
	public ResultDo getPermsInfo(Long id)
	{
		try
		{
			logger.info("====== start SysMenuController.getPermsInfo======");
			FXRole fxrole = sysRoleService.fingById(id);
			logger.info("====== end SysMenuController.getPermsInfo ,res[list=" + fxrole + "] ======");
			return ResultFactory.getSuccessResult(fxrole);
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getPerms error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取角色信息异常");
		}
	}

	@RequestMapping(value = "getMenuList")
	@ResponseBody
	public ResultDo getMenuList()
	{
		try
		{
			logger.info("====== start SysMenuController.getMenuList ======");
			List<SysMenuEntity> menus = sysMenuService.getMenuList();
			logger.info("====== end SysMenuController.getMenuList ======");
			return ResultFactory.getSuccessResult(menus);
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getMenuList error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取菜单列表异常");
		}
	}

	/**
	 * 用户菜单列表
	 */
	@RequestMapping("/users")
	@ResponseBody
	public Map<String, Object> users()
	{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(shiroUser.getId());
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("menuList", menuList);
		return r;
	}

	/**
	 * 用户二级菜单列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/user")
	@ResponseBody
	public Map<String, Object> user(String menuId)
	{
		//2级以上菜单
		Map<String, Object> childer = new HashMap<String, Object>();
		List<SysMenuEntity> childerList = new ArrayList<SysMenuEntity>();
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(shiroUser.getId()); //一级不为空
		if (StringUtils.isNotBlank(menuId))
		{
			//只保留相同一级菜单
			if (CollectionUtils.isNotEmpty(menuList))
			{
				for (SysMenuEntity sysMenuEntity : menuList)
				{
					if ((sysMenuEntity.getId() + "").equals(menuId))
					{

						List<SysMenuEntity> list = (List<SysMenuEntity>) sysMenuEntity.getList();
						for (SysMenuEntity obj : list)
						{
							childerList.add(obj);
						}
					}

				}

			}
		}
		else
		{
			//1级菜单
			if (CollectionUtils.isNotEmpty(menuList))
			{
				SysMenuEntity sysMenuEntity = menuList.get(0);
				//2级菜单，拆分
				List<SysMenuEntity> list = (List<SysMenuEntity>) sysMenuEntity.getList();
				for (SysMenuEntity obj : list)
				{
					//保存2级以上菜单
					childerList.add(obj);
				}
			}
		}
		childer.put("menuList", childerList);
		return childer;
	}

	/**
	 * 用户一级菜单列表
	 */
	@RequestMapping("/firstUser")
	@ResponseBody
	public Map<String, Object> firstUser()
	{
		//1级菜单
		Map<String, Object> first = new HashMap<String, Object>();
		List<SysMenuEntity> firstList = new ArrayList<SysMenuEntity>();
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(shiroUser.getId());
		//1级菜单
		for (SysMenuEntity sysMenuEntity : menuList)
		{
			sysMenuEntity.setList(null);
			firstList.add(sysMenuEntity);
		}
		first.put("firstList", firstList);
		return first;
	}

	@RequestMapping(value = "savaMenu")
	@ResponseBody
	public ResultDo saveMenu(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start SysMenuController.saveMenu ======");
			SysMenuEntity sysmenu = new SysMenuEntity();

			if (StringUtils.isBlank(request.getParameter("name")))
			{
				logger.info("====== end SysMenuController.saveMenu ======");
				return ResultFactory.getFailedResult("请输入菜单名");
			}
			if (StringUtils.isBlank(request.getParameter("type").toString()))
			{
				logger.info("====== end SysMenuController.saveMenuo ======");
				return ResultFactory.getFailedResult("请选择菜单类型");
			}
			if (StringUtils.isBlank(request.getParameter("url")))
			{
				logger.info("====== end SysMenuController.saveMenu ======");
				return ResultFactory.getFailedResult("请选择菜单URL");
			}
			String parentName = request.getParameter("parentName");
			if (StringUtils.isBlank(parentName))
			{
				sysmenu.setParentId((long) 0);

			}
			else
			{
				sysmenu.setParentId((long) Integer.parseInt(request.getParameter("parentName")));
			}
			sysmenu.setType(Integer.parseInt(request.getParameter("type")));
			sysmenu.setName(new String(request.getParameter("name").getBytes("ISO8859-1"), "UTF-8"));
			sysmenu.setUrl(request.getParameter("url"));
			sysmenu.setPerms(request.getParameter("perms"));
			sysmenu.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
			sysmenu.setIcon(request.getParameter("icon"));
			sysMenuService.saveSysMenu(sysmenu);
			logger.info("====== end SysMenuController.saveMenu ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.saveMenu error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存菜单信息异常");
		}
	}

	@RequestMapping(value = "getMenuinfo")
	@ResponseBody
	public ResultDo getMenu(Long id)
	{
		try
		{
			logger.info("====== start SysMenuController.getMenu, req[id=" + id + "] ======");
			SysMenuEntity sysmenu = sysMenuService.findOne(id);
			logger.info("====== end SysMenuController.getMenu ,res[sysmenu=" + sysmenu + "] ======");
			return ResultFactory.getSuccessResult(sysmenu);
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.getMenu error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("获取菜单信息异常");
		}
	}

	@RequestMapping(value = "delMenu")
	@ResponseBody
	public ResultDo delMenu(Long id)
	{
		try
		{
			logger.info("====== start SysMenuController.delMenu, req[id=" + id + "] ======");
			if (id == null)
			{
				logger.info("====== end SysMenuController.delMenu ======");
				return ResultFactory.getFailedResult("请选择菜单");
			}
			sysMenuService.delMenu(id);
			logger.info("====== end SysMenuController.delMenu ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.delMenu error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("删除菜单信息异常");
		}
	}

	@RequestMapping(value = "updateMenu")
	@ResponseBody
	public ResultDo updateMenu(HttpServletRequest request)
	{
		try
		{
			logger.info("====== start AdminManageController.updateMenu ======");
			request.setCharacterEncoding("utf-8");
			String id = request.getParameter("id");

			SysMenuEntity sysmenu = sysMenuService.findOne((long) Integer.parseInt(id));

			if (StringUtils.isBlank(request.getParameter("name")))
			{
				logger.info("====== end SysMenuController.updateMenu ======");
				return ResultFactory.getFailedResult("请输入菜单名");
			}
			if (StringUtils.isBlank(request.getParameter("type").toString()))
			{
				logger.info("====== end SysMenuController.updateMenu ======");
				return ResultFactory.getFailedResult("请选择菜单类型");
			}
			if (StringUtils.isBlank(request.getParameter("url")))
			{
				logger.info("====== end SysMenuController.updateMenu ======");
				return ResultFactory.getFailedResult("请选择菜单URL");
			}
			String parentName = request.getParameter("parentName");
			if (StringUtils.isBlank(parentName))
			{
				sysmenu.setParentId((long) 0);

			}
			else
			{
				sysmenu.setParentId((long) Integer.parseInt(request.getParameter("parentName")));
			}

			sysmenu.setType(Integer.parseInt(request.getParameter("type")));
			sysmenu.setName(request.getParameter("name"));
			sysmenu.setUrl(request.getParameter("url"));
			sysmenu.setPerms(request.getParameter("perms"));
			sysmenu.setOrderNum(Integer.parseInt(request.getParameter("orderNum")));
			sysmenu.setIcon(request.getParameter("icon"));
			sysMenuService.saveSysMenu(sysmenu);

			logger.info("====== end SysMenuController.updateMenu ======");
			return ResultFactory.getSuccessResult();

		}
		catch (Exception e)
		{
			logger.info("====== err SysMenuController.updateMenu ======" + e.getCause());
			return ResultFactory.getFailedResult(e.getMessage());
		}

	}

	/**
	 * 新增权限角色
	 */
	@RequestMapping(value = "addRoleMenu")
	@ResponseBody
	public ResultDo addRoleMenu(String value)
	{
		try
		{
			value = new String(value.getBytes("ISO8859-1"), "UTF-8");
			logger.info("====== start SysMenuController.addRoleMenu ,req[value=" + value + "]======");
			String[] val = value.split("#");
			if (val.length < 1)
			{
				logger.info("====== end SysMenuController.addRoleMenu ======");
				return ResultFactory.getFailedResult("请填写角色名称");
			}
			System.out.println("======================#" + val[1]);

			FXRole fxrole = new FXRole();
			fxrole.setName(val[1]);
			try
			{
				fxrole.setDescription(val[2]);
			}
			catch (Exception e)
			{
				return ResultFactory.getFailedResult("请填写备注");
			}
			

			List<FXRole> rolelist = sysRoleService.findFXRolebyName(val[1]);

			if (rolelist.size() >= 1)
			{
				return ResultFactory.getFailedResult("已存在相同的角色");
			}

			sysRoleService.saveFXRole(fxrole);

			List<FXRole> rolelist2 = sysRoleService.findFXRolebyName(val[1]);

			
			if (StringUtils.isBlank(val[0]))
			{
				logger.info("====== end SysMenuController.addRoleMenu ======");
				return ResultFactory.getFailedResult("请选择权限信息");
			}
			if (StringUtils.contains(val[0], "clearAll"))
			{
				sysMenuService.addrolemenu((long) rolelist2.get(0).getId(), new ArrayList<Long>());
			}
			else
			{
				String[] pris = val[0].split(",");
				if (pris.length < 1)
				{
					logger.info("====== end SysMenuController.addRoleMenu ======");
					return ResultFactory.getFailedResult("请选择权限信息");
				}
				List<Long> prisList = new ArrayList<Long>();
				for (String s : pris)
				{
					prisList.add(Long.parseLong(s));
				}
				sysMenuService.addrolemenu((long) rolelist2.get(0).getId(), prisList);
			}
			logger.info("====== end SysMenuController.addRoleMenu ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.addRoleMenu error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}

	/**
	 * 修改权限角色
	 */
	@RequestMapping(value = "updateRoleMenu")
	@ResponseBody
	public ResultDo updateRoleMenu(String value)
	{
		try
		{
			// value = new String(value.getBytes("ISO8859-1"), "UTF-8");
			logger.info("====== start SysMenuController.updateRoleMenu ,req[value=" + value + "]======");
			String[] val = value.split("#");
			if (val.length < 1)
			{
				logger.info("====== end SysMenuController.updateRoleMenu ======");
				return ResultFactory.getFailedResult("请填写角色名称");
			}
			FXRole fxrole = sysRoleService.fingById(Long.parseLong(val[3]));
			fxrole.setName(val[1]);
			try
			{
				fxrole.setDescription(val[2]);
			}
			catch (Exception e)
			{
				return ResultFactory.getFailedResult("请填写备注");
			}

			sysRoleService.saveFXRole(fxrole);

			List<FXRole> rolelist = sysRoleService.findFXRolebyName(val[1]);

			if (rolelist.size() > 1)
			{
				return ResultFactory.getFailedResult("已存在相同的角色");
			}

			if (StringUtils.isBlank(val[0]))
			{
				logger.info("====== end SysMenuController.updateRoleMenu ======");
				return ResultFactory.getFailedResult("请选择权限信息");
			}
			if (StringUtils.contains(val[0], "clearAll"))
			{
				sysMenuService.updaterolemenu((long) rolelist.get(0).getId(), new ArrayList<Long>());
			}
			else
			{
				String[] pris = val[0].split(",");
				if (pris.length < 1)
				{
					logger.info("====== end SysMenuController.updateRoleMenu ======");
					return ResultFactory.getFailedResult("请选择权限信息");
				}
				List<Long> prisList = new ArrayList<Long>();
				for (String s : pris)
				{
					prisList.add(Long.parseLong(s));
				}
				sysMenuService.updaterolemenu((long) rolelist.get(0).getId(), prisList);
			}
			logger.info("====== end SysMenuController.updateRoleMenu ======");
			return ResultFactory.getSuccessResult();
		}
		catch (Exception e)
		{
			logger.error("SysMenuController.updateRoleMenu error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult(e.getMessage());
		}
	}


	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.queryObject(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
	
}
