package com.hunterstudios.hunters.helper;

import com.hunterstudios.hunters.repository.Period;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Date;

public class DateHelper {
    public static Period createYearPeriod(int year) {
        Period period = new Period();
        period.setBegin(String.format("%04d-01-01", year));
        period.setEnd(String.format("%04d-12-31", year));
        return period;
    }

    public static Date toDate(OffsetDateTime offsetDateTime) {
        Instant instant = offsetDateTime.toInstant();
        return Date.from(instant);
    }
}
