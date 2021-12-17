package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.Info;
import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/17 10:25
 */
@Component
public class InfoDao {
    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/info";

    public List<Info> getInfo(String type){
        String url = BaseUrl + "/info.type=" + type;
        List<Info> list = new ArrayList<>();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        for(Object obj: data){
            Info info = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, Info.class);
            list.add(info);
        }
        return list;
    }
}
