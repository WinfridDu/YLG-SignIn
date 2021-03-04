package com.ylg.project.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "sign_limit")
@Data
public class SignUpLimit implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    /**
     * 时间
     */
    private Time startTime;
    /**
     * 星期几
     */
    private int startDay;

    private Time endTime;

    private int endDay;

    private String organizationId;

    private int maxPeople;
}
