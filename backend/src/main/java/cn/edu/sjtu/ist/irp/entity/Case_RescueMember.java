package cn.edu.sjtu.ist.irp.entity;

import lombok.Data;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * @author dyanjun
 * @date 2021/12/16 0:17
 */
@Data
public class Case_RescueMember {
    Integer id;
    Integer case_id;
    Integer rescue_member_id;
}
