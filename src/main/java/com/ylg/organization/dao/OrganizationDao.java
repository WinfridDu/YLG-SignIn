package com.ylg.organization.dao;

import com.ylg.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrganizationDao extends JpaRepository<Organization,String>, JpaSpecificationExecutor<Organization> {

}
