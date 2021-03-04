package com.ylg.project.domain.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class RankData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;

    private int count;

    private int ranking;

    private RankData(Object[] m) {
        this.userName = String.valueOf(m[0]);
        this.count = Integer.parseInt(m[1].toString());
        this.ranking = Integer.parseInt(m[2].toString());
    }

    public static RankData getOne(Object[] m) {
        return new RankData(m);
    }
}
