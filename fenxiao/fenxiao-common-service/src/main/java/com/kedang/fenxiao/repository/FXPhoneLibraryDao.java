package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.FXPhoneLibrary;
import com.kedang.fenxiao.entity.FXProduct;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by gegongxian on 17/4/20.
 */

public interface FXPhoneLibraryDao
        extends PagingAndSortingRepository<FXPhoneLibrary, Long>, JpaSpecificationExecutor<FXPhoneLibrary> {

    @Query("SELECT sendPhone,count(*) FROM FXSendMsg  WHERE createTime >= ?1 AND createTime <= ?2 GROUP BY sendPhone")
    public List<Object[]> getSendCountToDay(Date startDate, Date endDate);
}
