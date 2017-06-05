package com.kedang.fenxiao.repository;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.FXAdvert;

public interface IndexDao extends PagingAndSortingRepository<FXAdvert, Long>, JpaSpecificationExecutor<FXAdvert>
{

}
