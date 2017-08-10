package com.autoio.core_sdk.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wuhongyun on 17-8-10.
 */

public class TimeUtils {

    public static String getTimeZoneText(TimeZone tz) {
        SimpleDateFormat sdf = new SimpleDateFormat("ZZZZ, zzzz");
        sdf.setTimeZone(tz);
        return sdf.format(new Date());
    }

    public static String getTimeZoneText() {
        SimpleDateFormat sdf = new SimpleDateFormat("ZZZZ, zzzz");
        sdf.setTimeZone(Calendar.getInstance().getTimeZone());
        return sdf.format(new Date());
    }
}
