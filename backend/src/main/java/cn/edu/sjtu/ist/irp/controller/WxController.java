package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.config.BaseConfiguration;
import cn.edu.sjtu.ist.irp.entity.wx.MyTemplate;
import cn.edu.sjtu.ist.irp.entity.wx.DataValue;
import cn.edu.sjtu.ist.irp.util.HttpURLConnectionUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 20:24
 */
@RestController
@RequestMapping("/wx")
@Slf4j
public class WxController {

    String openid = "odd_e5fKXXp5jpWHi5n-aj_-T1Rg";

    @GetMapping("/code")
    public void getCode(@RequestParam(value="code") String code){
        //根据微信用户 code 获取 openID
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + BaseConfiguration.WX_APPID + "&secret=" + BaseConfiguration.WX_APPSECRET + "&js_code=" + code + "&grant_type=authorization_code";
        Map<String, Object> result = new HashMap<>();

        String response = HttpURLConnectionUtil.sendGet(requestUrl);
        JSONObject OpenidJSONO = JSONObject.fromObject(response);

        //OpenidJSONO可以得到的内容：access_token expires_in  refresh_token openid scope
        String openid = String.valueOf(OpenidJSONO.get("openid"));
        String session_key = String.valueOf(OpenidJSONO.get("session_key"));

        result.put("openid", openid);
        result.put("session_key", session_key);

        this.openid = openid;

        System.out.println(response);
        System.out.println(openid);
        System.out.println(result);
    }

    @GetMapping("")
    public String SendMessage() {
        String accessToken = WxController.getAccessToken();

        MyTemplate t = new MyTemplate();
        t.setTouser("odd_e5fKXXp5jpWHi5n-aj_-T1Rg");//根据上面我写的方法自行获取
        t.setTemplate_id("R1S1v3UnJasjrAQCzlLmexLRAAX8sWtvO_fJmYDx-qQ");//订阅消息模板id
        t.setPage(null);
        Map<String, DataValue> m = new HashMap<>();
        m.put("thing2", new DataValue("test"));
        m.put("character_string3", new DataValue("tstst"));
        m.put("phrase1", new DataValue("的我把"));
        t.setData(m);
        //请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(requestUrl, t, String.class);
        return responseEntity.getBody();

    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static String getAccessToken() {
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + BaseConfiguration.WX_APPID +
                "&secret=" + BaseConfiguration.WX_APPSECRET;
        String result = HttpURLConnectionUtil.sendGet(url);
        JSONObject object = JSONObject.fromObject(result);
        System.out.print(object);
        String accessToken = object.getString("access_token");
        if (accessToken != null) {
            log.info("获取 access_token 成功, Send Success");
            return accessToken;
        } else {
            log.info("获取 access_token 失败：");
        }
        return null;
    }

}
