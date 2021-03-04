package com.ylg.organization.dao;

import com.ylg.domain.organization.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 部门dao接口
 */
public interface DepartmentDao extends JpaRepository<Department,String> ,JpaSpecificationExecutor<Department> {
    public Department findByIdAndOrganizationId(String id, String organizationId);
}
