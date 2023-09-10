package com.pengchang.strategy.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 因子执行结果
 **/
@Data
@Accessors(chain=true)
public class DomainFactorFetchResponse {
    /**
     * 因子的取数结果
     */
    private Map<FactorFetchDTO, Object> fetch2Result;
}
