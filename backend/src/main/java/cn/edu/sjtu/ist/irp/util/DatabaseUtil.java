package cn.edu.sjtu.ist.irp.util;

import cn.edu.sjtu.ist.irp.util.response.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/15 12:04
 */

public class DatabaseUtil {


    public static LinkedHashMap<String,Object> sendGetRequest(String url){
        RestTemplate client = new RestTemplate();
        //执行HTTP请求
        ResponseEntity<Result> response  = client.getForEntity(url, Result.class);
        Result result = response.getBody();
        assert result != null;
        List<?> data = (List<?>) result.getData();
        if(data.size() > 0){
            return (LinkedHashMap<String,Object>) data.get(0);
        }else{
            throw new RuntimeException("数据为空");
        }
    }

    public static String sendPostRequest(String url, String postContent) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;

        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(postContent, headers);

        //执行HTTP请求
        ResponseEntity<String> postForEntity = client.postForEntity(url, requestEntity, String.class);

        return postForEntity.getBody();
    }

    public static void sendPutRequest(String url, String postContent) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PUT;

        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(postContent, headers);
        //执行HTTP请求
        client.put(url, requestEntity, String.class);
    }

    public static void sendDeleteRequest(String url) {
        RestTemplate client = new RestTemplate();
        //执行HTTP请求
        client.delete(url, String.class);
    }
}

