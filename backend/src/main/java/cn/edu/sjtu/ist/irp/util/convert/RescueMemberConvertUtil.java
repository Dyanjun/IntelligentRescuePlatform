package cn.edu.sjtu.ist.irp.util.convert;



import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.entity.RescueMemberStatus;

import java.util.LinkedHashMap;

/**
 * @author dyanjun
 * @date 2021/12/16 22:24
 */
public class RescueMemberConvertUtil {
    public static RescueMember convertPo2Domain(LinkedHashMap<String, Object> obj){
        RescueMember domain = new RescueMember();

        domain.setId((Integer) obj.get("id"));
        domain.setName((String) obj.get("name"));
        domain.setTelephone((String) obj.get("telephone"));
        domain.setUsername((String) obj.get("username"));
        domain.setPassword((String)obj.get("password"));
        domain.setDescription((String) obj.get("description"));
        domain.setPlace_id((Integer) obj.get("place_id"));
        domain.setStatus(RescueMemberStatus.fromString((String) obj.get("status")));

        return domain;
    }
}
