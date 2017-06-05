package com.kedang.fenxiao.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.Device;
import com.kedang.fenxiao.entity.FXMessage;
import com.kedang.fenxiao.entity.FXPerson;
import com.kedang.fenxiao.entity.FXSystemConfig;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月14日 下午3:51:47 
 */
public interface MonitorDao extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor<Device>
{
	@Query("from FXPerson where status=0")
	List<FXPerson> getPerson();

	@Query("from FXPerson where status=0 and openmessagestatus=1")
	List<FXPerson> getMessagePerson();

	@Query("from FXPerson where status=0 and openweichatstatus=1")
	List<FXPerson> getWeichatPerson();

	@Query("from FXMessage where messageStatus=0 and (sendWay=1 or sendWay=3) ORDER BY createTime")
	List<FXMessage> getMessage();

	@Query("from FXMessage where weichatStatus=0 and (sendWay=2 or sendWay=3) ORDER BY createTime")
	List<FXMessage> getWeichatMessage();

	@Modifying
	@Query("update FXMessage set weichatStatus=1 where id=?1")
	int updateWeichatStatus(int messageid);

	@Modifying
	@Query("update FXMessage set messageStatus=1 where id=?1")
	int updateMessageStatus(int messageid);

	@Query("from FXSystemConfig where systemKey=?1")
	FXSystemConfig getContinueFailLastStatisticTime(String systemKey);

	@Query("from FXSystemConfig")
	List<FXSystemConfig> getFXSystemConfig();

	@Query("select name from FXChannel where id=?1")
	String getChannelName(String channelId);

	@Query("select systemValue from FXSystemConfig where systemKey=?1")
	String getMonitoredEnterprise(String systemKey);

	@Query("select systemValue from FXSystemConfig where systemKey=?1")
	String getSystemValue(String systemKey);

	@Query(value = "SELECT SUM(costMoney) FROM fx_order_record WHERE clientSubmitTime >=?1 AND clientSubmitTime <?2 AND downstreamStatus = 0", nativeQuery = true)
	BigDecimal getCostMoney(Date startTime, Date endTime);

	@Query(value = "SELECT SUM(costMoney) FROM fx_order_record WHERE clientSubmitTime >=?1 AND clientSubmitTime <?2 AND downstreamStatus = 0 AND businessType=?3", nativeQuery = true)
	BigDecimal getCostMoney(Date startTime, Date endTime, int businessType);
}
