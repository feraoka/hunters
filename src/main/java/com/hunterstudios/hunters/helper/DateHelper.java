package com.hunterstudios.hunters.helper;

import com.hunterstudios.hunters.repository.Period;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.tomcat.jni.Local;

public class DateHelper {
    public static Period createYearPeriod(int year) {
        Period period = new Period();
        if (year == 0) {
            period.setBegin("0000-01-01");
            period.setEnd("2999-12-31");
        } else {
            period.setBegin(String.format("%04d-01-01", year));
            period.setEnd(String.format("%04d-12-31", year));
        }
        return period;
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
