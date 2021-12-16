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


    public static List<?> sendGetRequest(String url){
        RestTemplate client = new RestTemplate();
        //执行HTTP请求
        ResponseEntity<Result> response  = client.getForEntity(url, Result.class);
        Result result = response.getBody();
        assert result != null;
        if(result.getCode() == 0){
            List<?> data = (List<?>) result.getData();
            return data;
        }else{
            throw new RuntimeException(result.getMessage());
        }
    }

    public static LinkedHashMap<String, Object> sendPostRequest(String url,  Map<String, Object> requestParam) {
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(requestParam,headers);
        ResponseEntity<Result> response  = restTemplate.postForEntity(url, entity, Result.class);
        Result result = response.getBody();
        assert result != null;
        if(result.getCode() == 0){
            LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) result.getData();
            return data;
        }else{
            throw new RuntimeException(result.getMessage());
        }
    }

    public static LinkedHashMap<String, Object> sendPutRequest(String url, Map<String, Object> requestParam) {
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity<>(requestParam,headers);
        ResponseEntity<Result> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Result.class);
        Result result = response.getBody();
        assert result != null;
        if(result.getCode() == 0){
            LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) result.getData();
            return data;
        }else{
            throw new RuntimeException(result.getMessage());
        }
    }

    public static void sendDeleteRequest(String url) {
        RestTemplate client = new RestTemplate();
        //执行HTTP请求
        client.delete(url, String.class);
    }
}

