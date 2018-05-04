package com.lbd.filesystem.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luozhanghua on 2016/9/7 0007.
 */
public class DateUtils {
    public static Timestamp getTimestamp() {
        Date date = new Date();//获得系统时间.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
        return Timestamp.valueOf(nowTime);// 把时间转换
    }

    public static Timestamp getTimestamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
        return Timestamp.valueOf(nowTime);// 把时间转换
    }
}
