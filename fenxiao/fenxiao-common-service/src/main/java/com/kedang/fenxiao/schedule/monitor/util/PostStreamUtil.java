package com.kedang.fenxiao.schedule.monitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.kedang.fenxiao.schedule.monitor.Constants;

public class PostStreamUtil
{

	static Logger logger = LogManager.getLogger(PostStreamUtil.class);

	private static final int TIME_OUT = 100000;

	private PostStreamUtil()
	{
	}

	public static String sendPostStreamHandler(String iAddress, String iCommString)
	{
		String _oRecString = null;
		URL _url = null;
		HttpURLConnection _urlConn = null;
		OutputStream _out = null;
		InputStreamReader _isr = null;
		InputStream _is = null;
		BufferedReader _br = null;
		try
		{
			_url = new URL(iAddress); // 根据数据的发送地址构建URL
			_urlConn = (HttpURLConnection) _url.openConnection(); // 打开链接
			_urlConn.setConnectTimeout(TIME_OUT); // 链接超时设置为100秒
			_urlConn.setReadTimeout(TIME_OUT); // 读取超时设置100秒
			_urlConn.setRequestMethod("POST"); // 链接相应方式为post
			_urlConn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
			_urlConn.setDoOutput(true);
			_urlConn.setDoInput(true);

			_out = _urlConn.getOutputStream();
			_out.write(iCommString.getBytes("utf-8"));
			_out.flush();

			_is = _urlConn.getInputStream();
			_isr = new InputStreamReader(_is, "UTF-8");
			_br = new BufferedReader(_isr);
			StringBuffer _tempSB = new StringBuffer();
			String _tempStr = null;
			while ((_tempStr = _br.readLine()) != null)
			{
				_tempSB.append(_tempStr + Constants.lineSeparator);
			}
			_oRecString = _tempSB.toString().trim();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (_out != null)
				{
					_out.close();
				}
				if (_br != null)
				{
					_br.close();
				}
				if (_isr != null)
				{
					_isr.close();
				}
				if (_is != null)
				{
					_is.close();
				}

			}
			catch (IOException e)
			{
				logger.error(e.getMessage(), e);
			}
			if (_urlConn != null)
			{
				_urlConn.disconnect();
			}
		}
		return _oRecString;
	}

	public static String sendPostStreamHandler(String iAddress, List<NameValuePair> nameValuePairs)
	{
		String _oRecString = null;
		try
		{
			HttpClient _httpclient = new HttpClient();
			HttpClientParams _params = new HttpClientParams();
			_params.setParameter(HttpClientParams.HTTP_CONTENT_CHARSET, "utf-8");
			_params.setConnectionManagerTimeout(60000L);
			_params.setSoTimeout(60000);
			_httpclient.setParams(_params);
			PostMethod _httpPost = new PostMethod(iAddress);
			_httpPost.setRequestBody(nameValuePairs.toArray(new NameValuePair[]
			{}));

			// 向服务器传递参数
			int _status = _httpclient.executeMethod(_httpPost);
			_oRecString = _httpPost.getResponseBodyAsString();
			if (HttpStatus.SC_OK == _status)
			{
				logger.info("路由到" + iAddress + "成功,返回：" + _oRecString);
			}
			else
			{
				logger.info("路由到" + iAddress + "失败,返回：" + _oRecString);
			}
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
		}
		return _oRecString;
	}

	public static String sendPostStreamHandler4Reprot(String iAddress, String iCommString)
	{
		String _oRecString = null;
		URL _url = null;
		HttpURLConnection _urlConn = null;
		OutputStream _out = null;
		InputStreamReader _isr = null;
		InputStream _is = null;
		BufferedReader _br = null;
		try
		{
			_url = new URL(iAddress); // 根据数据的发送地址构建URL
			_urlConn = (HttpURLConnection) _url.openConnection(); // 打开链接
			_urlConn.setConnectTimeout(10000); // 链接超时设置为10秒
			_urlConn.setReadTimeout(10000); // 读取超时设置10秒
			_urlConn.setRequestMethod("POST"); // 链接相应方式为post
			_urlConn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
			_urlConn.setDoOutput(true);
			_urlConn.setDoInput(true);

			_out = _urlConn.getOutputStream();
			_out.write(iCommString.getBytes("utf-8"));
			_out.flush();

			_is = _urlConn.getInputStream();
			_isr = new InputStreamReader(_is, "UTF-8");
			_br = new BufferedReader(_isr);
			StringBuffer _tempSB = new StringBuffer();
			String _tempStr = null;
			while ((_tempStr = _br.readLine()) != null)
			{
				_tempSB.append(_tempStr + Constants.lineSeparator);
			}
			_oRecString = _tempSB.toString().trim();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (_out != null)
				{
					_out.close();
				}
				if (_br != null)
				{
					_br.close();
				}
				if (_isr != null)
				{
					_isr.close();
				}
				if (_is != null)
				{
					_is.close();
				}

			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (_urlConn != null)
			{
				_urlConn.disconnect();
			}
		}
		return _oRecString;
	}

}
