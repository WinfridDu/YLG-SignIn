package com.ylg.project.controller;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.project.domain.SignUpLimit;
import com.ylg.project.domain.request.TimeRequest;
import com.ylg.project.service.SignUpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;

/**
 * 报班人数限制，报班时间限制
 */
@CrossOrigin
@RestController
@RequestMapping(value="/signup/time")
public class SignUpLimitController extends BaseController{

    @Autowired
    private SignUpLimitService signUpLimitService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result save(int maxPeople, @RequestBody TimeRequest timeRequest){
        if(!timeRequest.isRight()){
            return new Result(ResultCode.TIME_ERROR);
        }
        SignUpLimit signUpLimit = makeTime(timeRequest);
        signUpLimit.setMaxPeople(maxPeople);
        signUpLimit.setOrganizationId(organizationId);
        signUpLimitService.save(signUpLimit);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(int maxPeople, @PathVariable(value = "id") String id, @RequestBody TimeRequest timeRequest) {
        if(!timeRequest.isRight()){
            return new Result(ResultCode.TIME_ERROR);
        }
        SignUpLimit signUpLimit = makeTime(timeRequest);
        signUpLimit.setId(id);
        signUpLimit.setOrganizationId(organizationId);
        signUpLimit.setMaxPeople(maxPeople);
        signUpLimitService.update(signUpLimit);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result find() {
        SignUpLimit signUpLimit = signUpLimitService.find(organizationId);
        return new Result(ResultCode.SUCCESS,signUpLimit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        signUpLimitService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    private SignUpLimit makeTime(TimeRequest timeRequest){
        SignUpLimit signUpLimit = new SignUpLimit();
        signUpLimit.setStartTime(new Time(timeRequest.getStartTime().getTime()));
        signUpLimit.setStartDay(timeRequest.getStartDay());
        signUpLimit.setEndTime(new Time(timeRequest.getEndTime().getTime()));
        signUpLimit.setEndDay(timeRequest.getEndDay());
        signUpLimit.setOrganizationId(organizationId);
        return signUpLimit;
    }

}
