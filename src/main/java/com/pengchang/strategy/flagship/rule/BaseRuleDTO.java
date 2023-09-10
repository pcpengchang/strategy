package com.pengchang.strategy.flagship.rule;

import lombok.Data;

import java.io.Serializable;

/**
 * 规则基类
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Data
public class BaseRuleDTO implements Serializable {
    private static final long serialVersionUID = -3748828516426112256L;

    /**
     * 规则名称
     */
    protected String name;
    /**
     * 规则类型
     */
    protected int ruleType;
}
