package cn.edu.sjtu.ist.irp.entity.dto;

import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.RescueMemberStatus;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/19 16:48
 */
@Data
public class RescueMemberDTO {
    Integer id;
    String name;
    RescueMemberStatus status = RescueMemberStatus.FREE;
    String description;
    Place place;
    String username;
    String password;
    String telephone;
    Double distance;
}
