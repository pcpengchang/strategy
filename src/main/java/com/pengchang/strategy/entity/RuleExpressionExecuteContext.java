package com.pengchang.strategy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author pengchang
 * @date 2023/08/18 23:35
 **/
@Data
@Accessors(chain = true)
public class RuleExpressionExecuteContext {
    /**
     * 因子信息，对应类型等
     */
    private Map<Long, FactorInfoVO> factorItemMap;
    /**
     * 每个因子取数的结果，对应结果等
     */
    private Map<Long, Object> factorId2ResultMap;
}
