package com.kedang.fenxiao.schedule.blacklist;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kedang.fenxiao.util.ResourcesConfig;

public class RefreshIFMemThread implements Runnable
{
	private Logger logger = LogManager.getLogger(RefreshIFMemThread.class);

	private ResourcesConfig resourcesConfig;

	public RefreshIFMemThread(ResourcesConfig resourcesConfig)
	{
		this.resourcesConfig = resourcesConfig;
	}

	@Override
	public void run()
	{
		refreshIFMem();
	}

	public void refreshIFMem()
	{
		try
		{
			Thread.sleep(1000);
			logger.info("==开始刷新接口黑名单缓存==");
			String configURL = resourcesConfig.getConfigString(Constants.BLACK_LIST_REFRESH_URL);
			if (configURL != null)
			{

				String[] urls = configURL.split(";");
				for (String url : urls)
				{
					if (url != null && url.length() > 0)
					{
						logger.info("url:" + url);
						HttpClient client = new HttpClient();
						client.getParams().setContentCharset("UTF-8");
						PostMethod postMethod = new PostMethod(url);
						client.executeMethod(postMethod);
						String _resp = postMethod.getResponseBodyAsString();
						postMethod.releaseConnection();
						logger.info("返回结果为：" + _resp);
					}
				}
			}
			logger.info("==刷新接口黑名单缓存结束==");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
