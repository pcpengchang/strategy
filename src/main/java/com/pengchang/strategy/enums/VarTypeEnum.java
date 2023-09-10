package com.pengchang.strategy.enums;

import com.pengchang.strategy.utils.ClassCastUtils;

/**
 * 规则因子返回类型枚举
 */
public enum VarTypeEnum {
    LONG(1, "long数值型", Long.class),
    STRING(2, "String型", String.class),
    BOOLEAN(3, "布尔型", Boolean.class),
    ;

    private Integer code;

    private String desc;

    private Class clazz;

    VarTypeEnum(Integer code, String desc, Class clazz) {
        this.code = code;
        this.desc = desc;
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Class getClazz() {
        return clazz;
    }

    public static VarTypeEnum valueOf(Class clazz) {
        for (VarTypeEnum varType : VarTypeEnum.values()) {
            if (varType.getClazz().equals(clazz)) {
                return varType;
            }
        }
        return null;
    }

    public static <T> T toDestType(Object object, VarTypeEnum type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case LONG:
                return (T) ClassCastUtils.object2Long(object);
            case BOOLEAN:
                return (T) ClassCastUtils.object2Boolean(object);
            case STRING:
                return (T) ClassCastUtils.object2String(object);
            default:
                break;
        }
        return null;
    }

    /**
     * 根据类对象将Object对象转换成指定的类型
     *
     * @param object Object对象
     * @param clazz  class
     * @param <T>    泛型
     * @return 指定类型实例
     */
    public static <T> T toDestType(Object object, Class<T> clazz) {
        VarTypeEnum type = VarTypeEnum.valueOf(clazz);
        if (type == null) {
            return null;
        }
        return toDestType(object, type);
    }
}
