package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXChannel;

public interface FXChannelDao extends PagingAndSortingRepository<FXChannel, Long>, JpaSpecificationExecutor<FXChannel>
{
	@Query("from FXChannel where id =?1 order by id desc ")
	public FXChannel findFXChannelById(String id);

	@Modifying
	@Query(" update FXChannel set status = ?1 where id=?2")
	public int updateChannelStatus(int status, String id);
}