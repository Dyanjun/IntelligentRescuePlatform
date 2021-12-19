package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.util.HttpURLConnectionUtil;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dyanjun
 * @date 2021/12/19 23:08
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @GetMapping("")
    public Result getAddress(@RequestParam(value = "lat") String lat, @RequestParam(value="lon") String lon){
        return ResultUtil.success(HttpURLConnectionUtil.findByLatAndLng(lon,lat));
    }
}
