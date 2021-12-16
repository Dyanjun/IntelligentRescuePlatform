package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.MissingPerson;
import cn.edu.sjtu.ist.irp.entity.dto.MissingPersonDTO;
import cn.edu.sjtu.ist.irp.service.MissingPersonService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public Result<MissingPerson>  createMissingPerson(@RequestBody MissingPersonDTO missingPerson, @RequestParam(value = "files") MultipartFile[] files){
        return ResultUtil.success(missingPersonService.createMissingPerson(missingPerson, files));
    }

    @GetMapping("/{id}")
    public Result<MissingPerson>  getMissingPersonByCase(@PathVariable Integer id){
        return ResultUtil.success(missingPersonService.getMissingPersonByCase(id));
    }

}
