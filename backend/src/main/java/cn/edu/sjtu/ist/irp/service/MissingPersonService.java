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

    public MissingPersonDTO createMissingPerson(MissingPersonDTO dto){
        Integer photoId = dto.getPhoto().getId();
        MissingPerson missingPerson1 = missingPersonDao.createMissingPerson(MissingPersonConvertUtil.convertDTO2Domain(dto));
        Photo photo = photoDao.getPhotoById(photoId);
        photo.setLost_person_id(missingPerson1.getId());
        return more(missingPerson1);
    }

    public MissingPerson getMissingPersonById(Integer id){
        return missingPersonDao.getMissingPersonById(id);
    }

    public MissingPersonDTO getMissingPersonByCase(Integer id){
        return more(missingPersonDao.getMissingPersonByCase(id));
    }

    public List<MissingPersonDTO> getMissingPersonByFamilyMember(Integer id) {
        return moreList(missingPersonDao.getMissingPersonByFamilyMember(id));
    }

    private List<MissingPersonDTO> moreList(List<MissingPerson> list){
        List<MissingPersonDTO> DTOList = new ArrayList<>();
        for(MissingPerson domain: list){
            List<Place> places = placeDao.getPlaceByMissingPerson(domain.getId());
            Photo photo = photoDao.getPhotoByMissingPerson(domain.getId());
            DTOList.add(MissingPersonConvertUtil.convertDomain2DTO(domain,places,photo));
        }
        System.out.println(DTOList);
        return DTOList;
    }

    private MissingPersonDTO more(MissingPerson domain){
        List<Place> places = placeDao.getPlaceByMissingPerson(domain.getId());
        Photo photo = photoDao.getPhotoByMissingPerson(domain.getId());
        return MissingPersonConvertUtil.convertDomain2DTO(domain,places,photo);
    }
}
