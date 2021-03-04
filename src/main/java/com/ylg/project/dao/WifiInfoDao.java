package com.ylg.project.dao;

import com.ylg.project.domain.WifiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface WifiInfoDao extends JpaRepository<WifiInfo,String>, JpaSpecificationExecutor<WifiInfo> {
    WifiInfo findByOrganizationId(String id);
}
