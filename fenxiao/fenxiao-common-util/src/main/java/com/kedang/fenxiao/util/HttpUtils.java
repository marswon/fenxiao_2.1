package com.kedang.fenxiao.util;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpUtils
{
	private final static String DEFAULT_CHARSET = "UTF-8";

	private HttpUtils()
	{
	}

	/**
	  * 方法描述：
	  * @param url
	  * @param params
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  * @author: zhuwanlin
	  * @date: 2017年3月14日 上午10:57:20
	  */
	public static String sendGet(String url, Map<String, String> params) throws HttpException, IOException
	{
		return sendGet(url, params, DEFAULT_CHARSET);
	}

	/**
	  * 方法描述：
	  * @param url
	  * @param params
	  * @param charset
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  * @author: zhuwanlin
	  * @date: 2017年3月14日 上午10:57:23
	  */
	public static String sendGet(String url, Map<String, String> params, String charset) throws HttpException,
			IOException
	{
		String _result = null;
		HttpClient client = new HttpClient();
		client.getParams().setContentCharset(charset);
		//		GetMethod postMethod = new GetMethod(url);
		PostMethod postMethod = new PostMethod(url);
		if (params != null)
		{
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet)
			{
				postMethod.addParameter(entry.getKey(), entry.getValue());
			}
		}
		client.executeMethod(postMethod);
		_result = postMethod.getResponseBodyAsString();
		postMethod.releaseConnection();
		return _result;
	}
}
