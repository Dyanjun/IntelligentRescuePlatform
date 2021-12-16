package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.Clue;
import cn.edu.sjtu.ist.irp.entity.dto.ClueDTO;
import cn.edu.sjtu.ist.irp.service.ClueService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/15 12:44
 */
@RestController
@RequestMapping("/clue")
public class ClueController {
    @Autowired
    ClueService clueService;

    @PostMapping("")
    public Result<ClueDTO> createClue(@RequestBody ClueDTO clueDTO, @RequestParam(value = "files") MultipartFile[] files){
        return ResultUtil.success(clueService.createClue(clueDTO,files));
    }

    @GetMapping("/case/{id}")
    public Result<List<ClueDTO>> getClueByCase(@PathVariable Integer id){
        return ResultUtil.success(clueService.getClueByCase(id));
    }
}
