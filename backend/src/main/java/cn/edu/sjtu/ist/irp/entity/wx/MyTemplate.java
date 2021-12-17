package cn.edu.sjtu.ist.irp.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/17 20:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyTemplate {

    //用户 openid
    private String touser;
    //模板id
    private String template_id;
    //点击模板跳转的页面
    private String page = "";
    //模板参数
    private Map<String, DataValue> data;
}
