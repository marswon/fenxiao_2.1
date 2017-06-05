package com.kedang.fenxiao.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kedang.fenxiao.common.Redis4Token;
import com.kedang.fenxiao.util.AES;
import com.kedang.fenxiao.util.AESUtil;
import com.kedang.fenxiao.util.BF;
import com.kedang.fenxiao.util.Base64;
import com.kedang.fenxiao.util.MD5Math;
import com.kedang.fenxiao.util.MD5Util;
import com.kedang.fenxiao.util.RSAUtil;
import com.kedang.fenxiao.util.SHA1Util;
import com.kedang.fenxiao.util.XiangShangUtil;

/**
 * ****************************************************************** Coryright
 * (c) 2014-2024 杭州可当科技有限公司 项目名称: fenxiao-common-service
 * 
 * @Author: gegongxian
 * @Date: 2016年10月18日
 * @Copyright: 2016 版权说明：本软件属于杭州可当科技有限公司所有，在未获得杭州可当科技有限公司正式授权
 *             情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知识产权的内容
 ******************************************************************
 */
@Component
public class QueryBalanceService {
	private static final Logger logger = LoggerFactory
			.getLogger(QueryBalanceService.class);
	@Autowired
	private Redis4Token redisToken;

	/**
	 * 中琛源
	 * 
	 * @return
	 */
	public static String queryZhongChenYuanBalance() {
		String userName = "15858269821@api";
		String userPwd = DigestUtils.md5Hex("269821@api");
		String userKey = "3214272b129e484d96553e2f05d43cc9";
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(
				"http://120.236.31.219:7070/flowRquest.do");
		httpClient.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"UTF-8");
		String sign = DigestUtils.md5Hex("userName=" + userName + "&userPwd="
				+ userPwd + userKey);
		// 请求信息封装
		NameValuePair[] data = { new NameValuePair("userName", userName),
				new NameValuePair("userPwd", userPwd),
				new NameValuePair("sign", sign),// 签名认证
				new NameValuePair("f", "queryOverAmount") // 方法
		};
		method.setRequestBody(data);
		try {
			httpClient.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			JSONObject json = JSONObject.parseObject(SubmitResult);
			System.out.println(json);
			if (null != json && json.containsKey("result")) {
				System.out.println("中琛源通道余额查询接口返回：：" + json);
				System.out.println("中琛源查询接口返回：：" + json.getString("result"));
				return json.getString("result");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sign;
	}

	/**
	 * 大汉三通
	 */

	public static String queryDaHanBalance() {
		String qureyurl = "http://if.dahanbank.cn/FCQueryBalanceServlet";
		String account = "AdminHZNC"; // 账号
		String pwd = "AdminHZNC";

		String sign = MD5Util.encrypt(account + MD5Util.encrypt(pwd));

		try {
			String param = "?account=" + account + "&sign=" + sign;

			// ?account=AdminHZNCsign=6434c3e940261c81dbeaa87d654a3f43clientOrderId=2017011700000003

			System.out.println("=============查询报文：" + param);

			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			GetMethod getMethod = new GetMethod(qureyurl + param);
			System.out.println(qureyurl + param);
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			String result = null;
			client.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
			result = (Float.valueOf(result) / 100) + "";
			logger.info("=====结束查询大汉余额=====" + result);

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 乐尧
	 */
	public static String queryLeYaoBalance() {
		String querybalanceurl = "http://api.ekhui.com/userBanlance";
		String userNo = "2017011400000415"; // 合作方用户编号
		String keyValue = "135F0D2A6E8FD9715512D7F68568928A"; // 密钥
		String sign = MD5Util.encrypt(userNo + keyValue);

		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			PostMethod postMethod = new PostMethod(querybalanceurl);

			postMethod.addParameter("userNo", userNo);
			postMethod.addParameter("ptOrderNo", querybalanceurl);
			postMethod.addParameter("sign", sign);

			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();

			System.out.println("乐尧查询接口返回" + result);

			Map<String, String> map = new HashMap<String, String>();
			map = getNodeData(postMethod.getResponseBodyAsString());

			if (null != map && map.containsKey("balance")) {
				System.out.println("乐尧通道余额查询接口返回：：" + map);
				System.out.println("乐尧查询接口返回：：" + map.get("balance"));
				return map.get("balance");
			}
			logger.info("=====结束查询乐尧余额=====");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 湖北时迅捷
	 */
	@SuppressWarnings("unused")
	public static String queryShiXunJieBalance() {
		System.out.println("=====开始查询湖北时迅捷余额=====");
		String action = "getBalance";
		String v = "1.1";
		String account = "kedang";
		String api = "2ad3667b467342beac3b5c77f7307893";
		String sign = MD5Math.GetMD5Code("account=" + account + "&key=" + api);
		String flowUrl = "http://114.55.75.222:8080/api.aspx";
		try {
			StringBuffer url = new StringBuffer();
			url.append(flowUrl);
			url.append("?v=" + v);
			url.append("&action=" + action);
			url.append("&account=" + account);
			url.append("&sign="
					+ MD5Math.GetMD5Code("account=" + account + "&key=" + api));
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(url.toString());
			client.executeMethod(postMethod);
			String result;
			result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			System.out.println("湖北时迅捷余额查询接口返回：：" + result);
			JSONObject json = JSONObject.parseObject(result);

			logger.info("=====结束查询湖北时迅捷余额=====");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 无忧
	 * 
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public String queryWuYouBalance() {
		String getBalanceUrl = "http://www.wuyoull.com/service/flow!getBalance.action";

		String memberId = "2022141892"; // 账户的ID
		String memberKey = "30749945db894a6aad94f8fee74278c4"; // 密钥（32位）

		JSONObject json = new JSONObject();
		json.put("memberId", memberId);
		json.put("memberKey", memberKey);
		System.out.println(json);

		String secretKey = "H5gOs1ZshKZ6WikN";
		String vector = "8888159601152533";
		String signbody;
		try {
			signbody = AESUtil.Encrypt(json.toString(), secretKey, vector);
			JSONObject jsonSign = new JSONObject(); // 签名
			jsonSign.put("code", signbody);
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(getBalanceUrl);
			postMethod.setRequestBody(jsonSign.toString());
			System.out.println("====参数====" + jsonSign.toString());

			httpClient.executeMethod(postMethod);

			// 读取返回内容
			byte[] responseBody = postMethod.getResponseBody();
			JSONObject jsonResult = JSONObject.parseObject(new String(
					responseBody));
			System.out.println("无忧查询接口返回" + jsonResult);

			JSONObject jsonResult1 = JSONObject.parseObject(jsonResult
					.getString("balance"));

			JSONObject jsonResult2 = JSONObject.parseObject(jsonResult1
					.getString("A"));

			if (null != jsonResult2 && jsonResult2.containsKey("balance")) {
				System.out.println("无忧通道余额查询接口返回：：" + jsonResult2);
				System.out.println("无忧查询接口返回：："
						+ jsonResult2.getString("balance"));
				return jsonResult2.getString("balance");
			}
			logger.info("=====结束查询无忧余额=====");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 蝶信
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String queryDieXinBalance() {
		String custInteId = "kedang01";// 客户接入代码
		String echo = (int) ((Math.random() * 9 + 1) * 10000) + "";
		String timestamp = new SimpleDateFormat("YYYYMMddHHmmss")
				.format(new Date());
		String version = "1";// 版本
		String key = "kedangmiyao01";
		String chargeSign;
		try {
			String source = custInteId + key + echo + timestamp;
			chargeSign = getSign(source);
			System.out.println("======" + chargeSign);
			String flowUrl = "http://47.93.80.151/custinte/query/balance.do";

			String requestParameter = "<request>" + "<head>" + "<custInteId>"
					+ custInteId + "</custInteId>" + "<echo>" + echo
					+ "</echo>" + "<timestamp>" + timestamp + "</timestamp>"
					+ "<version>" + version + "</version>" + "<chargeSign>"
					+ chargeSign + "</chargeSign>" + "</head>"

					+ "</request>";
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(flowUrl);
			postMethod.setRequestEntity(new StringRequestEntity(
					requestParameter, "text/xml", "utf-8"));
			client.executeMethod(postMethod);
			String result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			Map map = getNodeData(result);
			if (null != map && map.containsKey("balance")) {
				System.out.println("蝶信通道余额查询接口返回：：" + map);
				System.out.println("碟信查询接口返回：：" + map.get("balance"));
				return map.get("balance").toString();
			}
			logger.info("=====结束查询碟信余额=====");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 时和利
	 * 
	 * @return
	 */
	public String querysSiHeLiBalance() {
		String balanceUrl = "http://219.83.164.155/flow/balance";
		String sign = ""; // 签名
		String username = "kedang";
		String authorization = "7f7f305149c14c7599c8f25073a5f266"; // 授权码

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(date); // 时间戳

		String nonce = Base64.encode(username + ":" + timestamp);

		sign = MD5Util.encrypt(username + authorization + timestamp)
				.toLowerCase();

		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			GetMethod getMethod = new GetMethod(balanceUrl);

			getMethod.addRequestHeader(HTTP.CONTENT_TYPE,
					"application/x-www-form-urlencoded;charset=utf-8");

			getMethod.addRequestHeader("authorization", "sign=\"" + sign
					+ "\"," + "nonce=\"" + nonce + "\"");

			client.executeMethod(getMethod);
			String result = getMethod.getResponseBodyAsString();

			JSONObject json = JSONObject.parseObject(result);

			if (null != json && json.containsKey("balance")) {
				System.out.println("时和利通道余额查询接口返回：：" + json);
				System.out.println("时和利查询接口返回：：" + json.getString("balance"));
				return json.getString("balance");
			}
			logger.info("=====结束查询时和利余额=====");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 福建泉州
	 * 
	 * @return
	 */
	public String queryFuJianQuanZhouBalance() {
		logger.info("=====开始查询福建泉州余额=====");
		// 获取token
		String token = redisToken.get("FUJIANQUANZHOU_TOKEN");
		logger.info("===token[" + token + "]=======");
		if (StringUtils.isEmpty(token)) {
			logger.error("=====获取token异常====");
			return "获取token异常";
		}
		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PostMethod postMethod = new PostMethod(
					"http://27.155.65.86:8050/dfos/mAccountRestService/findBalAccount");
			postMethod.addParameter("access_token", token);
			client.executeMethod(postMethod);
			String result;
			result = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			System.out.println("福建泉州通道余额查询接口返回：：" + result);
			JSONObject json = JSONObject.parseObject(result);
			if (null != json && json.containsKey("cur_balance")) {
				System.out.println("中琛源查询接口返回：："
						+ json.getString("cur_balance"));
				return json.getString("cur_balance");
			}
			logger.info("=====结束查询福建泉州余额=====");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 趣讯
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String queryQuXunBalance() {
		String findUrl = "http://data.e7chong.com/data/balance";
		String partner_no = "700349";
		String request_no = "100000123456";
		String contract_id = "100001";
		JSONObject json = new JSONObject();
		json.put("partner_no", partner_no);
		json.put("request_no", request_no);
		json.put("contract_id", contract_id);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(findUrl);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		System.out.println("-------------------");
		System.out.println(json.toString());
		System.out.println("-------------------");
		postMethod.setRequestBody(json.toString());
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			System.out.println("statusCode " + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Method failed: "
						+ postMethod.getStatusLine());
			}
			// 读取返回内容
			byte[] responseBody = postMethod.getResponseBody();
			System.out.println(new String(responseBody));
			JSONObject json1 = (JSONObject) JSON
					.parse(new String(responseBody));
			System.out.println(json1);
			return json1.getString("balance");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		queryYouBiGeBalance();
	}

	/**
	 * 向上流量
	 * 
	 * @return
	 */
	public static String queryXiangShangBalance() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String macid = "kdll";
		String time = XiangShangUtil.getTime();
		paramMap.put("macid", macid);
		paramMap.put("time", time);
		String httpPath = "http://port.365xs.cn/shop/buyunit/balance.do";
		logger.info("httpPath============" + httpPath);
		// 加密原文
		String waitSignStr = XiangShangUtil.sortKeyValue(paramMap);
		logger.info("param:" + paramMap);
		logger.info("---------------------------私钥加密——公钥解密-----------------------------");
		logger.info("要加密的字符串:\r" + waitSignStr);
		byte[] data = null;
		try {
			data = waitSignStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String privateKey = "";
		try {
			privateKey = XiangShangUtil.getAnyPemPrivateKey("privateKeyPath");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("privateKey:" + privateKey);

		// System.out.println("---------------------------私钥签名——公钥验证签名---------------------------");
		String sign = "";
		try {
			sign = RSAUtil.signPem(data, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		} // 签名
		logger.info("私钥签名后的字符串:\r" + sign);
		paramMap.put("sign", sign);
		logger.info("paramMap::::" + paramMap);
		String st = XiangShangUtil.getUrlByParamMap(paramMap, httpPath);
		logger.info(st);
		String response = XiangShangUtil.httpRequest(st);
		logger.info("response::::" + response);

		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(response));
			Element root = document.getRootElement();
			String balance = root.elementText("balance");
			logger.info("balance:" + balance);
			return balance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 向上话费
	 */
	public static String queryXiangShangHFBalance() {
		String url = "http://port.365xs.cn/shop/buyunit/balance.do";

		String macid = "kedang"; // 代理商编号，由系统配置
		String sign = ""; // Rsa签名结果,采用Base64编码，ＨＴＴＰ发送时，需要进行URL编码
		String time = XiangShangUtil.getTime(); // 时间标签，1970-1-1到当前时间的秒数，该时间标签与本系统的时间相差不能超过5分钟

		// 封装参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("macid", macid);
		paramMap.put("time", time);

		System.out.println("查询的地址======" + url);

		// 加密原文
		String waitSignStr = XiangShangUtil.sortKeyValue(paramMap);
		System.out.println("param:" + paramMap);
		System.out
				.println("---------------------------私钥加密——公钥解密-----------------------------");

		System.out.println("要加密的字符串:\r" + waitSignStr);

		try {
			byte[] data = null;
			data = waitSignStr.getBytes("UTF-8");
			String privateKey = "";
			privateKey = XiangShangUtil.getAnyPemPrivateKey("privateKeyPath");
			System.out.println("privateKey:" + privateKey);
			// System.out.println("---------------------------私钥签名——公钥验证签名---------------------------");

			sign = RSAUtil.signPem(data, privateKey);
			// 签名
			System.out.println("私钥签名后的字符串:\r" + sign);
			paramMap.put("sign", sign);

			String endUrl = XiangShangUtil.getUrlByParamMap(paramMap, url);

			System.out.println("=======查询余额的参数：" + endUrl);

			String response = XiangShangUtil.httpRequest(endUrl);

			System.out.println("返回的结果：" + response);

			SAXReader reader = new SAXReader();
			Document document = reader.read(new StringReader(response));
			Element root = document.getRootElement();
			String balance = root.elementText("balance");
			logger.info("balance:" + balance);
			return balance;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 杰拓
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String queryJieTuoBalance() {
		String findUrl = "http://120.27.96.73/trms/do/intf/agent/balance";
		JSONObject json = new JSONObject();
		String contract_id = "C100601";
		String key = "oPGlgdEOqmnPMCpW";
		String vi = "3314006301267304";
		String partner_no = "A1006";
		json.put("contract_id", contract_id);
		try {
			String code = encrypt(json.toString(), key, vi);
			logger.info(code);
			json.clear();
			json.put("partner_no", partner_no);
			json.put("code", code);
			System.out.println(json.toString());
			HttpClient httpClient = new HttpClient();
			PostMethod postMethod = new PostMethod(findUrl);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			logger.info("-------------------");
			logger.info(json.toString());
			logger.info("-------------------");
			postMethod.setRequestBody(json.toString());
			int statusCode = httpClient.executeMethod(postMethod);
			logger.info("statusCode " + statusCode);
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + postMethod.getStatusLine());

			}
			// 读取返回内容
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			System.out.println("查询到杰拓余额：" + stringBuffer.toString());
			JSONObject js = JSONObject.parseObject(stringBuffer.toString());
			String balance = js.getString("balance");
			System.out.println(balance);
			return balance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 易充宝
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public static String queryYiChongBaoBalance() {

		String querybalanceurl = "http://120.27.134.79:26600/dataChargeAction/searchDataAccountRm.do";

		String dealerid = "kedang_170120"; // 代理商id
		String sign = ""; // 签名
		String key = "iu2nfxCG8Forupm09IwGifkBBO7AnS6x"; // 密钥

		StringBuffer str = new StringBuffer().append("dealerid=")
				.append(dealerid).append(key);

		try {

			sign = MD5Util.encrypt(str + "");

			System.out.println(sign);

			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			PostMethod postMethod = new PostMethod(querybalanceurl);

			postMethod.addParameter("dealerid", dealerid);
			postMethod.addParameter("sign", sign);
			Map resultMap = null;

			client.executeMethod(postMethod);

			System.out.println("*****查询余额返回的结果****"
					+ postMethod.getResponseBodyAsString());

			Map<String, String> map = new HashMap<String, String>();
			try {
				map = getNodeData(postMethod.getResponseBodyAsString());
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			System.out.println("******查询余额返回的结果map*****" + map.get("balance"));

			return map.get("balance");

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 炫彩游戏基地
	 * 
	 * @return
	 */
	public static String queryYouXuJiDiBalance() {
		String balanceQueryurl = "http://open.play.cn/api/v2/egame/flow/package/balance.json";
		String channel_code = "700817"; // 渠道号(流量分销商提供)
		String key = "sqh9wshbqvvq";

		String sign = MD5Util.encrypt(channel_code + key);

		try {
			StringBuffer param = new StringBuffer().append("?&md5=")
					.append(sign).append("&channel_code=").append(channel_code);

			HttpClient httpclient = new HttpClient();
			GetMethod getMethond = new GetMethod(balanceQueryurl + param);

			System.out
					.println("=============查询余额报文：" + balanceQueryurl + param);

			httpclient.executeMethod(getMethond);

			// 读取返回内容
			byte[] responseBody = getMethond.getResponseBody();
			System.out.println("*****查询返回的结果****" + new String(responseBody));

			String result = new String(responseBody);

			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) JSON.parse(result);

			System.out.println("******查询余额返回的结果map*****" + map.get("ext"));

			return map.get("ext") + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 网音
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String queryWangYinBalance() {
		String url = "http://api.yflow.cn/api/queryBalance.action";

		String userid = "17700001111"; // 用户名

		String t = System.currentTimeMillis() / 1000 + ""; // 时间戳
		String apikey = "IRDHz0nGalFwjFLZcEpusAfnr6Q1XRB8"; // 密钥

		String sign = MD5Util.encrypt(apikey + userid + t);

		StringBuffer sb = new StringBuffer();
		sb.append("?userid=").append(userid).append("&t=").append(t)
				.append("&sign=").append(sign);

		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			GetMethod getMethod = new GetMethod(url + sb);
			client.executeMethod(getMethod);

			// 读取返回内容
			Map<String, Object> map = (Map<String, Object>) JSONObject
					.parse(getMethod.getResponseBodyAsString());
			;

			System.out.println("******查询余额返回的结果map*****" + map.get("balance"));

			return map.get("balance") + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 蜂助手
	 * 
	 * @return
	 */
	public static String queryFengZhuShouBalance() {
		String url = "http://flow.phone580.com/fzsFlow/api/flowChannelInfo/surplusBalance";

		String channelNo = "10017210000247"; // SP编码如（00001），由蜂助手统一创建分配
		String userId = "flow_qghzkd";
		String pwd = "lsdjfowehi3kfd3@Et3";
		String userpws = com.kedang.fenxiao.util.Util.MD5(pwd).toLowerCase(); // //SP接入密码(为账户密码的MD5值，如登陆密码为111111,此时这个值为md5(“111111”)
																				// (32位小写
		StringBuffer param = new StringBuffer();
		param.append("?channelNo=").append(channelNo).append("&userid=")
				.append(userId).append("&userpws=").append(userpws);
		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			GetMethod getMethod = new GetMethod(url + param);
			client.executeMethod(getMethod);
			String result = getMethod.getResponseBodyAsString();
			System.out.println("==============返回结果：" + result);
			Map<String, String> map = new HashMap<String, String>();
			map = getNodeData(result);
			System.out.println("******查询余额返回的结果map*****"
					+ map.get("surplus_balance"));

			return map.get("surplus_balance") + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 精赢
	 */
	public static String queryJingYingBalance() {
		
		String endpoint = "http://121.199.4.218:8282/flowservice.asmx";
		String account = "kd";// 帐号
		String Key = "86cd1251-613b-49";
		
		try {
			String signaturestr = com.kedang.fenxiao.util.AESEncrypt.Encrypt(account, Key);

			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(new QName("http://tempuri.org/", "getBalance"));// WSDL里面描述的接口名称
			call.addParameter(new QName("http://tempuri.org/", "account"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);// 接口的参数
			call.addParameter(new QName("http://tempuri.org/", "signaturestr"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);// 接口的参数
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
			call.setSOAPActionURI("http://tempuri.org/getBalance");
			Object[] obj = { account, signaturestr };
			String result = (String) call.invoke(obj);
			System.out.println("===================订单查询余额返回的结果:" + result);
			// 读取返回内容
			Map<String, String> map = getNodeData(result);
			System.out.println("==================订单查询余额返回的结果map：" + map);
			
			return map.get("message") + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	/**
	 * queryNengLiangBalance 能良
	 */
	@SuppressWarnings("unchecked")
	public static String queryNengLiangBalance() {

		String authUrl = "http://112.124.69.211:10068/nlc/pt/nlc_service_auth.php";
		String device_id = "AC0016-3E0C-FFAD-6B7B49D244CB-92D40807-B58094B1A50F"; // 注册设备号由NL端分配
		String api_key = "sTT2mgVLLIMtm7eO";
		String api_id = "65411413"; // API_ID由CP端分配，目前长度8位
		String mac = "00:16:3E:0C:FF:AD"; // SP端服务器MAC 注册时所添加的。
		String dt = new SimpleDateFormat("YYYY-MM-dd").format(new Date()); // 当天日期
		String partner_no = "IW2199940504"; // 合作者编号，PT注册时自动获取或者由NL配发
		String usr = "hzkd"; // 用户名PT注册时自动获取或由NL配发
		String pwd = "C2JlGeUnQm"; // 登录口令。PT注册时自动获取或NL配发，SHA加密后传输
		String version = "4.0";
		String enc = "0"; // 输出方式
		String iv = "57456886";
		String key = "f7a90868a9273bcd"; // kv 里面的key取前面的16位

		// String key ="f7a90868a9273bcd602691076f215b1b";
		String iff = "NLA"; // 接口标识， 身份验证时固定为NLA
		Integer ts = (int) (System.currentTimeMillis() / 1000); // 时间戳
		String sign = ""; // 签名

		// 1.将参与签名的每个参数和值按照K:V的形式合成JSON字符串
		JSONObject raw_data = new JSONObject();
		raw_data.put("api_id", api_id);
		raw_data.put("dt", dt);
		raw_data.put("mac", mac.toUpperCase());

		// 2.将该字符串用指定的KEY作为密钥进行AES128 ECB模式加密
		String x_data = AES.encrypt(raw_data.toString(), api_key);

		// 3.加密后的结果用BASE64形式来表示
		String signature = x_data;

		System.out.println("=========signature====" + signature);

		String pwd1 = SHA1Util.hex_sha1(usr + "sd9r" + pwd);

		sign = MD5Util.encrypt(
				"enc=" + enc + "&iff=" + iff + "&partner_no=" + partner_no
						+ "&pwd=" + pwd1 + "&ts=" + ts + "&usr=" + usr
						+ "&secret=" + api_key).toLowerCase();

		JSONObject raw_code = new JSONObject();
		raw_code.put("iff", iff);
		raw_code.put("partner_no", partner_no);
		raw_code.put("usr", usr);
		raw_code.put("pwd", pwd1);
		raw_code.put("ts", ts);
		raw_code.put("enc", enc);
		raw_code.put("sign", sign);

		System.out.println("========raw_code=====" + raw_code);

		String x_code = BF.encrypt(raw_code.toString(), key, iv);
		String hash_code = x_code;

		System.out.println("========hash_code=====" + hash_code);

		try {
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");

			PostMethod postMethod = new PostMethod(authUrl);

			postMethod.addRequestHeader(HTTP.CONTENT_TYPE,
					"application/x-www-form-urlencoded;charset=utf-8");

			postMethod.addRequestHeader("device_id", device_id);
			postMethod.addRequestHeader("signature", signature);

			postMethod.addParameter("hash_code", hash_code);
			postMethod.addParameter("version", version);

			String result = null;
			client.executeMethod(postMethod);
			result = postMethod.getResponseBodyAsString();

			Map<String, Object> map = (Map<String, Object>) JSONObject
					.parse(result);
			System.out.println("****map*****" + map);

			Map<String, Object> map1 = (Map<String, Object>) JSONObject
					.parse(map.get("data").toString());

			System.out.println("****账户余额*****" + map1.get("account"));

			return map1.get("account") + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String queryYouBiGeBalance() {
		String url = "http://uumon.com/api/v2/egame/flow/package/balance.json";

		String channel_code = "20176123";

		try {
			HttpClient httpclient = new HttpClient();
			logger.info("查询优比格余额请求报文:" + url + "?" + channel_code);
			GetMethod getMethond = new GetMethod(url + "?channel_code=" +channel_code);
			httpclient.executeMethod(getMethond);
			// 读取返回内容
			byte[] responseBody = getMethond.getResponseBody();
			getMethond.releaseConnection();
			String result = new String(responseBody);
			System.out.println(result);
			Map<String, Object> map = (Map<String, Object>) JSON.parse(result);
			return map.get("ext") + "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密算法AES加密 base64编码的值
	 * 
	 * @param input
	 * @param key
	 * @param vi
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String input, String key, String vi)
			throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(),
					"AES"), new IvParameterSpec(vi.getBytes()));
			byte[] encrypted = cipher.doFinal(input.getBytes("utf-8"));
			// 此处使用 BASE64 做转码。
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 加密
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String getSign(String source) throws Exception {
		String chargeSign = "";
		MessageDigest digest = MessageDigest.getInstance("MD5");
		byte[] bts = digest.digest(source.getBytes("UTF-8"));
		BASE64Encoder encoder = new BASE64Encoder();
		chargeSign = encoder.encode(bts);
		return chargeSign;
	}

	public static Map<String, String> getNodeData(String xml)
			throws DocumentException {
		Map<String, String> resultMap = new HashMap<String, String>();
		Document doc;
		doc = DocumentHelper.parseText(xml);
		Element elem = doc.getRootElement();
		return getElementList(elem, resultMap);
	}

	/**
	 * 递归遍历方法
	 * 
	 * @param element
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getElementList(Element element,
			Map<String, String> resultMap) {
		List elements = element.elements();
		if (elements.size() == 0) {
			// 没有子元素
			resultMap.put(element.getName(), element.getTextTrim());
		} else {
			// 有子元素
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				// 递归遍历
				getElementList(elem, resultMap);
			}
		}
		return resultMap;
	}
}
