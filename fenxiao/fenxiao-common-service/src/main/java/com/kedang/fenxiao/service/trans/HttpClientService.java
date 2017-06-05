package com.kedang.fenxiao.service.trans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class HttpClientService {
    private static Logger logger = LoggerFactory.getLogger(HttpClientService.class);
    public final static boolean VALIDATE=Boolean.TRUE;
    public final static boolean INVALIDATE=Boolean.TRUE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;


    public boolean isValidate() {
        return isValidate;
    }

    public void setValidate(boolean isValidate) {
        this.isValidate = isValidate;
    }

    private boolean isValidate;

    private int readTimeout;


    public final static String TIMEOUTFLAG="TIMEOUTFLAG";

    public final static String LOGOUT_URL="http://www.taobao.com";
    public final static String LOGIN_URL="http://www.taobao.com";




    private HttpClient httpclient;
    private HttpClient httpclient2;
//	private static HttpClientService unionLoginInstance = new HttpClientService();

    public HttpClientService(int maxTotalConnections) {
        try {

            readTimeout=30000;







            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

//			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).build();
//
//			PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", sslsf).register("http", PlainConnectionSocketFactory.getSocketFactory()).build();

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//			设置最大连接数
            connectionManager.setMaxTotal(maxTotalConnections);

            // 设置 每个路由最大连接数
            connectionManager.setDefaultMaxPerRoute(maxTotalConnections+5);







            org.apache.http.client.CookieStore cookieStore = new BasicCookieStore();
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setDefaultCookieStore(cookieStore);
            httpClientBuilder.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.24 Safari/537.36");
            //设置重定向相关
            RequestConfig.Builder config= RequestConfig.custom().setCircularRedirectsAllowed(true).setMaxRedirects(100);
            config.setConnectTimeout(readTimeout);
            config.setSocketTimeout(readTimeout);
            httpClientBuilder.setDefaultRequestConfig(config.build());
//			httpClientBuilder.setConnectionManager(connectionManager);
            this.httpclient = httpClientBuilder.setSSLSocketFactory(
                    sslsf).build();


            SSLContextBuilder builder2 = new SSLContextBuilder();
            builder2.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf2 = new SSLConnectionSocketFactory(
                    builder2.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            org.apache.http.client.CookieStore cookieStore2 = new BasicCookieStore();
            HttpClientBuilder httpClientBuilder2 = HttpClients.custom();
            httpClientBuilder2.setDefaultCookieStore(cookieStore2);
//			httpClientBuilder2.setRedirectStrategy(RedirectStrategyImpl.INSTANCE);
            httpClientBuilder2.setUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.24 Safari/537.36");
            //设置重定向相关



            httpClientBuilder2.setConnectionManager(connectionManager);
            RequestConfig.Builder config2= RequestConfig.custom().setCircularRedirectsAllowed(true).setMaxRedirects(100);
            config2.setConnectTimeout(readTimeout);
            config2.setSocketTimeout(readTimeout);

            httpClientBuilder2.setDefaultRequestConfig(config2.build());
            this.httpclient2 = httpClientBuilder2.setSSLSocketFactory(
                    sslsf2).build();

            this.id= UUID.randomUUID().toString();
            this.isValidate=false;

//			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//			scheduler.scheduleAtFixedRate(new IdleConnectionMonitor(connectionManager), 2000, 5000, TimeUnit.MILLISECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//	public static HttpClientService getInstance() {
//		return unionLoginInstance;
//	}
//	public static HttpClientService getInstanceInit() {
//		unionLoginInstance = new HttpClientService();
//		return unionLoginInstance;
//	}


    public HttpClient getHttpclient() {
        return httpclient;
    }


    public String postUrl(String url, Map<String, String> params,String reffer) throws IOException {
        HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader("Origin","https://124.160.11.200:8443");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> param : params.entrySet()) {
            NameValuePair temp = new BasicNameValuePair(param.getKey(), param.getValue());
            pairs.add(temp);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));

        if(reffer!=null)
            httpPost.addHeader("Referer",reffer);

        HttpResponse response = httpclient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public String postUrlRedirect(String url, Map<String, String> params,String reffer) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> param : params.entrySet()) {
            NameValuePair temp = new BasicNameValuePair(param.getKey(), param.getValue());
            pairs.add(temp);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));

        if(reffer!=null)
            httpPost.addHeader("Referer",reffer);

        HttpResponse response = httpclient.execute(httpPost);


        StatusLine statusLine=  response.getStatusLine();

        int statuscode=statusLine.getStatusCode();
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statuscode == HttpStatus.SC_SEE_OTHER) ||
                (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

//			return null;

            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader == null) {
                return null;
            }
            String rdurl= locationHeader.getValue();

            return getUrl2(rdurl, url);
        }


        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }
    
    public String postUrl(String url, Map<String, String> params,String reffer, Cookie cookie) throws IOException {
        HttpPost httpPost = new HttpPost(url);
//      httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        httpPost.setHeader("Cookie", "JSESSIONID=7E552404DFE6F0F145431F7D349D7406;domain=account.chsi.com.cn;path=/account;expiry=null");
        httpPost.setHeader("Cookie", cookie.getName() + "=" + cookie.getValue());
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0");
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> param : params.entrySet()) {
            NameValuePair temp = new BasicNameValuePair(param.getKey(), param.getValue());
            pairs.add(temp);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));

        if(reffer!=null)
            httpPost.addHeader("Referer",reffer);

        HttpResponse response = httpclient.execute(httpPost);
        Header[] headers = response.getAllHeaders();
        for(Header s:headers){
	 	    System.out.println(s.getName() + "=" + s.getValue());
	    }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public String postUrl2(String url, Map<String, String> params,String reffer) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        for (Map.Entry<String, String> param : params.entrySet()) {
            NameValuePair temp = new BasicNameValuePair(param.getKey(), param.getValue());
            pairs.add(temp);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairs));

        if(reffer!=null)
            httpPost.addHeader("Referer",reffer);

        HttpResponse response = httpclient2.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public String getUrlRedirect(String url,String reffer) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        if(reffer!=null){
            httpGet.addHeader("Referer",reffer);
        }

        StatusLine statusLine=  response.getStatusLine();

        int statuscode=statusLine.getStatusCode();
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statuscode == HttpStatus.SC_SEE_OTHER) ||
                (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader == null) {
                return null;
            }
            String rdurl= locationHeader.getValue();
            return getUrlRedirect(rdurl, url);
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public String getUrl2(String url,String reffer) throws IOException {
        HttpGet httpGet = new HttpGet(url);


        HttpResponse response = httpclient2.execute(httpGet);
        if(reffer!=null){
            httpGet.addHeader("Referer",reffer);
        }

        StatusLine statusLine=  response.getStatusLine();

        int statuscode=statusLine.getStatusCode();
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                (statuscode == HttpStatus.SC_SEE_OTHER) ||
                (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

            Header header=	response.getFirstHeader("isTimeout");
            if(header!=null){
                return HttpClientService.TIMEOUTFLAG;
            }
        }



        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public String getUrl(String url,String reffer) throws IOException {
        HttpGet httpGet = new HttpGet(url);


        HttpResponse response = httpclient.execute(httpGet);
        if(reffer!=null){
            httpGet.addHeader("Referer",reffer);
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }

    public void downloadFile(String url,File file) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            InputStream input = entity.getContent();
            FileOutputStream output = new FileOutputStream(file);
            IOUtils.copy(input, output);
            output.close();
        }
    }


    @SuppressWarnings("unused")
	private final class IdleConnectionMonitor implements Runnable {
        PoolingHttpClientConnectionManager connectionManager;

		public IdleConnectionMonitor(PoolingHttpClientConnectionManager connectionManager) {
            this.connectionManager = connectionManager;
        }

        @Override
        public void run() {
            if (logger.isInfoEnabled()) {
                logger.info("release start connect count:=" + connectionManager.getTotalStats().getAvailable());
            }
            // Close expired connections
            connectionManager.closeExpiredConnections();
            // Optionally, close connections
            // that have been idle longer than readTimeout*2 MILLISECONDS
            connectionManager.closeIdleConnections(readTimeout * 2, TimeUnit.MILLISECONDS);

            if (logger.isInfoEnabled()) {
                logger.info("release end connect count:=" + connectionManager.getTotalStats().getAvailable());
            }

        }
    }

}
