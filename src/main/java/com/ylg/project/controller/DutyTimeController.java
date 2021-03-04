package com.ylg.project.controller;

import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.common.exception.CommonException;
import com.ylg.project.domain.DutyTime;
import com.ylg.project.domain.SignRule;
import com.ylg.project.domain.request.TimeRequest;
import com.ylg.project.service.DutyTimeService;
import com.ylg.project.service.SignRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.*;

/**
 * 值班时间段设置
 */
@CrossOrigin
@RestController
@RequestMapping(value="/duty/time")
public class DutyTimeController extends BaseController {

    @Autowired
    private DutyTimeService dutyTimeService;

    @Autowired
    private SignRuleService signRuleService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result save(@RequestBody TimeRequest timeRequest) {
        DutyTime dutyTime;
        try {
            dutyTime = makeDutyTime(timeRequest);
        } catch (CommonException e) {
            return new Result(ResultCode.TIME_ERROR);
        }
        try {
            dutyTimeService.save(dutyTime);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.REPEAT_TIME);
        }
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody TimeRequest timeRequest) {
        DutyTime dutyTime;
        try {
            dutyTime = makeDutyTime(timeRequest);
        } catch (CommonException e) {
            return new Result(ResultCode.TIME_ERROR);
        }
        dutyTime.setId(id);
        try {
            dutyTimeService.update(dutyTime);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.REPEAT_TIME);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询时间段列表
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result findAll() {
        List<DutyTime> dutyTimes = dutyTimeService.findAll(organizationId);
        return new Result(ResultCode.SUCCESS,dutyTimes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) {
        dutyTimeService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        DutyTime dutyTime = dutyTimeService.findById(id);
        return new Result(ResultCode.SUCCESS,dutyTime);
    }

    @RequestMapping(value = "/now", method = RequestMethod.GET)
    public Result findCurrentPeriod(){
        SignRule signRule = signRuleService.findByOrganizationId(organizationId);
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        //获取时间范围
        cal.setTimeInMillis(System.currentTimeMillis()-signRule.getAbsentAfterIn()*60*1000);
        Time begin = new Time(cal.getTimeInMillis());
        cal.setTimeInMillis(System.currentTimeMillis()+signRule.getBeforeIn()*60*1000);
        Time end = new Time(cal.getTimeInMillis());
        List<DutyTime> list = dutyTimeService.findCurrentPeriod(begin,end);
        if(0 == list.size()){
            return new Result(ResultCode.NOT_IN_DUTY_TIME);
        }
        return new Result(ResultCode.SUCCESS,list);
    }

    private DutyTime makeDutyTime(TimeRequest timeRequest) throws CommonException {
        if(timeRequest.getStartTime().after(timeRequest.getEndTime())){
            throw new CommonException(ResultCode.TIME_ERROR);
        }
        DutyTime dutyTime = new DutyTime();
        dutyTime.setStartTime(new Time(timeRequest.getStartTime().getTime()));
        dutyTime.setEndTime(new Time(timeRequest.getEndTime().getTime()));
        dutyTime.setOrganizationId(organizationId);
        return dutyTime;
    }
}
