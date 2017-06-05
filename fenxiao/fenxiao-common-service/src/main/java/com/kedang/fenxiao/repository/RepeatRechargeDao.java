package com.kedang.fenxiao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXRepeatRecharge;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月30日 下午1:59:36 
 */
public interface RepeatRechargeDao extends PagingAndSortingRepository<FXRepeatRecharge, Long>,
		JpaSpecificationExecutor<FXRepeatRecharge>
{

	@Query("select systemValue from FXSystemConfig where systemKey = ?1")
	String getPassEid(String systemKey);

	@Query(nativeQuery = true, value = "SELECT rr.curchannelid,(select name from fx_channel where id=rr.curchannelid),rr.curflowtype,rr.serialnum from fx_repeat_recharge rr WHERE rr.yystypeid=?1 AND rr.provinceid=?2 AND rr.flowType=?3 ORDER BY rr.serialnum")
	List<Object[]> getRepeatRechargeList(String yysTypeId, String provinceId, String flowType);

	@Query("select id,name from FXEnterprise")
	List<Object[]> getEnterpriseList();

	@Modifying
	@Query("update FXSystemConfig set systemValue=?1 where systemKey = ?2")
	int updateSystemConfig(String systemVal, String systemKey);

	@Modifying
	@Query("update FXRepeatRecharge set passeid=?1")
	int updatePasseid(String passeid);

	@Query(nativeQuery = true, value = "SELECT DISTINCT c.id,c.name,op.flowType FROM fx_channel c,fx_operators_product op where c.id=op.channelId AND op.yysTypeId=?1 AND (op.provinceId='000' or op.provinceId=?2) AND (op.flowType=0 or op.flowType=?3) AND op.businessType=0 GROUP BY op.id,op.flowType order by op.id")
	List<Object[]> searchChannel(String yysTypeId, String provinceId, int flowType);

	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM fx_repeat_recharge WHERE yystypeid=?1 AND provinceid=?2 AND flowType=?3")
	int deleteRepeatRecharge(String yysTypeId, String provinceId, int flowType);

	@Query(value = "FROM FXRepeatRecharge WHERE yystypeid=?1 AND provinceid=?2 AND flowType=?3")
	List<FXRepeatRecharge> selectRepeatRecharge(String yysTypeId, String provinceId, int flowType);

	@Query(nativeQuery = true, value = "SELECT rr.curchannelid,(SELECT name FROM fx_channel c WHERE c.id=rr.curchannelid),rr.curflowtype FROM fx_repeat_recharge rr WHERE yystypeid=?1 AND provinceid=?2 AND flowType=?3")
	List<Object[]> searchExistRepeatRehcarge(String yysTypeId, String provinceId, int flowType);
}
