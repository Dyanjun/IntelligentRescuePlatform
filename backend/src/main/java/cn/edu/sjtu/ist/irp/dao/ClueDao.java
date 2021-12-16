package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.Clue;
import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 0:12
 */
@Component
public class ClueDao {
    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/clue";

    public List<Clue> getClueByCase(Integer id){
        String url = BaseUrl + "/?clue.case_id=" + id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        List<Clue> clueList = new ArrayList<>();
        for(Object obj : data){
            Clue clue = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, Clue.class);
            clueList.add(clue);
        }
        return clueList;
    }

    public Clue createClue(Clue domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, Clue.class);
    }
}
