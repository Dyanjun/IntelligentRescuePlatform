package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.service.MissingPersonService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/17 0:14
 */
@RestController
@RequestMapping("/missing_person")
public class MissingPersonController {

    @Autowired
    MissingPersonService missingPersonService;

    @PostMapping("")
//    public Result<MissingPersonDTO>  createMissingPerson(@RequestBody MissingPersonDTO missingPersonDTO, @RequestParam(value = "files", required = false) MultipartFile[] files){
//        return ResultUtil.success(missingPersonService.createMissingPerson(missingPersonDTO, files));
//    }
    public Result<MissingPersonDTO>  createMissingPerson(@RequestBody MissingPersonDTO missingPersonDTO, @RequestParam(value = "files", required = false) MultipartFile[] files){
        return ResultUtil.success(missingPersonService.createMissingPerson(missingPersonDTO));
    }

    @GetMapping("/family_member/{id}")
    public Result<List<MissingPersonDTO>>  getMissingPersonByFamilyMember(@PathVariable Integer id){
        return ResultUtil.success(missingPersonService.getMissingPersonByFamilyMember(id));
    }

    @GetMapping("/case/{id}")
    public Result<MissingPersonDTO>  getMissingPersonByCase(@PathVariable Integer id){
        return ResultUtil.success(missingPersonService.getMissingPersonByCase(id));
    }

}
