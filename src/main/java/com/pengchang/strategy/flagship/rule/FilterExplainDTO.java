package com.pengchang.strategy.flagship.rule;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 过滤规则解释说明信息
 *
 * @author pengchang
 * @date 2023/8/28
 */
@Getter
@Setter
@ToString
public class FilterExplainDTO implements Serializable {
    private static final long serialVersionUID = 649319131536152829L;

    /**
     * 解释信息
     */
    private String loseTemplate;
}
