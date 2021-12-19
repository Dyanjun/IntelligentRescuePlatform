package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.dao.RescueMemberDao;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.entity.RescueMemberStatus;
import cn.edu.sjtu.ist.irp.entity.dto.RescueMemberDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/19 17:36
 */
@Service
public class RescueMemberService {
    @Autowired
    RescueMemberDao rescueMemberDao;

    @Autowired
    PlaceDao placeDao;

    public List<RescueMemberDTO> getRescueMemberStatus(RescueMemberStatus status){
        List<RescueMember> rescueMemberList = rescueMemberDao.getRescueMemberByStatus(status);
        List<RescueMemberDTO> rescueMemberDTOList = new ArrayList<>();
        for(RescueMember rescueMember: rescueMemberList){
            if(rescueMember.getPlace_id()==null) continue;
            Place place = placeDao.getPlaceById(rescueMember.getPlace_id());
            RescueMemberDTO rescueMemberDTO = new RescueMemberDTO();
            BeanUtils.copyProperties(rescueMember,rescueMemberDTO);
            rescueMemberDTO.setPlace(place);
            rescueMemberDTOList.add(rescueMemberDTO);
        }
        return rescueMemberDTOList;
    }
}
