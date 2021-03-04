package com.ylg.project.dao;

import com.ylg.project.domain.SignUpLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SignUpLimitDao extends JpaRepository<SignUpLimit,String>, JpaSpecificationExecutor<SignUpLimit> {
    SignUpLimit findByOrganizationId(String id);
}
