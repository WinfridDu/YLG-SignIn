package com.ylg.domain.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "co_organization")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    //ID
    @Id
    private String id;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织管理员ID
     */
    private String managerId;
    /**
     * 组织管理员
     */
    private String manager;
    /**
     * 组织管理员电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String mailbox;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 审核状态
     */
    private String auditState;
    /**
     * 状态
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;

}
