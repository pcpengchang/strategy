package com.pengchang.strategy.utils;

/**
 * @author pengchang
 * @date 2023/09/10 22:10
 **/
public class DateUtils {
    /**
     * 获取两个日期相差天数，时间戳单位 ms
     */
    public static int getDistanceDay(long time1, long time2) {
        long distance;
        if (time1 < time2) {
            distance = time2 - time1;
        } else {
            distance = time1 - time2;
        }
        return (int) (distance / (24 * 60 * 60 * 1000));
    }
}
