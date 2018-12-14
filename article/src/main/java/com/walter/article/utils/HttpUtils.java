package com.walter.article.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: java类作用描述
 * @author: suwx
 * @CreateDate: 2018/9/20 12:53
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class HttpUtils {
    public static String getDataWithProxy(String targetUrl, Map<String, String> headerMap) {
        return setHeaderMap(targetUrl, headerMap).execute().body();
    }

    public static String postData(String api, String body, Map<String, String> headMap) {
        return setHeaderWithBody(api, headMap, body).execute().body();
    }

    //get请求携带请求头
    private static HttpRequest setHeaderMap(String url, Map<String, String> headerMap) {
        HttpRequest request = HttpRequest.get(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        return request;
    }

    /**
     * post请求携带请求头信息，请求体信息的HttpRequest方式的
     *
     * @param url       目标地址
     * @param headerMap 请求头map
     * @param body      请求体
     * @return 封装好的请求信息
     * @author wangzheng6@ifeng.com
     * @date 2018年12月4日17:52:53
     */
    private static HttpRequest setHeaderWithBody(String url, Map<String, String> headerMap, String body) {
        HttpRequest request = HttpRequest.post(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }
        request.body(body);
        return request;
    }

    /**
     * 使用unirest进行携带请求头，请求体的post请求
     *
     * @param url       请求url
     * @param body      请求体
     * @param headerMap 请求头，为map类型
     * @return
     */
    public static String postDataByUnirest(String url, String body, Map<String, String> headerMap) {
        HttpRequestWithBody post = Unirest.post(url);
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.header(entry.getKey(), entry.getValue());
            }
        }
        post.body(body);
        try {
            return post.asString().getBody();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSon格式数据转换为map
     *
     * @param data String类型的json串
     * @return map
     * @date 2018年11月23日14:56:59
     * @author wangzheng6@ifeng.com
     */
    public static Map convertToMap(String data) {
        JSONObject object = JSONObject.parseObject(data);
        object.keySet();
        Set set = object.keySet();
        Iterator it = set.iterator();
        Map map = new HashMap();
        while (it.hasNext()) {
            String key = (String) it.next();
            String value = object.getString(key);
            map.put(key, value);
        }
        return map;
    }
}
