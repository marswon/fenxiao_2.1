package com.kedang.fenxiao.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXUser;
public interface FXUserDao extends PagingAndSortingRepository<FXUser, Long>, JpaSpecificationExecutor<FXUser>
{
	@Query("from FXUser a WHERE id =?1")
	FXUser findById(String id);
//	@Query("from FXUser a WHERE a.role.id in(:roleIds)")
//	List<FXUser> getAdminRoles(@Param("roleIds")List<Long> roleIds);
//	@Query("from FXUser WHERE adminInfo.id = ?1")
//	public AdminRole findByAdminId(Long id);

}
