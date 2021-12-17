package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.InfoDao;
import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.entity.Info;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.InfoDTO;
import cn.edu.sjtu.ist.irp.util.GeoUtil;
import cn.edu.sjtu.ist.irp.util.convert.InfoConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/17 10:44
 */
@Service
public class InfoService {

    @Autowired
    InfoDao infoDao;

    @Autowired
    PlaceDao placeDao;

    public List<InfoDTO> getInfo(String type, Place place){
        List<Info> infoList = infoDao.getInfo(type);
        List<InfoDTO> infoDTOList = new ArrayList<>();
        for(Info info: infoList){
            Place place1 = placeDao.getPlaceById(info.getPlace_id());
            Double distance = GeoUtil.getDistance(place1.getLongitude(),place1.getLatitude(),place.getLongitude(),place.getLatitude());
            InfoDTO infoDTO = InfoConvertUtil.convertDomain2DTO(info, place1, distance);
            infoDTOList.add(infoDTO);
        }
        infoDTOList.sort(Comparator.comparing(InfoDTO::getDistance));
        return infoDTOList;
    }


}
