package cn.edu.sjtu.ist.irp.controller;


import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.InfoDTO;
import cn.edu.sjtu.ist.irp.service.InfoService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/15 20:07
 */
@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    InfoService infoService;

    @PostMapping("/{type}")
    public Result<List<InfoDTO>> getInfo(@PathVariable String type, @RequestBody Place place){
        return ResultUtil.success(infoService.getInfo(type,place));
    }
}
