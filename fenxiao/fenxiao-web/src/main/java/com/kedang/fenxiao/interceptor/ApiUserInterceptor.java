package com.kedang.fenxiao.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kedang.fenxiao.util.Constant;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

public class ApiUserInterceptor extends HandlerInterceptorAdapter{
	private static Logger logger = LoggerFactory.getLogger(ApiUserInterceptor.class);
	public String getExURL() {
		return exURL;
	}

	public void setExURL(String exURL) {
		this.exURL = exURL;
	}

	private String exURL;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url=request.getRequestURL().toString();
		HttpSession session=request.getSession();
		if (!url.matches(exURL)) {
			if (session.getAttribute(Constant.LOGIN_USER) == null) {
				logger.warn("还未登录");
				ResultDo result=ResultFactory.getFailedResult(ResultDo.NOT_LOGIN,"还未登录!");

				response.setContentType("text/plain");
				response.setCharacterEncoding("utf-8");

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.writeValue(response.getOutputStream(),result);

				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
}
