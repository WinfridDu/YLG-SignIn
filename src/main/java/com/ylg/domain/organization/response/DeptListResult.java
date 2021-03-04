package com.ylg.domain.organization.response;

import com.ylg.domain.organization.Department;
import com.ylg.domain.organization.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {

    private String organizationId;
    private String organizationName;
    private String organizationManage;//公司联系人
    private List<Department> depts;

    public DeptListResult(Organization organization, List depts) {
        this.organizationId = organization.getId();
        this.organizationName = organization.getName();
        this.organizationManage = organization.getManager();//公司联系人
        this.depts = depts;
    }
}
