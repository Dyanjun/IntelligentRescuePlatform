package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.LostCase;
import cn.edu.sjtu.ist.irp.service.LostCaseService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dyanjun
 * @date 2021/12/15 10:18
 */

@RestController
@RequestMapping("/case")
public class LostCaseController {

    @Autowired
    LostCaseService lostCaseService;

    /**
     * 提交走失案例
     */
    @PostMapping("")
    public Result submitLostCase(@RequestBody LostCase lostCase){
        return ResultUtil.success();
    }

    /**
     * 发布走失案例
     */
    @PostMapping("/{id}")
    public Result publishLostCase(@PathVariable String id){
        return ResultUtil.success();
    }

    @GetMapping("/rescue_member/{id}/proceeding")
    public Result getProceedingLostCaseByRescueMemberId(@PathVariable String id){
        lostCaseService.getProceedingLostCaseByRescueMember(id);
        return ResultUtil.success();
    }

}
