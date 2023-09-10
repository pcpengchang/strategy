package com.pengchang.strategy.flagship.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规则类型枚举类
 *
 * @author pengchang
 * @date 2023/9/1
 */
@Getter
@AllArgsConstructor
public enum RuleTypeEnum {

    FILTER_RULE(1, "过滤规则"),
    DISPLAY_RULE(2, "展示规则");

    private int value;

    private String desc;
}
