package com.ylg.project.service;

import com.ylg.common.utils.IdWorker;
import com.ylg.project.dao.DutyTimeDao;
import com.ylg.project.domain.DutyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
public class DutyTimeService {

    @Autowired
    private DutyTimeDao dutyTimeDao;

    @Autowired
    private IdWorker idWorker;

    public void save(DutyTime dutyTime) {
        dutyTime.setId(idWorker.nextId()+"");
        dutyTimeDao.save(dutyTime);
    }

    public List<DutyTime> findAll(String organizationId) {
        return dutyTimeDao.findAllByOrganizationIdOrderByStartTime(organizationId);
    }

    public void deleteById(String id) {
        dutyTimeDao.deleteById(id);
    }

    public void update(DutyTime dutyTime) {
        dutyTimeDao.save(dutyTime);
    }

    public DutyTime findById(String id) {
        return dutyTimeDao.findById(id).get();
    }

    public List<DutyTime> findCurrentPeriod(Time time1,Time time2){
        return dutyTimeDao.findAllByStartTimeBetween(time1,time2);
    }
}
