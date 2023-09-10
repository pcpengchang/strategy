package com.pengchang.strategy.flagship.model;

import com.pengchang.strategy.flagship.rule.DisplayRuleDTO;
import com.pengchang.strategy.flagship.rule.FilterRuleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 推荐引擎
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Getter
@Setter
@ToString
public class RecommendEngineDTO implements Serializable {
    private static final long serialVersionUID = -7198402596151919579L;

    /**
     * 引擎id
     */
    private long id;

    /**
     * 过滤规则
     */
    private FilterRuleDTO filterRule;

    /**
     * 展示规则
     */
    private DisplayRuleDTO displayRule;
}
