package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/15 11:18
 */
@Data
public class Place {
    Integer id;
    Double longitude;
    Double latitude;
    String address;
    String description;
    Integer lost_person_id;
}
