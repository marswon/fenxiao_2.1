package com.kedang.fenxiao.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.kedang.fenxiao.entity.Contacts;

public interface ContactsDao  extends PagingAndSortingRepository<Contacts, Long>, JpaSpecificationExecutor<Contacts>{

}
