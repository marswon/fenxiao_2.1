package com.kedang.fenxiao.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.service.AdminRoleService;
import com.kedang.fenxiao.service.RolePrivilegeService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.Constant;

public class PrivilegeInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	@Autowired
	private AdminRoleService adminRoleService;
	public String getExURL() {
		return exURL;
	}

	public void setExURL(String exURL) {
		this.exURL = exURL;
	}

	private String exURL;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		String url = request.getRequestURL().toString();
		if(url.matches(exURL)){
			return super.preHandle(request, response, handler);
		}
		String cxt = request.getContextPath();
		String realurl = StringUtils.substringAfter(url,cxt);
		ShiroUser u = getCurrentUser();
		if(u != null){
		/*AdminRole aro = adminRoleService.findByAdminId(u.id);
		List<RolePrivilege> rps = rolePrivilegeService.getRolePrivilegeListByRoleId(aro.getRole().getId());
		for(RolePrivilege rp:rps){
			if(realurl.matches(".*"+rp.getPrivilege().getResource()+"/?.*")){
				return super.preHandle(request, response, handler);
			}
		}*/
			Set<String> set = new HashSet<String>();
			if(u.id==Constant.ADMIN_NUMBER){
				List<Privilege> result=	adminRoleService.findAllPrivilege();
				for(Privilege p:result){
					set.add(p.getResource());
				}
			}else{
				List<Privilege> pList = adminRoleService.findPrivilegeByAdminId(u.id);
				for(Privilege p:pList){
					set.add(p.getResource());
				}
			}
			for(String s:set){
				if(realurl.matches(".*"+s+"/?.*")){
					return super.preHandle(request, response, handler);
				}
			}
			
		}
		return false;
	}
	
	private ShiroUser getCurrentUser() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user;
	}
	
//	public static void main(String[] args) {
//		String str= "/privilege/getPrivilegePage";
//		str.matches(".*"+"/privilege");
//	}
	
}
