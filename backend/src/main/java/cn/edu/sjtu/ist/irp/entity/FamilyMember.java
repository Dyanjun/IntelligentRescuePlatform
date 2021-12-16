package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/15 11:32
 */
@Data
public class FamilyMember {
    Integer id;
    String name;
    String telephone;
    String relationship;
    String description;
    Integer place_id;
    String username;
    String password;
}
