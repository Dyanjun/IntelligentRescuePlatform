package cn.edu.sjtu.ist.irp.entity.dto;

import cn.edu.sjtu.ist.irp.entity.Photo;
import cn.edu.sjtu.ist.irp.entity.Place;
import lombok.Data;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 1:34
 */
@Data
public class MissingPersonDTO {
    Integer id;
    String name;
    String gender;
    Integer age;
    String dressing;
    String accent;
    String description;
    Integer family_member_id;
    List<Place> places;
    List<Photo> photos;
}
