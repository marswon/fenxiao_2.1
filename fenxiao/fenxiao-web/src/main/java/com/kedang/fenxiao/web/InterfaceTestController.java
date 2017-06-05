package com.kedang.fenxiao.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "interfaceTest")
public class InterfaceTestController extends BaseController{
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(InterfaceTestController.class);
	
	/**
	 * 跳转接口测试
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "interfaceTest")
	public String interfaceTest(Model model) {
		return "interfaceTest/test";
	}
	
	/**
	 * 接口测试
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getInterfaceTest")
	public ResultDo getInterfaceTest(String jkmc,String yhkh,String yhkyhm,String sfzhm,String yhm){
		
		return null;
	}
	
}
