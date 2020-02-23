package com.demand.driven.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zzy on 2019/11/3.
 */
public class DateUtil {

    private static String SDF_LONG = "yyyy-MM-dd HH:mm:ss SSS";

    public static String formatDateLongStr(Date date){

        SimpleDateFormat sdf =new SimpleDateFormat(SDF_LONG);
        String str = sdf.format(date);

        return str;
    }
}
