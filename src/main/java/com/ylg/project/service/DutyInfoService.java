package com.ylg.project.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.project.dao.DutyInfoDao;
import com.ylg.project.domain.DutyInfo;
import com.ylg.project.domain.DutyTime;
import com.ylg.project.domain.response.RankData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DutyInfoService {
    @Autowired
    private DutyInfoDao dutyInfoDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private DutyTimeService dutyTimeService;

    public int sameCount(String organizationId, Timestamp date){
        return dutyInfoDao.countByOrganizationIdAndStartTime(organizationId,date);
    }

    public void save(DutyInfo dutyInfo){
        dutyInfo.setId(idWorker.nextId()+"");
        dutyInfoDao.save(dutyInfo);
    }

    public DutyInfo findByStartTimeAndUserId(Timestamp startTime, String userId){
        return dutyInfoDao.findByStartTimeAndUserIdAndStateIsNot(startTime,userId,1);
    }

    public List<DutyInfo> findByPeriod(String userId, Timestamp firstEnd, Timestamp nextStart){
        return dutyInfoDao.findByUserIdAndStartTimeBetweenAndStateIsNot(userId,firstEnd,nextStart,1);
    }

    public void update(DutyInfo dutyInfo){
        dutyInfoDao.save(dutyInfo);
    }

    public void deleteById(String id) {
        dutyInfoDao.deleteById(id);
    }

    public List<DutyInfo> findAll(Timestamp startTime1, Timestamp startTime2,String organizationId){
        return dutyInfoDao.findByOrganizationIdAndStartTimeBetweenAndStateIsNot(organizationId,startTime1,startTime2,1);
    }

    public List<DutyInfo> findAll(Map<String,Object> map) {
        //1.需要查询条件
        Specification<DutyInfo> spec = new Specification<DutyInfo>() {
            /**
             * 动态拼接查询条件
             * @return
             */
            @SneakyThrows
            public Predicate toPredicate(Root<DutyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的organizationId是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("organizationId"))) {
                    list.add(criteriaBuilder.equal(root.get("organizationId").as(String.class), map.get("organizationId")));
                }
                //根据请求的部门id构造查询条件
                if(!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class), map.get("departmentId")));
                }
                //根据状态构造查询条件
                if(!StringUtils.isEmpty(map.get("state"))) {
                    list.add(criteriaBuilder.equal(root.get("state").as(Integer.class),Integer.parseInt((String)map.get("state"))));
                }
                //根据构造查询条件
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String) map.get("date"));
                if(StringUtils.isEmpty(map.get("dutyTimeId"))) {
                    Timestamp time1 = new Timestamp(date.getTime());
                    Timestamp time2 = new Timestamp(date.getTime()+24*60*60*1000);
                    list.add(criteriaBuilder.between(root.get("startTime").as(Timestamp.class),time1,time2));
                }else{
                    DutyTime dutyTime = dutyTimeService.findById((String)map.get("dutyTimeId"));
                    Calendar calendar = Calendar.getInstance(Locale.CHINA);
                    calendar.setTimeInMillis(date.getTime());
                    calendar.set(Calendar.HOUR_OF_DAY,dutyTime.getStartTime().getHours());
                    calendar.set(Calendar.MINUTE,dutyTime.getStartTime().getMinutes());
                    Timestamp time = new Timestamp(calendar.getTimeInMillis());
                    list.add(criteriaBuilder.equal(root.get("startTime").as(Timestamp.class),time));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return dutyInfoDao.findAll(spec);
    }

    public DutyInfo findById(String id){
        return dutyInfoDao.findById(id).get();
    }

    public List<Object> rank(int year,int month,String organizationId){
        List<Object> rankList = dutyInfoDao.findDutyCount(year,month,organizationId)
                .stream().map(RankData::getOne)
                .collect(Collectors.toList());
        Calendar cal = Calendar.getInstance(Locale.CHINA);
        int temp = cal.get(Calendar.DATE);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1,
                0, 0, 0);
        Timestamp beginOfMonth = new Timestamp(cal.getTimeInMillis());
        cal.set(Calendar.HOUR_OF_DAY,temp);
        Timestamp beginOfToday = new Timestamp(cal.getTimeInMillis());
        List<DutyInfo> dutyInfoList = dutyInfoDao.findByStateAndOrganizationIdAndStartTimeBetween(0, organizationId,beginOfMonth, beginOfToday);
        for (DutyInfo dutyInfo : dutyInfoList) {
            for (Object data : rankList){
                if(((RankData) data).getUserName().equals(dutyInfo.getUserName())){
                    rankList.remove(data);
                    break;
                }
            }
        }
        return rankList;
    }

    public List<Object> selectCountOfMonth(int year,int month,String organizationId){
        List<Object> rankList = dutyInfoDao.findDutyCount(year,month,organizationId)
                .stream().map(RankData::getOne)
                .collect(Collectors.toList());
        return rankList;
    }
}
