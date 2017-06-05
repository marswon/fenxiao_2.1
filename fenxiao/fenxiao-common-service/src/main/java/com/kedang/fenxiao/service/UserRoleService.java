package com.kedang.fenxiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.AdminRole;
import com.kedang.fenxiao.entity.FXEnterprise;
import com.kedang.fenxiao.entity.FXRole;
import com.kedang.fenxiao.entity.Privilege;
import com.kedang.fenxiao.entity.RolePrivilege;
import com.kedang.fenxiao.repository.AdminInfoDao;
import com.kedang.fenxiao.repository.AdminRoleDao;
import com.kedang.fenxiao.repository.FXEnterpriseDao;
import com.kedang.fenxiao.repository.FXUserDao;
import com.kedang.fenxiao.repository.PrivilegeDao;
import com.kedang.fenxiao.repository.RolePrivilegeDao;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.JpaQueryUtils;
import com.kedang.fenxiao.util.exception.ServiceException;

@Component
@Transactional(readOnly = true)
public class UserRoleService
{

	@Autowired
	private AdminRoleDao adminRoleDao;
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	@Autowired
	private PrivilegeDao privilegeDao;
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private AdminInfoDao adminInfoDao;

	@Autowired
	private FXUserDao fxUserDao;

	@Autowired
	private FXEnterpriseDao fxEnterpriseDao;

	public Page<AdminRole> getUserRoleInformation(Map<String, Object> params, Pageable pageable)
	{
		Page<AdminRole> userRoles = adminRoleDao.findAll(JpaQueryUtils.buildSpecification(AdminRole.class, params),
				pageable);
		return userRoles;
	}

	public List<FXEnterprise> getRoleList()
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findAll();
	}
	public List<FXEnterprise> getRoleListByBusinessType(String type)
	{
		return (List<FXEnterprise>) fxEnterpriseDao.findByBusinessType(Integer.parseInt(type));
	}
	public AdminRole getAdminRole(Long id)
	{
		return adminRoleDao.findOne(id);
	}

	public List<AdminRole> findByAdminId(Long adminId)
	{
		AdminInfo ai = adminInfoService.findOne(adminId);
		if (ai == null)
		{
			throw new ServiceException("管理员不存在");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_adminInfo.id", ai.getId());
		Specification<AdminRole> spec = JpaQueryUtils.buildSpecification(AdminRole.class, map);
		return adminRoleDao.findAll(spec);
	}

	/***
	 * 修改管理员
	 * @param adrId
	 * @param roleId
	 * @param realName
	 * @param mphone
	 * @return
	 */
	@Transactional(readOnly = false)
	public AdminRole updateAdminRole(String adrId, String roleId, String realName, String mphone, String email,
			int status, String id, String adminRoleId, String password, String account,String ip)
	{
		//			System.out.println(1111);
		AdminRole adr = null;
		AdminRole admin = null;
		AdminInfo adminInfo = null;
		if (adminRoleId != "")
		{
			adr = adminRoleDao.findOne(Long.parseLong(adminRoleId));
			adr.setUpdateTime(new Date());
			adminInfo = adminInfoDao.findById(Long.parseLong(id));
			try
			{
				FXRole role = roleService.getRole(roleId);
				adr.setRole(role);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			if (adrId != null)
			{
				FXEnterprise fxEnterprise = fxEnterpriseDao.findEnterpriseById(adrId);
				adminInfo.setFxEnterprise(fxEnterprise);
			}
			adminInfo.setRealName(realName);
			List<String> list = entryptPassword(password,null);
			adminInfo.setPwd(list.get(0));
			adminInfo.setSalt(list.get(1));
			adminInfo.setMphone(mphone);
			adminInfo.setStatus(status);
			adminInfo.setLoginName(account);
			adminInfo.setEmail(email);
			adminInfo.setLastLoginIp(ip);
			adminInfo.setLastLoginTime(new Date());
			adr.setAdminInfo(adminInfo);
			admin = adminRoleDao.save(adr);

		}
		else
		{
			adminInfo = new AdminInfo();
			adminInfo.setRealName(realName);
			List<String> list = entryptPassword(password,null);
			adminInfo.setPwd(list.get(0));
			adminInfo.setSalt(list.get(1));
			adminInfo.setMphone(mphone);
			adminInfo.setStatus(status);
			adminInfo.setLoginName(account);
			adminInfo.setEmail(email);
			adminInfo.setLastLoginIp(ip);
			adminInfo.setCreateTime(new Date());
			adminInfo.setLastLoginTime(new Date());
			if (adrId != null)
			{
				FXEnterprise fxEnterprise = fxEnterpriseDao.findEnterpriseById(adrId);
				adminInfo.setFxEnterprise(fxEnterprise);
			}
			else
			{
				FXEnterprise fxEnterprise = fxEnterpriseDao.findEnterpriseById("10000");
				adminInfo.setFxEnterprise(fxEnterprise);
			}
			try
			{
				adr = new AdminRole();
				adr.setCreateTime(new Date());
				FXRole role = roleService.getRole(roleId);
				adr.setRole(role);
				adminInfoDao.save(adminInfo);
				adr.setAdminInfo(adminInfo);
				adr.setUpdateTime(new Date());
				admin = adminRoleDao.save(adr);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}

		return admin;
	}
	
	public static List<String> entryptPassword(String password, String salt)
	{
		List<String> list = new ArrayList<String>();
		byte[] byteSalt = null;
		if (StringUtils.isBlank(salt))
		{
			byteSalt = Digests.generateSalt(8);
			salt = Encodes.encodeHex(byteSalt);
		}
		else
		{
			byteSalt = Encodes.decodeHex(salt);
		}
		byte[] hashPassword = Digests.sha1(password.getBytes(), byteSalt, 1024);
		list.add(Encodes.encodeHex(hashPassword));
		list.add(salt);
		return list;
	}

	/***
	 * 保存管理员
	 * @param id
	 * @param adinfo
	 * @return
	 */
	@Transactional(readOnly = false)
	public AdminRole saveAdminRole(Long id, AdminInfo adminInfo)
	{
		FXRole role = roleService.getRole(id);
		AdminRole adro = new AdminRole();
		adro.setRole(role);
		adro.setAdminInfo(adminInfo);
		adro.setCreateTime(new Date());
		adro.setUpdateTime(new Date());
		return adminRoleDao.save(adro);
	}

	/***
	 *获取角色权限列表 
	 * @param roleId
	 * @return
	 */
	public List<RolePrivilege> getRolePrivilegeListByRoleId(Long roleId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_roleId", roleId);
		Specification<RolePrivilege> spec = JpaQueryUtils.buildSpecification(RolePrivilege.class, map);
		List<RolePrivilege> list = rolePrivilegeDao.findAll(spec);
		return list;
	}

	/**
	 * 获得所有权限
	 * @return
	 */
	public List<Privilege> findAllPrivilege()
	{
		return privilegeDao.findByIsGroup(Constant.INT_NO);
	}

	/**
	 * 根据管理员id查询
	 * @param adminId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Privilege> findPrivilegeByAdminId(Long adminId)
	{
		if (adminId == null)
		{
			return new ArrayList<Privilege>();
		}
		String hql = "from Privilege p where p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId in(select ar.role.id from AdminRole ar where ar.adminInfo.id=:adminId))";
		Query query = em.createQuery(hql);
		query.setParameter("adminId", adminId);
		List<Privilege> result = query.getResultList();
		return result;
	}

	/**
	 * 查询管理员是否拥有给定权限编码的权限
	 * @param adminId
	 * @param privilegeCode
	 * @return
	 */
	public boolean existsPrivilegeByAdminIdAndPrivilegeCode(Long adminId, String privilegeCode)
	{
		if (adminId == null || StringUtils.isBlank(privilegeCode))
		{
			return false;
		}
		String hql = "select count(*) from Privilege p where p.priCode=:privilegeCode and p.id in(select rp.privilege.id from RolePrivilege rp where rp.roleId in(select ar.role.id from AdminRole ar where ar.adminInfo.id=:adminId))";
		Query query = em.createQuery(hql);
		query.setParameter("privilegeCode", privilegeCode);
		query.setParameter("adminId", adminId);
		Object result = query.getSingleResult();
		if (result != null)
		{
			long count = (Long) result;
			if (count > 0)
			{
				return true;
			}
		}
		return false;
	}

	//		public AdminRole findMsgByAdminId(Long id)
	//		{
	//			return adminRoleDao.findByAdminId(id);
	//		}
	
	
}
