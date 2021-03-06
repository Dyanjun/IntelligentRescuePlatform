package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/15 11:14
 */
@Data
public class RescueMember {
    Integer id;
    String name;
    RescueMemberStatus status = RescueMemberStatus.FREE;
    String description;
    Integer place_id;
    String username;
    String password;
    String telephone;
}
