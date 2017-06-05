package com.kedang.fenxiao.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kedang.fenxiao.util.TreeNodeUtil;
import com.kedang.fenxiao.util.po.JsonTreeData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kedang.fenxiao.entity.Constant.MenuType;
import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.entity.SysMenuEntity;
import com.kedang.fenxiao.entity.SysRoleMenu;
import com.kedang.fenxiao.repository.SysMenuDao;
import com.kedang.fenxiao.repository.SysRoleMenuDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

/**
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月23日 下午3:52:18
 */
@Component
public class SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private AdminInfoService adminInfoService;
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private SysRoleService roleService;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	/**
	 * 查询所有菜单
	 */
	public Page<SysMenuEntity> findAllMenu(Map<String, Object> params,
			Pageable pageable) {
		Page<SysMenuEntity> fxProductGroup = sysMenuDao.findAll(
				JpaQueryUtils.buildSpecification(SysMenuEntity.class, params),
				pageable);
		return fxProductGroup;
	}

	/**
	 * 获取当前用户权限下的菜单列表
	 * 
	 * @param id
	 * @return
	 */
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		// 系统管理员，拥有最高权限
		if (userId == 1) {
			return getAllMenuList(null);
		}
		// 用户菜单列表
		List<Long> menuIdList = adminInfoService.queryAllMenuId(userId);
		System.out.println("#################"+menuIdList.size());
		return getAllMenuList(menuIdList);
	}

	/**
	 * 根据菜单id查询菜单详细
	 * 
	 * @param menuIdList
	 * @return
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
		// 查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		// 递归获取子菜单
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 根据父菜单，查询子菜单
	 * 
	 * @param l
	 * @param menuIdList
	 * @return
	 */
	private List<SysMenuEntity> queryListParentId(long parentId,
			List<Long> menuIdList) {
		List<SysMenuEntity> menuList = sysMenuDao.queryListParentId(parentId);
		if (menuIdList == null) {
			return menuList;
		}

		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for (SysMenuEntity menu : menuList) {
			System.out.println(menu.getId());
//			System.out.println(menuIdList.get(0).getClass().getName());
//			System.out.println(menu.getId().getClass().getName());
			BigInteger bi = new BigInteger(menu.getId().toString());
			if (menuIdList.contains(bi)) {
				userMenuList.add(menu);
			}
		}

		System.out.println("*********************"+userMenuList.size());
		return userMenuList;
	}

	public String queryName(long menuId) {
		return sysMenuDao.queryName(menuId);
	}

	/**
	 * 递归获取子菜单
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList,
			List<Long> menuIdList) {
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();

		for (SysMenuEntity entity : menuList) {
			if (entity.getType() == MenuType.CATALOG.getValue()) {// 目录
				entity.setList(getMenuTreeList(
						queryListParentId(entity.getId(), menuIdList),
						menuIdList));
			}
			entity.setParentName(queryName(entity.getParentId()));
			subMenuList.add(entity);
		}
		return subMenuList;
	}

	/**
	 * 获取菜单列表
	 * 
	 * @return
	 */
	public List<SysMenuEntity> getMenuList() {
		return (List<SysMenuEntity>) sysMenuDao.findAll(new Sort(Direction.ASC,
				"id"));
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */
	public SysMenuEntity findOne(Long id) {
		if (id == null) {
			return null;
		}
		return sysMenuDao.findOne(id);
	}

	public void delMenu(Long id) {
		sysMenuDao.delete(id);
	}

	public SysMenuEntity saveSysMenu(SysMenuEntity sysmenu) {

		return sysMenuDao.save(sysmenu);
	}

	/**
	 * 查询菜单列表
	 * 
	 * @param hashMap
	 * @return
	 */
	/*
	 * public List<SysMenuEntity> queryList(HashMap<String, Object> hashMap) {
	 * return sysMenuDao.queryList(map);; }
	 */

	/**
	 * 查询用户的权限列表
	 */
	public List<SysMenuEntity> queryUserList(Long id) {
		return sysMenuDao.queryUserList(id);
	}

	/**
	 * 获取所有权限(包括权限组和权限)
	 * 
	 * @return
	 */
	public List<SysMenuEntity> findAll() {
		return (List<SysMenuEntity>) sysMenuDao.findAll();
	}

	public List<JsonTreeData> queryPrivilegeTree(Long roleId) {
		List<SysMenuEntity> allPrivilege = findAll();
		List<JsonTreeData> treeDataList = new ArrayList<JsonTreeData>();
		List<SysMenuEntity> roleMenu = findSysMenuEntityByRoleId(roleId);

         /*为了整理成公用的方法，所以将查询结果进行二次转换。
          * 其中specid为主键ID，varchar类型UUID生成
          * parentid为父ID
          * specname为节点名称
          * */
		for (SysMenuEntity sysMenuEntity : allPrivilege) {
			JsonTreeData treeData = new JsonTreeData();
			for (SysMenuEntity sysMen : roleMenu)
			{
				if (sysMenuEntity.getId().equals(sysMen.getId()))
				{
					treeData.setChecked("checked");
				}
			}		
			treeData.setId(sysMenuEntity.getId()+"");
			treeData.setPid(sysMenuEntity.getParentId()+"");
			treeData.setText(sysMenuEntity.getName()+"");
			treeDataList.add(treeData);
		}
		//最后得到结果集,经过FirstJSON转换后就可得所需的json格式
		List<JsonTreeData> newTreeDataList = TreeNodeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}



	/**
	 * 根据roleId获取所有权限
	 * 
	 * @param roleId
	 * @return
	 */
/*	public List<Map<String, Object>> queryPrivilegeTree(Long roleId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<SysMenuEntity> sysmenu = findAll();
		List<SysMenuEntity> roleMenu = findSysMenuEntityByRoleId(roleId);
		for (SysMenuEntity p : sysmenu) {
			if (p.getType() == 0) {
				Map<String, Object> group = new HashMap<String, Object>();
				List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
				for (SysMenuEntity pChild : sysmenu) {
					if (p.getId().equals(pChild.getParentId())) {
						Map<String, Object> child = new HashMap<String, Object>();
						//child.put("checked", "checked");
						for (SysMenuEntity roleP : roleMenu) {
							if (roleP.getId().equals(pChild.getId())) {
								child.put("checked", "checked");
							}
						}
						child.put("id", pChild.getId());
						child.put("text", pChild.getName());
						child.put("state", "open");
						childList.add(child);
					}
				}
				group.put("id", p.getId());
				group.put("text", p.getName());
				group.put("state", "open");
				group.put("children", childList);
				result.add(group);
			}
		}
		return result;
	}*/

	/**
	 * 根据角色id(权限组id)查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<SysMenuEntity> findSysMenuEntityByRoleId(Long roleId) {
		if (roleId == null) {
			return new ArrayList<SysMenuEntity>();
		}
		String hql = "from SysMenuEntity p where p.id in(select rp.sysmenu.id from SysRoleMenu rp where rp.roleId=:roleId))";
		Query query = em.createQuery(hql);
		query.setParameter("roleId", roleId);
		List<SysMenuEntity> result = query.getResultList();
		return result;
	}

	/**
	 * 新增权限角色
	 * 
	 * @param rolName
	 * @param arrayList
	 */
	@Transactional(readOnly = false)
	public void addrolemenu(Long roleId, List<Long> menuIds) {
		FXRole rol = roleService.getRole((long) roleId);
		if (rol == null) {
			throw new ServiceException("角色不存在");
		}
		for (Long pId : menuIds) {
			SysMenuEntity sysmenu = findOne(pId);
			if (sysmenu == null) {
				throw new ServiceException("权限不存在");
			}
			SysRoleMenu sysrolemenu = new SysRoleMenu();
			sysrolemenu.setRoleId((long) roleId);
			sysrolemenu.setSysmenu(sysmenu);
			sysRoleMenuDao.save(sysrolemenu);
		}
	}

	/**
	 * 修改权限角色
	 * 
	 * @param rolName
	 * @param arrayList
	 */
	@Transactional(readOnly = false)
	public void updaterolemenu(Long roleId, List<Long> menuIds) {
		FXRole rol = roleService.getRole((long) roleId);
		if (rol == null) {
			throw new ServiceException("角色不存在");
		}
		// 清空角色原来拥有的权限
		sysRoleMenuDao.deleteSysRoleMenuListByRoleId(roleId);
		for (Long pId : menuIds) {
			SysMenuEntity sysmenu = findOne(pId);
			if (sysmenu == null) {
				throw new ServiceException("权限不存在");
			}
			SysRoleMenu sysrolemenu = new SysRoleMenu();
			sysrolemenu.setRoleId((long) roleId);
			sysrolemenu.setSysmenu(sysmenu);
			sysRoleMenuDao.save(sysrolemenu);
		}
	}

		/**
	 * 按条件查询
	 * @param query
	 * @return
	 */
	public List<SysMenuEntity> queryList(Map<String, Object> map)
	{
		System.out.println("#############"+map);
		
		System.out.println("#############limit:"+map.get("limit"));
		
		System.out.println("#############offset:"+map.get("offset"));
		
		String limit = map.get("limit").toString();
		
		String offset = map.get("offset").toString();

		//return sysRoleDao.findLikeName(query.toString());
		return (List<SysMenuEntity>) sysMenuDao.queryList(Integer.valueOf(offset),Integer.valueOf(limit));
	}

	public List<SysMenuEntity> queryListByRoleName(String name)
	{
		name = "%"+name+"%";
		return sysMenuDao.findLikeName(name);
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	public List<SysMenuEntity> queryNotButtonList()
	{
		return (List<SysMenuEntity>) sysMenuDao.findAll();
	}

	public List<SysMenuEntity> queryList()
	{
		return (List<SysMenuEntity>) sysMenuDao.findAll();
	}

	public SysMenuEntity queryObject(Long id)
	{
		return sysMenuDao.findOne(id);
	}

	public void save(SysMenuEntity menu)
	{
		sysMenuDao.save(menu);		
	}

	public void deleteBatch(Long[] menuIds)
	{
		for (Long id : menuIds)
		{
			sysMenuDao.delete(id);
		}
	}

	
}
