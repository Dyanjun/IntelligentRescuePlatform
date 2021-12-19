package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.FamilyMember;
import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.RescueMemberConvertUtil;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/16 22:16
 */
@Service
public class UserDao {

    private final String FamilyMemberBaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/family_member";

    private final String RescueMemberBaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/rescue_member";

    public FamilyMember getFamilyMemberById(Integer id){
        String url = FamilyMemberBaseUrl + "/?family_member.id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            FamilyMember familyMember = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), FamilyMember.class);
            return familyMember;
        }
        return null;
    }

    public RescueMember getRescueMemberById(Integer id){
        String url = RescueMemberBaseUrl + "/?rescue_member.id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            RescueMember rescueMember = RescueMemberConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) data.get(0));
            return rescueMember;
        }
        return null;
    }

    public FamilyMember getFamilyMemberByUsername(String username){
        String url = FamilyMemberBaseUrl + "/?family_member.username="+username;
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            FamilyMember familyMember = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), FamilyMember.class);
            return familyMember;
        }
        return null;
    }

    public RescueMember getRescueMemberByUsername(String username){
        String url = RescueMemberBaseUrl + "/?rescue_member.username="+username;
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            RescueMember rescueMember = RescueMemberConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) data.get(0));
            return rescueMember;
        }
        return null;
    }

    public FamilyMember createFamilyMember(FamilyMember domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(FamilyMemberBaseUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, FamilyMember.class);
    }

    public RescueMember createRescueMember(RescueMember domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(RescueMemberBaseUrl,requestParam);
        return RescueMemberConvertUtil.convertPo2Domain(data);
    }

    public RescueMember putRescueMember(RescueMember domain) {
        Map<String, Object> requestParam = BeanMapUtilByReflect.putBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPutRequest(RescueMemberBaseUrl + "/" + domain.getId().toString(),requestParam);
        return RescueMemberConvertUtil.convertPo2Domain(data);
    }
}
