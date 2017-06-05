package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXProduct;

public interface FXProductDao extends PagingAndSortingRepository<FXProduct, Long>, JpaSpecificationExecutor<FXProduct>
{
	/**
	 * 获取所有平台产品
	 * 
	 * @return
	 */
	@Query("from FXProduct order by id desc ")
	public List<FXProduct> findAllFxEnterprise();

	/**
	 * 根据省份ID查询平台产品
	 * 
	 * @param proviceId
	 * @return
	 */
	@Query("from FXProduct where provinceId =?1 and flowType =?2 group by size ")
	public List<FXProduct> getProvinceToSize(String provinceId, int flowType);

	/**
	 * 根据省份ID查询平台产品所有类型
	 * 
	 * @param proviceId
	 * @return
	 */
	@Query("from FXProduct where provinceId =?1 group by flowType ")
	public List<FXProduct> getFlowTypeByPorinceId(String provinceId);

	/**
	 * 查找当前分销商所开通的产品
	 * 
	 * @param eId
	 * @return
	 */

	@Query("from FXProduct where id in(select ep.proId from FXEnterpriseProduct ep where eId=?1)")
	public List<FXProduct> findProductByEid(String eId);

	/**
	 * 根据平台主键ID查询proid
	 * @param proId
	 */
	@Query("from FXProduct where id =?1")
	public FXProduct findOneById(String proId);

	/**
	 * 根据主键ID上架下架平台产品
	 * @param status
	 * @param id
	 * @return
	 */
	@Modifying
	@Query(" update FXProduct set status = ?1 where id= ?2 ")
	public int updateProductStatus(int status, String id);

	/**
	 * 根据业务类型查询所有产品
	 * @param businessType
	 * @return
	 */
	@Query("from FXProduct where businessType = ?1 ")
	public List<FXProduct> findAllByBusinessType(Integer businessType);

	/**
	 * 根据业务类型查询所有产品
	 * @param businessType
	 * @return
	 */
	@Query("from FXProduct where businessType = ?1 and  selfProductType = ?2")
	public List<FXProduct> findAllByBusinessType(Integer businessType,Integer selfProductType);

}
