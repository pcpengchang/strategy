package com.pengchang.strategy.enums;

import java.util.Arrays;
import java.util.Objects;

public enum ExistInTypeEnum {
    EXIST_PASS(1, "在名单内通过"),
    NOT_EXIST_PASS(2, "不在名单内通过"),
    ;

    private Integer code;

    private String desc;

    ExistInTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ExistInTypeEnum findByCode(Integer code) {
        return Arrays.stream(ExistInTypeEnum.values()).filter(typeEnum -> Objects.equals(typeEnum.getCode(), code))
                .findFirst().orElse(null);
    }
}
