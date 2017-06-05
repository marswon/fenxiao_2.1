package com.kedang.fenxiao.schedule;

import java.util.Hashtable;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.User;
import com.kedang.fenxiao.repository.AdminRoleDao;
import com.kedang.fenxiao.repository.UserDao;
import com.kedang.fenxiao.service.RolePrivilegeService;
import com.kedang.fenxiao.service.account.AccountService;

@Component
@Lazy(false)
public class TaskingSchedule {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TaskingSchedule.class);
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	@Autowired
	private AdminRoleDao adminRoleDao;
	@SuppressWarnings("unused")
	private static Hashtable<Long,User> usermap = new Hashtable<Long, User>();
	public static Vector<AdminInfo> jcadminlist = new Vector<AdminInfo>();
	public static Vector<AdminInfo> gjadminlist = new Vector<AdminInfo>();
	@SuppressWarnings("unused")
	private static int jcindex = 0;
	@SuppressWarnings("unused")
	private static int gjindex = 0;
	
//	/***
//	 * 分配用户
//	 */
//	@SuppressWarnings("rawtypes")
//	@Scheduled(cron = "${tasking.distributionTime}")// 0/20 * 7-19 * * ?        "0 0/2 7-19 * * ?"
//	@Transactional(readOnly = false)
//	public synchronized void distribution(){
//		if(usermap.isEmpty()){
//			putMap();
//		}
//		if(!usermap.isEmpty()){
//		logger.info("筛选得到的待认证用户:"+usermap.size());
//		User u = null;
//		AdminInfo ad = null;
//		Date begin = DateUtils.getCurrDate();
//		logger.info("开始时间:"+DateUtils.getFormatDate(begin,"yyyy-MM-dd HH:mm:ss"));
//		   Iterator iterator = usermap.keySet().iterator();
//		   while(iterator.hasNext()){
//			  Long id = (Long) iterator.next();
//			  u = usermap.get(id);
//			  if(u.getAuthenticateType() < 63){
//				  ad = popJCAdmin();
//				  if(ad != null){
//				  userDao.updateUser(ad.getId(),u.getId());
//				  logger.info("将手机号为"+u.getMphone()+"的用户分配给账号为:"+ad.getLoginName()+"的管理员");
//				  }
//			  }else{
//				  ad = popGJAdmin();
//				  if(ad != null){
//				  userDao.updateUser(ad.getId(),u.getId());
//				  logger.info("将手机号为"+u.getMphone()+"的用户分配给账号为:"+ad.getLoginName()+"的管理员");
//				  }
//			  }
//		   }
//		   usermap.clear();
//	   Date end = DateUtils.getCurrDate();
//	   logger.info("结束时间:"+DateUtils.getFormatDate(begin,"yyyy-MM-dd HH:mm:ss"));
//	   logger.info("分配花费时间："+(end.getTime()-begin.getTime())+"毫秒");
//		}
//		//====================================
//		//分配银行卡任务到审核人员
//		Date begin = DateUtils.getCurrDate();
//		if (usetaskmap.isEmpty())
//		{
//			distributionMap();
//		}
//		if (!usetaskmap.isEmpty())
//		{
//			logger.info("筛选得到的待审核银行卡:" + usetaskmap.size());
//			AdminInfo ad = null;
//			UserTask u = null;
//			logger.info("开始时间:" + DateUtils.getFormatDate(begin, "yyyy-MM-dd HH:mm:ss"));
//			Iterator iterator = usetaskmap.keySet().iterator();
//			while (iterator.hasNext())
//			{
//				Long id = (Long) iterator.next();
//				u = usetaskmap.get(id);
//				ad = popJCAdmin();
//				if (ad != null)
//				{
//					userTaskDao.updateAdminId(ad.getId(), u.getId());
//					logger.info("将银行卡用户id" + u.getUserId() + "的银行卡分配给账号为:" + ad.getLoginName() + "的管理员");
//				}
//			}
//			usetaskmap.clear();
//			Date end = DateUtils.getCurrDate();
//			logger.info("结束时间:" + DateUtils.getFormatDate(begin, "yyyy-MM-dd HH:mm:ss"));
//			logger.info("分配花费时间：" + (end.getTime() - begin.getTime()) + "毫秒");
//		}
//		//====================================
//	}
//	
//	/***
//	 * 将待分配用户放入用户集合
//	 */
////	@Scheduled(cron = "0 0/1 7-19 * * ?")//7点到19点 
//	public void putMap(){
//		if(usermap.isEmpty()){
//		List<User> wfpusers = getUserAuth(0,150);
////		List<User> wfpgjusers = getGJUserAuth(0, 90);
//		/*Date begin = DateUtils.getCurrDate();
//		logger.info("开始填充......"+DateUtils.getFormatDate(begin,"yyyy-MM-dd HH:mm:ss"));*/
//		if(wfpusers.size() > 0){
//			for(User u : wfpusers){
//				usermap.put(u.getId(), u);
//			}
//		}
////		if(wfpgjusers.size() > 0){
////			for(User u : wfpgjusers){
////				usermap.put(u.getId(), u);
////			}
////		}
//		/*Date end = DateUtils.getCurrDate();
//		logger.info("本次填充时间："+(end.getTime()-begin.getTime())+"毫秒"+"table.size:"+usermap.size());*/
//		}
//	}
//	
//	/***
//	 * 获取未分配待认证用户
//	 * @param adminId 
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	public List<User> getUserAuth(int page,int rows){
//		Map<String,Object> map=new HashMap<String,Object>();
//		//待认证用户
//		List<Order> orders=new ArrayList<Order>();
////		orders.add(new Order(Direction.ASC, "authenticateType"));
//		orders.add(new Order(Direction.DESC, "authSubmitTime"));
//		Pageable pageable=new PageRequest(page, rows,new Sort(orders));
//		map.put("EQ_authenticateStatus", AuthStatus.drz.getType());
//		map.put("ISNULL_admin","null");  
//		Specification<User> spec=SearchUtils.buildSpec(User.class, map);
//		Page<User> result=accountService.findAllUser(spec, pageable);
//		List<User> users = result.getContent();
//		return users;
//	}
//	
//	/**
//	 * 获取待审核的银行卡任务
//	 * @return
//	 */
//	public List<UserTask> getUserTaskInHandleCard()
//	{
//		return null;
//	}
//	/**
//	 * 获取待审核银行卡
//	 * @param page
//	 * @param rows
//	 * @return
//	 */
//	public List<UserTask> getUserTask(int page,int rows){
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<Order> userTasks = new ArrayList<Order>();
//		userTasks.add(new Order(Direction.DESC,"createTime"));
//		Pageable pageable=new PageRequest(page, rows,new Sort(userTasks));
//		map.put("EQ_taskType", UserTaskType.bdyhk.getType());
//		map.put("EQ_taskStatus", UserTaskStatus.clz.getType());
//		map.put("ISNULL_adminId","null");  
//		Specification<UserTask> spec=SearchUtils.buildSpec(UserTask.class, map);
//		Page<UserTask> result=userTaskDao.findAll(spec, pageable);
//		List<UserTask> users = result.getContent();
//		return users;
//	}
//	/**
//	 * 分配银行卡任务
//	 */
//	public void distributionMap()
//	{
//		if(usetaskmap.isEmpty()){
//			//待认证银行卡
//			List<UserTask> userTasks = getUserTask(0,150);
//			if(userTasks.size() > 0){
//				for(UserTask u : userTasks){
//					usetaskmap.put(u.getId(), u);
//				}
//			}
//		}
//	}
//	/***
//	 * 获取基础认证操作管理员
//	 * @return
//	 */
//	public AdminInfo popJCAdmin(){
//		if(jcadminlist.isEmpty()){
//			List<AdminInfo> adms = getJCAdminInfo();
//			jcadminlist.addAll(adms);
//		}
//		if(jcadminlist.isEmpty()){
//			return null;
//		}
//		if(jcindex >= jcadminlist.size()){
//			jcindex = 0;
//		}
//		if(jcadminlist.size()>0){
//			AdminInfo admin = jcadminlist.get(jcindex);
//			jcindex++;
//			return admin;
//		}
//		return null;
//	}
//	
//	/***
//	 * 清空管理员列表
//	 */
//	@Scheduled(cron = "${tasking.clearadminlistTime}")
//	public void clearadminlist(){
//		logger.info("清空管理员列表");
//		jcadminlist.clear();
//		gjadminlist.clear();
//	}
//	
//	/***
//	 * 获取高级认证操作管理员
//	 * @return
//	 */
//	public AdminInfo popGJAdmin(){
//	/*	if(gjadminlist.isEmpty()){
//			List<AdminInfo> adms = getGJAdminInfo();
//			gjadminlist.addAll(adms);
//		}
//		if(gjadminlist.isEmpty()){
//			return null;
//		}*/
//		if(gjindex >= gjadminlist.size()){
//			gjindex = 0;
//		}
//		if(gjadminlist.size()>0){
//			AdminInfo admin = gjadminlist.get(gjindex);
//			gjindex++;
//			return admin;
//		}
//		return null;
//	}
//	
//	//获取 基础认证权限的管理员list//  初级认证审核权限code=1000101   id=39 
//	public List<AdminInfo> getJCAdminInfo(){
//		List<RolePrivilege> roprs = rolePrivilegeService.getRolePrivilegeListByPrivilegeCode(PrivilegeCode.gjrz.getCode());
//		List<Long> roleIds = new ArrayList<Long>();
//		for(RolePrivilege rpl:roprs){
//			roleIds.add(rpl.getRoleId());
//		}
//		List<AdminInfo> realadms = new ArrayList<AdminInfo>();
//		if(roleIds.size()>0){
//			List<AdminRole> adminroles = adminRoleDao.getAdminRoles(roleIds);
//			for(AdminRole arole:adminroles){
//				AdminInfo admin=arole.getAdminInfo();
//				if(admin!=null&&admin.getIsWorking().equals(Constant.INT_YES)){
//					realadms.add(admin);
//				}
//			}
//		}
//		return realadms;
//	}
//	//获取 高级认证权限的管理员list//  高级认证审核权限code=1000102   id=40
//	public List<AdminInfo> getGJAdminInfo(){
//		List<RolePrivilege> roprs = rolePrivilegeService.getRolePrivilegeListByPrivilegeCode(PrivilegeCode.gjrz.getCode());
//		List<Long> roleIds = new ArrayList<Long>();
//		for(RolePrivilege rpl:roprs){
//			roleIds.add(rpl.getRoleId());
//		}
//		List<AdminInfo> realadms = new ArrayList<AdminInfo>();
//		if(roleIds.size()>0){
//			List<AdminRole> adminroles = adminRoleDao.getAdminRoles(roleIds);
//			for(AdminRole arole:adminroles){
//				AdminInfo admin=arole.getAdminInfo();
//				if(admin!=null&&admin.getIsWorking().equals(Constant.INT_YES)){
//					realadms.add(admin);
//				}
//			}
//		}
//		return realadms;
//	}
}
