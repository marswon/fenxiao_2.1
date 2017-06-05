package com.kedang.fenxiao.schedule.monitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月15日 下午4:23:20 
 */
public class HttpParamUtil {

	static Logger logger = LogManager.getLogger(HttpParamUtil.class);

	private HttpParamUtil() {
	}

	private static final String CRNL = System.getProperty("line.separator");

	public static final String DEFAULT_ENCODE = "UTF-8";

	public static String sendPostParamHandler(String iUrl, Map<String, String> iParams, String iCharset) {
		String _oResponseVal = null;
		HttpClient _client = new HttpClient();
		// 针对连接建立的超时时间,测试时可将目的IP地址设为不存在的IP地址。
		_client.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		// 针对连接建立后,但是没有收到response的超时时间
		_client.getHttpConnectionManager().getParams().setSoTimeout(60000);
		PostMethod _postMethod = new PostMethod(iUrl);
		// 设置编码
		_postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, (iCharset != null ? iCharset : DEFAULT_ENCODE));
		// 设置重连处理方法
		_postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		// 设置Post数据
		if (!iParams.isEmpty()) {
			int i = 0;
			NameValuePair[] _postData = new NameValuePair[iParams.size()];
			for (Entry<String, String> _entry : iParams.entrySet()) {
				_postData[i] = new NameValuePair(_entry.getKey(), _entry.getValue());
				i++;
			}
			_postMethod.setRequestBody(_postData);
		}
		try {
			_client.executeMethod(_postMethod);
			if (_postMethod.getStatusCode() == HttpStatus.SC_OK) {
				_oResponseVal = getResponseString(_postMethod, iCharset);
			} else {
				if (_postMethod.getStatusLine() != null) {
					_oResponseVal = _postMethod.getStatusLine().toString();
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			if (_postMethod.getStatusLine() != null) {
				_oResponseVal = _postMethod.getStatusLine().toString();
			}
		} finally {
			_postMethod.releaseConnection();
		}
		return _oResponseVal;
	}

	public static String sendGetParamHandler(String iUrl, Map<String, String> iParams, String iCharset) {
		String _oResponseVal = null;
		HttpClient _client = new HttpClient();
		// 针对连接建立的超时时间,测试时可将目的IP地址设为不存在的IP地址。
		_client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
		// 针对连接建立后,但是没有收到response的超时时间
		_client.getHttpConnectionManager().getParams().setSoTimeout(100000);
		GetMethod _getMethod = new GetMethod(iUrl);
		// 设置编码
		_getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, (iCharset != null ? iCharset : DEFAULT_ENCODE));
		// 设置重连处理方法
		_getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		// 设置Get数据
		if (!iParams.isEmpty()) {
			int _i = 0;
			NameValuePair[] _getData = new NameValuePair[iParams.size()];
			for (Entry<String, String> _entry : iParams.entrySet()) {
				_getData[_i] = new NameValuePair(_entry.getKey(), _entry.getValue());
				_i++;
			}
			_getMethod.setQueryString(_getData);
		}
		try {
			_client.executeMethod(_getMethod);
			if (_getMethod.getStatusCode() == HttpStatus.SC_OK) {
				_oResponseVal = getResponseString(_getMethod, iCharset);
			} else {
				if (_getMethod.getStatusLine() != null) {
					_oResponseVal = _getMethod.getStatusLine().toString();
				}
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			if (_getMethod.getStatusLine() != null) {
				_oResponseVal = _getMethod.getStatusLine().toString();
			}
		} finally {
			_getMethod.releaseConnection();
		}
		return _oResponseVal;
	}


	private static String getResponseString(PostMethod iMethod, String iCharset) {
		BufferedReader _br = null;
		InputStreamReader _isr = null;
		StringBuffer _sb = null;
		try {
			_isr = new InputStreamReader(iMethod.getResponseBodyAsStream(), (iCharset != null ? iCharset : DEFAULT_ENCODE));
			_br = new BufferedReader(_isr);
			_sb = new StringBuffer();
			String _tempstr = "";
			while ((_tempstr = _br.readLine()) != null) {
				_sb.append(_tempstr + CRNL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (_br != null) {
					_br.close();
				}
				if (_isr != null) {
					_isr.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return _sb != null ? _sb.toString().trim() : null;
	}

	private static String getResponseString(GetMethod iMethod, String iCharset) {
		BufferedReader _br = null;
		InputStreamReader _isr = null;
		StringBuffer _sb = null;
		try {
			_isr = new InputStreamReader(iMethod.getResponseBodyAsStream(), (iCharset != null ? iCharset : DEFAULT_ENCODE));
			_br = new BufferedReader(_isr);
			_sb = new StringBuffer();
			String _tempstr = "";
			while ((_tempstr = _br.readLine()) != null) {
				_sb.append(_tempstr + CRNL);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (_br != null) {
					_br.close();
				}
				if (_isr != null) {
					_isr.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return _sb != null ? _sb.toString().trim() : null;
	}
}
