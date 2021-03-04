package com.ylg.project.domain.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@Data
public class TimeRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date startTime;

    /**
     * 星期几
     */
    private int startDay;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private int endDay;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

//    private String dutyTimeId;

//    private Integer state;

    public boolean isRight(){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//以周一为首日
        calendar.set(Calendar.DAY_OF_WEEK, startDay);
        calendar.set(Calendar.HOUR, startTime.getHours());
        calendar.set(Calendar.MINUTE, startTime.getMinutes());
        Date startDay = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, endDay);
        calendar.set(Calendar.HOUR, endTime.getHours());
        calendar.set(Calendar.MINUTE, endTime.getMinutes());
        Date endDay = calendar.getTime();
        return startDay.before(endDay);
    }
}
