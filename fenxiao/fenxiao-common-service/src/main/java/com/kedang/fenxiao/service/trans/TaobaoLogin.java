package com.kedang.fenxiao.service.trans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * User: skyrain
 * Date: 2014-12-05
 * Time: 19:52
 */
@SuppressWarnings("deprecation")
public class TaobaoLogin {

	private static final Logger logger = Logger.getLogger(TaobaoLogin.class.getName());

	private static String loginUrl = "https://login.taobao.com/member/login.jhtml";

	private static String tbToken = null;// 淘宝领金币使用的token

	private static DefaultHttpClient httpclient = null;// HttpClient对象

	private static HttpResponse response = null;

	private String userName = "";// 用户名

	private String passWord = "";// 密码明文

	/**
	 * 构造函数
	 *
	 * @param userName
	 * @param passWord
	 */
	public TaobaoLogin(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}

	/**
	 * 登陆淘宝
	 *
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public boolean login()  {

		if (null != httpclient) {
			return true;
		}

		httpclient = new DefaultHttpClient();
		// 设定cookie策略
		HttpClientParams.setCookiePolicy(httpclient.getParams(),
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 登陆使用的表单数据
		List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
		loginParams.add(new BasicNameValuePair("TPL_username", userName));
		loginParams.add(new BasicNameValuePair("TPL_password", passWord));

		//登陆post请求
		HttpPost loginPost = new HttpPost(loginUrl);
		loginPost.addHeader("Referer", loginUrl);
		loginPost.addHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
		loginPost.addHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.2; Tablet PC 2.0)");
		loginPost.addHeader("Host", "login.taobao.com");

		try {

			loginPost.setEntity(new UrlEncodedFormEntity(loginParams,  HTTP.UTF_8));

			//获取登陆应答内容
			response = httpclient.execute(loginPost);

			if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
				String redirectUrl=getRedirectUrl();
				if(!"".equals(redirectUrl)){
					//有重定向说明成功了,获取token
					getTbToken(redirectUrl);
				}else{
					logger.info("登陆请求出错，重定向失败！");
					return false;
				}
			}else{
				logger.info("登陆请求出错，post返回状态:"+response.getStatusLine().getStatusCode());
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			loginPost.abort();
		}

		return true;
	}

	/**
	 * 获取淘宝重定向url
	 * @return
	 */
	private String getRedirectUrl(){
		String redirectUrl="";
		HttpEntity resEntity =  response.getEntity();
		try {
			String bufferPageHtml= EntityUtils.toString(resEntity, HTTP.UTF_8);
			Pattern pattern1 = Pattern.compile("window.location = \"(.*)\";");
			Matcher m1 = pattern1.matcher(bufferPageHtml);
			if (m1.find()) {
				redirectUrl=m1.group(1);
				logger.info("redirectUrl:"+redirectUrl);
			} else {
				logger.error("获取redirectUrl失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return redirectUrl;
	}


	/**
	 * 获取淘宝登陆令牌
	 * 可以使用两种方式
	 * 1.jsoup解析网页获取
	 * 2.从httpclient对象的cookie中获取
	 * @param redirectUrl
	 */
	private void getTbToken(String redirectUrl){

		HttpGet itaobaoGet =new HttpGet(redirectUrl);
		try {
			httpclient.execute(itaobaoGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			itaobaoGet.abort();
		}

		CookieStore cookiestore=httpclient.getCookieStore();
		List<Cookie> cookies=cookiestore.getCookies();
		if (cookies.isEmpty()) {
			logger.info("cookies is null!");
		} else {
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie=cookies.get(i);
				//logger.info( cookies.get(i).toString());
				if(cookie.getName().equals("_tb_token_")){
					tbToken=cookie.getValue();
					logger.info("淘宝令牌:"+tbToken);
				}
			}
		}
	}

	public static void main(String arg[]){
		TaobaoLogin taobaoLogin=new TaobaoLogin("xhs1027","1231");
		taobaoLogin.login();
	}

}
