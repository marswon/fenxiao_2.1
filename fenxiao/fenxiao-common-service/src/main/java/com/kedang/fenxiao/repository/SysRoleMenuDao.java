package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.SysRoleMenu;

/** 
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月29日 下午7:54:42   
 */
public interface SysRoleMenuDao extends PagingAndSortingRepository<SysRoleMenu, Long>, JpaSpecificationExecutor<SysRoleMenu>  {

	/**
	 * 根据角色id删除权限
	 * @param roleId
	 */
	@Modifying
	@Query("delete from SysRoleMenu rp where rp.roleId=?1")
	void deleteSysRoleMenuListByRoleId(Long roleId);
}
