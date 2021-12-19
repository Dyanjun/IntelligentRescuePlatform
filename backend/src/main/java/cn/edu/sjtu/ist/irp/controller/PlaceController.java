package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.dao.PlaceDao;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dyanjun
 * @date 2021/12/17 11:24
 */
@RestController
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    PlaceDao placeDao;

    @GetMapping("/{id}")
    public Result<Place> getPlaceById(@PathVariable Integer id){
        return ResultUtil.success(placeDao.getPlaceById(id));
    }
}
