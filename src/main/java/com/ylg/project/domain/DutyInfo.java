package com.ylg.project.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tb_duty_info")
@Data
public class DutyInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String userId;

    private String userName;

    @ApiModelProperty(hidden = true)
    private Timestamp startTime;

    private Timestamp endTime;

    private String organizationId;

    private String departmentId;

    private String departmentName;

    private Date createTime;

    private Date signInTime;

    private Date signOutTime;

    private int state;

    private String mobile;

    /**
     * 1~7表示星期日到星期六
     */
    private int weekDay;

}
