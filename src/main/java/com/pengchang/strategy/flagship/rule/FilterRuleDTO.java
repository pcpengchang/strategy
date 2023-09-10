package com.pengchang.strategy.flagship.rule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 过滤规则
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Getter
@Setter
@ToString
public class FilterRuleDTO extends BaseRuleDTO implements Serializable {
    private static final long serialVersionUID = 7677471777274781283L;
    /**
     * 规则列表
     */
    private List<String> rules;

    /**
     * 过滤解释信息模版
     */
    private FilterExplainDTO filterExplain;
}
