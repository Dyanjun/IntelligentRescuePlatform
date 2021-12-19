package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.Photo;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/17 0:48
 */
@Component
public class PhotoDao {
    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/photo";

    public Photo createPhoto(Photo domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, Photo.class);
    }

    public Photo getPhotoById(Integer id){
        String url = BaseUrl + "/?photo.id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            Photo photo = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), Photo.class);
            return photo;
        }
        return null;
    }

    public Photo getPhotoByMissingPerson(Integer id){
        String url = BaseUrl + "/?photo.lost_person_id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            Photo photo = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), Photo.class);
            return photo;
        }
        return null;
    }
}
