package com.kedang.fenxiao.web;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.captcha.Captcha;
import com.kedang.fenxiao.util.captcha.SpecCaptcha;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "captcha")
public class CaptchaController
{
	@Autowired
	private ResourcesConfig resourcesConfig;

	@ResponseBody
	@RequestMapping(value = "createCaptcha")
	public String createCaptcha(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		String code = null;
		Captcha captcha = new SpecCaptcha(120, 40, 5);// png格式验证码
		try
		{
			String captcha_code_path = resourcesConfig.getConfigString(Constant.CAPTCHA_CODE_PATH);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "No-cache");
			response.setDateHeader("Expires", 0);
			//指定生成的响应图片,一定不能缺少这句话,否则错误.  
			response.setContentType("image/jpeg");
			code = captcha.out(new FileOutputStream(captcha_code_path));
			System.out.println("code:" + code);
			FileInputStream fis = new FileInputStream(captcha_code_path);
			OutputStream os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 1024];
			while ((count = fis.read(buffer)) != -1)
				os.write(buffer, 0, count);
			os.flush();
			HttpSession session = request.getSession(false);
			session.removeAttribute("randCheckCode");
			session.setAttribute("randCheckCode", code);
			// 禁止图像缓存。         
			fis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return code;
	}

	/**
	 * 效验验证码
	 */
	@ResponseBody
	@RequestMapping(value = "validateCode")
	public ResultDo validateCode(HttpServletRequest request, HttpServletResponse response)
	{

		String validateC = (String) request.getSession().getAttribute("randCheckCode");
		String veryCode = request.getParameter("c");
		System.err.println("validateC:" + validateC + ",veryCode:" + veryCode);
		if (veryCode == null || "".equals(veryCode) || !veryCode.trim().equalsIgnoreCase(validateC.trim()))
		{
			return ResultFactory.getFailedResult("验证码错误");
		}
		else
		{
			return ResultFactory.getSuccessResult("验证码正确");
		}
	}
}
