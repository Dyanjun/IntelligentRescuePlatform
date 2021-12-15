package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import java.util.List;
/**
 * @author dyanjun
 * @date 2021/12/15 10:56
 */
@Data
public class LostCase {
    String id;

    String lost_time;

    String emergency_level;

    Integer rescue_num;

    String description;

    String missing_person_id;

    LostCaseStatus status= LostCaseStatus.AUDITING;

    Place lost_place;

    List<Clue> clue;

    List<RescueMember> rescue_member;
}
