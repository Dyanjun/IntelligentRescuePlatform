package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.entity.LostCaseStatus;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.response.Result;
import com.alibaba.fastjson.JSONObject;
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

    String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/case";

//    public List<LostCase> getLostCaseById(String id){
//
//    }

    /**
     * 通过救援人员的id，获取其正在参与的案件
     * @param id
     * @return
     */
    public void getProceedingLostCaseByRescueMember(String id){
//        String url = BaseUrl + "/?case.rescue_member.id=" + id + "&case.status=" + LostCaseStatus.PROCEEDING;
        String url = BaseUrl + "/?case.status=" + LostCaseStatus.PROCEEDING;
        LinkedHashMap<String, Object> obj = DatabaseUtil.sendGetRequest(url);

        LostCase lostCase = new LostCase();
        lostCase.setDescription((String) obj.get("description"));

        System.out.print(lostCase);
    }




}
