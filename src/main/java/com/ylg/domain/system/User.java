package com.ylg.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 */
@Entity
@Table(name = "bs_user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 启用状态 0为待审核 1为审核通过 2为审核不通过
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

    /**
     * level
     *     String
     *          sysAdmin：系统管理员具备所有权限
     *          orAdmin：组织管理（创建组织的时候添加）
     *          deAdmin：部长
     *          user：普通用户（需要分配角色）
     */
    private String level;

    /**
     *  JsonIgnore
     *     : 忽略json转化
     */
    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="pe_user_role",joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id",referencedColumnName="id")}
    )
    private Set<Role> roles = new HashSet<>();//用户与角色   多对多
}
