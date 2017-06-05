package com.kedang.fenxiao.service.jiangsukami;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author wangbing
 * @create 2013-9-24
 */
@SuppressWarnings("deprecation")
public class HttpClientHelper {
	private static final int connectionTimeOut = 60*1000;

	private static final int soTimeout = 60*1000;

	private static Logger logger = Logger.getLogger(HttpClientHelper.class);

	@SuppressWarnings("resource")
	public static String post2(String actionUrl, Map<String, String> params) {
		BasicHttpParams bp = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(bp, connectionTimeOut); // 超时时间设置
		HttpConnectionParams.setSoTimeout(bp, soTimeout);
		HttpClient httpclient = new DefaultHttpClient(bp);
		HttpPost httpPost = new HttpPost(actionUrl);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 构建表单字段内容
			String key = entry.getKey();
			String value = entry.getValue();
			list.add(new BasicNameValuePair(key, value));
		}
		HttpResponse httpResponse;
		String responseString = "";
		logger.warn("传入后台的参数：" + list);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			httpResponse = httpclient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				responseString = EntityUtils.toString(httpResponse.getEntity());
				System.out.println("responseString:"+responseString);
				return responseString;
			} else if (httpResponse.getStatusLine().getStatusCode() == 404) {
				logger.warn("actionUrl:{} not found 404!" + actionUrl);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}

	public static void main(String[] args) {
		String request;
		Map<String, String> params = new HashMap<String, String>();
		try {
			//18362774899
			//"TSR_RESULT":"999",
			//null({"TSR_RESULT":"999","TSR_CODE":"999","TSR_MSG":"对不起系统繁忙，请稍后再试！","result":"999"})
			//null({"TSR_RESULT":"100005","TSR_MSG":"抱歉，本活动仅限电信天翼手机用户参与，感谢您的关注。"})

			request = SimpleDesAndroid
					.encrypt(
							"accNbr=15212605398;ticketNum=3L5VK2RM3JM;ztInterSource=201002_tzcd;staffValue=13338660192;pubAreaCode=025;pushUserId=jszt_123456",
							DataDirection.TO_SERVER);
			params.put("para", request);System.out.println(request);
			String rs = post2(
					"http://202.102.111.142/jszt/llzq/useElectronTicketBatch",
					params);
			//	String rs = "null({\"TSR_RESULT\":\"100005\",\"TSR_MSG\":\"抱歉，本活动仅限电信天翼手机用户参与，感谢您的关注。\"})";
		//	String rs = "null({\"TSR_RESULT\":\"999\",\"TSR_CODE\":\"999\",\"TSR_MSG\":\"对不起系统繁忙，请稍后再试！\",\"result\":\"999\"})";
			rs = rs.replace("null(", "").replace(")", "");
			System.out.println(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
