package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import java.util.List;
/**
 * @author dyanjun
 * @date 2021/12/15 11:20
 */
@Data
public class Clue {
    String id;

    String time;

    String description;

    String rescue_member_id;

    String case_id;

    Place place;

    List<Photo> photo;
}
