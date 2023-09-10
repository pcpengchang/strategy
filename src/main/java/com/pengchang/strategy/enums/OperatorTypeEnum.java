package com.pengchang.strategy.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * 操作符类型
 */
public enum OperatorTypeEnum {
    EQUAL(1, "==", "等于"),
    IN(2, "in", "位于"),
    LAGER_THAN(3, ">", "大于"),
    SMALL_THAN(4, "<", "小于"),
    LAGER_THAN_OR_EQUAL(5, ">=", "大于或等于"),
    SMALL_THAN_OR_EQUAL(6, "<=", "小于或等于"),
    ;

    private Integer code;

    private String operatorKey;

    private String desc;

    OperatorTypeEnum(Integer code, String operatorKey, String desc) {
        this.code = code;
        this.operatorKey = operatorKey;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getOperatorKey() {
        return operatorKey;
    }

    public static OperatorTypeEnum findByCode(Integer code) {
        return Arrays.stream(OperatorTypeEnum.values()).filter(typeEnum -> Objects.equals(typeEnum.getCode(), code))
                .findFirst().orElse(null);
    }
}
