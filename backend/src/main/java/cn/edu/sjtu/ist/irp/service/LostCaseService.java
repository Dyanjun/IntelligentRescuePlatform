package cn.edu.sjtu.ist.irp.service;

import cn.edu.sjtu.ist.irp.dao.LostCaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dyanjun
 * @date 2021/12/15 15:47
 */
@Service
public class LostCaseService {

    @Autowired
    LostCaseDao lostCaseDao;

    public void getProceedingLostCaseByRescueMember(String id){
        lostCaseDao.getProceedingLostCaseByRescueMember(id);
    }
}
