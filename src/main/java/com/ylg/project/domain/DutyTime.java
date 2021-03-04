package com.ylg.project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "duty_time")
@Data
public class DutyTime implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    /**
     * 起始时间的秒数
     */
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Time startTime;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Time endTime;

    private String organizationId;
}
