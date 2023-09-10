package com.pengchang.strategy.entity;

import com.pengchang.strategy.enums.ExistInTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @author pengchang
 * @date 2023/08/19 09:57
 **/
@Data
@Accessors(chain = true)
public class RuleInfoVO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 在名单内通过 or 不在名单内通过
     */
    private ExistInTypeEnum existInTypeEnum;
    /**
     * 因子实例id 集合
     */
    private Set<Long> factorIds;
    /**
     * 规则表达式
     */
    private String expression;
}
