package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.RescueMemberConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/15 15:46
 */
@Component
@Slf4j
public class LostCaseDao {

    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/case";

    private final String BaseRelationUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/case_rescue_member";

    private final String BaseMissingUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/missing_person";

    private final String BaseRescueUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/rescue_member";

    public Case_RescueMember createCase_RescueMember(Case_RescueMember domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseRelationUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, Case_RescueMember.class);
    }

    public LostCase getLostCaseById(Integer id){
        String url = BaseUrl + "/?case.id=" + id.toString();
        List<?> case_data = DatabaseUtil.sendGetRequest(url);
        if(case_data.size()>0){
            LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) case_data.get(0));
            return lostCase;
        }
        return null;
    }

    public List<LostCase> getAllLostCase(){
        List<LostCase> lostCaseList = new ArrayList<>();
        List<?> case_data = DatabaseUtil.sendGetRequest(BaseUrl);
        for(Object obj: case_data){
            LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) obj);
            lostCaseList.add(lostCase);
        }
        return lostCaseList;
    }

    public List<RescueMember> getAllRescueMember(Integer id){
        String allUrl =  BaseRelationUrl + "/?case_rescue_member.case_id=" + id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(allUrl);
        List<RescueMember> rescueMemberList = new ArrayList<>();
        for(Object obj : data){
            Case_RescueMember case_rescueMember = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, Case_RescueMember.class);
            String url = BaseRescueUrl + "/?rescue_member.id=" + case_rescueMember.getRescue_member_id();
            List<?> rescue_member_data = DatabaseUtil.sendGetRequest(url);
            if(rescue_member_data.size()>0){
                RescueMember rescueMember = RescueMemberConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) rescue_member_data.get(0));
                rescueMemberList.add(rescueMember);
            }
        }
        return rescueMemberList;
    }

    /**
     * 通过家属的id,获取其提交的所有案例
     * @param id 家属id
     * @return List<LostCase>
     */
    public List<LostCase> getLostCaseByFamilyMember(Integer id){
            String familyUrl = BaseMissingUrl + "/?missing_person.family_member_id="+ id.toString();
            List<?> data = DatabaseUtil.sendGetRequest(familyUrl);
            List<LostCase> lostCaseList = new ArrayList<>();
            for(Object obj: data){
                MissingPerson missingPerson = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, MissingPerson.class);
                String url = BaseUrl + "/?case.missing_person_id=" + missingPerson.getId().toString();
                List<?> case_data = DatabaseUtil.sendGetRequest(url);
                for(Object case_obj : case_data){
                    LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) case_obj);
                    lostCaseList.add(lostCase);
                }
            }
        }
        return lostCaseList;
    }

    /**
     * 通过救援人员的id，获取其参与过的所有案件
     * @param id 救援人员id
     * @return List<LostCase>
     */
    public List<LostCase> getLostCaseByRescueMember(Integer id){
        String allUrl =  BaseRelationUrl + "/?case_rescue_member.rescue_member_id=" + id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(allUrl);
        List<LostCase> lostCaseList = new ArrayList<>();
        for(Object obj : data){
            Case_RescueMember case_rescueMember = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, Case_RescueMember.class);
            String url = BaseUrl + "/?case.id=" + case_rescueMember.getCase_id();
            List<?> case_data = DatabaseUtil.sendGetRequest(url);
            if(case_data.size()>0){
                LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) case_data.get(0));
                lostCaseList.add(lostCase);
            }
        }
        return lostCaseList;
    }

    /**
     * 通过状态获取case
     * @param status 案件状态
     * @return List<LostCase>
     */
    public List<LostCase> getLostCaseByStatus(LostCaseStatus status){
        List<LostCase> lostCaseList = new ArrayList<>();
        String url = BaseUrl + "/?case.status=" + status.getStatus();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        for(Object obj : data){
            LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) obj);
            lostCaseList.add(lostCase);
        }
        return lostCaseList;
    }

    public LostCase createLostCase(LostCase lostCase){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(lostCase);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseUrl,requestParam);
        return LostCaseConvertUtil.convertPo2Domain(data);
    }

    public LostCase updateLostCase(LostCase lostCase){
        String url = BaseUrl + "/" + lostCase.getId().toString();
        Map<String, Object> requestParam = BeanMapUtilByReflect.putBeanToMap(lostCase);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPutRequest(url,requestParam);
        return LostCaseConvertUtil.convertPo2Domain(data);
    }

    public List<LostCase> getLostCase() {
        List<LostCase> lostCaseList = new ArrayList<>();
        List<?> data = DatabaseUtil.sendGetRequest(BaseUrl);
        for(Object obj : data){
            LostCase lostCase = LostCaseConvertUtil.convertPo2Domain((LinkedHashMap<String, Object>) obj);
            lostCaseList.add(lostCase);
        }
        return lostCaseList;
    }
}
