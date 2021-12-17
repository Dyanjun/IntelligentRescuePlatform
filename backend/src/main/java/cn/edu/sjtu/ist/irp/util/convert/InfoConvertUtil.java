package cn.edu.sjtu.ist.irp.util.convert;

import cn.edu.sjtu.ist.irp.dao.InfoDao;
import cn.edu.sjtu.ist.irp.entity.Info;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.InfoDTO;

/**
 * @author dyanjun
 * @date 2021/12/17 10:55
 */
public class InfoConvertUtil {
    public static InfoDTO convertDomain2DTO(Info clue, Place place, Double distance){
        InfoDTO dto = new InfoDTO();

        dto.setId(clue.getId());
        dto.setName(clue.getName());
        dto.setPlace(place);
        dto.setTelephone(clue.getTelephone());
        dto.setDistance(distance);

        return dto;
    }

}
