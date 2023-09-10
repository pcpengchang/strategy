package com.pengchang.strategy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 批量取数结果
 **/
@Data
@Accessors(chain = true)
public class DomainBatchFactorFetchResponse {
    /**
     * key为dimensionId, value是取数结果
     */
    private Map<String, DomainFactorFetchResponse> dimensionId2ResultMap;
}
