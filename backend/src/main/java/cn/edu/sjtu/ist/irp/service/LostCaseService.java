package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.*;
import cn.edu.sjtu.ist.irp.entity.*;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.RescueMemberDTO;
import cn.edu.sjtu.ist.irp.util.GeoUtil;
import cn.edu.sjtu.ist.irp.util.WxUtil;
import cn.edu.sjtu.ist.irp.util.convert.LostCaseConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    FamilyMemberDao familyMemberDao;

    @Autowired
    RescueMemberService rescueMemberService;

    @Autowired
    RescueMemberDao rescueMemberDao;

    public List<LostCaseDTO> getLostCaseByFamilyMember(Integer id){
        List<LostCase> lostCaseList = lostCaseDao.getLostCaseByFamilyMember(id);
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

    public List<LostCaseDTO> getAllLostCase(){
        return moreList(lostCaseDao.getLostCase());
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
            LostCaseDTO result = more(lostCase1);

            // 派单
            List<RescueMemberDTO> rescueMemberDTOList = rescueMemberService.getRescueMemberStatus(RescueMemberStatus.FREE);
            Place targetPlace = result.getPlace();
            for(RescueMemberDTO rescue: rescueMemberDTOList){
                Place place1 = rescue.getPlace();
                Double distance = GeoUtil.getDistance(place1.getLongitude(),place1.getLatitude(),targetPlace.getLongitude(),targetPlace.getLatitude());
                rescue.setDistance(distance);
            }
            rescueMemberDTOList.sort(Comparator.comparing(RescueMemberDTO::getDistance));

            Integer rescue_Number = result.getRescue_num();
            Integer number = rescueMemberDTOList.size() < rescue_Number ? rescueMemberDTOList.size() : rescue_Number;
            rescueMemberDTOList = rescueMemberDTOList.subList(0, number);
            for(RescueMemberDTO rescueMemberDTO: rescueMemberDTOList){
                Case_RescueMember case_rescueMember = new Case_RescueMember();
                case_rescueMember.setCase_id(result.getId());
                case_rescueMember.setRescue_member_id(rescueMemberDTO.getId());

                rescueMemberDao.updateStatus(rescueMemberDTO.getId(),RescueMemberStatus.WORKING);
                lostCaseDao.createCase_RescueMember(case_rescueMember);

                String familyOpenId = WxUtil.getOpenId(rescueMemberDTO.getUsername());
                WxUtil.SendMessage("新的案件",lostCase1.getId().toString(),"有新的案件",familyOpenId);
            }

            // 通知家属发布成功
            MissingPerson missingPerson = missingPersonDao.getMissingPersonByCase(lostCase1.getMissing_person_id());
            FamilyMember familyMember = familyMemberDao.getFamilyMemberById(missingPerson.getFamily_member_id());
            String familyOpenId = WxUtil.getOpenId(familyMember.getUsername());
            WxUtil.SendMessage("案件状态更新",lostCase1.getId().toString(),"审核通过",familyOpenId);

            return result;
        }
        else{
            throw new RuntimeException("案件未处于待审核状态");
        }
    }

    public LostCaseDTO rejectLostCase(Integer id) {
        LostCase lostCase = lostCaseDao.getLostCaseById(id);
        if(lostCase.getStatus() == LostCaseStatus.AUDITING){
            lostCase.setStatus(LostCaseStatus.REJECTED);
            LostCase lostCase1 = lostCaseDao.updateLostCase(lostCase);

            // 通知家属拒绝
            MissingPerson missingPerson = missingPersonDao.getMissingPersonByCase(lostCase1.getMissing_person_id());
            FamilyMember familyMember = familyMemberDao.getFamilyMemberById(missingPerson.getFamily_member_id());
            String familyOpenId = WxUtil.getOpenId(familyMember.getUsername());
            WxUtil.SendMessage("案件状态更新",lostCase1.getId().toString(),"审核不通过",familyOpenId);

            return more(lostCase1);
        }
        else{
            throw new RuntimeException("案件未处于待审核状态");
        }
    }



    public List<RescueMemberDTO> getRescueMemberPlaceByCase(Integer caseId) {
        List<RescueMember> rescueMemberList = lostCaseDao.getAllRescueMember(caseId);
        List<RescueMemberDTO> rescueMemberDTOList = new ArrayList<>();
        for(RescueMember rescueMember: rescueMemberList){
            if(rescueMember.getPlace_id() == null) continue;
            Place place = placeDao.getPlaceById(rescueMember.getPlace_id());
            RescueMemberDTO rescueMemberDTO = new RescueMemberDTO();
            BeanUtils.copyProperties(rescueMember,rescueMemberDTO);
            rescueMemberDTO.setPlace(place);
            rescueMemberDTOList.add(rescueMemberDTO);
        }
        return rescueMemberDTOList;
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
