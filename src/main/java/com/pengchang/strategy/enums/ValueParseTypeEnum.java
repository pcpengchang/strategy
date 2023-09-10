package com.pengchang.strategy.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author pengchang
 * @date 2023/08/19 01:07
 **/
public enum ValueParseTypeEnum {
    SINGLE(1, "单值"),
    LIST(2, "多值"),
    ;

    private Integer code;

    private String desc;

    ValueParseTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ValueParseTypeEnum findByCode(Integer code) {
        return Arrays.stream(ValueParseTypeEnum.values()).filter(typeEnum -> Objects.equals(typeEnum.getCode(), code))
                .findFirst().orElse(null);
    }
}
