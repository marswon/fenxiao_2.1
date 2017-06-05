package com.kedang.fenxiao.web;

/**
 * 主界面控制器
 */
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.AdminInfo;
import com.kedang.fenxiao.entity.FXAdvert;
import com.kedang.fenxiao.schedule.TaskingSchedule;
import com.kedang.fenxiao.service.AdminInfoService;
import com.kedang.fenxiao.service.IndexService;
import com.kedang.fenxiao.service.account.ShiroDbRealm.ShiroUser;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "/index")
public class MainController extends BaseController
{
	private Logger logger = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private AdminInfoService adminInfoService;

	@Autowired
	private IndexService indexService;
	
	   @RequestMapping(value = "home")
	    public String openCamiView() {
	        return "home/home";
	    }

	@RequestMapping(value = "")
	public String index(Model model)
	{
		try
		{
			logger.info("====== start MainController.index ======");
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			AdminInfo admin = adminInfoService.findOne(shiroUser.getId());
			model.addAttribute("fxEnterprise", admin.getFxEnterprise());
			if (admin != null)
			{
				//status==1,表示该用户已注销，不可登陆
				if (null != admin.getStatus() && admin.getStatus() == 1)
				{

					Subject subject = SecurityUtils.getSubject();
					if (subject.isAuthenticated())
					{
						subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
					}
					return "error/404";
				}
				boolean flag = false;
				Vector<AdminInfo> jcadminlist = TaskingSchedule.jcadminlist;
				if (jcadminlist.contains(admin))
				{
					flag = true;
				}
				Vector<AdminInfo> gjadminlist = TaskingSchedule.gjadminlist;
				if (gjadminlist.contains(admin))
				{
					flag = true;
				}
				model.addAttribute("adminId", admin.getId());
				if (flag)
				{
					model.addAttribute("isWorking", 1);
				}
				else
				{
					model.addAttribute("isWorking", 0);
				}
			}
			logger.info("====== end MainController.index ,res[activity=" + admin + "] ======");
			return "index/index";
		}
		catch (Exception e)
		{
			logger.error("MainController.index error[" + e.getCause() + "]");
			return "首页异常";
		}
	}

	@ResponseBody
	@RequestMapping("queryJcAdmin")
	public ResultDo queryJcAdmin()
	{
		return ResultFactory.getSuccessResult(TaskingSchedule.jcadminlist);
	}

	@RequestMapping("queryGjAdmin")
	public ResultDo queryGjAdmin()
	{
		return ResultFactory.getSuccessResult(TaskingSchedule.gjadminlist);
	}

	@RequestMapping("toIndexPage")
	public String toIndexPage()
	{
		return "index/indexDetail";
	}

	@RequestMapping("getMessage")
	@ResponseBody
	public ResultDo getMessage(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		List<FXAdvert> pageList = null;
		try
		{
			logger.info("====== start MainController.getMessage ======");
			pageList = indexService.getFive(new PageRequest(page - 1, rows));
			logger.info("====== end MainController.getMessage ,res[_list" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("MainController.getMessage error[" + e.getCause() + "]");
		}
		return ResultFactory.getSuccessResult(pageList);
	}
}
