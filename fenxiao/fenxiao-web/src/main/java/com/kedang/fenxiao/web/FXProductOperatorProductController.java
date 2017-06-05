package com.kedang.fenxiao.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.entity.FXProductOperatorsProduct;
import com.kedang.fenxiao.service.FXProductOperatorProductService;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

@Controller
@RequestMapping(value = "/productOperatorProduct")
public class FXProductOperatorProductController
{
	private static final Logger logger = LoggerFactory.getLogger(FXProductOperatorProductController.class);

	@Autowired
	private FXProductOperatorProductService fxProductOperatorProductService;

	@ResponseBody
	@RequestMapping(value = "findPopByProId")
	public ResultDo findPopByProId(String id)
	{
		logger.info("=====FXProductOperatorProductController.findOneFxProductOperatorProduct  req[id:" + id + "]=====");
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("平台产品ID不能为空");
		}
		List<FXProductOperatorsProduct> _listPoP = fxProductOperatorProductService.findPopByProId(id);
		logger.info("=====FXProductOperatorProductController.findOneFxProductOperatorProduct  res[_listPoP:" + _listPoP
				+ "]=====");
		return ResultFactory.getSuccessResult(_listPoP);
	}
}
