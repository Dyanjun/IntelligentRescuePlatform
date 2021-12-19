package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.*;
import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.ClueDTO;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.entity.dto.RescueMemberDTO;
import cn.edu.sjtu.ist.irp.util.WxUtil;
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

    @Autowired
    MissingPersonDao missingPersonDao;

    @Autowired
    FamilyMemberDao familyMemberDao;

    @Autowired
    LostCaseDao lostCaseDao;

    @Autowired
    LostCaseService lostCaseService;

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
        Place place;
        place = placeDao.getPlaceById(clue.getPlace_id());
        return  ClueConvertUtil.convertDomain2DTO(clue,place);
    }

    public void publishClue(Integer id) {
        LostCase lostCase = lostCaseDao.getLostCaseById(id);

        // 通知救援人员
        List<RescueMemberDTO> rescueMemberList = lostCaseService.getRescueMemberPlaceByCase(id);
        for(RescueMemberDTO rescueMemberDTO: rescueMemberList){
            String familyOpenId = WxUtil.getOpenId(rescueMemberDTO.getUsername());
            WxUtil.SendMessage("新的线索",lostCase.getId().toString(),"有新的线索",familyOpenId);
        }

        // 通知家属
        MissingPerson missingPerson = missingPersonDao.getMissingPersonByCase(lostCase.getMissing_person_id());
        FamilyMember familyMember = familyMemberDao.getFamilyMemberById(missingPerson.getFamily_member_id());
        String familyOpenId = WxUtil.getOpenId(familyMember.getUsername());
        WxUtil.SendMessage("案件线索更新",lostCase.getId().toString(),"有新的线索",familyOpenId);
    }
}
