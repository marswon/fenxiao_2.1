package com.kedang.fenxiao.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.Contacts;
import com.kedang.fenxiao.repository.ContactsDao;
import com.kedang.fenxiao.util.SearchUtils;

@Component
public class ContactsService {
	@Autowired
	private ContactsDao contactsDao;

	public Page<Contacts> findAll(Map<String, Object> searchParams,
			PageRequest pageRequest) {
		return contactsDao.findAll(SearchUtils.buildSpec(Contacts.class, searchParams), pageRequest);
	}
}
