package cn.edu.sjtu.ist.irp.util.convert;

import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.Photo;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 1:43
 */
public class MissingPersonConvertUtil {
    static public MissingPerson convertDTO2Domain(MissingPersonDTO dto){
        MissingPerson missingPerson = new MissingPerson();
        missingPerson.setName(dto.getName());
        missingPerson.setGender(dto.getGender());
        missingPerson.setAge(dto.getAge());
        missingPerson.setDressing(dto.getDressing());
        missingPerson.setAccent(dto.getAccent());
        missingPerson.setDescription(dto.getDescription());
        missingPerson.setFamily_member_id(dto.getFamily_member_id());
        return missingPerson;
    }

    static public MissingPersonDTO convertDomain2DTO(MissingPerson domain, List<Place> places, List<Photo> photos){
        MissingPersonDTO missingPersonDTO = new MissingPersonDTO();
        missingPersonDTO.setName(domain.getName());
        missingPersonDTO.setGender(domain.getGender());
        missingPersonDTO.setAge(domain.getAge());
        missingPersonDTO.setDressing(domain.getDressing());
        missingPersonDTO.setAccent(domain.getAccent());
        missingPersonDTO.setDescription(domain.getDescription());
        missingPersonDTO.setFamily_member_id(domain.getFamily_member_id());
        missingPersonDTO.setPlaces(places);
        missingPersonDTO.setPhotos(photos);
        return missingPersonDTO;
    }
}
