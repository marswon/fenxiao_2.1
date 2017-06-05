package com.kedang.fenxiao.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.SysMenuEntity;

/**
 *
 * @author wangning
 * @email ning_wg@163.com
 * @date 2017年5月23日 下午3:54:23
 */
public interface SysMenuDao extends
		PagingAndSortingRepository<SysMenuEntity, Long>,
		JpaSpecificationExecutor<SysMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId
	 * @return
	 */
	@Query(nativeQuery=true,value="select * from sys_menu where parent_id = ?1 order by order_num asc")
	List<SysMenuEntity> queryListParentId(long parentId);
	
	@Query(nativeQuery=true,value="select name from sys_menu where id = ?1 ")
	String queryName(long menuId);

	@Query(nativeQuery=true,value="select distinct m.*,(select p.name from sys_menu p where p.id = m.parent_id) as parentName from admin_role ur LEFT JOIN sys_role_menu rm on ur.role_id = rm.role_id 	LEFT JOIN sys_menu m on rm.menu_id = m.id 	where ur.user_id = ?1")
	List<SysMenuEntity> queryUserList(Long id);

	@Query("from SysMenuEntity a WHERE name like ?1")
	List<SysMenuEntity> findLikeName(String name);

	@Query(nativeQuery=true,value="select m.*,(select p.name from sys_menu p where p.id = m.parent_id) as parentName from sys_menu m order by m.order_num asc limit ?1, ?2")
	List<SysMenuEntity> queryList(int offset,int limit);


}
