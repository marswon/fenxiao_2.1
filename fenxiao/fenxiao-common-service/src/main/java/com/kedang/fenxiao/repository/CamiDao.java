package com.kedang.fenxiao.repository;

import com.kedang.fenxiao.entity.FXCami;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by gegongxian on 16/10/19.
 */
public interface CamiDao extends PagingAndSortingRepository<FXCami, Long>, JpaSpecificationExecutor<FXCami> {

    @Query(" from FXCami where camiStr=?1")
    public List<FXCami> findOneByCamiStr(String cami);

    @Modifying
    @Query(" update FXCami set status =?1 where camiStr=?2 ")
	public int updateStatus(Integer status,String camiStr);
}
