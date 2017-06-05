package com.kedang.fenxiao.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.service.FXMobileAreaService;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "addMobileArea")
public class AddMobileAreaController
{

	private Logger logger = LoggerFactory.getLogger(AddMobileAreaController.class);

	@Autowired
	private FXMobileAreaService fxMobileAreaService;

	//	@ResponseBody
	//	@RequestMapping("add")
	//	public ResultDo addMobileArea(String mobiles)
	//	{
	//		logger.info("===添加号段表数据===");
	//		if (StringUtils.isBlank(mobiles))
	//		{
	//			return ResultFactory.getFailedResult("===请输入有效号码===");
	//		}
	//		return fxMobileAreaService.addFxMobileArea(mobiles);
	//	}

	@ResponseBody
	@RequestMapping("addAreaAndAreaName")
	public ResultDo addAreaAndAreaName(String mobiles)
	{
		logger.info("=====补充城市code，城市名称=====");
		return fxMobileAreaService.addAreaAndAreaName(mobiles);
	}
}
