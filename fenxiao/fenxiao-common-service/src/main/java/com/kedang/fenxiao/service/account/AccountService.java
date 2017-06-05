package com.kedang.fenxiao.service.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.DateProvider;
import org.springside.modules.utils.Encodes;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.User;
import com.kedang.fenxiao.entity.UserAccount;
import com.kedang.fenxiao.repository.AdminInfoDao;
import com.kedang.fenxiao.repository.CustomerConfigDao;
import com.kedang.fenxiao.repository.UserDao;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.service.common.FileManageService;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ExBeanUtils;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.enums.PasswordType;
import com.kedang.fenxiao.util.exception.ServiceException;

/**
 * 用户管理类.
 * 
 * @author calvin
 */
// Spring Service Bean的标识.
@Component
@Transactional(readOnly = true)
public class AccountService
{

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private AdminInfoDao adminInfoDao;
	@Autowired
	private FileManageService fileManageService;
	@Autowired
	private CustomerConfigDao customerConfigDao;

	private DateProvider dateProvider = DateProvider.DEFAULT;
	
	public List<User> getAllUser()
	{
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id)
	{
		if (id == null)
		{
			return null;
		}
		return userDao.findOne(id);
	}

	public User findUserByMphone(String mphone)
	{
		if (StringUtils.isBlank(mphone))
		{
			return null;
		}
		return userDao.findByMphone(mphone.trim());
	}
	
	public User findUserByIdCode(String idCode)
	{
		if (StringUtils.isBlank(idCode))
		{
			return null;
		}
		return userDao.findByIdCode(idCode.trim());
	}

	public Page<User> findAllUser(Specification<User> spec, Pageable pageable)
	{
		return userDao.findAll(spec, pageable);
	}

	public List<User> findAllUser(Map<String, Object> searchParams)
	{
		return userDao.findAll(SearchUtils.buildSpec(User.class, searchParams));
	}


	/**
	 * 会员注册(旧)(同盾设备指纹未添加)
	 * @param user
	 * @param request 
	 * @param deviceInfo 
	 * @param deviceType 
	 * @return
	 */
	public User registerUser(User user, HttpServletRequest request)
	{
	try
		{
			List<String> list = entryptPassword(user.getPwd(), null);
			user.setCreateTime(dateProvider.getDate());
			user.setUpdateTime(dateProvider.getDate());
			user.setAuthenticateType(0);
			user.setAuthenticateStatus(0);
			user.setStatus(1);
			user.setPwd(list.get(0));
			user.setSalt(list.get(1));
			user.setSex(0);
			user.setIsNewPwd((short) -1);
			user.setRecommendedRecord(user.getRecommended());
			User recommended = findUserByMphone(user.getRecommended());
			if (recommended != null)
			{
				String name = recommended.getName();
				if (StringUtils.isNotBlank(name))
				{
					user.setRecommended(recommended.getMphone() + "(" + name + ")");
				}
				else
				{
					user.setRecommended(recommended.getMphone());
				}
			}
			else
			{
				user.setRecommended("自然注册");
			}
			user = userDao.save(user);
			//生成用户初始信用额度账户
			UserAccount userAccount = new UserAccount();
			userAccount.setStatus(1);
			userAccount.setWalletAmount(new BigDecimal(0));
			userAccount.setCreateTime(dateProvider.getDate());
			userAccount.setUpdateTime(dateProvider.getDate());
			userAccount.setAvailableAmount(new BigDecimal(0));
			userAccount.setFreeAmount(new BigDecimal(0));
			userAccount.setCreditAmount(new BigDecimal(0));
			userAccount.setUserId(user.getId());
		//	userAccountDao.save(userAccount);
		}
		catch (ServiceException e)
		{
			logger.error("会员注册时异常，异常：" + e.getCause());
			throw new ServiceException("会员注册时异常，异常：" + e.getCause());
		}
		return user;
	}
	
	/**
	 * 会员注册
	 * @param user
	 * @param request 
	 * @param deviceInfo 
	 * @param deviceType 
	 * @return
	 */
	public User registerUser(User user, HttpServletRequest request, String deviceInfo, String deviceType)
	{
	try
		{
			List<String> list = entryptPassword(user.getPwd(), null);
			user.setCreateTime(dateProvider.getDate());
			user.setUpdateTime(dateProvider.getDate());
			user.setAuthenticateType(0);
			user.setAuthenticateStatus(0);
			user.setStatus(1);
			user.setPwd(list.get(0));
			user.setSalt(list.get(1));
			user.setSex(0);
			user.setIsNewPwd((short) -1);
			user.setRecommendedRecord(user.getRecommended());
			User recommended = findUserByMphone(user.getRecommended());
			if (recommended != null)
			{
				String name = recommended.getName();
				if (StringUtils.isNotBlank(name))
				{
					user.setRecommended(recommended.getMphone() + "(" + name + ")");
				}
				else
				{
					user.setRecommended(recommended.getMphone());
				}
			}
			else
			{
				user.setRecommended("自然注册");
			}
			user = userDao.save(user);
			//生成用户初始信用额度账户
			UserAccount userAccount = new UserAccount();
			userAccount.setStatus(1);
			userAccount.setWalletAmount(new BigDecimal(0));
			userAccount.setCreateTime(dateProvider.getDate());
			userAccount.setUpdateTime(dateProvider.getDate());
			userAccount.setAvailableAmount(new BigDecimal(0));
			userAccount.setFreeAmount(new BigDecimal(0));
			userAccount.setCreditAmount(new BigDecimal(0));
			userAccount.setUserId(user.getId());
		//	userAccountDao.save(userAccount);
		}
		catch (ServiceException e)
		{
			logger.error("会员注册时异常，异常：" + e.getCause());
			throw new ServiceException("会员注册时异常，异常：" + e.getCause());
		}
		return user;
	}

	/**
	 * 注册成功消息推送
	 * @param user 用户信息
	 */
	@SuppressWarnings("unused")
	private void pushMsg(User user)
	{
		try
		{
			if (null==user)
			{
				return;
			}else{
				//发送推送消息
			}
		}
		catch (ServiceException e)
		{
			logger.error("会员注册时推送消息，异常：" + e.getCause());
		}
		catch (Exception e)
		{
			logger.error("会员注册时推送消息，异常：" + e.getCause());
		}
		
	}

	/**
	 * 会员信息修改
	 * @param user
	 * @param oldPwd
	 * @param oldPayPwd
	 * @return
	 */
	@Transactional
	public User updateUser(User user)
	{
		user.setUpdateTime(dateProvider.getDate());
		if (StringUtils.isNotBlank(user.getPayPwd()))
		{
			user.setPayPwdStatus(Constant.INT_YES);
		}
		else
		{
			user.setPayPwdStatus(Constant.INT_NO);
		}
		return userDao.save(user);
	}

	/**
	 * 找回密码
	 * @param mphone
	 * @param pwd
	 * @return
	 */
	@Transactional
	public User forgotPwd(String mphone, String pwd, Integer type)
	{
		User old = findUserByMphone(mphone);
		if (old == null)
		{
			throw new ServiceException("手机号还未注册");
		}
		List<String> list = entryptPassword(pwd, old.getSalt());
		if (type == 1)
		{
			old.setPwd(list.get(0));
		}
		else
		{
			old.setPayPwd(list.get(0));
		}
		if (StringUtils.isNotBlank(old.getPayPwd()))
		{
			old.setPayPwdStatus(Constant.INT_YES);
		}
		else
		{
			old.setPayPwdStatus(Constant.INT_NO);
		}
		return userDao.save(old);
	}

	/**
	 * 修改原登录密码
	 * @param id
	 * @param pwd
	 * @param oldPwd
	 * @return
	 */
	@Transactional
	public User updatePwd(Long id, String pwd, String oldPwd)
	{
		User old = userDao.findOne(id);
		if (old == null)
		{
			throw new ServiceException("用户不存在");
		}
		List<String> oldPwdList = entryptPassword(oldPwd, old.getSalt());
		if (!StringUtils.equalsIgnoreCase(oldPwdList.get(0), old.getPwd()))
		{
			throw new ServiceException("原登录密码错误");
		}
		List<String> list = entryptPassword(pwd, old.getSalt());
		old.setPwd(list.get(0));
		if (StringUtils.isNotBlank(old.getPayPwd()))
		{
			old.setPayPwdStatus(Constant.INT_YES);
		}
		else
		{
			old.setPayPwdStatus(Constant.INT_NO);
		}
		return userDao.save(old);
	}

	/**
	 * 设置或修改交易密码
	 * @param id
	 * @param payPwd
	 * @param oldPayPwd
	 * @return
	 */
	@Transactional
	public User savePayPwd(Long id, String payPwd, String oldPayPwd)
	{
		User old = userDao.findOne(id);
		if (old == null)
		{
			throw new ServiceException("用户不存在");
		}
		if (StringUtils.isNotBlank(oldPayPwd))
		{
			List<String> oldPayPwdList = entryptPassword(oldPayPwd, old.getSalt());
			if (!StringUtils.equalsIgnoreCase(oldPayPwdList.get(0), old.getPayPwd()))
			{
				throw new ServiceException("原支付密码错误");
			}
		}
		List<String> list = entryptPassword(payPwd, old.getSalt());
		if (StringUtils.equalsIgnoreCase(list.get(0), old.getPwd()))
		{
			throw new ServiceException("新支付密码不能和登录密码相同");
		}
		old.setPayPwd(list.get(0));
		old.setPayPwdStatus(Constant.INT_YES);
		return userDao.save(old);
	}
	
	

	public boolean checkPayPwd(Long userId, String payPwd)
	{
		User old = userDao.findOne(userId);
		if (old != null)
		{
			List<String> list = entryptPassword(payPwd, old.getSalt());
			if (!StringUtils.equalsIgnoreCase(list.get(0), old.getPwd()))
			{
				return true;
			}
		}
		return false;
	}


	/**
	 * 会员从接口登录
	 * @param user
	 * @return
	 */
	public User apiLogin(User user)
	{
		User old = findUserByMphone(user.getMphone());
		if (old != null)
		{
			if (checkUserPwd(old, user.getPwd(), PasswordType.dl.getType()))
			{
				if (StringUtils.isNotBlank(old.getPayPwd()))
				{
					old.setPayPwdStatus(Constant.INT_YES);
				}
				else
				{
					old.setPayPwdStatus(Constant.INT_NO);
				}
				//将用户密码密文和salt隐藏
				old.setPwd(null);
				old.setSalt(null);
				old.setPayPwd(null);
				return old;
			}
		}
		return null;
	}
	 
	/**
	 * 会员从接口登录(新)(同盾设备指纹未添加)
	 * @param request 
	 * @param deviceInfo 
	 * @param deviceType 
	 * @param user
	 * @return
	 */
	public User apiNewLogin(String loginName,String pwd, HttpServletRequest request)
	{
		User old = new User();
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idCode = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//定义判别用户手机号
		Pattern phone = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
		//通过Pattern获得Matcher  
		Matcher isPhone = phone.matcher(loginName); 
		Matcher isIdCode = idCode.matcher(loginName); 
		if (!isPhone.matches()&&!isIdCode.matches())
		{
			throw new ServiceException("手机号/身份证号输入格式不正确");
		}
		if (isPhone.matches())
		{
			if (StringUtils.isNotBlank(loginName))
			{
				old = findUserByMphone(loginName);
				if(null!=old){
					if (old.getStatus()==2)
					{
						throw new ServiceException("用户已经被冻结");
					}
				}
			}
		}else if (isIdCode.matches())
		{
			if (StringUtils.isNotBlank(loginName))
			{
				old = findUserByIdCode(loginName);
				if(null!=old){
					if (old.getStatus()==2)
					{
						throw new ServiceException("用户已经被冻结");
					}
				}
			}
		}
		if (old != null)
		{
			if (checkUserPwd(old, pwd, PasswordType.dl.getType()))
			{
				if (StringUtils.isNotBlank(old.getPayPwd()))
				{
					old.setPayPwdStatus(Constant.INT_YES);
				}
				else
				{
					old.setPayPwdStatus(Constant.INT_NO);
				}
				//将用户密码密文和salt隐藏
				old.setPwd(null);
				old.setSalt(null);
				old.setPayPwd(null);
				/**
				 * 同盾登录接入(异步)
				 */
				return old;
			}
		}
		return null;
	}
	
	 /**
	 * 会员从接口登录(新)
	 * @param request 
	 * @param deviceInfo 
	 * @param deviceType 
	 * @param user
	 * @return
	 */
	public User apiNewLogin(String loginName,String pwd, HttpServletRequest request, String deviceInfo, String deviceType)
	{
		User old = new User();
		//定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
		Pattern idCode = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
		//定义判别用户手机号
		Pattern phone = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");
		//通过Pattern获得Matcher  
		Matcher isPhone = phone.matcher(loginName); 
		Matcher isIdCode = idCode.matcher(loginName); 
		if (!isPhone.matches()&&!isIdCode.matches())
		{
			throw new ServiceException("手机号/身份证号输入格式不正确");
		}
		if (isPhone.matches())
		{
			if (StringUtils.isNotBlank(loginName))
			{
				old = findUserByMphone(loginName);
				if(null!=old){
					if (old.getStatus()==2)
					{
						throw new ServiceException("用户已经被冻结");
					}
				}
			}
		}else if (isIdCode.matches())
		{
			if (StringUtils.isNotBlank(loginName))
			{
				old = findUserByIdCode(loginName);
				if(null!=old){
					if (old.getStatus()==2)
					{
						throw new ServiceException("用户已经被冻结");
					}
				}
			}
		}
		if (old != null)
		{
			if (checkUserPwd(old, pwd, PasswordType.dl.getType()))
			{
				if (StringUtils.isNotBlank(old.getPayPwd()))
				{
					old.setPayPwdStatus(Constant.INT_YES);
				}
				else
				{
					old.setPayPwdStatus(Constant.INT_NO);
				}
				//将用户密码密文和salt隐藏
				old.setPwd(null);
				old.setSalt(null);
				old.setPayPwd(null);
				/**
				 * 同盾登录接入(异步)
				 */
				return old;
			}
		}
		return null;
	}

	/**
	 * 校验用户上传密码是否正确
	 * @param user
	 * @param checkPwd
	 * @return
	 */
	public boolean checkUserPwd(User user, String checkPwd, int type)
	{
		List<String> list = entryptPassword(checkPwd, user.getSalt());
		if (type == PasswordType.dl.getType())
		{
			if (StringUtils.equalsIgnoreCase(user.getPwd(), list.get(0)))
			{
				return true;
			}
		}
		else
		{
			if (StringUtils.equalsIgnoreCase(user.getPayPwd(), list.get(0)))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据id查找管理员
	 * @param id
	 * @return
	 */
	public AdminInfo findAdminById(Long id)
	{
		if (id == null)
		{
			return null;
		}
		return adminInfoDao.findOne(id);
	}


	/**
	 * 根据登录名查找管理员
	 * @param loginName
	 * @return
	 */
	public AdminInfo findAdminByLoginName(String loginName)
	{
		if (StringUtils.isBlank(loginName))
		{
			return null;
		}
		return adminInfoDao.findByLoginName(loginName);
	}

	/**
	 * 注册管理员
	 * @param adminInfo
	 * @return
	 */
	@Transactional
	public AdminInfo registerAdmin(AdminInfo adminInfo)
	{
		adminInfo.setCreateTime(dateProvider.getDate());
		adminInfo.setIsWorking(0);
		adminInfo.setStatus(1);
		List<String> list = entryptPassword(adminInfo.getPwd(), null);
		adminInfo.setPwd(list.get(0));
		adminInfo.setSalt(list.get(1));
		return adminInfoDao.save(adminInfo);
	}

	/**
	 * 修改管理员信息
	 * @param adminInfo
	 * @return
	 */
	@Transactional
	public AdminInfo updateAdmin(AdminInfo adminInfo)
	{
		AdminInfo old = adminInfoDao.findOne(adminInfo.getId());
		if (old == null)
		{
			throw new ServiceException("管理员不存在");
		}
		if (StringUtils.isNotBlank(adminInfo.getPwd()))
		{
			List<String> list = entryptPassword(adminInfo.getPwd(), old.getSalt());
			adminInfo.setPwd(list.get(0));
			adminInfo.setSalt(list.get(1));
		}
		adminInfo.setUpdateTime(dateProvider.getDate());
		ExBeanUtils.copyIgnoreNulls(old, adminInfo);
		return adminInfoDao.save(old);
	}

	/**
	 * 修改管理员工作信息
	 * @param adminInfo
	 * @return
	 */
	@Transactional
	public AdminInfo updateAdminIsWorking(AdminInfo adminInfo)
	{
		AdminInfo old = adminInfoDao.findOne(adminInfo.getId());
		if (old == null)
		{
			throw new ServiceException("管理员不存在");
		}
		adminInfo.setUpdateTime(dateProvider.getDate());
		ExBeanUtils.copyIgnoreNulls(old, adminInfo);
		return adminInfoDao.save(old);
	}


	@Transactional
	public void deleteUser(Long id)
	{
		if (isSupervisor(id))
		{
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id)
	{
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName()
	{
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public List<String> entryptPassword(String password, String salt)
	{
		List<String> list = new ArrayList<String>();
		byte[] byteSalt = null;
		if (StringUtils.isBlank(salt))
		{
			byteSalt = Digests.generateSalt(SALT_SIZE);
			salt = Encodes.encodeHex(byteSalt);
		}
		else
		{
			byteSalt = Encodes.decodeHex(salt);
		}
		byte[] hashPassword = Digests.sha1(password.getBytes(), byteSalt, HASH_INTERATIONS);
		list.add(Encodes.encodeHex(hashPassword));
		list.add(salt);
		return list;
	}

		public static void main(String[] args) {
			List<String> list = new AccountService().entryptPassword("123456","71d76800c9d42c9b");
			System.out.println(list.get(0).toString()+"----1");
//			AccountService ac = new AccountService();
//			List<String> list = ac.entryptPassword("654321","37849f7f45b472c2");
//			System.out.println(list.get(0).toString()+"----1");
//			List<String> list2 = ac.entryptPassword("654321","37849f7f45b472c2");
//			System.out.println(list2.get(0).toString()+"----2");
		}
	/**
	 * 保存商户详情图片
	 * @param request
	 * @return
	 */
	public String[] saveShxq(HttpServletRequest request,String constant)
	{
		String result[] = {"", ""};
		StringBuffer detail_pic = new StringBuffer();
		for (int i = 1; i <= 5; i++)
		{
			String pic = fileManageService.saveLocalFile(request, constant, "pic" + i);
			if (StringUtils.isNotBlank(pic))
			{
				detail_pic.append(pic).append(",");
			}
		}
		String dPic = detail_pic.toString();
		if (dPic.contains(","))
		{
			result[0] = dPic.split(",")[0];
		}
		result[1] =dPic;
		return result;
	}
	/**
	 * 保存手持学生证图片
	 * @param request
	 * @return
	 */
	public String[] saveScxsz(HttpServletRequest request,String constant)
	{
		String [] scxsz = {"",""};
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsDetail = new JSONObject();
		String pic = fileManageService.saveLocalFileMany(request, constant);
		if (null != pic && "" != pic)
		{
			String[] picData = pic.split(",");
			if (null != picData && picData.length > 0)
			{
				for (int i = 1; i <=picData.length; i++)
				{
					map.put("pic" + i, picData[i-1]);
				}
			}
			try
			{
				jsDetail = new JSONObject(map);
				scxsz[0] = (String) map.get("pic1");
				scxsz[1] = jsDetail.toString();
			}
			catch (Exception e)
			{
				throw new ServiceException("保存图片异常");
			}
		}
		return scxsz;
	}
	
	/**
	 * 忘记密码身份证与手机验证
	 * 
	 */
	@Transactional
	public User verifyUser(Long id,String idCode,String phone)
	{
		User old = userDao.findOne(id);
		if (old == null)
		{
			throw new ServiceException("用户不存在");
		}
		if (StringUtils.isNotBlank(idCode)&&StringUtils.isNotBlank(phone))
		{
			//校验身份证号和手机号是否正确
			if (!StringUtils.equalsIgnoreCase(old.getIdCode(), idCode))
			{
				throw new ServiceException("身份证号不正确");
			}else if (!StringUtils.equalsIgnoreCase(old.getMphone(), phone))
			{
				throw new ServiceException("手机号不正确");
			}
		}
		return old;
	}
	
	/**
	 * 设置交易密码
	 * @param userId
	 * @param pwd
	 * @param user
	 * @return
	 */
	@Transactional
	public User setPayPwd(Long userId,String pwd,User user)
	{
		User old = userDao.findOne(userId);
		User u = null;
		if (old == null)
		{
			throw new ServiceException("用户不存在");
		}
		List<String> list = entryptPassword(pwd, old.getSalt());
		old.setPayPwd(list.get(0));
		old.setPayPwdStatus(Constant.INT_YES);
		old.setIsNewPwd((short) 1);
		user.setPayPwdError((short) 0);
		user.setPayPwdFreeTime(null);
		u = userDao.save(old);
		//发送推送消息
		//发送短信
//		smsMsgService.sendMsg(u.getMphone(), "主人，您的付壹代帐号设置支付密码成功，请知悉！");
		return u;
	}
}
