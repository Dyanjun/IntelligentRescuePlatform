package cn.edu.sjtu.ist.irp.util.convert;

import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.ClueDTO;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;

/**
 * @author dyanjun
 * @date 2021/12/17 2:40
 */
public class ClueConvertUtil {
    public static ClueDTO convertDomain2DTO(Clue clue, Place place, Photo photo){
        ClueDTO dto = new ClueDTO();

        dto.setId(clue.getId());
        dto.setTime(clue.getTime());
        dto.setRescue_member_id(clue.getRescue_member_id());
        dto.setCase_id(clue.getCase_id());
        dto.setDescription(clue.getDescription());
        dto.setPlace(place);
        dto.setPhoto(photo);
        dto.setSimilarity(Math.random());
        return dto;
    }

    public static Clue convertDTO2Domain(ClueDTO clueDTO){
        Clue clue = new Clue();
        clue.setId(clueDTO.getId());
        clue.setTime(clueDTO.getTime());
        clue.setRescue_member_id(clueDTO.getRescue_member_id());
        clue.setCase_id(clueDTO.getCase_id());
        clue.setDescription(clueDTO.getDescription());
        return clue;
    }
}
