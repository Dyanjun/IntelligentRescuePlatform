package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.entity.RescueMemberStatus;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.RescueMemberConvertUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/19 17:36
 */
@Component
public class RescueMemberDao {
    final String  BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/rescue_member";

    public List<RescueMember> getRescueMemberByStatus(RescueMemberStatus status){
        String url = BaseUrl + "/?rescue_member.status="+ status.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        List<RescueMember> rescueMemberList = new ArrayList<>();
        for(Object obj : data){
            RescueMember rescueMember = RescueMemberConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) obj);
            rescueMemberList.add(rescueMember);
        }
        return rescueMemberList;
    }

    public RescueMember updateStatus(Integer id, RescueMemberStatus status){
        String url = BaseUrl + "/?rescue_member.id="+ id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            RescueMember rescueMember = RescueMemberConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) data.get(0));
            rescueMember.setStatus(status);
            String url1 = BaseUrl + '/' + id;
            Map<String, Object> requestParam = BeanMapUtilByReflect.putBeanToMap(rescueMember);
            LinkedHashMap<String, Object> data1 = DatabaseUtil.sendPutRequest(url1,requestParam);
            return RescueMemberConvertUtil.convertPo2Domain(data1);
        }
       return  null;
    }
}
