package com.ylg.project.dao;

import com.ylg.project.domain.DutyTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Time;
import java.util.List;

public interface DutyTimeDao extends JpaRepository<DutyTime,String>, JpaSpecificationExecutor<DutyTime> {

    List<DutyTime> findAllByOrganizationIdOrderByStartTime(String organizationId);

    List<DutyTime> findAllByStartTimeBetween(Time time1,Time time2);
}
