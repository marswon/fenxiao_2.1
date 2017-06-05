package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXDiscount;

public interface FXDiscountDao extends
		PagingAndSortingRepository<FXDiscount, Long>,
		JpaSpecificationExecutor<FXDiscount> {

	@Query("from FXDiscount where eId=?1")
	public List<FXDiscount> findEnterpriseDiscount(String eId);

	@Query("from FXDiscount where eId=?1 and provinceId=?2 and yysTypeId=?3 and flowType=?4 and size in(?5)")
	public List<FXDiscount> findDiscountByEidAndProduct(String eId,
			String provinceId, String yysTypeId, int flowType,
			List<Integer> size);

	@Modifying
	@Query(" update FXDiscount set discount =?1 where eId=?2 and provinceId=?3 and yysTypeId=?4 and flowType=?5 and size in(?6)")
	public void updateDiscountBySize(int discount, String eId,
			String provinceId, String yysTypeId, int flowType,
			List<Integer> size);

	@Modifying
	@Query(" delete FXDiscount where id =?1")
	public Integer removeDiscountById(int id);

	@Query(" from FXDiscount where eId =?1  and provinceId='000' group by yysTypeId order by yysTypeId asc")
	public List<FXDiscount> findDiscountByeId(String eId);
	
	@Modifying
	@Query(" delete FXDiscount where eId=?1 and provinceId='000' and yysTypeId in(?2) and businessType=?3 ")
	public Integer removeDiscountByEidAndYysType(String eId,List<String> yysType,Integer businessType);
}
