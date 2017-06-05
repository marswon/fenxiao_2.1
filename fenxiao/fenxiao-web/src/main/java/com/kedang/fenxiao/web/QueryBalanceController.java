package com.kedang.fenxiao.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kedang.fenxiao.service.QueryBalanceService;
import com.kedang.fenxiao.util.ResourcesConfig;
import com.kedang.fenxiao.util.ResultFactory;
import com.kedang.fenxiao.util.po.ResultDo;

/**
 *******************************************************************
* Coryright (c) 2014-2024 杭州可当科技有限公司
* 项目名称: fenxiao-web
* @Author: gegongxian
* @Date: 2016年10月18日
* @Copyright: 2016 
* 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
* 情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
******************************************************************
 */
@Controller
@RequestMapping("queryBalance")
public class QueryBalanceController
{
	private Logger logger = LoggerFactory.getLogger(QueryBalanceController.class);
	@Autowired
	private QueryBalanceService queryBalanceService;
	@Autowired
	private ResourcesConfig resourcesConfig;

	/**
	 * 查询中琛源账号余额
	 * @param id
	 * @return
	 */
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping("query")
	public ResultDo query(String id)
	{
		logger.info("======开始查询通道余额======");
		if (StringUtils.isBlank(id))
		{
			return ResultFactory.getFailedResult("id不能为空");
		}
		if ((resourcesConfig.getConfigString("channel.zhongchenyuan.id")).equals(id))
		{
			logger.info("======开始查询中琛源通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryZhongChenYuanBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.jingying.id")).equals(id))
		{
			logger.info("======开始查询精赢通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryJingYingBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.leyao.id")).equals(id))
		{
			logger.info("======开始查询乐尧通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryLeYaoBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.dahan.id")).equals(id))
		{
			logger.info("======开始查询大汉三通通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryDaHanBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.fujianquanzhou.id")).equals(id))
		{
			logger.info("======开始查询福建泉州通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryFuJianQuanZhouBalance());
		}
		else if (resourcesConfig.getConfigString("channel.quxun.id").equals(id))
		{
			logger.info("======开始查询趣训通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryQuXunBalance());
		}
		else if (resourcesConfig.getConfigString("channel.xiangshangdianxin.id").equals(id))
		{
			logger.info("======开始查询向上流量通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryXiangShangBalance());
		}
		else if (resourcesConfig.getConfigString("channel.xiangshangyidongHF.id").equals(id))
		{
			logger.info("======开始查询向上话费通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryXiangShangHFBalance());
		}
		else if (resourcesConfig.getConfigString("channel.xiangshangliantong.id").equals(id))
		{
			logger.info("======开始查询向上通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryXiangShangBalance());
		}
		else if (resourcesConfig.getConfigString("channel.jietuo.id").equals(id))
		{
			logger.info("======开始查询杰拓通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryJieTuoBalance());
		}
		//		else if ((resourcesConfig.getConfigString("channel.shiheli.id"+"") + "").equals(id))
		//		{
		//			logger.info("======开始查询时和利通道余额======");
		//			return ResultFactory.getSuccessResult(queryBalanceService.querysSiHeLiBalance());
		//		}
		else if ((resourcesConfig.getConfigString("channel.diexin.id")).equals(id))
		{
			logger.info("======开始查询蝶信通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryDieXinBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.wuyou.id")).equals(id))
		{
			//湖北宜昌
			logger.info("======开始查询无忧通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryWuYouBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.yichongbao.id")).equals(id))
		{
			logger.info("======开始查询易充宝通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryYiChongBaoBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.youxijidi.id")).equals(id))
		{
			logger.info("======开始查询炫彩游戏基地通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryYouXuJiDiBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.wangyin.id")).equals(id))
		{
			logger.info("======开始查询网音基地通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryWangYinBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.fengzhushou.id")).equals(id))
		{
			logger.info("======开始查询蜂助手通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryFengZhuShouBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.nengliang.id")).equals(id))
		{
			logger.info("======开始查询能良通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryNengLiangBalance());
		}
		else if ((resourcesConfig.getConfigString("channel.youbige.id")).equals(id))
		{
			logger.info("======开始查询优比格通道余额======");
			return ResultFactory.getSuccessResult(queryBalanceService.queryYouBiGeBalance());
		}
	
		return null;
	}
}
