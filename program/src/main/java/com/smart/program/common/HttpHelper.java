
package com.smart.program.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HttpHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpHelper.class);
    protected PoolingHttpClientConnectionManager connManager;
    protected LinkedList<BasicHeader> headers = new LinkedList<BasicHeader>();

    protected int MAX_CONNECTION_COUNT = 150;
    protected int CONNECT_TIMEOUT = 1000; // 1s
    protected int SOCKET_TIMEOUT = 3000; // 1s
    protected int CONNECTION_REQUEST_TIMEOUT = 1000; // 1s

    protected int WAIT_MILLI_SECONDS = 100; //ms
    protected int MAX_RETRY = 1;

    //实现单例模式
    private volatile static HttpHelper uniqueInstance = null;
    protected HttpHelper() {
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_CONNECTION_COUNT);
        connManager.setDefaultMaxPerRoute(MAX_CONNECTION_COUNT);

        headers.add(new BasicHeader(HttpHeaders.ACCEPT, "*/*"));
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36"));
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
    }

    protected HttpHelper(String authorization) {
        connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(MAX_CONNECTION_COUNT);
        connManager.setDefaultMaxPerRoute(MAX_CONNECTION_COUNT);

        headers.add(new BasicHeader(HttpHeaders.ACCEPT, "*/*"));
        headers.add(new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36"));
        headers.add(new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authorization));
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
    }



    public static HttpHelper getInstance() {
        if (null == uniqueInstance) {
            synchronized (HttpHelper.class) {
                if (null == uniqueInstance) {
                    uniqueInstance = new HttpHelper();
                }
            }
        }
        return uniqueInstance;
    }


    public static HttpHelper getInstance(String token) {
        return new HttpHelper(token);
    }

    private synchronized CloseableHttpClient getHttpClient() throws Exception{
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(SOCKET_TIMEOUT)
                .build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            // 信任所有
            @Override
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultHeaders(headers)
                .setSSLSocketFactory(sslsf)
                .build();
        return httpClient;
    }

    public HttpResult postFormBody(String url, JSONObject params) throws Exception {
/*        List<NameValuePair> formParams = map2FormParams(params);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");*/

        StringEntity entity = new StringEntity(params.toJSONString(),"utf-8");//解决中文乱码问题

        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(entity);
        long begin = System.currentTimeMillis();
        HttpResponse httpResponse;
        try {
            httpResponse = doHttpCall(getHttpClient(), httpPost, MAX_RETRY);
        } catch (HttpHostConnectException e) {

            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            LOGGER.debug("url: {}, params: {}, cost: {}",
                    url, params.toJSONString(), System.currentTimeMillis() - begin);

        }
        long end = System.currentTimeMillis();

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String content = EntityUtils.toString(httpResponse.getEntity());

        LOGGER.debug("url: {}, params: {}, statusCode: {}, responseBody: {}, cost: {}",
                url, params.toJSONString(), statusCode, content, end - begin);
        return new HttpResult(statusCode, content);
    }




    public HttpResult postFormBody(String url, Map<String, String> params, String contentType) throws Exception {
        List<NameValuePair> formParams = map2FormParams(params);
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", contentType);

        long begin = System.currentTimeMillis();
        HttpResponse httpResponse;
        try {
            httpResponse = doHttpCall(getHttpClient(), httpPost, MAX_RETRY);
        } catch (HttpHostConnectException e) {

            LOGGER.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        } finally {
            LOGGER.debug("url: {}, params: {}, cost: {}",
                    url, params.toString(), System.currentTimeMillis() - begin);

        }
        long end = System.currentTimeMillis();

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        String content = EntityUtils.toString(httpResponse.getEntity());

        LOGGER.debug("url: {}, params: {}, statusCode: {}, responseBody: {}, cost: {}",
                url, params.toString(), statusCode, content, end - begin);
        return new HttpResult(statusCode, content);
    }






    public HttpResult get(String url, Map<String, String> params) throws Exception {
        long begin = 0L;
        try {
            if (params != null) {
                if (params.size() > 0) {
                    url = url + "?" + convertParams(params);
                }

            }
            URI uri = new URIBuilder(url).build();
            HttpGet httpGet = new HttpGet(uri);
            begin = System.currentTimeMillis();
            HttpResponse httpResponse = doHttpCall(getHttpClient(), httpGet, MAX_RETRY);
            long end = System.currentTimeMillis();

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            String content = EntityUtils.toString(httpResponse.getEntity());

/*            LOGGER.debug("url: {}, params: {}, statusCode: {}, responseBody: {}, cost: {}",
                    url, convertParams(params), statusCode, content, end - begin);*/
            return new HttpResult(statusCode, content);
        } catch (Exception e) {
            LOGGER.debug("url: {}, params: {}, cost: {}",
                    url, convertParams(params), System.currentTimeMillis() - begin);
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    private HttpResponse doHttpCall(HttpClient httpclient, HttpUriRequest httpRequest, int maxRetry) throws Exception {
        List<Class<? extends Exception>> exceptionTypes = new ArrayList<Class<? extends Exception>>();
        exceptionTypes.add(IOException.class);
        int retryCount = 1;
        while (retryCount <= maxRetry) {
            try {
                LOGGER.debug("Current connection status: {}", connManager.getTotalStats());
                HttpResponse httpResponse = httpclient.execute(httpRequest);
                return httpResponse;
            } catch (Exception ex) {
                httpRequest.abort();
                boolean needRetry = false;
                if (retryCount < maxRetry) {
                    for (Class<? extends Exception> exceptionType : exceptionTypes) {
                        if (exceptionType.isInstance(ex)) {
                            needRetry = true;
                        }
                    }
                }
                if (needRetry) {
                    Thread.sleep(retryCount * WAIT_MILLI_SECONDS); //随着重试次数增多，sleep时间变长
                    retryCount++;
                } else {
                    throw ex;
                }
            }
        }
        return null;
    }

    public static String urlEncode(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = map2FormParams(params);
        return URLEncodedUtils.format(nameValuePairs, "UTF-8");
    }

    public static List<NameValuePair> map2FormParams(Map<String, String> params) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return formParams;
    }

    private static String convertParams(Map<String, String> params) throws IOException {
        return EntityUtils.toString(new UrlEncodedFormEntity(map2FormParams(params), "UTF-8"));
    }

}
