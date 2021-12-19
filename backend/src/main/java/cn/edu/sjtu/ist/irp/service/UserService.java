package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.dao.UserDao;
import cn.edu.sjtu.ist.irp.entity.FamilyMember;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.RescueMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dyanjun
 * @date 2021/12/16 22:01
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    PlaceDao placeDao;

    public Integer loginFamilyMember(String username){
        FamilyMember familyMember = userDao.getFamilyMemberByUsername(username);
        if(familyMember==null) throw new RuntimeException("家属不存在");
        return familyMember.getId();
    }

    public Integer loginRescueMember(String username){
        RescueMember rescueMember = userDao.getRescueMemberByUsername(username);
        // TODO 更新定位
        if(rescueMember==null) throw new RuntimeException("救援人员不存在");
        return rescueMember.getId();
    }

    public FamilyMember registerFamilyMember(FamilyMember domain){
        if(userDao.getFamilyMemberByUsername(domain.getUsername()) != null){
            throw new RuntimeException("用户名已存在");
        }
        FamilyMember familyMember = userDao.createFamilyMember(domain);
        return familyMember;
    }

    public RescueMember registerRescueMember(RescueMember domain){
        if(userDao.getRescueMemberByUsername(domain.getUsername()) != null){
            throw new RuntimeException("用户名已存在");
        }
        RescueMember rescueMember = userDao.createRescueMember(domain);
        return rescueMember;
    }

    public void updateRescueMember(Integer id, Place place) {
        RescueMember rescueMember = userDao.getRescueMemberById(id);
        if(rescueMember.getPlace_id() == null){
            Place place2 = placeDao.createPlace(place);
            rescueMember.setPlace_id(place2.getId());
            userDao.putRescueMember(rescueMember);
            return;
        }
        Place place1 = placeDao.getPlaceById(rescueMember.getPlace_id());
        place.setId(place1.getId());
        placeDao.putPlace(place);
    }
}
