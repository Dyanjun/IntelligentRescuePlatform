package cn.edu.sjtu.ist.irp.entity.dto;

import cn.edu.sjtu.ist.irp.entity.LostCaseStatus;
import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.Place;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/17 1:34
 */
@Data
public class LostCaseDTO {
    Integer id;

    String lost_time;

    String emergency_level;

    Integer rescue_num;

    String description;

    MissingPerson missingPerson;

    LostCaseStatus status= LostCaseStatus.AUDITING;

    Place place;
}
