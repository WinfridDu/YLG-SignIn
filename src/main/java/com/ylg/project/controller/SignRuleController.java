package com.ylg.project.controller;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.project.domain.SignRule;
import com.ylg.project.service.SignRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 签到签退时间规范
 */
@CrossOrigin
@RestController
@RequestMapping(value="/sign/rule")
public class SignRuleController extends BaseController {
    @Autowired
    private SignRuleService signRuleService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result save(@RequestBody SignRule signRule) {
        signRule.setOrganizationId(organizationId);
        signRuleService.save(signRule);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody SignRule signRule) {
        signRule.setId(id);
        signRule.setOrganizationId(organizationId);
        signRuleService.update(signRule);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result find() {
        SignRule signRule = signRuleService.findByOrganizationId(organizationId);
        return new Result(ResultCode.SUCCESS, signRule);
    }
}
