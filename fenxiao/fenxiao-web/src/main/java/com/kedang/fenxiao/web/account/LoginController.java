package com.kedang.fenxiao.web.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，

 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController
{
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String login()
	{
		return "account/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String login(String username, String password, Model model)
	{
		try
		{
			logger.info("====== start LoginController.login ,req[username=" + username + ",password=" + password
					+ "]======");
			Subject currentUser = SecurityUtils.getSubject();
			String result = "redirect:index";
			currentUser.logout();
			result = login(currentUser, username, password, model);
			logger.info("====== end LoginController.login ,res[result=" + result + "] ======");
			return result;
		}
		catch (Exception e)
		{
			logger.error("LoginController.login error[" + e.getCause() + "]");
			return "登陆异常";
		}
	}

	private String login(Subject currentUser, String username, String password, Model model)
	{
		try
		{
			logger.info("====== start LoginController.login ,req[currentUser=" + currentUser + ",username=" + username
					+ ",password=" + password + "]======");
			String result = "account/login";
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(false);
			try
			{
				currentUser.login(token);
				result = "redirect:index";
			}
			catch (UnknownAccountException uae)
			{
				model.addAttribute("message", "用户名或密码错误!");
				result = "account/login";
			}
			catch (IncorrectCredentialsException ice)
			{
				model.addAttribute("message", "用户名或密码错误!");
				result = "account/login";
			}
			catch (LockedAccountException lae)
			{
				model.addAttribute("message", "用户名或密码错误!");
				result = "account/login";
			}
			catch (AuthenticationException ae)
			{
				model.addAttribute("message", "用户名或密码错误!");
				result = "account/login";
			}
			logger.info("====== end LoginController.login ,res[result=" + result + "] ======");
			return result;
		}
		catch (Exception e)
		{
			logger.error("LoginController.login error[" + e.getCause() + "]");
			return "登陆异常";
		}
	}
}
