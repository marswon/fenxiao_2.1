package com.kedang.fenxiao.web;

import com.google.gson.Gson;
import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.entity.FXPhoneLibrary;
import com.kedang.fenxiao.service.FXPhoneLibraryService;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.kedang.fenxiao.util.ResultFactory.getSuccessResult;

/**
 * Created by gegongxian on 17/4/20.
 */
@Controller
@RequestMapping(value = "phoneLibrary")
public class PhoneLibraryController
{
	private Logger logger = LoggerFactory.getLogger(PhoneLibraryController.class);

	@Autowired
	private FXPhoneLibraryService fxPhoneLibraryService;

	/**
	 * 打开号码卡管理
	 */
	@RequestMapping(value = "/toGasCardOrder")
	public String toGasCardOrder()
	{
		return "order/gasCardOrder";
	}

	/**
	 * 查询所有号码库
	 * @param request
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "findAll")
	@ResponseBody
	public Page<FXPhoneLibrary> findAll(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "rows", defaultValue = "10", required = false) int rows)
	{
		Page<FXPhoneLibrary> pageList = null;
		try
		{
			logger.info("====== start PhoneLibraryController.findAll ======");
			Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
			//searchParams = new HashMap<String, Object>();
			pageList = fxPhoneLibraryService.findAll(searchParams,
					new PageRequest(page - 1, rows, Sort.Direction.DESC, "commPort"));
			//查询号码当日发送条数统计
			Map<String, String> sendCountToDay = fxPhoneLibraryService.getSendCountToDay();
			if (null != sendCountToDay)
			{
				List<FXPhoneLibrary> fxPhoneLibraries = pageList.getContent();
				for (FXPhoneLibrary fxPhoneLibrary : fxPhoneLibraries)
				{
					String count = sendCountToDay.get(fxPhoneLibrary.getPhone());
					if (count == null)
					{
						count = "0";
					}
					fxPhoneLibrary.setSendCountToDay(count);
				}
			}
			logger.info("====== end PhoneLibraryController.findAll ,res[pageList=" + pageList + "] ======");
		}
		catch (Exception e)
		{
			logger.error("PhoneLibraryController.savePhoneLibrary error[" + e.getCause() + "]");
		}
		return pageList;
	}

	/**
	 * 保存
	 * @param fxPhoneLibrary
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public ResultDo savePhoneLibrary(FXPhoneLibrary fxPhoneLibrary)
	{
		try
		{
			logger.info("====== start PhoneLibraryController.savePhoneLibrary ======");
			logger.info("======"+new Gson().toJson(fxPhoneLibrary));
			fxPhoneLibrary.setCreateTime(new Date());
			fxPhoneLibrary.setUpdateTime(new Date());
			fxPhoneLibrary = fxPhoneLibraryService.save(fxPhoneLibrary);
			logger.info("====== end PhoneLibraryController.savePhoneLibrary ,res[pageList="
					+ new Gson().toJson(fxPhoneLibrary) + "] ======");
		}
		catch (Exception e)
		{
			logger.error("PhoneLibraryController.savePhoneLibrary error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("保存失败" + e.getMessage());
		}
		return getSuccessResult();
	}

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findOnePhoneLibrary")
	public ResultDo findOnePhoneLibrary(Long id)
	{
		FXPhoneLibrary fxPhoneLibrary = null;
		try
		{
			logger.info("====== start PhoneLibraryController.findOnePhoneLibrary ======");
			if (id == null)
			{
				return ResultFactory.getFailedResult("id为空");
			}
			else
			{
				fxPhoneLibrary = fxPhoneLibraryService.findOne(id);
			}
			logger.info("====== end PhoneLibraryController.findOnePhoneLibrary ,res[pageList="
					+ new Gson().toJson(fxPhoneLibrary) + "] ======");
		}
		catch (Exception e)
		{
			logger.error("PhoneLibraryController.findOnePhoneLibrary error[" + e.getCause() + "]");
			return ResultFactory.getFailedResult("查询失败" + e.getMessage());
		}
		return ResultFactory.getSuccessResult(fxPhoneLibrary);
	}

}
