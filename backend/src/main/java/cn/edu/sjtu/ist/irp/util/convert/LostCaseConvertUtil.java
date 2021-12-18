package cn.edu.sjtu.ist.irp.util.convert;

import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.entity.LostCaseStatus;
import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;

import java.util.LinkedHashMap;

/**
 * @author dyanjun
 * @date 2021/12/16 10:24
 */
public class LostCaseConvertUtil {

    public static LostCase convertPo2Domain(LinkedHashMap<String, Object> obj){
        LostCase lostCase = new LostCase();

        lostCase.setId((Integer) obj.get("id"));
        lostCase.setLost_time((String) obj.get("lost_time"));
        lostCase.setEmergency_level((String) obj.get("emergency_level"));
        lostCase.setRescue_num((Integer)obj.get("rescue_num"));
        lostCase.setDescription((String) obj.get("description"));
        lostCase.setMissing_person_id((Integer) obj.get("missing_person_id"));
        lostCase.setStatus(LostCaseStatus.fromString((String) obj.get("status")));
        lostCase.setLost_place_id((Integer) obj.get("lost_place_id"));
        return lostCase;
    }
    Integer id;

    String lost_time;

    String emergency_level;

    Integer rescue_num;

    String description;

    MissingPersonDTO missingPerson;

    LostCaseStatus status= LostCaseStatus.AUDITING;

    Place place;
    public static LostCaseDTO convertDomain2DTO(LostCase lostCase, MissingPerson missingPerson, Place place){
        LostCaseDTO lostCaseDTO = new LostCaseDTO();
        lostCaseDTO.setId(lostCase.getId());
        lostCaseDTO.setLost_time(lostCase.getLost_time());
        lostCaseDTO.setEmergency_level(lostCase.getEmergency_level());
        lostCaseDTO.setRescue_num(lostCase.getRescue_num());
        lostCaseDTO.setDescription(lostCase.getDescription());
        lostCaseDTO.setMissingPerson(missingPerson);
        lostCaseDTO.setStatus(lostCase.getStatus());
        lostCaseDTO.setPlace(place);

        return lostCaseDTO;
    }

    public static LostCase convertDTO2Domain(LostCaseDTO lostCaseDTO, Integer placeId){
        LostCase lostCase = new LostCase();
        lostCase.setLost_time(lostCaseDTO.getLost_time());
        lostCase.setEmergency_level(lostCaseDTO.getEmergency_level());
        lostCase.setRescue_num(lostCaseDTO.getRescue_num());
        lostCase.setDescription(lostCaseDTO.getDescription());
        lostCase.setMissing_person_id(lostCaseDTO.getMissingPerson().getId());
        lostCase.setStatus(lostCaseDTO.getStatus());
        lostCase.setLost_place_id(placeId);
        return lostCase;
    }
}
