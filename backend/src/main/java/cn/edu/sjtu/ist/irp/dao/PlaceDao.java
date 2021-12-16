package cn.edu.sjtu.ist.irp.dao;

import cn.edu.sjtu.ist.irp.entity.Clue;
import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.util.DatabaseUtil;
import cn.edu.sjtu.ist.irp.util.convert.BeanMapUtilByReflect;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/17 1:51
 */
@Component
public class PlaceDao {
    private final String BaseUrl = "http://202.120.40.86:14642/rmp-resource-service/project/61b5ae6978ae950017f6ec74/resource/place";

    public Place createPlace(Place domain){
        Map<String, Object> requestParam = BeanMapUtilByReflect.postBeanToMap(domain);
        LinkedHashMap<String, Object> data = DatabaseUtil.sendPostRequest(BaseUrl,requestParam);
        return BeanMapUtilByReflect.mapToBean(data, Place.class);
    }

    public Place getPlaceById(Integer id){
        String url = BaseUrl + "/?place.id="+id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        if(data.size()>0){
            Place place = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) data.get(0), Place.class);
            return place;
        }
        return null;
    }

    public List<Place> getPlaceByMissingPerson(Integer id){
        String url = BaseUrl + "/?place.lost_person_id=" + id.toString();
        List<?> data = DatabaseUtil.sendGetRequest(url);
        List<Place> places = new ArrayList<>();
        for(Object obj : data){
            Place place = BeanMapUtilByReflect.mapToBean((LinkedHashMap<String, Object>) obj, Place.class);
            places.add(place);
        }
        return places;
    }
}
