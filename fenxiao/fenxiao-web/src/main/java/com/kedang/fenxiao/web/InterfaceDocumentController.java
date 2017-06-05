package com.kedang.fenxiao.web;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;

@Controller
@RequestMapping(value = "interfaceDocument")
public class InterfaceDocumentController {
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(InterfaceDocumentController.class);
	
	@Autowired
	private AdminInfoService adminInfoService;
	
	@RequestMapping(value = "openDocument")
	public String openInterfaceDocumentView()
	{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		AdminInfo admin = adminInfoService.findAdminInfoById(shiroUser.getId());
		if("0".equals(admin.getFxEnterprise().getBusinessType()+"")){
			return "interface_document/api";
		}else if("1".equals(admin.getFxEnterprise().getBusinessType()+"")){
			return "interface_document/billAPI";
		}else{
			return "interface_document/gasCardAPI";
		}
	}
}
