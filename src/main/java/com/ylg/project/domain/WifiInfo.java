package com.ylg.project.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "wifi_info")
@Data
public class WifiInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private String wifiName;

    private String wifiPwd;

    private String wifiSsid;

    private String organizationId;

}
