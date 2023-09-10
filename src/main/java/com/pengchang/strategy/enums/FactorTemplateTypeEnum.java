package com.pengchang.strategy.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author pengchang
 * @date 2023/08/19 11:01
 **/
public enum FactorTemplateTypeEnum {
    /**
     * 时间维度
     */
    TIME(1001L, "时间正确"),

    /**
     * 名称维度
     */
    NAME(2001L, "名称正确");

    private Long code;

    private String desc;

    FactorTemplateTypeEnum(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public long getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static FactorTemplateTypeEnum findByCode(Long code) {
        return Arrays.stream(FactorTemplateTypeEnum.values()).filter(typeEnum -> Objects.equals(typeEnum.getCode(), code))
                .findFirst().orElse(null);
    }
}
