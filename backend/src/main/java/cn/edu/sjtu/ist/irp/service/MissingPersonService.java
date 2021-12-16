package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.MissingPersonDao;
import cn.edu.sjtu.ist.irp.dao.PhotoDao;
import cn.edu.sjtu.ist.irp.dao.PhotoFileDao;
import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.MissingPersonConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 0:19
 */
@Service
public class MissingPersonService {

    @Autowired
    MissingPersonDao missingPersonDao;

    @Autowired
    PhotoDao photoDao;

    @Autowired
    PhotoFileDao photoFileDao;

    @Autowired
    PlaceDao placeDao;

    public MissingPerson createMissingPerson(MissingPersonDTO dto, MultipartFile[] files){
        MissingPerson missingPerson1 = missingPersonDao.createMissingPerson(MissingPersonConvertUtil.convertDTO2Domain(dto));
        List<Place> placeList = new ArrayList<>();
        for(Place place: dto.getPlaces()){
            Place place1 = placeDao.createPlace(place);
            placeList.add(place1);
        }
        for(MultipartFile file: files){
            PhotoFile photoFile = new PhotoFile();
            photoFile.setFile(file);
            PhotoFile photoFile1 = photoFileDao.createPhoto(photoFile);
            Photo photo = new Photo();
            photo.setLost_person_id(missingPerson1.getId());
            photo.setUrl(photoFile1.getUrl());
            photoDao.createPhoto(photo);
        }
        return missingPerson1;
    }

    public MissingPerson getMissingPersonById(Integer id){
        return missingPersonDao.getMissingPersonById(id);
    }

    public MissingPerson getMissingPersonByCase(Integer id){
        return missingPersonDao.getMissingPersonByCase(id);
    }
}
