package cn.edu.sjtu.ist.irp.util;

import cn.edu.sjtu.ist.irp.config.BaseConfiguration;
import cn.edu.sjtu.ist.irp.controller.WxController;
import cn.edu.sjtu.ist.irp.entity.wx.DataValue;
import cn.edu.sjtu.ist.irp.entity.wx.MyTemplate;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/15 20:09
 */
@Slf4j
public class WxUtil {

    public static Map<String,String> wxMap = new HashMap<>();

    public static void addOpenid(String username, String openId){
        wxMap.put(username,openId);
    }

    public static String getOpenId(String username){
        return wxMap.getOrDefault(username, null);
    }

    public static String SendMessage(String str1, String str2, String str3, String openId) {
        String accessToken = WxController.getAccessToken();

        MyTemplate t = new MyTemplate();
        t.setTouser(openId); //根据上面我写的方法自行获取
        t.setTemplate_id("R1S1v3UnJasjrAQCzlLmexLRAAX8sWtvO_fJmYDx-qQ"); //订阅消息模板id
        t.setPage(null);
        Map<String, DataValue> m = new HashMap<>();
        m.put("thing2", new DataValue(str1)); // 中英皆可
        m.put("character_string3", new DataValue(str2)); //只能是英文
        m.put("phrase1", new DataValue(str3)); // 只能是中文 5个字以内
        t.setData(m);
        //请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(requestUrl, t, String.class);
        System.out.print(responseEntity.getBody());
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
