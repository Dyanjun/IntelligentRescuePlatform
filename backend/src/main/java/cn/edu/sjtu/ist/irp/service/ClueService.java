package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.ClueDao;
import cn.edu.sjtu.ist.irp.dao.PhotoDao;
import cn.edu.sjtu.ist.irp.dao.PhotoFileDao;
import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.ClueDTO;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.util.convert.ClueConvertUtil;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/17 0:13
 */
@Service
public class ClueService {
    @Autowired
    ClueDao clueDao;

    @Autowired
    PhotoDao photoDao;

    @Autowired
    PhotoFileDao photoFileDao;

    @Autowired
    PlaceDao placeDao;

    public List<ClueDTO> getClueByCase(Integer id){
        return moreList(clueDao.getClueByCase(id));
    }

    public ClueDTO createClue(ClueDTO clueDTO, MultipartFile[] files){
        Clue clue1 = clueDao.createClue(ClueConvertUtil.convertDTO2Domain(clueDTO));
        placeDao.createPlace(clueDTO.getPlace());
        for(MultipartFile file: files){
            PhotoFile photoFile = new PhotoFile();
            photoFile.setFile(file);
            PhotoFile photoFile1 = photoFileDao.createPhoto(photoFile);
            Photo photo = new Photo();
            photo.setClue_id(clue1.getId());
            photo.setUrl(photoFile1.getUrl());
            photoDao.createPhoto(photo);
        }
        // TODO 共享线索
        return more(clue1);
    }

    private List<ClueDTO> moreList(List<Clue> clueList){
        List<ClueDTO> clueDTOList = new ArrayList<>();
        for(Clue clue: clueList){
            Place place = placeDao.getPlaceById(clue.getPlace_id());
            clueDTOList.add(ClueConvertUtil.convertDomain2DTO(clue,place));
        }
        return clueDTOList;
    }

    private ClueDTO more(Clue clue){
        Place place = placeDao.getPlaceById(clue.getPlace_id());
        return  ClueConvertUtil.convertDomain2DTO(clue,place);
    }
}
