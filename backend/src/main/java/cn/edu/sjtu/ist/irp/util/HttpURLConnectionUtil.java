package cn.edu.sjtu.ist.irp.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/17 20:28
 */
public class HttpURLConnectionUtil {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String findByLatAndLng(String lng, String lat) {
        try {
            //移除坐标前后的 空格
            /*lng = lng.trim();
            lat = lat.trim();*/
;
            // url中的ak值要替换成自己的:
            String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=RBY6QoIZfYUGNNCOPm5QhwQtMxojd7g5&output=json&coordtype=wgs84ll&location=" + lng + "," + lat;
            //System.out.println(url);
            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response = httpclient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();

            String json = EntityUtils.toString(httpEntity);

            Map<String, Object> result = JSONObject.parseObject(json, Map.class);

            if (result.get("status").equals(0)) {
                Map<String, Object> resultMap = (Map<String, Object>) result.get("result");
                resultMap = (Map<String, Object>) resultMap.get("addressComponent");
                String country = (String) resultMap.get("country");
                String province = (String) resultMap.get("province");
                String city = (String) resultMap.get("city");
                return country + province + city;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送HttpGet请求
     * @param url
     * @return
     */
    public static String sendGet(String url) {

        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String doPost(String url , String param){
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(param));

            // 执行http请求
            response = httpClient.execute(httpPost);
            System.out.print(response);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
