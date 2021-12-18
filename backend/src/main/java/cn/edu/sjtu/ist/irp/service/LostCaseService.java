package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.LostCaseDao;
import cn.edu.sjtu.ist.irp.dao.MissingPersonDao;
import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.dao.UserDao;
import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/15 15:47
 */
@Service
public class LostCaseService {

    @Autowired
    LostCaseDao lostCaseDao;

    @Autowired
    PlaceDao placeDao;

    @Autowired
    MissingPersonDao missingPersonDao;

    public List<LostCaseDTO> getLostCaseByFamilyMember(Integer id){
        List<LostCase> lostCaseList = lostCaseDao.getLostCaseByFamilyMember(id);
        System.out.println(lostCaseList);
        return moreList(lostCaseList);
    }

    public LostCaseDTO getProceedingLostCaseByRescueMember(Integer id){
        List<LostCase> lostCaseList = lostCaseDao.getLostCaseByRescueMember(id);
        if(lostCaseList.size()>0){
            for(LostCase c: lostCaseList){
                if(c.getStatus() == LostCaseStatus.PROCEEDING){
                    return more(c);
                }
            }
        }
        return null;
    }

    public List<LostCaseDTO> getLostCaseByRescueMember(Integer id){
        return moreList(lostCaseDao.getLostCaseByRescueMember(id));
    }

    public List<LostCaseDTO> getLostCaseByStatus(LostCaseStatus status){
        return moreList(lostCaseDao.getLostCaseByStatus(status));
    }

    public LostCaseDTO createLostCase(LostCaseDTO lostCaseDTO){

        Place place = placeDao.createPlace(lostCaseDTO.getPlace());

        LostCase lostCase= LostCaseConvertUtil.convertDTO2Domain(lostCaseDTO, place.getId());

        lostCase.setStatus(LostCaseStatus.AUDITING);
        LostCase lostCase1 = lostCaseDao.createLostCase(lostCase);

        // TODO 推送给审核

        return more(lostCase1);
    }

    public LostCaseDTO publishLostCase(Integer id){
        LostCase lostCase = lostCaseDao.getLostCaseById(id);
        if(lostCase.getStatus() == LostCaseStatus.AUDITING){
            lostCase.setStatus(LostCaseStatus.PROCEEDING);
            LostCase lostCase1 = lostCaseDao.updateLostCase(lostCase);

            // TODO 派单
            // TODO 通知家属发布成功

            return more(lostCase1);
        }
        else{
            throw new RuntimeException("案件未处于待审核状态");
        }
    }

    private List<LostCaseDTO> moreList(List<LostCase> lostCaseList){
        List<LostCaseDTO> lostCaseDTOList = new ArrayList<>();
        for(LostCase lostCase: lostCaseList){
            Place place = placeDao.getPlaceById(lostCase.getLost_place_id());
            MissingPerson missingPerson = missingPersonDao.getMissingPersonById(lostCase.getMissing_person_id());
            lostCaseDTOList.add(LostCaseConvertUtil.convertDomain2DTO(lostCase,missingPerson,place));
        }
        return lostCaseDTOList;
    }

    private LostCaseDTO more(LostCase lostCase){
        Place place = placeDao.getPlaceById(lostCase.getLost_place_id());
        MissingPerson missingPerson = missingPersonDao.getMissingPersonById(lostCase.getMissing_person_id());
        return LostCaseConvertUtil.convertDomain2DTO(lostCase,missingPerson,place);
    }
}
