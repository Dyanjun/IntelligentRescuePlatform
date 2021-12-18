package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.RescueMemberConvertUtil;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 0:20
 */
@Component
public class MissingPersonDao {
    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/missing_person";

    private final String BaseCaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/case";

    public MissingPerson createMissingPerson(MissingPerson domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, MissingPerson.class);
    }

    public MissingPerson getMissingPersonById(Integer id){
        String url = BaseUrl + "/?missing_person.id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            MissingPerson missingPerson = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), MissingPerson.class);
            return missingPerson;
        }
        return null;
    }

    public List<MissingPerson> getMissingPersonByFamilyMember(Integer id){
        String url = BaseUrl + "/?missing_person.family_member_id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        System.out.println(data);
        List<MissingPerson> missingPersonList = new ArrayList<>();
        for(Object obj: data){
            MissingPerson missingPerson = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, MissingPerson.class);
            missingPersonList.add(missingPerson);
        }
        System.out.println(missingPersonList);
        return missingPersonList;
    }

    public MissingPerson getMissingPersonByCase(Integer id){
        String case_url = BaseCaseUrl + "/?case.id="+id.toString();
        List<?> case_data = DatabaseUtil.sendGetRequest(case_url);
        if(case_data.size()>0){
            LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) case_data.get(0));
            String url = BaseUrl + "/?missing_person.id="+lostCase.getId().toString();
            List<?> data = DatabaseUtil.sendGetRequest(url);
            if(data.size()>0){
                MissingPerson missingPerson = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), MissingPerson.class);
                return missingPerson;
            }
        }
        return null;
    }
}
