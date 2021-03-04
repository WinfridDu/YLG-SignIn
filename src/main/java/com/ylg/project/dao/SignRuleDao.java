package com.ylg.project.dao;

import com.ylg.project.domain.SignRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SignRuleDao extends JpaRepository<SignRule,String>, JpaSpecificationExecutor<SignRule> {
    SignRule findByOrganizationId(String id);
}
