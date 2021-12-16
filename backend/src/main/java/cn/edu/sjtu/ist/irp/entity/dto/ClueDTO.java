package cn.edu.sjtu.ist.irp.entity.dto;

import cn.edu.sjtu.ist.irp.entity.Place;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/17 1:33
 */
@Data
public class ClueDTO {
    Integer id;

    String time;

    String description;

    Integer rescue_member_id;

    Integer case_id;

    Place place;
}
