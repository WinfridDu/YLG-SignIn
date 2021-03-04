package com.ylg.domain.system.response;

import com.ylg.domain.system.Role;
import com.ylg.domain.system.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResult implements Serializable {

    /**
     * ID
     */
    @Id
    private String id;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * openid
     */
    private String openid;

    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer enableState;
    /**
     * 创建时间
     */
    private Date createTime;

    private String organizationId;

    private String organizationName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 编号
     */
    private String workNumber;

    /**
     * 在职状态 1.在职  2.离职
     */
    private Integer inServiceStatus;

    private String departmentName;

    private List<String> roleIds = new ArrayList<>();

    public UserResult(User user) {
        BeanUtils.copyProperties(user,this);
        for (Role role : user.getRoles()) {
            this.roleIds.add(role.getId());
        }
    }
}
