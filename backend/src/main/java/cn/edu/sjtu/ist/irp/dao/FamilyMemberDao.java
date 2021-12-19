package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.FamilyMember;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import java.util.*;

import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/12/19 17:26
 */
@Component
public class FamilyMemberDao {
    final String  BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/family_member";

    public FamilyMember getFamilyMemberById(Integer id){
        String url = BaseUrl + "/?family_member.id=" + id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            FamilyMember familyMember = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), FamilyMember.class);
            return familyMember;
        }
        return null;
    }
}
