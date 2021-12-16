package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.FamilyMember;
import cn.edu.sjtu.ist.irp.entity.RescueMember;
import cn.edu.sjtu.ist.irp.service.UserService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dyanjun
 * @date 2021/12/15 20:06
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login/family_member")
    public Result<Integer> loginFamilyMember(@RequestParam(value = "username") String username){
        return ResultUtil.success(userService.loginFamilyMember(username));
    }

    @GetMapping("/login/rescue_member")
    public Result<Integer> loginRecsueMember(@RequestParam(value = "username") String username){
        return ResultUtil.success(userService.loginRescueMember(username));
    }

    @PostMapping("/register/family_member")
    public Result<FamilyMember> registerFamilyMember(@RequestBody FamilyMember familyMember){
        return ResultUtil.success(userService.registerFamilyMember(familyMember));
    }

    @PostMapping("/register/rescue_member")
    public Result<RescueMember> registerRescueMember(@RequestBody RescueMember rescueMember){
        return ResultUtil.success(userService.registerRescueMember(rescueMember));
    }
}
