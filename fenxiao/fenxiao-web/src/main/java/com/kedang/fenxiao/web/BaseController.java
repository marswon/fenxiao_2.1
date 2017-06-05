package com.kedang.fenxiao.web;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.exception.ServiceException;
import com.kedang.fenxiao.util.po.ResultDo;


/**
 *	web基础控制器
 *	用来捕获异常并返回json字符串
 */
public class BaseController {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	@ExceptionHandler
	@ResponseBody
	public ResultDo exception(Exception e){
		logger.error("出现异常", e);
		if(e instanceof ServiceException){
			ServiceException se=(ServiceException)e;
			if(se.getCode()!=0){
				return ResultFactory.getFailedResult(se.getCode(), se.getMessage());
			}else{
				return ResultFactory.getFailedResult(ResultDo.SERVICE_EXCEPTION,se.getMessage());
			}
		}else if(e instanceof ObjectOptimisticLockingFailureException){
			return ResultFactory.getFailedResult(ResultDo.SERVICE_EXCEPTION,"该数据已经被更新或删除,本次操作失败");
		}else if(e instanceof DataIntegrityViolationException){
			return ResultFactory.getFailedResult(ResultDo.SERVICE_EXCEPTION,"重复数据");
		}
		return ResultFactory.getFailedResult(ResultDo.UNKNOW_EXCEPTION, "系统内部异常!");
	}
}
