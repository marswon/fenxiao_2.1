package com.kedang.fenxiao.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class XiangShangUtil {

	/**
	 * 返回1970-1-1到当前时间的秒数
	 * 
	 * @return
	 */
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		try {
			try
			{
				d = sdf.parse("1970-01-01 00:00:01");
			}
			catch (java.text.ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {

			e.printStackTrace();
		}
		long t = (System.currentTimeMillis() - d.getTime()) / 1000;
		return String.valueOf(t);
	}

	/**
	 * 将键值对键的升序排列
	 * 
	 * @param paramMap
	 * @return
	 */
	public static String sortKeyValue(Map<String, Object> paramMap) {
		StringBuffer buf = new StringBuffer();
		Map<String, Object> orderMap = new TreeMap<String, Object>();
		orderMap.putAll(paramMap);
		Iterator<String> iterator = orderMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = String.valueOf(orderMap.get(key));
			if ((key != null) && (value.length() != 0))
				buf.append(key + value);
		}
		return buf.toString();
	}

	/**
	 * 组装请求url
	 * 
	 * @param paramMap
	 * @param url
	 * @return
	 */
	public static String getUrlByParamMap(Map<String, Object> paramMap,
			String url) {
		String param = "";
		Iterator<String> iterator = paramMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = String.valueOf(paramMap.get(key));
			try {
				value = java.net.URLEncoder.encode(value, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if ((key != null) && (value.length() != 0))
				param += "&" + key + "=" + value;
		}
		return url + "?" + param.substring(1);
	}

	/**
	 * 以http的方式请求
	 * 
	 * @param url
	 * @param reqstr
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String httpRequest(String url) {
		String response = "";
		try {
			response = getUrl(url);
		} catch (Exception e) {
			try {
				Thread.currentThread().sleep(100);
				response = getUrl(url);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new RuntimeException("请求url: " + url, e1);
			}
		}
		return response;
	}

	private static final String CONTENT_CHARSET = "UTF-8";

	@SuppressWarnings("deprecation")
	public static String getUrl(String url) {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				CONTENT_CHARSET);
		client.setTimeout(30000);
		GetMethod method = new GetMethod(url);
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				throw new RuntimeException("服务器响应错误[" + statusCode + "]");
			}
			byte[] responseBody = null;
			responseBody = method.getResponseBody();
			method.getRequestHeaders();
			return new String(responseBody, CONTENT_CHARSET);
		} catch (HttpException e) {

			throw new RuntimeException(e);
		} catch (IOException e) {
			
			throw new RuntimeException(e);
		} finally {
			method.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
	}


   
	   //根据秘钥在配置文件里的键值获得任意私钥
	   public static String getAnyPemPrivateKey(String anyKey)throws Exception{
		    Map<String, Object> keyMap = new HashMap<String, Object>();
		     keyMap = RSAUtil.initAnyPrivateKeyPem(anyKey);
		     String  anyKeyPrivatePem = RSAUtil.getPrivateKey(keyMap);  
		     return anyKeyPrivatePem;
	   }
}
