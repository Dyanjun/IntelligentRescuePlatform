package cn.edu.sjtu.ist.irp.entity.dto;

import cn.edu.sjtu.ist.irp.entity.Place;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/17 10:35
 */
@Data
public class InfoDTO {
    Integer id;

    String name;

    Place place;

    String telephone;

    Double distance;

}
