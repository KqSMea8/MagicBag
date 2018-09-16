package org.github.helixcs.java;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author: helix
 * @Time:9/16/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class HttpApi {
    // use httpcomponents
    private static  void httpRequest() throws IOException {
        CloseableHttpClient httpClient;
        HttpPost httpPost = new HttpPost("https://www.baidu.com");
        httpClient = HttpClients.createDefault();
//        httpClient = HttpClients.custom().setDefaultRequestConfig(new RequestConfig())
        String responseContent = EntityUtils.toString(httpClient.execute(httpPost).getEntity(),"UTF-8");
        System.out.println(responseContent);
    }
    // use apache
    private static void apacheHttpRequest() throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("https://www.baidu.com");
        httpClient.executeMethod(getMethod);
        String responseContent = getMethod.getResponseBodyAsString();
        System.out.println(responseContent);

    }

    public static void main(String[] args) throws IOException {
        httpRequest();
        apacheHttpRequest();
    }
}
