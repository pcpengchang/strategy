package com.pengchang.strategy.strategy;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pengchang.strategy.entity.DomainBatchFactorFetchResponse;
import com.pengchang.strategy.entity.DomainFactorFetchResponse;
import com.pengchang.strategy.entity.FactorFetchDTO;
import com.pengchang.strategy.enums.FactorTemplateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author pengchang
 * @date 2023/08/19 01:04
 **/
@Slf4j
public class FactorFetchStrategyFactory {

    private static final int MAX_FETCH_TIME = 200;

    private Map<FactorTemplateTypeEnum, BaseFactorFetchStrategy> factorFetchStrategyMap = Maps.newHashMap();

    {
        factorFetchStrategyMap.put(new TimeFactorFetchStrategy().supportTemplate(), new TimeFactorFetchStrategy());
    }

    public DomainBatchFactorFetchResponse batchFactorFetch(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap) {
        // 按照模板类型进行分类
        // key是模板类型，value是dimensionId对应的取数fetchDTO集合
        Map<FactorTemplateTypeEnum, Map<String, Set<FactorFetchDTO>>> templateType2FetchMap = Maps.newHashMap();
        DomainBatchFactorFetchResponse response = initResponse(dimensionId2FetchMap);

        dimensionId2FetchMap.forEach((dimensionId, factorFetchSet) ->
                factorFetchSet.forEach((factorFetch) -> {
                    FactorTemplateTypeEnum factorTemplateTypeEnum = factorFetch.getFactorTemplateTypeEnum();
                    templateType2FetchMap.computeIfAbsent(factorTemplateTypeEnum, templateTypeEnum -> Maps.newHashMap())
                            .computeIfAbsent(dimensionId, templateTypeEnum -> Sets.newHashSet())
                            .add(factorFetch);
                }));

        // 并行调用取数
        CompletableFuture[] fetchFutureArray = templateType2FetchMap.entrySet().stream().map(entry -> {
            // 不同模板对应不同数据源
            FactorTemplateTypeEnum templateType = entry.getKey();
            Map<String, Set<FactorFetchDTO>> fetchMap = entry.getValue();
            return CompletableFuture.runAsync(() -> doBatchFactorFetch(fetchMap, templateType, response));
        }).toArray(CompletableFuture[]::new);

        try {
            //最多等待200ms
            CompletableFuture.allOf(fetchFutureArray).get(MAX_FETCH_TIME, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("factorFetch error {}", dimensionId2FetchMap, e);
        }

        return response;
    }

    private void doBatchFactorFetch(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap,
                                    FactorTemplateTypeEnum templateType, DomainBatchFactorFetchResponse response) {
        if (MapUtils.isEmpty(dimensionId2FetchMap)) {
            return;
        }

        //策略工厂获取取数处理器，执行取数
       BaseFactorFetchStrategy fetchStrategy = factorFetchStrategyMap.get(templateType);
        //如果未找到对应的处理器，说明此时不支持此种因子取数
        if (fetchStrategy == null) {

            return;
        }

        DomainBatchFactorFetchResponse subResponse = null;
        try {
            subResponse = fetchStrategy.batchFactorFetch(dimensionId2FetchMap);
        } catch (Exception e) {
            log.error("FactorFetchStrategyFactory.doBatchFactorFetch error {}", dimensionId2FetchMap, e);
        }

        //处理取数成功的
        subResponse.getDimensionId2ResultMap().forEach((dimensionId, fetchResult) -> {
            if (MapUtils.isNotEmpty(fetchResult.getFetch2Result())) {
                response.getDimensionId2ResultMap().get(dimensionId).getFetch2Result()
                        .putAll(fetchResult.getFetch2Result());
            }
        });
    }

    private DomainBatchFactorFetchResponse initResponse(Map<String, Set<FactorFetchDTO>> dimensionId2FetchMap) {
       DomainBatchFactorFetchResponse response = new DomainBatchFactorFetchResponse()
                .setDimensionId2ResultMap(Maps.newHashMapWithExpectedSize(dimensionId2FetchMap.size()));
        dimensionId2FetchMap.forEach((dimensionId, factorFetchSet) -> {
            DomainFactorFetchResponse factorFetchResponse = new DomainFactorFetchResponse()
                    .setFetch2Result(Maps.newHashMap());
            response.getDimensionId2ResultMap().put(dimensionId, factorFetchResponse);
        });
        return response;
    }
}
