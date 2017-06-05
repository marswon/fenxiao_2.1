package com.kedang.fenxiao.service;

import com.kedang.fenxiao.entity.FXOrderRecord;
import com.kedang.fenxiao.entity.FXPhoneLibrary;
import com.kedang.fenxiao.repository.FXPhoneLibraryDao;
import com.kedang.fenxiao.util.CollectionUtils;
import com.kedang.fenxiao.util.DateUtils;
import com.kedang.fenxiao.util.SearchUtils;
import com.kedang.fenxiao.util.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gegongxian on 17/4/20.
 */
@Component
public class FXPhoneLibraryService
{
	private Logger logger = LoggerFactory.getLogger(FXPhoneLibraryService.class);

	@Autowired
	private FXPhoneLibraryDao fxPhoneLibraryDao;

	/**
	 * 查询表
	 * @param params
	 * @return
	 */
	public Page<FXPhoneLibrary> findAll(Map<String, Object> params, Pageable pageable)
	{

		Page<FXPhoneLibrary> phoneLibrary = fxPhoneLibraryDao
				.findAll(SearchUtils.buildSpec(FXPhoneLibrary.class, params), pageable);
		return phoneLibrary;
	}

	/**
	 * 保存
	 * @param fxPhoneLibrary
	 * @return
	 */
	public FXPhoneLibrary save(FXPhoneLibrary fxPhoneLibrary)
	{
		return fxPhoneLibraryDao.save(fxPhoneLibrary);
	}

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public FXPhoneLibrary findOne(Long id)
	{
		return fxPhoneLibraryDao.findOne(id);
	}

	/**
	 * 查询当日发送短信情况
	 * @return
	 */
	public Map<String, String> getSendCountToDay()
	{
		Map<String, String> sendCountToDay = new HashMap<String, String>();

		try
		{
			Date date = DateUtils.getCurrDate();
			List<Object[]> getSendCountToDay = fxPhoneLibraryDao.getSendCountToDay(DateUtils.dayBegin(date),
					DateUtils.dayEnd(date));
			if (CollectionUtils.isNotEmpty(getSendCountToDay))
			{
				for (Object[] obj : getSendCountToDay)
				{
					Object[] o = obj;
					if (null != o && o.length == 2)
					{
						String phone = o[0] + "";
						String acount = o[1] + "";
						sendCountToDay.put(phone, acount);
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("异常：" + e, e.getMessage());
			e.printStackTrace();
		}
		return sendCountToDay;
	}

}
