package com.ylg.organization.controller;

import com.ylg.common.annotation.IgnoreAuth;
import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.domain.organization.Organization;
import com.ylg.organization.service.OrganizationService;
import com.ylg.project.controller.DutyTimeController;
import com.ylg.project.domain.SignUpLimit;
import com.ylg.project.domain.WifiInfo;
import com.ylg.project.service.DutyTimeService;
import com.ylg.project.service.SignRuleService;
import com.ylg.project.service.SignUpLimitService;
import com.ylg.project.service.WifiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private WifiInfoService wifiInfoService;

    @Autowired
    private DutyTimeService dutyTimeService;

    @Autowired
    private SignRuleService signRuleService;

    @Autowired
    private SignUpLimitService signUpLimitService;

    //保存组织
    @RequestMapping(value="",method = RequestMethod.POST)
    public Result save(@RequestBody Organization organization)  {
        //业务操作
        organizationService.add(organization);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id更新组织
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value="id") String id, @RequestBody Organization organization ) {
        //业务操作
        organization.setId(id);
        organizationService.update(organization);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/check",method = RequestMethod.PUT)
    public Result check() {
        if(null == wifiInfoService.findByOrganizationId(organizationId)){
            return new Result(ResultCode.LACK_OF_WIFIINFO);
        }else if(null == signUpLimitService.findByOrganizationId(organizationId)){
            return new Result(ResultCode.LACK_OF_SIGNUP_LIMIT);
        }else if(0 == dutyTimeService.findAll(organizationId).size()){
            return new Result(ResultCode.LACK_OF_DUTYTIME);
        }else if(null == signRuleService.findByOrganizationId(organizationId)){
            return new Result(ResultCode.LACK_OF_SIGNRULE);
        }else{
            Organization organization = organizationService.findById(organizationId);
            organization.setState(1);
            organizationService.update(organization);
            return new Result(ResultCode.SUCCESS);
        }
    }

    //根据id删除组织
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value="id") String id) {
        organizationService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    //根据id查询组织
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value="id") String id) {
        Organization organization = organizationService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(organization);
        return result;
    }


    //查询全部组织列表
    @IgnoreAuth
    @RequestMapping(value="",method = RequestMethod.GET)
    public Result findAll(@RequestParam Map map) {
        List<Organization> list = organizationService.findAll(map);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(list);
        return result;
    }
}
