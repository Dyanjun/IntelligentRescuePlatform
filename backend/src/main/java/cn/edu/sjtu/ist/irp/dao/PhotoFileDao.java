package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.PhotoFile;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/17 0:47
 */
@Component
public class PhotoFileDao {

    public PhotoFile createPhoto(PhotoFile domain){
        // TODO 存数据库
        domain.setUrl("xx");
        return domain;
    }
}
