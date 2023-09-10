package com.pengchang.strategy.flagship.rule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 展示规则
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Getter
@Setter
public class DisplayRuleDTO extends BaseRuleDTO implements Serializable {
    private static final long serialVersionUID = -3359809085118700600L;

    /**
     * 规则列表
     */
    private List<String> rules;
}
