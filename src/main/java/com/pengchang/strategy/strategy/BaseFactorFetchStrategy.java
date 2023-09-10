package com.pengchang.strategy.strategy;

import com.pengchang.strategy.entity.DomainBatchFactorFetchResponse;
import com.pengchang.strategy.entity.FactorFetchDTO;
import com.pengchang.strategy.enums.FactorTemplateTypeEnum;

import java.util.Map;
import java.util.Set;

/**
 * 因子取数处理器
 */
public interface BaseFactorFetchStrategy {
    /**
     * 批量取数
     */
    DomainBatchFactorFetchResponse batchFactorFetch(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap) throws Exception;

    /**
     * 支持的取数模板
     */
    FactorTemplateTypeEnum supportTemplate();
}
