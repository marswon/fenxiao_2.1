package com.kedang.fenxiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.repository.SysRoleDao;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.util.Query;

/** 
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月29日 上午10:14:53   
 */
@Component
public class SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;
	
	
	/**
	 * 查询所有角色
	 */
	public Page<FXRole> findAllMenu(Map<String, Object> params,
			Pageable pageable) {
		Page<FXRole> fxRoleGroup = sysRoleDao.findAll(
				JpaQueryUtils.buildSpecification(FXRole.class, params),
				pageable);
		return fxRoleGroup;
	}

	/**
	 * 获取角色列表
	 * @return
	 */
	public List<FXRole> getRoleList() {
		return (List<FXRole>) sysRoleDao.findAll(new Sort(Direction.ASC, "id"));
	}

	/**
	 * 新增角色
	 * @param fxrole
	 */
	public void saveFXRole(FXRole fxrole) {
		sysRoleDao.save(fxrole);
	}

	/**
	 * 根据id查询角色信息
	 * @param roleId
	 * @return
	 */
	public FXRole getRole(Long roleId) {
		FXRole rol = sysRoleDao.findById(roleId);
		if(rol == null){
			throw new ServiceException("没有对应分组");
		}
		return rol;
	}

	public List<FXRole> findFXRolebyName(String name) {	
		return sysRoleDao.findByName(name);
	}

	public FXRole fingById(Long roleId) {
		return sysRoleDao.findById(roleId);
	}

	/**
	 * 删除角色
	 * @param id
	 */
	public void delRole(Long id) {
		sysRoleDao.delete(id);
	}


	/**
	 * 按条件查询
	 * @param query
	 * @return
	 */
	public List<FXRole> queryList(Query query)
	{
		System.out.println("#############"+query);
		//return sysRoleDao.findLikeName(query.toString());
		return (List<FXRole>) sysRoleDao.findAll();
	}

	public List<FXRole> queryListByRoleName(String name)
	{
		name = "%"+name+"%";
		return sysRoleDao.findLikeName(name);
	}
	
}
