package cn.edu.sjtu.ist.irp.controller;

import cn.edu.sjtu.ist.irp.entity.LostCaseStatus;
import cn.edu.sjtu.ist.irp.entity.Place;
import cn.edu.sjtu.ist.irp.entity.dto.LostCaseDTO;
import cn.edu.sjtu.ist.irp.entity.dto.RescueMemberDTO;
import cn.edu.sjtu.ist.irp.service.LostCaseService;
import cn.edu.sjtu.ist.irp.util.response.Result;
import cn.edu.sjtu.ist.irp.util.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result<LostCaseDTO> submitLostCase(@RequestBody LostCaseDTO lostCaseDTO) {
        return ResultUtil.success(lostCaseService.createLostCase(lostCaseDTO));
    }

    /**
     * 发布走失案例
     */
    @PostMapping("/{id}/publish")
    public Result<LostCaseDTO> publishLostCase(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.publishLostCase(id));
    }

    /**
     * 拒绝走失案例
     */
    @PostMapping("/{id}/reject")
    public Result<LostCaseDTO> rejectLostCase(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.rejectLostCase(id));
    }

    /**
     * 拒绝走失案例
     */
    @PostMapping("/{id}/finish")
    public Result<LostCaseDTO> finishLostCase(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.finishLostCase(id));
    }


    /**
     * 救援人员获取正在参与的走失案例
     */
    @GetMapping("/rescue_member/{id}/proceeding")
    public Result<LostCaseDTO> getProceedingLostCaseByRescueMember(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.getProceedingLostCaseByRescueMember(id));
    }

    /**
     * 救援人员获取正在参与和参与过的所有案例
     */
    @GetMapping("/rescue_member/{id}/all")
    public Result<List<LostCaseDTO>> getAllLostCaseByRescueMember(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.getLostCaseByRescueMember(id));
    }

    /**
     * 家属获取其提交的所有案例
     */
    @GetMapping("/family_member/{id}")
    public Result<List<LostCaseDTO>> getAllLostCaseByFamilyMember(@PathVariable Integer id) {
        return ResultUtil.success(lostCaseService.getLostCaseByFamilyMember(id));
    }

    /**
     * 审核人员获取需要审核的案例
     */
    @GetMapping("/audit")
    public Result<List<LostCaseDTO>> getAllLostCaseForAudit() {
        return ResultUtil.success(lostCaseService.getLostCaseByStatus(LostCaseStatus.AUDITING));
    }

    /**
     * 审核人员获取所有的案例
     */
    @GetMapping("")
    public Result<List<LostCaseDTO>> getAllLostCase() {
        return ResultUtil.success(lostCaseService.getAllLostCase());
    }

    @GetMapping("/{caseId}/rescue_member")
    public Result<List<RescueMemberDTO>> getRescueMemberPlaceByCase(@PathVariable Integer caseId){
        return ResultUtil.success(lostCaseService.getRescueMemberPlaceByCase(caseId));
    }
}
