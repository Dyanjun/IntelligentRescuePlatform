package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/15 11:22
 */
@Data
public class Photo {
    Integer id;

    String url;

    Integer lost_person_id;

    Integer clue_id;
}
