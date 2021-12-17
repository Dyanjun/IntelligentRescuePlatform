package cn.edu.sjtu.ist.irp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author dyanjun
 * @date 2021/12/17 20:22
 */
@Configuration
public class BaseConfiguration {
    //微信小程序
    public static String WX_APPID;
    public static String WX_APPSECRET;
    public static String WX_TEMPLATE_ID;

    @Value("${wx.appId}")
    public void setWxAppid(String param){
        WX_APPID = param;
    }

    @Value("${wx.appSecret}")
    public void setWxAppsecret(String param){
        WX_APPSECRET = param;
    }

    @Value("${wx.templateId}")
    public void setWxTemplateId(String param){
        WX_TEMPLATE_ID = param;
    }
}
