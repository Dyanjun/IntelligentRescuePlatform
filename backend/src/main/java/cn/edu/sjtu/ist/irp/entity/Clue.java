package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import java.util.List;
/**
 * @author dyanjun
 * @date 2021/12/15 11:20
 */
@Data
public class Clue {
    Integer id;

    String time;

    String description;

    Integer rescue_member_id;

    Integer case_id;

    Integer place_id;
}
