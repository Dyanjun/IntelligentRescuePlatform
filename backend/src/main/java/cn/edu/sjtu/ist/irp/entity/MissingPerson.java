package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import java.util.List;
/**
 * @author dyanjun
 * @date 2021/12/15 11:35
 */
@Data
public class MissingPerson {
    String id;
    String name;
    String gender;
    Integer age;
    String dressing;
    String accent;
    String description;
    String family_member_id;
}
