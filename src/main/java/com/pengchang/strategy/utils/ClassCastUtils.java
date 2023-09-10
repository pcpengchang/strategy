package com.pengchang.strategy.utils;

import com.pengchang.strategy.enums.VarTypeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClassCastUtils {

    public static <T> T tryCastObject(Object object, Class<T> type){
        //类型一致或者目标类型是object可直接返回
        if (type.equals(Object.class) || type.equals(object.getClass())) {
            return (T) object;
        }
        return VarTypeEnum.toDestType(object, type);
    }

    public static Double object2Double(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Double) {
            return (Double) object;
        }
        try {
            return Double.valueOf(object.toString());
        } catch (Exception e) {
            log.warn("object to double exception, obj:{}, msg:{}", object, e.getMessage(), e);
            return null;
        }
    }

    public static Integer object2Int(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        try {
            Double d = Double.valueOf(object.toString());
            return d.intValue();
        } catch (Exception e) {
            log.warn("object to int exception, obj:{}, msg:{}", object, e.getMessage(), e);
            return null;
        }
    }

    public static Long object2Long(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof Long) {
            return (Long) object;
        }
        try {
            Double d = Double.valueOf(object.toString());
            return d.longValue();
        } catch (Exception e) {
            log.warn("object to long exception, obj:{}, msg:{}", object, e.getMessage(), e);
            return null;
        }
    }

    public static String object2String(Object object) {
        if (null == object) {
            return null;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return object.toString();
    }

    public static Boolean object2Boolean(Object object) {
        if (null == object) {
            return false;
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        }

        Integer intValue = ClassCastUtils.object2Int(object);
        if (intValue != null) {
            return intValue == 1;
        }

        if (object instanceof String) {
            String objStr = object.toString().toLowerCase();
            if ("true".equals(objStr)) {
                return Boolean.TRUE;
            } else if ("false".equals(objStr)) {
                return Boolean.FALSE;
            } else {
                return false;
            }
        }
        return false;
    }
}