package com.ylg.project.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sign_rules")
@Data
public class SignRule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    /**
     * 开始前几分钟可签到
     */
    private int beforeIn;

    /**
     * 开始几分钟后算迟到
     */
    private int lateAfterIn;

    /**
     * 开始几分钟后算旷班（不可签到）
     */
    private int absentAfterIn;

    /**
     * 结束前几分钟可签退
     */
    private int beforeOut;

    /**
     * 结束后几分钟可签退
     */
    private int afterOut;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 连续班次是否允许一次签到
     */
    private int signInOnce;

}
