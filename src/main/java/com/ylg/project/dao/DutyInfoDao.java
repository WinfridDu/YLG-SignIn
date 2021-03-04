package com.ylg.project.dao;

import com.ylg.project.domain.DutyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface DutyInfoDao extends JpaRepository<DutyInfo,String>, JpaSpecificationExecutor<DutyInfo> {

    int countByOrganizationIdAndStartTime(String id, Timestamp date);

    DutyInfo findByStartTimeAndUserIdAndStateIsNot(Timestamp startTime, String userId, int state);

    List<DutyInfo> findByUserIdAndStartTimeBetweenAndStateIsNot(String id, Timestamp firstEnd, Timestamp nextStart,int state);

    List<DutyInfo> findByOrganizationIdAndStartTimeBetween(String organizationId, Timestamp startTime, Timestamp startTime2);

    List<DutyInfo> findByOrganizationIdAndStartTimeBetweenAndStateIsNot(String id,Timestamp startTime, Timestamp startTime2, int state);

    List<DutyInfo> findByStartTimeAndOrganizationId(Timestamp startTime, String organizationId);

    @Query(value = "SELECT t.user_name,t.count, " +
            "(SELECT count(*)+1 FROM " +
            "(SELECT t.user_name, COUNT(*) count FROM `tb_duty_info` as t where YEAR(t.start_time)=?1 and MONTH(t.start_time)=?2 and t.state not in (0,1,2,3) and organization_id = ?3 GROUP BY t.user_name) b " +
            "WHERE count > t.count) ranking " +
            "FROM (SELECT t.user_name, COUNT(*) count FROM `tb_duty_info` as t where YEAR(t.start_time)=?1 and MONTH(t.start_time)=?2 and t.state not in (0,1,2,3) and organization_id = ?3 GROUP BY t.user_name) t " +
            "ORDER BY t.count desc",nativeQuery = true)
    List<Object[]> findDutyCount(int year,int month,String organizationId);

    List<DutyInfo> findByStateAndOrganizationIdAndStartTimeBetween(int state, String organizationId, Timestamp startTime, Timestamp startTime2);

}
