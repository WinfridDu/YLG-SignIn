package com.ylg.project.controller;

import com.ylg.common.annotation.LoginUser;
import com.ylg.common.controller.BaseController;
import com.ylg.common.entity.Result;
import com.ylg.common.entity.ResultCode;
import com.ylg.common.redis.RedisCache;
import com.ylg.domain.system.User;
import com.ylg.project.domain.DutyInfo;
import com.ylg.project.domain.DutyTime;
import com.ylg.project.domain.SignRule;
import com.ylg.project.domain.SignUpLimit;
import com.ylg.project.domain.request.TimeRequest;
import com.ylg.project.service.DutyInfoService;
import com.ylg.project.service.DutyTimeService;
import com.ylg.project.service.SignRuleService;
import com.ylg.project.service.SignUpLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 签到表操作
 */
@CrossOrigin
@RestController
@RequestMapping(value="/duty")
public class DutyInfoController extends BaseController {

    @Autowired
    private DutyInfoService dutyInfoService;

    @Autowired
    private SignUpLimitService signUpLimitService;

    @Autowired
    private DutyTimeService dutyTimeService;

    @Autowired
    private SignRuleService signRuleService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 报班
     * @param user
     * @param dutyTimeId
     * @param weekDay
     * @return
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Result save(@LoginUser User user,String dutyTimeId,int weekDay) {
        SignUpLimit signUpLimit = signUpLimitService.findByOrganizationId(organizationId);
        Calendar calendar = getCalender();

        //判断是否在报班时间内报班
        if(!isInRegularTime(calendar, signUpLimit)){
            return new Result(ResultCode.NOT_IN_SIGN_TIME);
        }

        //判断报班人数是否达到上限
        calendar.add(Calendar.DATE,7);
        DutyTime dutyTime = dutyTimeService.findById(dutyTimeId);
        calendar.set(Calendar.DAY_OF_WEEK, weekDay);
        calendar.set(Calendar.HOUR_OF_DAY,dutyTime.getStartTime().getHours());
        calendar.set(Calendar.MINUTE,dutyTime.getStartTime().getMinutes());
        Timestamp dutyStartTime = new Timestamp(calendar.getTimeInMillis());
        if(signUpLimit.getMaxPeople()<=dutyInfoService.sameCount(organizationId,dutyStartTime)){
            return new Result(ResultCode.UP_TO_MAX);
        }

        calendar.set(Calendar.HOUR_OF_DAY,dutyTime.getEndTime().getHours());
        calendar.set(Calendar.MINUTE,dutyTime.getEndTime().getMinutes());
        Timestamp dutyEndTime = new Timestamp(calendar.getTimeInMillis());

        DutyInfo dutyInfo = new DutyInfo();
        dutyInfo.setUserId(user.getId());
        dutyInfo.setUserName(user.getName());
        dutyInfo.setCreateTime(new Date());
        dutyInfo.setStartTime(dutyStartTime);
        dutyInfo.setEndTime(dutyEndTime);
        dutyInfo.setWeekDay(weekDay);
        dutyInfo.setOrganizationId(organizationId);
        dutyInfo.setDepartmentId(user.getDepartmentId());
        dutyInfo.setDepartmentName(user.getDepartmentName());
        dutyInfo.setMobile(user.getMobile());
        try {
            dutyInfoService.save(dutyInfo);
        }catch (DataIntegrityViolationException e){
            return new Result(ResultCode.REPEAT_SAME);
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     *撤销前的查询报班信息
     * @param user
     * @return
     */
    @RequestMapping(value = "/signup/data", method = RequestMethod.GET)
    public Result findByUserId(@LoginUser User user) {
        SignUpLimit signUpLimit = signUpLimitService.findByOrganizationId(organizationId);
        Calendar calendar = getCalender();

        //判断是否在报班时间内
        if(!isInRegularTime(calendar, signUpLimit)){
            return new Result(ResultCode.NOT_IN_REVOKE_TIME);
        }
        calendar.add(Calendar.DATE,7);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE,7);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        List<DutyInfo> dutyInfoList = dutyInfoService.findByPeriod(user.getId(),beginTime,endTime);
        return new Result(ResultCode.SUCCESS,dutyInfoList);
    }

    /**
     * 查询某一天某一个班次的报班数据
     * @param map
     * @return
     */
    @RequestMapping(value = "/data/time", method = RequestMethod.GET)
    public Result findByTime(@RequestParam Map<String,Object> map) {
        map.put("organizationId", organizationId);
        List<DutyInfo> dutyInfoList = dutyInfoService.findAll(map);
        return new Result(ResultCode.SUCCESS, dutyInfoList);
    }

    /**
     * 查询某个星期的值班情况
     * @param timeRequest
     * @return
     */
    @RequestMapping(value = "/data/week", method = RequestMethod.GET)
    public Result findAllByWeek(@RequestBody TimeRequest timeRequest) {
        Timestamp beginTime;
        Timestamp endTime;
        if(null!=timeRequest.getDate()){
            beginTime = new Timestamp(timeRequest.getDate().getTime());
            endTime = new Timestamp(timeRequest.getDate().getTime()+7*24*60*60*1000);
        }else{
            Calendar calendar = getCalender();
            calendar.set(Calendar.DAY_OF_WEEK,2);
            calendar.set(Calendar.HOUR_OF_DAY,0);
            beginTime = new Timestamp(calendar.getTimeInMillis());
            calendar.set(Calendar.DAY_OF_WEEK,1);
            endTime = new Timestamp(calendar.getTimeInMillis());
        }
        List<DutyInfo> list = dutyInfoService.findAll(beginTime,endTime,organizationId);
        return new Result(ResultCode.SUCCESS, list);
    }

    @RequestMapping(value = "/data/week/this", method = RequestMethod.GET)
    public Result findAllByWeek() {
        Calendar calendar = getCalender();
        calendar.set(Calendar.DAY_OF_WEEK,2);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        Timestamp beginTime = new Timestamp(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_WEEK,1);
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        List<DutyInfo> list = dutyInfoService.findAll(beginTime,endTime,organizationId);
        return new Result(ResultCode.SUCCESS, list);
    }

    /**
     * 撤销报班
     * @param id
     * @return
     */
    @RequestMapping(value = "/revoke/{id}", method = RequestMethod.DELETE)
    public Result revoke(@PathVariable(value = "id") String id) {
        dutyInfoService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 判断用户状态：是否值班中
     * @return
     */
    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public Result findUserState(@LoginUser User user) {
        if(redisCache.getCacheList(user.getId()).isEmpty()){
            return new Result(ResultCode.NOT_IN_DUTY);
        }else{
            return new Result(ResultCode.IN_DUTY);
        }
    }

    /**
     * 请假
     * @param id
     * @return
     */
    @RequestMapping(value = "/leave/{id}", method = RequestMethod.PUT)
    public Result takeLeave(@PathVariable(value = "id") String id) {
        DutyInfo dutyInfo = dutyInfoService.findById(id);
        dutyInfo.setState(1);
        dutyInfoService.update(dutyInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 换班
     * @param id1
     * @param id2
     * @return
     */
    @Transactional
    @RequestMapping(value = "/swap/{id1}/{id2}", method = RequestMethod.PUT)
    public Result swap(@PathVariable(value = "id1") String id1, @PathVariable(value = "id2") String id2) {
        DutyInfo dutyInfo1 = dutyInfoService.findById(id1);
        DutyInfo dutyInfo2 = dutyInfoService.findById(id2);
        Timestamp tempStart = new Timestamp(dutyInfo1.getStartTime().getTime());
        Timestamp tempEnd = new Timestamp(dutyInfo1.getEndTime().getTime());
        int tempWeekDay = dutyInfo1.getWeekDay();
        dutyInfo1.setStartTime(dutyInfo2.getStartTime());
        dutyInfo1.setEndTime(dutyInfo2.getEndTime());
        dutyInfo1.setWeekDay(dutyInfo2.getWeekDay());
        dutyInfo2.setStartTime(tempStart);
        dutyInfo2.setEndTime(tempEnd);
        dutyInfo2.setWeekDay(tempWeekDay);
        dutyInfoService.update(dutyInfo1);
        dutyInfoService.update(dutyInfo2);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 修改员工值班情况
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result changeState(@PathVariable(value = "id") String id, int state) {
        DutyInfo dutyInfo = dutyInfoService.findById(id);
        dutyInfo.setState(state);
        dutyInfoService.update(dutyInfo);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 签到
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "sign/in/{id}", method = RequestMethod.PUT)
    public Result signIn(@LoginUser User user, @PathVariable(value = "id") String id) {
        DutyTime dutyTime = dutyTimeService.findById(id);
        //获取calendar并初始化
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(System.currentTimeMillis());//当前时间
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.HOUR_OF_DAY,dutyTime.getStartTime().getHours());
        calendar.set(Calendar.MINUTE,dutyTime.getStartTime().getMinutes());
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());
        DutyInfo dutyInfo = dutyInfoService.findByStartTimeAndUserId(startTime,user.getId());
        //判断是否报过该班
        if(null == dutyInfo){
            return new Result(ResultCode.NEVER_SIGN_UP);
        }
        //判断是否签过到
        if(dutyInfo.getSignInTime()!=null){
            return new Result(ResultCode.REPEAT_SIGN_IN);
        }
        dutyInfo.setSignInTime(new Date());
        //判断是否迟到
        SignRule signRule = signRuleService.findByOrganizationId(organizationId);
        Date lateTime = new Date(startTime.getTime()+signRule.getLateAfterIn()*60*1000);
        if(new Date().after(lateTime)){
            dutyInfo.setState(3);
        }else{
            dutyInfo.setState(2);
        }
        dutyInfoService.update(dutyInfo);

        long expireTime;
        //允许连续班次一次签到
        if(signRule.getSignInOnce() == 1){
            List<DutyInfo> dutyInfoList = new ArrayList<>();
            while(null != dutyInfo){
                dutyInfoList.add(dutyInfo);
                Timestamp endTime = dutyInfo.getEndTime();
                Timestamp nextStartTime = new Timestamp(endTime.getTime()+30*60*1000);
                List<DutyInfo> list = dutyInfoService.findByPeriod(user.getId(),endTime,nextStartTime);
                dutyInfo = (list.size() == 0? null : list.get(0));
            }
            dutyInfo = dutyInfoList.get(dutyInfoList.size()-1);
            expireTime = dutyInfo.getEndTime().getTime()-System.currentTimeMillis()+signRule.getAfterOut()*60*1000;
            redisCache.setCacheList(user.getId(), dutyInfoList, expireTime, TimeUnit.MILLISECONDS);
        }else{
            //不允许相邻班次一次签到
            expireTime = dutyInfo.getEndTime().getTime()-System.currentTimeMillis()+signRule.getAfterOut()*60*1000;
            redisCache.setCacheObject(user.getId(),dutyInfo,expireTime,TimeUnit.MILLISECONDS);
        }

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 签退
     * @param user
     * @return
     */
    @RequestMapping(value = "/sign/out", method = RequestMethod.PUT)
    public Result signOut(@LoginUser User user) {
        SignRule signRule = signRuleService.findByOrganizationId(organizationId);
        if(signRule.getSignInOnce() == 1){
            List<DutyInfo> dutyInfoList = redisCache.getCacheList(user.getId());
            boolean flag = false;
            for (int i = 0; i<dutyInfoList.size(); i++) {
                Date signOutBegin = new Date(dutyInfoList.get(i).getEndTime().getTime()-signRule.getBeforeOut()*60*1000);
                Date signOutEnd = new Date(dutyInfoList.get(i).getEndTime().getTime()+signRule.getAfterOut()*60*1000);
                if(new Date().after(signOutBegin) && new Date().before(signOutEnd)){
                    DutyInfo dutyInfo = dutyInfoList.get(0);
                    dutyInfo.setState(dutyInfo.getState()+2);
                    dutyInfo.setSignOutTime(new Date());
                    dutyInfoService.update(dutyInfo);
                    for(int j = 1; j <= i; j++){
                        DutyInfo temp = dutyInfoList.get(j);
                        temp.setState(4);//正常签到正常签退
                        temp.setSignInTime(dutyInfo.getSignInTime());
                        temp.setSignOutTime(new Date());
                        dutyInfoService.update(temp);
                    }
                    flag = true;
                    break;
                }
            }
            if(!flag){
                return new Result(ResultCode.NOT_IN_SIGNOUT_TIME);
            }else{
                redisCache.deleteObject(user.getId());
                return new Result(ResultCode.SUCCESS);
            }
        }else{
            DutyInfo dutyInfo = redisCache.getCacheObject(user.getId());
            Date signOutBegin = new Date(dutyInfo.getEndTime().getTime()-signRule.getBeforeOut()*60*1000);
            Date signOutEnd = new Date(dutyInfo.getEndTime().getTime()+signRule.getAfterOut()*60*1000);
            if(!(new Date().after(signOutBegin) && new Date().before(signOutEnd))){
                return new Result(ResultCode.NOT_IN_SIGNOUT_TIME);
            }else{
                dutyInfo.setState(dutyInfo.getState()+2);
                dutyInfo.setSignOutTime(new Date());
                dutyInfoService.update(dutyInfo);
                redisCache.deleteObject(user.getId());
                return new Result(ResultCode.SUCCESS);
            }
        }
    }

    /**
     * 获取当月值班排名
     * @return
     */
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Result findUserState() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;
        List<Object> list = dutyInfoService.rank(year,month,organizationId);
        return new Result(ResultCode.SUCCESS,list);
    }

    /**
     * 每月值班次数统计
     * @return
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Result findStatistics(int year,int month) {
        List<Object> list = dutyInfoService.selectCountOfMonth(year,month,organizationId);
        return new Result(ResultCode.SUCCESS,list);
    }

    private Calendar getCalender(){
        //获取calendar并初始化
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        calendar.setTimeInMillis(System.currentTimeMillis());//当前时间
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar;
    }

    /**
     * 判断是否在规定时间
     */
    private boolean isInRegularTime(Calendar calendar, SignUpLimit signUpLimit){
        //判断是否在报班时间内报班
        calendar.set(Calendar.DAY_OF_WEEK, signUpLimit.getStartDay());
        calendar.set(Calendar.HOUR_OF_DAY,signUpLimit.getStartTime().getHours());
        calendar.set(Calendar.MINUTE,signUpLimit.getStartTime().getMinutes());
        Timestamp startTime = new Timestamp(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_WEEK, signUpLimit.getEndDay());
        calendar.set(Calendar.HOUR_OF_DAY,signUpLimit.getEndTime().getHours());
        calendar.set(Calendar.MINUTE,signUpLimit.getEndTime().getMinutes());
        Timestamp endTime = new Timestamp(calendar.getTimeInMillis());
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return currentTime.after(startTime) && currentTime.before(endTime);
    }
}
